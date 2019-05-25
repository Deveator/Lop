package com.example.andrey.lop;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
//import android.graphics.Point;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrey.lop.ImageActions.BasicDrawing;
import com.example.andrey.lop.ImageActions.CannyImage;
import com.example.andrey.lop.ImageActions.ContourImage;
import com.example.andrey.lop.ImageActions.DilateImage;
import com.example.andrey.lop.ImageActions.ErodeImage;
import com.example.andrey.lop.ImageActions.FaceDetect;
import com.example.andrey.lop.ImageActions.FindContourImage;
import com.example.andrey.lop.ImageActions.FromWhiteToBlackBackgrnd;
import com.example.andrey.lop.ImageActions.FullBody;
import com.example.andrey.lop.ImageActions.GaussianImage;
import com.example.andrey.lop.ImageActions.GetHUEValue;
import com.example.andrey.lop.ImageActions.GrayImage;
import com.example.andrey.lop.ImageActions.LabClass;
import com.example.andrey.lop.ImageActions.LabImg;
import com.example.andrey.lop.ImageActions.OriginalImage;
import com.example.andrey.lop.ImageActions.SURFFLANNMatchingHomography;
import com.example.andrey.lop.ImageActions.SobelImage;
import com.example.andrey.lop.ImageActions.TestClass;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.example.andrey.lop.ImageActions.FindContourImage.contours2;
import static com.example.andrey.lop.ImageActions.FindContourImage.xCorC;
import static com.example.andrey.lop.ImageActions.FindContourImage.yCorC;
import static com.example.andrey.lop.ImageActions.LabClass.x_Cor;
import static com.example.andrey.lop.ImageActions.LabClass.y_Cor;
import static com.example.andrey.lop.ImageActions.LabImg.xCor;
import static com.example.andrey.lop.ImageActions.LabImg.yCor;
import static com.example.andrey.lop.ImageActions.TestClass.allX;
import static com.example.andrey.lop.ImageActions.TestClass.allY;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<Integer> xCoo = new ArrayList<>();
    public static ArrayList<Integer> yCoo = new ArrayList<>();

    public static ArrayList<Double> DCol1 = new ArrayList<>();
    public static ArrayList<Double> DCol2 = new ArrayList<>();
    public static ArrayList<Double> DCol3 = new ArrayList<>();

    public static double blueVal, greenVal, redVal;

    ImageView iV, iV2, iV3, iV4;
    Button bttn, bttn2, bttn3;
    TextView infoTw;
    static int mark = 0;
    static int mark2 = 0;
    Mat oImage, oImage2, houghImage, helpImg, houghCirculeImage, greyImage, greyImage2, cannyImage, gaussianImage, filter2DImage_2, filter2DImage, preImg, preImg_2, anPreImg;
    int tX, tY;
    double Dx1, Dx2, Dy1, Dy2;
    int d;
    public static String path;
    public static CascadeClassifier cascadeClassifier;

    //  Mat sampledImg = new Mat();
    //  Mat rgbImg = new Mat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("opencv_java3");
        //   System.loadLibrary("opencv");
        OpenCVLoader.initDebug();

        iV = findViewById(R.id.imageV);
        iV2 = findViewById(R.id.imageV2);
        iV3 = findViewById(R.id.imageV3);
        iV4 = findViewById(R.id.imageV4);
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

            path = getPath(imageUri);

            // loadImage(path);

            oImage = OriginalImage.GetOriginalImage(path, 4, 4);
            oImage2 = OriginalImage.GetOriginalImage(path, 4, 4);

            //Imgproc.cvtColor(oImage,oImage,Imgproc.COLOR_BGR2Lab);
            //Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_BGR2HSV);

            //   oImage = OriginalImage.GetOriginalImage2(path);


            //  greyImage2 = GrayImage.GetGrayImage(oImage);

            //  greyImage = GrayImage.GetGrayImage(oImage);

            /// Imgproc.rectangle(greyImage, new Point(greyImage.cols() / 4, greyImage.rows() / 2),
            ////        new Point(greyImage.cols() / 4 + 150, greyImage.rows() / 2 + 100), new Scalar(255, 0, 0), 2, 8, 0);

            //  gaussianImage = GaussianImage.GetGaussianImage(oImage,3,3,5);

            // filter2DImage = TwoD_image.GetTwoD_Image(oImage);
            //Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_BGR2GRAY);
            //  Mat dst = new Mat();
            //  Imgproc.equalizeHist( oImage, dst );
            //   preImg = ErodeImage.getErodeImage(oImage);
            //   cannyImage = CannyImage.GetCannyImage(dst,100,200);

            // houghImage = HoughLinesImage.GetHoughLinesImage(oImage);

            //  houghCirculeImage = HoughCircle.GetHoughCircle(oImage);

            // filter2DImage = TwoD_image.GetTwoD_Image(oImage);

            //  filter2DImage_2 = TwoD_image.GetTwoD_Image_2(oImage);
            //  CascadeClassifier faceCascade = new CascadeClassifier();
            //   CascadeClassifier eyesCascade = new CascadeClassifier();
/*
            try {
                InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt);

                File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);

                File mCascadeFile = new File(cascadeDir, "cascade.xml");

                FileOutputStream os = new FileOutputStream(mCascadeFile);


                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }

                is.close();
                os.close();

                // Load the cascade classifier
                cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                cascadeClassifier.load(mCascadeFile.getAbsolutePath());
                if (cascadeClassifier.empty()) {
                    Log.e("dd", "Failed to load cascade classifier");
                    cascadeClassifier = null;
                }
            } catch (Exception e) {
                Log.e("OpenCVActivity", "Error loading cascade", e);
            }

*/
            // System.out.println(path);
            // faceCascade.load(path);
            //   eyesCascade.load(path);


            switch (mark) {

                case 0:


                    //   anPreImg = TwoD_image.GetTwoD_Image(oImage);
                    ///  LabClass.calculate(oImage);
                    // anPreImg = TwoD_image.GetTwoD_Image(oImage);
                    ///  preImg = changeColor(oImage);

                    /// preImg = TwoD_image.GetTwoD2_Image(anPreImg);
                    /// preImg = ErodeImage.getErodeImage(anPreImg);
                    ///  anPreImg = ErodeImage.getErodeImage(anPreImg);
                    /// helpImg = ContourImage.getMainContourImage(preImg);
                    //   displayImage(oImage);
                    ///  System.out.println(oImage.cols());
                    ///  System.out.println(oImage.rows());


                    //   LabClass.upDownStage(oImage);
                    // makes white background
                    //   preImg = changeColor(oImage);

                    //    LabClass.leftRightStage(preImg);

                    //    preImg = changeColor(preImg);

                    LabClass.calculate(oImage);

                    preImg = changeColor(oImage);

                   // LabClass.leftRightStage(preImg);

                   // preImg = changeColor(preImg);


                     anPreImg = ErodeImage.getErodeImage(preImg);

                     helpImg = ContourImage.getMainContourImage(anPreImg);

                    displayImage(preImg);

                    break;
                case 1:
                    Mat sMat = oImage.submat(200, 300, 0, 100);

                    Imgproc.blur(sMat, sMat, new Size(3, 3));

                    displayImage(setBlack(GrayImage.GetGrayImage(sMat)));
                    //  getAverage(sMat);
                    // displayImage(FindContourImage.getContourImg(sMat));
                    //  displayImage(sMat);
                    //  displayImage(FaceDetect.getFaceDetect(oImage, getCascadeClassifier(R.raw.haarcascade_frontalface_alt)));
                    //   didIt(filter2DImage_2);
                    break;
                case 2:
                    Mat sMat2 = oImage.submat(200, 300, 101, 290);

                    Imgproc.blur(sMat2, sMat2, new Size(3, 3));

                    displayImage(setBlack2(GrayImage.GetGrayImage(sMat2)));
                    //  displayImage(FindContourImage.getContourImg(sMat2));
                    break;
                case 3:
                    Mat sMat3 = oImage.submat(100, 300, 291, 389);
                    displayImage(FromWhiteToBlackBackgrnd.getFromWhiteToBlackBackgrnd(sMat3));
                    //  displayImage(FindContourImage.getContourImg(sMat3));
                    break;

            }


            // displayImage(AffineImage.getAffinedImage(oImage));
/*
            if(mark!=1){

                getGreyImage(orImage);

                displayImage(greyImage);

            }else{

                getGreyImage(orImage);

                get2DfilterImage(greyImage);

                displayImage(filter2DImage);

            }
*/
            mark++;
        }
    }

    public static Mat changeColor(Mat inputImg) {


        for (int i = 0; i < x_Cor.size(); i++) {
            double[] g = {255.0, 255.0, 255.0};
            inputImg.put(y_Cor.get(i), x_Cor.get(i), g);
        }

        x_Cor.clear();
        y_Cor.clear();
        System.out.println("x_Cor size after clean: " + x_Cor.size());
        return inputImg;

    }

    public static Mat cloneMat(Mat inputImg) {

        for (int i = 0; i < 100; i++) {
            for (int y = 0; y < 100; y++) {
                double[] g = {255.0, 255.0, 255.0};
                inputImg.put(i, y, g);
            }
        }
        return inputImg;
    }

    // method to make black image with original dimensions
    public static Mat retMat(Mat my) {

        for (int x = 0; x < my.cols(); x++) {
            for (int y = 0; y < my.rows(); y++) {

                double[] dVal = {0.0, 0.0, 0.0};
                my.put(y, x, dVal);
            }
        }
        return my;
    }


    public CascadeClassifier getCascadeClassifier(int resource) {
        try {
            InputStream is = getResources().openRawResource(resource);

            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);

            File mCascadeFile = new File(cascadeDir, "cascade.xml");

            FileOutputStream os = new FileOutputStream(mCascadeFile);


            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }

            is.close();
            os.close();

            // Load the cascade classifier
            cascadeClassifier = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            cascadeClassifier.load(mCascadeFile.getAbsolutePath());
            if (cascadeClassifier.empty()) {
                Log.e("dd", "Failed to load cascade classifier");
                cascadeClassifier = null;
            }

        } catch (Exception e) {
            Log.e("OpenCVActivity", "Error loading cascade", e);
        }

        return cascadeClassifier;
    }

    private byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (iVal < 0 ? 0 : iVal);
        return (byte) iVal;
    }

    public static void getAverage(Mat matImg) {

        double[] ft1 = matImg.get(50, 50);
        for (int i = 0; i < ft1.length; i++) {
            System.out.println(ft1[i]);

        }

        double[] g = {0.0, 25.0, 89.0};
        matImg.put(50, 50, g);

        double[] ft2 = matImg.get(50, 50);
        for (int i = 0; i < ft2.length; i++) {
            System.out.println(ft2[i]);

        }

        /*

        int count = 0;
        double av = 0.0;
        for (int row = 0; row < 100; row++) {

            for (int col = 0; col < 100; col++) {

                count++;

                double[] ft1 = matImg.get(row, col);

                double bf1 = ft1[0];

                int u = (int) bf1;

                av = av + bf1;

                System.out.print(u + ",");

                //   infoTw.append(bf1 + " , ");
            }
            System.out.println("\n");
            // System.out.println("--------" + count + "-----------");
            // infoTw.append("\n");
        }

*/
        // double[] ft2 = matImg.get(50, 0);
        //  double Lbf2 = ft2[0];
        //  double[] ft3 = matImg.get(50, 75);
        // double Rbf2 = ft3[0];
        //  System.out.println("COUNT = " + count);
        //  System.out.println("AVERAGE = " + av / count);
        //  System.out.println("LEFT = " + Lbf2);
        //  System.out.println("RIGHT = " + Rbf2);


    }

    public static Mat setBlack(Mat matImg) {

        //  int count = 0;
        //  double av = 0.0;
        for (int row = 0; row < 100; row++) {

            for (int col = 0; col < 100; col++) {

                //  count++;

                double[] ft1 = matImg.get(row, col);

                double bf1 = ft1[0];
                //  double bf2 = ft1[1];
                //   double bf3 = ft1[2];

                if (bf1 > 105.0) {

                    bf1 = 0.0;

                    //  double[] ft2 = {bf1, bf2, bf3};

                } else {
                    bf1 = 255.0;
                    //  matImg.put(row, col, bf1);
                }
                matImg.put(row, col, bf1);
            }
        }
        return matImg;
    }

    public static Mat setBlack2(Mat matImg) {

        //  int count = 0;
        //  double av = 0.0;
        for (int row = 0; row < 100; row++) {

            for (int col = 0; col < 189; col++) {

                //  count++;

                double[] ft1 = matImg.get(row, col);

                double bf1 = ft1[0];
                //  double bf2 = ft1[1];
                //   double bf3 = ft1[2];

                if (bf1 > 105.0) {

                    bf1 = 0.0;

                } else {
                    bf1 = 255.0;
                }
                matImg.put(row, col, bf1);
            }
        }
        return matImg;
    }

    Mat matImg1, matImg2, matImg3, matImg4;

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
            matImg3 = mat;
        }
        if (mark == 3) {
            iV4.setImageBitmap(bitmap);
            matImg4 = mat;
        }
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

    // method to get pixel value
    public void didIt(Mat mImage, int aRow, int aCol) {
        //  mImage.convertTo(mImage, CvType.CV_8UC1);
        double[] ft = mImage.get(aRow, aCol);

        for (int i = 0; i < 10; i++) {

            double[] ft3 = mImage.get(10 + i, 20 + i);
            double b = ft3[0];
            infoTw.append(b + " , ");
        }
    }

    public void didIt(Mat mImage) {
        //  mImage.convertTo(mImage, CvType.CV_8UC1);
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {

                double[] ft1 = mImage.get(k, i);
                double bf1 = ft1[0];
                infoTw.append(bf1 + " , ");
            }
            infoTw.append("\n");
        }
        infoTw.append("\n");
        infoTw.append("\n");
    }


    public void actionAny(View view, Mat countImg) {


        //  Mat kernel = new Mat();
        // Mat ones = Mat.ones(3, 3, CvType.CV_32F);
        // Core.multiply(ones, new Scalar(1 / (double) (3 * 3)), kernel);

        // int rows1 = mImage.rows();
        // int clmns1 = mImage.cols();


        for (int i = 0; i < 5; i++) {


            for (int k = 0; k < 5; k++) {

                double[] ft1 = matImg1.get(k, i);
                double bf1 = ft1[0];
                infoTw.append(bf1 + " , ");

            }
            infoTw.append("\n");

        }
        infoTw.append("\n");
        infoTw.append("\n");

        for (int i = 0; i < 5; i++) {


            for (int k = 0; k < 5; k++) {

                double[] ft1 = matImg2.get(k, i);
                double bf1 = ft1[0];
                infoTw.append(bf1 + " , ");

            }
            infoTw.append("\n");

        }
        infoTw.append("\n");
        infoTw.append("\n");
        // double[] ft1 = kernel.get(1, 1);
        // double bf1 = ft1[0];//rows = 4160 clmns = 3120


        //  double g =  ft[1];
        //  double r =  ft[2];
        //  changedImg.get(50, 50);

        //  long s = (int) changedImg.total();


        // infoTw.setText("" + bf1);
        // infoTw.setText(rows + " " + clmns + " " + chn + " " + "b = " + b + " g = " + g +  " r = " + r );
        // infoTw.setText(rows + " " + clmns + " " + chn + " " + "b = " + b );


    }

    //
    public void summary(View view) {

        getCoordinateArrays(helpImg);

        //  TestClass.testCalculate();

        getColorData();


        // infoTw.setText(Dx1+" , " +Dx2+" , " +Dy1+" , " +Dy1+" , " +d);
        //  Point start = new Point(100, 100);
        // Point end = new Point(300, 100);
        // Imgproc.line(greyImage, start, end, new Scalar(255, 0, 0), 1);
        // displayImage(greyImage);
        // didIt(greyImage, 100, 700);
        // Mat matImg3 = new Mat();
        //   int r = greyImage.rows();
        //  int c = greyImage.cols();
        //   infoTw.setText(r + " , " + c);
        // Core.addWeighted(matImg1, 0.7, matImg2, 0.3, 0.0, matImg3);
        // Core.add(matImg1, matImg2, matImg3);

        //  double[] dVal = {255.0, 255.0, 255.0};


        //  helpImg.put(622,189,dVal);//

        //int[] xC = {189, 190, 191, 192, 193, 194, 195, 196, 197, 198, 199, 200, 201};
        // int[] yC = {622, 622, 623, 623, 623, 623, 623, 623, 623, 623, 622, 622, 622};


        ///    getCordinateArrays(helpImg);

        Mat nMat = retMat(helpImg);
        for (int y = 0; y < xCoo.size(); y++) {
            double[] dVal2 = {DCol1.get(y), DCol2.get(y), DCol2.get(y)};
            nMat.put(yCoo.get(y), xCoo.get(y), dVal2);
        }


        displayImage(helpImg);

    }

    public void getColorData() {
        for (int i = 0; i < xCoo.size(); i++) {
            double[] vFull = oImage2.get(yCoo.get(i), xCoo.get(i));
            DCol1.add(vFull[0]);
            DCol2.add(vFull[1]);
            DCol3.add(vFull[2]);
        }
    }

    public void getCoordinateArrays(Mat pImg) {

        Imgproc.blur(pImg, pImg, new Size(3, 3));
        Imgproc.blur(pImg, pImg, new Size(3, 3));
        // Imgproc.blur(pImg, pImg, new Size(3, 3));
        // Imgproc.blur(pImg, pImg, new Size(3, 3));
        for (int r = 0; r < pImg.rows(); r++) {
            for (int c = 0; c < pImg.cols(); c++) {
                double[] sc = pImg.get(r, c);
                if ((sc[0] == blueVal) && (sc[1] == greenVal) && (sc[2] == redVal)) {
                    xCoo.add(c);
                    yCoo.add(r);
                }
            }
        }
        //System.out.println(88888888);
        //System.out.println(xCoo.size());
        // System.out.println(yCoo.size());
    }

    public void draftpolygon(Mat mImg) {

        for (int r = 0; r < mImg.rows(); r++) {
            for (int c = 0; c < mImg.cols(); c++) {
                double[] dVal = {100.0, 100.0, 100.0};
                mImg.put(r, c, dVal);
            }
        }

        int lineType = 1;
        int shift = 0;

        Point[] rook_points = new Point[1252];
        for (int i = 0; i < xCorC.size(); i++) {
            rook_points[i] = new Point(xCorC.get(i), yCorC.get(i));

        }
        ///rook_points[0] = new Point(200, 400);
        ///rook_points[1] = new Point(600, 450);
        /// rook_points[2] = new Point(450, 750);
        ///rook_points[3] = new Point(700, 750);
        ///rook_points[4] = new Point(600, 850);
        ///rook_points[5] = new Point(250, 800);
        ///rook_points[6] = new Point(100, 500);
        ///rook_points[7] = new Point(200, 400);
        ///rook_points[8] = new Point(600, 450);

        MatOfPoint matPt = new MatOfPoint();
        matPt.fromArray(rook_points);
        List<MatOfPoint> ppt = new ArrayList<MatOfPoint>();
        ppt.add(matPt);

        Imgproc.fillPoly(mImg, ppt, new Scalar(178, 149, 12), lineType, shift, new Point(0, 0));


    }

    public Mat drawLine(Mat img) {

        Point start = new Point(100, 150);
        Point end = new Point(200, 150);
        Point start2 = new Point(100, 150);
        Point end2 = new Point(100, 350);
        Point start3 = new Point(100, 350);
        Point end3 = new Point(200, 350);
        Point start4 = new Point(200, 350);
        Point end4 = new Point(200, 150);
        Imgproc.line(img, start, end, new Scalar(0, 0, 0), 2);
        Imgproc.line(img, start2, end2, new Scalar(0, 0, 0), 2);
        Imgproc.line(img, start3, end3, new Scalar(0, 0, 0), 2);
        Imgproc.line(img, start4, end4, new Scalar(0, 0, 0), 2);
        return img;

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

