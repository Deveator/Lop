package com.example.andrey.lop.ImageActions;

import android.content.Context;

import com.example.andrey.lop.R;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import static com.example.andrey.lop.MainActivity.path;

public class FaceDetect {

    // public static Mat getFaceDetect(Mat mImg, CascadeClassifier cC, CascadeClassifier eC) {
    public static Mat getFaceDetect(Mat mImg, CascadeClassifier cC) {

        Mat img = mImg.clone();
        Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(mImg, mImg);

        // -- Detect faces
        MatOfRect faces = new MatOfRect();

        cC.detectMultiScale(mImg, faces);

        List<Rect> listOfFaces = faces.toList();

        System.out.println(listOfFaces.size());
        System.out.println(listOfFaces.get(0));

        for (Rect face : listOfFaces) {
            ///  Point center = new Point(face.x + face.width / 2, face.y + face.height / 2);
            ///  Imgproc.ellipse(img, center, new Size(face.width / 2, face.height / 2), 0, 0, 360,
            ///          new Scalar(255, 0, 255));

            ///  Imgproc.rectangle(img, new Point(face.x, face.y), new Point(face.x + face.width, face.y + face.height), new Scalar(155, 0, 240), 3);

            Mat faceROI = mImg.submat(face);

            for (int i = 0; i < 121; i++) {
                for (int y = 0; y < 121; y++) {
                    double ft[] = faceROI.get(i, y);
                    img.put(98 + i, 44 + y, ft[0], ft[0], ft[0]);
                }
            }

/*
            MatOfRect eyes = new MatOfRect();
            eC.detectMultiScale(faceROI, eyes);
            List<Rect> listOfEyes = eyes.toList();
            for (Rect eye : listOfEyes) {
                Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                int radius = (int) Math.round((eye.width + eye.height) * 0.25);
                Imgproc.circle(img, eyeCenter, radius, new Scalar(255, 0, 0), 4);
            }
*/
        }

        return img;
    }
}
