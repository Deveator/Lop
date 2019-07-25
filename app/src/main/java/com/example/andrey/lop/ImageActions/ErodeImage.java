package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.MORPH_RECT;


public class ErodeImage {

    public static Mat getErodeImage(Mat mImg, int h, int w, int xK, int yK){

        Mat img = new Mat();

        Size kSize = new Size();
        kSize.height = h;
        kSize.width = w;
        Point kPoint = new Point();
        kPoint.x = xK;
        kPoint.y = yK;

        Mat kMat = Imgproc.getStructuringElement(MORPH_RECT, kSize, kPoint);
        Imgproc.erode(mImg,img,kMat);
        return img;
    }
}
