package com.mentormate;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Objects;

public class Homework_Lesson_10_CalculatorExercise {
//Calculator exercise
    @DataProvider(name = "calculator")
    public Object [][] dpMethod (Method m) {
        switch (m.getName()) {
            case "sum":
                return new Object[][] {{4, 2, 6}, {5, 10, 15}};
            case "minus":
                return new Object[][] {{4, 2, 2}, {5, 10, -5}};
            case "multiplication":
                return new Object[][] {{4, 2, 8}, {5, 10, 50}};
            case "division":
                return new Object[][] {{4, 2, 2}, {50, 10, 5}};
            case "modul":
                return new Object[][] {{8, 4, 0}, {15, 5, 0}};
        }
        return null;
    }

    @Test(dataProvider = "calculator", groups = "group1")
    public void sum (int a, int b, int result) {
        int sum = a + b;
        Assert.assertEquals(result, sum);
    }

    @Test(dataProvider = "calculator", groups = "group2")
    public void minus (int a, int b, int result) {
        int minus = a - b;
        Assert.assertEquals(result, minus);
    }

    @Test(dataProvider = "calculator", groups = "group3")
    public void multiplication (int a, int b, int result) {
        int multiplication = a * b;
        Assert.assertEquals(result, multiplication);
    }

    @Test(dataProvider = "calculator", groups = "group4")
    public void division (int a, int b, int result) {
        int division = a / b;
        Assert.assertEquals(result, division);
    }

    @Test(dataProvider = "calculator", groups = "group5")
    public void modul (int a, int b, int result) {
        int modul = a % b;
        Assert.assertEquals(result, modul);
    }

}
