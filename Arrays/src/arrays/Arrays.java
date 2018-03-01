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
public class Arrays {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] intArray = new int[7];
        intArray[0]=14;
        intArray[1]=-12;
        intArray[2]=45;
        intArray[3]=69;
        intArray[4]=45;
        intArray[5]=72;
        intArray[6]=21;
        
        for (int i=0; i< intArray.length; i++){
            System.out.println(intArray[i]);
        }

    }
    
}
