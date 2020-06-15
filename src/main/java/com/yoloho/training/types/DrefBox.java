package com.yoloho.training.types;

import java.nio.ByteBuffer;

/**
 * Data (locator) information box
 * 
 * @author jason
 *
 */
public class DrefBox extends MoovBoxBase {

    public DrefBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
    }

}
