/**
 * 
 */
package com.allen.lambda;

/**
 * This is a customer functional interface.
 * @author first
 * FunctionalInterface:In Java 8 a functional interface is defined as an interface with exactly one abstract method(The one abstract method is created by the interface but not including inheritances in super interface .). This
 * even applies to interfaces that were created with previous versions of Java.
 */
@FunctionalInterface
public interface Animal<T> {

    String get();
}