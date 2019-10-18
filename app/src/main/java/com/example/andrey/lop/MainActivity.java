package com.example.andrey.lop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
//import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.lop.CustomView.DrawRect;
import com.example.andrey.lop.CustomView.MyImageView;
import com.example.andrey.lop.ImageActions.ContourImage;
import com.example.andrey.lop.ImageActions.ErodeImage;
import com.example.andrey.lop.ImageActions.FromWhiteToBlackBackgrnd;
import com.example.andrey.lop.ImageActions.GrayImage;
import com.example.andrey.lop.ImageActions.LabClass;
import com.example.andrey.lop.ImageActions.OriginalImage;
import com.github.chrisbanes.photoview.PhotoView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.andrey.lop.ChangeValues.mChangeToCanny;
import static com.example.andrey.lop.ChangeValues.mDownUpChangeROI;
import static com.example.andrey.lop.ChangeValues.mLeftRightChangeROI;
import static com.example.andrey.lop.ChangeValues.mRightLeftChangeROI;
import static com.example.andrey.lop.ChangeValues.mUpDownChangeROI;
import static com.example.andrey.lop.CustomView.DrawRect.xGreen;
import static com.example.andrey.lop.CustomView.DrawRect.xOrg;
import static com.example.andrey.lop.CustomView.DrawRect.xRed;
import static com.example.andrey.lop.CustomView.DrawRect.xYell;
import static com.example.andrey.lop.CustomView.DrawRect.yGreen;
import static com.example.andrey.lop.CustomView.DrawRect.yOrg;
import static com.example.andrey.lop.CustomView.DrawRect.yRed;
import static com.example.andrey.lop.CustomView.DrawRect.yYell;
import static com.example.andrey.lop.ImageActions.FindContourImage.xCorC;
import static com.example.andrey.lop.ImageActions.FindContourImage.yCorC;
import static com.example.andrey.lop.ImageActions.LabClass.all_A_Values;
import static com.example.andrey.lop.ImageActions.LabClass.all_B_Values;
import static com.example.andrey.lop.ImageActions.LabClass.all_C_Values;
import static com.example.andrey.lop.ImageActions.LabClass.checkPreconditions;
import static com.example.andrey.lop.ImageActions.LabClass.maxIntense;
import static com.example.andrey.lop.ImageActions.LabClass.minIntense;
import static com.example.andrey.lop.ImageActions.LabClass.rMax;
import static com.example.andrey.lop.ImageActions.LabClass.rMin;
import static com.example.andrey.lop.ImageActions.LabClass.x_Cor;
import static com.example.andrey.lop.ImageActions.LabClass.y_Cor;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<Integer> xCoo = new ArrayList<>();
    public static ArrayList<Integer> yCoo = new ArrayList<>();

    public static ArrayList<Double> DCol1 = new ArrayList<>();
    public static ArrayList<Double> DCol2 = new ArrayList<>();
    public static ArrayList<Double> DCol3 = new ArrayList<>();

    public static ArrayList<Integer> a = new ArrayList<Integer>();
    public static ArrayList<Integer> i = new ArrayList<>();


    public static ArrayList<Integer> hueVal = new ArrayList<Integer>();
    public static ArrayList<Integer> satVal = new ArrayList<>();
    public static ArrayList<Integer> valVal = new ArrayList<>();

    public static ArrayList<Integer> all_A_ValuesFromROI = new ArrayList<Integer>();
    public static ArrayList<Integer> all_B_ValuesFromROI = new ArrayList<Integer>();
    public static ArrayList<Integer> all_C_ValuesFromROI = new ArrayList<Integer>();

    public static ArrayList<Integer> x_CorFromROI = new ArrayList<Integer>();
    public static ArrayList<Integer> y_CorFromROI = new ArrayList<Integer>();

    public static ArrayList<Integer> full_x_ROIcoord = new ArrayList<>();
    public static ArrayList<Integer> full_y_ROIcoord = new ArrayList<>();

    public static double blueVal, greenVal, redVal;

    public static int maxAfromROI, minAfromROI, rMinfromROI, rMaxfromROI, minIntensefromROI, maxIntensefromROI, min_A, max_A, min_Intense, max_Intense;

    ImageView iV2, iV3, iV4;
    Button bttn, bttn2, bttn3, bttn4, bttn5, bttn6, bttn7, bttn8, bttn9, bttn10, bttn11, bttn12, bttn13, bttn14, bttn24;
    TextView infoTw1, infoTw2, infoTw3, infoTw4, infoTw5, infoTw6, infoTw7, infoTw8, infoTw9, infoTw10, infoTw11, infoTw12;
    static int mark = 0;
    static int mark2 = 0;
    static int m = 3;
    public static int screenWidth, idealWidth, idealHeight, originalHeight, originalWidth;
    public static int thres_1 = 100, thres_2 = 200;
    Mat oImage, oImage2, _oImage, _oImage2, zoomMat, helpImg, houghCirculeImage, greyImage, greyImage2, cannyImage, gaussianImage, filter2DImage_2, filter2DImage, preImg, preImg_2, anPreImg, labImg;
    int tX, tY;
    double Dx1, Dx2, Dy1, Dy2;
    int d;
    Bitmap zoomedBitmap, bitmapS, bitmapS2;
    public static String path;
    public static CascadeClassifier cascadeClassifier;
    int pon = 0;
    PhotoView photoView;
    View view3;
    MyImageView iV;
    GestureDetectorCompat mGestureDetector;

    //  Mat sampledImg = new Mat();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("opencv_java3");

        OpenCVLoader.initDebug();


        bttn7 = findViewById(R.id.button7);
        bttn10 = findViewById(R.id.button10);
        bttn11 = findViewById(R.id.button11);
        bttn12 = findViewById(R.id.button12);
        bttn13 = findViewById(R.id.button13);
        bttn14 = findViewById(R.id.button14);
        bttn24 = findViewById(R.id.button24);
        infoTw1 = findViewById(R.id.tW1);
        infoTw2 = findViewById(R.id.tW2);
        infoTw3 = findViewById(R.id.tW3);
        infoTw4 = findViewById(R.id.tW4);
        infoTw5 = findViewById(R.id.tW5);
        infoTw6 = findViewById(R.id.tW6);
        infoTw7 = findViewById(R.id.tW7);
        infoTw8 = findViewById(R.id.tW8);
        infoTw9 = findViewById(R.id.tW9);
        infoTw10 = findViewById(R.id.tW10);
        infoTw11 = findViewById(R.id.tW11);
        infoTw12 = findViewById(R.id.tW12);
        iV = findViewById(R.id.imageV);
        view3 = findViewById(R.id.imageV5);


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

            //   _oImage = photoView.setImageURI();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;

            oImage = OriginalImage.getRequiredSizeImage(path);
            // OriginalImage.calculateRequiredSize(path);
            // adopt image to screen size
            //  oImage = OriginalImage.GetAdoptedImage(path);
            oImage2 = OriginalImage.GetAdoptedImage(path);
/*
            Point centerHandR = new Point(230.0, 875.0);
            Imgproc.ellipse(oImage, centerHandR, new Size(65, 65), 0, 0, 360,
                    new Scalar(255, 0, 255), 2);

            Point centerHandL = new Point(500.0, 870.0);
            Imgproc.ellipse(oImage, centerHandL, new Size(60, 60), 0, 0, 360,
                    new Scalar(255, 0, 255), 2);

            Point centerHead = new Point(360.0, 300.0);
            Imgproc.ellipse(oImage, centerHead, new Size(110, 110), 0, 0, 360,
                    new Scalar(255, 0, 255), 2);
*/

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

                    ///AFTER THIS NOTE IS CODE WHICH FINDS SHORTS
                    /*
                     *LabClass.calculate(oImage);
                     *preImg = changeColor(preImg);
                     *anPreImg = ErodeImage.getErodeImage(preImg);
                     *helpImg = ContourImage.getMainContourImage(anPreImg);
                     *displayImage(helpImg);
                     */

                    /*
                    // block to erase background
                    LabClass.upDownStage(oImage);
                    preImg = changeColor(oImage);

                    LabClass.leftRightStage(preImg);
                    preImg = changeColor(preImg);

                    LabClass.rightLeftStage(preImg);
                    preImg = changeColor(preImg);

                    LabClass.downUpStage(preImg);
                    preImg = changeColor(preImg);
                    // end block

                    */

                    // get value of background color
                    //// checkPreconditions(oImage);
//by default here should be oImage,oImage2 is set because of surrounded hands
/*
                    checkPreconditions(oImage2);

                    infoTw2.append(Integer.toString(rMin));
                    infoTw4.append(Integer.toString(rMax));
                    infoTw6.append(Integer.toString(minIntense));
                    infoTw8.append(Integer.toString(maxIntense));
                    infoTw10.append(Integer.toString(thres_1));
                    infoTw12.append(Integer.toString(thres_2));
                    setActualValues();
*/
                    view3.setVisibility(View.INVISIBLE);

                    displayImage(oImage);


                    ///   LabClass.leftRightStage(oImage);

                    //    preImg = changeColor(preImg);

                    ///LabClass.calculate(oImage);

                    //LabClass.leftRightStage(preImg);

                    // preImg = changeColor(preImg);


                    ///anPreImg = ErodeImage.getErodeImage(preImg);

                    /// helpImg = ContourImage.getMainContourImage(anPreImg);

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

    public static Mat changeColorFromROI(Mat inputImg) {


        for (int i = 0; i < x_Cor.size(); i++) {
            double[] g = {255.0, 255.0, 255.0};
            inputImg.put(y_Cor.get(i) + yRed, x_Cor.get(i) + xRed, g);
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

    private void displayImage(Mat mat, int num) {

        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);

        Utils.matToBitmap(mat, bitmap);

        if (num == 1) {
            iV.setImageBitmap(bitmap);
        } else {
            return;
        }

    }

    private void displayImage(Mat mat) {

        bitmapS = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);


        Utils.matToBitmap(mat, bitmapS);
        iV.setMinimumWidth(idealWidth);
        iV.setMaxWidth(idealWidth);
        iV.setMinimumHeight(idealHeight);
        iV.setMaxHeight(idealHeight);
        // System.out.println("Start");
        //System.out.println(iV.getWidth());
        // System.out.println(iV.getHeight());

        if (mark == 0) {

            iV.setImageBitmap(bitmapS);


         /*   iV.setOnTouchListener(new View.OnTouchListener() {

                                      //   int x = (int) event.getX();
                                      //    int y = (int) event.getY();
                                      @Override
                                      public boolean onTouch(View v, MotionEvent event) {

                                          int ad = (screenWidth - idealWidth) / 2;
                                          int x = (int) event.getX() - ad;
                                          int y = (int) event.getY();
                                          System.out.println("Current");
                                          System.out.println(x * 4);
                                          System.out.println(y * 4);

                  /*
                    Rect rectCrop = new Rect(x, y, 600, 800);
                    Mat imageROI = oImage.submat(rectCrop);
                    bitmapS2 = Bitmap.createBitmap(imageROI.cols(), imageROI.rows(), Bitmap.Config.RGB_565);
                    Utils.matToBitmap(imageROI, bitmapS2);
                    iV.setImageBitmap(bitmapS2);

*/
         /*
                                          return true;
                                      }
                                  }
            );
            */


////
            /*
            iV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_custom_layout, null);
                    PhotoView photoView = mView.findViewById(R.id.imageView);
                    photoView.setImageBitmap(bitmapS);
                    mBuilder.setView(mView);
                    AlertDialog mDialog = mBuilder.create();
                    mDialog.show();

                }
            });
            */
/////
            matImg1 = mat;
        }
        if (mark == 1) {
            iV2.setImageBitmap(bitmapS);
            matImg2 = mat;
        }
        if (mark == 2) {
            iV3.setImageBitmap(bitmapS);
            matImg3 = mat;
            mark = 3;
        }
        if (mark == 3) {
            iV4.setImageBitmap(bitmapS);
            matImg4 = mat;
        }
        // mark++;
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
/*
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
*/

    public void actionAny(View view) {

        if (pon == 0) {

            anPreImg = ErodeImage.getErodeImage(preImg, 5, 5, 2, 2);
            anPreImg = ErodeImage.getErodeImage(anPreImg, 5, 5, 2, 2);
            anPreImg = ErodeImage.getErodeImage(anPreImg, 5, 5, 2, 2);
            anPreImg = ErodeImage.getErodeImage(anPreImg, 5, 5, 2, 2);
            helpImg = ContourImage.getMainContourImage(anPreImg);
            pon = 1;
            displayImage(helpImg);


        } else if (pon == 1) {

            anPreImg = ErodeImage.getErodeImage(preImg, 11, 11, 5, 5);
            helpImg = ContourImage.getMainContourImage(anPreImg);
            displayImage(helpImg);

            pon = 2;

        } else {

            LabClass.downUpStage(preImg);

            preImg = changeColor(preImg);

            displayImage(preImg);

            pon = 2;

        }

        //  Mat kernel = new Mat();
        // Mat ones = Mat.ones(3, 3, CvType.CV_32F);
        // Core.multiply(ones, new Scalar(1 / (double) (3 * 3)), kernel);

        // int rows1 = mImage.rows();
        // int clmns1 = mImage.cols();
/*

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

*/
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

    public void getRoiXy(View view) {
        DrawRect.getCoord();
    }

    public void getFullRoiXy(View view) {

        mFullRoiXy();
    }

    public static void mFullRoiXy() {

        if (xRed < 0 || xRed > 750 || yRed < 0 || yRed > 1000 ||
                xOrg < 0 || xOrg > 750 || yOrg < 0 || yOrg > 1000 ||
                xYell < 0 || xYell > 750 || yYell < 0 || yYell > 1000 ||
                xGreen < 0 || xGreen > 750 || yGreen < 0 || yGreen > 1000) {
            return;
        } else {
            for (int x = xRed; x <= xYell; x++) {
                for (int y = yRed; y <= yYell; y++) {
                    full_x_ROIcoord.add(x);
                    full_y_ROIcoord.add(y);
                }
            }
        }
        System.out.println("x_full " + full_x_ROIcoord.size());
        System.out.println("y_full " + full_y_ROIcoord.size());
    }


    public void innerChng(View view) {


        DrawRect.getCoord();

        Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_BGR2Lab);

        // codeBlock change color upDown
        for (int x = xRed; x < (xYell + 1); x++) {
            for (int y = yRed; y < (yYell + 1); y++) {
                double[] joy = oImage.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                }
            }
        }

        Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_Lab2BGR);
        changeColor(oImage);
        displayImage(oImage, 1);


    }

    // try to change image in HSV format
    public void getColorRoi(View view) {

        DrawRect.getCoord();
        ///Imgproc.cvtColor(preImg, labImg2, Imgproc.COLOR_BGR2Lab);

        ///  Mat labImg2 = new Mat();
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(preImg, hsvMat, Imgproc.COLOR_BGR2HSV);


        for (int x = xRed; x < (xYell + 1); x++) {

            for (int y = yRed; y < (yYell + 1); y++) {

                double[] joy = hsvMat.get(y, x);
                int vHue = (int) joy[0];
                int vSat = (int) joy[1];
                int vValue = (int) joy[2];


                satVal.add(vSat);
                valVal.add(vValue);
                hueVal.add(vHue);

            }
        }
        System.out.println("Size of satVal array " + satVal.size());
        System.out.println("Size of valVal array " + valVal.size());
        System.out.println("Size of hueVal array " + hueVal.size());
        System.out.println("Max of satVal array " + getMaxFromArray(satVal));
        System.out.println("Min of satVal array " + getMinFromArray(satVal));
        System.out.println("Max of valVal array " + getMaxFromArray(valVal));
        System.out.println("Min of valVal array " + getMinFromArray(valVal));
        System.out.println("Max of hueVal array " + getMaxFromArray(hueVal));
        System.out.println("Min of hueVal array " + getMinFromArray(hueVal));
    }

    public int getMaxFromArray(ArrayList<Integer> aL) {
        int y = 0;
        for (int x : aL) {
            if (x > y) {
                y = x;
            }
        }
        return y;
    }

    public int getMinFromArray(ArrayList<Integer> aL) {
        int y = 256;
        for (int x : aL) {
            if (x < y) {
                y = x;
            }
        }
        return y;
    }

    public void jackStay(View view) {

        maxAfromROI = a.get(0);
        minAfromROI = a.get(0);

        for (int y : a) {
            if (y >= maxAfromROI) {
                maxAfromROI = y;
            }
            if (y <= minAfromROI) {
                minAfromROI = y;
            }
        }

        maxIntensefromROI = i.get(0);
        minIntensefromROI = i.get(0);

        for (int y : i) {
            if (y >= maxIntensefromROI) {
                maxIntensefromROI = y;
            }
            if (y <= minIntensefromROI) {
                minIntensefromROI = y;
            }
        }
        System.out.println("MinAfromROI");
        System.out.println(minAfromROI);
        System.out.println("MaxAfromROI");
        System.out.println(maxAfromROI);
        System.out.println("MaxIntensefromROI");
        System.out.println(maxIntensefromROI);
        System.out.println("MinIntensefromROI");
        System.out.println(minIntensefromROI);

        for (int i = minAfromROI; i <= maxAfromROI; i++) {
            all_A_ValuesFromROI.add(i);
            all_B_ValuesFromROI.add(i);
        }

        for (int i = minIntensefromROI; i <= maxIntensefromROI; i++) {
            all_C_ValuesFromROI.add(i);
        }

        Imgproc.cvtColor(preImg, preImg, Imgproc.COLOR_BGR2Lab);

        for (int x = 0; x < preImg.cols(); x++) {
            for (int y = 0; y < preImg.rows(); y++) {
                double[] joy = preImg.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_ValuesFromROI.contains(aVal) && all_B_ValuesFromROI.contains(bVal) && all_C_ValuesFromROI.contains(cVal)) {
                    x_CorFromROI.add(x);
                    y_CorFromROI.add(y);
                }
            }
        }
        Imgproc.cvtColor(preImg, preImg, Imgproc.COLOR_Lab2BGR);

        // change color
        for (int i = 0; i < x_CorFromROI.size(); i++) {
            double[] g = {255.0, 255.0, 255.0};
            preImg.put(y_CorFromROI.get(i), x_CorFromROI.get(i), g);
        }

        x_CorFromROI.clear();
        y_CorFromROI.clear();

        displayImage(preImg, 1);
    }

    // use button
    public void changeColorRoiBgrnd(View view) {

        DrawRect.getCoord();

        Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_BGR2Lab);

        // codeBlock change color upDown
        for (int x = xRed; x < (xYell + 1); x++) {
            for (int y = yRed; y < (yYell + 1); y++) {
                double[] joy = oImage.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else {
                    break;
                }
            }
        }
        Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_Lab2BGR);
        System.out.println("upDownStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());

        changeColor(oImage);
        Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_BGR2Lab);
        // codeBlock change color leftRight
        int sMark_2 = 0;
        for (int y = yRed; y < (yYell + 1); y++) {
            for (int x = xRed; x < (xYell + 1); x--) {
                double[] joy = oImage.get(y, x);
                int cVal = (int) joy[0];
                int aVal = (int) joy[1];
                int bVal = (int) joy[2];
                if (all_A_Values.contains(aVal) && all_B_Values.contains(bVal) && all_C_Values.contains(cVal)) {
                    x_Cor.add(x);
                    y_Cor.add(y);
                } else if (aVal == 128 && bVal == 128 && cVal == 255) {
                    sMark_2 = 1;
                } else {
                    break;
                }
            }
        }

        Imgproc.cvtColor(oImage, oImage, Imgproc.COLOR_Lab2BGR);
        System.out.println("upDownStage completed");
        System.out.println("x_Cor size: " + x_Cor.size());
        changeColor(oImage);
        displayImage(oImage, 1);

    }

    // create/save zoomed bitmap for later use
    public void createZoomedBitmap() {

        iV.setDrawingCacheEnabled(true);
        iV.buildDrawingCache(true);
        zoomedBitmap = Bitmap.createScaledBitmap(iV.getDrawingCache(true), iV.getWidth(), iV.getHeight(), true);
        iV.setDrawingCacheEnabled(false);
    }

    public void getZoomedBitmap(View view) {


        createZoomedBitmap();

        // START creating directory for images
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/App_images");
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        // STOP creating directory for images
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        String zoomedImgPath = file.toString();
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            System.out.println(155);
            // instead of 'zoomedBitmap' should be another
            zoomedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), zoomedImgPath + " is saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        zoomMat = new Mat();
        zoomMat = OriginalImage.GetImage(zoomedImgPath);
        //Utils.bitmapToMat(zoomedBitmap, zoomMat);


/*
        if(zoomedBitmap!=null){
            Toast.makeText(getApplicationContext(), "ZoomedBitmap created", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(), "No zoomedBitmap", Toast.LENGTH_SHORT).show();
        }
        zoomMat = new Mat();
       // Mat src = new Mat();
        Utils.bitmapToMat(zoomedBitmap, zoomMat);

        System.out.println("Height - " + zoomMat.rows());
        System.out.println("Width - " + zoomMat.cols());
        */
    }

    public void saveImg(View view) {
        //
        //  view3.setVisibility(View.INVISIBLE);

        // START creating directory for images
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/App_images");
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        // STOP creating directory for images
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        String imgPath = file.toString();
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            System.out.println(155);
            // instead of 'zoomedBitmap' should be another
            bitmapS.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Toast.makeText(getApplicationContext(), imgPath + " is saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void plusMin_A(View view) {
        int min = Integer.valueOf(String.valueOf(infoTw2.getText()));
        min++;
        infoTw2.setText(String.valueOf(min));
        setActualValues();
    }

    public void minusMin_A(View view) {
        int min = Integer.valueOf(String.valueOf(infoTw2.getText()));
        min--;
        infoTw2.setText(String.valueOf(min));
        setActualValues();
    }

    public void plusMax_A(View view) {
        int max = Integer.valueOf(String.valueOf(infoTw4.getText()));
        max++;
        infoTw4.setText(String.valueOf(max));
        setActualValues();
    }

    public void minusMax_A(View view) {
        int max = Integer.valueOf(String.valueOf(infoTw4.getText()));
        max--;
        infoTw4.setText(String.valueOf(max));
        setActualValues();
    }

    public void plusMinIntense(View view) {
        int min = Integer.valueOf(String.valueOf(infoTw6.getText()));
        min++;
        infoTw6.setText(String.valueOf(min));
        setActualValues();
    }

    public void minusMinIntense(View view) {
        int min = Integer.valueOf(String.valueOf(infoTw6.getText()));
        min--;
        infoTw6.setText(String.valueOf(min));
        setActualValues();
    }

    public void plusMaxIntense(View view) {
        int max = Integer.valueOf(String.valueOf(infoTw8.getText()));
        max++;
        infoTw8.setText(String.valueOf(max));
        setActualValues();
    }

    public void minusMaxIntense(View view) {
        int max = Integer.valueOf(String.valueOf(infoTw8.getText()));
        max--;
        infoTw8.setText(String.valueOf(max));
        setActualValues();
    }

    public void plusTr1(View view) {
        int min = Integer.valueOf(String.valueOf(infoTw10.getText()));
        min++;
        infoTw10.setText(String.valueOf(min));
        setActualValues();
    }

    public void minusTr1(View view) {
        int min = Integer.valueOf(String.valueOf(infoTw10.getText()));
        min--;
        infoTw10.setText(String.valueOf(min));
        setActualValues();
    }

    public void plusTr2(View view) {
        int max = Integer.valueOf(String.valueOf(infoTw12.getText()));
        max++;
        infoTw12.setText(String.valueOf(max));
        setActualValues();
    }

    public void minusTr2(View view) {
        int max = Integer.valueOf(String.valueOf(infoTw12.getText()));
        max--;
        infoTw12.setText(String.valueOf(max));
        setActualValues();
    }

    // get actual values for using
    public void setActualValues() {
        min_A = Integer.valueOf(String.valueOf(infoTw2.getText()));
        max_A = Integer.valueOf(String.valueOf(infoTw4.getText()));
        min_Intense = Integer.valueOf(String.valueOf(infoTw6.getText()));
        max_Intense = Integer.valueOf(String.valueOf(infoTw8.getText()));
        thres_1 = Integer.valueOf(String.valueOf(infoTw10.getText()));
        thres_2 = Integer.valueOf(String.valueOf(infoTw12.getText()));
    }

    public void upDownChange(View view) {
        // mUpDownChangeROI(zoomMat, min_A, max_A, min_Intense, max_Intense);
        displayImage(mUpDownChangeROI(zoomMat, min_A, max_A, min_Intense, max_Intense));
        ///   displayImage(mUpDownChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        ///   oImage2 = oImage;
    }

    public void downUpChange(View view) {
        displayImage(mDownUpChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        oImage2 = oImage;
    }

    public void rightLeftChange(View view) {
        displayImage(mRightLeftChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        oImage2 = oImage;
    }

    public void leftRightChange(View view) {
        displayImage(mLeftRightChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        oImage2 = oImage;
    }

    public void changeToCanny(View view) {

        if (m % 2 != 0) {
            view3.setVisibility(View.VISIBLE);
            m++;
        } else {
            view3.setVisibility(View.INVISIBLE);
            m++;
        }
        // displayImage(mChangeToCanny(oImage, oImage2, thres_1, thres_2));
    }

    public void getContour(View view) {
        // Mat m =   mUpDownChangeROI(oImage, min_A, max_A, min_Intense, max_Intense);
        // after this is required code
        displayImage(ContourImage.getMainContourFromROI(oImage, oImage2));


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

