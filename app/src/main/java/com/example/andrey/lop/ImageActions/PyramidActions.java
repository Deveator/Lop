package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class PyramidActions {

    public static Mat getPyrUpImg(Mat mImg) {
        Mat img = new Mat();
        Imgproc.pyrUp(mImg, img, new Size(img.cols() * 2, img.rows() * 2));
        return img;

    }

    public static Mat getPyrDownImg(Mat mImg) {
        Mat img = new Mat();
        Imgproc.pyrDown(mImg, img, new Size(img.cols() / 2, img.rows() / 2));
        return img;

    }


}
