package com.mic.consoledraw;

import java.io.IOException;

public class ConsoleDraw {

	public static void main(String[] args) throws IOException {

		UserInterface userInterface = new UserInterfaceCommandLine();

		while (true) {
			userInterface.implement(userInterface.getCommand());
		}
	}

}
