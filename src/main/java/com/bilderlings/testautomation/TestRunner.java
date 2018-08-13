package com.bilderlings.testautomation;

import com.bilderlings.testautomation.test.Ordering;
import org.testng.TestNG;

import java.util.Arrays;
import java.util.List;

public class TestRunner
{
    public static void main(String[] args) {

        List<String> file = Arrays.asList(args);
        TestNG testNG = new TestNG();
        testNG.setTestSuites(file);
        testNG.run();


    }
}
