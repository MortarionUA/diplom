package com.gmail.dmytro.backend.data.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class Port extends AbstractEntity {

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
