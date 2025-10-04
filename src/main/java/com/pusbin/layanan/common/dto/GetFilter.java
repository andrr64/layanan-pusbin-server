package com.pusbin.layanan.common.dto;

import java.util.List;

public class GetFilter {
    private String key;
    private String label;
    private List<FilterValue> values;
    
    public GetFilter() {}

    public GetFilter(String key, String label, List<FilterValue> values) {
        this.key = key;
        this.label = label;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<FilterValue> getValues() {
        return values;
    }

    public void setValues(List<FilterValue> values) {
        this.values = values;
    }
}
