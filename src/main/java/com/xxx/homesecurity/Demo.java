package com.xxx.homesecurity;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;

public class Demo {

    public static void captureAndSave(String rstpUrl, String outputFile) throws FrameGrabber.Exception, FFmpegFrameRecorder.Exception {

        FFmpegLogCallback.set();

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(rstpUrl);
        grabber.start();  // Start the grabber to get both audio and video

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile, 640, 640);
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);  // Set video codec
        recorder.setAudioCodec(avcodec.AV_CODEC_ID_AAC);  // Set audio codec (AAC)
        recorder.setAudioChannels(2);  // Set stereo audio channels (2)
        recorder.setAudioBitrate(44100);  // Set audio sample rate (e.g., 44.1 kHz)
        recorder.setFormat("mp4");  // Set output format
        recorder.setFrameRate(30);  // Set video frame rate

        // Set the pixel format (avoid deprecated ones)
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);  // Standard YUV format

        // Set the color range explicitly (use full range for better quality)
        recorder.setOption("color_range", "limited"); // Use full range (0-255)
        recorder.start();  // Start recording with both audio and video

        Frame frame;
        while ((frame = grabber.grabFrame()) != null) {
            if (frame.samples != null) {
                recorder.recordSamples(frame.sampleRate,2, frame.samples);  // Record audio samples
            }
            recorder.record(frame);  // Record video frame
        }
        recorder.stop();
        grabber.stop();
    }

    public static void main(String[] args) throws FrameGrabber.Exception, FFmpegFrameRecorder.Exception {
        captureAndSave("", "d:/code/video1.mp4");
    }
}
