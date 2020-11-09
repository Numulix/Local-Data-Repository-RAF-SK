package view;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import api.DataRepoSpec;
import api.Entity;
import controller.AddEntityButtonController;
import customImpl.DataRepoCustomImpl;
import utils.ConstUtils;

public class MainView extends JFrame {
	
	private DataRepoSpec db;
	private JList<Entity> entityJList;
    private JButton addEntityButton;
    private JButton deleteEntityButton;
    private JButton updateEntityButton;
    private JButton filterButton;
    private JButton sortButton;
    private JButton addEntityAttrButton;
    private JButton openButton;
    private DefaultListModel<Entity> entityListModel;
    private String pathName = null;
	
	private static MainView instance = null;
	
	private MainView () {

		db = new DataRepoCustomImpl();
		db.setPathName(ConstUtils.getPathName());
		db.setMaxEnPerFile(ConstUtils.getMaxEnPerFile());
		db.setAutoGenID(ConstUtils.isAutoGenID());
		
		
		entityListModel = new DefaultListModel<>();
		if (db.getPathName() != null) {
			db.load();
			for (Entity e: db.getEntityList()) {
				entityListModel.addElement(e);
			}
		}
		
		entityJList = new JList<Entity> (entityListModel);
		entityJList.setLayoutOrientation(JList.VERTICAL);
        addEntityButton = new JButton ("Add");
        deleteEntityButton = new JButton ("Delete");
        updateEntityButton = new JButton ("Update");
        filterButton = new JButton ("Filter");
        sortButton = new JButton ("Sort");
        addEntityAttrButton = new JButton ("Add Entity as attribute");
        openButton = new JButton ("Open an existing database");
        
        setMinimumSize (new Dimension (550, 480));
        setLayout (null);
        setTitle("Data Repo Application");
        setLocationRelativeTo(null);
        
        
        
        add (entityJList);
        add (addEntityButton);
        add (deleteEntityButton);
        add (updateEntityButton);
        add (filterButton);
        add (sortButton);
        add (addEntityAttrButton);
        add (openButton);
        
        addEntityButton.addActionListener(new AddEntityButtonController());
        
        entityJList.setBounds (35, 25, 470, 225);
        addEntityButton.setBounds (35, 275, 100, 25);
        deleteEntityButton.setBounds (160, 275, 100, 25);
        updateEntityButton.setBounds (285, 275, 100, 25);
        filterButton.setBounds (35, 355, 100, 25);
        sortButton.setBounds (160, 355, 100, 25);
        addEntityAttrButton.setBounds (35, 315, 175, 25);
        openButton.setBounds (35, 400, 195, 25);
        
        setVisible(true);
	}
	
	public static MainView getInstance() {
		if (instance == null) {
			instance = new MainView();
		}
		return instance;
	}
	
	public DataRepoSpec getDb() {
		return db;
	}
	
	public String getPathName() {
		return pathName;
	}
	
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	
	public DefaultListModel<Entity> getEntityListModel() {
		return entityListModel;
	}
	
	public void refreshList() {
		entityListModel.removeAllElements();
		for (Entity e: db.getEntityList()) {
			entityListModel.addElement(e);
		}
	}
}
