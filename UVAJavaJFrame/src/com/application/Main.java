package com.application;

import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import feedback.AlertBuilder;
import file.FileManager;

public class Main extends JFrame {

	JTextField editText;
	JButton button;
	JLabel text;
	
	private static final String DEFAULT_TEXT = "Texto gerado automáticamente.\nTrabalho feito por:\n• João Gabriel\n• Isabella Lucero";
	public  static final String APPLICATION_FILE_PATH = File.listRoots()[0] + File.separator + "Java_UVA";
	
	public static void main(String[] args) {
		start();
	}

	public static void start() {
		
		File fileToCheck = new File(APPLICATION_FILE_PATH);
		
		// verifica se o diretorio existe
		if(fileToCheck.exists() == false) {
			fileToCheck.mkdir();

			// cria o arquivo default
			FileManager manager = new FileManager(fileToCheck.getAbsolutePath() + File.separator + "textoInicial.txt");
			manager.saveEditedFile(DEFAULT_TEXT);
			
			// abre o arquivo default
			new FileEditor(manager);
			
		} else {
			
			FileManager manager = new FileManager(fileToCheck.getAbsolutePath() + File.separator + "textoInicial.txt");
			
			// verifica se o arquivo existe,
			// a pasta pode existir, mas o arquivo ser excluido
			if(manager.getFile().exists() == false) {
				
				try {
					
					manager.getFile().createNewFile();
					manager.saveEditedFile(DEFAULT_TEXT);
					new FileEditor(manager);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else 
				new FileEditor(manager);
		}
	}
}
