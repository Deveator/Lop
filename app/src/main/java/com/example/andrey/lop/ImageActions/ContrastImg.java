package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;

import static com.example.andrey.lop.Saturate.saturate;

public class ContrastImg {

    public static Mat getContrastImg(Mat mat, double cntrst, int bright){

        Mat newImage = Mat.zeros(mat.size(), mat.type());
        double alpha = cntrst; /*< Simple contrast control */
        int beta = bright;       /*< Simple brightness control */

        byte[] imageData = new byte[(int) (mat.total()*mat.channels())];
        mat.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total()*newImage.channels())];
        for (int y = 0; y < mat.rows(); y++) {
            for (int x = 0; x < mat.cols(); x++) {
                for (int c = 0; c < mat.channels(); c++) {
                    double pixelValue = imageData[(y * mat.cols() + x) * mat.channels() + c];
                    pixelValue = pixelValue < 0 ? pixelValue + 256 : pixelValue;
                    newImageData[(y * mat.cols() + x) * mat.channels() + c]
                            = saturate(alpha * pixelValue + beta);
                }
            }
        }

        newImage.put(0, 0, newImageData);

        return newImage;
    }
}
