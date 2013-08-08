package com.gdiamantidis;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.gdiamantidis.events.AddCommentEvent;
import com.gdiamantidis.events.Consumer;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ActorSystem simpleSystem = ActorSystem.create("SimpleSystem");

        ActorRef producer = simpleSystem.actorOf(new Props(Producer.class), "producer");
        simpleSystem.actorOf(new Props(Consumer.class), "consumer");

        int noOfEvents = 100;

        for (int i = 0; i < noOfEvents; i++) {
            Thread.sleep(2000);
            producer.tell(new AddCommentEvent(i), ActorRef.noSender());
        }
    }
}
