package com.asymmetrik.utils.hash;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

/**
 * Abstract class for an image hash generator
 */
public abstract class HashGen {

	/**
	 * Generate hash of given image
	 * @param is stream containing image data
	 * @return the generated hash or null if the image could not be read
	 * @throws IOException on error
	 */
	public String getHash(InputStream is) throws IOException {
		return getHash(IOUtils.toByteArray(is));
	}
	
	/**
	 * Generate hash of given base 64 encoded image
	 * @param b64 String containing encoded image data
	 * @return the generated hash or null if the image could not be read
	 * @throws IOException on error
	 */
	public String getHash(String b64) throws IOException {
		return getHash(Base64.getDecoder().decode(b64));
	}

	/**
	 * Generate hash of given base 64 encoded image
	 * @param byteImage byte array containing image data
	 * @return the generated hash or null if the image could not be read
	 * @throws IOException on error
	 */
	public abstract String getHash(byte[] byteImage) throws IOException;
}
