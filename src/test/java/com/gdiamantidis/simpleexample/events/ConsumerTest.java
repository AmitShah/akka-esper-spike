package com.gdiamantidis.simpleexample.events;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.testkit.TestActorRef;
import com.gdiamantidis.simpleexample.Consumer;
import org.junit.Test;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class ConsumerTest {

    @Test
    public void consumerTest() throws Exception {

        Props props = new Props(Consumer.class);
        ActorSystem simpleSystem = ActorSystem.create("SimpleSystem");
        TestActorRef<Consumer> testActorRef = TestActorRef.create(simpleSystem, props, "testA");

        AverageCountEvent event = new AverageCountEvent(1.0);

        Object result = Await.result(Patterns.ask(testActorRef, event, 5000), Duration.apply(5, TimeUnit.SECONDS));
        System.out.println(result);

    }
}
