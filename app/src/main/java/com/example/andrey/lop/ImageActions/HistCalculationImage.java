package com.example.andrey.lop.ImageActions;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class HistCalculationImage {

    public static Mat getHistCalculationImage(Mat mImg) {

        // Separate the source image in its three R,G and B planes. For this we use the OpenCV function cv::split :
        List<Mat> bgrPlanes = new ArrayList<>();
        // Divides a multi-channel array into several single-channel arrays.
        // our input is the image to be divided (this case with three channels) and the output is a vector of Mat
        Core.split(mImg, bgrPlanes);

        // we know that our values will range in the interval [0,255]
        int histSize = 256;

        // Set the range of values (as we said, between 0 and 255 )
        float[] range = {0, 256}; //the upper boundary is exclusive
        MatOfFloat histRange = new MatOfFloat(range);

        // We want our bins to have the same size (uniform) and to clear the histograms in the beginning
        boolean accumulate = false;

        Mat bHist = new Mat(), gHist = new Mat(), rHist = new Mat();

        // "bgrPlanes" - source array
        // "new Mat()" - A mask to be used on the source array ( zeros indicating pixels to be ignored ). If not defined it is not used
        // "bHist" - The Mat object where the histogram will be stored
        // "new MatOfInt(histSize)" - The number of bins per each used dimension OR Array of histogram sizes in each dimension
        // bins: It is the number of subdivisions in each dim.
        // dims: The number of parameters you want to collect data o
        // "histRange" - The range of values to be measured per each dimension
        // "uniform" and "accumulate": The bin sizes are the same and the histogram is cleared at the beginning.
        Imgproc.calcHist(bgrPlanes, new MatOfInt(0), new Mat(), bHist, new MatOfInt(histSize), histRange, accumulate);
        /// Imgproc.calcHist(bgrPlanes, new MatOfInt(1), new Mat(), gHist, new MatOfInt(histSize), histRange, accumulate);
        ///  Imgproc.calcHist(bgrPlanes, new MatOfInt(2), new Mat(), rHist, new MatOfInt(histSize), histRange, accumulate);
        // Create an image to display the histograms
        int histW = 512, histH = 400;
        int binW = (int) Math.round((double) histW / histSize);
        Mat histImage = new Mat(histH, histW, CvType.CV_8UC3, new Scalar(0, 0, 0));
        // Notice that before drawing, we first cv::normalize the histogram
        // so its values fall in the range indicated by the parameters entered:
        // "0" and "histImage.rows": For this example, they are the lower and upper limits to normalize the values of r_hist
        // "NORM_MINMAX": Argument that indicates the type of normalization (as described above, it adjusts the values between the two limits set before)
        Core.normalize(bHist, bHist, 0, histImage.rows(), Core.NORM_MINMAX);
        // Core.normalize(gHist, gHist, 0, histImage.rows(), Core.NORM_MINMAX);
        // Core.normalize(rHist, rHist, 0, histImage.rows(), Core.NORM_MINMAX);

        float[] bHistData = new float[(int) (bHist.total() * bHist.channels())];
        bHist.get(0, 0, bHistData);
        /// float[] gHistData = new float[(int) (gHist.total() * gHist.channels())];
        /// gHist.get(0, 0, gHistData);
        ///  float[] rHistData = new float[(int) (rHist.total() * rHist.channels())];
        ///  rHist.get(0, 0, rHistData);
        for (int i = 1; i < histSize; i++) {
            Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(bHistData[i - 1])),
                    new Point(binW * (i), histH - Math.round(bHistData[i])), new Scalar(255, 0, 0), 2);
            /// Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(gHistData[i - 1])),
            ///         new Point(binW * (i), histH - Math.round(gHistData[i])), new Scalar(0, 255, 0), 2);
            /// Imgproc.line(histImage, new Point(binW * (i - 1), histH - Math.round(rHistData[i - 1])),
            ///         new Point(binW * (i), histH - Math.round(rHistData[i])), new Scalar(0, 0, 255), 2);
        }
        return histImage;
    }
}
