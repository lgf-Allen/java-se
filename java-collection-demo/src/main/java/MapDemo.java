package main.java;


import java.util.HashMap;
import java.util.Map;

public class MapDemo {


    public static void hashMapRehash(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>(10);
        for (int i = 0; i < 1000000; i++){
            map.put("124","123");
        }
        long endTime = System.currentTimeMillis();
        long durationTime = endTime - startTime;
        System.out.println("rehash time:" + durationTime);
    }

    public static void hashMapInit(){
        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>(1333400);
        for (int i = 0; i < 1000000; i++){
            map.put("124","123");
        }
        long endTime = System.currentTimeMillis();
        long durationTime = endTime - startTime;
        System.out.println("not rehash time:" + durationTime);
    }

    public static void main(String[] args) {
        hashMapRehash();
        hashMapInit();
    }

}
