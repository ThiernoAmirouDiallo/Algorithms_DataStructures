/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrays;

/**
 *
 * @author Diallo110339
 */
public class InsertionSortRecusive {

    public static void main(String[] args) {
        int[] intArray = {20, 35, -15, 7, 55, 1, -22};

        insertionSort(intArray, intArray.length);
        System.out.println("Recursive insertion sort result");
        for (int i = 0; i < intArray.length; i++) {
            System.out.println(intArray[i]);
        }
    }

    public static void insertionSort(int[] input, int length) {
        if (length < 2) {
            return;
        }

        insertionSort(input, length - 1);
        insert(input, length - 1);

    }

    public static void insert(int[] input, int elementPostion) {
        int newElement = input[elementPostion];

        int i;

        for (i = elementPostion; i > 0 && input[i - 1] > newElement; i--) {
            input[i] = input[i - 1];
        }

        input[i] = newElement;

    }
}
