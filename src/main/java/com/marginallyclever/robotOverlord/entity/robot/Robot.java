package com.marginallyclever.robotOverlord.entity.robot;

import java.util.ArrayList;

import javax.swing.JPanel;

import com.marginallyclever.communications.NetworkConnectionManager;
import com.marginallyclever.convenience.AnsiColors;
import com.marginallyclever.communications.NetworkConnection;
import com.marginallyclever.communications.NetworkConnectionListener;
import com.marginallyclever.robotOverlord.RobotOverlord;
import com.marginallyclever.robotOverlord.entity.physicalObject.PhysicalObject;


/**
 * A robot visible with a physical presence in the World.  Assumed to have an NetworkConnection to a machine in real life.  
 * @author Dan Royer
 *
 */
public abstract class Robot extends PhysicalObject implements NetworkConnectionListener {
	// comms	
	protected transient NetworkConnection connection;
	protected transient boolean isReadyToReceive;
	
	protected transient RobotPanel robotPanel=null;
	
	
	public Robot() {
		super();
	}
	
	@Override
	public ArrayList<JPanel> getContextPanel(RobotOverlord gui) {
		ArrayList<JPanel> list = super.getContextPanel(gui);
		if(robotPanel == null) robotPanel = new RobotPanel(gui,this);
		list.add(robotPanel);
		
		return list;
	}
	
	public void closeConnection() {
		connection.closeConnection();
		connection.removeListener(this);
		connection=null;
	}
	
	public void openConnection() {
		NetworkConnection s = NetworkConnectionManager.requestNewConnection(null);
		if(s!=null) {
			connection = s;
			connection.addListener(this);
		}
	}
	
	
	public NetworkConnection getConnection() {
		return this.connection;
	}
	
	
	@Override
	public void dataAvailable(NetworkConnection arg0,String data) {
		if(arg0==connection && connection!=null) {
			if(data.startsWith(">")) {
				isReadyToReceive=true;
			}
		}
		
		System.out.print(AnsiColors.GREEN+data+AnsiColors.RESET);
	}
	
	/**
	 * Processes a single instruction meant for the robot.
	 * @param line command to send
	 * @return true if the command is sent to the robot.
	 */
	public boolean sendCommand(String command) {
		if(connection==null) return false;

		// contains a comment?  if so remove it
		int index=command.indexOf('(');
		if(index!=-1) {
			//String comment=line.substring(index+1,line.lastIndexOf(')'));
			//Log("* "+comment+NL);
			command=command.substring(0,index).trim();
			if(command.length()==0) {
				// entire line was a comment.
				return false;  // still ready to send
			}
		}

		if(!command.endsWith("\n")) {
			command+="\n";
		}
		
		// send relevant part of line to the robot
		try{
			connection.sendMessage(command);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public void lineError(NetworkConnection arg0, int lineNumber) {
		// back up to the line that had an error and send again.
	}

	@Override
	public void sendBufferEmpty(NetworkConnection arg0) {
		// just because the buffer is empty does not mean the robot is ready to receive. 
	}
/*
	// pull the last connected port from prefs
	private void loadRecentPortFromPreferences() {
		recentPort = prefs.get("recent-port", "");
	}

	// update the prefs with the last port connected and refreshes the menus.
	public void setRecentPort(String portName) {
		prefs.put("recent-port", portName);
		recentPort = portName;
		//UpdateMenuBar();
	}
*/

	/**
	 * Each robot implementation should customize the keframe as needed. 
	 * @return an instance derived from RobotKeyframe
	 */
	public abstract RobotKeyframe createKeyframe();
	
	@Override
	public void update(double dt) {
		super.update(dt);
		if(connection!=null) {
			connection.update();
		}
	}
	
	public boolean isReadyToReceive() {
		return isReadyToReceive;
	}
}
