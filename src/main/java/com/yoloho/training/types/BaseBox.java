package com.yoloho.training.types;

import java.nio.ByteBuffer;

import com.yoloho.training.utils.BufferUtil;

public class BaseBox {
    private int size;
    private String type;
    
    public BaseBox(ByteBuffer buffer) {
        this.size = buffer.getInt();
        this.type = BufferUtil.loadString(buffer, 4);
    }

    public int getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

}
