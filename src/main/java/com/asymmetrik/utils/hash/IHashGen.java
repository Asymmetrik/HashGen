package com.asymmetrik.utils.hash;

import java.io.IOException;
import java.io.InputStream;

/**
 * Interface for an image hash generator
 */
public interface IHashGen {

    /**
     * Generate hash of given image
     * @param is stream containing image data
     * @return the generated hash or null if the image could not be read
     * @throws IOException on error
     */
    String getHash(InputStream is) throws IOException;

}
