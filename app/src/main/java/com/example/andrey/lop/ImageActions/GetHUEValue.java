package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetHUEValue {

    //  public static ArrayList lidtT;
   // public static ArrayList<Integer> xCor = new ArrayList<Integer>();
   // public static ArrayList<Integer> yCor = new ArrayList<Integer>();


    public static Mat getHUEValue(Mat img) {

        // ArrayList myList = new ArrayList();
        //ArrayList<double> al=new ArrayList<double>();
        // List<Double> list = new ArrayList<>(Arrays.asList(double));

        ArrayList<Integer> hue = new ArrayList<Integer>();
        ArrayList<Integer> sat = new ArrayList<Integer>();
        ArrayList<Integer> value = new ArrayList<Integer>();

        ArrayList<Integer> allHueDigits = new ArrayList<Integer>();
        // ArrayList<Integer> xCor = new ArrayList<Integer>();
        // ArrayList<Integer> yCor = new ArrayList<Integer>();

        int maxHue, minHue, avrHue;
        int maxSat, minSat, avrSat;
        int maxValue, minValue, avrValue;


        Mat mImg = new Mat();
        Size vSize = new Size(3,3);
        Imgproc.blur(img,mImg,vSize);

        Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2HSV);

        for (int i = 0; i < 250; i++) {

            for (int c = 0; c < 250; c++) {

                double[] val = mImg.get(749+i, c);

                int vHue = (int) val[0];
                int vSat = (int) val[1];
                int vValue = (int) val[2];
                hue.add(vHue);
                sat.add(vSat);
                value.add(vValue);
                // System.out.println(o);
                // System.out.println(val[0] + "|" + val[1] + "|" + val[2]);
            }
        }

        int s = hue.size();
        System.out.println(s);
        maxHue = hue.get(0);
        minHue = hue.get(0);
        maxSat = sat.get(0);
        minSat = sat.get(0);
        maxValue = value.get(0);
        minValue = value.get(0);
        avrHue = 0;
        for (int y : hue) {

            if (y >= maxHue) {
                maxHue = y;
            }
            if (y <= minHue) {
                minHue = y;
            }
        }
        //maxHue = maxHue + 16;
        //minHue = minHue - 16;
        System.out.println(maxHue + " | maxHue ");
        System.out.println(minHue + " || minHue");

        int deviation = maxHue - minHue;

        allHueDigits.add(minHue);
        allHueDigits.add(maxHue);
        for (int k = 1; k < deviation; k++) {

            allHueDigits.add(minHue + k);

        }
        System.out.println(allHueDigits.size());

        for (int i = 0; i < mImg.cols(); i++) {
            for (int y = 0; y < mImg.rows(); y++) {
                double[] joy = mImg.get(y, i);
                int h2 = (int) joy[0];
                if (allHueDigits.contains(h2)) {
                  //  xCor.add(i);
                  //  yCor.add(y);
                }
            }
        }
        //   System.out.println(xCor.size());
        // System.out.println(yCor.size());
/*
        for (int y : sat) {
            if (y >= maxSat) {
                maxSat = y;
            }
            if (y <= minSat) {
                minSat = y;
            }
        }
        System.out.println(maxSat + " | maxSat ");
        System.out.println(minSat + " || minSat");

        for (int y : value) {
            if (y >= maxValue) {
                maxValue = y;
            }
            if (y <= minValue) {
                minValue = y;
            }
        }
        System.out.println(maxValue + " | maxValue ");
        System.out.println(minValue + " || minValue");
        */
        //  System.out.println(avrHue / s);
/*
        System.out.println(img.cols());
        System.out.println(img.rows());

        // System.out.println("- -  - - - - - - -  - -");

        for (int z = 0; z < 40; z++) {

            double[] tre = img.get(550, 300 + z);

            System.out.print(String.valueOf(tre[2]) + " | ");

        }

        */
        //  System.out.println(val[1]);
        //  System.out.println(val[2]);
        //  System.out.println(img.channels());


        return img;

    }
}
