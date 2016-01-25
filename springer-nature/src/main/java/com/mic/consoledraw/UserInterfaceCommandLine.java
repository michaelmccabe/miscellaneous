package com.mic.consoledraw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.mic.consoledraw.canvas.Command;
import com.mic.consoledraw.canvas.CommandException;

public class UserInterfaceCommandLine implements UserInterface {

	private final BufferedReader bufferedReader;

	public UserInterfaceCommandLine() {
		super();

		bufferedReader = new BufferedReader(new InputStreamReader(System.in));
	}

	@Override
	public String getCommand() {
		try {
			return bufferedReader.readLine();

		} catch (IOException e) {
			e.printStackTrace();
			return e.getMessage();
		}

	}

	@Override
	public void implement(String command) {

		try {
			output((new Command(command)).outputAsString());

		} catch (CommandException e) {
			output(e.getMessage());
		}

	}

	private void output(String output) {
		System.out.println(output);

	}

}
