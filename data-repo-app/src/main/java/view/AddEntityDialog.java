package view;

import java.awt.Dimension;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import utils.ConstUtils;

@SuppressWarnings("serial")
public class AddEntityDialog extends JDialog {
	
	private JLabel idLabel;
    private JTextField idTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel attrLabel;
    private JTextArea attrTextArea;
    private JLabel noteLabel;
    private JLabel exampleLabel;
    private JButton okButton;
    
    private static AddEntityDialog instance = null;
    
    private AddEntityDialog() {
    	
    	idLabel = new JLabel ("Entity ID:");
        idTextField = new JTextField (5);
        nameLabel = new JLabel ("Entity name:");
        nameTextField = new JTextField (5);
        attrLabel = new JLabel ("Entity attributes:");
        attrTextArea = new JTextArea (5, 5);
        noteLabel = new JLabel ("Note: Attribute key value pairs are seperated by a new line");
        exampleLabel = new JLabel ("A key value pair example -> name:Pera");
        okButton = new JButton ("OK");
        
        setMinimumSize (new Dimension (360, 500));
        setLayout (null);
        setLocationRelativeTo(null);
        
        add (idLabel);
        add (idTextField);
        add (nameLabel);
        add (nameTextField);
        add (attrLabel);
        add (attrTextArea);
        add (noteLabel);
        add (exampleLabel);
        add (okButton);
        
        if (ConstUtils.isAutoGenID()) idTextField.setEnabled(false);
        else idTextField.setEnabled(true);
        
        okButton.addActionListener(e -> {
        	
        	int id = 0;
        	String name;
        	HashMap<String, Object> attr = new HashMap<String, Object>();
        	
        	if (!ConstUtils.isAutoGenID()) {
        		try {
					id = Integer.parseUnsignedInt(idTextField.getText().trim());
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
        		MainView.getInstance().refreshList();
        	} else {
        		if (!MainView.getInstance().getDb().add(id, name, attr)) {
        			JOptionPane.showMessageDialog(null, "An Entity with that ID already exists", "ID error", JOptionPane.ERROR_MESSAGE);
        		} else {
        			MainView.getInstance().refreshList();
        		}
        	}
        	
        	idTextField.setText("");
        	nameTextField.setText("");
        	attrTextArea.setText("");
        	
        	dispose();
        });
        
        idLabel.setBounds (15, 45, 100, 25);
        idTextField.setBounds (120, 45, 190, 25);
        nameLabel.setBounds (15, 105, 100, 25);
        nameTextField.setBounds (120, 105, 190, 25);
        attrLabel.setBounds (10, 185, 100, 25);
        attrTextArea.setBounds (120, 180, 195, 175);
        noteLabel.setBounds (10, 370, 330, 25);
        exampleLabel.setBounds (10, 395, 255, 25);
        okButton.setBounds (120, 435, 100, 25);
    	
    }
    
    public static AddEntityDialog getInstance() {
    	if (instance == null)
    		instance = new AddEntityDialog();
		return instance;
	}
	
}
