package com.umpaytest.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.io.ByteBufferSeekableByteChannel;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * 视频处理
 *
 * @author Hu.ChangLiang
 * @date 2024/5/28 18:20
 */
public class VideoUtil {
//    static String videoFilePath = "https://mxpos-sg.oss-ap-southeast-1.aliyuncs.com/oms/order/20240530/b58d33b119ce47a59de90ecd57268ad3.mp4";
    static String videoFilePath = "http://stream4.iqilu.com/ksd/video/2020/02/17/97e3c56e283a10546f22204963086f65.mp4";

    public static void main(String[] args) {
        aa();
    }

    public void test() {
        // 创建URL对象
        String localFilePath = "video.mp4";
        try {
            URL url = new URL(videoFilePath);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // 设置超时时间
            connection.connect();
            // 获取文件大小
            int fileLength = connection.getContentLength();
            // 输出文件流
            FileOutputStream outputStream = new FileOutputStream(localFilePath);

            // 输入流
            InputStream inputStream = new BufferedInputStream(connection.getInputStream());

            // 缓存
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            long totalBytesRead = 0;

            // 开始下载
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                totalBytesRead += bytesRead;
                outputStream.write(dataBuffer, 0, bytesRead);
                System.out.println("下载进度：" + (totalBytesRead * 100 / fileLength) + "%");
            }

            // 关闭流
            outputStream.flush();
            outputStream.close();
            inputStream.close();

            System.out.println("视频下载完成!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void bb() {
        // 视频文件路径
        String videoLocalFilePath = "C:\\Users\\li185\\Videos\\Captures\\1.mp4";
        // 保存帧的目录
        String outputDir = "C:\\Users\\li185\\Videos\\Captures\\";


        try {
            // 创建 FrameGrab 对象
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(new File(videoLocalFilePath)));

            // 获取视频的总帧数
            int totalFrames = grab.getVideoTrack().getMeta().getTotalFrames();

            // 生成随机帧数
            Random random = new Random();
            int randomFrameNumber = random.nextInt(totalFrames);

            // 跳到随机帧
            grab.seekToFramePrecise(randomFrameNumber);

            // 获取随机帧
            Picture picture = grab.getNativeFrame();
            if (picture != null) {
                // 将帧转换为 BufferedImage
                BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

                // 保存 BufferedImage 为图片
                File outputFile = new File(outputDir, String.format("random_frame_%05d.png", randomFrameNumber));
                ImageIO.write(bufferedImage, "png", outputFile);

                System.out.println("Saved frame " + randomFrameNumber + " to " + outputFile.getAbsolutePath());
            }
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }
    }

    public static void aa() {
        try {
            // 从URL读取视频文件到内存中
            File file = readBytesFromUrl(videoFilePath);


            // 使用内存中的字节数组进行处理
            FrameGrab grab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(file));

            // 获取视频的总帧数
            int totalFrames = grab.getVideoTrack().getMeta().getTotalFrames();

            // 生成随机帧数
            Random random = new Random();
            int randomFrameNumber = random.nextInt(totalFrames);

            // 跳到随机帧
            grab.seekToFramePrecise(randomFrameNumber);

            // 获取随机帧
            Picture picture = grab.getNativeFrame();
            if (picture != null) {
                // 将帧转换为 BufferedImage
                BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

                // 保存 BufferedImage 为图片
                File outputFile = new File("random_frame.png");
                ImageIO.write(bufferedImage, "png", outputFile);

                System.out.println("Saved frame " + randomFrameNumber + " to " + outputFile.getAbsolutePath());
            }
        } catch (IOException | JCodecException e) {
            e.printStackTrace();
        }
    }

    private static File readBytesFromUrl(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        File video = File.createTempFile("video", ".mp4");
        try (InputStream inputStream =connection.getInputStream();
             FileOutputStream fileOutputStream = new FileOutputStream(video)) {
            byte[] buffer = new byte[1024];
            int n;
            while ((n = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, n);
            }
            return video;
        }
    }
}
