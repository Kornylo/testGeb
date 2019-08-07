package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.Mail
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.MailUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('ScUI949 - 2FA_Selection Screen')
class SCUI949Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static HomePage homePage
    static Precondition precondition
    static ClinicSettingsPage clinicSettingsPage

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def '2FA_Selection Screen'() {
        when: 'Execute ScUI 949 as per test plan.'
        and: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink Software).' +
                'Record the server URL address.'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on Register Clinic link.' +
                'Create a new clinic.' +
                'Record the info: - Clinic Name.'
        and: 'Create a new administrative user.' +
                'Record the info:' +
                '- Username (example: TCXXXX);' +
                '- Password (example: Password1).'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, "Belgique", emailAddress, "Test1234@")
        println("Username: " + adminUs + " Password: Test1234@")
        and: 'Sign into the MMT-7340 application using the credentials above.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("Test1234@")
        signInPage.clickOnSignIn()
        and: 'Click on [Continue] button.'
        and: 'Enter valid Medtronic email address into the entry box and Click on [Send email now] button.'
        signInPage.getMFAVerifyCode(emailAddress)
        and: 'Enter valid code and click on [Verify Code] button.'
        MailUtil.waitForLetter(emailAddress, 1)
        MailUtil.checkEmailBoxMFAVerifyCode(emailAddress)
        signInPage.verifyCodeMFAFlow(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').trim())
        and: 'Click on Sign out link.'
        homePage = browsers.at HomePage
        sleep(2000)
        homePage.clickSignOutButton()
        and: 'Sign into the MMT-7340 application using the credentials from step 2764398.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("Test1234@")
        signInPage.clickOnSignIn()
        then: 'Verify that the MFA Email Setup Screen is displayed.'
        then: 'Verify that MFA Email Setup Screen only displays the Medtronic Logo and CareLink Logo from the Common Screen Header Elements.' +
                'Note: For Carelink Logo:' +
                'When Spark_Flag is False, the CareLink Therapy Management Software for Diabetes brand logo is displayed in the upper right of the screen. ' +
                'When Spark_Flag is True, the “EnvisionTM Pro” brand logo is displayed in the upper right of the screen.'
        then: 'Verify that the MFA Email Setup Screen includes the following Common Screen Footer Elements: User Guide, Training (if locale is US), Terms of Use, Privacy Statement, Contact Us.'
        then: 'Verify that the MFA Email Setup Screen displays the title “Two-Factor Authentication Required”.'
        then: 'Verify that the screen displays the text containing the information about the MFA Email method.'
        then: 'Verify that the MFA Email Setup Screen displays a Help Icon.'

        when: 'Click on the Help Icon.'
        then: 'Verify that the Help text for MFA feature in a new window is displayed.'

        when: 'Click on Close Window link.'
        signInPage.verifyMFAEmailSetupScreen()
        and: 'Click on Cancel link.'
        then: 'Verify that the MFA Cancel Confirmation Screen is displayed.'
        signInPage.clickCancelMFAScreen()
        when: 'Click on [Continue with Two-Factor Authentication] button and return to previous screen.'
        signInPage.clickContinueMFAScreen()

        then: 'Verify that when [Clinic_Staff_User_MFA_Email] contains an entry, the screen displays a label indicating the use of email address for MFA followed by email address partially obscured.'
        then: 'Verify that the [Send email now] button is displayed.'
        when: 'Click on [Send email now] button.'
        signInPage.sendEmailNowFlowMFAScreen()
        then: 'Verify that a [Send email now] button sends an email to the entered email address with a new code and navigates to the MFA Email Verification Screen.'
        when: 'Enter valid code and click on [Verify Code] button.'
        MailUtil.waitForLetter(emailAddress, 2)
        MailUtil.checkEmailBoxMFAVerifyCode(emailAddress)
        signInPage.verifyCodeMFAFlow(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').trim())
        and: 'Click on Clinic Settings tab.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        and: 'Click on Users tab.'
        clinicSettingsPage.assertUsersClick()
        and: 'Select User created in step ID 2764398.'
        and: 'Click [Open user] button.'
        and: 'Check "Reset Two-Factor Authentication" checkbox on User Information Screen.'
        and: 'Click [Save] button and navigate to Clinic Settings Screen.'
        clinicSettingsPage.resetTwoFactorAuthentication()
        and: 'Click on Sign out link.'
        sleep(1500)
        clinicSettingsPage.goToHomePageClick()
        homePage = browsers.at HomePage
        sleep(1000)
        homePage.clickSignOutButton()
        and: 'Sign into the MMT-7340 application using the credentials from step ID 2764398.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("Test1234@")
        signInPage.clickOnSignIn()
        and: 'Click on [Continue] button.'
        then: 'Verify that when [Clinic_Staff_User_MFA_Email] is empty, the screen displays a message indicating no email address has been set up.'
        when: 'Enter valid Medtronic email address into the entry box and Click on [Send email now] button.'
        signInPage.getMFAVerifyCode(emailAddress)
        and: 'Enter valid code and click on [Verify Code] button.'
        MailUtil.waitForLetter(emailAddress, 3)
        MailUtil.checkEmailBoxMFAVerifyCode(emailAddress)
        signInPage.verifyCodeMFAFlow(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').trim())
        and: 'Click on Sign out link.'
        homePage = browsers.at HomePage
        sleep(1000)
        homePage.clickSignOutButton()
        then: 'End of test.'
    }
}