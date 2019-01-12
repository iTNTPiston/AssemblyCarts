package com.tntp.assemblycarts.tileentity;

public enum ACTEEvents {
    UNKNOWN, REVERSE_TRACK;

    public static ACTEEvents getEventSafe(int i) {
        ACTEEvents[] e = values();
        if (i >= 0 && i < e.length)
            return e[i];
        return UNKNOWN;
    }
}
