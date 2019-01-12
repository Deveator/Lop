package com.example.andrey.lop;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class CannyImage {

    // method to image in gray color with Canny edges
    public static Mat GetCannyImage (Mat mImg,int min_threshold, int max_threshold){

        Mat img = new Mat();
        // convert to gray color
        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);
        // convert to "Canny" image
        // min_threshould & max_threshold set threshold to find better edges or filter found edges via threshold with help of pixels values
        // if pixel value more then max_threshold  - pixel is marked as strong
        // if pixel value lower then max_threshold &  more then min_threshold - pixel is marked as weak
        // if pixel value lower then min_threshold  - pixel is suppressed
        Imgproc.Canny(img, img, min_threshold, max_threshold);
        return img;
    }
}
