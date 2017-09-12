package com.example.android.sp;

import com.example.android.sp.FragmentsScreen3.FunctionsToCheck;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by valeria on 24/08/2017.
 */
public class Screen2_listActivity3 {

    @Test
    public void convertToRealTest1() throws Exception{
        float number = (float)0.753678;
        float[] input = {(float) 169.25, (float)172.45, (float) 170.44, (float) 165.61, (float)  165.28};
        float expected =(float) 170.683;
        float outputReceived = FunctionsToCheck.convertToReal(number, input);
        Assert.assertEquals(expected, outputReceived, 0.03);
    }

    @Test
    public void convertToRealTest2() throws Exception{
        float number = (float)0.45683;
        float[] input = {(float) 56.25, (float)57.45, (float) 58.44, (float) 59.61, (float)  60.28};
        float expected =(float) 58.091;
        float outputReceived = FunctionsToCheck.convertToReal(number, input);
        Assert.assertEquals(expected, outputReceived, 0.03);
    }

    @Test
    public void convertToRealTest3() throws Exception{
        float number = (float)0.012345;
         float[] input = {(float) 34.25, (float)37.45, (float) 35.44, (float) 32.61, (float)  34.28};
        float expected =(float) 32.6697;
        float outputReceived = FunctionsToCheck.convertToReal(number, input);
        Assert.assertEquals(expected, outputReceived, 0.03);
    }

}