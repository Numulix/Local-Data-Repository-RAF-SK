package controller;

import java.awt.event.ActionEvent;
import java.util.Map;

import javax.swing.AbstractAction;

import api.Entity;
import view.MainView;
import view.UpdateEntityDialog;

public class UpdateEntityButtonController extends AbstractAction {

	@Override
	public void actionPerformed(ActionEvent e) {

		if (MainView.getInstance().getEntityJList().getSelectedValue() != null) {
			
			Entity en = MainView.getInstance().getEntityJList().getSelectedValue();
			
			UpdateEntityDialog.getInstance().getIdTextField().setText(String.valueOf(en.getId()));
			UpdateEntityDialog.getInstance().getNameTextField().setText(en.getName());
			
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> attr: en.getAttributes().entrySet()) {
				sb.append(attr.getKey() + ":" + attr.getValue() + "\n");
			}
			sb.deleteCharAt(sb.length() - 1);
			UpdateEntityDialog.getInstance().getAttrTextArea().setText(sb.toString());
			UpdateEntityDialog.getInstance().setVisible(true);
			
		}
		
	}

}
