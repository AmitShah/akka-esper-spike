package com.gdiamantidis.masterexample;

import akka.actor.UntypedActor;

import java.util.UUID;

public class Master extends UntypedActor {

    @Override
    public void preStart() throws Exception {
        System.out.println(UUID.randomUUID());
    }

    @Override
    public void onReceive(Object o) throws Exception {

    }
}
