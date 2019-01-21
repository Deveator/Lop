package com.example.andrey.lop.ImageActions;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class FindEdgesViaMorfOptns {

    public static Mat findEdgesViaMorfOptns(Mat mImg){

      //  Mat img = new Mat();

        // Inverse vertical image
        Mat vImg = FindVertLinesViaMorfOprtns.findVertLinesViaMorfOprtns(mImg);
        Core.bitwise_not(vImg, vImg);
        // Extract edges and smooth image according to the logic
        // 1. extract edges
        // 2. dilate(edges)
        // 3. src.copyTo(smooth)
        // 4. blur smooth img
        // 5. smooth.copyTo(src, edges)
        // Step 1
        Mat edges = new Mat();
        Imgproc.adaptiveThreshold(vImg, edges, 255, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 3, -2);
        // Step 2
        // Mat.ones - Returns an array of all 1's of the specified size and type.
        Mat kernel = Mat.ones(2, 2, CvType.CV_8UC1);
        Imgproc.dilate(edges, edges, kernel);
        // Step 3
        Mat smooth = new Mat();
        vImg.copyTo(smooth);
        // Step 4
        Imgproc.blur(smooth, smooth, new Size(2, 2));
        // Step 5
        smooth.copyTo(vImg, edges);

        return edges;
    }
}
