/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arrays;
import java.util.Arrays;

/**
 *
 * @author Diallo110339
 */
public class JdkSort {
     public static void main(String[] args) {
        int[] intArray = { 20, 35, -15, 7, 55, 1, -22 };

        Arrays.parallelSort(intArray);
//        Arrays.sort(intArray);

        System.out.println("JDK sort result");
        for (int i = 0; i < intArray.length; i++) {
            System.out.println(intArray[i]);
        }
    }
    
}
