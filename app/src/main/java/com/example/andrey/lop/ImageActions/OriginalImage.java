package com.example.andrey.lop.ImageActions;

import android.util.DisplayMetrics;

import com.example.andrey.lop.MainActivity;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static com.example.andrey.lop.MainActivity.idealHeight;
import static com.example.andrey.lop.MainActivity.idealWidth;
import static com.example.andrey.lop.MainActivity.originalHeight;
import static com.example.andrey.lop.MainActivity.originalWidth;

public class OriginalImage {

    // method to get image in Mat format resized down 4 times from path
    public static Mat GetOriginalImage(String path, int d, int d2) {

        Mat orImage = new Mat();
        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format
        int rows = originImg.rows();
        int clmns = originImg.cols();
        Size sz = new Size(clmns / d, rows / d2);
        Imgproc.resize(originImg, originImg, sz);
        orImage = originImg;
        Imgproc.cvtColor(orImage, orImage, Imgproc.COLOR_BGR2RGB);
        return orImage;
    }

    // method to get image in Mat format resized down 4 times from path
    public static Mat GetResizedImage(String path) {
        Mat orImage = new Mat();
        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format
        Size sz = new Size(750, 1000);

        System.out.println(originImg.size());

        Imgproc.resize(originImg, orImage, sz);
        //  orImage = originImg;
        Imgproc.cvtColor(orImage, orImage, Imgproc.COLOR_BGR2RGB);
        return orImage;
    }

    public static Mat GetAdoptedImage(String path) {
        Mat orImage = new Mat();
        int nWidth = 1;
        int nHeight = 1;
        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format

        if (MainActivity.screenWidth < originImg.cols()) {

            double mtx = (double) originImg.rows() / (double) originImg.cols();

            nWidth = MainActivity.screenWidth;

            if (((int) (nWidth * mtx) % 2) == 1) {
                nHeight = (int) (nWidth * mtx) + 1;
            } else {
                System.out.println("Yes2");
                nHeight = (int) (nWidth * mtx);
            }
        }
        Size sz = new Size(nWidth, nHeight);

        System.out.println(originImg.size());

        Imgproc.resize(originImg, orImage, sz);
        //  orImage = originImg;
        Imgproc.cvtColor(orImage, orImage, Imgproc.COLOR_BGR2RGB);
        return orImage;
    }

    public static Mat getRequiredSizeImage(String path) {

        int imageViewWidth = 1;
        int imageViewHeight = 1;

        Mat orImage = new Mat();
        Mat originImg = Imgcodecs.imread(path);
        originalWidth = originImg.cols();
        originalHeight = originImg.rows();

        double mtx = (double) originalWidth / (double) originalHeight;

        imageViewWidth = MainActivity.screenWidth;

        if (((int) (imageViewWidth * mtx) % 4) == 0) {
            imageViewHeight = (int) (imageViewWidth * mtx);

        } else if (((int) (imageViewWidth * mtx) % 4) == 1) {
            imageViewHeight = (int) (imageViewWidth * mtx) - 1;

        } else if (((int) (imageViewWidth * mtx) % 4) == 2) {
            imageViewHeight = (int) (imageViewWidth * mtx) - 2;

        } else if (((int) (imageViewWidth * mtx) % 4) == 3) {
            imageViewHeight = (int) (imageViewWidth * mtx) - 3;
        }

        idealWidth = imageViewWidth * 4;
        idealHeight = imageViewHeight * 4;

        if (idealWidth > originalWidth || idealHeight > originalHeight) {

            if (originalWidth % 4 == 0) {
                idealWidth = originalWidth / 4;

            } else if (originalWidth % 4 == 1) {
                idealWidth = (originalWidth / 4) - 1;
                originalWidth = originalWidth - 1;

            } else if (originalWidth % 4 == 2) {
                idealWidth = (originalWidth / 4) - 2;
                originalWidth = originalWidth - 2;

            } else if (originalWidth % 4 == 3) {
                idealWidth = (originalWidth / 4) - 3;
                originalWidth = originalWidth - 3;
            }

            if (originalHeight % 4 == 0) {
                idealHeight = originalHeight / 4;

            } else if (originalHeight % 4 == 1) {
                idealHeight = (originalHeight / 4) - 1;
                originalHeight = originalHeight - 1;

            } else if (originalHeight % 4 == 2) {
                idealHeight = (originalHeight / 4) - 2;
                originalHeight = originalHeight - 2;

            } else if (originalHeight % 4 == 3) {
                idealHeight = (originalHeight / 4) - 3;
                originalHeight = originalHeight - 3;
            }
        }

        Size sz = new Size(originalWidth, originalHeight);

        Imgproc.resize(originImg, orImage, sz);
        //  orImage = originImg;
        Imgproc.cvtColor(orImage, orImage, Imgproc.COLOR_BGR2RGB);
        return orImage;

    }


    // method to get Mat from path of zoomed image
    public static Mat GetImage(String path) {
        Mat originImg = Imgcodecs.imread(path);
        Imgproc.cvtColor(originImg, originImg, Imgproc.COLOR_BGR2RGB);
        return originImg;
    }

    public static int getXforRect(int x, int imageWidth, int step) {
        int rectX = 0;
        int helpX;
        switch (step) {
            case 1:
                rectX = x - (imageWidth / 4);
                if ((rectX + (imageWidth / 2)) > imageWidth) {
                    helpX = (rectX + (imageWidth / 2)) - imageWidth;
                    rectX = rectX - helpX;
                } else if (rectX < 0) {
                    rectX = 0;
                }
                break;
            case 2:
                imageWidth = imageWidth / 2;
                rectX = x - (imageWidth / 4);
                if ((rectX + (imageWidth / 2)) > imageWidth) {
                    helpX = (rectX + (imageWidth / 2)) - imageWidth;
                    rectX = rectX - helpX;
                } else if (rectX < 0) {
                    rectX = 0;
                }
                break;
        }
        return rectX;
    }

    public static int getYforRect(int y, int imageHeight, int step) {
        int rectY = 0;
        int helpY;
        switch (step) {
            case 1:
                rectY = y - (imageHeight / 4);
                if ((rectY + (imageHeight / 2)) > imageHeight) {
                    helpY = (rectY + (imageHeight / 2)) - imageHeight;
                    rectY = rectY - helpY;
                } else if (rectY < 0) {
                    rectY = 0;
                }
                break;
            case 2:
                imageHeight = imageHeight / 2;
                rectY = y - (imageHeight / 4);
                if ((rectY + (imageHeight / 2)) > imageHeight) {
                    helpY = (rectY + (imageHeight / 2)) - imageHeight;
                    rectY = rectY - helpY;
                } else if (rectY < 0) {
                    rectY = 0;
                }
                break;
        }
        return rectY;
    }
}
