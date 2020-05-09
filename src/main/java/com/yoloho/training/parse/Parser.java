package com.yoloho.training.parse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import com.yoloho.training.types.BaseBox;
import com.yoloho.training.types.FreeBox;
import com.yoloho.training.types.MdatBox;
import com.yoloho.training.types.MoovBox;
import com.yoloho.training.types.TypeBox;

public class Parser {
    private ByteBuffer buffer;
    private List<BaseBox> boxes = new ArrayList<>();
    
    public Parser(InputStream is) throws IOException {
        this(is, 102400);
    }
    
    public Parser(InputStream is, int capacity) throws IOException {
        byte[] data = new byte[capacity];
        int pos = 0;
        while (pos < data.length) {
            int read = is.read(data, pos, data.length - pos);
            if (read < 0) {
                break;
            }
            pos += read;
        }
        this.buffer = ByteBuffer.wrap(data, 0, pos);
        this.buffer.order(ByteOrder.BIG_ENDIAN);
        parse();
    }
    
    private void parse() {
        buffer.rewind();
        BaseBox box;
        while (buffer.hasRemaining()) {
            buffer.mark();
            box = new BaseBox(buffer);
            buffer.reset();
            int pos = buffer.position();
            switch (box.getType()) {
                case "ftyp":
                    boxes.add(new TypeBox(buffer));
                    break;
                case "moov":
                    boxes.add(new MoovBox(buffer));
                    break;
                case "mdat":
                    boxes.add(new MdatBox(buffer));
                    break;
                case "free":
                    boxes.add(new FreeBox(buffer));
                    break;

                default:
                    System.out.println(box.getType());
                    break;
            }
            pos += box.getSize();
            if (pos >= buffer.limit()) {
                break;
            }
            buffer.position(pos);
        }
    }
    
    public boolean isH264() {
        for (BaseBox box : boxes) {
            if (box instanceof TypeBox) {
                TypeBox b = (TypeBox)box;
                return b.getAdditionalBrands().contains("avc1");
            }
        }
        return false;
    }
    
    public List<BaseBox> getBoxes() {
        return boxes;
    }
}
