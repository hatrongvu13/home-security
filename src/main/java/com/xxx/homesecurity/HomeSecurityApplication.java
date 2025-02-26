package com.xxx.homesecurity;

import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;

import javax.swing.*;

import java.awt.image.BufferedImage;

public class HomeSecurityApplication {


    public static void main(String[] args) throws FrameGrabber.Exception {
        // RTSP URL (replace with your own)
        String rtspUrl = "rtsp://**********:*******@*********:554/onvif1";

        // Initialize FFmpegFrameGrabber for RTSP
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspUrl);
        grabber.start(); // Connect to the stream

        // Check if connection succeeded
        if (!grabber.hasVideo()) {
            System.out.println("Error: Could not connect to RTSP stream");
            grabber.close();
            return;
        }
        System.out.println("Connected to RTSP stream successfully");

        // Set up display window
        CanvasFrame canvas = new CanvasFrame("RTSP Person Detection", CanvasFrame.getDefaultGamma());
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Frame processing loop
        Java2DFrameConverter converter = new Java2DFrameConverter();
        OpenCVFrameConverter.ToMat matConverter = new OpenCVFrameConverter.ToMat();

        while (canvas.isVisible()) {
            Frame frame = grabber.grab(); // Grab a frame from the stream
            if (frame == null) {
                System.out.println("Error: No frame grabbed");
                break;
            }

            // Convert frame to OpenCV Mat
            Mat mat = matConverter.convert(frame);
//
//            // Detect persons
//            RectVector found = new RectVector();
//            hog.detectMultiScale(mat, found);

//            // Draw rectangles around detected persons
//            for (long i = 0; i < found.size(); i++) {
////                Rect r = found.get(i);
//                rectangle(mat, r.tl(), r.br(), Scalar.RED, 2, 8, 0); // Red bounding box
//            }

            // Convert back to Java frame for display
            BufferedImage image = converter.convert(matConverter.convert(mat));
            canvas.showImage(image);
        }

        // Cleanup
        grabber.close();
        canvas.dispose();
    }

}
