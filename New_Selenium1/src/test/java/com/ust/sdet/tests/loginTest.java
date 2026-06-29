//package com.ust.sdet.tests;
//
//import com.ust.sdet.pages.LoginPage;
//import com.ust.sdet.support.DriverFactory;
//import org.junit.jupiter.api.*;
//import org.openqa.selenium.WebDriver;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class loginTest {
//
//  public static  WebDriver driver;
//
//  @BeforeEach
//    void setup(){
//      driver = DriverFactory.getDriver();
//  }
//
//  @AfterEach
//    void teardown(){
//      DriverFactory.quitDriver();
//  }
//
//  @Test
//  @DisplayName("Login Page")
//  void getLoggedIn(){
//      LoginPage login = new LoginPage(driver);
//      login.open();
//      login.login("customer@example.com","Password@123");
//  }
//
//}
