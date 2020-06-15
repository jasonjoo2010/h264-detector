package com.yoloho.training.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;

public class BufferUtil {
    public static String loadString(ByteBuffer buffer, int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            builder.append((char) buffer.get());
        }
        return builder.toString();
    }

    public static InputStream getUrlRangeStream(String filepath, int offset, int size) throws IOException {
        boolean isUrl = false;
        if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
            isUrl = true;
        }
        if (isUrl) {
            // url
            URL url = new URL(filepath);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Range", "bytes=" + offset + "-" + (offset + size - 1));
            conn.connect();
            return conn.getInputStream();
        } else {
            // file
            InputStream is = new FileInputStream(new File(filepath));
            if (offset > 0) {
                is.skip(offset);
            }
            return is;
        }
    }
}
