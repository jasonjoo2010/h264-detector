package com.yoloho.training.types;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Edit list
 * 
 * @author jason
 *
 */
public class ElstBox extends MoovBoxBase {
    public static class Edit {
        private long timeLength;
        private long startTime;
        public Edit(long length, long start) {
            this.timeLength = length;
            this.startTime = start;
        }
        
        public long getStartTime() {
            return startTime;
        }
        
        public long getTimeLength() {
            return timeLength;
        }
    }
    
    private byte version;
    private int flags;
    private int editsCount;
    private List<Edit> edits = new ArrayList<>();

    public ElstBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
        // 1 byte version
        this.version = buffer.get();
        // 3 bytes flags
        this.flags = buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.editsCount = buffer.getInt();
        for (int i = 0; i < this.editsCount; i++) {
            if (this.version == 1) {
                // 8 bytes date
                this.edits.add(new Edit(buffer.getLong(), buffer.getLong()));
            } else {
                // 4 bytes date
                this.edits.add(new Edit(buffer.getInt() & 0x00000000ffffffffL, buffer.getInt() & 0x00000000ffffffffL));
            }
        }

        // 4 bytes decimal playback speed
        buffer.getInt();
    }

    public byte getVersion() {
        return version;
    }

    public int getFlags() {
        return flags;
    }

    public int getEditsCount() {
        return editsCount;
    }

    public List<Edit> getEdits() {
        return edits;
    }

}
