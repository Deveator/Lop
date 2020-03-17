package com.example.andrey.lop.ImageActions;

import android.util.Log;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LabImg {

    public static ArrayList<Integer> xCor = new ArrayList<Integer>();
    public static ArrayList<Integer> yCor = new ArrayList<Integer>();
    public static ArrayList<Integer> allAValues = new ArrayList<Integer>();
    public static ArrayList<Integer> allBValues = new ArrayList<Integer>();

    public static ArrayList<Integer> aValues = new ArrayList<Integer>();
    public static ArrayList<Integer> bValues = new ArrayList<Integer>();
    public static ArrayList<String> a_b_Values = new ArrayList<String>();
    public static ArrayList<String> y_x_Values = new ArrayList<String>();
    public static ArrayList<ArrayList> clusterS_y_x_Values = new ArrayList<ArrayList>();
    public static ArrayList<Double> cluster_y_x_Values = new ArrayList<Double>();
    public static ArrayList<Integer> diffAValues = new ArrayList<Integer>();
    public static ArrayList<Integer> diffBValues = new ArrayList<Integer>();
    public static ArrayList<Integer> diffXValues = new ArrayList<Integer>();
    public static ArrayList<Integer> diffYValues = new ArrayList<Integer>();

    public static ArrayList<ArrayList> all_diffXValues = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> all_diffYValues = new ArrayList<ArrayList>();

    public static ArrayList<String> diffrent_a_b_values = new ArrayList<String>();
    public static ArrayList<Integer> diffrent_a_b_values_indexes = new ArrayList<Integer>();

    public static ArrayList<ArrayList> clusters = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> clustersA = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> clustersAIndex = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> clustersB = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> maxClusters = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> clustersBIndex = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> clustersBIndexCopy = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> finalSortedCluster = new ArrayList<ArrayList>();


    public static void getClustersFromLabImg(Mat mat) {

        Mat labMat = new Mat();
        Imgproc.cvtColor(mat, labMat, Imgproc.COLOR_BGR2Lab);

        for (int x = 0; x < labMat.cols(); x++) {
            for (int y = 0; y < labMat.rows(); y++) {
                double[] fullLabValue = labMat.get(y, x);
                int a = (int) fullLabValue[1];
                int b = (int) fullLabValue[2];
                String s = a + "." + b;
                a_b_Values.add(s);
                String s1 = y + "." + x;
                y_x_Values.add(s1);
            }
        }

        //   double[] fullLabValue = labMat.get(500, 1);
        //    int a = (int) fullLabValue[1];
        //    int b = (int) fullLabValue[2];
        //     String s = a + "." + b;
        System.out.println(a_b_Values.size());
        System.out.println(123456);
        System.out.println(y_x_Values.size());

/*
        for (int x = 0; x < 401; x = x + 10) {
            double[] fullLabValue = labMat.get(500, x);
            int a = (int) fullLabValue[1];
            int b = (int) fullLabValue[2];
            String s = a + "." + b;
            System.out.println(x + " - " + s);
        }
*/
        getDifferentAbValues();
        getAandBarrays();
        clusterizeA(29);
        clusterizeB(29);
        sortArray2();
    }

    public static void getDifferentAbValues() {


        int num = 0;
        for (int i = 0; i < a_b_Values.size(); i++) {
            String s = a_b_Values.get(i);
            if (!diffrent_a_b_values.contains(s)) {
                diffrent_a_b_values.add(s);
                diffrent_a_b_values_indexes.add(num);
                num++;
            }
        }
        // System.out.println("diffrent_a_b_values 5 index - " + diffrent_a_b_values.get(499));


        // System.out.println("diffrent_a_b_values - " + diffrent_a_b_values.size());
        //  System.out.println("diffrent_a_b_values_indexes - " + diffrent_a_b_values_indexes.size());
    }

    public static void getAandBarrays() {
        for (int i = 0; i < diffrent_a_b_values.size(); i++) {
            String r = String.valueOf(diffrent_a_b_values.get(i));
            String[] arrOfStr = r.split("\\.");
            diffAValues.add(Integer.valueOf(arrOfStr[0]));
            diffBValues.add(Integer.valueOf(arrOfStr[1]));
        }
        //  System.out.println("diffAValues - " + diffAValues.size());
        ///   System.out.println("****************");
        ///  System.out.println("diffBValues 499 index - " + diffBValues.get(499));
    }

    public static void clusterizeA(int clusterStep) {

        for (int i = 0; i < diffAValues.size(); i++) {
            int aVal = diffAValues.get(i);
            ArrayList<Integer> aL1 = new ArrayList<>();
            ArrayList<Integer> indexFromAStep = new ArrayList<>();
            aL1.add(aVal);
            indexFromAStep.add(i);
            for (int i2 = 0; i2 < diffAValues.size(); i2++) {
                if (i2 != i) {
                    int aVal2 = diffAValues.get(i2);
                    int res = aVal2 - aVal;
                    if ((res >= 0) && (res < clusterStep)) {
                        aL1.add(aVal2);
                        indexFromAStep.add(i2);
                    }
                }
            }
            clustersA.add(aL1);
            clustersAIndex.add(indexFromAStep);
        }
        /// System.out.println("clustersA - " + clustersA.size());
        ///  System.out.println("clustersA first - " + clustersA.get(0).size());
        ///  System.out.println("****************");
        ///   System.out.println("clustersAIndex - " + clustersAIndex.size());
        ///   System.out.println("clustersAIndex first - " + clustersAIndex.get(0).size());
    }

    public static void clusterizeB(int clusterStep) {

        for (int i = 0; i < clustersAIndex.size(); i++) {

            ArrayList<Integer> helpForB = clustersAIndex.get(i);
            ArrayList<Integer> bL1 = new ArrayList<>();
            ArrayList<Integer> indexFromBStep = new ArrayList<>();
            ArrayList<Integer> indexFromBStepCopy = new ArrayList<>();

            int bVal = diffBValues.get(helpForB.get(0));

            bL1.add(bVal);
            indexFromBStep.add(helpForB.get(0));
            indexFromBStepCopy.add(helpForB.get(0));

            for (int y = 1; y < helpForB.size(); y++) {
                int bVal1 = diffBValues.get(helpForB.get(y));
                int res = bVal1 - bVal;
                if ((res >= 0) && (res < clusterStep)) {
                    bL1.add(bVal1);
                    indexFromBStep.add(helpForB.get(y));
                    indexFromBStepCopy.add(helpForB.get(y));
                }
            }
            clustersB.add(bL1);
            clustersBIndex.add(indexFromBStep);
            clustersBIndexCopy.add(indexFromBStepCopy);
        }
        /// System.out.println("clustersB - " + clustersB.size());
        /// System.out.println("clustersB first size - " + clustersB.get(0).size());
        System.out.println("****************");
        ///  System.out.println("clustersBIndex - " + clustersBIndex.size());
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(129).get(0));
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(129).size());
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(0).get(0));
        ///  System.out.println("clustersBIndexCopy - " + clustersBIndexCopy.size());
        ///  System.out.println("clustersBIndexCopy first size - " + clustersBIndexCopy.get(0).size());
    }

    /*
        // get num of specific pixelValue from all image
        public static void getNumOfSpecificValues() {

            ArrayList<Double> al = new ArrayList<Double>();
            ArrayList<Integer> al1 = clustersBIndex.get(0);
            int count = 0;
            for (int k = 0; k < al1.size(); k++) {
                int ind = al1.get(k);
                al.add(diffrent_a_b_values.get(ind));
            }

            System.out.println(al.size());

            for (int i = 0; i < a_b_Values.size(); i++) {
                if (al.contains(a_b_Values.get(i))) {
                    count++;
                }
            }
            System.out.println("Specific values = " + count);
        }
    */
    //
    public static void sortArray2() {

        // array with different a_b combination
        ArrayList<ArrayList> aL = new ArrayList<ArrayList>();
        // array with different indexes
        ArrayList<ArrayList> aL_1 = new ArrayList<ArrayList>();
        // array help to sort
        ArrayList<Integer> aList = new ArrayList<Integer>();
        // array help to find required array
        ArrayList<Integer> aList2 = new ArrayList<Integer>();

        for (int i = 0; i < clustersBIndex.size(); i++) {
            aList.add(clustersBIndex.get(i).size());
        }
        Collections.sort(aList);
        Collections.reverse(aList);

        ///  System.out.println("aList size - " + aList.size());
        /// System.out.println("aList first - " + aList.get(0));

        /// System.out.println("*******1*********");
        /// System.out.println("clustersBIndex - " + clustersBIndex.size());
        /// System.out.println("clustersBIndex first size - " + clustersBIndex.get(0).size());
        /// System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(129).size());
        ///  System.out.println("****************");

        for (int y = 0; y < clustersBIndexCopy.size(); y++) {
            for (int j = 0; j < clustersBIndexCopy.size(); j++) {
                if ((aList.get(y) != 0) && (aList.get(y) == clustersBIndexCopy.get(j).size())) {
                    aList2.add(j);
                    clustersBIndexCopy.get(j).clear();
                    break;
                }
            }
        }
        ///  System.out.println("*******2*********");
        ///   System.out.println("clustersBIndex - " + clustersBIndex.size());
        ///   System.out.println("clustersBIndex first size - " + clustersBIndex.get(0).size());
        ///    System.out.println("clustersBIndex 129 size - " + clustersBIndex.get(129).size());

        ///    System.out.println("aList2 size - " + aList2.size());
        ///    System.out.println("aList2 first - " + aList2.get(0));
        ////     System.out.println("-------------");
        ///      System.out.println("clustersBIndex.get(aList2.get(0)).size() - " + clustersBIndex.get(aList2.get(0)).size());

        // sorted array with different indexes
        ArrayList<ArrayList> aL_2 = new ArrayList<ArrayList>();

        for (int i = 0; i < aList2.size(); i++) {
            aL_2.add(clustersBIndex.get(aList2.get(i)));
        }
        /// System.out.println("-------------");
        /// System.out.println(aL_2.size());
        ///  System.out.println(aL_2.get(0).size());
        ///  System.out.println(aL_2.get(0).get(0));
        ///   System.out.println("-------------");
        ///   System.out.println(aL_2.get(1).size());
        ///   System.out.println(aL_2.get(1).get(0));
        ///  System.out.println("-------------");
        ///  System.out.println(aL_2.get(2).size());
        ///  System.out.println(aL_2.get(129).size());
        ///   System.out.println("-------------");

        for (int i = 0; i < aL_2.size() - 1; i++) {

            for (int y = 0; y < aL_2.get(i).size(); y++) {

                ArrayList<Integer> helpAL = aL_2.get(i);

                int val = helpAL.get(y);

                for (int u = (i + 1); u < aL_2.size(); u++) {

                    for (int p = 0; p < aL_2.get(u).size(); p++) {

                        ArrayList<Integer> helpAL2 = aL_2.get(u);

                        int val2 = helpAL2.get(p);

                        if (val == val2) {

                            helpAL2.remove(p);
                            p--;
                        }
                    }
                }
            }
            removeZero(aL_2);
            ArrayList<ArrayList> nAl = sortDesc(aL_2);
            aL_2.clear();
            for (int i2 = 0; i2 < nAl.size(); i2++) {
                aL_2.add((ArrayList) nAl.get(i2).clone());
            }
            nAl.clear();
            removeZero(aL_2);
        }


        /// for (int i = 0; i < aL_2.size(); i++) {
        ///     if (aL_2.get(i).size() == 0) {
        ///         aL_2.remove(i);
        ///         i--;
        ///      }
        ///  }
        ///   System.out.println("****************");
        for (int i = 0; i < aL_2.size(); i++) {
            finalSortedCluster.add(aL_2.get(i));
        }
        System.out.println("finalSortedCluster - " + finalSortedCluster.size());
        System.out.println(finalSortedCluster.get(0).size());
        System.out.println(finalSortedCluster.get(0).get(0));
       // System.out.println(diffrent_a_b_values.get(122));

        reColor();

    }

    public static void removeZero(ArrayList<ArrayList> aL) {
        for (int i = 0; i < aL.size(); i++) {
            if (aL.get(i).size() == 0) {
                aL.remove(i);
                i--;
            }
        }
    }

    public static ArrayList<ArrayList> sortDesc(ArrayList<ArrayList> _aL) {

        //copy to help
        ArrayList<ArrayList> allArraysCopy = new ArrayList<ArrayList>();
        for (int i = 0; i < _aL.size(); i++) {
            allArraysCopy.add((ArrayList) _aL.get(i).clone());
        }

        ArrayList<Integer> arrayListSize = new ArrayList<>();
        for (int i = 0; i < _aL.size(); i++) {
            arrayListSize.add(_aL.get(i).size());
        }
        Collections.sort(arrayListSize);
        Collections.reverse(arrayListSize);

        ArrayList<Integer> aList2 = new ArrayList<Integer>();

        for (int y = 0; y < allArraysCopy.size(); y++) {
            for (int j = 0; j < allArraysCopy.size(); j++) {
                if ((arrayListSize.get(y) != 0) && (arrayListSize.get(y) == allArraysCopy.get(j).size())) {
                    aList2.add(j);
                    allArraysCopy.get(j).clear();
                    break;
                }
            }
        }
        // sorted array with different indexes
        ArrayList<ArrayList> aL_2 = new ArrayList<ArrayList>();

        for (int i = 0; i < aList2.size(); i++) {
            aL_2.add(_aL.get(aList2.get(i)));
        }
        return aL_2;
    }

    public static void reColor() {

        ArrayList<String> aL = new ArrayList<>();
        ArrayList<ArrayList> aL_All = new ArrayList<>();
// changes
        for (int c = 0; c < finalSortedCluster.size(); c++) {

            ArrayList<Integer> help_aL = finalSortedCluster.get(c);

            for (int i = 0; i < help_aL.size(); i++) {

                int y = help_aL.get(i);

                aL.add(diffrent_a_b_values.get(y));

            }
            aL_All.add((ArrayList) aL.clone());
            aL.clear();
        }


        System.out.println("++++++++++++++++++");
        //  for (int i = 0; i < 200; i++) {
        //      System.out.println(i + " - " + aL_All.get(0).get(i));
        //System.out.println(aL.get(0));
        //  }

        ArrayList<String> aL_2 = new ArrayList<>();
        //   ArrayList<ArrayList> aL_All_2 = new ArrayList<>();

        for (int c = 0; c < aL_All.size(); c++) {

            ArrayList<String> help_aL = aL_All.get(c);

            for (int i = 0; i < help_aL.size(); i++) {

                for (int q = 0; q < a_b_Values.size(); q++) {

                    if (help_aL.get(i).equals(a_b_Values.get(q))) {

                        aL_2.add(y_x_Values.get(q));

                        //  if(help_aL.get(i)== 128.125){
                        //     System.out.println(y_x_Values.get(q));
                        //  }
                    }
                }
            }
            clusterS_y_x_Values.add((ArrayList) aL_2.clone());
            aL_2.clear();
        }
        int res=0;
        System.out.println("*********************");
        System.out.println(clusterS_y_x_Values.size());
        System.out.println(clusterS_y_x_Values.get(0).size());

        for(int i = 0; i < clusterS_y_x_Values.size(); i++){
            res = res + clusterS_y_x_Values.get(i).size();
        }
        System.out.println("Count of all arrays " + res);
        // for (int i = 0; i < 200; i++) {
        //     System.out.println(i + " - " + clusterS_y_x_Values.get(0).get(i));
        //System.out.println(aL.get(0));
        //  }


       getYandXarrays();

    }

    public static void getYandXarrays() {

        ArrayList<Integer> _diffYValues = new ArrayList<>();
        ArrayList<Integer> _diffXValues = new ArrayList<>();

        for (int c = 0; c < clusterS_y_x_Values.size(); c++) {

            ArrayList<String> help_aL = clusterS_y_x_Values.get(c);

            for (int i = 0; i < help_aL.size(); i++) {

                String r = help_aL.get(i);

                String[] arrOfStr = r.split("\\.");

                _diffYValues.add(Integer.valueOf(arrOfStr[0]));

                _diffXValues.add(Integer.valueOf(arrOfStr[1]));
            }
            all_diffYValues.add((ArrayList) _diffYValues.clone());
            all_diffXValues.add((ArrayList) _diffXValues.clone());
            _diffYValues.clear();
            _diffXValues.clear();
        }

        //   for (int i = 0; i < all_diffYValues.size(); i++) {
        //      System.out.println(" - " + all_diffYValues.get(i).size());
        //   System.out.println(aL.get(0));
        //    }
    }


    public static void check(Mat mat) {

        Mat labMat = new Mat();

        Imgproc.cvtColor(mat, labMat, Imgproc.COLOR_BGR2Lab);

        ArrayList<Double> aL = new ArrayList<>();
        for (int x = 0; x < labMat.cols(); x++) {
            double[] fullLabValue = labMat.get(0, x);
            int a = (int) fullLabValue[1];
            int b = (int) fullLabValue[2];
            String s = a + "." + b;
            double d = Double.valueOf(s);
            aL.add(d);
        }
        for (int i = 0; i < 100; i++) {
            System.out.println(i + " - " + aL.get(i));
        }
        System.out.println("_40 - " + aL.get(40));

    }

    public static void getMaxArray() {

        int num = clustersB.get(0).size();
        int rIndex = 0;
        int hIndex = 0;
        for (int i = 1; i < clustersB.size(); i++) {
            if (clustersB.get(i).size() > num) {
                num = clustersB.get(i).size();
                rIndex = i;
            }
        }
        maxClusters.add(clustersB.get(rIndex));

        for (int i2 = 1; i2 < clustersB.size(); i2++) {
            if (i2 != rIndex) {
                if (clustersB.get(i2).size() == num) {
                    num = clustersB.get(i2).size();
                    hIndex = i2;
                    maxClusters.add(clustersB.get(hIndex));
                }
            }
        }
        System.out.println(maxClusters.size());
        System.out.println(maxClusters.get(0).size());
        System.out.println(maxClusters.get(1).size());
    }
/*
    public static void getCoordinatesForDifferentAbValues() {
        String d = "";
        for (int i = 0; i < diffrent_a_b_values.size(); i++) {
            System.out.println(i);
            ArrayList<Double> dAL = new ArrayList<>();
            for (int e = 0; e < a_b_Values.size(); e++) {
                if (a_b_Values.get(e).equals(diffrent_a_b_values.get(i))) {
                    dAL.add(y_x_Values.get(e));
                }
            }
            clusters.add(dAL);
        }
    }

    */

    public static void fillClusters() {

        for (int i = 0; i < aValues.size(); i++) {
            int aV = aValues.get(i);
            int bV = bValues.get(i);

        }
    }

    public static void sortArray() {
        ArrayList<Integer> aL = new ArrayList<Integer>();
        for (int i = 0; i < clustersB.size(); i++) {
            aL.add(clustersB.get(i).size());
        }
        Collections.sort(aL);
        Collections.reverse(aL);
    }

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
