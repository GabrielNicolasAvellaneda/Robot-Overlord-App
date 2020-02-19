package com.marginallyclever.robotOverlord.entity.physicalObject;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.vecmath.Vector3d;

import com.marginallyclever.convenience.MatrixHelper;
import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.engine.undoRedo.commands.UserCommandSelectVector3d;

/**
 * The user interface for an Entity.
 * @author Dan Royer
 *
 */
public class PhysicalObjectControlPanel extends JPanel implements ChangeListener {
	private static final long serialVersionUID = 1L;
	private PhysicalObject entity;
	private transient UserCommandSelectVector3d setPosition;
	private transient UserCommandSelectVector3d setRotation;

	/**
	 * @param ro the application instance
	 * @param entity The entity controlled by this panel
	 */
	public PhysicalObjectControlPanel(RobotOverlord ro,PhysicalObject entity) {
		super();
		
		this.entity = entity;
		this.setName("Physical Object");
		this.setLayout(new GridBagLayout());
		this.setBorder(new EmptyBorder(0,0,0,0));
		
		GridBagConstraints con1 = new GridBagConstraints();
		con1.gridx=0;
		con1.gridy=0;
		con1.weighty=0;
		con1.fill=GridBagConstraints.HORIZONTAL;
		con1.anchor=GridBagConstraints.FIRST_LINE_START;
		
		this.add(setPosition = new UserCommandSelectVector3d(ro,"position",entity.getPosition()),con1);
		con1.gridy++;
		setPosition.addChangeListener(this);

		Vector3d temp = new Vector3d();
		entity.getRotation(temp);
		temp.scale(180/Math.PI);
		con1.weighty=1;  // last item gets weight 1.
		this.add(setRotation = new UserCommandSelectVector3d(ro,"rotation",temp),con1);
		con1.gridy++;
		setRotation.addChangeListener(this);
	}
	
	
	/**
	 * Call by an Entity when it's details change so that they are reflected on the panel.
	 * This might be better as a listener pattern.
	 */
	public void updateFields() {
		setPosition.setValue(entity.getPosition());
		Vector3d temp = new Vector3d();
		entity.getRotation(temp);
		temp.scale(180.0/Math.PI);
		setRotation.setValue(temp);
	}
	
	
	/**
	 * Called by the UI when the user presses buttons on the panel.
	 * @param e the {@link ChangeEvent} details
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		Object subject = e.getSource();
		
		if( subject==setPosition ) {
			Vector3d pos = entity.getPosition();
			Vector3d newPos = setPosition.getValue();
			if(!newPos.epsilonEquals(pos, 1e-4)) {
				entity.setPosition(newPos);
			}
		}
		if( subject==setRotation ) {
			Vector3d temp = new Vector3d();
			entity.getRotation(temp);
			Vector3d newPos = setRotation.getValue();
			newPos.scale(Math.PI/180.0);
			if(!newPos.epsilonEquals(temp, 1e-4)) {
				entity.setRotation(MatrixHelper.eulerToMatrix(newPos));
			}
		}
	}
}