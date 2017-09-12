package com.example.android.sp.FragmentsScreen3;

/**
 * Created by valeria on 24/08/2017.
 */
/*
This class contains functions that are used in the activities. they are used in JUnit tests
 */
public class FunctionsToCheck {

    public static float[] findMinMax (float[] numbers){
        float[] minMax = new float[2];
        float max = Integer.MIN_VALUE;
        float min = Integer.MAX_VALUE;
        for(int i=0; i<numbers.length; i++)
        {
            if(numbers[i] < min)
                min = numbers[i];
            if(numbers[i] > max)
                max = numbers[i];
        }
        minMax[0]=min;
        minMax[1]=max;
        return minMax;
    }

    public static float[] normalize(float[] inputs){
        float[] normilised = new float[inputs.length];
        float[] minMax = findMinMax(inputs);
        for (int i =0; i<inputs.length; i++){
            normilised[i]=(inputs[i]-minMax[0])/(minMax[1]-minMax[0]);
        }
        return normilised;
    }

    public static float convertToReal(float num, float[] inputs){
        float[] minMax = findMinMax(inputs);
        float numReal = num*(minMax[1]-minMax[0])+minMax[0];
        return numReal;
    }

    private float[] convertToFloat(String [] input){
        float[] output = new float[input.length];
        for (int i=0; i<output.length; i++){
            output[i]=Float.parseFloat(input[i]);
        }
        return output;
    }
}
