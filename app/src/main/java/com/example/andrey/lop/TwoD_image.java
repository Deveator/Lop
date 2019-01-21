package com.example.andrey.lop;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TwoD_image {

    // method to image after 2Dfilter
    public static Mat GetTwoD_Image(Mat mImg) {

        Mat img = new Mat();
        Mat kern = new Mat(3, 3, CvType.CV_8S);
        int row = 0, col = 0;
        kern.put(row, col, 0, -1, 0, -1, 5, -1, 0, -1, 0);// '0, -1, 0, -1, 5, -1, 0, -1, 0' is dataSet to get 2D image
        Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.filter2D(mImg, img, mImg.depth(), kern);
        //  filter2DImage = img;
        return img;
    }

    // method to image after 2Dfilter
    public static Mat GetTwoD_Image_2(Mat mImg) {

        Mat img = new Mat();
      //  Mat kernel = new Mat();
        Mat ones = Mat.ones(3, 3, CvType.CV_32F) ;
        Mat kernel = new Mat(3, 3, CvType.CV_32F);
        int row = 0, col = 0;
        kernel.put(row, col, 0, -1, 0, -1, 5, -1, 0, -1, 0);
        //Core.multiply(ones, new Scalar(1 / (double) (3 * 3)), kernel);
        Point anchor = new Point(-1, -1);
        double delta = 0.0;
        Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.filter2D(mImg, img, mImg.depth(), kernel, anchor, delta, Core.BORDER_DEFAULT);

        //  filter2DImage = img;
        return img;
    }
}
