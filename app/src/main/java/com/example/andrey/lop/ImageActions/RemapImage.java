package com.example.andrey.lop.ImageActions;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class RemapImage {

    public static Mat getRemappedImage(Mat mImg, int act) {

        Mat img = new Mat();
        Mat grImg = new Mat();
        Mat mapX = new Mat();
        Mat mapY = new Mat();


        mapX = new Mat(mImg.size(), CvType.CV_32F);
        mapY = new Mat(mImg.size(), CvType.CV_32F);

        Imgproc.cvtColor(mImg, grImg, Imgproc.COLOR_BGR2GRAY);

        // get number of pixels and create array
        float buffX[] = new float[(int) (mapX.total())];
      //  mapX.get(0, 0, buffX);
        float buffY[] = new float[(int) (mapY.total())];
      //  mapY.get(0, 0, buffY);

        for (int i = 0; i < mapX.rows(); i++) {
            for (int j = 0; j < mapX.cols(); j++) {
                switch (act) {
                    case 0:
                        if (j > mapX.cols() * 0.25 && j < mapX.cols() * 0.75 && i > mapX.rows() * 0.25 && i < mapX.rows() * 0.75) {
                            buffX[i * mapX.cols() + j] = 2 * (j - mapX.cols() * 0.25f) + 0.5f;
                            buffY[i * mapY.cols() + j] = 2 * (i - mapX.rows() * 0.25f) + 0.5f;
                        } else {
                            buffX[i * mapX.cols() + j] = 0;
                            buffY[i * mapY.cols() + j] = 0;
                        }
                        break;

                        // Turn the image upside down:
                    case 1:
                        buffX[i * mapX.cols() + j] = j;
                        buffY[i * mapY.cols() + j] = mapY.rows() - i;
                        break;
                }
            }
        }
        mapX.put(0, 0, buffX);
        mapY.put(0, 0, buffY);

        Imgproc.remap(grImg, img, mapX, mapY, Imgproc.INTER_LINEAR);

        return img;

    }
}
