package mergesorter;

import java.util.Comparator;

/**
 * This class carries out the merge sort algorithm using multiple threads.
 * It is a modified version of the MergeSorter class. It contains a modified version of the sort
 * method and the MergeSort method. Both methods contain a parameter for the number of threads available.
 *
 * @author Kentessa Fanfair
 */
public class ParallelMergeSorter {

    /**
     * Sorts an array, using the merge sort algorithm and additional threads.
     * @param a the array to sort
     * @param comp the comparator to compare array elements
     * @param availableThreads the number of threads allotted for the current iteration
     */
    public static <E> void sort(E[] a, Comparator<? super E> comp, int availableThreads) {
        mergeSort(a, 0, a.length - 1, comp, availableThreads);
    }

    /**
     * Sorts a range of an array, using the merge sort algorithm using multiple threads.
     *
     * @param a the array to sort
     * @param from the first index of the range to sort
     * @param to the last index of the range to sort
     * @param comp the comparator to compare array elements
     */
    private static <E> void mergeSort(E[] a, int from, int to, Comparator<? super E> comp, int availableThreads) {

        if (from == to)
            return;

        if (availableThreads <= 1) {
            MergeSorter.mergeSort(a, from, to, comp);
            return;
        }

        int mid = (from + to) / 2;

        // Sort the first and the second half on separate threads (if available)
        Thread leftSorterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mergeSort(a, from, mid, comp, availableThreads / 2);
            }
        });

        Thread rightSorterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mergeSort(a, mid + 1, to, comp, availableThreads / 2);
            }
        });


        leftSorterThread.start();
        rightSorterThread.start();

        try {
            leftSorterThread.join();
            rightSorterThread.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        ParallelMergeSorter.merge(a, from, mid, to, comp);
    }


    /**
     * Merges two adjacent subranges of an array
     *
     * @param a the array with entries to be merged
     * @param from the index of the first element of the first range
     * @param mid the index of the last element of the first range
     * @param to the index of the last element of the second range
     * @param comp the comparator to compare array elements
     */
    @SuppressWarnings("unchecked")
    private static <E> void merge(E[] a,
                                  int from, int mid, int to, Comparator<? super E> comp) {
        int n = to - from + 1;
        // Size of the range to be merged

        // Merge both halves into a temporary array b
        Object[] b = new Object[n];

        int i1 = from;
        // Next element to consider in the first range
        int i2 = mid + 1;
        // Next element to consider in the second range
        int j = 0;
        // Next open position in b

        // As long as neither i1 nor i2 past the end, move
        // the smaller element into b
        while (i1 <= mid && i2 <= to) {
            if (comp.compare(a[i1], a[i2]) < 0) {
                b[j] = a[i1];
                i1++;
            } else {
                b[j] = a[i2];
                i2++;
            }
            j++;
        }

        // Note that only one of the two while loops
        // below is executed
        // Copy any remaining entries of the first half
        while (i1 <= mid) {
            b[j] = a[i1];
            i1++;
            j++;
        }

        // Copy any remaining entries of the second half
        while (i2 <= to) {
            b[j] = a[i2];
            i2++;
            j++;
        }

        // Copy back from the temporary array
        for (j = 0; j < n; j++) {
            a[from + j] = (E) b[j];
        }
    }
}