package com.xxx.homesecurity;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;

import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_highgui.waitKey;

public class RTSPStream {
    public static void main(String[] args) {
        String rtspUrl = "rtsp://*******:********@*******:554/onvif1"; // Thay bằng thông tin thật

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspUrl)) {
            grabber.setOption("rtsp_transport", "tcp"); // Dùng TCP để ổn định hơn
            grabber.setFormat("rtsp");
            grabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            grabber.start();

            OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
            while (true) {
                Frame frame = grabber.grab();
                if (frame == null) break;

                Mat mat = converter.convert(frame);
                if (mat != null) {
                    imshow("Yoosee Camera", mat); // ✅ Hiển thị hình ảnh
                    if (waitKey(30) == 27) break; // Nhấn ESC để thoát
                }
            }
            grabber.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
