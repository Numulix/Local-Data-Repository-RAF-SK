package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import view.ConfigDialog;


public class BrowseButtonController extends AbstractAction {
	
	
	public BrowseButtonController() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			ConfigDialog.getInstance().getFilePathTextField().setText(chooser.getSelectedFile().getAbsolutePath());
		}
	}

}
