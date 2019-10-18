package com.example.andrey.lop;

import com.example.andrey.lop.CustomView.DrawRect;
import com.example.andrey.lop.ImageActions.ErodeImage;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;

import static com.example.andrey.lop.CustomView.DrawRect.xGreen;
import static com.example.andrey.lop.CustomView.DrawRect.xOrg;
import static com.example.andrey.lop.CustomView.DrawRect.xRed;
import static com.example.andrey.lop.CustomView.DrawRect.xYell;
import static com.example.andrey.lop.CustomView.DrawRect.yGreen;
import static com.example.andrey.lop.CustomView.DrawRect.yOrg;
import static com.example.andrey.lop.CustomView.DrawRect.yRed;
import static com.example.andrey.lop.CustomView.DrawRect.yYell;
import static com.example.andrey.lop.ImageActions.LabClass.checkPreconditions;
import static com.example.andrey.lop.ImageActions.LabClass.downUpStageROI;
import static com.example.andrey.lop.ImageActions.LabClass.leftRightStageROI;
import static com.example.andrey.lop.ImageActions.LabClass.rightLeftStageROI;
import static com.example.andrey.lop.ImageActions.LabClass.upDownStageROI;
import static com.example.andrey.lop.MainActivity.changeColorFromROI;
import static com.example.andrey.lop.MainActivity.full_x_ROIcoord;
import static com.example.andrey.lop.MainActivity.full_y_ROIcoord;
import static com.example.andrey.lop.MainActivity.mFullRoiXy;

public class ChangeValues {

    public static Mat mUpDownChangeROI(Mat oMat, int vAMin, int vAMax, int vInMin, int vInMax) {

        ///  DrawRect.getCoord();

        mFullRoiXy();

        System.out.println("Red " + xRed + "::" + yRed);
        System.out.println("Orange " + xOrg + "::" + yOrg);
        System.out.println("Yellow " + xYell + "::" + yYell);
        System.out.println("Green " + xGreen + "::" + yGreen);

        Mat sMat = oMat.submat(yRed, yGreen, xRed, xOrg);



        checkPreconditions(vAMin, vAMax, vInMin, vInMax);

        upDownStageROI(sMat);

        changeColorFromROI(oMat);
        /*
        // need to clear
        full_x_ROIcoord.clear();
        full_y_ROIcoord.clear();

*/
        return oMat;
    }

    public static Mat mDownUpChangeROI(Mat oMat, int vAMin, int vAMax, int vInMin, int vInMax) {

        DrawRect.getCoord();

        mFullRoiXy();

        Mat sMat = oMat.submat(yRed, yGreen, xRed, xOrg);

        checkPreconditions(vAMin, vAMax, vInMin, vInMax);

        downUpStageROI(sMat);

        changeColorFromROI(oMat);
        // need to clear
        full_x_ROIcoord.clear();
        full_y_ROIcoord.clear();

        return oMat;
    }

    public static Mat mLeftRightChangeROI(Mat oMat, int vAMin, int vAMax, int vInMin, int vInMax) {

        DrawRect.getCoord();

        mFullRoiXy();

        Mat sMat = oMat.submat(yRed, yGreen, xRed, xOrg);

        checkPreconditions(vAMin, vAMax, vInMin, vInMax);

        leftRightStageROI(sMat);

        changeColorFromROI(oMat);
        // need to clear
        full_x_ROIcoord.clear();
        full_y_ROIcoord.clear();

        return oMat;
    }

    public static Mat mRightLeftChangeROI(Mat oMat, int vAMin, int vAMax, int vInMin, int vInMax) {

        DrawRect.getCoord();

        mFullRoiXy();

        Mat sMat = oMat.submat(yRed, yGreen, xRed, xOrg);

        checkPreconditions(vAMin, vAMax, vInMin, vInMax);

        rightLeftStageROI(sMat);

        changeColorFromROI(oMat);
        // need to clear
        full_x_ROIcoord.clear();
        full_y_ROIcoord.clear();

        return oMat;
    }

    // 2 Mat parameters - working and displaying
    public static Mat mChangeToCanny(Mat oMat, Mat oMat2, int tr_1, int tr_2) {

        DrawRect.getCoord();

        Mat sMat = oMat2.submat(yRed, yGreen, xRed, xOrg);

        Imgproc.cvtColor(sMat, sMat, Imgproc.COLOR_BGR2GRAY);

        Imgproc.Canny(sMat, sMat, tr_1, tr_2);

        ArrayList<Double> subMatValue = new ArrayList<>();

        for (int x = 0; x < sMat.cols(); x++) {
            for (int y = 0; y < sMat.rows(); y++) {
                double[] ft2 = sMat.get(y, x);
                subMatValue.add(ft2[0]);
            }
        }

        int count = 0;
        for (int x = xRed; x < xYell; x++) {
            for (int y = yRed; y < yYell; y++) {
                if (subMatValue.get(count) == 255.0) {
                    double[] dVal = {255.0, 0.0, 0.0};
                    oMat.put(y, x, dVal);
                }
                count++;
            }
        }
        return oMat;
    }
}
