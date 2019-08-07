package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.dashboard.Mail
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.MailUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SСUI235 - Username Reminder Screen Content and Functionality per test plan.')
@RegressionTest
@Slf4j
@Stepwise
@Screenshot

class SCUI235Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LoginPage loginPage
    static Precondition precondition

    static final emailAddress = Calendar.getInstance().format('MMMddHHmmss').toString() + '@medtronictest.mailinator.com'
    static final adminUs = Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final password = 'Test1234@'

    def 'Login screen'() {
        when: 'Open software under test.'
        signInPage = browsers.to SignInPage
        then: ''
        signInPage.checkIncludedFooterElements()
    }

    def 'Create a new HCP '() {
        when: 'Create a new HCP and record the User ID __________ and Password __________'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, password)
        then: ''
        println("User ID: " + adminUs + ", Password: " + password)
    }

    def 'Navigate to the Login screen.'() {
        when: 'Navigate to the Login screen.'
        signInPage = browsers.to SignInPage
        then: 'Verify that “Forgot username?” link is exist in the Login page and located right below username entry field.'
        waitFor { signInPage.verifyForgotuserNameClick.displayed }
        signInPage.verifyForgotuserNameClick.text() == 'Forgot username?'
    }

    def 'Forgot Username'() {
        when: ''
        then: 'Verify that clicking on “Forgot username?” link shall navigate user to the Username Reminder Screen'
        then: 'Verify that the header (top section) of Username Reminder Screen contains the updated Medtronic logo at the top left.'
        then: 'Verify that the header (top section) of Username Reminder Screen contains the application identification label “CareLink” at the top right.'
        then: 'Verify that the header (top section) of Username Reminder Screen contains the header separation line'
        then: 'Verify that the Common Screen Footer Elements (Resourse Link, User Guide Link, Order Supplies Link, Terms of Use Link, Privacy Statement Link and Contact Us Link) are not displayed.'
        then: 'Verify that right below of the header separation line the Username Reminder Screen contains screen title “Forgot username” left at the top.'
        then: 'Verify that the Username Reminder screen shall display at the center the following text: '
        and: '"To receive a username reminder sent to your email account, please enter your last name and the email address associated with your CareLink iPro system account. After you complete this form it may take a few minutes to receive your reminder”'
        then: 'Verify that the label as “Last Name” shall be located at the same name entry field left.'
        then: 'Verify that the label as “E mail” shall be located at the same name entry field left.'
        then: 'Verify that the user entry field “Last Name” is located at the center of screen.'
        then: 'Verify that the user entry field “E mail” is located at the center of screen.'
        then: 'Verify that the Submit button shall be located below those two entry fields and labeled as “Submit”'
        when: 'Enter to the “ Last Name” and “E mail” user entry fields any valid or invalid user id and/or valid or invalid email address.'
        and: 'Click on Cancel button'
        then: 'Verify that this clicking on Cancel link shall close the Username Reminder Browser and the user should be taken back to the Login screen'
        signInPage.forgotUserNamePositiveFlow(" ", " ")

        when: 'Navigate back to the Username Reminder Screen.'
        and: 'Enter to the “Last Name” and “ E mail” user entry fields any invalid user id and/or invalid email address.'
        and: 'Click on Submit button.'
        then: 'Verify that the system presents the following message: '
        and: '“ The information you provided does not match our records. Please confirm spelling and be sure to use the email address associated with your account. If you continue to have problems, please call Contact_Us_Info_Phone for assistance.”'
        signInPage.forgotUserNameFlowNegative('wrongName', 'wrong@wrong.com')

        when: 'Enter to the “Last Name” and “email” user entry fields valid user id created on step 2 and valid email address .'
        then: 'Verify that when the Submit button is pressed the system shall submit the Request and as a result will display the Username Reminder Submitted Screen.'
        then: 'Verify that Username Reminder Submitted Screen should contain the next:'
        and: 'a) Medtronic logo at the top left of the Username Reminder Submitted Screen'
        and: 'b) application identification label “CareLink iPro” at the top right'
        and: 'c) The screen shall display the title “Username reminder sent”'
        and: 'd) Username Reminder Submitted Screen shall display the following text : “We have sent your username via email. It may take a few minutes to receive your reminder.”'
        and: 'e) The screen shall display Close Button, labeled as “Close”, shall close the Username'
        signInPage.forgotUserNamePositiveFlow("Stadnik", emailAddress)
    }

    def 'Reminder Browser'() {
        when: ''
        then: 'Verify that after user closed Username Reminder Submitted screen and waited a few minutes, he will receive reminder as a email message'
        and: 'Verify that the received reminder message is matched with expected'
        while (Mail.getInboxMessages(emailAddress).size() != 1) {
            sleep(5000)
        }
        MailUtil.checkEmailBoxForgotUsername(emailAddress, adminUs)
    }
}
