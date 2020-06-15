package com.yoloho.training.types;

import java.nio.ByteBuffer;

/**
 * Sample (framing info) table box
 * 
 * @author jason
 *
 */
public class StblBox extends MoovBoxBase {

    public StblBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
    }

}
