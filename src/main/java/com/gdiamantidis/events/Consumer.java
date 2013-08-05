package com.gdiamantidis.events;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import com.espertech.esper.client.*;

import java.util.Map;

public class Consumer extends UntypedActor {


    private final EPServiceProvider epService;
    private final ActorSelection producer;

    public Consumer() {
        Configuration config = new Configuration();
        config.addEventTypeAutoName("com.gdiamantidis.events");

        epService = EPServiceProviderManager.getDefaultProvider(config);
        EPAdministrator epAdministrator = epService.getEPAdministrator();

        EPStatement epl = epAdministrator.createEPL("select avg(count) as avg_val from AddCommentEvent.win:time_batch(5 sec)");
        epl.setSubscriber(new Subscriber());
        producer = getContext().system().actorSelection("user/producer");
    }


    public class Subscriber {

        public void update(Map<String, Double> eventMap) {

            // average temp over 10 secs
            Double avg = (Double) eventMap.get("avg_val");
            System.out.println("avg = " + avg);

            producer.tell(new AverageCountEvent(avg), getSelf());

        }
    }

    @Override
    public void onReceive(Object event) throws Exception {
        if (event instanceof AddCommentEvent) {
            epService.getEPRuntime().sendEvent(event);
            System.out.println("Received " + event.getClass().getSimpleName());
        } else {
            unhandled(event);
        }
    }
}
