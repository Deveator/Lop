package com.example.andrey.lop.ImageActions;

import com.example.andrey.lop.CustomView.DrawRect;
import com.example.andrey.lop.MainActivity;

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

import static com.example.andrey.lop.CustomView.DrawRect.xGreen;
import static com.example.andrey.lop.CustomView.DrawRect.xOrg;
import static com.example.andrey.lop.CustomView.DrawRect.xRed;
import static com.example.andrey.lop.CustomView.DrawRect.xYell;
import static com.example.andrey.lop.CustomView.DrawRect.yGreen;
import static com.example.andrey.lop.CustomView.DrawRect.yOrg;
import static com.example.andrey.lop.CustomView.DrawRect.yRed;
import static com.example.andrey.lop.CustomView.DrawRect.yYell;
import static com.example.andrey.lop.CustomView.MyImageView.xR2;
import static com.example.andrey.lop.CustomView.MyImageView.xRR;
import static com.example.andrey.lop.CustomView.MyImageView.yR2;
import static com.example.andrey.lop.MainActivity.blueVal;
import static com.example.andrey.lop.MainActivity.greenVal;
import static com.example.andrey.lop.MainActivity.mFullRoiXy;
import static com.example.andrey.lop.MainActivity.redVal;

public class ContourImage {

    public static List<MatOfPoint> contoursM = new ArrayList<>();
    public static ArrayList<int[]> contourCoordinatesX_Y = new ArrayList<>();
    public static ArrayList<ArrayList> contourCoordinatesX_Y_All = new ArrayList<>();
    public static ArrayList<int[]> subMatFullCrdntsX_Y = new ArrayList<>();
    private static Random rnd = new Random(12345);
    private static final boolean subMatIsDone = false;
    private static boolean noContours = false;

    public static ArrayList<Integer> r_array = new ArrayList<>();
    public static ArrayList<Integer> g_array = new ArrayList<>();
    public static ArrayList<Integer> b_array = new ArrayList<>();

    public static ArrayList<String> rgb_string_array = new ArrayList<>();
    public static ArrayList<ArrayList> color_X_Y = new ArrayList<>();

    public static ArrayList<Double> blueVal_full = new ArrayList<>();
    public static ArrayList<Double> greenVal_full = new ArrayList<>();
    public static ArrayList<Double> redVal_full = new ArrayList<>();

    public static ArrayList<double[]> colorsFromClusteredROI = new ArrayList<>();


    public static void getLabValues(Mat mImg) {

        DrawRect.getCoord();

        Mat sMat = mImg.submat(yRed, yGreen, xRed, xOrg);

        //   for (int x = 0; x < sMat.cols(); x++) {
        //      //      for (int y = 0; y < sMat.rows(); y++) {
        //                double[] ft2 = sMat.get(50, 50);
        //
        //        System.out.println("--------");
        //        System.out.println(sMat.cols());
        //        System.out.println(sMat.rows());
        //        System.out.println("--------");
        //
        //                System.out.println(ft2[0]);
        //        System.out.println(ft2[1]);
        //        System.out.println(ft2[2]);
        //             //   if (ft2[0] == blueVal && ft2[1] == greenVal && ft2[2] == redVal) {
        //          //          int[] ft4 = {y, x};
        //        //            contourCoordinatesX_Y.add(ft4);
        //           //     }
        //         //   }


    }

    public static Mat getColorLines(Mat inputImage) {

        DrawRect.getCoord();


        Mat sMat = inputImage.submat(yRed, yGreen, xRed, xOrg);

        MainActivity._yFromROI = yRed;
        MainActivity._xFromROI = xRed;

   //     Mat sMat = inputImage.submat(388, 438, 468, 518);

        getNumOfColors(sMat);

        return sMat;

    }

    public static Mat getMatFromROI(Mat inputImage) {

        DrawRect.getCoord();
        Mat sMat = inputImage.submat(yRed, yGreen, xRed, xOrg);

        Imgproc.cvtColor(sMat, sMat, Imgproc.COLOR_BGR2Lab);


        return sMat;

    }

    public static void getNumOfColors(Mat iImage) {

        double[] colValue = iImage.get(0, 0);

        int r = (int) colValue[0];
        int g = (int) colValue[1];
        int b = (int) colValue[2];

        String rgb_String = r + ";" + g + ";" + b;

        rgb_string_array.add(rgb_String);

        for (int x = 0; x < iImage.cols(); x++) {

            for (int y = 0; y < iImage.rows(); y++) {

                colValue = iImage.get(y, x);
                int r2 = (int) colValue[0];
                int g2 = (int) colValue[1];
                int b2 = (int) colValue[2];

                String v = r2 + ";" + g2 + ";" + b2;

                if (!rgb_string_array.contains(v)) {
                    rgb_string_array.add(v);
                }
            }
        }
        System.out.println("Number of colors - " + rgb_string_array.size());
        for (int j = 0; j < rgb_string_array.size(); j++) {
            System.out.println("colors - " + rgb_string_array.get(j));
        }

      ///  findLines(iImage, rgb_string_array);

    }

    public static void findLines(Mat iImage, ArrayList<String> rgbValues) {


        int checker=0;
        double[] colValue;

        for (int i = 0; i < rgbValues.size(); i++) {
            ArrayList<String> aL = new ArrayList<>();
            color_X_Y.add(aL);
        }
        System.out.println("Number of arrays for colors - " + color_X_Y.size());


        for (int x = 0; x < iImage.cols(); x++) {

            for (int y = 0; y < iImage.rows(); y++) {

                colValue = iImage.get(y, x);
                int r3 = (int) colValue[0];
                int g3 = (int) colValue[1];
                int b3 = (int) colValue[2];

                String v = r3 + ";" + g3 + ";" + b3;

                for (int i = 0; i < rgbValues.size(); i++) {

                    if (v.equals(rgbValues.get(i))) {


                        if (y == 0) {
                            checker = i;
                        }

                        if(checker != i){
                            System.out.println("border");
                            checker = i;
                        }
                        String valXY = x + ";" + y;

                        color_X_Y.get(i).add(valXY);

                    }
                }
            }
        }


    }


    public static Mat getContourLines(Mat inputImage) {

        DrawRect.getCoord();
        Mat sMat = inputImage.submat(yRed, yGreen, xRed, xOrg);
        Mat resultImg = new Mat();
        Imgproc.cvtColor(sMat, resultImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(resultImg, resultImg, 100, 100 * 2);
        Mat hierarchy = new Mat();
        Imgproc.findContours(resultImg, contoursM, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);
        System.out.println("Contours " + contoursM.size());

        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);

        for (int i = 0; i < contoursM.size(); i++) {

            Scalar color = new Scalar(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Imgproc.drawContours(drawing, contoursM, i, color, 3, Core.LINE_8, hierarchy, 0, new Point());

            blueVal_full.add(color.val[0]);
            greenVal_full.add(color.val[1]);
            redVal_full.add(color.val[2]);
        }

        if (contoursM.size() != 0) {

            for (int i = 0; i < blueVal_full.size(); i++) {

                for (int x = 0; x < drawing.cols(); x++) {

                    for (int y = 0; y < drawing.rows(); y++) {

                        double[] ft2 = drawing.get(y, x);

                        if (ft2[0] == blueVal_full.get(i) && ft2[1] == greenVal_full.get(i) && ft2[2] == redVal_full.get(i)) {

                            int[] ft4 = {y + yRed, x + xRed};

                            contourCoordinatesX_Y.add(ft4);

                        }
                    }
                }
                contourCoordinatesX_Y_All.add((ArrayList) contourCoordinatesX_Y.clone());
                contourCoordinatesX_Y.clear();
            }

            // double[] ft3 = {0.0, 0.0, 0.0};
            // for (int i = 0; i < contourCoordinatesX_Y.size(); i++) {
            //     int[] ft5 = contourCoordinatesX_Y.get(i);
            //      int y = ft5[0];
            //       int x = ft5[1];
            //   mImg.put(y + yRed, x + xRed, ft3);
            //    }
        }

        System.out.println("Contours coordinates " + contourCoordinatesX_Y_All.size());


        return sMat;
    }

    public static Mat getExtraContourFromROI(Mat mImg, Mat mImg2) {

        DrawRect.getCoord();

        GammaImg.getGammaImg(mImg2, 0.5);

        Mat sMat = mImg2.submat(yRed, yGreen, xRed, xOrg);

        Mat resultImg = new Mat();

        Imgproc.cvtColor(sMat, resultImg, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Canny(resultImg, resultImg, 100, 100 * 2);

        Mat hierarchy = new Mat();

        Imgproc.findContours(resultImg, contoursM, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE);

        System.out.println("Contours " + contoursM.size());

        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);

        for (int i = 0; i < contoursM.size(); i++) {

            Scalar color = new Scalar(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            Imgproc.drawContours(drawing, contoursM, i, color, 3, Core.LINE_8, hierarchy, 0, new Point());

            if ((contoursM.size() != 0) && (i == (contoursM.size() - 1))) {
                blueVal = color.val[0];
                greenVal = color.val[1];
                redVal = color.val[2];
            }
        }

        if (contoursM.size() != 0) {
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
        }

        if (contoursM.size() == 0) {
            noContours = true;
        }

        contourCoordinatesX_Y.clear();
        contoursM.clear();
        System.out.println("Stage extraContour comleted ");
        return mImg;
    }

    public static Mat _2getMainContourFromROI(Mat mImg, Mat mImg2) {

        DrawRect.getCoord();

        if (noContours) {

        }
        Mat sMat = mImg2.submat(yRed, yGreen, xRed, xOrg);

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

            blueVal = color.val[0];
            greenVal = color.val[1];
            redVal = color.val[2];

            for (int x = 0; x < drawing.cols(); x++) {
                for (int y = 0; y < drawing.rows(); y++) {
                    double[] ft2 = drawing.get(y, x);
                    if (ft2[0] == blueVal && ft2[1] == greenVal && ft2[2] == redVal) {
                        int[] ft4 = {y, x};
                        contourCoordinatesX_Y.add(ft4);
                    }
                }
            }
            double[] ft3 = {blueVal, greenVal, redVal};

            for (int i1 = 0; i1 < contourCoordinatesX_Y.size(); i1++) {
                int[] ft5 = contourCoordinatesX_Y.get(i1);
                int y = ft5[0];
                int x = ft5[1];
                mImg.put(y + yRed, x + xRed, ft3);
            }

            System.out.println("Contour length - " + contourCoordinatesX_Y.size());

            contourCoordinatesX_Y.clear();

            // code for finding value of the most long contour
           /*
            if ((contoursM.size()!=0) && (i == (contoursM.size() - 1))) {
                blueVal = color.val[0];
                greenVal = color.val[1];
                redVal = color.val[2];
            }
            */
        }
/*
        if (contoursM.size() != 0) {
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
        }
        */

        if (contoursM.size() == 0) {
            noContours = true;
        }

        contourCoordinatesX_Y.clear();
        contoursM.clear();
        System.out.println("Stage contour completed");
        return mImg;
    }

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
        for (int i = 0; i < aL.size(); i++) {

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
