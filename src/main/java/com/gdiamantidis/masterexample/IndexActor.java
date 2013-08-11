package com.gdiamantidis.masterexample;

import akka.actor.UntypedActor;

public class IndexActor extends UntypedActor{

    private final String actorName;

    public IndexActor(String actorName) {
        this.actorName = actorName;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        System.out.println(actorName + " received " + o);
    }
}
