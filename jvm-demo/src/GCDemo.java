/**
 * @Author: lingfeng
 * @Date: 2019/5/19 17:00
 */
public class GCDemo {

    //    public static void main(String[] args) {
//        System.out.println("Hello world");
//    }
    public static void main(String[] args) {
        // allocate 4M space
        byte[] b = new byte[4 * 1024 * 1024];
        System.out.println("first allocate");
        b = new byte[4 * 1024 * 1024];
        System.out.println("Second allocate");
    }
}


