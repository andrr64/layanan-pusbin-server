package com.pusbin.layanan.common.dto;

public class FilterValue {
   private String label;
   private int value;

    public FilterValue() {}

    public FilterValue(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int  value) {
        this.value = value;
    }
}
