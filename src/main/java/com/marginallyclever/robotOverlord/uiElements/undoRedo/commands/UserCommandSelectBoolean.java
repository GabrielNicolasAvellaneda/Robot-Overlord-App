package com.marginallyclever.robotOverlord.uiElements.undoRedo.commands;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;

import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.entity.basicDataTypes.BooleanEntity;
import com.marginallyclever.robotOverlord.uiElements.undoRedo.actions.UndoableActionSelectBoolean;

/**
 * Panel to alter a boolean parameter.  There is currently no way to limit the length of strings.
 * @author Dan Royer
 *
 */
public class UserCommandSelectBoolean extends JPanel implements ItemListener, Observer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox checkboxField;
	private RobotOverlord ro;
	private BooleanEntity e;
	
	public UserCommandSelectBoolean(RobotOverlord ro,BooleanEntity e) {
		super();
		this.ro = ro;
		this.e = e;
				
		checkboxField = new JCheckBox();
		checkboxField.setSelected(e.get());
		checkboxField.addItemListener(this);
		checkboxField.setBorder(new EmptyBorder(5,0,5,5));
		
		JLabel label=new JLabel(e.getName(),SwingConstants.LEFT);
		label.setLabelFor(checkboxField);
		
		this.setLayout(new BorderLayout());
		//this.setBorder(new LineBorder(Color.RED));
		this.add(checkboxField,BorderLayout.LINE_START);
		this.add(label,BorderLayout.CENTER);
	}
	
	/**
	 * the panel element has changed.  poke the entity.
	 */
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		boolean newValue = checkboxField.isSelected();
		if(e.get()!=newValue) {
			ro.undoableEditHappened(new UndoableEditEvent(this,new UndoableActionSelectBoolean(e, e.getName(), newValue) ) );
		}
	}

	/**
	 * entity we are observing has changed.  poke the panel element.
	 */
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof BooleanEntity) {
			checkboxField.setSelected((boolean)arg);
		}
	}
}
