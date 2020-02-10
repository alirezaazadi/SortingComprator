
public class SortingAlgorithms {

    protected static void insertionSort(int[] unsortedList) {
        if (unsortedList.length <= 1) {
            //if we jus had one element, so it must be sorted, we don't need any sorting
            return;
        } else {
            }
            for (int i = 1; i < unsortedList.length; i++) {
                int extractedElement = unsortedList[i];
                int j = 0;
                for (j = i - 1; (j >= 0 && unsortedList[j] > extractedElement); j--)
                    unsortedList[j + 1] = unsortedList[j];
                unsortedList[j + 1] = extractedElement;
            }
        }
    }


    protected static void mergeSort(int[] unsortedList, int lowerBound, int upperBound) {
        if (unsortedList.length <= 1 || (upperBound > unsortedList.length - 1) || lowerBound < 0) {
            /*
            check some states to prevent from exception
            by checking the logic of inputs . . .
             */
            return;
        } else {
            if (upperBound > lowerBound) {
                //if our array has a length
                int middleBound = lowerBound + (upperBound - lowerBound) / 2;
                mergeSort(unsortedList, lowerBound, middleBound);
                mergeSort(unsortedList, (middleBound + 1), upperBound);
                /*
                divide our input array recursively until
                we arrive to an array with one element
                then merge this array to each other ( srt them and merge them )
                until we arrive to our first array by using merge function
                 */
                merge(lowerBound, middleBound, upperBound, unsortedList);
            } else {
                return;
            }
        }

    }

    private static void merge(int lowerBound, int middleBound,
                              int upperBound, int[] unsortedList) {

        int i = 0, j = 0, k = lowerBound;
        int leftSize = middleBound - lowerBound + 1;
        int rightSize = upperBound - middleBound;
        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];
        for (; i < leftSize; i++)
            leftArray[i] = unsortedList[lowerBound + i];

        for (; j < rightSize; j++)
            rightArray[j] = unsortedList[middleBound + 1 + j];

        for (i = 0, j = 0; i < leftSize && j < rightSize; k++) {

            if (leftArray[i] <= rightArray[j]) {
                unsortedList[k] = leftArray[i];
                i++;
            } else {
                unsortedList[k] = rightArray[j];
                j++;
            }
        }
        for (; i < leftSize; i++, k++)
            unsortedList[k] = leftArray[i];

        for (; j < rightSize; j++, k++)
            unsortedList[k] = rightArray[j];

    }

}
