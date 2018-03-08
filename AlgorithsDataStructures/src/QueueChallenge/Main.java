package academy.learnprogramming.queueschallenge;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        // should return true
        System.out.println(checkForPalindrome("abccba"));
        // should return true
        System.out.println(checkForPalindrome("Was it a car or a cat I saw?"));
        // should return true
        System.out.println(checkForPalindrome("I did, did I?"));
        // should return false
        System.out.println(checkForPalindrome("hello"));
        // should return true
        System.out.println(checkForPalindrome("Don't nod"));
    }

    public static boolean checkForPalindrome(String string) {

        LinkedList<String> stack=new LinkedList<String>();      
        LinkedList<String> queue=new LinkedList<String>();

        String formatedString =string.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        for (int i=0; i<formatedString.length();i++){
            stack.push(formatedString.substring(i, i+1));
            queue.addLast(formatedString.substring(i, i+1));
        }
        
        String poped="";
        while (!stack.isEmpty() && !queue.isEmpty() && stack.pop().equals(queue.removeFirst()))
            ;
                
        return stack.isEmpty() && queue.isEmpty()?true:false;
    }
}
