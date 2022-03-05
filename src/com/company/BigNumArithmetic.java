package com.company; //comment this out when submitting

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
            String[] arr = line.split(" ");
            ArrayList<String> expression = new ArrayList<>();
            for (String value : arr) {
                if (!value.equals("")) {
                    expression.add(value);
                }
            }
            //Scanner in2 = new Scanner(line); //scanner for scanning in parts of an expression
            for (int i = 0; i < expression.size(); i++) { //this is for iterating through a single expression
                String s = expression.get(i);
                s = removeZeros(s);

                if (s.equals("+")) {
                    if (stack.length() < 2) {
                        for (int j = 0; j < expression.size(); j++) {
                            System.out.print(expression.get(j) + " ");
                        }
                        System.out.println("= ");
                        return;
                    }
                    else {
                        String str2 = stack.pop().toString();
                        String str1 = stack.pop().toString();
                        LList list1 = stringToList(str2);
                        LList list2 = stringToList(str1);
                        LList result = add(list1, list2);
                        String strResult = listToString(result);
                        strResult = removeZeros(strResult);
                        stack.push(strResult);
                    }
                }
                else if (s.equals("*")) {
                    if (stack.length() < 2) {
                        if (stack.length() < 2) {
                            for (int j = 0; j < expression.size(); j++) {
                                System.out.print(expression.get(j) + " ");
                            }
                            System.out.println("= ");
                            return;
                        }
                        return;
                    }
                    else {
                        String str2 = stack.pop().toString();
                        String str1 = stack.pop().toString();
                        LList list1 = stringToList(str2);
                        LList list2 = stringToList(str1);
                        LList result = multiply(list1, list2);
                        result.moveToStart();
                        String strResult = listToString(result);
                        strResult = removeZeros(strResult);
                        stack.push(strResult);
                    }
                }
                else if (s.equals("^")) {
                    if (stack.length() < 2) {
                        if (stack.length() < 2) {
                            for (int j = 0; j < expression.size(); j++) {
                                System.out.print(expression.get(j) + " ");
                            }
                            System.out.println("= ");
                            return;
                        }
                        return;
                    }
                    else {
                        /*String str2 = stack.pop().toString();
                        String str1 = stack.pop().toString();
                        LList list1 = stringToList(str2);
                        LList list2 = stringToList(str1);
                        LList result = exponentiate(list1, list2);
                        String strResult = listToString(result);
                        strResult = removeZeros(strResult);
                        stack.push(strResult);
                        System.out.println("exponentiate: " + strResult);*/
                        s = s.replace("^", "");
                    }
                }
                else {
                    stack.push(s); //when the current element in the string is a number
                }
            }
            for (int i = 0; i < expression.size(); i++) {
                System.out.print(expression.get(i) + " ");
            }
            System.out.println("= " + stack.pop());
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

    //might be able to copy one linked list into another by simple assignment, can test later
    public static LList multiply(LList list1, LList list2) {
        //adds extra 0s to the end of the shorter list for easier multiplication and addition
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

        LList sum1 = new LList();
        LList sum2 = new LList();
        LList totalSum = new LList();
        int currSum;
        int carry = 0;
        for (int i = 0; i < list1.length(); i++) {
            totalSum.clear();
            LList currSumList = new LList();
            int num1 = (int)list1.getValue();
            list1.next();
            for (int j = 0; j < i; j++) {
                currSumList.append(0); //adds extra 0s depending on how far into the multiplication the program is
            }
            //multiplies the "top" list by j's element in the "bottom" list
            for (int j = 0; j < list2.length(); j++) {
                int num2 = (int)list2.getValue();
                list2.next();
                currSum = num1 * num2 + carry;
                carry = currSum / 10;
                currSumList.append(currSum % 10);
            }
            //this if else chain is for copying the contents of one list into another based on the current sum
            if (i == 0) {
                for (int j = 0; j < currSumList.length(); j++) {
                    sum1.append(currSumList.getValue());
                    currSumList.next();
                }
            }
            else {
                for (int j = 0; j < currSumList.length(); j++) {
                    sum2.append(currSumList.getValue());
                    currSumList.next();
                }
            }
            totalSum = add(sum1, sum2);
            sum1.clear();
            //copies the result of the addition into sum1 for the next iteration
            for (int j = 0; j < totalSum.length(); j++) {
                sum1.append(totalSum.getValue());
                totalSum.next();
            }
            list2.moveToStart();
            sum2.clear();
        }
        return totalSum;
    }

    public static LList exponentiate(LList list1, LList list2) {
        LList result = new LList();
        int num = (int)list2.getValue();
        if (num == 0) { //base case
            result.append(1);
            return result;
        }
        else if (num % 2 == 0) {
            LList newList = new LList();
            newList.append(num / 2);
            return exponentiate(multiply(list1, list1), newList);
        }
        else {
            LList newList = new LList();
            newList.append((num - 1) / 2);
            return multiply(list1, exponentiate(multiply(list1, list1), newList)); //returns an error
        }
    }

    /**
     * This method turns a string into a linked list by iterating through the string in reverse order and adding each
     * number in the string to the linked list
     * @param s the string that is to be turned into a linked list
     * @return a linked list that contains each number from the string but in reverse order
     */
    public static LList stringToList(String s) {
        LList list = new LList();
        for (int i = s.length() - 1; i > -1; i--) {
            int num = Character.getNumericValue(s.charAt(i));
            list.append(num);
        }
        return list;
    }

    /**
     * This method turns a linked list into a string and reverses the resulting string since the provided list is in
     * reverse order of the actual result
     * @param list the list that is to be turned into a string
     * @return a string that contains a reversed order of the numbers in the linked list
     */
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

    /**
     * This method removes all leading zeros from a string, but in the case that the string consists of only zeros, it
     * will leave the final zero in the string to be operated on later in the program
     * @param s the string whose leading zeros will be removed
     * @return a string that has had its leading zeros removed
     */
    public static String removeZeros(String s) {
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(0) == '0') {
                s = s.replaceFirst("0", "");
            }
        }
        return s;
    }
}
