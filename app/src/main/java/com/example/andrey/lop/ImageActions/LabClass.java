package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

import static com.example.andrey.lop.MainActivity.allColors_AL;
import static com.example.andrey.lop.MainActivity.borderPoints_AL;
import static com.example.andrey.lop.MainActivity.colorDefiningAl;
import static com.example.andrey.lop.MainActivity.linesDscrptn;

public class LabClass {

    public static ArrayList<Integer> x_Cor = new ArrayList<Integer>();
    public static ArrayList<Integer> y_Cor = new ArrayList<Integer>();

    public static ArrayList<Integer> x_Cor_ROI = new ArrayList<Integer>();
    public static ArrayList<Integer> y_Cor_ROI = new ArrayList<Integer>();

    public static ArrayList<String> xy_Cor_string = new ArrayList<String>();
    public static ArrayList<Integer> all_A_Values = new ArrayList<Integer>();
    public static ArrayList<Integer> all_B_Values = new ArrayList<Integer>();
    public static ArrayList<Integer> all_C_Values = new ArrayList<Integer>();
    public static int mDone = 0;

    public static int maxA, minA, rMin, rMax, minIntense, maxIntense;

    public static int count = 0;
    public static int upDownBorderPoints = 0;
    public static int downUpBorderPoints = 0;
    public static int leftRightBorderPoints = 0;
    public static int rightLeftBorderPoints = 0;
    public static int downLeftUpRightBorderPoints = 0;
    public static int downRightUpLeftBorderPoints = 0;
    public static int upLeftDownRightBorderPoints = 0;
    public static int upRightDownLeftBorderPoints = 0;
    public static int Nocount = 0;
    public static int countAll = 0;

    public static int squareNum = 1;
    public static int isFirst = 0;

    public static int squareNumHrzntl = 0;
    public static int squareNumVrtcl = 0;

    public static final String VERT_RLTN = "vertical relation";
    public static final String NO_VERT_RLTN = "no vertical relation";//just for developing
    public static final String HOR_RLTN = "horizontal relation";
    public static final String NO_HOR_RLTN = "no horizontal relation";//just for developing
    public static final String VERT_HOR_RLTN = "vertical horizontal relation";
    public static final String NO_RLTN = "no relation";

    public static final String START_POINT = "start point";
    public static final String SEMI_USUAL_POINT_X_0 = "semi usual point x=0";
    public static final String SEMI_USUAL_POINT_Y_0 = "semi usual point y=0";
    public static final String USUAL_POINT = "usual point";


    public static void upDown(Mat matRoi) {

        for (int i = 0; i < matRoi.cols(); i++) {

            for (int j = 0; j < matRoi.rows(); j++) {

                double[] colValue = matRoi.get(j, i);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;
                //  System.out.println("---------");
                //  System.out.println(rgb_String);

                int color = getColorAlias(rgb_String);

                if (i == 0 && j == 0) {
                    findRequiredArray(color, START_POINT, j, i);

                } else if (i == 0 && j != 0) {
                    findRequiredArray(color, SEMI_USUAL_POINT_X_0, j, i);

                } else if (i != 0 && j == 0) {

                    findRequiredArray(color, SEMI_USUAL_POINT_Y_0, j, i);

                } else if (i != 0 && j != 0) {
                    findRequiredArray(color, USUAL_POINT, j, i);
                }
            }
        }

        addColorsToBorderPoints(allColors_AL, borderPoints_AL);

        //  showBorderPointsArrays();


        // showArrays();
        // System.out.println(matRoi.cols());
        // System.out.println(matRoi.rows());

    }

    public static void downUp(Mat matRoi) {

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int i = 0; i < matRoi.cols(); i++) {

            for (int c = (matRoi.rows() - 2); c >= 0; c--) {

                double[] colValue = matRoi.get(c, i);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int _c = c + 1;
                        if (isBorderPointDownUp(allColors_AL.get(j), _c, i)) {
                            helpAl_forBorderPoint.add(c);
                            helpAl_forBorderPoint.add(i);
                            searchAddReqCoordinates(allColors_AL, _c, i, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, c, i, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            downUpBorderPoints++;
                        }
                        break;
                    }
                }
            }
        }
    }

    public static void leftRight(Mat matRoi) {

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int i = 0; i < matRoi.rows(); i++) {

            for (int c = 1; c < matRoi.cols(); c++) {

                double[] colValue = matRoi.get(i, c);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int _c = c - 1;

                        if (isBorderPointDownUp(allColors_AL.get(j), i, _c)) {
                            helpAl_forBorderPoint.add(i);
                            helpAl_forBorderPoint.add(c);
                            searchAddReqCoordinates(allColors_AL, i, _c, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, i, c, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            leftRightBorderPoints++;
                        }
                        break;
                    }
                }
            }
        }
        //  showBorderPointsArrays();
    }

    public static void rightLeft(Mat matRoi) {

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int i = 0; i < matRoi.rows(); i++) {

            for (int c = (matRoi.cols() - 2); c >= 0; c--) {

                double[] colValue = matRoi.get(i, c);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int _c = c + 1;

                        if (isBorderPointDownUp(allColors_AL.get(j), i, _c)) {
                            helpAl_forBorderPoint.add(i);
                            helpAl_forBorderPoint.add(c);
                            searchAddReqCoordinates(allColors_AL, i, _c, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, i, c, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            rightLeftBorderPoints++;
                        }
                        break;
                    }
                }
            }
        }
    }

    //downUp leftRight
    public static void diagonal_1(Mat matRoi) {

        int y = matRoi.rows();
        int x = matRoi.cols();

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int d = 0; d <= (y - 2); d++) {

            int x1 = 1;

            for (int y1 = d; y1 >= 0; y1--) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 + 1;
                        int x2 = x1 - 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            downLeftUpRightBorderPoints++;
                        }
                        break;
                    }
                }
                x1++;
                if (x1 >= x) break;
            }
        }

        for (int i = 2; i < x; i++) {

            int y1 = y - 2;

            for (int x1 = i; x1 < x; x1++) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 + 1;
                        int x2 = x1 - 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            downLeftUpRightBorderPoints++;
                        }
                        break;
                    }
                }
                y1--;
            }
        }
    }

    //upDown rightLeft
    public static void diagonal_2(Mat matRoi) {

        int y = matRoi.rows();
        int x = matRoi.cols();

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int d = 2; d <= x; d++) {

            int x1 = (d - 2);

            for (int y1 = 1; y1 < d; y1++) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 - 1;
                        int x2 = x1 + 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            upRightDownLeftBorderPoints++;
                        }
                        break;
                    }
                }
                x1--;
                if (x1 < 0) break;
            }
        }

        for (int d = 2; d <= y; d++) {

            int x1 = (x - 2);

            for (int y1 = d; y1 < y; y1++) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;

                int color1 = getColorAlias(rgb_String);
                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 - 1;
                        int x2 = x1 + 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            upRightDownLeftBorderPoints++;
                        }
                        break;
                    }
                }
                x1--;
            }
        }

    }

    // upDown leftRight
    public static void diagonal_3(Mat matRoi) {

        int y = matRoi.rows();
        int x = matRoi.cols();

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int st = 1; st < x; st++) {

            int x1 = st;

            for (int y1 = 1; y1 < y; y1++) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;
                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 - 1;
                        int x2 = x1 - 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            upLeftDownRightBorderPoints++;
                        }
                        break;
                    }
                }
                if (x1 == (x - 1)) break;
                x1++;
            }
        }

        for (int st = 2; st < y; st++) {

            int y1 = st;

            for (int x1 = 1; x1 < x; x1++) {

                if (y1 > (y - 1)) break;

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;
                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 - 1;
                        int x2 = x1 - 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            upLeftDownRightBorderPoints++;
                        }
                        break;
                    }
                }
                y1++;
            }
        }
    }

    //downUp rightLeft
    public static void diagonal_4(Mat matRoi) {

        int y = matRoi.rows();
        int x = matRoi.cols();

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int st = (y - 2); st >= 0; st--) {

            int y1 = st;

            for (int x1 = (x - 2); x1 >= 0; x1--) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;
                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 + 1;
                        int x2 = x1 + 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            downRightUpLeftBorderPoints++;
                        }
                        break;
                    }
                }
                y1--;
                if (y1 < 0) break;
            }
        }

        for (int st = (x - 3); st >= 0; st--) {

            int x1 = st;

            for (int y1 = (y - 2); y1 >= 0; y1--) {

                double[] colValue = matRoi.get(y1, x1);
                int r = (int) colValue[0];
                int g = (int) colValue[1];
                int b = (int) colValue[2];
                String rgb_String = r + ";" + g + ";" + b;
                int color1 = getColorAlias(rgb_String);

                for (int j = 0; j < allColors_AL.size(); j++) {

                    if ((int) allColors_AL.get(j).get(0).get(4) == color1) {

                        int y2 = y1 + 1;
                        int x2 = x1 + 1;

                        if (isBorderPointDiagonal(allColors_AL.get(j), y2, x2, y1, x1)) {

                            helpAl_forBorderPoint.add(y1);
                            helpAl_forBorderPoint.add(x1);

                            searchAddReqCoordinates(allColors_AL, y2, x2, helpAl_forBorderPoint);
                            searchAddReqCoordinates(allColors_AL, y1, x1, helpAl_forBorderPoint);
                            borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                            helpAl_forBorderPoint.clear();
                            downRightUpLeftBorderPoints++;
                        }
                        break;
                    }
                }
                x1--;
                if (x1 < 0) break;
            }
        }
    }

    public static boolean isBorderPointDiagonal(ArrayList<ArrayList> aL, int yPrev, int xPrev, int yCur, int xCur) {

        boolean isBorder = true;

        for (int i = 1; i < aL.size(); i++) {

            if (((int) aL.get(i).get(0) == yPrev) && ((int) aL.get(i).get(1) == xPrev)) {

                if (getColorSquare(aL, yCur, xCur) == (int) aL.get(i).get(2)) {
                    isBorder = false;

                }
                break;
            }
        }
        return isBorder;
    }

    public static int getColorSquare(ArrayList<ArrayList> aL, int y3, int x3) {

        int colorSquare = 0;

        for (int i = 1; i < aL.size(); i++) {

            if (((int) aL.get(i).get(0) == y3) && ((int) aL.get(i).get(1) == x3)) {

                colorSquare = (int) aL.get(i).get(2);
                break;
            }
        }
        return colorSquare;
    }

    public static boolean isBorderPointDownUp(ArrayList<ArrayList> aL, int y1, int x1) {

        boolean isBorder = true;

        for (int i = 1; i < aL.size(); i++) {

            if (((int) aL.get(i).get(0) == y1) && ((int) aL.get(i).get(1) == x1)) {
                isBorder = false;
                break;
            }
        }
        return isBorder;
    }

    public static void addColorsToBorderPoints(ArrayList<ArrayList<ArrayList>> arrayWithColors, ArrayList<ArrayList> arrayWithBorderPoints) {

        for (int i = 0; i < arrayWithBorderPoints.size(); i++) {

            int yC = (int) arrayWithBorderPoints.get(i).get(0);
            int xC = (int) arrayWithBorderPoints.get(i).get(1);

            searchAddFromCoordinates(arrayWithColors, yC, xC, arrayWithBorderPoints.get(i));
            searchAddReqCoordinates(arrayWithColors, yC, xC, arrayWithBorderPoints.get(i));
            //searchAddFromCoordinates(arrayWithColors, yC, xC, arrayWithBorderPoints.get(i));

        }

    }

    public static void searchAddFromCoordinates(ArrayList<ArrayList<ArrayList>> arrayWithColors, int y, int x, ArrayList<Integer> arrayWithBP) {

        for (int i = 0; i < arrayWithColors.size(); i++) {

            ArrayList<ArrayList> specificColor = arrayWithColors.get(i);

            int clr = (int) specificColor.get(0).get(4);

            for (int j = 1; j < specificColor.size(); j++) {

                if (((int) specificColor.get(j).get(0) == (y - 1)) && ((int) specificColor.get(j).get(1) == x)) {

                    int clrSq = (int) specificColor.get(j).get(2);

                    arrayWithBP.add(clr);
                    arrayWithBP.add(clrSq);

                    break;

                }
            }
        }
    }

    public static void searchAddReqCoordinates(ArrayList<ArrayList<ArrayList>> arrayWithColors, int y, int x, ArrayList<Integer> arrayWithBP) {

        for (int i = 0; i < arrayWithColors.size(); i++) {

            ArrayList<ArrayList> specificColor = arrayWithColors.get(i);

            int clr = (int) specificColor.get(0).get(4);

            for (int j = 1; j < specificColor.size(); j++) {

                if (((int) specificColor.get(j).get(0) == y) && ((int) specificColor.get(j).get(1) == x)) {

                    int clrSq = (int) specificColor.get(j).get(2);

                    arrayWithBP.add(clr);
                    arrayWithBP.add(clrSq);

                    break;

                }
            }
        }
    }

    public static void findRequiredArray(int colorNumber, String pointPosition, int y1, int x1) {

        ArrayList<Integer> helpAl_forBorderPoint = new ArrayList<>();

        for (int i = 0; i < allColors_AL.size(); i++) {
            // found required array and start work with it
            if ((int) allColors_AL.get(i).get(0).get(4) == colorNumber) {

                if (pointPosition.equals(USUAL_POINT)) {

                    if (isVerticalRelation(pointPosition, allColors_AL.get(i), y1, x1)) {


                        if (isHorizontalRelation(pointPosition, allColors_AL.get(i), y1, x1)) {
                            putUsualPointCoordinates(allColors_AL.get(i), USUAL_POINT, y1, x1, VERT_HOR_RLTN);
                        } else {
                            putUsualPointCoordinates(allColors_AL.get(i), USUAL_POINT, y1, x1, VERT_RLTN);
                        }
                    } else {
                        helpAl_forBorderPoint.add(y1);
                        helpAl_forBorderPoint.add(x1);
                        borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                        helpAl_forBorderPoint.clear();
                        upDownBorderPoints++;

                        if (isHorizontalRelation(pointPosition, allColors_AL.get(i), y1, x1)) {
                            putUsualPointCoordinates(allColors_AL.get(i), USUAL_POINT, y1, x1, HOR_RLTN);
                        } else {
                            putUsualPointCoordinates(allColors_AL.get(i), USUAL_POINT, y1, x1, NO_RLTN);
                        }
                    }
                }

                if (pointPosition.equals(START_POINT)) {
                    putCoordinates(allColors_AL.get(i), pointPosition, y1, x1);
                }

                if (pointPosition.equals(SEMI_USUAL_POINT_X_0)) {

                    if (isBorderPoint(SEMI_USUAL_POINT_X_0, allColors_AL.get(i), y1, x1)) {

                        putCoordinates(allColors_AL.get(i), SEMI_USUAL_POINT_X_0, y1, x1);

                        helpAl_forBorderPoint.add(y1);
                        helpAl_forBorderPoint.add(x1);
                        borderPoints_AL.add((ArrayList) helpAl_forBorderPoint.clone());
                        helpAl_forBorderPoint.clear();
                        upDownBorderPoints++;

                    } else {
                        putCoordinates(allColors_AL.get(i), SEMI_USUAL_POINT_X_0, y1, x1);
                    }
                }

                if (pointPosition.equals(SEMI_USUAL_POINT_Y_0)) {

                    if (isColorArrayContainPoint(allColors_AL.get(i), y1, x1)) {

                        putCoordinates(allColors_AL.get(i), SEMI_USUAL_POINT_Y_0, y1, x1);

                    } else {
                        putCoordinates(allColors_AL.get(i), SEMI_USUAL_POINT_Y_0, y1, x1);
                    }
                }
                break;
            }
        }
    }

    public static void putUsualPointCoordinates(ArrayList<ArrayList> aL, String pointPosition, int y5, int x5, String relationType) {

        ArrayList<Integer> help_AL = new ArrayList<>();
        int s = aL.size();

        if (s > 1) {

            if (relationType.equals(VERT_RLTN)) {
                help_AL.add(y5);
                help_AL.add(x5);
                help_AL.add(squareNumVrtcl);
                help_AL.add(0);
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();

            } else if (relationType.equals(HOR_RLTN)) {
                help_AL.add(y5);
                help_AL.add(x5);
                help_AL.add(squareNumHrzntl);
                help_AL.add(isFirst);
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();

            } else if (relationType.equals(NO_RLTN)) {

                squareNum = getMaxSquareNum(aL) + 1;
                help_AL.add(y5);
                help_AL.add(x5);
                help_AL.add(squareNum);
                help_AL.add(1);
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();

            } else if (relationType.equals(VERT_HOR_RLTN)) {

                if (squareNumHrzntl > squareNumVrtcl) {

                    squareNum = squareNumVrtcl;

                    changeSquareNum(squareNumHrzntl, squareNum, aL);

                } else if (squareNumHrzntl < squareNumVrtcl) {

                    squareNum = squareNumHrzntl;

                    changeSquareNum(squareNumVrtcl, squareNum, aL);

                } else if (squareNumHrzntl == squareNumVrtcl) {

                    squareNum = squareNumHrzntl;
                }
                help_AL.add(y5);
                help_AL.add(x5);
                help_AL.add(squareNum);
                help_AL.add(0);
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();
            }

        } else if (s == 1) {
            help_AL.add(y5);// add Y coordinate
            help_AL.add(x5);// add X coordinate
            help_AL.add(1);// startPoint, new color startColorPoint
            help_AL.add(1);// startPoint, startColorPoint
            aL.add((ArrayList) help_AL.clone());
            help_AL.clear();
        }

    }

    public static void changeSquareNum(int exSqNum, int reqSqNum, ArrayList<ArrayList> aL) {

        for (int i = 0; i < aL.size(); i++) {
            if ((int) aL.get(i).get(2) == exSqNum) {
                // int isF = (int) aL.get(i).get(3);
                aL.get(i).remove(3);// remove isFirst index
                aL.get(i).remove(2);// remove squarenNum index
                aL.get(i).add(reqSqNum);
                aL.get(i).add(0);
            }
        }
    }

    public static boolean isVerticalRelation(String pointPosition, ArrayList<ArrayList> aL, int y1, int x1) {

        boolean isRelated = false;
        int s = aL.size();

        if (s > 1) {
            if (pointPosition.equals(USUAL_POINT)) {
                // get color from upper previous point
                if (((int) aL.get(s - 1).get(0) == y1 - 1) && ((int) aL.get(s - 1).get(1) == x1)) {
                    squareNumVrtcl = (int) aL.get(s - 1).get(2);
                    isFirst = 0;
                    isRelated = true;

                } else {
                    squareNumVrtcl = getMaxSquareNum(aL) + 1;
                    isFirst = 1;
                }
            }
        }
        return isRelated;
    }

    public static boolean isHorizontalRelation(String pointPosition, ArrayList<ArrayList> aL, int y5, int x5) {

        boolean isRelated = false;

        int aLsize = aL.size();

        if (aLsize > 1) {

            for (int i = 1; i < aLsize; i++) {

                int _y = (int) aL.get(i).get(0);
                int _x = (int) aL.get(i).get(1);

                if (_y == y5 && _x == (x5 - 1)) {

                    isRelated = true;
                    squareNumHrzntl = (int) aL.get(i).get(2);
                    isFirst = 0;
                    break;

                } else {

                    squareNumHrzntl = getMaxSquareNum(aL) + 1;
                    isFirst = 1;

                }
            }
        }
        return isRelated;
    }

    public static boolean isColorArrayContainPoint(ArrayList<ArrayList> aL, int y4, int x4) {

        boolean contain = false;

        int aLsize = aL.size();

        if (aLsize > 1) {

            for (int i = 1; i < aLsize; i++) {

                int _y = (int) aL.get(i).get(0);
                int _x = (int) aL.get(i).get(1);

                if (_y == y4 && _x == (x4 - 1)) {

                    contain = true;
                    squareNum = (int) aL.get(i).get(2);
                    isFirst = 0;
                    break;

                } else {
                    contain = false;
                    squareNum = getMaxSquareNum(aL) + 1;
                    isFirst = 1;
                }
            }
        } else {
            contain = false;
            isFirst = 1;
            squareNum = 1;
        }
        return contain;
    }

    public static int getMaxSquareNum(ArrayList<ArrayList> aL) {

        int num = 0;

        for (int i = 1; i < aL.size(); i++) {

            if ((int) aL.get(i).get(2) > num) {
                num = (int) aL.get(i).get(2);
            }
        }
        return num;
    }

    public static void putCoordinates(ArrayList<ArrayList> aL, String pointPosition, int y2, int x2) {

        ArrayList<Integer> help_AL = new ArrayList<>();
        int s = aL.size();

        if (pointPosition.equals(START_POINT)) {

            help_AL.add(y2);// add Y coordinate
            help_AL.add(x2);// add X coordinate
            help_AL.add(1);// startPoint, new color startColorPoint
            help_AL.add(1);// startPoint, startColorPoint
            aL.add((ArrayList) help_AL.clone());
            help_AL.clear();
        }

        if (pointPosition.equals(SEMI_USUAL_POINT_X_0)) {

            if (s == 1) {
                help_AL.add(y2);// add Y coordinate
                help_AL.add(x2);// add X coordinate
                help_AL.add(1);// startPoint, new color startColorPoint
                help_AL.add(1);// startPoint, startColorPoint
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();

            } else {
                help_AL.add(y2);
                help_AL.add(x2);
                help_AL.add(squareNum);
                help_AL.add(isFirst);
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();
            }
        }

        if (pointPosition.equals(SEMI_USUAL_POINT_Y_0)) {
            if (s == 1) {
                help_AL.add(y2);// add Y coordinate
                help_AL.add(x2);// add X coordinate
                help_AL.add(1);// startPoint, new color startColorPoint
                help_AL.add(1);// startPoint, startColorPoint
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();

            } else {
                help_AL.add(y2);
                help_AL.add(x2);
                help_AL.add(squareNum);
                help_AL.add(isFirst);
                aL.add((ArrayList) help_AL.clone());
                help_AL.clear();
            }
        }
    }

    public static void showArrays() {
        System.out.println("Start to show");
        for (int i = 0; i < allColors_AL.size(); i++) {

            // for (int i = 4; i < 5; i++) {

            System.out.println(allColors_AL.get(i).size());
/*
           for (int c = 0; c < allColors_AL.get(i).size(); c++) {

            //    for (int c = 309; c < 625; c++) {

               // System.out.println(allColors_AL.get(i).size());

                for (int v = 0; v < allColors_AL.get(i).get(c).size(); v++) {

                    System.out.print(allColors_AL.get(i).get(c).get(v) + " ");

                }
                System.out.println();
            }*/
            System.out.println("******************");
        }
    }

    public static boolean isBorderPoint(String pointPosition, ArrayList<ArrayList> aL, int y1, int x1) {

        boolean isBorder = false;

        int s = aL.size();

        if (pointPosition.equals(SEMI_USUAL_POINT_X_0)) {
            // get color from upper previous point
            if ((int) aL.get(s - 1).get(0) == y1 - 1) {
                squareNum = (int) aL.get(s - 1).get(2);
                isBorder = false;
                isFirst = 0;
            }
            if ((int) aL.get(s - 1).get(0) != y1 - 1) {
                squareNum = (int) aL.get(s - 1).get(2) + 1;
                isBorder = true;
                isFirst = 1;
            }
        }
        return isBorder;
    }

    public static int getColorAlias(String clrStringValue) {
        int clrAlias = -1;
        for (int i = 0; i < colorDefiningAl.size(); i++) {
            if (colorDefiningAl.get(i).get(1).equals(clrStringValue)) {
                clrAlias = Integer.valueOf((String) colorDefiningAl.get(i).get(0));
                break;
            }
        }
        return clrAlias;
    }

    public static void findNumberOfLines() {
        System.out.println("Started");


        for (int i = 0; i < borderPoints_AL.size(); i++) {

            ArrayList<Integer> aL_aL = borderPoints_AL.get(i);
            String s_fVal = "";
            // String val = "";
            //   int i_fVal = null;
            for (int j = 2; j < aL_aL.size(); j++) {

                String val = String.valueOf(aL_aL.get(j));

                s_fVal = s_fVal + val;


            }
            linesDscrptn.add(s_fVal);
        }
    }




    public static void showBorderPointsArrays() {

        System.out.println("borderPoints_AL " +borderPoints_AL.size());

/*       for (int i = 770; i < borderPoints_AL.size(); i++) {

            ArrayList<Integer> aL_aL = borderPoints_AL.get(i);
           System.out.print(i + "  ");
            for (int j = 0; j < aL_aL.size(); j++) {

                System.out.print(aL_aL.get(j) + "  ");
            }
            System.out.println();
        }

     System.out.println("------------");
   /*     System.out.println("upDownBorderPoints");
        System.out.println(upDownBorderPoints);
        System.out.println("downUpBorderPoints");
        System.out.println(downUpBorderPoints);
        System.out.println("leftRightBorderPoints");
        System.out.println(leftRightBorderPoints);
        System.out.println("rightLeftBorderPoints");
        System.out.println(rightLeftBorderPoints);
        System.out.println("downLeftUpRightBorderPoints");
        System.out.println(downLeftUpRightBorderPoints);
       System.out.println("upRightDownLeftBorderPoints");
        System.out.println(upRightDownLeftBorderPoints);
        System.out.println("upLeftDownRightBorderPoints");
        System.out.println(upLeftDownRightBorderPoints);
        System.out.println("downRightUpLeftBorderPoints");
        System.out.println(downRightUpLeftBorderPoints);
*/


    }

    private static void calculateMaxMinValues(Mat img) {

        ArrayList<Integer> a = new ArrayList<Integer>();
        ArrayList<Integer> b = new ArrayList<Integer>();

        Mat labImg = new Mat();
        Imgproc.cvtColor(img, labImg, Imgproc.COLOR_BGR2Lab);

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val = labImg.get(800 + c, i);

                int vIntense = (int) val[0];
                int vSat = (int) val[1];
                int vValue = (int) val[2];

                a.add(vSat);
                a.add(vValue);
                b.add(vIntense);
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val2 = labImg.get(800 + c, 550 + i);

                int vIntense2 = (int) val2[0];
                int vSat2 = (int) val2[1];
                int vValue2 = (int) val2[2];

                a.add(vSat2);
                a.add(vValue2);
                b.add(vIntense2);
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val3 = labImg.get(c, 550 + i);

                int vIntense3 = (int) val3[0];
                int vSat3 = (int) val3[1];
                int vValue3 = (int) val3[2];

                a.add(vSat3);
                a.add(vValue3);
                b.add(vIntense3);
            }
        }

        for (int i = 0; i < 200; i++) {
            for (int c = 0; c < 200; c++) {

                double[] val4 = labImg.get(c, i);

                int vIntense4 = (int) val4[0];
                int vSat4 = (int) val4[1];
                int vValue4 = (int) val4[2];

                a.add(vSat4);
                a.add(vValue4);
                b.add(vIntense4);
            }
        }

        maxA = a.get(0);
        minA = a.get(0);

        for (int y : a) {
            if (y >= maxA) {
                maxA = y;
            }
            if (y <= minA) {
                minA = y;
            }
        }

        maxIntense = b.get(0);
        minIntense = b.get(0);

        for (int y : b) {
            if (y >= maxIntense) {
                maxIntense = y;
            }
            if (y <= minIntense) {
                minIntense = y;
            }
        }


        System.out.println("MinA");
        System.out.println(minA);
        System.out.println("MaxA");
        System.out.println(maxA);
        System.out.println("MaxIntense");
        System.out.println(maxIntense);
        System.out.println("MinIntense");
        System.out.println(minIntense);
    }

    public static void findNeedValues(int min, int max) {

        if ((max - min) < 42) {
            do {
                min = (min == 0 ? 0 : min - 1);
                rMin = min;
                max = (max == 255 ? 255 : max + 1);
                rMax = max;
            } while ((rMax - rMin) < 44);
        } else {
            rMin = min;
            rMax = max;
        }
        System.out.println("rMinA");
        System.out.println(rMin);
        System.out.println("rMaxA");
        System.out.println(rMax);
    }

    public static void checkPreconditions(Mat mat1) {

        if (mDone == 0) {

            calculateMaxMinValues(mat1);
            findNeedValues(minA, maxA);

            for (int i = rMin; i < rMax; i++) {
                all_A_Values.add(i);
                all_B_Values.add(i);
            }
            // set '165' instead on 'minIntense' for find Olga on photo
            // if 157-197 is set - reason (minIntense+maxIntense)/2 - deviation 40
            for (int i = minIntense; i < maxIntense; i++) {
                all_C_Values.add(i);
            }
            mDone = 1;
        }
    }

    public static void checkPreconditions(int vRmin, int vRmax, int vMinIntense, int vMaxIntense) {

        all_A_Values.clear();
        all_B_Values.clear();
        all_C_Values.clear();

        for (int i = vRmin; i < vRmax; i++) {
            all_A_Values.add(i);
            all_B_Values.add(i);
        }
        for (int i = vMinIntense; i < vMaxIntense; i++) {
            all_C_Values.add(i);
        }

        System.out.println(all_A_Values.size());
        System.out.println(all_B_Values.size());
        System.out.println(all_C_Values.size());
    }

    public static void calculate(Mat mat1) {

        checkPreconditions(mat1);

        Imgproc.cvtColor(mat1, mat1, Imgproc.COLOR_BGR2Lab);

        for (int i = 0; i < mat1.cols(); i++) {
            for (int y = 0; y < mat1.rows(); y++) {
                double[] joy = mat1.get(y, i);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if ((all_A_Values.contains(aVal)) && (all_B_Values.contains(bVal) && (all_C_Values.contains(cVal)))) {
                    x_Cor.add(i);
                    y_Cor.add(y);
                    String stringX = String.valueOf(i);
                    String stringY = String.valueOf(y);
                    String xy_String = stringX + stringY;
                    //  xy_Cor_string.add(Integer.valueOf(xy_String));
                }
            }
        }
        double[] joy2 = mat1.get(999, 749);
        int cVal2 = (int) joy2[0];
        int aVal2 = (int) joy2[1];
        int bVal2 = (int) joy2[2];
        System.out.println("EX:");
        System.out.println(cVal2);
        System.out.println(aVal2);
        System.out.println(bVal2);
        System.out.println(mat1.cols());
        System.out.println(mat1.rows());

        System.out.println("y-Cor: " + y_Cor.size());
        System.out.println("xy_String: " + xy_Cor_string.size());
        Imgproc.cvtColor(mat1, mat1, Imgproc.COLOR_Lab2BGR);
    }

    public static void leftRightStage(Mat mat2) {

        checkPreconditions(mat2);

        System.out.println("leftRightStage start");
        System.out.println("all_A_Values size: " + all_A_Values.size());
        System.out.println("all_C_Values size: " + all_C_Values.size());

        Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_BGR2Lab);

        int sMark = 0;
        for (int y = 0; y < mat2.rows(); y++) {
            for (int x = 0; x < mat2.cols(); x++) {
                double[] joy = mat2.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark = 1;
                } else {
                    break;
                }

            }
        }
        Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_Lab2BGR);
        System.out.println("leftRightStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void upDownStage(Mat mat3) {

        checkPreconditions(mat3);

        System.out.println("all_A_Values size: " + all_A_Values.size());
        System.out.println("upDownStage start");
        System.out.println("all_C_Values size: " + all_C_Values.size());

        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_BGR2Lab);

        for (int x = 0; x < mat3.cols(); x++) {
            for (int y = 0; y < mat3.rows(); y++) {
                double[] joy = mat3.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else {
                    break;
                }
            }
        }
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_Lab2BGR);
        System.out.println("upDownStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void upDownStageROI(Mat mat3) {
        // no checkPreconditions() - is applied before first-time image is displayed.
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_BGR2Lab);
        int sMark_3 = 0;
        for (int x = 0; x < mat3.cols(); x++) {
            for (int y = 0; y < mat3.rows(); y++) {
                double[] joy = mat3.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark_3 = 1;
                } else {
                    break;
                }
            }
        }
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_Lab2BGR);
        System.out.println("upDownROIStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void downUpStageROI(Mat mat3) {
        // no checkPreconditions() - is applied before first-time image is displayed.
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_BGR2Lab);
        int sMark_3 = 0;

        for (int x = 0; x < mat3.cols(); x++) {
            for (int y = (mat3.rows() - 1); y >= 0; y--) {
                double[] joy = mat3.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark_3 = 1;
                } else {
                    break;
                }
            }
        }
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_Lab2BGR);
        System.out.println("downUpStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void rightLeftStageROI(Mat mat3) {

        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_BGR2Lab);
        int sMark_2 = 0;

        for (int y = 0; y < mat3.rows(); y++) {
            for (int x = (mat3.cols() - 1); x >= 0; x--) {
                double[] joy = mat3.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark_2 = 1;
                } else {
                    break;
                }
            }
        }
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_Lab2BGR);
        System.out.println("rightLeftStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void leftRightStageROI(Mat mat2) {

        Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_BGR2Lab);

        int sMark = 0;
        for (int y = 0; y < mat2.rows(); y++) {
            for (int x = 0; x < mat2.cols(); x++) {
                double[] joy = mat2.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark = 1;
                } else {
                    break;
                }

            }
        }
        Imgproc.cvtColor(mat2, mat2, Imgproc.COLOR_Lab2BGR);
        System.out.println("leftRightStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void downUpStage(Mat mat3) {

        checkPreconditions(mat3);

        System.out.println("upDownStage start");
        System.out.println("all_A_Values size: " + all_A_Values.size());
        System.out.println("all_C_Values size: " + all_C_Values.size());

        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_BGR2Lab);
        int sMark_3 = 0;

        for (int x = 0; x < mat3.cols(); x++) {
            for (int y = (mat3.rows() - 1); y >= 0; y--) {
                double[] joy = mat3.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark_3 = 1;
                } else {
                    break;
                }
            }
        }
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_Lab2BGR);
        System.out.println("downUpStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }

    public static void getClustersFromHsv(Mat mat) {


    }


    public static void rightLeftStage(Mat mat3) {
        checkPreconditions(mat3);
        System.out.println("upDownStage start");
        System.out.println("all_A_Values size: " + all_A_Values.size());
        System.out.println("all_C_Values size: " + all_C_Values.size());
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_BGR2Lab);
        int sMark_2 = 0;

        for (int y = 0; y < mat3.rows(); y++) {
            for (int x = (mat3.cols() - 1); x >= 0; x--) {
                double[] joy = mat3.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark_2 = 1;
                } else {
                    break;
                }


                /*
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                    sMark_2 = 1;

                } else if ((aVal == 128 && bVal == 128 && cVal == 255 && sMark_2 == 1)) {
                    sMark_2 = 0;
                    break;

                } else if (!all_A_Values.contains(aVal) && !all_B_Values.contains(bVal) && !all_C_Values.contains(cVal) && sMark_2 == 1) {
                    sMark_2 = 0;
                    break;
                }

                */
            }
        }
        Imgproc.cvtColor(mat3, mat3, Imgproc.COLOR_Lab2BGR);
        System.out.println("rightLeftStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
    }
}

