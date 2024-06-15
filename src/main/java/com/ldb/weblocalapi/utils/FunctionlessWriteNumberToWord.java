package com.ldb.weblocalapi.utils;

public class FunctionlessWriteNumberToWord {
    public static String numberToWord(double num) {
        return new LaoKipText().getText(num); //don't need to create extra variables if this is what you need
    }

    public static String numberToWord(String num) {
        return new LaoKipText().getText(num);
    }

    public static void main(String[] args) {
        System.out.println("NUmber = " + numberToWord("9897056.24"));
        System.out.println("Num " +  new LaoKipText().getText(1233245));
    }
}
