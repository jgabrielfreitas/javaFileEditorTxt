package com.application;

import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
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
	        Person newPerson = new Person(combo.getSelectedItem().toString(),
	        						   Integer.parseInt(ageField.getText()),
	        						   nameField.getText(),
	        						   new Double(registerField.getText()));
	        newPerson.setCreateAt(personToEdit.getCreateAt()); // mantem a data antiga
	        
	        JTable table = tableApplication.getTable();
	        
	        // varre a lista novamente
	        String contentNewFile = null;
	        for (int i = 0; i < table.getRowCount(); i++) {
	        	
            	int currentId = new Integer(table.getValueAt(i, 0).toString());
            	// se for um id diferente
            	if(personId != currentId) {
            		String name = (String) table.getValueAt(i, 1); // pega o nome da pessoa que esta na posicao de i
            		int age = new Integer((String) table.getValueAt(i, 2)); // pega a idade da pessoa que esta na posicao de i
            		double register = new Double((String) table.getValueAt(i, 3)); // pega a matricula da pessoa que esta na posicao de i
            		String date = (String) table.getValueAt(i, 4); // pega a data de criacao da pessoa que esta na posicao de i
            		String personType = (String) table.getValueAt(i, 5); // pega o tipo da pessoa que esta na posicao de i
            		
            		 
            		Person personDifetent = new Person(personType, age, name, register);
            		personDifetent.setCreateAt(date);
            		
            		if (contentNewFile == null)
            			contentNewFile = personDifetent.toString();
            		else
            			contentNewFile = contentNewFile + "and" + personDifetent.toString();
            		
            	} else { // quando for o id selecionado
            		
            		if (contentNewFile == null)
            			contentNewFile = newPerson.toString();
            		else
            			contentNewFile = contentNewFile + "and" + newPerson.toString();
            	}
            }
	        
	        manager.saveEditedFile(contentNewFile);
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
