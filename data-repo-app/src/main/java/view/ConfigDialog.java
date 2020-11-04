package view;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.BrowseButtonController;
import controller.ConfirmButtonController;

@SuppressWarnings("serial")
public class ConfigDialog extends JDialog {
	
	private JRadioButton yesRadioButton;
    private JRadioButton noRadioButton;
    private JLabel label1;
    private JLabel label2;
    private JTextField entityNumberTextField;
    private JButton confirmButton;
    private JLabel label3;
    private JButton browseButton;
    private JTextField filePathTextField;
    private ButtonGroup btnGrp;
    
    private static ConfigDialog instance = null;
    
    public static ConfigDialog getInstance() {
    	if (instance == null) {
    		instance = new ConfigDialog();
    	}
		return instance;
	}
    
    private ConfigDialog() {
    	yesRadioButton = new JRadioButton ("Da");
        noRadioButton = new JRadioButton ("Ne");
        label1 = new JLabel ("Automatski generisi ID?");
        label2 = new JLabel ("Max Entiteta po fajlu (Default je 5)");
        entityNumberTextField = new JTextField (5);
        confirmButton = new JButton ("Potvrdi");
        label3 = new JLabel ("Pocni iz postojace baze");
        browseButton = new JButton ("Browse");
        filePathTextField = new JTextField (5);
        btnGrp = new ButtonGroup();
        
        btnGrp.add(yesRadioButton);
        btnGrp.add(noRadioButton);
        
        yesRadioButton.setSelected(true);
        noRadioButton.setSelected(false);
        
        filePathTextField.setEnabled (false);
        
        setMinimumSize(new Dimension (430, 285));
        setLayout (null);
        setLocationRelativeTo(null);
        setTitle("Config");

        add (yesRadioButton);
        add (noRadioButton);
        add (label1);
        add (label2);
        add (entityNumberTextField);
        add (confirmButton);
        add (label3);
        add (browseButton);
        add (filePathTextField);
        
        yesRadioButton.setBounds (190, 20, 100, 25);
        noRadioButton.setBounds (290, 20, 100, 25);
        label1.setBounds (15, 20, 140, 20);
        label2.setBounds (10, 65, 200, 25);
        entityNumberTextField.setBounds (215, 65, 185, 25);
        confirmButton.setBounds (150, 205, 100, 25);
        label3.setBounds (50, 115, 140, 25);
        browseButton.setBounds (230, 115, 100, 25);
        filePathTextField.setBounds (15, 160, 385, 25);
        
        browseButton.addActionListener(new BrowseButtonController());
        confirmButton.addActionListener(new ConfirmButtonController());
        
        setVisible(true);
    	
	}
    
    public boolean yesSelected() {
    	return yesRadioButton.isSelected();
    }
    
    public JTextField getEntityNumberTextField() {
		return entityNumberTextField;
	}
    
    public JTextField getFilePathTextField() {
		return filePathTextField;
	}

}
