package com.yoloho.training.types;

import java.nio.ByteBuffer;

public class MdatBox extends BaseBox {
    
    public MdatBox(ByteBuffer buffer) {
        super(buffer);
        if (getSize() == 1) {
            // 64bit unsigned
            this.size = buffer.getLong();
        }
    }

}
