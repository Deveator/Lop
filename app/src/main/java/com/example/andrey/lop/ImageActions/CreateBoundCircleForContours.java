package com.example.andrey.lop.ImageActions;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateBoundCircleForContours {

    private static Random rng = new Random(12345);

    public static Mat getBndrsCircleForCntrs(Mat mImg) {

        Mat resultImg = new Mat();

        Imgproc.cvtColor(mImg,resultImg,Imgproc.COLOR_BGR2GRAY);

        Imgproc.blur(resultImg,resultImg,new Size(3,3));

        Imgproc.Canny(resultImg, resultImg, 100, 200 );

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();

        List<MatOfPoint> contours2 = new ArrayList<>();
        Mat hierarchy2 = new Mat();
        Imgproc.findContours(resultImg, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Imgproc.findContours(resultImg, contours2, hierarchy2, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);



        RotatedRect[] minRect = new RotatedRect[contours2.size()];
        RotatedRect[] minEllipse = new RotatedRect[contours2.size()];
        for (int i = 0; i < contours2.size(); i++) {
            minRect[i] = Imgproc.minAreaRect(new MatOfPoint2f(contours2.get(i).toArray()));
            minEllipse[i] = new RotatedRect();
            if (contours2.get(i).rows() > 5) {
                minEllipse[i] = Imgproc.fitEllipse(new MatOfPoint2f(contours2.get(i).toArray()));
            }
        }

        MatOfPoint2f[] contoursPoly  = new MatOfPoint2f[contours.size()];

        Rect[] boundRect = new Rect[contours.size()];

        Point[] centers = new Point[contours.size()];

        float[][] radius = new float[contours.size()][1];

        for (int i = 0; i < contours.size(); i++) {
            contoursPoly[i] = new MatOfPoint2f();
            // Approximates a polygonal curve(s) with the specified precision.
            // The function cv::approxPolyDP approximates a curve or a polygon with another curve/polygon with less vertices so that the distance between them is less or equal to the specified precision
            // "new MatOfPoint2f(contours.get(i).toArray())" - 	Input vector of a 2D point stored in std::vector or Mat
            // " contoursPoly[i]"  - Result of the approximation. The type should match the type of the input curve.
            // "3" - Parameter specifying the approximation accuracy. This is the maximum distance between the original curve and its approximation
            // "true" - If true, the approximated curve is closed (its first and last vertices are connected). Otherwise, it is not closed.
            Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), contoursPoly[i], 3, true);

            // The function calculates and returns the minimal up-right bounding rectangle for the specified point set or non-zero pixels of gray-scale image.
            boundRect[i] = Imgproc.boundingRect(new MatOfPoint(contoursPoly[i].toArray()));
            centers[i] = new Point();
            // The function finds the minimal enclosing circle of a 2D point set using an iterative algorithm.
            Imgproc.minEnclosingCircle(contoursPoly[i], centers[i], radius[i]);
        }

        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);

        List<MatOfPoint> contoursPolyList = new ArrayList<>(contoursPoly.length);
        for (MatOfPoint2f poly : contoursPoly) {
            contoursPolyList.add(new MatOfPoint(poly.toArray()));
        }
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(drawing, contoursPolyList, i, color);
            Imgproc.rectangle(drawing, boundRect[i].tl(), boundRect[i].br(), color, 2);
            Imgproc.circle(drawing, centers[i], (int) radius[i][0], color, 2);
        }

        for (int i = 0; i < contours2.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            // contour
            Imgproc.drawContours(drawing, contours2, i, color);
            // ellipse
            Imgproc.ellipse(drawing, minEllipse[i], color, 2);
            // rotated rectangle
            Point[] rectPoints = new Point[4];
            minRect[i].points(rectPoints);
            for (int j = 0; j < 4; j++) {
                Imgproc.line(drawing, rectPoints[j], rectPoints[(j+1) % 4], color);
            }
        }
        return drawing;
    }
}
