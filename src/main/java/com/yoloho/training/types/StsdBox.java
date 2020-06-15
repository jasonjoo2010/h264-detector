package com.yoloho.training.types;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.yoloho.training.utils.BufferUtil;

/**
 * Sample (frame encoding) description box
 * 
 * @author jason
 *
 */
public class StsdBox extends MoovBoxBase {
    public static class Desc {
        private List<String> formats = new ArrayList<>();
        
        public List<String> getFormats() {
            return formats;
        }
    }
    
    private byte version;
    private int flags;
    private int descCount;
    private List<Desc> descs = new ArrayList<>();

    public StsdBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
        // 1 byte version
        this.version = buffer.get();
        // 3 bytes flags
        this.flags = buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        // 4 bytes number of descriptions
        this.descCount = buffer.getInt();
        
        for (int i = 0; i < this.descCount; i ++) {
            // 4 bytes description length
            int len = buffer.getInt();
            Desc desc = new Desc();
            for (int j = 0; j < 4; j ++) {
                // 4 bytes description visual format * 4
                String format = BufferUtil.loadString(buffer, 4);
                if (format.charAt(0) != 0) {
                    desc.getFormats().add(format);
                }
            }
            // consume rest
            for (int j = 0; j < len - 4 * 4; j ++) {
                buffer.get();
            }
            if (desc.getFormats().size() > 0) {
                this.descs.add(desc);
            }
        }
    }
    
    public byte getVersion() {
        return version;
    }
    
    public int getFlags() {
        return flags;
    }
    
    public int getDescCount() {
        return descCount;
    }
    
    public List<Desc> getDescs() {
        return descs;
    }
}
