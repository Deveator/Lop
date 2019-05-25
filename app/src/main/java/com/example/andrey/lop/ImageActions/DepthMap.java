package com.example.andrey.lop.ImageActions;

import org.opencv.calib3d.StereoBM;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.DisparityFilter;

public class DepthMap {

    public static Mat getDepthMap(Mat mImg,Mat mImg2){

        Mat img = new Mat();
        StereoBM se = StereoBM.create(16,15);







        return img;
    }
}
