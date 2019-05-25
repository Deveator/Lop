package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class LabImg {

    public static ArrayList<Integer> xCor = new ArrayList<Integer>();
    public static ArrayList<Integer> yCor = new ArrayList<Integer>();
    public static ArrayList<Integer> allAValues = new ArrayList<Integer>();
    public static ArrayList<Integer> allBValues = new ArrayList<Integer>();




    public static void calculateMaxMinValues(Mat img) {

        ArrayList<Integer> intensity = new ArrayList<Integer>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();

        int maxIntensity, minintensity;
        int maxA, minA;
        int maxB, minB;
        Mat labImg = new Mat();
        Imgproc.cvtColor(img, labImg, Imgproc.COLOR_BGR2Lab);

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val = labImg.get(800 + c, i);

                int vHue = (int) val[0];
                int vSat = (int) val[1];
                int vValue = (int) val[2];
                intensity.add(vHue);
                a.add(vSat);
               // b.add(vValue);
                a.add(vValue);
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val2 = labImg.get(800 + c, 550 + i);

                int vHue2 = (int) val2[0];
                int vSat2 = (int) val2[1];
                int vValue2 = (int) val2[2];
                intensity.add(vHue2);
                a.add(vSat2);
                //b.add(vValue2);
                a.add(vValue2);
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val3 = labImg.get(c, 550 + i);

                int vHue3 = (int) val3[0];
                int vSat3 = (int) val3[1];
                int vValue3 = (int) val3[2];
                intensity.add(vHue3);
                a.add(vSat3);
              //  b.add(vValue3);
                a.add(vValue3);
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val4 = labImg.get(c, i);

                int vHue4 = (int) val4[0];
                int vSat4 = (int) val4[1];
                int vValue4 = (int) val4[2];
                intensity.add(vHue4);
                a.add(vSat4);
               // b.add(vValue4);
                a.add(vValue4);
            }
        }


        maxIntensity = intensity.get(0);
        minintensity = intensity.get(0);
        maxA = a.get(0);
        minA = a.get(0);
      //  maxB = b.get(0);
      //  minB = b.get(0);
/*
        for (int y : intensity) {

            if (y >= maxIntensity) {
                maxIntensity = y;
            }
            if (y <= minintensity) {
                minintensity = y;
            }
        }
        //maxHue = maxHue + 16;
        //minHue = minHue - 16;
        System.out.println(maxIntensity + " | maxIntensity ");
        System.out.println(minintensity + " || minintensity");
*/
        for (int y : a) {

            if (y >= maxA) {
                maxA = y;
            }
            if (y <= minA) {
                minA = y;
            }
        }
        //maxHue = maxHue + 16;
        //minHue = minHue - 16;
        System.out.println(maxA + " | maxA ");
        System.out.println(minA + " || minA");
/*
        for (int y : b) {

            if (y >= maxB) {
                maxB = y;
            }
            if (y <= minB) {
                minB = y;
            }
        }
        //maxHue = maxHue + 16;
        //minHue = minHue - 16;
        System.out.println(maxB + " | maxB ");
        System.out.println(minB + " || minB");
        */
    }


    public static void calculate(Mat mat) {

        for (int i = 123; i < 130; i++) {
            allAValues.add(i);
            allBValues.add(i);
        }
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2Lab);

        for (int i = 0; i < mat.cols(); i++) {
            for (int y = 0; y < mat.rows(); y++) {
                double[] joy = mat.get(y, i);
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if ((allAValues.contains(aVal)) && (allBValues.contains(bVal))) {
                    xCor.add(i);
                    yCor.add(y);
                }
            }
        }

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_Lab2BGR);
        System.out.println(xCor.size());
        System.out.println(yCor.size());
    }
}
