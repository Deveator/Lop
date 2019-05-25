package com.example.andrey.lop.ImageActions;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.andrey.lop.MainActivity.blueVal;
import static com.example.andrey.lop.MainActivity.greenVal;
import static com.example.andrey.lop.MainActivity.redVal;

public class ContourImage {

    public static List<MatOfPoint> contoursM = new ArrayList<>();
    private static Random rnd = new Random(12345);


    public static Mat getMainContourImage(Mat mImg) {

        Mat resultImg = new Mat();

        Imgproc.cvtColor(mImg, resultImg, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Canny(resultImg, resultImg, 100, 100 * 2);

        Mat hierarchy = new Mat();

        Imgproc.findContours(resultImg, contoursM, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);

        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);

        for (int i = 0; i < contoursM.size(); i++) {

            Scalar color = new Scalar(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Imgproc.drawContours(drawing, contoursM, i, color, -1, Core.LINE_8, hierarchy, 0, new Point());
            if (i == (contoursM.size() - 1)) {
                blueVal = color.val[0];
                greenVal = color.val[1];
                redVal = color.val[2];
                System.out.println("#" + i);
                System.out.println(blueVal);
                System.out.println(greenVal);
                System.out.println(redVal);
            }
        }
        return drawing;
    }
}
