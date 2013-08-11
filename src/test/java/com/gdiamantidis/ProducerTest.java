package com.gdiamantidis;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Inbox;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import com.gdiamantidis.simpleexample.Consumer;
import com.gdiamantidis.simpleexample.Producer;
import com.gdiamantidis.simpleexample.events.AddCommentEvent;
import com.gdiamantidis.simpleexample.events.AverageCountEvent;
import org.junit.Ignore;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ProducerTest {

    @Ignore("failing - not waiting for result")
    @Test
    public void testName() throws Exception {
        Props props = new Props(Producer.class);
        ActorSystem simpleSystem = ActorSystem.create("SimpleSystem");
        TestActorRef<Producer> testActorRef = TestActorRef.create(simpleSystem, props, "testA");

        AverageCountEvent event = new AverageCountEvent(1.0);

        Object result = Await.result(Patterns.ask(testActorRef, event, 5000), Duration.apply(5, TimeUnit.SECONDS));
        System.out.println(result);
    }

    @Test
    public void asyncTest() throws Exception {
        final ActorSystem system = ActorSystem.create();

        new JavaTestKit(system) {{
            final ActorRef producer = system.actorOf(Props.create(Producer.class), "producer");
            final ActorRef consumer = system.actorOf(Props.create(Consumer.class), "consumer");

            final JavaTestKit producerProbe = new JavaTestKit(system);
            final JavaTestKit consumerProbe = new JavaTestKit(system);
            producer.tell(producerProbe.getRef(), getRef());
            consumer.tell(consumerProbe.getRef(), getRef());

            new Within(duration("100 seconds")) {

                @Override
                protected void run() {
                    AverageCountEvent msg = new AverageCountEvent(1.0);
                    AddCommentEvent msg1 = new AddCommentEvent(1);
                    producer.tell(msg1, getRef());

                    new AwaitCond() {
                        protected boolean cond() {
                            return consumerProbe.msgAvailable();
                        }
                    };

                    consumerProbe.expectMsgEquals(Duration.apply(10, TimeUnit.SECONDS), msg1);
                    producerProbe.expectMsgEquals(Duration.apply(10, TimeUnit.SECONDS), msg1);
                }
            };
        }};
    }

    @Test
    public void anotherWayToTest() throws Exception {
        ActorSystem simpleSystem = ActorSystem.create("SimpleSystem");

        AddCommentEvent event = new AddCommentEvent(1);

        ActorRef producer = simpleSystem.actorOf(new Props(Producer.class));

        Inbox inbox = Inbox.create(simpleSystem);
        inbox.send(producer, event);

        Object receive = inbox.receive(Duration.create(5, TimeUnit.SECONDS));
        System.out.println("message received = " + receive);


    }
}
