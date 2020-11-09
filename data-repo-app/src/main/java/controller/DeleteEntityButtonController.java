package controller;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import api.Entity;
import view.MainView;

public class DeleteEntityButtonController extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {

		List<Entity> list = MainView.getInstance().getEntityJList().getSelectedValuesList();
		
		for (Entity en: list) {
			MainView.getInstance().getDb().deleteByID(en.getId());
		}
		
		MainView.getInstance().refreshList();
		
	}

}
