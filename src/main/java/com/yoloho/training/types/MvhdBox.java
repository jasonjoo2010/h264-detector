package com.yoloho.training.types;

import java.nio.ByteBuffer;

public class MvhdBox extends MoovBoxBase {
    private byte version;
    private int flags;
    private long created; // from 1904
    private long modified; // from 1904
    private int timeScale;
    private long duration;
    private float playbackSpeed;
    private float volume;

    public MvhdBox(int offset, ByteBuffer buffer) {
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
            this.timeScale = buffer.getInt();
            this.duration = buffer.getLong();
        } else {
            // 4 bytes date
            this.created = buffer.getInt() & 0x00000000ffffffffL;
            this.modified = buffer.getInt() & 0x00000000ffffffffL;
            this.timeScale = buffer.getInt();
            this.duration = buffer.getInt();
        }
        // TODO 4 bytes playback spped
        buffer.getFloat();
        // TODO 2 bytes volume
        buffer.getInt();
        // 10 bytes reserved
        buffer.getLong();
        buffer.getShort();
        // 9 * 4 bytes decimal window
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        buffer.getInt();
        // 8 bytes QUICKTIME preview
        buffer.getLong();
        // 4 bytes QUICKTIME still poster
        buffer.getInt();
        // 8 bytes QUICKTIME selection time
        buffer.getLong();
        // 4 bytes QUICKTIME current time
        buffer.getInt();
        // 4 bytes next/new track id
        buffer.getInt();
    }

    public int getVersion() {
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

    public int getTimeScale() {
        return timeScale;
    }

    public long getDuration() {
        return duration;
    }

    public float getPlaybackSpeed() {
        return playbackSpeed;
    }

    public float getVolume() {
        return volume;
    }

}
