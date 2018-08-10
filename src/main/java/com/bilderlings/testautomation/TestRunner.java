package com.bilderlings.testautomation;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.TestNG;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.Double.parseDouble;
import static java.util.UUID.randomUUID;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


public class TestRunner
{
    public static void main(String[] args) {
        XmlSuite suite = new XmlSuite();
        suite.setName("Suite");

        XmlTest test = new XmlTest(suite);
        test.setName("Test");
        List<XmlClass> classes = new ArrayList<XmlClass>();
        classes.add(new XmlClass(""));
        test.setXmlClasses(classes) ;

        List<XmlSuite> suites = new ArrayList<XmlSuite>();
        suites.add(suite);
        TestNG tng = new TestNG();
        tng.setXmlSuites(suites);
        tng.run();

    }
}
