package com.asymmetrik.utils.hash;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public abstract class TestHashGen {

    private static final File TEST_IMAGE_GOOD = new File(TestHashGen.class.getClassLoader()
            .getResource("apple.jpg").getFile());
    private static final File TEST_IMAGE_BAD = new File(TestHashGen.class.getClassLoader()
            .getResource("not_an_image.txt").getFile());

    private HashGen hashGen;

    protected abstract HashGen createInstance();

    @Before
    public void setUp() {
        hashGen = createInstance();
    }

    @Test
    public void testValidImage() throws IOException {
        String hash = hashGen.getHash(new FileInputStream(TEST_IMAGE_GOOD));
        assertTrue(hash != null && !hash.isEmpty());
    }

    @Test
    public void testInvalidImage() throws IOException {
        String hash = hashGen.getHash(new FileInputStream(TEST_IMAGE_BAD));
        assertEquals(null, hash);
    }

}