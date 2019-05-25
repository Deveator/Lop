package com.example.andrey.lop.ImageActions;

import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.AKAZE;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.KAZE;
import org.opencv.xfeatures2d.SURF;

import static org.opencv.features2d.AKAZE.DESCRIPTOR_MLDB;

public class FeatureDtector {

    public static Mat getFeatureDtector(Mat mImg) {

        Mat img = new Mat();
        img = mImg.clone();

        double hessianThreshold = 400;
        int nOctaves = 4, nOctaveLayers = 3;
        boolean extended = false, upright = false;
        float threshold = 0.001f;
        KAZE detector = KAZE.create(false, false, threshold, nOctaves, nOctaveLayers);
        MatOfKeyPoint keypoints = new MatOfKeyPoint();
        detector.detect(img, keypoints);
        Features2d.drawKeypoints(img, keypoints, img);
        return img;
    }


}
