package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.List;

public class FullBody {

    public static Mat getFullBody(Mat mImg, CascadeClassifier cC) {

        Mat img = mImg.clone();
        Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mImg, mImg);

        // -- Detect faces
        MatOfRect bodies = new MatOfRect();

        cC.detectMultiScale(mImg, bodies);


        List<Rect> listOfBodies = bodies.toList();

        if (listOfBodies.size() > 0) {

            for (Rect body : listOfBodies) {

                Imgproc.rectangle(img, new Point(body.x, body.y), new Point(body.x + body.width, body.y + body.height), new Scalar(155, 0, 240), 3);

                Mat faceROI = mImg.submat(body);
            }
            return img;

        } else {

            return mImg;
        }
    }
}
