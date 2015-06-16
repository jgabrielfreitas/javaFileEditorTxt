package com.application;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import feedback.AlertBuilder;
import file.FileManager;

public class FileEditor extends JFrame implements ActionListener {
	
	FileManager fileToEdit;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu fileM,viewM;
	private JScrollPane scpane;
	private JMenuItem menuExit,menuSave,menuLoad,credit,menuNew;
	
	public FileEditor(FileManager file) {
		
		setTitle("Editor da UVA");
		this.fileToEdit = file;
		
	    setSize(600, 500);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container pane = getContentPane();
	    pane.setLayout(new BorderLayout());

	    textArea = new JTextArea(); //textarea
	    menuBar  = new JMenuBar(); //menubar
	    fileM    = new JMenu("Arquivo"); //file menu
	    viewM    = new JMenu("Exibir"); //edit menu
	    scpane 	 = new JScrollPane(textArea); //scrollpane  and add textarea to scrollpane
	    menuExit    = new JMenuItem("Sair");
	    menuSave = new JMenuItem("Salvar"); //menuitems
	    menuLoad = new JMenuItem("Abrir"); //menuitems
	    menuNew  = new JMenuItem("Novo"); //menuitems
	    credit   = new JMenuItem("Créditos"); //menuitems

	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);

	    // barra de menu
	    setJMenuBar(menuBar);
	    menuBar.add(fileM);
	    menuBar.add(viewM);

	    // Arquivo
	    fileM.add(menuNew);
	    fileM.add(menuSave);
	    fileM.add(menuLoad);
	    fileM.add(menuExit);

	    // Exibir
	    viewM.add(credit);

	    menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	    menuLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	    menuNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
	    menuExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));

	    pane.add(scpane,BorderLayout.CENTER);

	    menuSave.addActionListener(this);
	    menuNew.addActionListener(this);
	    menuLoad.addActionListener(this);
	    menuExit.addActionListener(this);
	    credit.addActionListener(this);
	    
	    textArea.setText(file.loadFileByPath());

	    setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	    JMenuItem choice = (JMenuItem) e.getSource();
	    
	    // se o usuario clicar em 'salvar'
	    if (choice == menuSave) {
	        fileToEdit.saveEditedFile(textArea.getText());
	        
	        AlertBuilder alertBuilder = new AlertBuilder("Arquivo salvo com sucesso");
			alertBuilder.build();
	        
	    // se o usuario clicar em 'carregar'
	    } else if (choice == menuLoad) {
	    	
	    	JFileChooser fileChooser = new JFileChooser();
	    	FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt", "txt", "text");
	    	FileNameExtensionFilter logs   = new FileNameExtensionFilter(".log", "log", "text");
	    	FileNameExtensionFilter md     = new FileNameExtensionFilter(".md", "md", "text");
	    	fileChooser.setFileFilter(filter);
	    	fileChooser.setFileFilter(logs);
	    	fileChooser.setFileFilter(md);
			int returnOfFileChooser  = fileChooser.showOpenDialog(null);

			// se o usuario escolheu algum arquivo
			if (returnOfFileChooser == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				FileManager manager = new FileManager(file.getAbsolutePath());
				
				try {
					if( manager.validateFile() == true){
						this.fileToEdit = manager;
						textArea.setText(fileToEdit.loadFileByPath());
					}
				} catch (Exception ex) {
					
					// escolheu um arquivo que não é .txt
					AlertBuilder alertBuilder = new AlertBuilder("Por favor, escolha um arquivo .txt");
					alertBuilder.build();
				}
				
			} else { 
				
				// se o usuario não escolheu nenhum arquivo
				// ou seja, fechou a janela ou apertou em 'cancelar'
				AlertBuilder alertBuilder = new AlertBuilder("Sem arquivo para exibir");
				alertBuilder.build();
			}
			
	    } else if (choice == credit) {
	    	
	    	AlertBuilder alertBuilder = new AlertBuilder("Trabalho criado no Eclipse por:\n• João Gabriel\n• Isabella Lucero\n");
			alertBuilder.build();
	    	
	    } else if (choice == menuNew) {
	    	
	    	String dateAsString = "_" + new SimpleDateFormat("HH_mm_ss").format(new GregorianCalendar().getTime()) + ".txt";
	    	String newFileName = JOptionPane.showInputDialog("Digite o nome do arquivo") + dateAsString;
	    	
	    	if(newFileName.equals(" ") == false) {
	    		try {
	    			
	    			FileManager newManager = new FileManager(Main.APPLICATION_FILE_PATH + File.separator + newFileName);
					newManager.getFile().createNewFile();
					new FileEditor(newManager);
					dispose();// fecha a janela atual
	    		} catch (IOException e1) {
	    			
	    			AlertBuilder alertBuilder = new AlertBuilder("Desculpe, não foi possível criar seu arquivo.");
					alertBuilder.build();
	    			e1.printStackTrace();
	    		} 
	    	} else {
	    		AlertBuilder alertBuilder = new AlertBuilder("O nome do arquivo não pode ser nulo");
				alertBuilder.build();
	    	}
	    	
	    	
	    // if user clicked in 'sair'
	    } else if (choice == menuExit) {
	        System.exit(0);
	        textArea.selectAll();
	    }else if (e.getSource() == credit) {
	        //not yet implmented
	    }
	}

}
