package com.yoloho.training.types;

import java.nio.ByteBuffer;

import com.yoloho.training.utils.BufferUtil;

/**
 * Handler reference
 * 
 * @author jason
 *
 */
public class HdlrBox extends MoovBoxBase {
    private byte version;
    private int flags;
    private String type;
    private String mediaType;
    private String manufacturer;
    private String componentTypeName;

    public HdlrBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
        // 1 byte version
        this.version = buffer.get();
        // 3 bytes flags
        this.flags = buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        this.flags <<= 8;
        this.flags |= buffer.get();
        // 4 bytes QUICKTIME type
        this.type = BufferUtil.loadString(buffer, 4);
        // 4 bytes subtype/media type
        this.mediaType = BufferUtil.loadString(buffer, 4);
        // 4 bytes QUICKTIME manufacturer reserved
        this.manufacturer = BufferUtil.loadString(buffer, 4);
        // 4 bytes QUICKTIME component reserved flags
        buffer.getInt();
        // 4 bytes QUICKTIME component reserved flags mask
        buffer.getInt();
        // component type name ASCII string
        StringBuilder builder = new StringBuilder();
        while (true) {
            byte ch = buffer.get();
            if (ch == 0) {
                break;
            }
            builder.append((char) ch);
        }
        this.componentTypeName = builder.toString();
    }

    public byte getVersion() {
        return version;
    }

    public int getFlags() {
        return flags;
    }

    public String getType() {
        return type;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getComponentTypeName() {
        return componentTypeName;
    }

}
