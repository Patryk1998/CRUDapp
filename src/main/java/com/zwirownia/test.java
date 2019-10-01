package com.zwirownia;


public class test {

    public void asd() throws Exception {
        throw new Exception("asd");
    }

    public void aaa() throws Exception{
        asd();
        System.out.print("asdssss");
    }

    public static void main(String[] args) {
        test test = new test();
        try {
            test.aaa();
            System.out.print("ll3l");
        } catch (Exception e) {

        }
        System.out.print("llllll");

    };

}
