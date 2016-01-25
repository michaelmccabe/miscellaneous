package com.mic.consoledraw.canvas;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CommandTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void lineCorodinatesMustBeVerticalOrHorizontal() throws Exception {

		// verify
		expectedException.expect(CommandException.class);
		expectedException.expectMessage("line coordinates do not create a horizontal or vertical line");
		// setup
		// exercise
		new Command("L 14 12 15 15");
	}

	@Test
	public void lineCorodinatesNotNumbers() throws Exception {

		// verify
		expectedException.expect(CommandException.class);
		expectedException.expectMessage("not a valid command: For input string: \"1S");
		// setup
		// exercise
		new Command("L 14 12 1S 15");
	}

	@Test
	public void newCanvasMustHaveCorrectNumberOfCoordinates() throws Exception {

		// verify
		expectedException.expect(CommandException.class);
		expectedException.expectMessage("wrong number of coordinates");
		// setup
		// exercise
		new Command("C 14 12 15 ");
	}

	@Test
	public void bucketFillParametersMustBeCorrectFormat() throws Exception {

		// verify
		expectedException.expect(CommandException.class);
		expectedException.expectMessage("wrong number of parameters for B command");
		// setup
		// exercise
		new Command("B 14 12 15 22 11");
	}
}
