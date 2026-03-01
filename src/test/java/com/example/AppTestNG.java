package com.example;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TestNG version of App unit tests
 */
public class AppTestNG {
    
    @Test
    public void testGreet() {
        App app = new App();
        String result = app.greet("World");
        Assert.assertEquals(result, "Hello, World!");
    }

    @Test
    public void testGreetWithDifferentName() {
        App app = new App();
        String result = app.greet("TestNG");
        Assert.assertEquals(result, "Hello, TestNG!");
    }
}