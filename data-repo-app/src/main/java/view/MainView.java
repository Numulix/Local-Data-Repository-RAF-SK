package view;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import api.DataRepoSpec;
import api.Entity;
import controller.DeleteEntityButtonController;
import controller.UpdateEntityButtonController;
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

        addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				db.save();
				System.out.println(db.getEntityList());
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
        
        
        add (entityJList);
        add (addEntityButton);
        add (deleteEntityButton);
        add (updateEntityButton);
        add (filterButton);
        add (sortButton);
        add (addEntityAttrButton);
        add (openButton);
        
        addEntityButton.addActionListener(e -> {
        	AddEntityDialog.getInstance().setVisible(true);
        });
        
        deleteEntityButton.addActionListener(new DeleteEntityButtonController());
        updateEntityButton.addActionListener(new UpdateEntityButtonController());
        
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
	
	public JList<Entity> getEntityJList() {
		return entityJList;
	}
}
