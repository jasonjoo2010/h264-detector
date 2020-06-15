package com.yoloho.training;

import java.io.IOException;
import java.io.InputStream;

import com.alibaba.fastjson.JSON;
import com.yoloho.training.parse.MoovParser;
import com.yoloho.training.parse.Parser;
import com.yoloho.training.types.BaseBox;
import com.yoloho.training.types.MoovBox;
import com.yoloho.training.utils.BufferUtil;

public class Detector {
    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args[0] == null || args[0].length() < 1) {
            System.out.println("Usage: java h264-detector.jar <filepath | url>");
            System.out.println();
            return;
        }
        final int desiredBytes = 1024 * 10;
        int offset = 0;
        MoovBox moovBox = null;
        String str = args[0];
        Parser parser = null;
        while (true) {
            try (InputStream is = BufferUtil.getUrlRangeStream(str, offset, desiredBytes)) {
                parser = new Parser(is, desiredBytes);
                if (parser.getBoxes().size() < 1) {
                    break;
                }
                System.out.println("Video boxes structure:");
                System.out.println(JSON.toJSONString(parser.getBoxes(), true));
                for (BaseBox box : parser.getBoxes()) {
                    if (box instanceof MoovBox) {
                        moovBox = (MoovBox) box;
                        moovBox.setOffset(offset);
                    }
                    offset += box.getSize();
                }
            } catch (IOException e) {
                break;
            }
        }
        // try to parse moov
        if (moovBox != null) {
            new MoovParser(str, moovBox).parse();
            System.out.println("Moov structure:");
            System.out.println(JSON.toJSONString(moovBox, true));
            if (moovBox.isH264()) {
                System.out.println("It's a valid h264 video (moov)");
                return;
            }
        }
        if (parser != null && parser.isH264()) {
            System.out.println("It's a valid h264 video");
        } else {
            System.out.println("It's NOT a valid h264 video");
        }
    }
}
