package com.mic.consoledraw.canvas;

import java.util.ArrayList;
import java.util.List;

class Canvas {

	String[][] pixels;
	List<Shape> shapes = new ArrayList<>();

	// make canvas a singleton to ensure there only ever is one canvas
	private static Canvas instance = new Canvas();

	private Canvas() {
	}

	static Canvas processCommand(Command command) throws CommandException {

		if (command.getType().equals(Command.TYPE.COMMANDTYPE_CANVAS)) {
			instance.shapes.clear();
			instance.pixels = new String[1 + command.getCoordinates()[0]][1 + command.getCoordinates()[1]];
			instance.drawBorder();
			return instance;

		}
		int[] coords = command.getCoordinates();

		if (command.getType().equals(Command.TYPE.COMMANDTYPE_LINE) || command.getType().equals(Command.TYPE.COMMANDTYPE_RECTANGLE)) {
			if (coords[0] < 1 || coords[0] > instance.pixels.length - 2 || coords[2] < 1 || coords[2] > instance.pixels.length - 2
					|| coords[1] < 1 || coords[1] > instance.pixels[0].length - 2 || coords[3] < 1
					|| coords[3] > instance.pixels[0].length - 2) {
				throw new CommandException("coordinates are outside canvas");
			}
		}

		if (command.getType().equals(Command.TYPE.COMMANDTYPE_LINE)) {
			instance.addShape(new Line(coords));
		}
		if (command.getType().equals(Command.TYPE.COMMANDTYPE_RECTANGLE)) {
			instance.addShape(new Rectangle(coords));
		}
		if (command.getType().equals(Command.TYPE.COMMANDTYPE_BUCKETFILL)) {

			if (coords[0] < 1 || coords[0] > instance.pixels.length - 2 || coords[1] < 1 || coords[1] > instance.pixels[0].length - 2) {
				throw new CommandException("coordinates are outside canvas");
			}

			instance.bucketFill(coords, command.getColour());
		}
		return instance.drawCanvas();
	}

	private void bucketFill(int[] coords, String colour) {

		int[] coordinates = new int[2];
		instance.pixels[coords[0]][coords[1]] = colour;

		for (int x = (coords[0] - 1); x <= (coords[0] + 1); x++) {
			for (int y = (coords[1] - 1); y <= (coords[1] + 1); y++) {

				if (x > 0 && x < instance.pixels.length - 1 && y > 0 && y < instance.pixels[x].length - 1
						&& instance.pixels[x][y] == null) {
					coordinates[0] = x;
					coordinates[1] = y;
					bucketFill(coordinates, colour);
					instance.pixels[x][y] = colour;
				}
			}
		}
	}

	private Canvas drawCanvas() {
		for (Shape shape : instance.shapes) {
			shape.draw(instance);
		}
		return instance;
	}

	private Canvas addShape(Shape shape) {
		instance.shapes.add(shape);

		return instance;
	}

	private void drawBorder() {

		for (int i = 1; i < instance.pixels[0].length; i++) {
			instance.pixels[0][i] = "|";
			instance.pixels[instance.pixels.length - 1][i] = "|";
		}

		for (int i = 0; i < instance.pixels.length; i++) {
			instance.pixels[i][0] = "-";
			instance.pixels[i][instance.pixels[0].length - 1] = "-";
		}

	}

	@Override
	public String toString() {
		String canvas = "";

		for (int j = 0; j < instance.pixels[0].length; j++) {
			String newline = "";

			for (int i = 0; i < instance.pixels.length; i++) {
				if (instance.pixels[i][j] == null)
					newline += " ";
				else
					newline += instance.pixels[i][j];
			}
			newline += "\n";
			canvas += newline;

		}

		return canvas;
	}

}
