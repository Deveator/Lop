package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class MorfOperations {

    public static Mat getMorfGradientImg(Mat mat, int kW, int kH, int pX, int pY){

        Mat matImgDst = new Mat();
        Mat element = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(kW, kH),
                new Point(pX, pY));
        Imgproc.morphologyEx(mat, matImgDst, Imgproc.MORPH_GRADIENT, element);
        return matImgDst;
    }

    public static Mat getMorfOpenImg(Mat mat, int kW, int kH, int pX, int pY){

        Mat matImgDst = new Mat();
        Mat element = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(kW, kH),
                new Point(pX, pY));
        Imgproc.morphologyEx(mat, matImgDst, Imgproc.MORPH_OPEN, element);
        return matImgDst;
    }
}
