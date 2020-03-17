package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

public class NextLabImg {

    public static ArrayList<String> Lab_Values = new ArrayList<String>();
    public static ArrayList<String> yx_Values = new ArrayList<String>();
    public static ArrayList<String> diffrent_Lab_values = new ArrayList<String>();
    public static ArrayList<Integer> diffrent_Lab_indexes = new ArrayList<Integer>();
    public static ArrayList<Integer> diffA_Values = new ArrayList<Integer>();
    public static ArrayList<Integer> diffB_Values = new ArrayList<Integer>();
    public static ArrayList<Integer> diffL_Values = new ArrayList<Integer>();
    public static ArrayList<ArrayList<Integer>> clustersA_ = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> clustersA_Index = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> clustersB_ = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList<Integer>> clustersB_Index = new ArrayList<ArrayList<Integer>>();
    public static ArrayList<ArrayList> clustersB_IndexCopy = new ArrayList<ArrayList>();

    public static ArrayList<String> diffrent_ab_values = new ArrayList<String>();

    public static void getClustersFromLabImg(Mat mat) {

        Mat labMat = new Mat();

        Imgproc.cvtColor(mat, labMat, Imgproc.COLOR_BGR2Lab);

        for (int x = 0; x < labMat.cols(); x++) {
            for (int y = 0; y < labMat.rows(); y++) {
                double[] fullLabValue = labMat.get(y, x);

                int L = (int) fullLabValue[0];
                int a = (int) fullLabValue[1];
                int b = (int) fullLabValue[2];

                String s = a + "." + b + "." + L;
                Lab_Values.add(s);
                String s1 = y + "." + x;
                yx_Values.add(s1);
            }
        }
        getDifferentLabValues();
        getAandBarrays();


        clusterizeA(40);
        clusterizeB(40);
        System.out.println("COMPLETED");
    }

    public static void getDifferentLabValues() {
        // find different values
      ///  TreeSet<String> tSetNumbers = new TreeSet(Lab_Values);
      ///  diffrent_Lab_values.addAll(tSetNumbers);
      ///  System.out.println("diffrent_Lab_values - " + diffrent_Lab_values.size());
        int num = 0;
        for (int i = 0; i < Lab_Values.size(); i++) {
            String s = Lab_Values.get(i);
            if (!diffrent_Lab_values.contains(s)) {
                diffrent_Lab_values.add(s);
                diffrent_Lab_indexes.add(num);
                num++;
            }
        }
        System.out.println("different_Lab_values - " + diffrent_Lab_values.size());
    }

    public static void getAandBarrays() {
        for (int i = 0; i < diffrent_Lab_values.size(); i++) {
            String r = String.valueOf(diffrent_Lab_values.get(i));
            String[] arrOfStr = r.split("\\.");
            diffA_Values.add(Integer.valueOf(arrOfStr[0]));
            diffB_Values.add(Integer.valueOf(arrOfStr[1]));
            diffL_Values.add(Integer.valueOf(arrOfStr[2]));
        }
    }

    public static void clusterizeA(int clusterStep) {

        for (int i = 0; i < diffA_Values.size(); i++) {
            int aVal = diffA_Values.get(i);
            ArrayList<Integer> aL1 = new ArrayList<>();
            ArrayList<Integer> indexFromAStep = new ArrayList<>();
            aL1.add(aVal);
            indexFromAStep.add(i);

            for (int i2 = 0; i2 < diffA_Values.size(); i2++) {
                if (i2 != i) {
                    int aVal2 = diffA_Values.get(i2);
                    int res = aVal2 - aVal;
                    if ((res >= 0) && (res < clusterStep)) {
                        aL1.add(aVal2);
                        indexFromAStep.add(i2);
                    }
                }
            }
            clustersA_.add(aL1);
            clustersA_Index.add(indexFromAStep);
        }
        System.out.println("clustersA_ - " + clustersA_.size());
        for(int i = 0; i < 10; i++){
            System.out.println(i + " - " + clustersA_.get(i).size());
        }
    }

    public static void clusterizeB(int clusterStep) {

        for (int i = 0; i < clustersA_Index.size(); i++) {

            ArrayList<Integer> helpForB = clustersA_Index.get(i);
            ArrayList<Integer> bL1 = new ArrayList<>();
            ArrayList<Integer> indexFromBStep = new ArrayList<>();
            ArrayList<Integer> indexFromBStepCopy = new ArrayList<>();

            int bVal = diffB_Values.get(helpForB.get(0));

            bL1.add(bVal);
            indexFromBStep.add(helpForB.get(0));
            indexFromBStepCopy.add(helpForB.get(0));

            for (int y = 1; y < helpForB.size(); y++) {
                int bVal1 = diffB_Values.get(helpForB.get(y));
                int res = bVal1 - bVal;
                if ((res >= 0) && (res < clusterStep)) {
                    bL1.add(bVal1);
                    indexFromBStep.add(helpForB.get(y));
                    indexFromBStepCopy.add(helpForB.get(y));
                }
            }
            clustersB_.add(bL1);
            clustersB_Index.add(indexFromBStep);
            clustersB_IndexCopy.add(indexFromBStepCopy);
        }
         System.out.println("clustersB_ - " + clustersB_.size());
        for(int i = 0; i < 10; i++){
            System.out.println(i + " - " + clustersB_.get(i).size());
        }
        /// System.out.println("clustersB first size - " + clustersB.get(0).size());
        System.out.println("****************");
        ///  System.out.println("clustersBIndex - " + clustersBIndex.size());
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(129).get(0));
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(129).size());
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(0).get(0));
        ///  System.out.println("clustersBIndexCopy - " + clustersBIndexCopy.size());
        ///  System.out.println("clustersBIndexCopy first size - " + clustersBIndexCopy.get(0).size());
    }


}
