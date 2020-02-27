package com.ronaksoftware.musicchi.controllers;

import com.ronaksoftware.musicchi.controllers.recognizer.RecognizeResult;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventController {
    public static final Subject<Object> authChanged = PublishSubject.create();
    public static final Subject<RecognizeResult> recognizeResult = PublishSubject.create();
    public static final Subject<Boolean> recognizeStatusChanged = PublishSubject.create();

    public static final Subject<Object[]> songPlayingPlayStateChanged = PublishSubject.create();
    public static final Subject<Object[]> songPlayingProgressDidChanged = PublishSubject.create();
    public static final Subject<Object[]> songPlayingDidReset = PublishSubject.create();
    public static final Subject<Object[]> songPlayingDidStart = PublishSubject.create();
}
