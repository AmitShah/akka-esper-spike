package com.gdiamantidis.simpleexample;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.espertech.esper.client.*;
import com.gdiamantidis.simpleexample.events.AddCommentEvent;
import com.gdiamantidis.simpleexample.events.AverageCountEvent;

import java.util.Map;

public class Consumer extends UntypedActor {


    private final EPServiceProvider epService;
    private final ActorSelection producer;
    private ActorRef target;

    public Consumer() {
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.gdiamantidis.simpleexample.events");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        EPAdministrator epAdministrator = epService.getEPAdministrator();

        EPStatement epl = epAdministrator.createEPL("select avg(count) as avg_val from AddCommentEvent.win:time_batch(5 sec)");
        epl.setSubscriber(new Subscriber());
        producer = getContext().system().actorSelection("user/producer");
    }


    public class Subscriber {
        public void update(Map<String, Double> eventMap) {
            Double avg = eventMap.get("avg_val");
            producer.tell(new AverageCountEvent(avg), getSelf());
        }
    }

    @Override
    public void onReceive(Object event) throws Exception {
        if (event instanceof AddCommentEvent) {
            epService.getEPRuntime().sendEvent(event);
            System.out.println("Received " + event.getClass().getSimpleName() + " count: " + ((AddCommentEvent) event).getCount());
            if (target != null) target.forward(event, getContext());
        } else if (event instanceof ActorRef) {
            target = (ActorRef) event;
            getSender().tell("done", getSelf());
        }
    }
}
