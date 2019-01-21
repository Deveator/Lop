package com.example.andrey.lop.ImageActions;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class HoughCircle {

    public  static Mat GetHoughCircle(Mat mImg){

        Mat img = new Mat();
        Mat circleMatImg = new Mat();
        Mat circlatImg = new Mat();
        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(img, img, new Size(11, 11), 5);
        circlatImg.create(img.rows(), img.cols(), CvType.CV_8UC1);
        // minDist - Minimum distance between the centers of the detected circles
        // param1 - like in Canny - threshold1 = param1 / 2, threshold2 = param1
        // param2 - threshold for the circle center !!!The smaller it is, the more false circles may be detected. Circles, corresponding to the larger accumulator values, will be returned first!!!
        // minRadius, maxRadius - Maximum circle radius. If <= 0, uses the maximum image dimension. If < 0, returns centers without finding the radius
        Imgproc.HoughCircles(img, circleMatImg, Imgproc.CV_HOUGH_GRADIENT, 1, img.rows() - 5, 10, 10, 50, 200);
        int radius;
        Point pt;

        for (int x = 0; x < circleMatImg.cols(); x++) {
            double vCircle[] = circleMatImg.get(0, x);

            if (vCircle == null)
                break;

            pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
            radius = (int) Math.round(vCircle[2]);

            // draw the found circle
            Imgproc.circle(circlatImg, pt, radius, new Scalar(255, 0, 0), 3);
            Imgproc.circle(circlatImg, pt, 3, new Scalar(255, 0, 0), 3);
        }
      //  houghCirculeImage = circlatImg;
        return circlatImg;

    }
}
