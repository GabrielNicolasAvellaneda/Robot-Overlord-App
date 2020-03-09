package com.marginallyclever.robotOverlord.entity.robot.sixi2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.DefaultListModel;

import com.marginallyclever.robotOverlord.engine.log.Log;

/**
 * A model of a list of commands that can be read by the Sixi2
 * @author Dan Royer
 * @since 1.6.0
 * TODO add unit tests
 */
public class Sixi2Recording extends Observable {
	public String fileFrom;
	public String fileTo;
	
	protected DefaultListModel<String> commandsModel;
	protected ArrayList<String> commands;
	protected int commandIndex;
	
	//commandsModel added to this method
	public Sixi2Recording() {
		commands = new ArrayList<String>();
		commands.add("");
		
		commandsModel = new DefaultListModel<String>();
	}

	//commandsModel added to this method
	public void reset() {
		commandIndex = 0;
		
		commandsModel.firstElement();
	}

	
	public void loadRecording(String fileName) {
		commands.clear();

		try {
			BufferedReader stream = new BufferedReader(new FileReader(new File(fileName)));
			String line;
			while ((line = stream.readLine()) != null) {
				commands.add(line);
			}
			stream.close();
		} catch (IOException e) {
			Log.error("Failed to load file " + fileName);
			commands.clear();
		}
		reset();
		fileFrom=fileName;
		Log.message("loaded " + fileName + " with " + (commands.size()) + " lines.");
	}

	public void saveRecording(String fileName) {
		try {
			PrintWriter stream = new PrintWriter(new FileWriter(new File(fileName)));
			for (String line : commands) {
				stream.println(line);
			}
			stream.flush();
			stream.close();
			Log.message("saved " + fileName + " with " + (commands.size()) + " lines.");
		}
		catch(IOException e) {
			Log.error("Failed to save file "+fileName);
		}
		fileTo=fileName;
	}

	//commandsModel added to this method
	/**
	 * add a command after the current command.
	 * @param line
	 */
	public void addCommand(String line) {
		System.out.println("Add command: "+line);
		if (commands.size() == 0) {
			commands.add(line);
			commandIndex = 0;
			return;
		}
		if (commandIndex >= 0 && commandIndex < commands.size()) {
			commandIndex++;
			commands.add(commandIndex, line);
		}
		//-----------------------------------------------------
		if (commandsModel.size() == 0) {
			commandsModel.addElement(line);
			commandIndex = 0;
			return;
		}
		if (commandIndex >= 0 && commandIndex < commands.size()) {
			commandIndex++;
			commandsModel.add(commandIndex, line);
		}
	}

	//commandsModel added to this method
	// change the current command
	public void setCommand(String line) {
		System.out.println("set command: "+line);
		if (commands.size() == 0) {
			commands.add(line);
			commandIndex = 0;
			return;
		}
		if (commandIndex >= 0 && commandIndex < commands.size()) {
			commands.set(commandIndex, line);
		}
		//-----------------------------------------------------
		if (commandsModel.size() == 0) {
			commandsModel.addElement(line);
			commandIndex = 0;
			return;
		}
		if (commandIndex >= 0 && commandIndex < commands.size()) {
			commandsModel.set(commandIndex, line);
		}
	}
	
	public DefaultListModel<String> getCommandsList(){
		return commandsModel;
	}

	public String getCommand(int index) {
		if (index < 0 || index >= commands.size())
			return null;
		return commands.get(index);
	}

	public String getCommand() {
		return getCommand(commandIndex);
	}

	public boolean hasNext() {
		return commandIndex < commands.size() - 1;
	}

	public boolean hasPrev() {
		return commandIndex > 0;
	}

	public String next() {
		commandIndex++;
		return getCommand();
	}

	public String prev() {
		commandIndex--;
		return getCommand();
	}

	public void deleteCurrentCommand() {
		if (commandIndex >= 0 && commandIndex < commands.size()) {
			commands.remove(commandIndex);
			commandIndex = Math.min(commandIndex, commands.size()-1);
			commandIndex = Math.max(commandIndex, 0);
		}
	}

	public void deletePreviousCommand() {
		if (commands.size() > 0 && commandIndex - 1 > 0) {
			commands.remove(commandIndex - 1);
			commandIndex--;
		}
	}
}