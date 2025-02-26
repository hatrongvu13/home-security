package com.xxx.homesecurity;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

public class SaveRTSPStream {
    public static void main(String[] args) {
        String rtspUrl = "rtsp://admin:password@192.168.1.100:554/onvif1"; // Thay bằng thông tin thật
        String outputFile = "output.mp4";

        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rtspUrl);
             FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 1280, 720)) {

            grabber.setOption("rtsp_transport", "tcp");
            grabber.setFormat("rtsp");
            grabber.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            grabber.start();

            recorder.setFormat("mp4");
            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFrameRate(30);
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            recorder.start();

            Frame frame;
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }

            grabber.stop();
            recorder.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
