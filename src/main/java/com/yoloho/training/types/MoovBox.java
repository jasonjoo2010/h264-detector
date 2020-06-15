package com.yoloho.training.types;

import java.nio.ByteBuffer;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.yoloho.training.types.StsdBox.Desc;

public class MoovBox extends MoovBoxBase {
    public MoovBox(int offset, ByteBuffer buffer) {
        super(offset, buffer);
    }
    
    private boolean dig(List<MoovBoxBase> boxes) {
        for (MoovBoxBase box : boxes) {
            if (box instanceof StsdBox) {
                for (Desc desc : ((StsdBox) box).getDescs()) {
                    if (desc.getFormats().contains("avc1")) {
                        return true;
                    }
                }
            }
            if (box.getChildren().size() > 0) {
                if (dig(box.getChildren())) {
                    return true;
                }
            }
        }
        return false;
    }
    
    @JSONField(serialize = false, deserialize = false)
    public boolean isH264() {
        return dig(getChildren());
    }
    
}
