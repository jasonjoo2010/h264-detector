package com.yoloho.training;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;
import com.yoloho.training.parse.Parser;

public class Detector {
    public static void main(String[] args) throws IOException {
        // bad: http://v.dayima.com/sv/12486bbb-171f2e0611e/12486bbb-171f2e0611e.mp4
        if (args.length < 1 || args[0] == null || args[0].length() < 1) {
            System.out.println("Usage: java h264-detector <filepath | url>");
            System.out.println();
            return;
        }
        int desiredBytes = 1024 * 100;
        String str = args[0];
        InputStream is;
        if (str.startsWith("http://") || str.startsWith("https://")) {
            // url
            URL url = new URL(str);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.addRequestProperty("Range", "bytes=0-" + (desiredBytes - 1));
            conn.connect();
            is = conn.getInputStream();
        } else {
            // file
            is = new FileInputStream(new File(str));
        }
        Parser parser = new Parser(is, desiredBytes);
        is.close();
        System.out.println("Video boxes structure:");
        System.out.println(JSON.toJSONString(parser.getBoxes(), true));
        if (parser.isH264()) {
            System.out.println("It's a valid x264 video");
        } else {
            System.out.println("It's NOT a valid x264 video");
        }
    }
}
