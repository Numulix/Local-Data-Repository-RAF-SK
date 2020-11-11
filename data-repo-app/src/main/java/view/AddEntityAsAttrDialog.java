package view;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import api.Entity;
import utils.ConstUtils;

public class AddEntityAsAttrDialog extends JDialog {

	private JLabel label1;
    private JLabel idLabel1;
    private JTextField idTextField1;
    private JButton addExistingButton;
    private JLabel label2;
    private JLabel idLabel2;
    private JTextField idTextField2;
    private JTextField nameTextField;
    private JLabel nameLabel;
    private JTextArea attrTextArea;
    private JButton addNewButton;
    private JLabel attrLabel;
    private JTextField entAttrTextField;
    private JLabel entityAttrLabel;
    private Entity selected;
    
    private static AddEntityAsAttrDialog instance = null;
    
    private AddEntityAsAttrDialog() {
    	label1 = new JLabel ("Add an existing entity as attribute");
        idLabel1 = new JLabel ("Entity ID:");
        idTextField1 = new JTextField (5);
        addExistingButton = new JButton ("Add");
        label2 = new JLabel ("Add a new entity as attribute");
        idLabel2 = new JLabel ("Entity ID:");
        idTextField2 = new JTextField (5);
        nameTextField = new JTextField (5);
        nameLabel = new JLabel ("Entity name:");
        attrTextArea = new JTextArea (5, 5);
        addNewButton = new JButton ("Add");
        attrLabel = new JLabel ("Entity attributes:");
        entAttrTextField = new JTextField (5);
        entityAttrLabel = new JLabel ("Atrribute name:");
        
        setMinimumSize(new Dimension(330, 550));
        setLayout(null);
        setLocationRelativeTo(null);
        
        add (label1);
        add (idLabel1);
        add (idTextField1);
        add (addExistingButton);
        add (label2);
        add (idLabel2);
        add (idTextField2);
        add (nameTextField);
        add (nameLabel);
        add (attrTextArea);
        add (addNewButton);
        add (attrLabel);
        add (entAttrTextField);
        add (entityAttrLabel);
        
        if (ConstUtils.isAutoGenID()) idTextField2.setEnabled(false);
        else idTextField2.setEnabled(true);
        
        addExistingButton.addActionListener(e -> {
        	if (entAttrTextField.getText().trim().isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Please leave a non-empty input", "Input error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	int id = 0;
        	try {
				id = Integer.parseUnsignedInt(idTextField1.getText().trim());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Please input a valid number", "Number error", JOptionPane.ERROR_MESSAGE);
				return;
			}
        	
        	if (id == selected.getId()) {
        		JOptionPane.showMessageDialog(null, "You can't add the same entity as its own attribute", "Entity error", JOptionPane.ERROR_MESSAGE);
        		return;
        	} 
        	
        	Entity en = MainView.getInstance().getDb().findEntityByID(id);
        	
        	if (en == null) {
        		JOptionPane.showMessageDialog(null, "An entity with that ID doesn't exist", "Entity error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	MainView.getInstance().getDb().addEntityAsAttribute(
        			selected.getId(), 
        			entAttrTextField.getText().trim(), 
        			en.getId(), 
        			en.getName(), 
        			en.getAttributes());
        	
        	selected = null;
        	entAttrTextField.setText("");
        	idTextField1.setText("");
        	idTextField2.setText("");
        	attrTextArea.setText("");
        	nameTextField.setText("");
        	
        	MainView.getInstance().refreshList();
        	
        	dispose();
        });
        
        
        addNewButton.addActionListener(l -> {
        	if (entAttrTextField.getText().trim().isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Please leave a non-empty input", "Input error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	int id = 0;
        	String name;
        	HashMap<String, Object> attr = new HashMap<String, Object>();
        	
        	if (!ConstUtils.isAutoGenID()) {
        		try {
					id = Integer.parseUnsignedInt(idTextField2.getText().trim());
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Please input a valid number", "Number error", JOptionPane.ERROR_MESSAGE);
					return;
				}
        	}
        	
        	name = nameTextField.getText().trim();
        	
        	if (name.isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Please provide a name for the entity", "Name error", JOptionPane.ERROR_MESSAGE);
        		return;
        	}
        	
        	String[] attrSplit = attrTextArea.getText().split("\n");
        	
        	for (String a: attrSplit) {
        		if (!a.contains(":")) {
        			JOptionPane.showMessageDialog(null, "Invalid key value pair", "Attribute error", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		String[] line = a.split(":");
        		attr.put(line[0], line[1]);
        	}
        	
        	if (ConstUtils.isAutoGenID()) {
        		MainView.getInstance().getDb().addAutoId(name, attr);
        		Entity en = MainView.getInstance()
        				.getDb()
        				.getEntityList()
        				.get(   MainView.getInstance()
        						.getDb()
        						.getEntityList().size() - 1);
        		MainView.getInstance().getDb().addEntityAsAttribute(
        				selected.getId(), 
        				entAttrTextField.getText().trim(), 
        				en.getId(), 
        				en.getName(), 
        				en.getAttributes());
        		
        		MainView.getInstance().refreshList();
        	} else {
        		if (!MainView.getInstance().getDb().add(id, name, attr)) {
        			JOptionPane.showMessageDialog(null, "An Entity with that ID already exists", "ID error", JOptionPane.ERROR_MESSAGE);
        		} else {
        			MainView.getInstance().getDb().addEntityAsAttribute(selected.getId(), 
        					entAttrTextField.getText().trim(), 
        					id, 
        					name, 
        					attr);
        			MainView.getInstance().refreshList();
        		}
        	}
        	
        	selected = null;
        	entAttrTextField.setText("");
        	idTextField1.setText("");
        	idTextField2.setText("");
        	attrTextArea.setText("");
        	nameTextField.setText("");
        	
        	dispose();
        	
        });
        
        
        label1.setBounds (20, 130, 200, 25);
        idLabel1.setBounds (20, 155, 100, 25);
        idTextField1.setBounds (115, 160, 115, 25);
        addExistingButton.setBounds (80, 195, 100, 25);
        label2.setBounds (15, 230, 230, 25);
        idLabel2.setBounds (15, 275, 100, 25);
        idTextField2.setBounds (125, 275, 115, 25);
        nameTextField.setBounds (125, 305, 115, 25);
        nameLabel.setBounds (15, 305, 100, 25);
        attrTextArea.setBounds (125, 340, 115, 130);
        addNewButton.setBounds (80, 485, 100, 25);
        attrLabel.setBounds (15, 335, 100, 25);
        entAttrTextField.setBounds (20, 85, 220, 30);
        entityAttrLabel.setBounds (20, 45, 100, 25);
    }
    
    public static AddEntityAsAttrDialog getInstance() {
    	if (instance == null) {
    		instance = new AddEntityAsAttrDialog();
    	}
		return instance;
	}
    
    public void setSelected(Entity selected) {
		this.selected = selected;
	}
	
}
