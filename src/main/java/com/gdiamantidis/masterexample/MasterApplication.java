package com.gdiamantidis.masterexample;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

public class MasterApplication {

    private ActorRef actor;

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        ActorSystem system = ActorSystem.create("CreationApplication", ConfigFactory.load().getConfig("remotecreation"));

        HashMap<String, ActorRef> map = new HashMap<String, ActorRef>();
        String command;
        do {

            command = scanner.nextLine().trim();
            if (command.equals("new")) {

                System.out.print("index name=");
                String indexName = scanner.nextLine().trim();
                System.out.println("index name = " + indexName);
                final ActorRef remoteActor = system.actorOf(Props.create(IndexActor.class, indexName), indexName);
                map.put(indexName, remoteActor);

                System.out.println("Tell actor something");
                remoteActor.tell(scanner.nextLine().trim(), null);
            }

            if(command.equals("all")) {
                String message = scanner.nextLine().trim();
                ActorSelection actorSelection = system.actorSelection("user/index*");

                actorSelection.tell(message, null);
            }


        } while (!command.equals("quit"));
    }

    public MasterApplication() {
//        final ActorRef remoteActor = system.actorOf(Props.create(JAdvancedCalculatorActor.class), "advancedCalculator");
//        actor = system.actorOf(Props.create(JCreationActor.class, remoteActor), "creationActor");
    }
}
