package com.marginallyclever.robotOverlord.swingInterface.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;

import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.entity.basicDataTypes.IntEntity;
import com.marginallyclever.robotOverlord.swingInterface.actions.ActionChangeComboBox;

public class ViewElementComboBox extends ViewElement implements ActionListener, Observer {
	private JComboBox<String> field;
	private IntEntity e;
	
	public ViewElementComboBox(RobotOverlord ro,IntEntity e,String [] listOptions) {
		super(ro);
		this.e=e;
		
		e.addObserver(this);
		
		field = new JComboBox<String>(listOptions);
		field.setSelectedIndex(e.get());
		field.addActionListener(this);
		field.addFocusListener(this);

		JLabel label=new JLabel(e.getName(),JLabel.LEADING);
		label.setLabelFor(field);

		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(0,0,0,1));
		panel.add(label,BorderLayout.LINE_START);
		panel.add(field,BorderLayout.LINE_END);
	}
	
	public String getValue() {
		return field.getItemAt(e.get());
	}

	/**
	 * I have changed.  poke the entity
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int newIndex = field.getSelectedIndex();
		if(newIndex != e.get()) {
			ro.undoableEditHappened(new UndoableEditEvent(this,new ActionChangeComboBox(e, e.getName(), newIndex) ) );
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		field.setSelectedIndex((Integer)arg1);
	}

	@Override
	public void setReadOnly(boolean arg0) {
		field.setEnabled(!arg0);
	}
}
