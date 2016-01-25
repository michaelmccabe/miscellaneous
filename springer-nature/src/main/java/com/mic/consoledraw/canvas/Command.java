package com.mic.consoledraw.canvas;

public class Command {

	public static enum TYPE {
		COMMANDTYPE_CANVAS, COMMANDTYPE_LINE, COMMANDTYPE_RECTANGLE, COMMANDTYPE_BUCKETFILL
	};

	private TYPE type;
	private int[] coordinates;
	private String colour;

	public Command(String command) throws CommandException {
		decipher(command.replaceAll("\\s+", " ").trim()); // strip out extra spaces
	}

	public String outputAsString() throws CommandException {
		return Canvas.processCommand(this).toString();
	}

	private void decipher(String command) throws CommandException {

		if (command == null || command.length() < 1) {
			throw new CommandException("not a valid command");
		}

		String[] parts = command.split(" ");

		if (command.substring(0, 1).equalsIgnoreCase("C")) {
			type = TYPE.COMMANDTYPE_CANVAS;
			coordinates = new int[2];
			if (parts.length != 3) {
				throw new CommandException("wrong number of coordinates");
			}

			try {
				coordinates[0] = Integer.parseInt(parts[1]);
				coordinates[1] = Integer.parseInt(parts[2]);

			} catch (NumberFormatException e) {
				throw new CommandException("not a valid command: " + e.getMessage());
			}
		} else if (command.substring(0, 1).equalsIgnoreCase("L")) {
			type = TYPE.COMMANDTYPE_LINE;

			getFourCoordinates(parts);
			if (coordinates[0] != coordinates[2] && coordinates[1] != coordinates[3]) {
				throw new CommandException("line coordinates do not create a horizontal or vertical line");
			}
		} else if (command.substring(0, 1).equalsIgnoreCase("R")) {
			type = TYPE.COMMANDTYPE_RECTANGLE;
			getFourCoordinates(parts);
		} else if (command.substring(0, 1).equalsIgnoreCase("B")) {
			type = TYPE.COMMANDTYPE_BUCKETFILL;
			coordinates = new int[2];
			if (parts.length != 4) {
				throw new CommandException("wrong number of parameters for B command");
			}

			try {
				coordinates[0] = Integer.parseInt(parts[1]);
				coordinates[1] = Integer.parseInt(parts[2]);

			} catch (NumberFormatException e) {
				throw new CommandException("not a valid command: " + e.getMessage());
			}

			colour = parts[3];
		} else if (command.substring(0, 1).equalsIgnoreCase("Q")) {
			System.exit(0);

		} else {
			throw new CommandException("not a valid command ");

		}

	}

	private void getFourCoordinates(String[] part) throws CommandException {
		coordinates = new int[4];
		if (part.length != 5) {
			throw new CommandException("wrong number of coordinates");
		}

		try {
			coordinates[0] = Integer.parseInt(part[1]);
			coordinates[1] = Integer.parseInt(part[2]);
			coordinates[2] = Integer.parseInt(part[3]);
			coordinates[3] = Integer.parseInt(part[4]);

		} catch (NumberFormatException e) {
			throw new CommandException("not a valid command: " + e.getMessage());
		}
	}

	int[] getCoordinates() {
		return coordinates;
	}

	TYPE getType() {
		return type;
	}

	String getColour() {
		return colour;
	}

}
