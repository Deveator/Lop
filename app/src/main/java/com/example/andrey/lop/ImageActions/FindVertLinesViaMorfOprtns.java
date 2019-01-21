package com.example.andrey.lop.ImageActions;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class FindVertLinesViaMorfOprtns {

    public static Mat findVertLinesViaMorfOprtns(Mat mImg) {

        Mat img = new Mat();
        // convert to gray color
        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);
        // Apply adaptiveThreshold at the bitwise_not of gray
        Mat bw = new Mat();
        //Inverts every bit of an array.
        // The function cv::bitwise_not calculates per-element bit-wise inversion of the input array:
        Core.bitwise_not(img, img);
        // Applies an adaptive threshold to an array
        //The function transforms a grayscale image to a binary image
        // maxValue - Non-zero value assigned to the pixels for which the condition is satisfied
        // Imgproc.ADAPTIVE_THRESH_MEAN_C - the threshold value T(x,y) is a mean of the blockSize×blockSize(here 15x150 neighborhood of (x,y) minus C(here c= -2)
        // Imgproc.THRESH_BINARY - type of the threshold operation
        Imgproc.adaptiveThreshold(img, bw, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, -2);
        // Create the images that will use to extract the vertical lines
        Mat vertical = bw.clone();
        // Specify size on vertical axis
        int vertical_size = vertical.rows() / 10;
        // Create structure element for extracting horizontal lines through morphology operations
        // Imgproc.getStructuringElement - Returns a structuring element of the specified size and shape for morphological operations
        Mat verticalStructure = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(1,vertical_size));
        // Apply morphology operations
        Imgproc.erode(vertical, vertical, verticalStructure);
        Imgproc.dilate(vertical, vertical, verticalStructure);

        return vertical;
    }
}
