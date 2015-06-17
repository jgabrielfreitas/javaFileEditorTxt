package com.application;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import objects.FileManager;
import objects.Person;

public class MainApplication {

	public  static final String APPLICATION_FILE_PATH = File.listRoots()[0] + File.separator + "Crud_UVA";
	private static FileManager manager = null;
	private static final String DEFAULT_TEXT = "Aluno:20:Joao Gabriel:9876.54:16/06/2015andAluno:21:Isabella Lucero:9876.54:16/06/2015";

	public static void main(String[] args) {
		

		// verifica se a pasta da aplicacao existe
		File fileToCheck = new File(APPLICATION_FILE_PATH);
		if (fileToCheck.exists() == false) {

			boolean createdPath = fileToCheck.mkdir();
			if (createdPath == true) {
				manager = new FileManager(fileToCheck.getAbsolutePath() + File.separator + "crud_java.txt");
				// cria o arquivo default
				if(manager.getFile().exists() == false) {
					manager.saveEditedFile(DEFAULT_TEXT);
					goToTable();
				}
			}
			
		} else {
			
			// cria o arquivo default
			manager = new FileManager(fileToCheck.getAbsolutePath() + File.separator + "crud_java.txt");
			
			if(manager.getFile().exists() == false) {
				
				try {
					
					manager.getFile().createNewFile();
					manager.saveEditedFile(DEFAULT_TEXT);
					goToTable();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else 
				goToTable();
		}

	}
	
	public static List<Person> loadPersonsByFile(FileManager manager){
		List<Person> personList = new ArrayList<Person>();
		String contentFile = manager.loadFileByPath();
		String[] objectAsString = contentFile.split("and");
		
		for (int i = 0; i < objectAsString.length; i++) {
			String personAsString = objectAsString[i];
			String[] objectInfo = personAsString.split(":");
			Person person = new Person(objectInfo[0], Integer.parseInt(objectInfo[1]), objectInfo[2], Double.parseDouble(objectInfo[3]));
			person.setCreateAt(objectInfo[4]);
			personList.add(person);
		}
		
		return personList;
	}

	private static void goToTable(){
		TableApplication application = new TableApplication(loadPersonsByFile(manager));
		application.setManager(manager);
	}
}
