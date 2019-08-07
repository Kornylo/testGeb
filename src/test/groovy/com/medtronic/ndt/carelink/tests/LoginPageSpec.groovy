package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.util.SwitchingUtil
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j

@Slf4j
@SmokeTest
class LoginPageSpec extends CareLinkSpec {
    static SignInPage signInPage
    static LoginPage loginPage
    static SwitchingUtil globalDataUtil
    def 'Browser Warning'() {
        given: 'Carelink is navigated to using the browser using an unsupported browser version'
        signInPage = browsers.to SignInPage

        and: 'The browser warning shall be displayed'
        println 'Verifying the signin button is displayed'
        assert signInPage.signInButtonDisplayed()
    }

    def "User able to see carelink banner image in login page"() {
        given: "User is at the carelink login page"
        loginPage = browsers.to LoginPage
//        when: 'User is hovering over an image'
        and: 'User is able to see banner image'
        assert loginPage.isBannerImageDisplayed()
    }

    def 'Login Error'() {
        when: 'Verification that error message is showing up'
        signInPage = browsers.to signInPage
        then: 'Unsuccessfully login to Carelink '
        signInPage.enterUsername("****")
        signInPage.enterPassword("1234")
        signInPage.clickOnSignIn()
        and: 'User is able to see error message'
        signInPage.assertErrorMsg(true)
        log.info( 'Verifying the error message is displayed')
        signInPage.assertPageErrorMsgContains('We do not recognize your username and/or password. Please try again. Enter your username and password to sign in.')
    }
    def 'Verify page'() {
        given: 'Carelink is navigated to using the browser using an unsupported browser version'
        loginPage = browsers.to LoginPage
        and: 'The browser warning shall be displayed'
        log.info( 'Verifying the signin button is displayed')
        loginPage.signInButtonDisplayed()
        log.info( 'Verifying the login form is displayed')
        loginPage.loginFormDisplayed()
        log.info( 'Verifying the footer submenu is displayed')
        loginPage.footerSubmenuDisplayed()
        log.info( 'Verifying the legal_right is displayed')
        loginPage.legalLabeDisplayed()
    }
    def 'Inactivity warning popup, logout page'() {
        when: 'A user supplies valid carelink credentials'
        loginPage = browsers.to LoginPage
        then: 'User should be able to successfully log into Carelink envision pro'
        loginPage.enterUsername("kornylodmytro")
        loginPage.enterPassword("Maluy013+")
        loginPage.clickOnSignIn()
        Thread.sleep(19 * 60 * 1000)
        then: 'Appear inactivity popup with button'
        loginPage.isSessionTimeoutPopupTextDisplayed()
        log.info( 'Timeout popup text displayed')
        loginPage.isSessionTimeoutButtonDisplayed()
        log.info( 'Timeout button displayed')
        loginPage.isSessionTimeouTitleDisplayed()
        log.info( 'Timeout title displayed')
        then: 'After two minutes automatic logout'
        Thread.sleep(4 * 60 * 1000)
        loginPage.isLogoutPageDisplayed()
        log.info( ' Logout page, eroor page - displayed')
        loginPage.isErrorMessageLogoutPageDisplayed()
        log.info( 'Error message on the  logout page displayed')
    }

    def 'Inactivity warning popup'() {
        when: 'A user supplies valid carelink credentials'
        loginPage = browsers.to LoginPage
        then: 'User should be able to successfully log into Carelink envision pro'
        loginPage.enterUsername("kornylodmytro")
        loginPage.enterPassword("Maluy013+")
        loginPage.clickOnSignIn()
        Thread.sleep(19 * 60 * 1000)
        then: 'Appear inactivity popup with button'
        loginPage.isSessionTimeoutPopupTextDisplayed()
        log.info( 'Timeout popup text displayed')
        loginPage.isSessionTimeoutButtonDisplayed()
        log.info( 'Timeout button displayed')
        loginPage.isSessionTimeouTitleDisplayed()
        log.info( 'Timeout title displayed')
    }
    def 'Inactivity logout page verification'() {
        when: 'When user inactive 20 min he appears on Timeout Page'
        loginPage = browsers.to LoginPage
        loginPage.goToTimedOutPage()
        then: 'Appear inactiv page'
        loginPage.isLogoutPageDisplayed()
        log.info( 'Error message on the  logout page displayed')
    }
    def 'Reminder usermane error'() {
        loginPage = browsers.to LoginPage
        loginPage.getUrlReminderUserName()
        when: 'Insert incorrect data'
        loginPage.lastNameTextbox("dmytrokornylo")
        loginPage.emailReminderTextbox("dmytro.kornylo@globallogic.com")
        loginPage.isReminderButtonDisplayed()
        loginPage.reminderButtonClick()
        then: 'Error reminder message'
        loginPage.isReminderUserErrorDisplayed()
        log.info( 'Verifying the error reminder message is displayed, reminder error')
        loginPage.reminderButtonCancelClick()
    }

     def 'Reminder password '() {
         loginPage = browsers.to LoginPage
         globalDataUtil= browsers.at SwitchingUtil
         loginPage.reminderPassURLButtonClick()
         when: 'Switch to new tab'
         Thread.sleep(1 * 60 * 1000)
         globalDataUtil.switchToNewTab()
         then: 'Is config browser displayed'
         loginPage.userNameTextboxClick()
         loginPage.userNameTextbox("Reminder")
         loginPage.emailReminderTextbox("dmytro.kornylo@globallogic.com")
         loginPage.isReminderButtonDisplayed()
         loginPage.reminderButtonClick()
         then: 'reminder message success'
         loginPage.isPasswordReminderSentDisplayed()
         loginPage.reminderPasswordCloseClick()
         log.info( 'Verifying the  reminder message is displayed, reminder error')
     }
     def 'Reminder usermane'() {
         loginPage = browsers.to LoginPage
         loginPage.getUrlReminderUserName()
         when: 'Insert correct data'
         loginPage.lastNameTextbox("Reminder")
         loginPage.emailReminderTextbox("dmytro.kornylo@globallogic.com.com")
         loginPage.isReminderButtonDisplayed()
         loginPage.reminderButtonClick()
         then: 'reminder messagesuccess'
         loginPage.isReminderUserSuccessDisplayed()
         log.info( 'Verifying the reminder message is displayed, reminder success')
     }

    /*
    def 'Google Chrome'() {
        driver = new ChromeDriver() ;
        loginPage = browsers.to LoginPage
        when: 'Open Configuration page Medtronic CareLink '
        loginPage.isButtonContinueDisplayed()
        then: 'Is config table displayed'
        loginPage.isCconfigTableDisplayed()
        then: 'Is config browser displayed'
        loginPage.isConfigTableBrowser()
        println 'Verifying the configuration page in google chrome opend'
        driver.quit()
    }
*/
    /* // Dmytro Kornylo Verifying reminder password error
     def 'Reminder password error' () {
         loginPage = browsers.to LoginPage
         loginPage.reminderPassURLButtonClick()
         when: 'Switch to new tab'
         loginPage.switchToNewTab()
         loginPage.userNameTextboxClick()
         loginPage.userNameTextbox("kornylo")
         loginPage.emailReminderTextbox("dmytro.kornylo@globallogic.com")
         loginPage.isReminderButtonDisplayed()
         loginPage.reminderButtonClick()
         then: 'password reminder, message error'
         loginPage.isReminderUserErrorDisplayed()
         println 'Verifying the error reminder message is displayed, reminder error'
     }*/
    /* // User Login
     def 'User Login' () {
         when: 'A user supplies valid carelink credentials'
         loginPage = browsers.to loginPage
         then: 'User should be able to successfully log into Carelink envision pro'
         loginPage.lastNameTextbox("Akilaa")
         loginPage.emailReminderTextbox("Test1234")
         loginPage.clickOnSignIn()
     }*/




}