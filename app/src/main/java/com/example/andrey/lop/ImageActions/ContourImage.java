package com.example.andrey.lop.ImageActions;

import com.example.andrey.lop.CustomView.DrawRect;

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

import static com.example.andrey.lop.CustomView.DrawRect.xOrg;
import static com.example.andrey.lop.CustomView.DrawRect.xRed;
import static com.example.andrey.lop.CustomView.DrawRect.xYell;
import static com.example.andrey.lop.CustomView.DrawRect.yGreen;
import static com.example.andrey.lop.CustomView.DrawRect.yRed;
import static com.example.andrey.lop.CustomView.DrawRect.yYell;
import static com.example.andrey.lop.MainActivity.blueVal;
import static com.example.andrey.lop.MainActivity.greenVal;
import static com.example.andrey.lop.MainActivity.mFullRoiXy;
import static com.example.andrey.lop.MainActivity.redVal;

public class ContourImage {

    public static List<MatOfPoint> contoursM = new ArrayList<>();
    public static ArrayList<int[]> contourCoordinatesX_Y = new ArrayList<>();
    public static ArrayList<int[]> subMatFullCrdntsX_Y = new ArrayList<>();
    private static Random rnd = new Random(12345);
    private static final boolean subMatIsDone = false;

    public static Mat getMainContourFromROI(Mat mImg, Mat mImg2) {

        DrawRect.getCoord();

        Mat sMat = mImg2.submat(yRed, yGreen, xRed, xOrg);

        for (int x = xRed; x < xOrg; x++) {
            for (int y = yRed; y < yGreen; y++) {
                int xy[] = {x, y};
                if (subMatFullCrdntsX_Y.contains(xy)) {
                    subMatFullCrdntsX_Y.add(xy);
                }
            }
        }
        System.out.println(subMatFullCrdntsX_Y.size());


        Mat resultImg = new Mat();

        Imgproc.cvtColor(sMat, resultImg, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Canny(resultImg, resultImg, 100, 100 * 2);

        Mat hierarchy = new Mat();

        Imgproc.findContours(resultImg, contoursM, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);

        System.out.println("Contours " + contoursM.size());
        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);

        for (int i = 0; i < contoursM.size(); i++) {

            Scalar color = new Scalar(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Imgproc.drawContours(drawing, contoursM, i, color, -1, Core.LINE_8, hierarchy, 0, new Point());
            if (i == (contoursM.size() - 1)) {
                blueVal = color.val[0];
                greenVal = color.val[1];
                redVal = color.val[2];
            }
        }

        for (int x = 0; x < drawing.cols(); x++) {
            for (int y = 0; y < drawing.rows(); y++) {
                double[] ft2 = drawing.get(y, x);
                if (ft2[0] == blueVal && ft2[1] == greenVal && ft2[2] == redVal) {
                    int[] ft4 = {y, x};
                    contourCoordinatesX_Y.add(ft4);
                }
            }
        }

        double[] ft3 = {0.0, 0.0, 0.0};
        for (int i = 0; i < contourCoordinatesX_Y.size(); i++) {
            int[] ft5 = contourCoordinatesX_Y.get(i);
            int y = ft5[0];
            int x = ft5[1];
            mImg.put(y + yRed, x + xRed, ft3);
        }

        contourCoordinatesX_Y.clear();
        contoursM.clear();
        return mImg;
    }

    public void checkCoordinatesInArray(int[] vCoor, ArrayList<int[]> aL) {

        int x = vCoor[0];
        int y = vCoor[1];
        int xC;
        int yC;
        for(int i = 0; i<aL.size(); i++){


        }

    }


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
