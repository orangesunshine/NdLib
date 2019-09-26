package com.orange.lib;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        List<String> test = new ArrayList<String>(Arrays.asList(new String[]{"1", "2", "3", "4", "5"}));
        for (String s : test) {
            if ("3".equals(s)) test.remove(s);
        }
        System.out.println("test = " + test);
    }
}