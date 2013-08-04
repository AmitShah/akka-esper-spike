package com.gdiamantidis;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.gdiamantidis.events.AverageCountEvent;
import com.gdiamantidis.events.Consumer;
import com.gdiamantidis.events.Event;

public class Producer extends UntypedActor {

    private final ActorRef consumer;

    public Producer() {
        consumer = getContext().system().actorOf(new Props(Consumer.class), "consumer");
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if(o instanceof Event) {
            consumer.tell(o, getSelf());
        } else if(o instanceof AverageCountEvent) {
            System.out.println("[PRODUCER] average count " +  ((AverageCountEvent)o).getCount());
        }
    }
}
