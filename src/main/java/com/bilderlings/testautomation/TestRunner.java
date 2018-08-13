package com.bilderlings.testautomation;

import com.bilderlings.testautomation.test.Ordering;
import org.testng.TestNG;

public class TestRunner
{
    public static void main(String[] args) {

        TestNG testNG = new TestNG();
        testNG.setTestClasses(new Class[]{Ordering.class});
        testNG.run();

    }
}
