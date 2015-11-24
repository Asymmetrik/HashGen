package com.asymmetrik.utils.hash;

import net.logstash.logback.encoder.org.apache.commons.io.IOUtils;
import org.bytedeco.javacpp.indexer.Indexer;
import org.bytedeco.javacpp.opencv_core;

import java.io.IOException;
import java.io.InputStream;

import static org.bytedeco.javacpp.opencv_core.mean;
import static org.bytedeco.javacpp.opencv_imgcodecs.CV_LOAD_IMAGE_UNCHANGED;
import static org.bytedeco.javacpp.opencv_imgcodecs.imdecode;
import static org.bytedeco.javacpp.opencv_imgproc.COLOR_BGR2GRAY;
import static org.bytedeco.javacpp.opencv_imgproc.cvtColor;
import static org.bytedeco.javacpp.opencv_imgproc.resize;

/**
 * Generates a 64-bit fuzzy image hash by first computing an image's average pixel value, and then setting a bit
 * for each pixel according to whether that pixel >= average.
 */
public class AverageHash implements IHashGen {

    /**
     * Square dimensions to shrink image into
     */
    private static final int SHRINK_SIZE = 24;

    @Override
    public String getHash(InputStream input) throws IOException {
        // Load original image
        opencv_core.Mat image = imdecode(new opencv_core.Mat(IOUtils.toByteArray(input)), CV_LOAD_IMAGE_UNCHANGED);

        // openCV returns an empty matrix if the image could not be read (e.g. unsupported or invalid format)
        if (image.size().area() == 0) {
            return null;
        }

        // Convert to grayscale and resize
        opencv_core.Mat grayImage;
        if (image.channels() != 1) {
            grayImage = new opencv_core.Mat();
            cvtColor(image, grayImage, COLOR_BGR2GRAY);
        } else {
            // Already single channel
            grayImage = image;
        }
		
        opencv_core.Mat smallImage;
        if (image.rows() != SHRINK_SIZE || image.cols() != SHRINK_SIZE) {
            smallImage = new opencv_core.Mat();
            resize(grayImage, smallImage, new opencv_core.Size(SHRINK_SIZE, SHRINK_SIZE));
        } else {
            smallImage = grayImage;
        }

        // Get mean pixel value
        double pixelMean = mean(smallImage).get(0);

        // Compute and return hash
        StringBuilder hash = new StringBuilder();
        Indexer idx = smallImage.createIndexer();
        for (int row = 0; row < smallImage.rows(); row++) {
            for (int col = 0; col < smallImage.cols(); col++) {
                int pixel = new Double(idx.getDouble(row, col)).intValue();
                hash.append((pixel > pixelMean) ? 1 : 0);
            }
        }
        return hash.toString();
    }
}
