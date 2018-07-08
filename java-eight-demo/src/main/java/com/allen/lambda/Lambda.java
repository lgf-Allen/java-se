/**
 * 
 */
package com.allen.lambda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Test;

/**
 * This is a demo about java 8 Lambda Expressions.
 * 
 * @author Allen
 * @since 2018-07-08
 */
public class Lambda {

    @Test
    public void customerNoArgsFunctionalInterface() {
        Animal<String> animal = () -> "lisi";
        Assert.assertEquals("lisi", animal.get());

    }

    @Test
    public void testOneArgs() {
        Person<String, Integer> person = (String str) -> Integer.parseInt(str);
        Assert.assertEquals(Integer.valueOf(5), person.apply("5"));
    }

    @Test
    public void testBlock1() throws IOException {
        Consumer<String> b = s -> System.out.println(s);
        Arrays.asList("Foo", "Bar", "Baz", "Baz", "Foo", "Bar").forEach(b);
    }

    @Test
    public void testBlock2() {
        Consumer<String> b = s -> {
            System.out.println(s);
        };
        Arrays.asList("Foo", "Bar", "Baz", "Baz", "Foo", "Bar").forEach(b);
    }
    
    @Test
    public void testJava7() {
        
        ActionListener al = new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e.getActionCommand());
                
            }
        };
    }
    
    @Test
    public void testJava8() {
        ActionListener  al8 = e -> System.out.println(e.getActionCommand());
    }
    
    
    
}
