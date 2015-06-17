package objects;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.JOptionPane;
import javax.swing.JTable;

public class FileManager {
	
	File file;
	FileReader fileReader;

	public FileManager(String filePath) {
		file = new File(filePath);
	}
	
	public File getFile(){
		return file;
	}

	public String loadFileByPath() {

		String fileAsString = null;

		try {
			
			FileReader fileReader = new FileReader(file);
			
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			StringBuilder stringBuilder = new StringBuilder();
			String currentLine = bufferedReader.readLine();

			while (currentLine != null) {
				stringBuilder.append(currentLine);
				stringBuilder.append(System.lineSeparator());
				currentLine = bufferedReader.readLine();
			}
			
			fileAsString = stringBuilder.toString();
			bufferedReader.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileAsString;
	}

	public boolean saveEditedFile(String toSave) {
		
		boolean success = false;

		try {
			
            BufferedWriter bufwriter = new BufferedWriter(new FileWriter(file)); // new buffer writer passing the current file to edit
            bufwriter.write(toSave);//writes the edited string buffer to the new file
            bufwriter.close();//closes the file
            success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return success;

	}
	
	public boolean validateFile() throws Exception{
		if(file.getAbsolutePath().endsWith(".txt") || file.getAbsolutePath().endsWith(".md") ||
		   file.getAbsolutePath().endsWith(".log"))
			return true;
		else 
			throw new Exception();
	}

	public void updateTable(JTable table) {
		try {
			
			
            String contentNewFile = null;
            for (int i = 0; i < table.getRowCount(); i++) {
            	
            	String name = (String) table.getValueAt(i, 1); // pega o nome da pessoa que esta na posicao de i
            	int age = new Integer((String) table.getValueAt(i, 2)); // pega a idade da pessoa que esta na posicao de i
            	double register = new Double((String) table.getValueAt(i, 3)); // pega a matricula da pessoa que esta na posicao de i
            	String date = (String) table.getValueAt(i, 4); // pega a data de criacao da pessoa que esta na posicao de i
            	String personType = (String) table.getValueAt(i, 5); // pega o tipo da pessoa que esta na posicao de i
            		
            	Person person = new Person(personType, age, name, register);
            	person.setCreateAt(date);
            		
            	if (contentNewFile == null)
            		contentNewFile = person.toString();
            	else
            		contentNewFile = contentNewFile + "and" + person.toString();
            }
            
            if(contentNewFile != null) {
            	saveEditedFile(contentNewFile);
            }
			
		} catch (Exception e) {
			new JOptionPane().showMessageDialog(null, "Oops...\nOcorreu um erro na atualização da tabela");
			e.printStackTrace();
		}
	}
}
