package com.github.kazuhito_m.mysample.domain.model.profile;

public class ImageBinary {
    final byte[] value;

    public ImageBinary(byte[] value) {
        this.value = value;
    }

    public byte[] value() {
        return value;
    }
}
