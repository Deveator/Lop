package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;

import java.util.ArrayList;
import java.util.List;

public class BlackWithArea {

    public static Mat getBlackWithAreaImg(Mat mImg,int s1,int s2, int wd, int ht){

      //  Point startPoint = new Point(s1,s2);
        // cut inputImage to get rectangle template
        Mat aImg = mImg.submat(new Rect(s1, s2 , wd, ht));

        // get black image
        Mat imgDst = Mat.zeros(mImg.rows(), mImg.cols(), mImg.type());

        // create array with pixel value
        List<Double> getVal = new ArrayList();

        for (int q = 0; q < ht; q++) {

            for (int w = 0; w < wd; w++) {

                double[] ft = aImg.get(q, w);

                getVal.add(ft[0]);
            }
        }

        // set value in specific pixels
        int u = 0;

        for (int g = 0; g < ht; g++) {

            for (int y = 0; y < wd; y++) {

                imgDst.put(s2 + g, s1 + y, getVal.get(u));

                u++;
            }
        }

        return imgDst;
    }





}
