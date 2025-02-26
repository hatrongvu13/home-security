package com.xxx.homesecurity.services.record;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class VideoRecord implements Record {
    @Override
    public void recordVideo(String outputFile, OpenCVFrameGrabber grabber) {

        try {
            grabber.start();
            int width = grabber.getImageWidth();
            int height = grabber.getImageHeight();

            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, width, height);

            recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
            recorder.setFormat("mp4");
            recorder.setFrameRate(30);
            recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
            recorder.start();

            Frame frame = grabber.grab();
            while ((frame = grabber.grab()) != null) {
                recorder.record(frame);
            }

            recorder.stop();
            grabber.stop();

            System.out.println(new File(outputFile).getAbsolutePath());

        } catch (FrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } catch (FFmpegFrameRecorder.Exception e) {
            throw new RuntimeException(e);
        }

    }
}
