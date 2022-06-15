package ru.netology.graphics.image;

import java.util.Arrays;

public class ColorsShem implements TextColorSchema {

    private int number = 256;
    private char[] symble = new char[number];// от 0 до 255


    @Override

    public char convert(int color) {
        char[] value = {'#', '$', '@', '%', '*', '+', '-', '.'};  // 256 / 8 = 32
        int index = 0;
        for (int i = 0; i < 8; i++) { //8
            Arrays.fill(symble, index + 0, index + 32, value[i]);
            index += 32;
        }

        return symble[color];
    }

}
