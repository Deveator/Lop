package com.example.andrey.lop.ImageActions;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConvexHallImg {

    private static Random rng = new Random(12345);

    public static Mat getConvexHallImg (Mat mImg){

        Mat resultImg = new Mat();

        Imgproc.cvtColor(mImg, resultImg, Imgproc.COLOR_BGR2GRAY);
        Imgproc.blur(resultImg, resultImg, new Size(3, 3));


        Imgproc.Canny(resultImg, resultImg, 100, 100 * 2);

        List<MatOfPoint> contours = new ArrayList<>();

        Mat hierarchy = new Mat();

        Imgproc.findContours(resultImg, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        List<MatOfPoint> hullList = new ArrayList<>();

        for (MatOfPoint contour : contours) {

            MatOfInt hull = new MatOfInt();

            // Finds the convex hull of a point set.
            // cotour - 	Input 2D point set, stored in std::vector or Mat.
            // hull - Output convex hull.
            // It is either an integer vector of indices or vector of points.
            // In the first case, the hull elements are 0-based indices of the convex hull points in the original array (since the set of convex hull points is a subset of the original point set). In the second case, hull elements are the convex hull points themselves.
            Imgproc.convexHull(contour, hull);

            Point[] contourArray = contour.toArray();

            Point[] hullPoints = new Point[hull.rows()];

            List<Integer> hullContourIdxList = hull.toList();

            for (int i = 0; i < hullContourIdxList.size(); i++) {
                hullPoints[i] = contourArray[hullContourIdxList.get(i)];
            }
            hullList.add(new MatOfPoint(hullPoints));
        }

        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);
        for (int i = 0; i < contours.size(); i++) {
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(drawing, contours, i, color);
            Imgproc.drawContours(drawing, hullList, i, color );
        }


        return drawing;

    }
}
