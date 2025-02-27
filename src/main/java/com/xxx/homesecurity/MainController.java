//package com.xxx.homesecurity;
//import javafx.fxml.FXML;
//import javafx.scene.canvas.Canvas;
//import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
//import uk.co.caprica.vlcj.player.base.MediaPlayer;
//import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
//import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//
//public class MainController {
//    @FXML private Canvas videoCanvas;
//    @FXML private javafx.scene.control.Button leftButton, rightButton, upButton, downButton, zoomInButton, zoomOutButton;
//
//    private EmbeddedMediaPlayer mediaPlayer;
//    private final String rtspUrl = "rtsp://admin:123@[ip_address]:554/11"; // Thay bằng URL của bạn
//    private final String cameraBaseUrl = "http://[ip_address]/cgi-bin/hi3510"; // Thay bằng IP của bạn
//
//    @FXML
//    public void initialize() {
//        // Khởi tạo VLCJ để phát RTSP
//        MediaPlayerFactory factory = new MediaPlayerFactory();
//        mediaPlayer = factory.mediaPlayers().newEmbeddedMediaPlayer();
//        mediaPlayer.videoSurface().set(new ImageViewVideoSurface(videoCanvas));
//
//        // Phát luồng RTSP
//        mediaPlayer.media().play(rtspUrl);
//    }
//
//    // Điều khiển PTZ qua HTTP
//    private void sendPtzCommand(String command) {
//        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
//            HttpGet request = new HttpGet(cameraBaseUrl + "/ptzctrl.cgi?-step=0&-act=" + command);
//            httpClient.execute(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML private void panLeft() { sendPtzCommand("left"); }
//    @FXML private void panRight() { sendPtzCommand("right"); }
//    @FXML private void tiltUp() { sendPtzCommand("up"); }
//    @FXML private void tiltDown() { sendPtzCommand("down"); }
//    @FXML private void zoomIn() { sendPtzCommand("zoomin"); }
//    @FXML private void zoomOut() { sendPtzCommand("zoomout"); }
//}