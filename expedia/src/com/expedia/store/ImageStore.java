package com.expedia.store;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.expedia.store.types.Image;

public class ImageStore {

    private final Map<String, Image> imageMap = Collections
	    .synchronizedMap(new HashMap<String, Image>());

    public void storeImage(String imageName, byte[] imageData) throws Exception {

	if (null == imageName || imageName.trim().length() == 0)
	    throw new Exception("image name must not be null or empty");

	if (null == imageData)
	    throw new Exception("image must not be null");

	Image newImage = new Image(imageData);

	synchronized (imageMap) {

	    if (imageMap.containsKey(imageName))
		imageMap.remove(imageName);

	    removeImage(newImage);
	    imageMap.put(imageName, newImage);
	}

    }

    private void removeImage(Image newImage) {
	Iterator iterator = imageMap.entrySet().iterator();
	while (iterator.hasNext()) {
	    Map.Entry pairs = (Map.Entry) iterator.next();

	    String name = (String) pairs.getKey();
	    Image image = (Image) pairs.getValue();

	    if (newImage.equals(image))
		iterator.remove();

	}
    }

    public byte[] fetchImage(String imageName) {
	return imageMap.get(imageName).getData();
    }

    public int size() {
	return imageMap.size();
    }

}
