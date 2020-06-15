package com.yoloho.training.types;

import java.nio.ByteBuffer;

/**
 * Sound media (stream) info header
 * 
 * @author jason
 *
 */
public class SmhdBox extends MoovBoxBase {
    private byte version;
    private int flags;

    public SmhdBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
        // 1 byte version
        this.version = buffer.get();
        // 3 bytes flags
        this.flags = buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        // 2 bytes audio balance
        // balnce scale is left = negatives ; normal = 0.0 ; right = positives
        buffer.getShort();
        // 2 bytes reserved
        buffer.getShort();
    }

    public byte getVersion() {
        return version;
    }

    public int getFlags() {
        return flags;
    }

}
