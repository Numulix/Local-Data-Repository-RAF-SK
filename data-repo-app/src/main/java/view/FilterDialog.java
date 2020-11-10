package view;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import api.Entity;

public class FilterDialog extends JDialog {

	private JLabel nameLabel;
    private JLabel attrLabel;
    private JTextField nameTextField;
    private JTextArea attrTextArea;
    private JButton okButton;

    private static FilterDialog instance = null;
    
    private FilterDialog() {
    	nameLabel = new JLabel ("Entity name:");
        attrLabel = new JLabel ("Entity attributes:");
        nameTextField = new JTextField (5);
        attrTextArea = new JTextArea (5, 5);
        okButton = new JButton ("OK");
        
        setMinimumSize(new Dimension(330, 350));
        setLayout(null);
        setLocationRelativeTo(null);
        
        add (nameLabel);
        add (attrLabel);
        add (nameTextField);
        add (attrTextArea);
        add (okButton);
        
        okButton.addActionListener(e -> {
        	
        	if (nameTextField.getText().trim().isEmpty() && attrTextArea.getText().trim().isEmpty()) {
        		JOptionPane.showMessageDialog(null, "Please provide a valid input", "Empty input error", JOptionPane.ERROR_MESSAGE);
				return;
        	}
        	
        	List<Entity> nameList = null;
        	HashMap<String, Object> attrMap = new HashMap<>();
        	
        	if (!nameTextField.getText().trim().isEmpty()) {
        		nameList = MainView.getInstance().getDb().filterByEntityName(nameTextField.getText().trim());
        	}
        	
        	if (!attrTextArea.getText().trim().isEmpty()) {
        		for (String line: attrTextArea.getText().split("\n")) {
        			String[] a = line.split(":");
        			attrMap.put(a[0], a[1]);
        		}
        	}
        	
        	if (nameList == null) {
        		MainView.getInstance().filter(MainView.getInstance()
        				.getDb()
        				.filterByArgs(attrMap));
        	} else {
        		MainView.getInstance().filter(MainView.getInstance()
        				.getDb()
        				.filterByNameAndArgs(
        						nameTextField.getText().trim(), 
        						attrMap));
        	}
        	
        	dispose();
        	
        });
        
        nameLabel.setBounds (5, 40, 100, 25);
        attrLabel.setBounds (5, 85, 100, 25);
        nameTextField.setBounds (130, 45, 180, 25);
        attrTextArea.setBounds (130, 95, 175, 155);
        okButton.setBounds (100, 270, 100, 25);
    }
	
    public static FilterDialog getInstance() {
    	if (instance == null) {
    		instance = new FilterDialog();
    	}
		return instance;
	}
}
