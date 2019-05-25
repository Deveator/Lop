package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ValidateCoordinates {

    public static Mat getOnlyClotheImg(Mat matImg, ArrayList<Integer> xArray, ArrayList<Integer> yArray) {

        ArrayList<Integer> xValid = new ArrayList<Integer>();
        ArrayList<Integer> yValid = new ArrayList<Integer>();
        ArrayList<Integer> Index = new ArrayList<Integer>();
        ArrayList<Integer> yValues = new ArrayList<Integer>();

        for (int c = 0; c < matImg.cols(); c++) {

            if(xArray.contains(c)){

                for (int u : xArray) {

                    if (u == c) {
                        Index.add(xArray.indexOf(c));
                    }
                }
                // find y values
               // for(int c2 = 0; c2<)

            }


/*
            if (xArray.contains(c)) {

                for (int u : xArray) {

                    if (u == c) {
                        foundIndexX.add(xArray.indexOf(c));
                    }
                }

                for (int b = 0; b < foundIndexX.size(); b++) {
                    int c2 = foundIndexX.get(b);
                    foundIndexY.add(yArray.get(c2));
                }

                Collections.sort(foundIndexY);

                if (foundIndexY.size() == 1) {
                    xValid.add(c);
                    yValid.add(foundIndexY.get(0));

                } else if (foundIndexY.size() == 2) {

                    if ((foundIndexY.get(1) - foundIndexY.get(0)) == 1) {
                        xValid.add(c);
                        yValid.add(foundIndexY.get(0));
                        xValid.add(c);
                        yValid.add(foundIndexY.get(1));

                    } else if ((foundIndexY.get(1) - foundIndexY.get(0)) > 1) {

                        if (yArray.contains(foundIndexY.get(0) + 1) ) {

                        }

                    }
                }
            }

            */
        }


        return matImg;
    }

    public int getT(ArrayList<Integer> xArray, ArrayList<Integer> yArray,int y) {

        int yr = 0;
        return  y;

    }

}
