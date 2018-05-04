package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class HazmatInfo  implements Serializable {

    private String hazmatCode;

    private String hazmatGroup;

    private String hazmatClass;

    public String getHazmatCode() {
        return hazmatCode;
    }

    public void setHazmatCode(String hazmatCode) {
        this.hazmatCode = hazmatCode;
    }

    public String getHazmatGroup() {
        return hazmatGroup;
    }

    public void setHazmatGroup(String hazmatGroup) {
        this.hazmatGroup = hazmatGroup;
    }

    public String getHazmatClass() {
        return hazmatClass;
    }

    public void setHazmatClass(String hazmatClass) {
        this.hazmatClass = hazmatClass;
    }
}
