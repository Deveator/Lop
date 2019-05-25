package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.andrey.lop.MainActivity.xCoo;
import static com.example.andrey.lop.MainActivity.yCoo;

public class TestClass {

    static ArrayList<Integer> xV = new ArrayList<Integer>();
    static ArrayList<Integer> yV = new ArrayList<Integer>();
    static ArrayList<Integer> index = new ArrayList<Integer>();
    static ArrayList<Integer> yDraft = new ArrayList<Integer>();
    static ArrayList<Integer> xDraft = new ArrayList<Integer>();
    public static ArrayList<Integer> allX = new ArrayList<Integer>();
    public static ArrayList<Integer> allY = new ArrayList<Integer>();
    static ArrayList<Integer> needDr = new ArrayList<Integer>();
    static boolean res = false;

    public static Mat getOnlyClothImg(Mat mImg2){







        return mImg2;
    }

    public static void testCalculate() {

        for (int t : xCoo) {
            xV.add(t);
        }
        for (int t : yCoo) {
            yV.add(t);
        }




      /*
        int[] vX = {13, 8, 6, 12, 9, 10, 5, 4, 5, 6, 6, 6, 7, 3, 4, 9, 10, 11, 12, 13, 13, 12, 13, 12, 11, 4, 4, 3, 4, 4, 5, 11, 7, 8, 13, 14, 15, 14, 13, 14, 13, 13, 10, 11, 11, 12};
        int[] vY = {2, 12, 5, 8, 3, 10, 8, 11, 10, 10, 11, 12, 12, 10, 9, 12, 12, 12, 12, 12, 11, 11, 10, 10, 10, 7, 6, 6, 5, 4, 5, 9, 5, 4, 7, 7, 6, 6, 5, 4, 4, 3, 3, 3, 2, 2};


        //already create/get X and Y coordinates
        for (int z = 0; z < vX.length; z++) {
            xV.add(vX[z]);
            yV.add(vY[z]);
        }
        */


        // loop to iterate through all columns
        for (int c = 0; c < 800; c++) {
            // get index and set indexes in 'index' array, to get Y value,
            for (int t = 0; t < xV.size(); t++) {
                if (xV.get(t) == c) {
                    index.add(t);
                }
            }
            // with the help of 'index' array create 'yDraft' array of Y values

            for (int u = 0; u < index.size(); u++) {
                yDraft.add(yV.get(index.get(u)));

            }
            index.clear();

            //index.clear();
            // sort "yDraft' array
            Collections.sort(yDraft);
            // start check cases of 'yDraft' array size
            // size == 1


            if (yDraft.size() == 1) {
                // add 1 in final XY
                allX.add(c);
                allY.add(yDraft.get(0));
                // size == 2
            } else if (yDraft.size() == 2) {
                // 'nextVal' - "previousVal' ==1
                if ((yDraft.get(1) - yDraft.get(0)) == 1) {
                    // add 2 in final XY
                    allX.add(c);
                    allY.add(yDraft.get(0));
                    allX.add(c);
                    allY.add(yDraft.get(1));
                    // 'nextVal' - "previousVal' > 1
                } else if ((yDraft.get(1) - yDraft.get(0)) > 1) {
                    // get first value from 'yDraft' array
                    int g = yDraft.get(0);
                    // create list of values between first and second values, need to understand must put all column in final XY or not
                    createList();
                    // take decision put all OR only two
                    // c - current column number
                    if (fillIt(g, c)) {
                        // add all column in final XY
                        addCoordinates(needDr, c);
                        // add 2 in final XY
                    } else {
                        allX.add(c);
                        allY.add(yDraft.get(0));
                        allX.add(c);
                        allY.add(yDraft.get(1));
                    }
                }
                // size > 2
            } else if (yDraft.size() > 2) {
                chekVar(yDraft, c);
            }

            yDraft.clear();
            xDraft.clear();
            needDr.clear();
        }
        // System.out.println(yDraft.size());
         System.out.println(allX.size());
        System.out.println(allY.size());
      //  for (int u2 = 0; u2 < allX.size(); u2++) {
            //  System.out.println(needDr.get(u2));
        //    System.out.println(allX.get(u2) + " || " + allY.get(u2));
       // }
    }

    public static void createList() {

        int t = yDraft.get(0);
        int t2 = yDraft.get(1);

        for (int r = t + 1; r < t2; r++) {
            needDr.add(r);
        }
    }

    public static void createList2(int e, int e2) {

        for (e = e + 1; e < e2; e++) {
            needDr.add(e);
            // System.out.println("------------");
            //  System.out.println(e);

        }
    }

    public static void createlstX() {

        int t = yDraft.get(0);
        int t2 = yDraft.get(1);

        for (int r = t + 1; r < t2; r++) {
            needDr.add(r);
        }
    }


    public static boolean fillIt(int nYCoor, int xCoord) {
        // get first value  + 1 from 'yDraft" array
        nYCoor = nYCoor + 1;
        //  System.out.println("++++" + nYCoor);
        res = false;
        int m = 0;
        int m2 = 0;
        // create 'xDraft' array of x values if y value == first value + 1
        for (int i2 = 0; i2 < yV.size(); i2++) {
            if (yV.get(i2) == nYCoor) {
                xDraft.add(xV.get(i2));
                //   System.out.println("||||" + xV.get(i2));
            }
        }
        for (int u = 0; u < xDraft.size(); u++) {
            if (xDraft.get(u) < xCoord) {
                m = 1;
                //   System.out.println("less" );
            } else if (xDraft.get(u) > xCoord) {
                //  System.out.println("more" );
                m2 = 1;
            }
        }
        if ((m == 1) && (m2 == 1)) {
            res = true;
        }
        xDraft.clear();
        return res;
    }


    public static void addCoordinates(ArrayList<Integer> arrayList, int f) {

        allX.add(f);
        allY.add(yDraft.get(0));
        for (int i = 0; i < arrayList.size(); i++) {
            allX.add(f);
            allY.add(arrayList.get(i));
        }
        allX.add(f);
        allY.add(yDraft.get(1));


    }

    public static void addCoordinates2(ArrayList<Integer> arrayList, int f, int fY) {
        // 'arrayList' = 'needDr' array
        // 'f' = current column(c)
        // 'fY' = y2
        for (int i = 0; i < arrayList.size(); i++) {
            allX.add(f);
            allY.add(arrayList.get(i));
        }
        allX.add(f);
        allY.add(fY);


    }

    // create pairs of values
    public static void chekVar(ArrayList<Integer> arrayList, int t) {
        // arrayList = 'yDraft' array
        // t = current column(c)
        int r1 = 0;
        int r2 = 0;

        allX.add(t);
        allY.add(arrayList.get(0));

        for (int y = 0; y < arrayList.size(); y++) {

            if (y < (arrayList.size() - 1)) {

                r1 = arrayList.get(y);
                int u2 = y + 1;
                r2 = arrayList.get(u2);

                checkNum(r1, r2, t);
                //System.out.println(r1 + " * " + r2);
            }
        }
    }

    public static void checkNum(int y, int y2, int xC) {
        // y = r1 from 'checkVar' method
        // y2 = r2 from 'checkVar' method
        // xC - current column(c)
        // case deviation == 1 - add second value in final XY
        // allX.add(xC);
        // allY.add(y);
        if ((y2 - y) == 1) {
            allX.add(xC);
            allY.add(y2);
            //System.out.println(11);
            // case 'deviation' > 1
        } else {
            //System.out.println(13);
            ///   System.out.println(y + " " + y2);
            // create list of values between first and second values, need to understand must put all column in final XY or not
            createList2(y, y2);

            if (fillIt(y, xC)) {

                addCoordinates2(needDr, xC, y2);
                //needDr.clear();

            } else {
              //  System.out.println(14);
                allX.add(xC);
                allY.add(y2);
                //needDr.clear();
            }
            needDr.clear();
        }

    }
}
