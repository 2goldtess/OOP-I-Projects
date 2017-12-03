package mergesorter;

import java.util.Arrays;
import java.util.Comparator;

class ParallelSortTester extends Thread {

    static final int AVAILABLETHREADS = Runtime.getRuntime().availableProcessors();
    static final int ROUNDS = 15;
    int LENGTH = 1000;   // length of array to sort for first iteration
    Integer[] randomArrays = null;


//    ParallelSortTester(String threadName) {
//        super(threadName);
//    }




    @Override
    public void run() {

        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer d1, Integer d2) {
                return d1.compareTo(d2);
            }
        };


        for (int k = 1; k <= AVAILABLETHREADS; k *= 2 ) {

            System.out.printf("%n%n %s :%n", k + " threads");

            for (int i = 0; i < ROUNDS; i++) {

                randomArrays = SortTester.createRandomArray(LENGTH);

                // run the algorithm and time how long it takes to sort the elements
                long startTime = System.currentTimeMillis();
                ParallelMergeSorter.sort(randomArrays, comp, k / 2);
                long endTime = System.currentTimeMillis();

                if (!SortTester.isSorted(randomArrays, comp)) {
                    throw new RuntimeException("not sorted afterward: " + Arrays.toString(randomArrays));
                }

                System.out.printf("%10d elements  =>  %6d ms \n", LENGTH, endTime - startTime);
                LENGTH = LENGTH * 2;
            }

            LENGTH = 1000;
        }
    }
}