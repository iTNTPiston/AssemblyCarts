package com.tntp.minecraftmodapi;

public enum Turnary {
    TRUE, FALSE, UNKNOWN;
    public boolean value() {
        return (this == TRUE);
    }

}
