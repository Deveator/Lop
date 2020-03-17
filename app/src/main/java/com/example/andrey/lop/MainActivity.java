package com.example.andrey.lop;

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

import java.util.concurrent.ThreadLocalRandom;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andrey.lop.CustomView.DrawRect;
import com.example.andrey.lop.CustomView.MyImageView;
import com.example.andrey.lop.ImageActions.BgrComputeImg;
import com.example.andrey.lop.ImageActions.CannyImage;
import com.example.andrey.lop.ImageActions.ContrastImg;
import com.example.andrey.lop.ImageActions.ContourImage;
import com.example.andrey.lop.ImageActions.ErodeImage;
import com.example.andrey.lop.ImageActions.FaceDetect;
import com.example.andrey.lop.ImageActions.FromWhiteToBlackBackgrnd;
import com.example.andrey.lop.ImageActions.GammaImg;
import com.example.andrey.lop.ImageActions.GaussianImage;
import com.example.andrey.lop.ImageActions.GrayImage;
import com.example.andrey.lop.ImageActions.LabClass;
import com.example.andrey.lop.ImageActions.LabImg;
import com.example.andrey.lop.ImageActions.MorfOperations;
import com.example.andrey.lop.ImageActions.NextLabImg;
import com.example.andrey.lop.ImageActions.OriginalImage;
import com.example.andrey.lop.ImageActions.ScharrImage;
import com.example.andrey.lop.ImageActions.ShiTomasiCornerDet;
import com.example.andrey.lop.ImageActions.SobelImage;
import com.github.chrisbanes.photoview.PhotoView;

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
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
import static com.example.andrey.lop.CustomView.MyImageView.doubleZoomedImage;
import static com.example.andrey.lop.ImageActions.ContourImage.contourCoordinatesX_Y_All;
import static com.example.andrey.lop.ImageActions.ContourImage.getColorLines;
import static com.example.andrey.lop.ImageActions.ContourImage.getContourLines;
import static com.example.andrey.lop.ImageActions.ContourImage.getMatFromROI;
import static com.example.andrey.lop.ImageActions.ContourImage.rgb_string_array;
import static com.example.andrey.lop.ImageActions.FindContourImage.xCorC;
import static com.example.andrey.lop.ImageActions.FindContourImage.yCorC;
import static com.example.andrey.lop.ImageActions.LabClass.all_A_Values;
import static com.example.andrey.lop.ImageActions.LabClass.all_B_Values;
import static com.example.andrey.lop.ImageActions.LabClass.all_C_Values;
import static com.example.andrey.lop.ImageActions.LabClass.checkPreconditions;
import static com.example.andrey.lop.ImageActions.LabClass.diagonal_1;
import static com.example.andrey.lop.ImageActions.LabClass.diagonal_2;
import static com.example.andrey.lop.ImageActions.LabClass.diagonal_3;
import static com.example.andrey.lop.ImageActions.LabClass.diagonal_4;
import static com.example.andrey.lop.ImageActions.LabClass.downUp;
import static com.example.andrey.lop.ImageActions.LabClass.findNumberOfLines;
import static com.example.andrey.lop.ImageActions.LabClass.leftRight;
import static com.example.andrey.lop.ImageActions.LabClass.rightLeft;
import static com.example.andrey.lop.ImageActions.LabClass.showBorderPointsArrays;
import static com.example.andrey.lop.ImageActions.LabClass.upDown;
import static com.example.andrey.lop.ImageActions.LabClass.x_Cor;
import static com.example.andrey.lop.ImageActions.LabClass.x_Cor_ROI;
import static com.example.andrey.lop.ImageActions.LabClass.y_Cor;
import static com.example.andrey.lop.ImageActions.LabClass.y_Cor_ROI;
import static com.example.andrey.lop.ImageActions.LabImg.all_diffXValues;
import static com.example.andrey.lop.ImageActions.LabImg.all_diffYValues;
import static com.example.andrey.lop.ImageActions.LabImg.diffXValues;
//import static com.example.andrey.lop.ImageActions.LabImg.diffXValues_All;
import static com.example.andrey.lop.ImageActions.LabImg.diffYValues;
//import static com.example.andrey.lop.ImageActions.LabImg.diffYValues_All;


public class MainActivity extends AppCompatActivity {

    public static ArrayList<Integer> xCoo = new ArrayList<>();
    public static ArrayList<Integer> yCoo = new ArrayList<>();

    public static ArrayList<Double> DCol1 = new ArrayList<>();
    public static ArrayList<Double> DCol2 = new ArrayList<>();
    public static ArrayList<Double> DCol3 = new ArrayList<>();

    public static ArrayList<double[]> originalImageColor = new ArrayList<>();

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

    public static ArrayList<ArrayList<ArrayList>> allColors_AL = new ArrayList<ArrayList<ArrayList>>();
    public static ArrayList<ArrayList> borderPoints_AL = new ArrayList<ArrayList>();
    public static ArrayList<String> linesDscrptn = new ArrayList<String>();
    public static ArrayList<String> difrntLinesDscrptn = new ArrayList<String>();
    public static ArrayList<ArrayList> difrntLines_indexes = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> difrntLines_coordinatesY = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> difrntLines_coordinatesX = new ArrayList<ArrayList>();

    public static ArrayList<Double> _1_clr = new ArrayList<Double>();
    public static ArrayList<Double> _2_clr = new ArrayList<Double>();
    public static ArrayList<Double> _3_clr = new ArrayList<Double>();

    public static ArrayList<Integer> y1 = new ArrayList<Integer>();
    public static ArrayList<Integer> x1 = new ArrayList<Integer>();

    public static ArrayList<ArrayList> yColorCoor = new ArrayList<ArrayList>();
    public static ArrayList<ArrayList> xColorCoor = new ArrayList<ArrayList>();

    public static ArrayList<ArrayList> colorDefiningAl = new ArrayList<ArrayList>();

    public static ArrayList<ArrayList> all_colorSquare = new ArrayList<ArrayList>();

    private static Random rnd = new Random(12345);

    public static double blueVal, greenVal, redVal;

    public static int maxAfromROI, minAfromROI, rMinfromROI, rMaxfromROI, minIntensefromROI, maxIntensefromROI, min_A, max_A, min_Intense, max_Intense;
    int clicks = 0;
    int colorBack = 0;

    ImageView iV2, iV3, iV4;
    Button bttn, bttn2, bttn3, bttn4, bttn5, bttn6, bttn7, bttn8, bttn9, bttn10, bttn11, bttn12, bttn13, bttn14, bttn24;
    TextView infoTw1, infoTw2, infoTw3, infoTw4, infoTw5, infoTw6, infoTw7, infoTw8, infoTw9, infoTw10, infoTw11, infoTw12;
    static int mark = 0;
    static int mark2 = 0;
    static int m = 3;
    static int m2 = 3;
    static int numLines, numColorSections;

    public static int _xFromROI;
    public static int _yFromROI;

    public static int screenWidth, idealWidth, idealHeight, originalHeight, originalWidth;
    public static int thres_1 = 100, thres_2 = 200;
    Mat oImage, oImage2, _oImage, _oImage2, zoomMat, helpImg, houghCirculeImage, greyImage, greyImage2, cannyImage, gaussianImage,
            filter2DImage_2, filter2DImage, preImg, preImg_2, labImg, dilateImage, erodeImage;
    int tX, tY;
    double Dx1, Dx2, Dy1, Dy2;
    int clickCount = 1;
    Bitmap zoomedBitmap, bitmapS, bitmapS2;
    public static String path;
    public static CascadeClassifier cascadeClassifier;
    int pon = 0;
    PhotoView photoView;
    View view3;
    MyImageView iV;
    GestureDetectorCompat mGestureDetector;
    public static Mat oImage3, anPreImg;
    public static Mat oImage3ClusterColored, imgROIfromClustered;
    public static int doubleTapCount = 3;
    double[] oColor;
    // public Mat matImgDst = new Mat();

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

        int y = 78;
        int u = 99;
        String combo = y + "." + u;
        double t = Double.valueOf(combo);
        System.out.println(t);


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
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            screenWidth = displayMetrics.widthPixels;
            oImage = OriginalImage.getRequiredSizeImage(path);

            // oImage3 is main image
            oImage3 = OriginalImage.GetResizedImage(path);
            oImage3ClusterColored = OriginalImage.GetResizedImage(path);///
            // OriginalImage.calculateRequiredSize(path);
            // adopt image to screen size
            //  oImage = OriginalImage.GetAdoptedImage(path);
            // oImage2 = OriginalImage.GetAdoptedImage(path);

            //   anPreImg = ErodeImage.getErodeImage(oImage,3,3,-1,-1);
            //dilateImage = DilateImage.getDilateImage(oImage);
            // erodeImage = ErodeImage.getErodeImage(oImage,3,3,-1,-1);
            // oImage = TwoD_image.GetTwoD_Image(oImage);

            // anPreImg = TwoD_image.GetTwoD_Image(oImage);
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

            try {
                InputStream is = getResources().openRawResource(R.raw.haarcascade_lowerbody);

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

                    //    oImage3 = GammaImg.getGammaImg(oImage3,0.5);
                    //  Imgproc.blur(oImage3,oImage3,new Size(3,3));
                    //Imgproc.blur(oImage3,oImage3,new Size(3,3));
                    //  oImage3 = ContrastImg.getContrastImg(oImage3, 1.7, 0);
                    // oImage3 = MorfOperations.getMorfGradientImg(oImage3, 7, 7, 3, 3);
                    //   oImage3 = ContrastImg.getContrastImg(oImage3, 1.7, -20);
                    // Imgproc.blur(oImage3,oImage3,new Size(3,3));
                    // oImage3 = MorfOperations.getMorfOpenImg(oImage3, 7, 7, 3, 3);


                    //  Imgproc.cvtColor(oImage3, oImage3, Imgproc.COLOR_BGR2Lab);
                    //  oImage3 = ContrastImg.getContrastImg(oImage3, 1.7, -20);
                    //  oImage3 = CannyImage.GetCannyImage(oImage3,50,200);
                    //   oImage3 = MorfOperations.getMorfOpenImg(oImage3, 7, 7, 3, 3);
                    //  oImage3 = TwoD_image.GetTwoD_Image(oImage3);

                    // main idea
                    // oImage3 = ErodeImage.getErodeImage(oImage3, 7,7,3,3);
                 //   Imgproc.line(oImage3, new Point(375, 500), new Point(375, 530), new Scalar(255.0, 255.0, 255.0), 3);
                    displayImage(oImage3);
                    ///LabImg.getClustersFromLabImg(oImage3);///IMAGE LAB START

                   /// NextLabImg.getClustersFromLabImg(oImage3);///

                    /// LabImg.check(oImage3);
                    //  displayImage(matImgDst);


                    ///   LabClass.leftRightStage(oImage);

                    //    preImg = changeColor(preImg);

                    ///LabClass.calculate(oImage);

                    //LabClass.leftRightStage(preImg);

                    // preImg = changeColor(preImg);

                    /// helpImg = ContourImage.getMainContourImage(anPreImg);

                    break;
                case 1:
                    Mat sMat = oImage.submat(200, 300, 0, 100);
                    Imgproc.blur(sMat, sMat, new Size(3, 3));
                    displayImage(setBlack(GrayImage.GetGrayImage(sMat)));
                    break;
                case 2:
                    Mat sMat2 = oImage.submat(200, 300, 101, 290);

                    Imgproc.blur(sMat2, sMat2, new Size(3, 3));

                    displayImage(setBlack2(GrayImage.GetGrayImage(sMat2)));
                    break;
                case 3:
                    Mat sMat3 = oImage.submat(100, 300, 291, 389);
                    displayImage(FromWhiteToBlackBackgrnd.getFromWhiteToBlackBackgrnd(sMat3));
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

        displayImage(FaceDetect.getFaceDetect(oImage3, cascadeClassifier));

/*
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
        */
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

        createColorSectionCoordinates();

        /*
        int max = Integer.valueOf(String.valueOf(infoTw4.getText()));
        max++;
        infoTw4.setText(String.valueOf(max));
        setActualValues();

         */
    }

    public void createColorSectionCoordinates() {

        ArrayList<Integer> aL_y = new ArrayList<>();
        ArrayList<Integer> aL_x = new ArrayList<>();

        for (int i = 0; i < all_colorSquare.size(); i++) {

            ArrayList<Integer> aL_1 = all_colorSquare.get(i);

            ArrayList<ArrayList> aL_1_1 = allColors_AL.get(i);

            for (int j = 0; j < aL_1.size(); j++) {

                for (int l = 1; l < aL_1_1.size(); l++) {

                    if (aL_1.get(j).equals(aL_1_1.get(l).get(2))) {

                        aL_y.add((Integer) aL_1_1.get(l).get(0));
                        aL_x.add((Integer) aL_1_1.get(l).get(1));
                    }
                }
                yColorCoor.add((ArrayList) aL_y.clone());
                xColorCoor.add((ArrayList) aL_x.clone());

                aL_y.clear();
                aL_x.clear();
            }
        }

        numColorSections = yColorCoor.size();
        System.out.println("numColorSectionsnum " + numColorSections);
        // System.out.println(yColorCoor.size());
        // for (int i = 0; i < yColorCoor.size(); i++) {
        //      System.out.println(yColorCoor.get(i).size());
        //  }
    }

    public void minusMax_A(View view) {

        for (int i = 0; i < allColors_AL.size(); i++) {

            ArrayList<ArrayList> aL_1 = allColors_AL.get(i);
            ArrayList<Integer> aL_2 = new ArrayList<>();

            for (int j = 1; j < aL_1.size(); j++) {

                int clrSqr = (int) aL_1.get(j).get(2);

                if (!aL_2.contains(clrSqr)) {
                    aL_2.add(clrSqr);
                }
            }

            all_colorSquare.add((ArrayList) aL_2.clone());
            aL_2.clear();
        }

        System.out.println(all_colorSquare.size());

        for (int i = 0; i < all_colorSquare.size(); i++) {
            System.out.println(i + " color ");
            for (int j = 0; j < all_colorSquare.get(i).size(); j++) {

                System.out.println(all_colorSquare.get(i).get(j));

            }
        }
        /*
        int max = Integer.valueOf(String.valueOf(infoTw4.getText()));
        max--;
        infoTw4.setText(String.valueOf(max));
        setActualValues();

         */
    }

    public void plusMinIntense(View view) {

        recolorInOriginalColorSection();


        /*
        int min = Integer.valueOf(String.valueOf(infoTw6.getText()));
        min++;
        infoTw6.setText(String.valueOf(min));
        setActualValues();

         */
    }

    public void minusMinIntense(View view) {

        recolorInWhiteColorSection();

        /*
        int min = Integer.valueOf(String.valueOf(infoTw6.getText()));
        min--;
        infoTw6.setText(String.valueOf(min));
        setActualValues();

         */
    }

    // return to original color every color section
    public void recolorInOriginalColorSection() {

        if (clickCount <= numColorSections) {

            for (int i = 0; i < y1.size(); i++) {

                double[] color = {_1_clr.get(i), _2_clr.get(i), _3_clr.get(i)};

                oImage3.put(y1.get(i), x1.get(i), color);

            }

            displayImage(oImage3);

        } else {

            Toast.makeText(getBaseContext(), "Lines are finished", Toast.LENGTH_LONG).show();

        }


    }

    // color in white every color section
    public void recolorInWhiteColorSection() {

        if (clickCount <= numColorSections) {

            if (!y1.isEmpty()) {
                y1.clear();
                x1.clear();
                _1_clr.clear();
                _2_clr.clear();
                _3_clr.clear();
            }

            double[] whiteClr = {255.0, 255.0, 255.0};

            ArrayList<Integer> aL_y = yColorCoor.get(clickCount);
            ArrayList<Integer> aL_x = xColorCoor.get(clickCount);

            for (int i = 0; i < aL_y.size(); i++) {

                double[] originalColor = oImage3.get(aL_y.get(i) + _yFromROI, aL_x.get(i) + _xFromROI);

                if (originalColor[0] != 255.0 && originalColor[1] != 255.0 && originalColor[2] != 255.0) {

                    y1.add(aL_y.get(i) + _yFromROI);
                    x1.add(aL_x.get(i) + _xFromROI);

                    _1_clr.add(originalColor[0]);
                    _2_clr.add(originalColor[1]);
                    _3_clr.add(originalColor[2]);
                }

                oImage3.put(aL_y.get(i) + _yFromROI, aL_x.get(i) + _xFromROI, whiteClr);

            }
            displayImage(oImage3);
            System.out.println("clickCount " + clickCount);
            clickCount++;

        } else {
            Toast.makeText(getBaseContext(), "Lines are finished", Toast.LENGTH_LONG).show();
        }
    }

    // return image in original state
    public void undrawLines() {
        if (clickCount <= numLines) {

            for (int i = 0; i < y1.size(); i++) {

                // System.out.println(y1.size());

                double[] color = {_1_clr.get(i), _2_clr.get(i), _3_clr.get(i)};

                oImage3.put(y1.get(i), x1.get(i), color);

            }

            displayImage(oImage3);
        } else {

            Toast.makeText(getBaseContext(), "Lines are finished", Toast.LENGTH_LONG).show();

        }
    }

    // draw lines corresponding borderPoints
    public void drawLines() {
        if (clickCount <= numLines) {

            if (!y1.isEmpty()) {
                y1.clear();
                x1.clear();
                _1_clr.clear();
                _2_clr.clear();
                _3_clr.clear();
            }

            double[] whiteClr = {255.0, 255.0, 255.0};

            ArrayList<Integer> aL_y = difrntLines_coordinatesY.get(clickCount);
            ArrayList<Integer> aL_x = difrntLines_coordinatesX.get(clickCount);

            for (int i = 0; i < aL_y.size(); i++) {

                //  System.out.println(aL_y.size());

                double[] originalColor = oImage3.get(aL_y.get(i) + _yFromROI, aL_x.get(i) + _xFromROI);

                if (originalColor[0] != 255.0 && originalColor[1] != 255.0 && originalColor[2] != 255.0) {
                    y1.add(aL_y.get(i) + _yFromROI);
                    x1.add(aL_x.get(i) + _xFromROI);

                    _1_clr.add(originalColor[0]);
                    _2_clr.add(originalColor[1]);
                    _3_clr.add(originalColor[2]);
                }

                oImage3.put(aL_y.get(i) + _yFromROI, aL_x.get(i) + _xFromROI, whiteClr);

            }

            displayImage(oImage3);
            clickCount++;
            System.out.println("clickCount " + clickCount);
        } else {
            Toast.makeText(getBaseContext(), "Lines are finished", Toast.LENGTH_LONG).show();
        }

    }

    public void plusMaxIntense(View view) {

        ArrayList<Integer> cY = new ArrayList<>();
        ArrayList<Integer> cX = new ArrayList<>();

        numLines = difrntLines_indexes.size();

        for (int i = 0; i < difrntLines_indexes.size(); i++) {

            for (int j = 0; j < difrntLines_indexes.get(i).size(); j++) {

                cY.add((int) borderPoints_AL.get((int) difrntLines_indexes.get(i).get(j)).get(0));
                cX.add((int) borderPoints_AL.get((int) difrntLines_indexes.get(i).get(j)).get(1));

            }
            difrntLines_coordinatesY.add((ArrayList) cY.clone());
            difrntLines_coordinatesX.add((ArrayList) cX.clone());
            cY.clear();
            cX.clear();
        }
        System.out.println("Lines coordinates completed");
        System.out.println("difrntLines_coordinatesX size " + difrntLines_coordinatesX.size());

        /*
        System.out.println(difrntLines_coordinatesX.get(2).size());

        for(int i = 0; i < difrntLines_coordinatesX.get(2).size();i++){
            System.out.println(difrntLines_coordinatesY.get(2).get(i));
            System.out.println(difrntLines_coordinatesX.get(2).get(i));
            System.out.println("------");
        }

         */






        /*
        int max = Integer.valueOf(String.valueOf(infoTw8.getText()));
        max++;
        infoTw8.setText(String.valueOf(max));
        setActualValues();

         */
    }

    public void minusMaxIntense(View view) {

        String lineDescr;
        ArrayList<Integer> aL = new ArrayList<>();

        for (int i = 0; i < difrntLinesDscrptn.size(); i++) {

            lineDescr = difrntLinesDscrptn.get(i);

            for (int j = 0; j < linesDscrptn.size(); j++) {

                if (lineDescr.equals(linesDscrptn.get(j))) {
                    aL.add(j);
                }
            }

            difrntLines_indexes.add((ArrayList) aL.clone());
            aL.clear();
        }
        System.out.println("Arrays completed");

 /*
        System.out.println(difrntLines_indexes.size());

        for (int i = 0; i < difrntLines_indexes.size(); i++) {

            for (int j = 0; j < difrntLines_indexes.get(i).size(); j++) {
                System.out.println(difrntLines_indexes.get(i).get(j));
            }
        }
*/


        /*
        int max = Integer.valueOf(String.valueOf(infoTw8.getText()));
        max--;
        infoTw8.setText(String.valueOf(max));
        setActualValues();
        */
    }

    public void plusTr1(View view) {

        findNumberOfLines();

        System.out.println("Find lines completed");

        for (int i = 0; i < linesDscrptn.size(); i++) {
            if (!difrntLinesDscrptn.contains(linesDscrptn.get(i))) {
                difrntLinesDscrptn.add(linesDscrptn.get(i));
            }
        }
        System.out.println("Find different lines completed");
        System.out.println("difrntLinesDscrptn size " + difrntLinesDscrptn.size());

        for (int i = 0; i < 10; i++) {
            System.out.println(difrntLinesDscrptn.get(i));
        }




        /*
        int min = Integer.valueOf(String.valueOf(infoTw10.getText()));
        min++;
        infoTw10.setText(String.valueOf(min));
        setActualValues();*/
    }

    public void minusTr1(View view) {


        upDown(imgROIfromClustered);
        System.out.println("all_colorsAL size " + allColors_AL.size());

        for (int i = 0; i < allColors_AL.size(); i++) {
            System.out.println("all_colorsAL get " + i + " " + allColors_AL.get(i).size());
        }

        //    System.out.println("upDown ready");

     /*
        downUp(imgROIfromClustered);
        //    System.out.println("downUp ready");
        leftRight(imgROIfromClustered);
        //    System.out.println("leftRight ready");
        rightLeft(imgROIfromClustered);
        //   System.out.println("rightLeft ready");
        diagonal_1(imgROIfromClustered);
        //   System.out.println("diagonal_1 ready");
        diagonal_2(imgROIfromClustered);
        //   System.out.println("diagonal_2 ready");
        diagonal_3(imgROIfromClustered);
        //    System.out.println("diagonal_3 ready");
        diagonal_4(imgROIfromClustered);
        //     System.out.println("diagonal_4 ready");

        showBorderPointsArrays();

        */


        /*
        int min = Integer.valueOf(String.valueOf(infoTw10.getText()));
        min--;
        infoTw10.setText(String.valueOf(min));
        setActualValues();

         */
    }

    public void plusTr2(View view) {

        ArrayList<ArrayList> hAL = new ArrayList<ArrayList>();
        ArrayList<Integer> hAL3 = new ArrayList<Integer>();


        for (int i = 0; i < rgb_string_array.size(); i++) {
            hAL3.add(0);
            hAL3.add(0);
            hAL3.add(0);
            hAL3.add(0);
            hAL3.add(i);
            hAL.add((ArrayList) hAL3.clone());
            hAL3.clear();
            allColors_AL.add((ArrayList<ArrayList>) hAL.clone());
            hAL.clear();
        }


        // create array for helping to find color
        ArrayList<String> hAL4 = new ArrayList<String>();
        for (int i = 0; i < rgb_string_array.size(); i++) {
            hAL4.add(String.valueOf(i));
            hAL4.add(rgb_string_array.get(i));
            colorDefiningAl.add((ArrayList) hAL4.clone());
            hAL4.clear();

        }

        for (int i = 0; i < colorDefiningAl.size(); i++) {
            System.out.println(colorDefiningAl.get(i).get(0));
            System.out.println(colorDefiningAl.get(i).get(1));
        }


        //System.out.println("**************");
        // System.out.println(allColors_AL.size());

        /*
        int max = Integer.valueOf(String.valueOf(infoTw12.getText()));
        max++;
        infoTw12.setText(String.valueOf(max));
        setActualValues();
        */

    }

    public void minusTr2(View view) {

        rgb_string_array.clear();
        Toast.makeText(getBaseContext(), "rgb_string_array is cleared", Toast.LENGTH_LONG).show();
        // after code related to minusTr2
        /*
        int max = Integer.valueOf(String.valueOf(infoTw12.getText()));
        max--;
        infoTw12.setText(String.valueOf(max));
        setActualValues();

         */
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

        ArrayList<int[]> al = contourCoordinatesX_Y_All.get(colorBack);

        for (int i = 0; i < al.size(); i++) {
            int[] ft5 = al.get(i);
            int y = ft5[0];
            int x = ft5[1];
            oColor = originalImageColor.get(i);
            oImage3.put(y, x, oColor);
        }
        colorBack++;
        originalImageColor.clear();
        displayImage(oImage3);
        // mUpDownChangeROI(zoomMat, min_A, max_A, min_Intense, max_Intense);
        //displayImage(mUpDownChangeROI(zoomMat, min_A, max_A, min_Intense, max_Intense));
        ///   displayImage(mUpDownChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        ///   oImage2 = oImage;
    }

    public void downUpChange(View view) {

        double[] ft3 = {0.0, 0.0, 0.0};

        ArrayList<int[]> al = contourCoordinatesX_Y_All.get(clicks);

        for (int i = 0; i < al.size(); i++) {
            int[] ft5 = al.get(i);
            int y = ft5[0];
            int x = ft5[1];
            oColor = oImage3.get(y, x);
            originalImageColor.add(oColor);
            oImage3.put(y, x, ft3);
        }

        displayImage(oImage3);

        clicks++;


        // after code related to downUpChange
        /*
        displayImage(mDownUpChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        oImage2 = oImage;
         */
    }

    public void rightLeftChange(View view) {

        if (m2 % 2 != 0) {
            displayImage(oImage3ClusterColored);
            m2++;
        } else {
            displayImage(oImage3);
            m2++;
        }


        // after code related to leftrightChange
        /*
        displayImage(mRightLeftChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        oImage2 = oImage;
        */

    }

    public void clearDataForNext() {

        LabClass.squareNum = 1;
        LabClass.isFirst = 0;
        LabClass.squareNumHrzntl = 0;
        LabClass.squareNumVrtcl = 0;
        rgb_string_array.clear();
        all_colorSquare.clear();
        allColors_AL.clear();
        colorDefiningAl.clear();
        borderPoints_AL.clear();
        linesDscrptn.clear();
        difrntLinesDscrptn.clear();
        difrntLines_indexes.clear();
        difrntLines_coordinatesY.clear();
        difrntLines_coordinatesX.clear();
        yColorCoor.clear();
        xColorCoor.clear();
        clickCount = 0;
        y1.clear();
        x1.clear();
        _1_clr.clear();
        _2_clr.clear();
        _3_clr.clear();
        System.out.println("All previous data is cleared");

    }

    public void leftRightChange(View view) {

        clearDataForNext();

        imgROIfromClustered = getColorLines(oImage3ClusterColored);

        /// getContourLines(oImage3ClusterColored);

     /*   DrawRect.getCoord();

        for (int x = xRed; x < (xYell + 1); x++) {

            for (int y = yRed; y < (yYell + 1); y++) {

                x_Cor_ROI.add(x);

                y_Cor_ROI.add(y);

            }
        }

        System.out.println(y_Cor_ROI.size());

        double[] clr = {255.0,255.0,255.0};
        for(int i = 0; i < x_Cor_ROI.size(); i++){
            oImage3.put(y_Cor_ROI.get(i), x_Cor_ROI.get(i),clr);
        }

        System.out.println("is put");*/

        // displayImage(oImage3);

        // after code related to leftrightChange
        /*
        displayImage(mLeftRightChangeROI(oImage, min_A, max_A, min_Intense, max_Intense));
        oImage2 = oImage;
        */

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
        //   displayImage(ContourImage._2getMainContourFromROI(doubleZoomedImage, doubleZoomedImage));
        /// ContourImage.getLabValues(oImage3);
        //  ContourImage._2getMainContourFromROI(oImage3, oImage3);
        //BELOW CODE FOR RECOLOR CONTOURS

        ////   for (int i = 0; i < diffXValues.size(); i++) {
        ////       double[] dVal = {rVal[0], gVal[0], bVal[0]};
        ////       oImage3.put(diffYValues.get(i), diffXValues.get(i), dVal);
        ////    }


        for (int m = 0; m < all_diffYValues.size(); m++) {
            ArrayList<Integer> XValues = all_diffXValues.get(m);
            ArrayList<Integer> YValues = all_diffYValues.get(m);
            double[] dVal = getListColor();
            for (int i = 0; i < XValues.size(); i++) {
                //   double[] dVal = {rVal[m], gVal[m], bVal[m]};
                // double[] dVal = getListColor();
                oImage3ClusterColored.put(YValues.get(i), XValues.get(i), dVal);
            }
        }

        Toast.makeText(getBaseContext(), "oImage3ClusterColored is ready", Toast.LENGTH_LONG).show();
        displayImage(oImage3);
/*
        for (int i = 0; i < all_diffXValues.get(0).size(); i++) {
            if ((Integer) all_diffYValues.get(0).get(i) == 500) {
                System.out.println(all_diffXValues.get(0).get(i) + ":" + all_diffYValues.get(0).get(i));
            }
        }
*/


        //  displayImage(oImage3);

        //for (int i = 0; i < 100; i++) {
        //    double[] vFull = oImage3.get(500, i);
        //     System.out.println(i + " - " + vFull[0] + ":" + vFull[1] + ":" + vFull[2]);
        // DCol1.add(vFull[0]);
        //   DCol2.add(vFull[1]);
        // DCol3.add(vFull[2]);
        // double[] dVal = {rVal[m], gVal[m], bVal[m]};
        //    }


    }

    public static double[] getListColor() {

        double[] list = new double[]{(double) getRandDouble(0, 254), (double) getRandDouble(0, 254), (double) getRandDouble(0, 254)};
        return list;
    }

    public static int getRandDouble(int min, int max) {

        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public ArrayList<ArrayList> sortDesc(ArrayList<ArrayList> _aL) {

        //copy to help
        ArrayList<ArrayList> allArraysCopy = new ArrayList<ArrayList>();
        for (int i = 0; i < _aL.size(); i++) {
            allArraysCopy.add((ArrayList) _aL.get(i).clone());
        }
        // System.out.println(allArraysCopy.get(0).size());
        // System.out.println(allArraysCopy.get(1).size());
        // System.out.println(allArraysCopy.get(2).size());
        // System.out.println(allArraysCopy.get(3).size());
        // System.out.println("++++++++++++++++");

        ArrayList<Integer> arrayListSize = new ArrayList<>();
        for (int i = 0; i < _aL.size(); i++) {
            arrayListSize.add(_aL.get(i).size());
        }
        Collections.sort(arrayListSize);
        Collections.reverse(arrayListSize);

        //   for (int i = 0; i < arrayListSize.size(); i++) {
        //       System.out.println(i + " - " + arrayListSize.get(i));
        //   }

        ArrayList<Integer> aList2 = new ArrayList<Integer>();

        for (int y = 0; y < allArraysCopy.size(); y++) {
            for (int j = 0; j < allArraysCopy.size(); j++) {
                if ((arrayListSize.get(y) != 0) && (arrayListSize.get(y) == allArraysCopy.get(j).size())) {
                    aList2.add(j);
                    allArraysCopy.get(j).clear();
                    break;
                }
            }
        }
        //   System.out.println(_aL.get(0).size());
        //   for (int i = 0; i < _aL.get(0).size(); i++) {
        //       System.out.println(" - " + _aL.get(0).get(i));
        //   }

        // sorted array with different indexes
        ArrayList<ArrayList> aL_2 = new ArrayList<ArrayList>();

        for (int i = 0; i < aList2.size(); i++) {
            aL_2.add(_aL.get(aList2.get(i)));
        }
/*
        System.out.println(aL_2.get(0).size());
        for (int i = 0; i < aL_2.get(0).size(); i++) {
            System.out.println(" - " + aL_2.get(0).get(i));
        }
        System.out.println("++++++++++++++++");
        System.out.println(aL_2.get(1).size());
        for (int i = 0; i < aL_2.get(1).size(); i++) {
            System.out.println(" - " + aL_2.get(1).get(i));
        }
        System.out.println("++++++++++++++++");
        System.out.println(aL_2.get(2).size());
        for (int i = 0; i < aL_2.get(2).size(); i++) {
            System.out.println(" - " + aL_2.get(2).get(i));
        }
        System.out.println("++++++++++++++++");
        System.out.println(aL_2.get(3).size());
        for (int i = 0; i < aL_2.get(3).size(); i++) {
            System.out.println(" - " + aL_2.get(3).get(i));
        }
*/
        return aL_2;
    }


    public void extraContour(View view) {

        NextLabImg.getClustersFromLabImg(getMatFromROI(oImage3));



        //displayImage(ContourImage.getExtraContourFromROI(doubleZoomedImage, doubleZoomedImage));
    }
}

