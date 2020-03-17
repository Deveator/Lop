package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class BgrComputeImg {

    public static ArrayList<String> bgr_Values = new ArrayList<String>();
    public static ArrayList<String> yx_Values = new ArrayList<String>();
    public static ArrayList<String> diffrent_bgr_values = new ArrayList<String>();
    public static ArrayList<Integer> diffBValues = new ArrayList<Integer>();
    public static ArrayList<Integer> diffGValues = new ArrayList<Integer>();
    public static ArrayList<Integer> diffRValues = new ArrayList<Integer>();
    public static ArrayList<ArrayList> clustersB = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> clustersBIndex = new ArrayList<ArrayList>();

    public static void getClustersFromBgrImg(Mat mat) {

        for (int x = 0; x < mat.cols(); x++) {
            for (int y = 0; y < mat.rows(); y++) {
                double[] fullBgrValue = mat.get(y, x);
                int b = (int) fullBgrValue[0];
                int g = (int) fullBgrValue[1];
                int r = (int) fullBgrValue[2];
                String s = b + "." + g + "." + r;
                bgr_Values.add(s);
                String s1 = y + "." + x;
                yx_Values.add(s1);
            }
        }

        getDifferentBgrValues();
        getBGRarrays();
        clusterizeB(20);

        System.out.println("COMPLETED");
    }

    public static void getDifferentBgrValues() {
        // find different values
        HashSet<String> hSetNumbers = new HashSet(bgr_Values);
        diffrent_bgr_values.addAll(hSetNumbers);
        System.out.println("diffrent_bgr_values - " + diffrent_bgr_values.size());
    }

    public static void getBGRarrays() {

        String[] arrOfStr;

        for(String str : diffrent_bgr_values){
            arrOfStr = str.split("\\.");
            diffBValues.add(Integer.valueOf(arrOfStr[0]));
            diffGValues.add(Integer.valueOf(arrOfStr[1]));
            diffRValues.add(Integer.valueOf(arrOfStr[2]));
        }
        System.out.println("diffBValues - " + diffBValues.size());
    }

    public static void clusterizeB(int clusterStep) {

        ArrayList<Integer> aL1 = new ArrayList<>();
        ArrayList<Integer> indexFromBStep = new ArrayList<>();

        for (int i = 0; i < diffBValues.size(); i++) {
            int bVal = diffBValues.get(i);
           // ArrayList<Integer> aL1 = new ArrayList<>();
           // ArrayList<Integer> indexFromBStep = new ArrayList<>();
            aL1.add(bVal);
            indexFromBStep.add(i);
            for (int i2 = 0; i2 < diffBValues.size(); i2++) {
                if (i2 != i) {
                    int bVal2 = diffBValues.get(i2);
                    int res = bVal2 - bVal;
                    if ((res >= 0) && (res < clusterStep)) {
                    //    aL1.add(bVal2);
                      //  indexFromBStep.add(i2);
                    }
                }
            }
            clustersB.add(aL1);
           // clustersBIndex.add(indexFromAStep);
        }
         System.out.println("clustersB - " + clustersB.size());
        ///  System.out.println("clustersA first - " + clustersA.get(0).size());
        ///  System.out.println("****************");
        ///   System.out.println("clustersAIndex - " + clustersAIndex.size());
        ///   System.out.println("clustersAIndex first - " + clustersAIndex.get(0).size());
    }

}
