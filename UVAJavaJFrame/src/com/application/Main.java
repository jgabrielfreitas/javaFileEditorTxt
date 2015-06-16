package com.application;

import java.io.File;

import javax.swing.*;

import feedback.AlertBuilder;
import file.FileManager;

public class Main extends JFrame {

	JTextField editText;
	JButton button;
	JLabel text;
	
	public static void main(String[] args) {
		start();
	}

	public static void start() {

		JFileChooser fileChooser = new JFileChooser();
		int returnOfFileChooser  = fileChooser.showOpenDialog(null);

		if (returnOfFileChooser == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			FileManager manager = new FileManager(file.getAbsolutePath());
			
			try {
				if( manager.validateFile() == true){
					FileEditor editor = new FileEditor(manager);
				}
			} catch (Exception e) {
				AlertBuilder alertBuilder = new AlertBuilder("Por favor, escolha um arquivo .txt");
				alertBuilder.build();
				start();
			}
			
		} else {
			AlertBuilder alertBuilder = new AlertBuilder("Sem arquivo para exibir");
			alertBuilder.build();
		}

	}

}
