package com.ronaksoftware.musicchi.controllers;

import com.ronaksoftware.musicchi.controllers.recognizer.RecognizeResult;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventController {
    public static final Subject<Object> authChanged = PublishSubject.create();
    public static final Subject<RecognizeResult> recognizeResult = PublishSubject.create();
}
