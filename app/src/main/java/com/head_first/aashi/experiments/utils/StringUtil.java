package com.head_first.aashi.experiments.utils;

/**
 * Created by Aashish Indorewala on 03-Jan-17.
 */

public class StringUtil {

    public static String[] split(String string, Character regex){
        int countOfRegexOccurence = string.length() - string.replace(regex.toString(), "").length();
        String[] array = new String[countOfRegexOccurence + 1];
        int j = 0;
        array[0] = "";
        for(int i = 0; i < string.length() && j <= countOfRegexOccurence; i++){
            if(string.charAt(i) == regex){
                array[++j] = "";
            }
            else{
                array[j] += string.charAt(i);
            }
        }
        return array;
    }
}
