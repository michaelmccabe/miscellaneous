package com.mic.consoledraw.canvas;

class Line extends Shape {

	public Line(int[] coords) {
		super(coords);
	}

	@Override
	public void draw(Canvas canvas) {

		String[][] pixels = canvas.pixels;
		if (coords[0] == coords[2]) { // draw vertical line
			int length = Math.abs(coords[3] - coords[1]);
			int begin = Math.min(coords[3], coords[1]);

			while (length-- > 0) {
				pixels[coords[0]][begin++] = "X";

			}
		}

		if (coords[1] == coords[3]) { // draw horizontal line
			int length = Math.abs(coords[2] - coords[0]);
			int begin = Math.min(coords[2], coords[0]);

			while (length-- > 0) {
				pixels[begin++][coords[1]] = "X";

			}
		}

	}

}
