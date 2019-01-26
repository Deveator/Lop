package com.example.andrey.lop.ImageActions;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.core.Core.addWeighted;

public class TemplateMatching {

    public static Mat getMarkedTemplateImage(Mat mImg) {
        // convert to grayScale
        Imgproc.cvtColor(mImg, mImg, Imgproc.COLOR_BGR2GRAY);
        // cut inputImage to get template
        Mat tImg = mImg.submat(new Rect(mImg.cols() / 4, mImg.rows() / 2, 100, 100));

        Mat result = new Mat();
        result.create(mImg.rows(), mImg.cols(), mImg.type());

        Mat img = new Mat();
        mImg.copyTo(img);

        result.create(mImg.rows(), mImg.cols(), mImg.type());

        // could set another method
        Imgproc.matchTemplate(mImg, tImg, result, 5);

        // The function cv::normalize normalizes scale and shift the input array elements so that
        Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());

        Point matchLoc;
        // get point with maxValue remember smooth b/w image and light
        // for our case it is upper-right corner of rectangle template
        Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
        matchLoc = mmr.maxLoc;
        // get image with template in rectangle
        Imgproc.rectangle(img, matchLoc, new Point(matchLoc.x + tImg.cols(), matchLoc.y + tImg.rows()),
                new Scalar(0, 0, 0), 2, 8, 0);
        return img;

        // to see why we need point locator with high value - uncomment below '///'
      ///  Imgproc.rectangle(result, matchLoc, new Point(matchLoc.x + tImg.cols(), matchLoc.y + tImg.rows()),
      ///          new Scalar(0, 0, 0), 2, 8, 0);
      ///  result.convertTo(result, CvType.CV_8UC1, 255.0);
      ///  return result;
    }
}
