package com.marginallyclever.robotOverlord.dhRobot;

import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.commands.UserCommandSelectBoolean;
import com.marginallyclever.robotOverlord.commands.UserCommandSelectNumber;

/**
 * Subsection of the DHPanel dealing with a single link in the kinematic chain of a DHRobot.
 * @author Dan Royer
 *
 */
public class DHLinkPanel {
	/**
	 * {@value #link} the DHLink referenced by this DHLinkPanel
	 */
	public DHLink link;

	/**
	 * {@value #isRotation} true if this a rotary joint, false if this is a translation joint. 
	 */
	public UserCommandSelectBoolean isRotation;

	/**
	 * {@value #d} the displayed value for link.d
	 */
	public UserCommandSelectNumber d;

	/**
	 * {@value #theta} the displayed value for link.theta
	 */
	public UserCommandSelectNumber theta;

	/**
	 * {@value #r} the displayed value for link.r
	 */
	public UserCommandSelectNumber r;

	/**
	 * {@value #alpha} the displayed value for link.alpha
	 */
	public UserCommandSelectNumber alpha;
	
	public DHLinkPanel(RobotOverlord gui,DHLink link,int k) {
		this.link=link;
		isRotation = new UserCommandSelectBoolean(gui,k+" Rotation?",true);
		d     = new UserCommandSelectNumber(gui,k+" d",(float)link.d);
		theta = new UserCommandSelectNumber(gui,k+" theta",(float)link.theta);
		r     = new UserCommandSelectNumber(gui,k+" r",(float)link.r);
		alpha = new UserCommandSelectNumber(gui,k+" alpha",(float)link.alpha);

		d		.setReadOnly((link.flags & DHLink.READ_ONLY_D		)!=0);
		theta	.setReadOnly((link.flags & DHLink.READ_ONLY_THETA	)!=0);
		r		.setReadOnly((link.flags & DHLink.READ_ONLY_R		)!=0);
		alpha	.setReadOnly((link.flags & DHLink.READ_ONLY_ALPHA	)!=0);
	}
};