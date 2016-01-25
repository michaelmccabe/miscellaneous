package com.mic.consoledraw.canvas;

abstract class Shape {

	protected int[] coords;

	public Shape(int[] coords) {
		this.coords = coords;
	}

	public abstract void draw(Canvas canvas);
}
