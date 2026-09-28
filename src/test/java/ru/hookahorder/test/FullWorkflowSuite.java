package ru.hookahorder.test;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.TestClassOrder;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Сквозной сьют: От создания кальянной до заказа")
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@SelectClasses({
        CreateLoungeTest.class,
        CreateMenuTest.class,
        CreateOrderTest.class
})
public class FullWorkflowSuite {
}
