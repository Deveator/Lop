package com.example.andrey.lop.ImageActions;

import android.util.Log;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.opencv.core.CvType.CV_32SC2;

public class FindContourImage {

    public static ArrayList<Integer> xCorC = new ArrayList<Integer>();
    public static ArrayList<Integer> yCorC = new ArrayList<Integer>();

    private static Random rng = new Random(12345);

    public static List<MatOfPoint> contours2 = new ArrayList<>();
    public static List<MatOfPoint> ppt2 = new ArrayList<MatOfPoint>();

    public static Mat getContourImg(Mat mImg) {

        Mat resultImg = new Mat();


        Imgproc.cvtColor(mImg, resultImg, Imgproc.COLOR_BGR2GRAY);
        // Imgproc.equalizeHist( resultImg, resultImg );
        //  System.out.println(resultImg.cols());
        //  System.out.println(resultImg.rows());


       //    Imgproc.blur(resultImg, resultImg, new Size(2, 2));


        Imgproc.Canny(resultImg, resultImg, 100, 100 * 2);

        ///  List<MatOfPoint> contours = new ArrayList<>();

        List<Integer> nums = new ArrayList<>();

        // create Optional output vector (e.g. std::vector<cv::Vec4i>),
        // containing information about the image topology.
        // It has as many elements as the number of contours.
        // For each i-th contour contours[i], the elements hierarchy[i][0] , hierarchy[i][1] , hierarchy[i][2] , and hierarchy[i][3]
        // are set to 0-based indices in contours of the next and previous contours at the same hierarchical level,
        // the first child contour and the parent contour, respectively.
        // If for the contour i there are no next, previous, parent, or nested contours, the corresponding elements of hierarchy[i] will be negative.
        Mat hierarchy = new Mat();

        // contours - Detected contours. Each contour is stored as a vector of points (e.g. std::vector<std::vector<cv::Point> >).
        // Imgproc.RETR_TREE - Contour retrieval mode
        // 	Imgproc.CHAIN_APPROX_SIMPLE - Contour approximation method
        Imgproc.findContours(resultImg, contours2, hierarchy, Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_NONE );

        Mat tr = new Mat();

        // contours2.get(0).convertTo(tr, CV_32SC2);
        // System.out.println(contours2.size());
        //  int h = tr.rows();// 1252
        // System.out.println(99999999);
        // System.out.println(h);
/*
        for (int i = 0; i < contours2.size(); i++) {
            contours2.get(i).convertTo(tr, CV_32SC2);
            System.out.println("||" + i);
            System.out.println(tr.rows());
        }
        */
/*
        for (int k = 0; k < h; k++) {
            double[] g = tr.get(k, 0);
            int fx = (int) g[0];
            int fy = (int) g[1];
          //   System.out.println(k);
          //  System.out.println(fx + " - " + fy);
            xCorC.add(fx);
            yCorC.add(fy);
        }

        System.out.println(xCorC.size());
        System.out.println(yCorC.size());
        System.out.println(99555599);
       // System.out.println(xCorC.get(246));
      //  System.out.println(yCorC.get(246));
       // System.out.println(xCorC.get(247));
      //  System.out.println(yCorC.get(247));
*/
/*
        for (int u = 0; u < 17; u++) {
            contours.get(u).convertTo(tr, CV_32SC2);
            int y1 = tr.cols();
            int h = tr.rows();
            System.out.println(y1 + "cols" + h + "rows");
        }
*/
        // Log.e("INFO", y + "cols" + h + "rows");

        // Log.e("INFO", "size " + contours.size());

        Mat drawing = Mat.zeros(resultImg.size(), CvType.CV_8UC3);


        // Mat t = new Mat();
        for (int i = 0; i < contours2.size(); i++) {
            //  Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));

            contours2.get(i).convertTo(tr, CV_32SC2);

            int y = tr.rows();
            //System.out.println(i + " | " + y);
            nums.add(y);
            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
            Imgproc.drawContours(drawing, contours2, i, color, -1, Core.LINE_8, hierarchy, 0, new Point());
            System.out.println("#" + i);
            System.out.println(color);
            // Imgproc.drawContours(drawing, contours, i, color, 2, Core.LINE_8, hierarchy, 0, new Point());
        }

        // System.out.println(color);

        int maxV = 0;
        int or = 0;

        for (int p = 0; p < nums.size(); p++) {
            if (nums.get(p) > maxV) {
                maxV = nums.get(p);
                or = p;

            }
        }

        // Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
        // Imgproc.drawContours(drawing, contours, or, color, 2, Core.LINE_8, hierarchy, 0, new Point());


        return drawing;
    }
}
