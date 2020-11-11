package view;

import java.awt.Dimension;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JRadioButton;

public class SortByDialog extends JDialog {

	private JRadioButton idRadioButton;
    private JRadioButton nameRadioButton;
    private JButton okButton;
    private ButtonGroup btnGrp;
    
    private static SortByDialog instance = null;
    
    private SortByDialog( ) {
    	idRadioButton = new JRadioButton ("Sort by ID");
        nameRadioButton = new JRadioButton ("Sort by name");
        okButton = new JButton ("OK");
        btnGrp = new ButtonGroup();
        
        btnGrp.add(idRadioButton);
        btnGrp.add(nameRadioButton);
        
        idRadioButton.setSelected(true);

        setMinimumSize (new Dimension (300, 160));
        setLayout (null);
        setLocationRelativeTo(null);
        
        add (idRadioButton);
        add (nameRadioButton);
        add (okButton);
        
        idRadioButton.setBounds (20, 25, 100, 25);
        nameRadioButton.setBounds (165, 25, 100, 25);
        okButton.setBounds (85, 70, 100, 25);
        
        okButton.addActionListener(e -> {
        	if (idRadioButton.isSelected())
        		MainView.getInstance().getDb().sortEntityByID();
        	else {
        		MainView.getInstance().getDb().sortEntityByName();
        	}
        	MainView.getInstance().refreshList();
        	dispose();
        });
    }
	
    public static SortByDialog getInstance() {
    	if (instance == null) {
    		instance = new SortByDialog();
    	}
		return instance;
	}
    
}
