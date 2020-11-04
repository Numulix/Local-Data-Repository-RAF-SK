package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import utils.ConstUtils;
import view.ConfigDialog;
import view.MainView;

public class ConfirmButtonController extends AbstractAction{

	public ConfirmButtonController() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		int enNumber;
		String pathName = null;
		
		if (!ConfigDialog.getInstance().getEntityNumberTextField().getText().trim().isEmpty())
			try {
				enNumber = Integer.parseUnsignedInt(ConfigDialog.getInstance().getEntityNumberTextField().getText());
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(null, "Please input a valid number", "Number error", JOptionPane.ERROR_MESSAGE);
				return;
			}
		else enNumber = 5;
		
		pathName = ConfigDialog.getInstance().getFilePathTextField().getText().trim();
		System.out.println(pathName);
		
		ConstUtils.setMaxEnPerFile(enNumber);
		ConstUtils.setPathName(pathName);
		
		MainView.getInstance();
//		MainView.getInstance().getDb().setMaxEnPerFile(enNumber);
//		
//		MainView.getInstance().setPathName(pathName);
		
		ConfigDialog.getInstance().dispose();
	}

}
