package com.yoloho.training.parse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.yoloho.training.types.DinfBox;
import com.yoloho.training.types.DrefBox;
import com.yoloho.training.types.EdtsBox;
import com.yoloho.training.types.ElstBox;
import com.yoloho.training.types.HdlrBox;
import com.yoloho.training.types.MdhdBox;
import com.yoloho.training.types.MdiaBox;
import com.yoloho.training.types.MinfBox;
import com.yoloho.training.types.MoovBoxBase;
import com.yoloho.training.types.MvhdBox;
import com.yoloho.training.types.SmhdBox;
import com.yoloho.training.types.StblBox;
import com.yoloho.training.types.StsdBox;
import com.yoloho.training.types.TkhdBox;
import com.yoloho.training.types.TrakBox;
import com.yoloho.training.types.UdtaBox;
import com.yoloho.training.types.VmhdBox;
import com.yoloho.training.utils.BufferUtil;

public class MoovParser {
    private ByteBuffer buffer;
    private MoovBoxBase box;
    
    public MoovParser(String filepath, MoovBoxBase box) throws IOException {
        this.box = box;
        try (InputStream is = BufferUtil.getUrlRangeStream(filepath, box.getOffset() + 8, (int)box.getSize() - 8)) {
            byte[] data = new byte[(int)box.getSize()];
            int pos = 0;
            while (pos < data.length) {
                int read = is.read(data, pos, data.length - pos);
                if (read < 0) {
                    break;
                }
                pos += read;
            }
            this.buffer = ByteBuffer.wrap(data, 0, data.length);
            this.buffer.order(ByteOrder.BIG_ENDIAN);
        }
    }
    
    public MoovParser(byte[] data, MoovBoxBase box) {
        this.box = box;
        this.buffer = ByteBuffer.wrap(data, 0, data.length);
        this.buffer.order(ByteOrder.BIG_ENDIAN);
    }
    
    public void parse() {
        buffer.rewind();
        MoovBoxBase box;
        byte[] data;
        while (buffer.hasRemaining()) {
            buffer.mark();
            box = new MoovBoxBase(buffer.position(), buffer);
            buffer.reset();
            int pos = buffer.position();
            switch (box.getType()) {
                case "mvhd":
                    box = new MvhdBox(pos, buffer);
                    break;
                    
                case "udta":
                    box = new UdtaBox(pos, buffer);
                    break;
                    
                case "trak":
                    box = new TrakBox(pos, buffer);
                    data = new byte[(int)box.getSize() - 8];
                    buffer.get(data, 0, data.length);
                    new MoovParser(data, box).parse();
                    break;
                    
                case "tkhd":
                    box = new TkhdBox(pos, buffer);
                    break;
                    
                case "edts":
                    box = new EdtsBox(pos, buffer);
                    data = new byte[(int)box.getSize() - 8];
                    buffer.get(data, 0, data.length);
                    new MoovParser(data, box).parse();
                    break;
                    
                case "elst":
                    box = new ElstBox(pos, buffer);
                    break;
                    
                case "mdia":
                    box = new MdiaBox(pos, buffer);
                    data = new byte[(int)box.getSize() - 8];
                    buffer.get(data, 0, data.length);
                    new MoovParser(data, box).parse();
                    break;
                    
                case "mdhd":
                    box = new MdhdBox(pos, buffer);
                    break;
                    
                case "hdlr":
                    box = new HdlrBox(pos, buffer);
                    break;
                    
                case "minf":
                    box = new MinfBox(pos, buffer);
                    data = new byte[(int)box.getSize() - 8];
                    buffer.get(data, 0, data.length);
                    new MoovParser(data, box).parse();
                    break;
                    
                case "vmhd":
                    box = new VmhdBox(pos, buffer);
                    break;
                    
                case "smhd":
                    box = new SmhdBox(pos, buffer);
                    break;
                    
                case "dinf":
                    box = new DinfBox(pos, buffer);
                    data = new byte[(int)box.getSize() - 8];
                    buffer.get(data, 0, data.length);
                    new MoovParser(data, box).parse();
                    break;
                    
                case "dref":
                    box = new DrefBox(pos, buffer);
                    break;
                    
                case "stbl":
                    box = new StblBox(pos, buffer);
                    data = new byte[(int)box.getSize() - 8];
                    buffer.get(data, 0, data.length);
                    new MoovParser(data, box).parse();
                    break;
                    
                case "stsd":
                    box = new StsdBox(pos, buffer);
                    break;
                    
                case "stts":
                case "ctts":
                case "sdtp":
                case "stsz":
                case "stss":
                case "stsc":
                case "stco":
                    // ignore
                    break;
                    
                default:
                    if (box.getType().substring(1).equals("e9p")) {
                        // ignore
                    } else if (box.getType().charAt(0) == 0) {
                        // ignore
                    } else {
                        System.out.println(box.getType());
                    }
                    break;
            }
            this.box.addChild(box);
            pos += box.getSize();
            if (box.getSize() == 0 || pos < 1 || pos >= buffer.limit()) {
                break;
            }
            buffer.position(pos);
        }
    }
}
