package com.example.andrey.lop.ImageActions;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import static org.opencv.core.Core.FILLED;
import static org.opencv.core.Core.LINE_AA;
//import static org.opencv.imgproc.Imgproc.LINE_AA;
//import static org.opencv.imgproc.Imgproc.FILLED;
//import static org.opencv.core.Core.FILLED;

public class BasicDrawing {

    private static final int W = 400;
    private static final double angle_1 = 90.0;
    private static final double angle_2 = 0.0;
    private static final double angle_3 = 45.0;
    private static final double angle_4 = -45.0;

    static Mat atom_image = Mat.zeros(W, W, CvType.CV_8UC3);

    //
    protected static Mat MyEllipse() {
        // create image to display
      //  Mat atom_image = Mat.zeros(W, W, CvType.CV_8UC3);
        // set line thickness in pixels
        int thickness = 2;
        // Type of the ellipse boundary
        int lineType = 8;
        // Number of fractional bits in the coordinates of the center and values of axes.
        int shift = 0;


        Imgproc.ellipse(atom_image, new Point(W / 2, W / 2), new Size(W / 4, W / 16),
                angle_1, 0.0, 360.0, new Scalar(255, 0, 0), thickness, lineType, shift);
        Imgproc.ellipse(atom_image, new Point(W / 2, W / 2), new Size(W / 4, W / 16),
                angle_2, 0.0, 360.0, new Scalar(255, 0, 0), thickness, lineType, shift);
        Imgproc.ellipse(atom_image, new Point(W / 2, W / 2), new Size(W / 4, W / 16),
                angle_3, 0.0, 360.0, new Scalar(255, 0, 0), thickness, lineType, shift);
        Imgproc.ellipse(atom_image, new Point(W / 2, W / 2), new Size(W / 4, W / 16),
                angle_4, 0.0, 360.0, new Scalar(255, 0, 0), thickness, 4, shift);

        return atom_image;
    }

    protected static Mat CenterAtom(){

        //Since thickness = -1, the circle will be drawn filled
        int thickness = -1;
        int lineType = 8;
        int shift = 0;
        Point center = new Point(W/2,W/2);
        Imgproc.circle( atom_image, center, W/32, new Scalar( 0, 0, 255 ), thickness, lineType, shift );
        return atom_image;
    }

    public static Mat DrawAtom(){
        MyEllipse();
        CenterAtom();
        return atom_image;
    }


}
