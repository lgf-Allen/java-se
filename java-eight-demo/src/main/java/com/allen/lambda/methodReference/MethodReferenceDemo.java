package com.allen.lambda.methodReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 
 * @author first
 *
 */
public class MethodReferenceDemo {

    public static void main(String[] args) {
        Cat o1 = new Cat("lisi", "red");
        Cat o2 = new Cat("wangwu", "blue");
        List<Cat> list = new ArrayList<Cat>();
        list.add(o1);
        list.add(o2);
        System.out.println(java7(list));

    }

    // Java7
    private static <T> Comparable<T> java7(List<Cat> list) {
        // Java 7
        Collections.sort(list, new Comparator<Cat>() {

            @Override
            public int compare(Cat o1, Cat o2) {
                int n = o1.getBrand().compareTo(o2.getBrand());
                if (n == 0) {
                    return o1.getColor().compareTo(o2.getColor());
                }
                return n;
            }

        });
        return null;
    }

}
