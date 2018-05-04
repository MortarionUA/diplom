package com.gmail.dmytro.backend.data;

import com.vaadin.shared.util.SharedUtil;

public enum Incoterms {
    EXW , FCA , FAS , FOB , CFR , CIF , CIP , CPT , DAT , DAP , DDP;

    public String getDisplayName() {
        return SharedUtil.upperCaseUnderscoreToHumanFriendly(name());
    }


    public static Incoterms forDisplayName(String displayName) {
        for (Incoterms incoterms : values()) {
            if (displayName.toLowerCase().equals(incoterms.getDisplayName().toLowerCase())) {
                return incoterms;
            }
        }
        return null;
    }

}
