package com.linCu.view.backingBeans;

import java.util.Random;

public class Test {
    public Test() {
        super();
    }

    public static void main(String[] args) {
        Test test = new Test();
        String characters = "";
        //        String pwd = RandomStringUtils.random( 3, characters );
        //        System.out.println( "-------------Random Password------------------"+pwd );
        Random randomGenerator = new Random();
        while (characters.length() < 3) {

            int random = randomGenerator.nextInt(6);
            if ((!characters.contains(String.valueOf(random))) && (random != 0)){
                characters = characters.concat(String.valueOf(random));
            }
        }
        System.out.println( "-------------Random Password------------------"+characters );
    }
}
