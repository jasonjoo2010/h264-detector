package com.yoloho.training.utils;

import java.nio.ByteBuffer;

public class BufferUtil {
    public static String loadString(ByteBuffer buffer, int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i ++) {
            builder.append((char)buffer.get());
        }
        return builder.toString();
    }
}
