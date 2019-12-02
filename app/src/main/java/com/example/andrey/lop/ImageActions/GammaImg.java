package com.example.andrey.lop.ImageActions;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import static com.example.andrey.lop.Saturate.saturate;

public class GammaImg {

    public static Mat getGammaImg(Mat mat, double gammaVal){

        Mat lookUpTable = new Mat(1, 256, CvType.CV_8U);
        byte[] lookUpTableData = new byte[(int) (lookUpTable.total()*lookUpTable.channels())];
        for (int i = 0; i < lookUpTable.cols(); i++) {
            lookUpTableData[i] = saturate(Math.pow(i / 255.0, gammaVal) * 255.0);
        }
        lookUpTable.put(0, 0, lookUpTableData);
        Mat img = new Mat();
        Core.LUT(mat, lookUpTable, img);
        return img;
    }
}
