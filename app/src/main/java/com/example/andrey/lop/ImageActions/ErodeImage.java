package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.MORPH_RECT;


public class ErodeImage {

    public static Mat getErodeImage(Mat mImg){
        Mat img = new Mat();

        Size kSize = new Size();
        kSize.height = 7;
        kSize.width = 7;
        Point kPoint = new Point();
        kPoint.x = 3;
        kPoint.y = 3;

        Mat kMat = Imgproc.getStructuringElement(MORPH_RECT, kSize, kPoint);
        Imgproc.erode(mImg,img,kMat);
        return img;
    }
}
