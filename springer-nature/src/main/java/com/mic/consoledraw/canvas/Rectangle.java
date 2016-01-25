package com.mic.consoledraw.canvas;

class Rectangle extends Shape {

	public Rectangle(int[] coords) {
		super(coords);
	}

	@Override
	public void draw(Canvas canvas) {

		String[][] pixels = canvas.pixels;

		int width = Math.abs(coords[2] - coords[0]);
		int length = Math.abs(coords[3] - coords[1]);
		int left = Math.min(coords[0], coords[2]);
		int top = Math.min(coords[3], coords[1]);

		for (int i = left; i <= left + width; i++) {
			pixels[i][top] = "X";
			pixels[i][length + top] = "X";
		}

		for (int i = top; i <= top + length; i++) {
			pixels[left][i] = "X";
			pixels[width + left][i] = "X";
		}

	}

}
