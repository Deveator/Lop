package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OriginalImage {

    // method to get image in Mat format resized down 4 times from path
    public static Mat GetOriginalImage(String path, int d,int d2) {

        Mat orImage = new Mat();
        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format
        int rows = originImg.rows();
        int clmns = originImg.cols();
        Size sz = new Size(clmns / d, rows / d2);
        Imgproc.resize(originImg, originImg, sz);
        orImage = originImg;
        Imgproc.cvtColor(orImage, orImage, Imgproc.COLOR_BGR2RGB);
        return orImage;
    }

    // method to get image in Mat format resized down 4 times from path
    public static Mat GetOriginalImage2(String path) {

        Mat orImage = new Mat();
        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format
        int rows = originImg.rows();
        int clmns = originImg.cols();
        System.out.println(rows + " rows");
        System.out.println(clmns + " clmns");

      //  Imgproc.resize(originImg, originImg, sz);
      //  orImage = originImg;
        Imgproc.cvtColor(originImg, orImage, Imgproc.COLOR_BGR2RGB);
        return orImage;
    }
}
