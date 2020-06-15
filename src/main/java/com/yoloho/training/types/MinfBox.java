package com.yoloho.training.types;

import java.nio.ByteBuffer;

/**
 * Media information
 * 
 * @author jason
 *
 */
public class MinfBox extends MoovBoxBase {

    public MinfBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
    }

}
