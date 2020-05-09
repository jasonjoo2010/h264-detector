package com.yoloho.training.types;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.yoloho.training.utils.BufferUtil;

public class MoovBox extends BaseBox {
    
    public MoovBox(ByteBuffer buffer) {
        super(buffer);
        int begin = buffer.position() - 8;
    }

}
