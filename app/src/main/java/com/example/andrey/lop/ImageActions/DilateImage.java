package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.MORPH_RECT;

public class DilateImage {

    public static Mat getDilateImage(Mat mImg) {

        Mat img = new Mat();
        // Size of the structuring element
        Size kSize = new Size();
        kSize.height = 11;
        kSize.width = 11;
        //Anchor position within the element
        // The default value (−1,−1) means that the anchor is at the center.
        // Note that only the shape of a cross-shaped element depends on the anchor position.
        // In other cases the anchor just regulates how much the result of the morphological operation is shifted
        Point kPoint = new Point();
        kPoint.x = 5;
        kPoint.y = 5;
        // Returns a structuring element of the specified size and shape for morphological operations
        // MORPH_RECT - 	Element shape
        Mat kMat = Imgproc.getStructuringElement(MORPH_RECT, kSize, kPoint);
        Imgproc.dilate(mImg, img, kMat);
        return img;
    }
}
