/**
 * 
 */
package com.allen.java8;

/**
 * This is a demo about java 8 Lambda Expressions.
 * @author first
 *
 */
public class LambdaTest {

    
    Runnable r1 = () -> System.out.println(this);
    Runnable r2 = () -> System.out.println(toString());
    /**
     * @param args
     */
    public static void main(String[] args) {
        new LambdaTest().r1.run();
        new LambdaTest().r2.run();
    }
    @Override
    public String toString() {
        return "hello,Lambda!";
    }
    

}
