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

public class MainActivity extends AppCompatActivity {

    ImageView iV, iV2, iV3;
    Button bttn, bttn2, bttn3;
    TextView infoTw;
    static int mark = 0;


    //  Mat houghLines = new Mat();

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

            loadImage(path);

            displayImage(sampledImg);

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


    Mat sampledImg, changedImg, newImg;

    private void loadImage(String path) {

        Mat originImg = Imgcodecs.imread(path);// image is BGR format , try to get format


        int rows = originImg.rows();
        int clmns = originImg.cols();

        Size sz = new Size(clmns / 2, rows / 2);
        Imgproc.resize(originImg, originImg, sz);


      //  Mat rgbImg = new Mat();
       // Mat cannyEdges = new Mat();
       // Mat lines = new Mat();


        int y = 7;
        //  Imgproc.cvtColor(originImg, rgbImg, Imgproc.COLOR_BGR2GRAY);
        // convert to Canny Edge Detector
        ///  Imgproc.Canny(rgbImg,rgbImg,50,50);

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
/*
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int mobile_width = size.x;
        int mobile_height = size.y;
*/

        //    sampledImg = setHoughLines(originImg);

        //   int rows = newImg.rows();
        //   int clmns = newImg.cols();

        ///  Size sz = new Size(clmns / 2, rows / 2);
        //  Imgproc.resize(newImg, sampledImg, sz);


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


        private Mat setHoughLines(Mat mat) {

            Mat grayIMat = new Mat();
            Mat cannyIMat = new Mat();
            Mat houghLinesIMat = new Mat();

            Imgproc.cvtColor(mat, grayIMat, Imgproc.COLOR_BGR2GRAY);
            Imgproc.Canny(grayIMat, cannyIMat, 10, 100);
            Imgproc.HoughLinesP(cannyIMat, houghLinesIMat, 1, Math.PI / 180, 50, 20, 20);

            Mat houghLines = new Mat();
           // houghLines.create(houghLinesIMat.rows(), houghLinesIMat.cols(), CvType.CV_8UC1);

            //Drawing lines on the image
            for (int i = 0; i < houghLinesIMat.cols(); i++) {
                double[] points = houghLinesIMat.get(0, i);
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
           return houghLines;

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

    public void actionAny(View view) {

        //   changedImg = new Mat();
        //   changedImg = sampledImg;

        //   Imgproc.cvtColor(sampledImg, changedImg, Imgproc.COLOR_BGR2GRAY);

        //   Imgproc.applyColorMap(sampledImg,changedImg,Imgproc.COLORMAP_JET);
        //  Imgproc.cvtColor(sampledImg, changedImg, Imgproc.COLOR_RGB2GRAY);

        //  displayImage(changedImg);

        //  changedImg.convertTo(changedImg,CvType.CV_16UC1);
        int rows1 = matImg1.rows();
        int clmns1 = matImg1.cols();

        int rows2 = matImg2.rows();
        int clmns2 = matImg2.cols();
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

        infoTw.setText("rows 1 = " + rows1 + " , columns 1 = " + clmns1 + '\n' + "rows 2 = " + rows2 + " , columns 2 = " + clmns2);
        //  infoTw.setText("rows 1 = " + rows2 + " , columns 1 = " + clmns2 );

        // double[] ft = changedImg.get(1060, 2080);

        //  double b =  ft[0];rows = 4160 clmns = 3120
        //  double g =  ft[1];
        //  double r =  ft[2];
        // changedImg.get(50, 50);

        //  long s = (int) changedImg.total();


        //  infoTw.setText(rows + " " + clmns + " " + chn + " " + "b = " + b + " g = " + g +  " r = " + r );
        // infoTw.setText(rows + " " + clmns + " " + chn + " " + "b = " + b );


    }


    public void summary(View view) {

        Mat matImg3 = new Mat();

        //  Core.addWeighted(matImg1,0.7,matImg2,0.3,0.0,matImg3);
        Core.add(matImg1, matImg2, matImg3);
        displayImage(matImg3);

    }
}

