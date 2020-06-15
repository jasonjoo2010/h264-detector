package com.yoloho.training.types;

import java.nio.ByteBuffer;

/**
 * Data reference box
 * 
 * @author jason
 *
 */
public class DinfBox extends MoovBoxBase {

    public DinfBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
    }

}
