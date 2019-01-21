package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.Core.addWeighted;
import static org.opencv.core.Core.convertScaleAbs;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

public class SobelImage {

    public static Mat getSobelImage(Mat mImg){

        Mat img = new Mat();
        Mat imgX = new Mat();
        Mat imgY = new Mat();

        Imgproc.GaussianBlur(mImg,img,new Size(3,3), 0, 0, BORDER_DEFAULT );

        Imgproc.cvtColor(img,img,COLOR_BGR2GRAY);

        Imgproc.Sobel(img, imgX, img.depth(), 1, 0, 3, 1, 0, BORDER_DEFAULT);
        Imgproc.Sobel(img, imgY, img.depth(), 0, 1, 3, 1, 0, BORDER_DEFAULT);

        // converting back to CV_8U
        convertScaleAbs(imgX, imgX);
        convertScaleAbs(imgY, imgY);

        addWeighted(imgX, 0.5, imgY, 0.5, 0, img);

        return img;
    }
}
