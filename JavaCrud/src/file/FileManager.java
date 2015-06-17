package file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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

}
