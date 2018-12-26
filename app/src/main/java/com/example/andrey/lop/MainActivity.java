package com.example.andrey.lop;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
//import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Point;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;

import static java.lang.Math.PI;


public class MainActivity extends AppCompatActivity {

    ImageView iV, iV2, iV3;
    Button bttn, bttn2, bttn3;
    TextView infoTw;
    static int mark = 0;
    static int mark2 = 0;
    Mat orImage, houghImage, houghCirculeImage, greyImage, cannyImage, gaussianImage;
    int tX, tY;
    double Dx1, Dx2, Dy1, Dy2;
    int d;

    //  Mat sampledImg = new Mat();
    //  Mat rgbImg = new Mat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("opencv_java3");
        OpenCVLoader.initDebug();

        iV = findViewById(R.id.imageV);
        iV2 = findViewById(R.id.imageV2);
        iV3 = findViewById(R.id.imageV3);
        bttn = findViewById(R.id.button1);
        bttn2 = findViewById(R.id.button2);
        bttn3 = findViewById(R.id.button3);
        infoTw = findViewById(R.id.tW);


    }

    public void openGallery(View v) {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {

            Uri imageUri = data.getData();

            String path = getPath(imageUri);

            // loadImage(path);

            getOriginImage(path);


            getHoughCircle(orImage);

            displayImage(houghCirculeImage);

            mark++;
        }
    }

    Mat matImg1, matImg2;

    private void displayImage(Mat mat) {

        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);

        Utils.matToBitmap(mat, bitmap);

        if (mark == 0) {
            iV.setImageBitmap(bitmap);
            matImg1 = mat;
        }

        if (mark == 1) {
            iV2.setImageBitmap(bitmap);
            matImg2 = mat;
        }
        if (mark == 2) {
            iV3.setImageBitmap(bitmap);
            //  matImg3 = mat;
        }
    }


    // method to get imate in Mat format resized down 4 times from path
    private Mat getOriginImage(String path) {
        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format
        int rows = originImg.rows();
        int clmns = originImg.cols();
        Size sz = new Size(clmns / 4, rows / 4);
        Imgproc.resize(originImg, originImg, sz);
        orImage = originImg;
        return orImage;
    }

    //  Mat greyImage;

    // method to get image in gray color
    private Mat getGreyImage(Mat matImage) {
        Mat img = new Mat();
        // convert to gray color
        Imgproc.cvtColor(matImage, img, Imgproc.COLOR_BGR2GRAY);
        greyImage = img;
        return greyImage;
    }

    // method to image in gray color with Canny edges
    private Mat getCannyImage(Mat matImage, int min_threshold, int max_threshold) {
        Mat img = new Mat();
        // convert to gray color
        Imgproc.cvtColor(matImage, img, Imgproc.COLOR_BGR2GRAY);
        // convert to "Canny" image
        // min_threshould & max_threshold set threshold to find better edges or filter found edges via threshold with help of pixels values
        // if pixel value more then max_threshold  - pixel is marked as strong
        // if pixel value lower then max_threshold &  more then min_threshold - pixel is marked as weak
        // if pixel value lower then min_threshold  - pixel is suppressed
        Imgproc.Canny(img, img, min_threshold, max_threshold);
        cannyImage = img;
        return cannyImage;
    }


    // method to image in gray color with Hough lines
    private Mat getHoughLinesImage(Mat matImage) {
        Mat rgbImg = new Mat();
        Mat cannyEdges = new Mat();
        Mat hLImage = new Mat();
        Mat linesImage = new Mat();
        Size vSize = new Size(21, 21);
        // convert to gray color
        Imgproc.cvtColor(matImage, rgbImg, Imgproc.COLOR_BGR2GRAY);
        // convert to "Canny" image
        // min_threshould & max_threshold set threshold to find better edges or filter found edges via threshold with help of pixels values
        // if pixel value more then max_threshold  - pixel is marked as strong
        // if pixel value lower then max_threshold &  more then min_threshold - pixel is marked as weak
        // if pixel value lower then min_threshold  - pixel is suppressed
        Imgproc.Canny(rgbImg, cannyEdges, 80, 255);
        // threshold - min number of votes to set
        //theta
        Imgproc.HoughLinesP(cannyEdges, linesImage, 1, PI / 2, 40, 20, 20);
        hLImage.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);
        Dx1 = hLImage.cols();
        Dx2 = hLImage.rows();
        //Drawing lines on the image
/*
        for (int i = 0; i < linesImage.cols(); i++) {

            double[] points = linesImage.get(0, i);
            double x1, y1, x2, y2;

            x1 = points[0];
            y1 = points[1];
            x2 = points[2];
            y2 = points[3];

            Point pt1 = new Point(x1, y1);
            Point pt2 = new Point(x2, y2);

            //Drawing lines on an image
            Imgproc.line(hLImage, pt1, pt2, new Scalar(255, 0, 0), 1);
        }
*/


        for (int x = 0; x < linesImage.rows(); x++) {
            double[] fer = linesImage.get(x, 0);

            double x1 = fer[0],
                    y1 = fer[1],
                    x2 = fer[2],
                    y2 = fer[3];

            Point start = new Point(x1, y1);
            Point end = new Point(x2, y2);

            //  double dx = x1 - x2;
            // double dy = y1 - y2;

            // double dist = Math.sqrt(dx * dx + dy * dy);

            Imgproc.line(hLImage, start, end, new Scalar(255, 0, 0), 1);
            // Dx1 = linesImage.cols();
            // Dx2 = linesImage.rows();;
            //y1 = y1;
            //y2 = y2;
            // = fer.length;


        }


        houghImage = hLImage;
        return houghImage;
    }

    private Mat getGaussianImage(Mat matImage, int xSize, int ySize, double vSigma) {

        // vSigma - more vSigma digit - more smoother
        Mat img = new Mat();
        Imgproc.cvtColor(matImage, img, Imgproc.COLOR_BGR2GRAY);
        // mask size
        Size vSize = new Size();
        vSize.height = ySize;
        vSize.width = xSize;
        Imgproc.GaussianBlur(img, img, vSize, vSigma);
        gaussianImage = img;
        return gaussianImage;
    }

    private Mat getHoughCircle(Mat matImage) {
        Mat img = new Mat();
        Mat circleMatImg = new Mat();
        Mat circlatImg = new Mat();
        Imgproc.cvtColor(matImage, img, Imgproc.COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(img, img, new Size(11, 11), 5);
        circlatImg.create(img.rows(), img.cols(), CvType.CV_8UC1);
        // minDist - Minimum distance between the centers of the detected circles
        // param1 - like in Canny - threshold1 = param1 / 2, threshold2 = param1
        // param2 - threshold for the circle center !!!The smaller it is, the more false circles may be detected. Circles, corresponding to the larger accumulator values, will be returned first!!!
        // minRadius, maxRadius - Maximum circle radius. If <= 0, uses the maximum image dimension. If < 0, returns centers without finding the radius
        Imgproc.HoughCircles(img, circleMatImg, Imgproc.CV_HOUGH_GRADIENT, 1, img.rows() - 5, 10, 10, 50, 200);
        int radius;
        Point pt;

        for (int x = 0; x < circleMatImg.cols(); x++) {
            double vCircle[] = circleMatImg.get(0, x);

            if (vCircle == null)
                break;

            pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));
            radius = (int) Math.round(vCircle[2]);

            // draw the found circle
            Imgproc.circle(circlatImg, pt, radius, new Scalar(255, 0, 0), 3);
            Imgproc.circle(circlatImg, pt, 3, new Scalar(255, 0, 0), 3);
        }
        houghCirculeImage = circlatImg;
        return houghCirculeImage;
    }


    private double calculateSubSimpleSize(Mat src, int mobile_width, int mobile_height) {

        final int width = src.width();
        final int height = src.height();
        double inSampleSize = 1;

        if (height > mobile_height || width > mobile_width) {

            final double heightRatio = (double) mobile_height / (double) height;
            final double widthRatio = (double) mobile_width / (double) width;

            inSampleSize = heightRatio < widthRatio ? height : width;
        }
        return inSampleSize;
    }

    private String getPath(Uri uri) {
        if (uri == null) {
            return null;
        } else {

            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

            if (cursor != null) {
                int col_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();

                return cursor.getString(col_index);
            }
        }
        return uri.getPath();
    }

    public void didIt(Mat mImage, int aRow, int aCol) {

        mImage.convertTo(mImage, CvType.CV_8UC1);

        double[] ft = mImage.get(aRow, aCol);
/*
        for (int i = 0; i < 10; i++) {

            double[] ft3 = mImage.get(10 + i, 20 + i);
            double b = ft3[0];
            infoTw.append(b + " , ");
        }
*/

        double bwVal = ft[0];

        infoTw.setText(bwVal + "");

    }

    public void actionAny(View view, Mat mImage, int aRow, int aCol) {


        //  changedImg.convertTo(changedImg,CvType.CV_16UC1);
        int rows1 = mImage.rows();
        int clmns1 = mImage.cols();


        //  int rows2 = matImg2.rows();
        // int clmns2 = matImg2.cols();
        // int chn = changedImg.channels();
        // changedImg.convertTo(changedImg,CV_8U);
        // int sRbgColor = changedImg.getRGB(int x, int y);
        //   int t = changedImg.get(50, 50);

     /*   for (int i = 0; i < 10; i++) {

            double[] ft = changedImg.get(10 + i, 20 + i);
            double b = ft[0];
            infoTw.append(b + " , ");
            }

   */

        // infoTw.setText("rows 1 = " + rows1 + " , columns 1 = " + clmns1 + '\n' + "rows 2 = " + rows2 + " , columns 2 = " + clmns2);
        //  infoTw.setText("rows 1 = " + rows2 + " , columns 1 = " + clmns2 );

        double[] ft1 = mImage.get(aRow, aCol);

        double bf1 = ft1[0];//rows = 4160 clmns = 3120
        //  double g =  ft[1];
        //  double r =  ft[2];
        // changedImg.get(50, 50);

        //  long s = (int) changedImg.total();

        infoTw.setText((int) bf1);
        // infoTw.setText(rows + " " + clmns + " " + chn + " " + "b = " + b + " g = " + g +  " r = " + r );
        // infoTw.setText(rows + " " + clmns + " " + chn + " " + "b = " + b );


    }

    public void summary(View view) {

        // infoTw.setText(Dx1+" , " +Dx2+" , " +Dy1+" , " +Dy1+" , " +d);
        Point start = new Point(100, 100);
        Point end = new Point(300, 100);


        // Imgproc.line(greyImage, start, end, new Scalar(255, 0, 0), 1);
        // displayImage(greyImage);
        didIt(greyImage, 100, 700);
        // Mat matImg3 = new Mat();
        //   int r = greyImage.rows();
        //  int c = greyImage.cols();
        //   infoTw.setText(r + " , " + c);
        //  Core.addWeighted(matImg1,0.7,matImg2,0.3,0.0,matImg3);
        // Core.add(matImg1, matImg2, matImg3);
        // displayImage(matImg3);

    }

    /*
    private void loadImage(String path) {

        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format

        int rows = originImg.rows();
        int clmns = originImg.cols();

        Size sz = new Size(clmns / 4, rows / 4);
        Imgproc.resize(originImg, originImg, sz);


        Mat rgbImg = new Mat();

        // Mat cannyEdges = new Mat();
        // Mat lines = new Mat();


        //int y = 7;
        Imgproc.cvtColor(originImg, rgbImg, Imgproc.COLOR_BGR2GRAY);
        // convert to Canny Edge Detector
        Imgproc.Canny(rgbImg, rgbImg, 50, 50);

        //   Imgproc.HoughLines(rgbImg,rgbImg,5.0,4.0,50);

        //Converting the image to grayscale
        //  Imgproc.cvtColor(originalMat, grayMat, Imgproc.COLOR_BGR2GRAY);

        //  setHoughLines(originImg);
        //

        void HoughLines () {

            Mat rgbImg = new Mat();
            Mat cannyEdges = new Mat();
            Mat lines = new Mat();

            Imgproc.cvtColor(originImg, rgbImg, Imgproc.COLOR_BGR2GRAY);
            Imgproc.Canny(rgbImg, cannyEdges, 10, 100);
            Imgproc.HoughLinesP(cannyEdges, lines, 1, Math.PI / 180, 50, 20, 20);

            Mat houghLines = new Mat();
            houghLines.create(cannyEdges.rows(), cannyEdges.cols(), CvType.CV_8UC1);

            //Drawing lines on the image
            for (int i = 0; i < lines.cols(); i++) {
                double[] points = lines.get(0, i);
                double x1, y1, x2, y2;

                x1 = points[0];
                y1 = points[1];
                x2 = points[2];
                y2 = points[3];

                Point pt1 = new Point(x1, y1);
                Point pt2 = new Point(x2, y2);

                //Drawing lines on an image
                Imgproc.line(houghLines, pt1, pt2, new Scalar(255, 0, 0), 1);
            }
            sampledImg = houghLines;
        }


        //Drawing lines on the image


        //   Imgproc.GaussianBlur(rgbImg,rgbImg, new Size(y,y),0.0);
        //  Imgproc.GaussianBlur(rgbImg,rgbImg,new Size(21,21),9);
        // Imgproc.cvtColor(rgbImg, newImg, Imgproc.COLOR_RGB2GRAY);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int mobile_width = size.x;
        int mobile_height = size.y;


        sampledImg = rgbImg;


        // double downSampleRatio = calculateSubSimpleSize(rgbImg, mobile_width, mobile_height);

        //  Imgproc.resize(rgbImg, sampledImg, new Size(), downSampleRatio, downSampleRatio, Imgproc.INTER_AREA);// need more info


        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    sampledImg = sampledImg.t();
                    Core.flip(sampledImg, sampledImg, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    sampledImg = sampledImg.t();
                    Core.flip(sampledImg, sampledImg, 0);
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}

