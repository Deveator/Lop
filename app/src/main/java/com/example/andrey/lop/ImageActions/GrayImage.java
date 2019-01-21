package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class GrayImage {

    public static Mat GetGrayImage(Mat mImg) {

        Mat img = new Mat();
        // convert to gray color
        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);
        return img;
    }
}
