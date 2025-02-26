package com.xxx.homesecurity.services.record;

import org.bytedeco.javacv.OpenCVFrameGrabber;

public interface Record {

    void recordVideo(String outputFile, OpenCVFrameGrabber grabber);
}
