package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class GaussianImage {

    public static Mat GetGaussianImage(Mat mImg,int xSize, int ySize, double vSigma){

        // vSigma - more vSigma digit - more blur/smooth
        Mat img = new Mat();
        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);
        // mask size more mask more pixeler
        Size vSize = new Size();
        vSize.height = ySize;
        vSize.width = xSize;
        Imgproc.GaussianBlur(img, img, vSize, vSigma);
        Imgproc.cvtColor(img, img, Imgproc.COLOR_GRAY2BGR);
        return img;
    }
}
