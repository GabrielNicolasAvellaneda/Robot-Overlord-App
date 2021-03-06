package com.marginallyclever.robotOverlord.swingInterface.commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import com.marginallyclever.robotOverlord.swingInterface.translator.Translator;

/**
 * Display an About dialog box. This action is not an undoable action.
 * @author Admin
 *
 */
public class CommandAboutControls extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CommandAboutControls() {
		super(Translator.get("Controls"));
        putValue(SHORT_DESCRIPTION, Translator.get("About controls"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String body = "<h1>Controls</h1>";
		body+="<h2>Flying</h2>";
		body+="<p>RMB+Mouse: pan + tilt camera</p>";
		body+="<p>WASDQE: fly forward, back, left, right, up, and down.</p>";
		body+="<h2>Selecting</h2>";
		body+="<p>Double click anything in the world to see its menu.</p>";
		body+="<p>Double click again or press escape to unselect.</p>";
		body+="<h2>Robots</h2>";
		body+="<p>Robots can be moved when they are selected.</p>";
		body+="<p>Robots can be moved according to a <i>frame of reference</i>, which can be changed in the robot's panel.</p>";
		body+="<p>LMB+Mouse: drag robot finger tip in two directions.</p>";
		body+="<p>LMB+Mouse+Ctrl/Option: drag the robot finger tip in the third direction.</p>";
		body+="<p>LMB+Mouse+Shift: turn robot finger tip in two directions.</p>";
		body+="<p>LMB+Mouse+Shift+Ctrl/Option: turn the robot finger tip in the third direction.</p>";

		body = "<html><body>"+body+"</body></html>";
		
		JOptionPane.showMessageDialog(null,body);
	}
}
