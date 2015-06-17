package com.application;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.FileManager;
import objects.Person;

public class AlterPersonApplication extends JFrame {
	
	private FileManager manager;
	TableApplication tableApplication;
	JTextField nameField, ageField, registerField;
	Person personToEdit;
	JComboBox combo;
	int personId;
	
	public AlterPersonApplication(FileManager manager, TableApplication tableApplication, Person person) {
		this.manager = manager;
		this.tableApplication = tableApplication;
		this.personToEdit = person;
	}
	
	public void start() {
		String[] items = {"Aluno", "Professor", "Diretor", "Funcionario"};
	    combo = new JComboBox(items);
	    nameField = new JTextField(personToEdit.getName());
	    ageField  = new JTextField(String.valueOf(personToEdit.getAge()));
	    registerField = new JTextField(String.valueOf(personToEdit.getRegister()));
	    JPanel panel = new JPanel(new GridLayout(0, 2));
	    panel.add(new JLabel("Tipo:"));
	    panel.add(combo);
	    panel.add(new JLabel("Nome:"));
	    panel.add(nameField);
	    panel.add(new JLabel("Idade:"));
	    panel.add(ageField);
	    panel.add(new JLabel("Matricula:"));
	    panel.add(registerField);
	    
	    // coloca o tipo da pessoa na posicao correta
	    for (int i = 0; i < items.length; i++)
			if (personToEdit.getPersonType().equals(items[i]))
				combo.setSelectedIndex(i);
	    
	   int result = JOptionPane.showConfirmDialog(null, panel, "Editor de Pessoa", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
	   
	    if (result == JOptionPane.OK_OPTION) {
	        Person person = new Person(combo.getSelectedItem().toString(),
	        						   Integer.parseInt(ageField.getText()),
	        						   nameField.getText(),
	        						   new Double(registerField.getText()));
	        person.setCreateAt(personToEdit.getCreateAt()); // mantem a data antiga
	        manager.saveEditedFile(manager.loadFileByPath() + "and" + person.toString());
	        tableApplication.refreshTable();
	    }
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
}
