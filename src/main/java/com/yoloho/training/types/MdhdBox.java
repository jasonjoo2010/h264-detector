package com.yoloho.training.types;

import java.nio.ByteBuffer;

public class MdhdBox extends MoovBoxBase {
    private byte version;
    private int flags;
    private long created;
    private long modified;
    private long timeScale;
    private long duration;
    private short language;

    public MdhdBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
        // 1 byte version
        this.version = buffer.get();
        // 3 bytes flags
        this.flags = buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        if (this.version == 1) {
            // 8 bytes date
            this.created = buffer.getLong();
            this.modified = buffer.getLong();
        } else {
            // 4 bytes date
            this.created = buffer.getInt() & 0x00000000ffffffffL;
            this.modified = buffer.getInt() & 0x00000000ffffffffL;
        }
        // 4 bytes time scale
        this.timeScale = buffer.getInt();
        if (this.version == 1) {
            this.duration = buffer.getLong();
        } else {
            this.duration = buffer.getInt();
        }
        // 1/8 byte ISO language padding = 1-bit value set to 0
        // 1 7/8 bytes content language = 3 * 5-bits ISO 639-2 language code less 0x60
        // example code for english = 0x15C7
        this.language = buffer.getShort();
        // 2 bytes QUICKTIME quality
        buffer.getShort();
    }

    public byte getVersion() {
        return version;
    }

    public int getFlags() {
        return flags;
    }

    public long getCreated() {
        return created;
    }

    public long getModified() {
        return modified;
    }

    public long getDuration() {
        return duration;
    }
    
    public short getLanguage() {
        return language;
    }
    
    public long getTimeScale() {
        return timeScale;
    }

}
