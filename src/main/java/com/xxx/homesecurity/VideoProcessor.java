package com.xxx.homesecurity;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.Rect;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.springframework.stereotype.Component;

@Component
public class VideoProcessor {
    private final String rtspUrl = "rtsp://admin:123@192.168.1.100:554/onvif1"; // Thay bằng URL của bạn
    private FFmpegFrameGrabber grabber;
    private final CascadeClassifier personDetector;

    public VideoProcessor() {
        personDetector = new CascadeClassifier("src/main/resources/config/haarcascade_fullbody.xml");
    }

    public void startProcessing() throws FrameGrabber.Exception {
        grabber = new FFmpegFrameGrabber(rtspUrl);
        grabber.setOption("rtsp_transport", "tcp"); // Dùng TCP để ổn định hơn
        grabber.start();

        // Vòng lặp đọc từng frame
        while (true) {
            Frame frame = grabber.grabImage();
            if (frame == null) break;

            // Chuyển Frame sang Mat để OpenCV xử lý
            OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
            Mat matFrame = converter.convert(frame);

            // Xử lý nhận diện người/vật
            processFrame(matFrame);

            // Thoát vòng lặp nếu cần (ví dụ: thêm điều kiện dừng)
        }
        grabber.stop();
    }

    private void processFrame(Mat frame) {
        // Chuyển sang thang xám để tăng hiệu suất
        Mat grayFrame = new Mat();
        org.bytedeco.opencv.global.opencv_imgproc.cvtColor(frame, grayFrame, org.bytedeco.opencv.global.opencv_imgproc.COLOR_BGR2GRAY);

        // Phát hiện người
        RectVector detections = new RectVector();
        personDetector.detectMultiScale(grayFrame, detections);

        // Vẽ hình chữ nhật quanh đối tượng được phát hiện
        for (int i = 0; i < detections.size(); i++) {
            Rect rect = detections.get(i);
            org.bytedeco.opencv.global.opencv_imgproc.rectangle(
                    frame, rect, new Scalar(0, 255, 0, 0));
        }

        // Lưu hoặc truyền frame đã xử lý
        System.out.println("Detected " + detections.size() + " persons");
    }

    public String getRtspUrl() {
        return rtspUrl;
    }
}
