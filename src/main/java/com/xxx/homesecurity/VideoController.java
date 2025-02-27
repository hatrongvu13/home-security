package com.xxx.homesecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class VideoController {
    @Autowired
    private VideoProcessor videoProcessor;

    @GetMapping(value = "/video", produces = "application/x-mpegURL")
    public StreamingResponseBody streamVideo() throws IOException {
        // Chạy FFmpeg để chuyển RTSP sang HLS
        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i", videoProcessor.getRtspUrl(),
                "-c:v", "libx264", "-c:a", "aac",
                "-f", "hls", "-hls_time", "2", "-hls_list_size", "3",
                "-hls_segment_filename", "target/hls/segment%d.ts",
                "target/hls/playlist.m3u8"
        );
        Process process = pb.start();

        // Trả về playlist HLS
        return outputStream -> {
            File playlist = new File("target/hls/playlist.m3u8");
            while (!playlist.exists()) {
                try {
                    Thread.sleep(1000); // Đợi FFmpeg tạo file
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try (FileInputStream fis = new FileInputStream(playlist)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        };
    }
}
