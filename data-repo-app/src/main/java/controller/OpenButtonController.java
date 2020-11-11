package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import view.MainView;

public class OpenButtonController extends AbstractAction {
	
	public OpenButtonController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			MainView.getInstance().getDb().setPathName(chooser.getSelectedFile().getAbsolutePath());
			MainView.getInstance().getDb().getEntityList().clear();
			MainView.getInstance().getDb().load();
			MainView.getInstance().refreshList();
		}
	}

}
