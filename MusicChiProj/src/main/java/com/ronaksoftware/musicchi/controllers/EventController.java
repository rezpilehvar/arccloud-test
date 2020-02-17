package com.ronaksoftware.musicchi.controllers;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class EventController {
    public static final Subject<Object> authChanged = PublishSubject.create();
}
