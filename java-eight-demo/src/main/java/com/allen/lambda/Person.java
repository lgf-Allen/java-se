/**
 * 
 */
package com.allen.lambda;

/**
 * @author first
 *
 */
@FunctionalInterface
public interface Person<T , R> {

    R apply(T str);
}
