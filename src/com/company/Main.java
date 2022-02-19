package com.company; //comment this out when submitting

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	    String filename = args[0];
        Scanner in;
        AStack stack = new AStack();
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
                String s = in2.next();
                if (s.charAt(0) == '0') { //removes leading zeros
                    s = s.replace("0", "");
                }
                switch (s) {
                    case "+" -> {
                        String str1 = stack.pop().toString();
                        String str2 = stack.pop().toString();
                        String result = add(str1, str2);
                        stack.push(result);
                        System.out.println(result);
                    }
                    /*case "*" -> {
                        String str1 = stack.pop().toString();
                        String str2 = stack.pop().toString();
                        String result = multiply(str1, str2);
                        stack.push(result);
                    }
                    case "^" -> {
                        String str1 = stack.pop().toString();
                        String str2 = stack.pop().toString();
                        String result = exponentiate(str1, str2);
                        stack.push(result);
                    }*/
                    default -> stack.push(s); //when the current element in the string is a number
                }
            }
            //System.out.println(str + " = " + stack.pop());
        }
    }

    //almost done, but theres an issue with the number not always being correct
    public static String add(String str1, String str2) {
        //take in the numbers, turn them each into linked lists with the 1's place at the end of the list
        //then add the 1's, the 10's, etc and push the results back into the lists
        LList list1 = new LList();
        LList list2 = new LList();
        String result = "";
        for (int i = str1.length() - 1; i > -1; i--) { //creates a reversed list of the 1st number
            int num = Character.getNumericValue(str1.charAt(i));
            list1.append(num);
        }
        for (int i = str2.length() - 1; i > -1; i--) { //creates a reversed list of the 2nd number
            int num = Character.getNumericValue(str2.charAt(i));
            list2.append(num);
        }

        int carry = 0;
        for (int i = 0; i < list1.length(); i++) {
            int num1;
            int num2;

            if (list1.isAtEnd()) {
                num1 = 0;
            }
            else {
                num1 = (int)list1.getValue();
                list1.next();
            }
            if (list2.isAtEnd()) {
                num2 = 0;
            }
            else {
                num2 = (int)list2.getValue();
                list2.next();
            }

            int sum = num1 + num2 + carry;
            if (sum > 9) {
                carry = sum / 10; //gets the first digit of the sum and turns it into a carry if > 9
            }
            else {
                carry = 0; //resets the carry for the next iteration
            }
            result = result + (sum % 10);
            if (list1.isAtEnd() && list2.isAtEnd()) {
                result += carry;
            }
        }
        result = new StringBuilder(result).reverse().toString();
        return result;
    }

    public static String multiply(String str1, String str2) {
        //get the 1st value by multiplying the top digits first like in handwritten multiplication
        //then get the 2nd value by multiplying the lower digits and put a 0 before everything else
        //use .add() to add these digits together and return the result
        LList list1 = new LList();
        LList list2 = new LList();
        String result = "";
        for (int i = str1.length() - 1; i > 0; i--) {
            list1.append((int)str1.charAt(i));
        }
        for (int i = str2.length() - 1; i > 0; i--) {
            list2.append((int)str2.charAt(i));
        }

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
        LList list1 = new LList();
        LList list2 = new LList();
        String result = "";
        for (int i = str1.length() - 1; i > 0; i--) {
            list1.append((int)str1.charAt(i));
        }
        for (int i = str2.length() - 1; i > 0; i--) {
            list2.append((int)str2.charAt(i));
        }

        return result;
    }
}
