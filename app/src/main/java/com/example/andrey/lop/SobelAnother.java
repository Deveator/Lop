package com.example.andrey.lop;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.BORDER_DEFAULT;
import static org.opencv.core.Core.addWeighted;
import static org.opencv.core.Core.convertScaleAbs;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

public class SobelAnother {

    public static Mat getSobelImage(Mat mImg) {

        // Mat img = new Mat();
        Mat imgX = new Mat();
        Mat imgY = new Mat();


        Mat img = Mat.zeros(mImg.size(), mImg.type());
/*
        byte[] imageData = new byte[(int) (mImg.total() * mImg.channels())];
        mImg.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (img.total() * img.channels())];
        for (int y = 0; y < mImg.rows(); y++) {
            for (int x = 0; x < mImg.cols(); x++) {
                for (int c = 0; c < mImg.channels(); c++) {
                    double pixelValue = imageData[(y * mImg.cols() + x) * mImg.channels() + c];
                    pixelValue = pixelValue < 0 ? pixelValue + 256 : pixelValue;
                    newImageData[(y * mImg.cols() + x) * mImg.channels() + c]
                            = Saturate.saturate(pixelValue + 40);
                }
            }
        }
        img.put(0, 0, newImageData);
*/
        //    Imgproc.GaussianBlur(mImg, img, new Size(3, 3), 0, 0, BORDER_DEFAULT);

        Imgproc.GaussianBlur(mImg, img, new Size(0, 0), 20, 20);
        Core.addWeighted(mImg, 1.5, img, -0.5, 0, img);
        //  TwoD_image.GetTwoD_Image(img);
        Imgproc.cvtColor(img, img, COLOR_BGR2GRAY);

        Imgproc.Sobel(img, imgX, img.depth(), 1, 0, 3, 1, 0, BORDER_DEFAULT);
        Imgproc.Sobel(img, imgY, img.depth(), 0, 1, 3, 1, 0, BORDER_DEFAULT);

        // converting back to CV_8U
        convertScaleAbs(imgX, imgX);
        convertScaleAbs(imgY, imgY);

        addWeighted(imgX, 0.5, imgY, 0.5, 0, img);

        return img;
    }
}


