package com.yoloho.training.types;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class MoovBoxBase extends BaseBox {
    private int offset = -1;
    private List<MoovBoxBase> children = new ArrayList<>();

    public MoovBoxBase(int offset, ByteBuffer buffer) {
        super(buffer);
        this.offset = offset;
    }
    
    public void setOffset(int offset) {
        this.offset = offset;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public List<MoovBoxBase> getChildren() {
        return children;
    }
    
    public void addChild(MoovBoxBase child) {
        children.add(child);
    }
    
}
