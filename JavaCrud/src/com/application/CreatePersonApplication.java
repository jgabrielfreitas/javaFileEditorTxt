package com.application;

import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javafx.scene.control.Tab;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import file.FileManager;

public class CreatePersonApplication extends JFrame {
	
	private FileManager manager;
	TableApplication tableApplication;
	
	public CreatePersonApplication(FileManager manager) {
		this.manager = manager;
	}

	public void start() {
		String[] items = {"Aluno", "Professor", "Diretor", "Funcionario"};
	    JComboBox combo = new JComboBox(items);
	    JTextField nameField = new JTextField("");
	    JTextField ageField  = new JTextField("");
	    JTextField registerField = new JTextField("");
	    JPanel panel = new JPanel(new GridLayout(0, 2));
	    panel.add(new JLabel("Tipo:"));
	    panel.add(combo);
	    panel.add(new JLabel("Nome:"));
	    panel.add(nameField);
	    panel.add(new JLabel("Idade:"));
	    panel.add(ageField);
	    panel.add(new JLabel("Matricula:"));
	    panel.add(registerField);
	    
	   int result = JOptionPane.showConfirmDialog(null, panel, "Criador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	   
	    if (result == JOptionPane.OK_OPTION) {
	        Person person = new Person(combo.getSelectedItem().toString(),
	        						   Integer.parseInt(ageField.getText()),
	        						   nameField.getText(),
	        						   new Double(registerField.getText()));
	        person.setCreateAt(new SimpleDateFormat("dd/MM/YYYY").format(new GregorianCalendar().getTime()));
	        manager.saveEditedFile(manager.loadFileByPath() + "and" + person.toString());
	        tableApplication.refreshTable();
	    }
	}

	public void setTableApplication(TableApplication tableApplication) {
		this.tableApplication = tableApplication;
	}
	
	
	
}
