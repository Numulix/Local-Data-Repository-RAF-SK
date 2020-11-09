package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import view.AddEntityDialog;

public class AddEntityButtonController extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		AddEntityDialog.getInstance().setVisible(true);
	}

}
