/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.utils;

import java.util.Arrays;
import java.util.Random;
import org.springframework.stereotype.Component;

/**
 *
 * @author Disha Varshney
 */
@Component
public final class MyEncoder {

    private static final char[] ENCODE_TABLE = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
        'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
        'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
        'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int ENCODE_MAX_VALUE = ENCODE_TABLE.length * ENCODE_TABLE.length;
    private static final int ENCODE_MAX_VALUE_LENGTH = ("" + ENCODE_MAX_VALUE).length();
    private static final int ENCODE_TABLE_LENGTH = ENCODE_TABLE.length;

    public String encode(long hashcode, boolean addRandom) {
        int nextInt = 0;
        if (addRandom) {
            Random random = new Random();
            nextInt = random.nextInt(1000);
        }
        String strHashcode = "" + Math.abs(hashcode + nextInt);
        return encode(strHashcode);
    }

    private String encode(String hashcode) {
        if (hashcode.length() == 0) {
            return "";
        }
        String lReturn = "";
        int currEncodeLength = ENCODE_MAX_VALUE_LENGTH;
        if (hashcode.length() > ENCODE_MAX_VALUE_LENGTH) {
            int parseInt = Integer.parseInt(hashcode.substring(0, ENCODE_MAX_VALUE_LENGTH));
            if (parseInt > ENCODE_MAX_VALUE) {
                currEncodeLength = ENCODE_MAX_VALUE_LENGTH - 1;
                parseInt = Integer.parseInt(hashcode.substring(0, ENCODE_MAX_VALUE_LENGTH - 1));
            }
            lReturn += ENCODE_TABLE[parseInt / ENCODE_TABLE_LENGTH];
            lReturn += ENCODE_TABLE[parseInt % ENCODE_TABLE_LENGTH];
        } else {
            int parseInt = Integer.parseInt(hashcode.substring(0));
            lReturn += ENCODE_TABLE[parseInt / ENCODE_TABLE_LENGTH];
            lReturn += ENCODE_TABLE[parseInt % ENCODE_TABLE_LENGTH];
            return lReturn;
        }
        return lReturn + encode(hashcode.substring(currEncodeLength));
    }

    public long decode(String hashcode) {
        String lReturn = "";
        char[] lHashcodeChars = hashcode.toCharArray();
        for (int i = 0; i < lHashcodeChars.length; i += 2) {
            char c1 = lHashcodeChars[i];
            char c2 = lHashcodeChars[i + 1];
            long value = (Arrays.binarySearch(ENCODE_TABLE, c1) * ENCODE_TABLE_LENGTH)
                    + Arrays.binarySearch(ENCODE_TABLE, c2);
            lReturn += value;
        }
        return Long.parseLong(lReturn);
    }

}
