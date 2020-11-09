package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import api.Entity;
import view.MainView;

public class DeleteEntityButtonController extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {

		for (Entity en: MainView.getInstance().getEntityJList().getSelectedValuesList()) {
			MainView.getInstance().getDb().deleteByID(en.getId());
		}
		
		MainView.getInstance().refreshList();
		
	}

}
