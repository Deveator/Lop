package com.example.andrey.lop;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class TwoD_image {

    // method to image after 2Dfilter
    public static Mat GetTwoD_Image(Mat mImg){

        Mat img = new Mat();
        Mat kern = new Mat(3, 3, CvType.CV_8S);
        int row = 0, col = 0;
        kern.put(row, col, 0, -1, 0, -1, 5, -1, 0, -1, 0);// '0, -1, 0, -1, 5, -1, 0, -1, 0' is dataSet to get 2D image
        Imgproc.filter2D(mImg, img, mImg.depth(), kern);
      //  filter2DImage = img;
        return img;
    }
}
