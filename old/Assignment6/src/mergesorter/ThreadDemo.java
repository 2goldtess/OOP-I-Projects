/*
 * Code created for educational purposes. TxState - CS3354
 */
package src.mergesorter;


/**
 *
 * @author vangelis
 */
// ThreadDemo.java
class ThreadDemo {

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            SomeThread mt = new SomeThread();
            mt.start();
        }
        for (int i = 0; i < 50; i++) {
            System.out.println("i = " + i + ", i * i = " + i * i);
        }
    }
}

class SomeThread extends Thread {

    public void run() {
        for (int count = 1, row = 1; row < 20; row++, count++) {
            for (int i = 0; i < count; i++) {
                System.out.print('*');
            }
            System.out.print('\n');
        }
    }
}
