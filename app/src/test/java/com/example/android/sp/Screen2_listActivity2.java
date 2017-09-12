package com.example.android.sp;

import com.example.android.sp.FragmentsScreen3.FunctionsToCheck;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by valeria on 24/08/2017.
 */
public class Screen2_listActivity2 {


    @Test
    public void normalizeTest1() throws Exception{
        float[] input = {(float) 169.25, (float)172.45, (float) 170.44, (float) 165.61, (float)  165.28};
        float[] expected ={(float)0.5536963, (float)1.0, (float)0.71966594, (float)0.046025373, (float)0.0};
        float[] outputReceived = FunctionsToCheck.normalize(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }

    @Test
    public void normalizeTest2() throws Exception{
        float[] input = {(float) 56.25, (float)57.45, (float) 58.44, (float) 59.61, (float)  60.28};
        float[] expected ={(float)0.0, (float)0.29776704, (float)0.5434241, (float)0.8337473, (float)1.0};
        float[] outputReceived = FunctionsToCheck.normalize(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }

    @Test
    public void normalizeTest3() throws Exception{
        float[] input = {(float) 34.25, (float)37.45, (float) 35.44, (float) 32.61, (float)  34.28};
        float[] expected ={(float)0.33884284, (float)1.0, (float)0.5847103, (float)0.0, (float)0.34504095};
        float[] outputReceived = FunctionsToCheck.normalize(input);

        Assert.assertEquals(Arrays.toString(expected), Arrays.toString(outputReceived));
    }


}