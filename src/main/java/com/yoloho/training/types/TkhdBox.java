package com.yoloho.training.types;

import java.nio.ByteBuffer;

public class TkhdBox extends MoovBoxBase {
    private byte version;
    private int flags;
    private long created;
    private long modified;
    private long duration;

    public TkhdBox(int offset, ByteBuffer buffer) {
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
        // 4 bytes next/new track id
        buffer.getInt();
        // 8 bytes reserved
        buffer.getLong();
        if (this.version == 1) {
            this.duration = buffer.getLong();
        } else {
            this.duration = buffer.getInt();
        }
        // 4 bytes reserved
        buffer.getInt();
        // 2 bytes video layer
        buffer.getShort();
        // 2 bytes QUICKTIME alternate/other
        buffer.getShort();
        // 2 bytes track audio volume
        buffer.getShort();
        // 2 bytes reserved
        buffer.getShort();
        // 9 * 4 bytes decimal video geometry
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        // 8 bytes decimal video frame size
        buffer.getLong();
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

}
