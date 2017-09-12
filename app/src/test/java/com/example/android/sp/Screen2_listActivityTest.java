package com.example.android.sp;

import com.example.android.sp.FragmentsScreen3.FunctionsToCheck;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by valeria on 24/08/2017.
 */
public class Screen2_listActivityTest {


    @Test
    public void findMinMaxTest1() throws Exception{
        float[] input = {(float)1.2, (float)1.1, (float)0.2, (float)2.2, (float) 1.8};
        float[] expected = {(float)0.2, (float)2.2};
        float[] outputReceived = FunctionsToCheck.findMinMax(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }


    @Test
    public void findMinMaxTest2() throws Exception{
        float[] input = {(float)1.2, (float)1.2, (float)0.7, (float)0.7,(float)1.2 };
        float[] expected = {(float)0.7, (float)1.2};
        float[] outputReceived = FunctionsToCheck.findMinMax(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }


    @Test
    public void findMinMaxTest3() throws Exception{
        float[] input = {(float)1.2, (float)1.1, (float)0.2, (float)2.2, (float)4.2};
        float[] expected = {(float)0.2, (float)4.2};
        float[] outputReceived = FunctionsToCheck.findMinMax(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }

    @Test
    public void findMinMaxTest4() throws Exception{
        float[] input = {(float)1.2, (float)1.2,(float)1.2,(float)1.2,(float)1.2, };
        float[] expected = {(float)1.2, (float)1.2};
        float[] outputReceived = FunctionsToCheck.findMinMax(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }




}