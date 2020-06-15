package com.yoloho.training.types;

import java.nio.ByteBuffer;

/**
 * Visual media (stream) info header
 * 
 * @author jason
 *
 */
public class VmhdBox extends MoovBoxBase {
    private byte version;
    private int flags;
    private short graphicMode;
    private long colorMode;

    public VmhdBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
        // 1 byte version
        this.version = buffer.get();
        // 3 bytes flags
        this.flags = buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        // 2 bytes QuickDraw graphic mode
        this.graphicMode = buffer.getShort();
        // 6 bytes graphic mode color = 3 * short unsigned QuickDraw RGB color 
        this.colorMode |= buffer.getShort();
        this.colorMode <<= 16;
        this.colorMode |= buffer.getShort();
        this.colorMode <<= 16;
        this.colorMode |= buffer.getShort();
    }
    
    public byte getVersion() {
        return version;
    }
    
    public int getFlags() {
        return flags;
    }
    
    public short getGraphicMode() {
        return graphicMode;
    }
    
    public long getColorMode() {
        return colorMode;
    }

}
