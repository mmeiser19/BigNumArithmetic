package com.company; //comment this out when submitting

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BigNumArithmetic {
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
            String line = in.nextLine();
            Scanner in2 = new Scanner(line); //scanner for scanning in parts of an expression
            while (in2.hasNext()) { //this is for iterating through a single expression
                String s = in2.next();
                //need to have a case for if the number is 00000
                if (s.charAt(0) == '0' && s.length() > 1) { //removes leading zeros but leaves one 0 if the str is all 0s
                    s = s.replace("0", "");
                }
                switch (s) {
                    case "+" -> {
                        String str2 = stack.pop().toString();
                        String str1 = stack.pop().toString();
                        LList list1 = stringToList(str2);
                        LList list2 = stringToList(str1);
                        LList result = add(list1, list2);
                        String strResult = listToString(result);
                        stack.push(strResult);
                        System.out.println(strResult);
                    }
                    case "*" -> {
                        String str2 = stack.pop().toString();
                        String str1 = stack.pop().toString();
                        LList list1 = stringToList(str2);
                        LList list2 = stringToList(str1);
                        LList result = multiply(list1, list2);
                        String strResult = listToString(result);
                        stack.push(strResult);
                        System.out.println(strResult);
                    }
                    /*case "^" -> {
                        String str2 = stack.pop().toString();
                        String str1 = stack.pop().toString();
                        LList list1 = stringToList(str2);
                        LList list2 = stringToList(str1);
                        LList result = exponentiate(list1, list2);
                        String strResult = listToString(result);
                        stack.push(strResult);
                        System.out.println(strResult);
                    }*/
                    default -> stack.push(s); //when the current element in the string is a number
                }
            }
            System.out.println(line + " = " + stack.pop());
        }
    }

    public static LList add(LList list1, LList list2) {
        LList sum = new LList();

        //adds extra 0s to the shorter list to make the lists have an equal length to simplify addition
        if (list1.length() > list2.length()) {
            int diff = list1.length() - list2.length();
            for (int i = 0; i < diff; i++) {
                list2.append(0);
            }
        }
        else if (list1.length() < list2.length()) {
            int diff = list2.length() - list1.length();
            for (int i = 0; i < diff; i++) {
                list1.append(0);
            }
        }

        int carry = 0;
        for (int i = 0; i < list1.length(); i++) {
            int num1 = (int)list1.getValue();
            int num2 = (int)list2.getValue();
            list1.next();
            list2.next();
            int currSum = num1 + num2 + carry;
            carry = currSum / 10; //updates carry by giving 10's place digit and assigning it to the carry
            sum.append(currSum % 10); //adds the digit in the 1's place to the linked list sum
            if (list1.isAtEnd() && list2.isAtEnd()) {
                sum.append(carry); //adds the remaining carry to the linked list when finished adding both strings
            }
        }
        return sum;
    }

    public static LList multiply(LList list1, LList list2) {
        //get the 1st value by multiplying the top digits first like in handwritten multiplication
        //then get the 2nd value by multiplying the lower digits and put a 0 before everything else
        //use .add() to add these digits together and return the result
        LList sum1 = new LList();
        LList sum2 = new LList();
        LList totalSum = new LList();

        int currSum = 0;
        int carry = 0;
        //need to implement 2 sums that get added
        for (int i = 0; i < list1.length(); i++) {
            int num1 = (int)list1.getValue();
            int num2 = (int)list2.getValue();
            for (int j = 0; j < num2; j++) {
                currSum += num1;
            }
            currSum += carry;
            carry = currSum / 10;
            totalSum.append(currSum % 10);
            currSum = 0;
        }
        return totalSum;
    }

    public static LList exponentiate(LList list1, LList list2) {
        LList totalSum = new LList();
        return totalSum;
    }

    public static LList stringToList(String s) {
        LList list = new LList();
        for (int i = s.length() - 1; i > -1; i--) {
            int num = Character.getNumericValue(s.charAt(i));
            list.append(num);
        }
        return list;
    }

    public static String listToString(LList list) {
        String result = "";
        for (int i = 0; i < list.length(); i++) {
            int num = (int)list.getValue();
            list.next();
            result += num;
        }
        result = new StringBuilder(result).reverse().toString();
        return result;
    }
}
