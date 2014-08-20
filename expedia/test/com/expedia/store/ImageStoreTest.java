package com.expedia.store;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ImageStoreTest {

    ImageStore underTest = new ImageStore();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    byte[][] images = { "imagadedata1".getBytes(), "imagadedata2".getBytes(),
	    "imagadedata3".getBytes(), "imagadedata4".getBytes(),
	    "imagadedata5".getBytes(), "imagadedata6".getBytes()

    };

    @Test
    public void imageNameShouldNeitherBeBlankNorNull() throws Exception {
	// setup
	expectedException.expect(Exception.class);
	expectedException.expectMessage("image name must not be null or empty");

	// exercise and verify
	underTest.storeImage(null, images[0]);

    }

    @Test
    public void imageNameShouldNeitherBeBlank() throws Exception {
	// setup
	expectedException.expect(Exception.class);
	expectedException.expectMessage("image name must not be null or empty");

	// exercise and verify
	underTest.storeImage("", images[0]);
    }

    @Test
    public void imageNameShouldNotBeNull() throws Exception {
	// setup
	expectedException.expect(Exception.class);
	expectedException.expectMessage("image must not be null");

	// exercise and verify
	underTest.storeImage("no image", null);

    }

    @Test
    public void newImageShouldBeStored() throws Exception {

	// exercise
	underTest.storeImage("image1", images[0]);

	byte[] image = underTest.fetchImage("image1");

	// verify
	assertEquals(image, images[0]);
	assertEquals(1, underTest.size());

    }

    @Test
    public void imageWithExistingNameShouldBeOverwritten() throws Exception {

	// setup
	underTest.storeImage("image name", images[0]);

	// before
	byte[] imageBeforeModification = underTest.fetchImage("image name");
	assertEquals(imageBeforeModification, images[0]);

	// exercise
	underTest.storeImage("image name", images[1]);

	// verify
	byte[] imageAfterModification = underTest.fetchImage("image name");

	assertEquals(imageAfterModification, images[1]);

    }

    @Test
    public void existingImageShouldHaveNameUpdated() throws Exception {

	// setup
	underTest.storeImage("old image name", images[0]);

	// before
	byte[] imageBeforeModification = underTest.fetchImage("old image name");
	assertEquals(imageBeforeModification, images[0]);

	// exercise
	underTest.storeImage("new image name", images[0]);

	// verify
	byte[] imageAfterModification = underTest.fetchImage("new image name");

	assertEquals(imageAfterModification, images[0]);

    }

    @Test
    public void readMeTestCase1() throws Exception {
	// 1) Given the following sequence of operations:
	//
	// Insert <"Hotel_A", imageA>
	// Insert <"Hotel a", imageA>
	// Insert <"Hotel B", imageB>
	//
	// The store should have size 2 and contain imageA and imageB.
	byte[] imageA = images[0];
	byte[] imageB = images[1];

	underTest.storeImage("Hotel_A", imageA);
	underTest.storeImage("Hotel a", imageA);
	underTest.storeImage("Hotel B", imageB);

	// verify'

	assertEquals(2, underTest.size());
	assertEquals(imageA, underTest.fetchImage("Hotel a"));
	assertEquals(imageB, underTest.fetchImage("Hotel B"));

    }

    @Test
    public void readMeTestCase2() throws Exception {
	// 2) Given the following sequence of operations:
	//
	// Insert <"Hotel_A", imageA>
	// Insert <"Hotel a", imageA>
	// Insert <"Hotel_A", imageB);
	// Insert <"Hotel a", imageB>
	//
	// The store should have size 1 and only contain imageB.
	byte[] imageA = images[0];
	byte[] imageB = images[1];

	underTest.storeImage("Hotel_A", imageA);
	underTest.storeImage("Hotel a", imageA);
	underTest.storeImage("Hotel_A", imageB);
	underTest.storeImage("Hotel a", imageB);
	// verify'

	assertEquals(1, underTest.size());
	assertEquals(imageB, underTest.fetchImage("Hotel a"));

    }

}
