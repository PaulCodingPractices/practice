package com.example.demo.events;

import org.springframework.context.ApplicationEvent;

public class StepEvent extends ApplicationEvent {

    public StepEvent(Object source) {
        super(source);
    }
}
