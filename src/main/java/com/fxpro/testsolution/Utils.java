package com.fxpro.testsolution;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Utils {
    public static int getArrayMaxValue(int[] array) {
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < array.length; i++) {
            if(array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }
}
