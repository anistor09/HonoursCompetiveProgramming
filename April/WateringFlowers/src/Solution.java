class Solution {


    /**
     * This method is used to count the number of inversions in the array and also sorts the array.
     *
     * @param array The array to be sorted.
     * @return The number of inversions in the array.
     */
    static long countInversions(int[] array) {

        return mergeSort(array,0,array.length-1);

    }
    static long mergeSort(int[] array, int start, int end){
        if(end == start)
            return 0;
        int mid = (start+end)/2;
        long countLeft = mergeSort(array ,start , mid)%(1000000007);
        long countRight =mergeSort(array, mid+1, end)%(1000000007);
        long countCurrent = merge(array, start,mid, end)%(1000000007);
        return (countLeft + countRight + countCurrent)%(1000000007);
    }
    static long merge(int[] arr, int start,int mid, int end){
        int[] left = new int[mid-start+1];
        int[] right = new int[end-mid];
        long count = 0;

        for(int i = 0; i< left.length; i++){
            left[i] = arr[start+i];
        }
        for(int i = 0; i< right.length; i++){
            right[i] = arr[mid+i+1];
        }
        int i = 0;
        int j = 0;
        int t = start;

        while(i< left.length && j < right.length){
            if(left[i] <= right[j]){
                arr[t++] = left[i];
                i++;
            }else{
                arr[t++] = right[j];

                count += (left.length-i);
                count %= 1000000007;
                j++;
            }
        }

        while(i< left.length){
            arr[t++] = left[i];
            i++;
        }

        while(j < right.length){
            arr[t++] = right[j];
            j++;
        }
        return count% 1000000007;
    }
}
