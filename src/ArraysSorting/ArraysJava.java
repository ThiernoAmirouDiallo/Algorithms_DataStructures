/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ArraysSorting;

/**
 *
 * @author Diallo110339
 */
public class ArraysJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[] intArray = new int[7];

	    intArray[0] = 20;
	    intArray[1] = 35;
	    intArray[2] = -15;
	    intArray[3] = 7;
	    intArray[4] =55;
	    intArray[5] = 1;
	    intArray[6] = -22;

	    int index = -1;
	    for (int i = 0; i < intArray.length; i++) {
	        if (intArray[i] == 7) {
	            index = i;
	            break;
            }
        }

        System.out.println("index = " + index);
        String chaine ="ABCDE";
        System.out.println("ABDC = " + chaine.substring(4, 5));
        System.out.println(chaine.charAt(1) + "'a'="+(0+'a'));

        
    }
    
}
