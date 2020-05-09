package com.yoloho.training.types;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import com.yoloho.training.utils.BufferUtil;

public class TypeBox extends BaseBox {
    private int version;
    private String brand;
    private List<String> additionalBrands = new ArrayList<>();
    
    public TypeBox(ByteBuffer buffer) {
        super(buffer);
        int begin = buffer.position() - 8;
        this.brand = BufferUtil.loadString(buffer, 4);
        this.version = buffer.getInt();
        while (buffer.position() < begin + getSize()) {
            this.additionalBrands.add(BufferUtil.loadString(buffer, 4));
        }
    }

    public int getVersion() {
        return version;
    }

    public String getBrand() {
        return brand;
    }

    public List<String> getAdditionalBrands() {
        return additionalBrands;
    }

}
