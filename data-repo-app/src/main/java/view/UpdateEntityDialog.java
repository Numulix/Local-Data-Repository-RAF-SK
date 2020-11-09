package view;

import java.awt.Dimension;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UpdateEntityDialog extends JDialog {

	private JLabel idLabel;
    private JTextField idTextField;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel attrLabel;
    private JTextArea attrTextArea;
    private JLabel noteLabel;
    private JLabel exampleLabel;
    private JButton okButton;
    
    private static UpdateEntityDialog instance = null;
    
    private UpdateEntityDialog() {
    	
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
        
        idLabel.setBounds (15, 45, 100, 25);
        idTextField.setBounds (120, 45, 190, 25);
        nameLabel.setBounds (15, 105, 100, 25);
        nameTextField.setBounds (120, 105, 190, 25);
        attrLabel.setBounds (10, 185, 100, 25);
        attrTextArea.setBounds (120, 180, 195, 175);
        noteLabel.setBounds (10, 370, 330, 25);
        exampleLabel.setBounds (10, 395, 255, 25);
        okButton.setBounds (120, 435, 100, 25);
        
        okButton.addActionListener(e -> {
        	dispose();
        });
    	
    }
    
    public static UpdateEntityDialog getInstance() {
    	if (instance == null) {
    		instance = new UpdateEntityDialog();
    	}
		return instance;
	}
    
    public JTextField getIdTextField() {
		return idTextField;
	}
    
    public JTextField getNameTextField() {
		return nameTextField;
	}
    
    public JTextArea getAttrTextArea() {
		return attrTextArea;
	}
	
}
