package com.mic.consoledraw.canvas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

// Description
//
// You're given the task of writing a simple console version of a drawing
// program. At this time, the
//
// functionality of the program is quite limited but this might change in
// the future. In a nutshell, the
//
// program should work as follows:
//
// 1. create a new canvas
//
// 2. start drawing on the canvas by issuing various commands
//
// 3. quit
//
// At the moment, the program should support the following commands:
//
// Command Description
//
// C w h Should create a new canvas of width w and height h.
//
// L x1 y1 x2 y2
//
// Should create a new line from (x1,y1) to (x2,y2). Currently only
// horizontal or vertical lines are supported. Horizontal and vertical lines will be drawn
// using the 'x' character.

// R x1 y1 x2 y2 Should create a new rectangle, whose upper left corner is
// (x1,y1) and lower right
// corner is (x2,y2). Horizontal and vertical lines will be drawn using the
// 'x' character.

// B x y c
//
// Should fill the entire area connected to (x,y) with "colour" c. The
// behaviour of this is
//
// the same as that of the "bucket fill" tool in paint programs.

// Q Should quit the program.
public class CanvasTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testCreateNewCanvasCreatesNewStringArrayAndInsertsBorder() throws Exception {

		// setup
		// exercise
		Canvas newCanvas = Canvas.processCommand(new Command("C 12 12"));
		assertEquals(0, newCanvas.shapes.size());

		Canvas newLineInCanvas = Canvas.processCommand(new Command("L 1 2 6 2"));
		// verify
		assertEquals(1, newLineInCanvas.shapes.size());

		// it's a singleton so it's the same object!
		assertSame(newCanvas, newLineInCanvas);

		Canvas newCanvasAgain = Canvas.processCommand(new Command("C 12 12"));
		assertEquals(0, newCanvasAgain.shapes.size());
	}

	@Test
	public void rectangleCoordinatesMustBeWithinTheCanvas() throws Exception {
		// verify
		expectedException.expect(CommandException.class);
		expectedException.expectMessage("coordinates are outside canvas");
		// setup
		// exercise
		Canvas.processCommand(new Command("C 12 12"));
		Canvas.processCommand(new Command("R 1 12 14 12"));

	}

	@Test
	public void bucketFillCoordinatesMustBeWithinTheCanvas() throws Exception {

		// verify
		expectedException.expect(CommandException.class);
		expectedException.expectMessage("coordinates are outside canvas");
		// setup
		// exercise
		Canvas.processCommand(new Command("C 12 12"));
		Canvas.processCommand(new Command("B 14 12 B"));
	}

	@Test
	public void rectangleCommandAddsNewShape() throws Exception {

		// setup
		// exercise
		Canvas newCanvas = Canvas.processCommand(new Command("C 12 12"));
		assertEquals(0, newCanvas.shapes.size());

		Canvas.processCommand(new Command("R 1 2 6 2"));
		assertEquals(1, newCanvas.shapes.size());

		if (!(newCanvas.shapes.get(0) instanceof Rectangle)) {
			fail("Should have had a rectangle added");
		}

	}

}
