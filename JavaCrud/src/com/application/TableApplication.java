package com.application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import objects.FileManager;
import objects.Person;

import java.util.ArrayList;
import java.util.List;;

public class TableApplication extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	// instancia dos atributos
	private JTable table;
	private JScrollPane scrollPane;
	List<Person> personListToShow = new ArrayList<Person>();
	private JMenu menuFile, menuAbout;
	public static JMenuItem menuItemNewPerson, menuSave, menuRemove, menuAlter, menuCredit;
	JMenuBar menuBar;
	private static FileManager manager;

	// construtor
	public TableApplication(List<Person> personList) {
		setPersonList(personList);
		// Set the frame characteristics
		setTitle("Simple Table Application");
		setSize(550, 400);
		setBackground(Color.gray);
		Container pane = getContentPane();
	    pane.setLayout(new BorderLayout());
	    
	    // exibir a janela no centro da tela
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		
		// creating toolbar
		menuBar  = new JMenuBar();
		menuFile = new JMenu("Arquivo"); 		   // opcao 'arquivo'
		menuAbout = new JMenu("Sobre"); 		   // opcao 'sobre'
		menuItemNewPerson = new JMenuItem("Novo"); // nova pessoa
		menuSave = new JMenuItem("Salvar");        // salvar tabela
		menuRemove = new JMenuItem("Remover");     // remover da tabela
		menuAlter = new JMenuItem("Alterar");      // alterar da tabela
		menuCredit = new JMenuItem("Credito");      // alterar da tabela
		
		// adicionando na opcao 'arquivo'
		menuFile.add(menuItemNewPerson);
		menuFile.add(menuRemove);
		menuFile.add(menuAlter);
		menuFile.add(menuSave);

		// adicionando na opcao 'credito'
		menuAbout.add(menuCredit);
		
		// adicionando os menus
		menuBar.add(menuFile);
		menuBar.add(menuAbout);
		
		// criando atalhos
		menuItemNewPerson.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuRemove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menuSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		menuAlter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
	    menuItemNewPerson.addActionListener(this);
	    menuAlter.addActionListener(this);
	    menuRemove.addActionListener(this);
	    menuSave.addActionListener(this);
	    menuCredit.addActionListener(this);

		// criando as colunas da tabela
		String columnNames[] = { "ID","Nome", "Idade", "Matricula", "Data", "Tipo" };
		int columnSize = columnNames.length;
		
		// populando as colunas da tabela
		Object personListAsStringArray[][] = new Object[personListToShow.size()][columnSize];
		for (int i = 0; i < this.personListToShow.size(); i++) {
			Object stringList[] = new Object[columnSize];
			stringList[0] = i + 1;
			stringList[1] = personListToShow.get(i).getName();
			stringList[2] = String.valueOf(personListToShow.get(i).getAge());
			stringList[3] = String.valueOf(personListToShow.get(i).getRegister());
			stringList[4] = personListToShow.get(i).getCreateAt();
			stringList[5] = personListToShow.get(i).getPersonType();
			personListAsStringArray[i] = stringList;
		}
		

		// criando a instancia da tabela
		table = new JTable(personListAsStringArray, columnNames);
		setTitle("Pessoas da Faculdade");

		// adicionando a barra de menu e tabela
		scrollPane = new JScrollPane(table);
		pane.add(menuBar, BorderLayout.NORTH);
		pane.add(scrollPane, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	// distribui os objetos 'Person' da lista recebida
	// para a lista da classe
	public void setPersonList(List<Person> personList){
		for (Person person : personList)
			this.personListToShow.add(person);
	}

	// metodo de acoes para cada click
	public void actionPerformed(ActionEvent e) {
		
		JMenuItem choice = (JMenuItem) e.getSource();
		
		// nova pessoa
		if (choice == menuItemNewPerson) {
			
			CreatePersonApplication creator = new CreatePersonApplication(getManager());
			creator.setTableApplication(this);
			creator.start();
			// salvar
		} else if (choice == menuSave) {
			
			manager.updateTable(table);
			refreshTable();
			
			// alterar
		} else if (choice == menuAlter) {
			
			String idSelected = JOptionPane.showInputDialog("Digite o id da pessoa que deseja alterar");
			Person person = getPersonById(idSelected);
			if(person != null) {
				
				AlterPersonApplication alterPersonApplication = new AlterPersonApplication(manager, this, person);
				alterPersonApplication.setPersonId(new Integer(idSelected));
				alterPersonApplication.start();
			}
			
			// remover
		} else if (choice == menuRemove) {
			
			String idPersonToDelete = JOptionPane.showInputDialog("Digite o id da pessoa que deseja deletar");
			if (idPersonToDelete.equals("")) {
				new JOptionPane().showMessageDialog(null, "Digite um ID valido");
				return;
			} else 
				updateFile(idPersonToDelete);
			
		} else if (choice == menuCredit) {

			new JOptionPane().showMessageDialog(null, "Trabalho realizado por:\n\n• Isabella Lucero\n•Joao Gabriel\n");
		}
	}
	
	private Person getPersonById(String idSelected){
		
		Person person = null;
		
		try {
			
			int idToDelete = Integer.parseInt(idSelected);
			
			if (idToDelete > table.getRowCount()) 
				throw new Exception();
			
			// varre a tabela a procura da pessoa
            String contentNewFile = null;
            for (int i = 0; i < table.getRowCount(); i++) {
            	int currentId = new Integer(table.getValueAt(i, 0).toString());
            	if(idToDelete == currentId) {
            		String name = (String) table.getValueAt(i, 1); // pega o nome da pessoa que esta na posicao de i
            		int age = new Integer((String) table.getValueAt(i, 2)); // pega a idade da pessoa que esta na posicao de i
            		double register = new Double((String) table.getValueAt(i, 3)); // pega a matricula da pessoa que esta na posicao de i
            		String date = (String) table.getValueAt(i, 4); // pega a data de criacao da pessoa que esta na posicao de i
            		String personType = (String) table.getValueAt(i, 5); // pega o tipo da pessoa que esta na posicao de i
            		
            		person = new Person(personType, age, name, register);
            		person.setCreateAt(date);
            		
            		if (contentNewFile == null)
            			contentNewFile = person.toString();
            		else
            			contentNewFile = contentNewFile + "and" + person.toString();
            	}
            }
			
		} catch (Exception e) {
			new JOptionPane().showMessageDialog(null, "Digite um ID valido");
			e.printStackTrace();
		}
		
		return person;
		
	}
	
	public void refreshTable(){
		TableApplication application = new TableApplication(MainApplication.loadPersonsByFile(manager));
		application.setManager(manager);
		dispose();
	}

	public FileManager getManager() {
		return manager;
	}

	public void setManager(FileManager manager) {
		this.manager = manager;
	}

	public JTable getTable(){
		return table;
	}
	
	private void updateFile(String id) {
		
		try {
			
			int idToDelete = Integer.parseInt(id);
			
			if (idToDelete > table.getRowCount()) 
				throw new Exception();
			
			
            String contentNewFile = null;
            for (int i = 0; i < table.getRowCount(); i++) {
            	int currentId = new Integer(table.getValueAt(i, 0).toString());
            	if(idToDelete != currentId) {
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
            }
            
            if(contentNewFile != null) {
            	manager.saveEditedFile(contentNewFile);
            	refreshTable();
            }
			
		} catch (Exception e) {
			new JOptionPane().showMessageDialog(null, "Digite um ID valido");
			e.printStackTrace();
		}
	}
}
