package com.gdiamantidis;

import akka.actor.*;
import com.gdiamantidis.events.AddCommentEvent;
import com.gdiamantidis.events.AverageCountEvent;
import com.gdiamantidis.events.Consumer;
import scala.concurrent.Await;

public class Producer extends UntypedActor {
    private ActorRef target;


    @Override
    public void onReceive(Object event) throws Exception {
        if (event instanceof AddCommentEvent) {
            ActorSelection actorSelection = getContext().system().actorSelection("user/consumer");
            actorSelection.tell(event, getSelf());
            getSender().tell("got it", getSelf());
            if (target != null) target.forward(event, getContext());
        } else if (event instanceof AverageCountEvent) {
            System.out.println("[PRODUCER] average count " + ((AverageCountEvent) event).getCount());
        }    else if (event instanceof ActorRef) {
            target = (ActorRef) event;
            getSender().tell("done", getSelf());
        }
    }
}
