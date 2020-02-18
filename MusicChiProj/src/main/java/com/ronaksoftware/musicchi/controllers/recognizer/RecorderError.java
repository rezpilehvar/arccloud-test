package com.ronaksoftware.musicchi.controllers.recognizer;

public class RecorderError extends Exception {
    private int code;

    public RecorderError(int code) {
        super();
        this.code = code;
    }
}
