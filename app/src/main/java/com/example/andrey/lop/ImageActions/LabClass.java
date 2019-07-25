package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

public class LabClass {

    public static ArrayList<Integer> x_Cor = new ArrayList<Integer>();
    public static ArrayList<Integer> y_Cor = new ArrayList<Integer>();
    public static ArrayList<String> xy_Cor_string = new ArrayList<String>();
    public static ArrayList<Integer> all_A_Values = new ArrayList<Integer>();
    public static ArrayList<Integer> all_B_Values = new ArrayList<Integer>();
    public static ArrayList<Integer> all_C_Values = new ArrayList<Integer>();
    public static int mDone = 0;

    public static int maxA, minA, rMin, rMax, minIntense, maxIntense;


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

        System.out.println("upDownStage start");
        System.out.println("all_A_Values size: " + all_A_Values.size());
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

