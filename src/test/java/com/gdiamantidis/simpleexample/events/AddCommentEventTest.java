package com.gdiamantidis.simpleexample.events;

import org.junit.Test;

import java.lang.reflect.Field;

public class AddCommentEventTest {


    @Test
    public void testName() throws Exception {
        AddCommentEvent addCommentEvent = new AddCommentEvent(1);
        Field test = addCommentEvent.getClass().getDeclaredField("test");
        Class<?> declaringClass = test.getDeclaringClass();
        Class<?> type = test.getType();
        System.out.println("type = " + type);
        System.out.println("declaringClass = " + declaringClass);


        Field testInt = addCommentEvent.getClass().getDeclaredField("testInt");
        Class<?> type1 = testInt.getType();
        System.out.println("type1 = " + type1);

    }
}
