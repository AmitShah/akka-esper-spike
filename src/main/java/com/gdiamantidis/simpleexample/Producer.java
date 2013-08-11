package com.gdiamantidis.simpleexample;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.gdiamantidis.simpleexample.events.AddCommentEvent;
import com.gdiamantidis.simpleexample.events.AverageCountEvent;

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
        } else if (event instanceof ActorRef) {
            target = (ActorRef) event;
            getSender().tell("done", getSelf());
        }
    }
}
