package com.marginallyclever.robotOverlord.entity.physicalObject.boxObject;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.marginallyclever.convenience.PanelHelper;
import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.engine.translator.Translator;
import com.marginallyclever.robotOverlord.engine.undoRedo.commands.UserCommandSelectNumber;

public class BoxObjectPanel extends JPanel implements ChangeListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BoxObject box;
	RobotOverlord gui;
	
	UserCommandSelectNumber chooseWidth,chooseHeight,chooseDepth;
	
	public BoxObjectPanel(RobotOverlord gui, BoxObject box) {
		super();
		this.gui=gui;
		this.box=box;
		
		this.setName("Box");
		

		this.setLayout(new GridBagLayout());

		GridBagConstraints con1 = PanelHelper.getDefaultGridBagConstraints();
		
		this.add(chooseWidth  = new UserCommandSelectNumber(gui,com.marginallyclever.robotOverlord.engine.translator.Translator.get("Width"),(float)box.getWidth()),con1);
		con1.gridy++;
		this.add(chooseHeight = new UserCommandSelectNumber(gui,Translator.get("Height"),(float)box.getHeight()),con1);
		con1.gridy++;
		con1.weighty=1;
		this.add(chooseDepth  = new UserCommandSelectNumber(gui,Translator.get("Depth" ),(float)box.getDepth() ),con1);
		con1.gridy++;

		chooseWidth .addChangeListener(this);
		chooseHeight.addChangeListener(this);
		chooseDepth .addChangeListener(this);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(e.getSource()==chooseWidth ) box.setWidth(chooseWidth .getValue());
		if(e.getSource()==chooseHeight) box.setHeight(chooseHeight.getValue());
		if(e.getSource()==chooseDepth ) box.setDepth(chooseDepth .getValue());
		
	}
}
