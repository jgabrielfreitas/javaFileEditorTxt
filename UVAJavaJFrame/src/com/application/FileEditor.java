package com.application;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

import feedback.AlertBuilder;
import file.FileManager;

public class FileEditor extends JFrame implements ActionListener {
	
	FileManager fileToEdit;
	private JTextArea textArea;
	private JMenuBar menuBar;
	private JMenu fileM,editM,viewM;
	private JScrollPane scpane;
	private JMenuItem exitI,menuSave,menuLoad,statusI;
	private JToolBar toolBar;
	
	public FileEditor(FileManager file) {
		
		setTitle("Editor da UVA");
		this.fileToEdit = file;
		
	    setSize(600, 600);
	    setLocationRelativeTo(null);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container pane = getContentPane();
	    pane.setLayout(new BorderLayout());

	    textArea = new JTextArea(); //textarea
	    menuBar = new JMenuBar(); //menubar
	    fileM = new JMenu("Arquivo"); //file menu
	    editM = new JMenu("Editar"); //edit menu
	    viewM = new JMenu("Exibir"); //edit menu
	    scpane = new JScrollPane(textArea); //scrollpane  and add textarea to scrollpane
	    exitI = new JMenuItem("Sair");
	    menuSave = new JMenuItem("Salvar"); //menuitems
	    menuLoad = new JMenuItem("Carregar"); //menuitems
	    statusI = new JMenuItem("Status"); //menuitems
	    toolBar = new JToolBar();

	    textArea.setLineWrap(true);
	    textArea.setWrapStyleWord(true);

	    setJMenuBar(menuBar);
	    menuBar.add(fileM);
	    menuBar.add(editM);
	    menuBar.add(viewM);

	    fileM.add(menuSave);
	    fileM.add(menuLoad);
	    fileM.add(exitI);

	    viewM.add(statusI);

	    menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
	    menuLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));

	    pane.add(scpane,BorderLayout.CENTER);
	    pane.add(toolBar,BorderLayout.SOUTH);

	    menuSave.addActionListener(this);
	    menuLoad.addActionListener(this);
	    exitI.addActionListener(this);
	    statusI.addActionListener(this);
	    
	    textArea.setText(file.loadFileByPath());

	    setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
	    JMenuItem choice = (JMenuItem) e.getSource();
	    
	    // if user clicked in 'salvar'
	    if (choice == menuSave) {
	        fileToEdit.saveEditedFile(textArea.getText());
	        
	    // if user clicked in 'carregar'
	    } else if (choice == menuLoad) {
	    	
	    	JFileChooser fileChooser = new JFileChooser();
			int returnOfFileChooser  = fileChooser.showOpenDialog(null);

			if (returnOfFileChooser == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				FileManager manager = new FileManager(file.getAbsolutePath());
				
				try {
					if( manager.validateFile() == true){
						this.fileToEdit = manager;
						textArea.setText(fileToEdit.loadFileByPath());
					}
				} catch (Exception ex) {
					AlertBuilder alertBuilder = new AlertBuilder("Por favor, escolha um arquivo .txt");
					alertBuilder.build();
				}
				
			} else {
				AlertBuilder alertBuilder = new AlertBuilder("Sem arquivo para exibir");
				alertBuilder.build();
			}
	    // if user clicked in 'sair'
	    } else if (choice == exitI) {
	        System.exit(0);
	        textArea.selectAll();
	    }else if (e.getSource() == statusI) {
	        //not yet implmented
	    }
	}

}
