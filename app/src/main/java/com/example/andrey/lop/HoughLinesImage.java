package com.example.andrey.lop;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static java.lang.Math.PI;

public class HoughLinesImage {

    // method to image in gray color with Hough lines
    public static Mat GetHoughLinesImage(Mat mImg){

        Mat rgbImg = new Mat();
        Mat cannyEdges = new Mat();
        Mat hLImage = new Mat();
        Mat linesImage = new Mat();
        Size vSize = new Size(21, 21);
        // convert to gray color
        Imgproc.cvtColor(mImg, rgbImg, Imgproc.COLOR_BGR2GRAY);
        // convert to "Canny" image
        // min_threshould & max_threshold set threshold to find better edges or filter found edges via threshold with help of pixels values
        // if pixel value more then max_threshold  - pixel is marked as strong
        // if pixel value lower then max_threshold &  more then min_threshold - pixel is marked as weak
        // if pixel value lower then min_threshold  - pixel is suppressed
        Imgproc.Canny(rgbImg, cannyEdges, 80, 255);
        // threshold - min number of votes to set
        //theta angle
        // linesImage need to set lines coordinates
        Imgproc.HoughLinesP(cannyEdges, linesImage, 1, PI / 2, 40, 20, 20);
        hLImage.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);
       // Dx1 = hLImage.cols();
       // Dx2 = hLImage.rows();

        //Drawing lines on the image
        for (int x = 0; x < linesImage.rows(); x++) {
            double[] fer = linesImage.get(x, 0);

            double x1 = fer[0],
                    y1 = fer[1],
                    x2 = fer[2],
                    y2 = fer[3];

            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            Imgproc.line(hLImage, start, end, new Scalar(255, 0, 0), 1);
        }

       // houghImage = hLImage;
        return hLImage;

/*
        for (int i = 0; i < linesImage.cols(); i++) {

            double[] points = linesImage.get(0, i);
            double x1, y1, x2, y2;

            x1 = points[0];
            y1 = points[1];
            x2 = points[2];
            y2 = points[3];

            Point pt1 = new Point(x1, y1);
            Point pt2 = new Point(x2, y2);

            //Drawing lines on an image
            Imgproc.line(hLImage, pt1, pt2, new Scalar(255, 0, 0), 1);
        }
*/

        //  double dx = x1 - x2;
        // double dy = y1 - y2;
        // double dist = Math.sqrt(dx * dx + dy * dy);
        // Dx1 = linesImage.cols();
        // Dx2 = linesImage.rows();;
        //y1 = y1;
        //y2 = y2;
        // = fer.length;


    }
}
