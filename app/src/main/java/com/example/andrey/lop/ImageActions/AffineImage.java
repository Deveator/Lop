package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

public class AffineImage {

    public static Mat getAffinedImage(Mat mImg) {

        Mat img = new Mat();

        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);

        Point[] srcTri = new Point[3];
        srcTri[0] = new Point(0, 0);
        srcTri[1] = new Point(img.cols() - 1, 0);
        srcTri[2] = new Point(0, img.rows() - 1);

        Point[] dstTri = new Point[3];
        dstTri[0] = new Point(0, img.rows() * 0.33);
        dstTri[1] = new Point(img.cols() * 0.85, img.rows() * 0.25);
        dstTri[2] = new Point(img.cols() * 0.15, img.rows() * 0.7);
        // Armed with both sets of points, we calculate the Affine Transform(transformation matrix.) by using OpenCV function cv::getAffineTransform
        Mat warpMat = Imgproc.getAffineTransform(new MatOfPoint2f(srcTri), new MatOfPoint2f(dstTri));
        Mat warpDst = Mat.zeros(img.rows(), img.cols(), img.type());
        // warpDst - destination image
        // warpMat - transformation matrix.
        // warp.Dst - The desired size of the output image
        Imgproc.warpAffine(img, warpDst, warpMat, warpDst.size());

        return warpDst;
    }

    public static Mat getRotatedImage(Mat mImg) {

        Mat img = new Mat();

        Imgproc.cvtColor(mImg, img, Imgproc.COLOR_BGR2GRAY);

        Point[] srcTri = new Point[3];
        srcTri[0] = new Point(0, 0);
        srcTri[1] = new Point(img.cols() - 1, 0);
        srcTri[2] = new Point(0, img.rows() - 1);

        Point[] dstTri = new Point[3];
        dstTri[0] = new Point(0, img.rows() * 0.33);
        dstTri[1] = new Point(img.cols() * 0.85, img.rows() * 0.25);
        dstTri[2] = new Point(img.cols() * 0.15, img.rows() * 0.7);
        // Armed with both sets of points, we calculate the Affine Transform(transformation matrix.) by using OpenCV function cv::getAffineTransform
        Mat warpMat = Imgproc.getAffineTransform(new MatOfPoint2f(srcTri), new MatOfPoint2f(dstTri));
        Mat warpDst = Mat.zeros(img.rows(), img.cols(), img.type());
        // warpDst - destination image
        // warpMat - transformation matrix.
        // warp.Dst - The desired size of the output image
        Imgproc.warpAffine(img, warpDst, warpMat, warpDst.size());
        Point center = new Point(warpDst.cols() / 2, warpDst.rows() / 2);
        double angle = -50.0;
        double scale = 0.6;
        // We generate the rotation matrix with the OpenCV function cv::getRotationMatrix2D , which returns a 2×3 matrix (in this case rot_mat)
        Mat rotMat = Imgproc.getRotationMatrix2D(center, angle, scale);
        // We now apply the found rotation to the output of our previous Transformation:
        Mat warpRotateDst = new Mat();
        // We now apply the found rotation to the output of our previous Transformation:
        // warpDst - input image
        // warpRotateDst - outPut image
        // ritMat - rotation Matrix
        // warpDst.size() - desired size
        Imgproc.warpAffine(warpDst, warpRotateDst, rotMat, warpDst.size());

        return warpRotateDst;
    }
}
