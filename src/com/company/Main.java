package com.company; //comment this out when submitting

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
	    String filename = args[0];
        Scanner in;
        Stack<String> stack = new Stack<>();
        try {
            FileInputStream file = new FileInputStream(filename);
            in = new Scanner(file); //scanner for scanning in each expression from a file
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found");
            return;
        }
        while (in.hasNext()) { //this is for iterating through the entire file
            String str = in.nextLine();
            Scanner in2 = new Scanner(str); //scanner for scanning in parts of an expression
            while (in2.hasNext()) { //this is for iterating through a single expression
                String s = in.next();
                switch (s) {
                    case "+" -> {
                        String str1 = stack.pop();
                        String str2 = stack.pop();
                        String result = add(str1, str2);
                        stack.push(result);
                    }
                    case "*" -> {
                        String str1 = stack.pop();
                        String str2 = stack.pop();
                        String result = multiply(str1, str2);
                        stack.push(result);
                    }
                    case "^" -> {
                        String str1 = stack.pop();
                        String str2 = stack.pop();
                        String result = exponentiate(str1, str2);
                        stack.push(result);
                    }
                    default -> stack.push(str); //when the current element in the string is a number
                }
            }
            System.out.println(str + " = " + stack.pop());
        }
    }

    public static String add(String str1, String str2) {
        //take in the numbers, turn them each into linked lists with the 1's place at the end of the list
        //then add the 1's, the 10's, etc and push the results back into the lists
        //https://www.geeksforgeeks.org/sum-two-large-numbers/
        String result = "";
        int length = Math.max(str1.length(), str2.length());
        int carry = 0; //need to implement this somehow
        for (int i = 0; i < length; i++) {
            int num1 = Character.getNumericValue(str1.charAt(length - i - 1));
            int num2 = Character.getNumericValue(str2.charAt(length - i - 1));
            int sum = num1 + num2;
            result = sum + "";
        }
        result = new StringBuilder(result).reverse().toString();
        return result;
    }

    public static String multiply(String str1, String str2) {
        //get the 1st value by multiplying the top digits first like in handwritten multiplication
        //then get the 2nd value by multiplying the lower digits and put a 0 before everything else
        //use .add() to add these digits together and return the result

        String result = "";
        int length = Math.max(str1.length(), str2.length());
        int sum = 0;
        int carry = 0; //need to implement this somewhere
        for (int i = 0; i < length; i++) {
            int value1 = Character.getNumericValue(str1.charAt(length - i - 1));
            int value2 = Character.getNumericValue(str2.charAt(length - i - 1));
            for (int j = 0; j < value2; j++) {
                sum += value1;
                result = sum + ""; //needs to be in the loop to keep result as a string
            }
            sum = 0;
        }
        result = new StringBuilder(result).reverse().toString(); //reverses the number so that it's in the right order
        return result;
    }

    public static String exponentiate(String str1, String str2) {
        return str1;
    }
}
