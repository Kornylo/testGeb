package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.dashboard.ForgotUserNamePage
import com.medtronic.ndt.carelink.pages.dashboard.Mail
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.MailUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('SCUI197 - Username Reminder Request')
class SCUI197Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static Precondition precondition
    static ForgotUserNamePage forgotUserNamePage


    static final emailAddress = Calendar.getInstance().format('MMMddHHmmss').toString() + '@medtronictest.mailinator.com'
    static final adminUs = Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final newPassword = 'Test1234@'
    def pass = "1Test1234@"
    static final lastName = 'Stadnik'

    def 'Launch the MMT – 7340 application under test'() {
        when: 'Launch the MMT – 7340 application under test.'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        then: ''
        signInPage.checkIncludedFooterElements()
    }

    def 'Register a clinic with the username and password.'() {
        when: 'Register a clinic with the username and password.'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, pass)
        and: 'Note down the Username'
        and: 'Note down the Password'
        and: 'Note down the First name and Last Name'
        and: 'Note down the Email'
        signInPage = browsers.to SignInPage
        signInPage.signInButtonDisplayed()
        then: ''
        println("User ID: " + adminUs + ", Password: " + pass + ", FirstName: Dmytro " + ", LastName: " + lastName + ", Email: " + emailAddress)
    }

    def 'Login with the same username and password'() {
        when: 'Login with the same username and password'
        precondition = browsers.to Precondition
        precondition.signInAsClinicAdmin(adminUs, pass)
        then: 'Verify that system navigates to the Home screen'
        signInPage.signoutButtonDisplayed()
        when: 'Press sign out link'
        signInPage.clickOnSignOutLink()
        then: 'Verify that system navigates to the login screen'
        signInPage.signInButtonDisplayed()
        then: 'Verify that login page has forgot username link as “Forgot username?'
        assert signInPage.verifyForgotuserNameClick.displayed
    }

    def 'Forgot User Name'() {
        when: 'Press forgot username link.'
        then: 'Verify that system open username reminder screen in new browser window or tab .'
        when: 'Enter the Last name and Email as noted in the above steps to the appropriate data field.'
        and: 'Press Submit Button.'
        then: 'Verify that system navigates to the Username Reminder Submitted Screen.'
        when: 'Press close button.'
        then: 'Verify that system close that browser.'
        signInPage.forgotUserNamePositiveFlow(lastName, emailAddress)
    }

    def 'Verify Email'() {
        when: ''
        then: 'Verify that system send a user reminder email to the email address for that user.'
        when: 'Note the temporary Password1 '
        then: 'Verify that User reminder Email include the following information and text:'
        then: 'Sent From: “Medtronic CareLink System Support [donotreply@medtronic.com]”'
        then: 'Subject: “CareLink iPro Username Reminder”'
        then: 'Body:'
        then: '“Your username is [Clinic_Staff_User_LoginID]'
        then: '“Your temporary CareLink iPro password is: [Clinic_Staff_User_Temporary_Password]'
        then: 'For your security, a temporary password has been assigned to the login above. Please access the CareLink system at www.ipro2.com and sign in using the username and temporary password, exactly as shown above. The system will then ask you to create a new password that you will use on subsequent visits to the system.'
        then: 'The temporary password will expire 24 hours after it was issued, so please use it to sign in to the system at your earliest opportunity. If it expires before you have a chance to use it, you can request a new temporary password from the CareLink home page.'
        then: 'This is an automated message; please do not reply. If you need further assistance, please call customer support at [Contact_Us_Info_Phone].'
        then: 'Thank You,'
        then: 'Medtronic Diabetes”'
        MailUtil.waitForLetter(emailAddress, 1)
        MailUtil.checkEmailBoxForgotUsername(emailAddress, adminUs)
    }

    def 'Try to login with Old Password and see error'() {
        when: 'Navigate to the login screen.'
        and: 'Try to log in with the username and password noted in the above steps ID 2789535 and 2789549.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(pass)
        signInPage.clickOnSignIn()
        then: 'Verify that system navigates to the Login error screen.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Login with Temporary Password1 and setup new one'() {
        when: 'Try to login with the username and temporary password1 from user reminder email.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
        then: 'Verify that system navigates to the Password Reset Required Screen.'
        signInPage.newPasswordScreen('Change Password Page', 'New password required')
        when: 'Enter the new password on the Password Reset Required Screen.'
        and: 'Note the New Password'
        and: 'Press the Save button.'
        signInPage.enterPasswordConfirm(newPassword)
        println('The New Password: ' + newPassword)
        then: 'Verify that system navigates to the Home screen.'
        when: 'Press sign out button on home screen.'
        signInPage.signoutButtonDisplayed()
        then: ''
        signInPage.clickOnSignOutLink()
    }

    def 'Try to login with Temporary Password1 and see error'() {
        when: 'Try to login with the username and temporary password1 from user reminder email.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        then: 'Verify that system navigates to the Login error screen.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Login with New Password'() {
        when: 'Try to log in with the username and New password noted in step ID 2789646.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(newPassword)
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
        then: 'Verify that system accept the password and navigates to the home screen.'
        signInPage.signoutButtonDisplayed()
        when: 'Press sign out button.'
        signInPage.clickOnSignOutLink()
        then: 'Verify that system logs out the patient and navigates to the login screen.'
        signInPage.signInButtonDisplayed()
    }

    def 'Forgot User Name 2'() {
        when: 'Press forgot username link.'
        then: 'Verify that system open username reminder screen in new browser window or tab .'
        when: 'Enter the Invalid Last name and valid Email as noted in the above steps to the appropriate data field.'
        and: 'Press Submit Button.'
        then: 'Verify that username reminder screen give the error message.'
        signInPage.forgotUserNameFlowNegative("wrongLastName", emailAddress)
        then: 'Verify that No username reminder Email send to the valid email address.'
        assert Mail.getAllMessagesWithoutWait(emailAddress).size() == 1 //only 1 letter in inbox

        when: 'Enter the valid Last name and invalid Email as noted in the above steps to the appropriate data field.'
        and: 'Press Submit Button.'
        then: 'Verify that username reminder screen give the error message.'
        signInPage.forgotUserNameFlowNegative(lastName, "wrong@wrong.com")
        then: 'Verify that No username reminder Email send to the email address.'
        assert Mail.getAllMessagesWithoutWait(emailAddress).size() == 1 //only 1 letter in inbox

        when: 'Enter the valid Last name and valid Email as noted in the above steps to the appropriate data field.'
        and: 'Press Submit Button.'
        then: 'Verify that system navigates to the Username Reminder Submitted Screen.'
        when: 'Press close button.'
        then: 'Verify that system close that browser.'
        signInPage.forgotUserNamePositiveFlow(lastName, emailAddress)
        then: 'Verify that system send a user reminder email to the email address for that user.'
    }

    def 'Verify email 2'() {
        when: 'Note the Temporary Password2'
        then: 'Verify that User reminder Email include the following information and text:'
        then: 'Sent From: “Medtronic CareLink System Support [donotreply@medtronic.com]”'
        then: 'Subject: “CareLink iPro Username Reminder”'
        then: 'Body:'
        then: '“Your username is [Clinic_Staff_User_LoginID]'
        then: '“Your temporary CareLink iPro password is: [Clinic_Staff_User_Temporary_Password]'
        then: 'For your security, a temporary password has been assigned to the login above. Please access the CareLink system at www.ipro2.com and sign in using the username and temporary password, exactly as shown above. The system will then ask you to create a new password that you will use on subsequent visits to the system.'
        then: 'The temporary password will expire 24 hours after it was issued, so please use it to sign in to the system at your earliest opportunity. If it expires before you have a chance to use it, you can request a new temporary password from the CareLink home page.'
        then: 'This is an automated message; please do not reply. If you need further assistance, please call customer support at [Contact_Us_Info_Phone].'
        then: 'Thank You,'
        then: 'Medtronic Diabetes”'
        MailUtil.waitForLetter(emailAddress,2)
        MailUtil.checkEmailBoxForgotUsername(emailAddress, adminUs)
    }

    def 'Try to login with Temporary Password1 and see error '() {
        when: 'Navigate to the login screen.'
        and: 'Try to log in with the username and temporary password1 from the first Username reminder email.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).first.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        then: 'Verify that system navigates to the Login error screen.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Wait more than 24 hours'() {
        when: 'Wait for more than 24 hours.'
        DataBaseRequestsUtil.changeExpiredPasswordTime(adminUs)
        then: ''
    }

    def 'Verify that both temporary Passwords are expired'() {
        when: 'Try to log in with the username and temporary password1 from the first Username reminder email.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).first.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        then: 'Verify that system navigates to the Login error screen.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")

        when: 'Try to log in with the username and temporary password2 from the second Username reminder email.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        then: 'Verify that system navigates to the Login error screen.'
        signInPage.checkLoginError("Your temporary password has expired.", "Please reset your password again and login within 24 hours.", "Click the \"Forgot password?\" link below to reset your password.")
    }

    def 'Forgot User Name 3'() {
        when: 'Press forgot username link.'
        then: 'Verify that system open username reminder screen in new browser window or tab .'
        when: 'Enter the Valid Last name and valid Email as noted in the above steps to the appropriate data field.'
        then: 'Press Submit Button.'
        then: 'Verify that system navigates to the Username Reminder Submitted Screen.'
        when: 'Press close button.'
        forgotUserNamePage = browsers.to ForgotUserNamePage
        then: 'Verify that system close that browser.'
        forgotUserNamePage.enterDataAndSendExpired(lastName, emailAddress)
        then: 'Verify that system send a user reminder email to the email address for that user.'
    }

    def 'Verify email 3'() {
        when: 'Note the temporary password3'
        then: 'Verify that User reminder Email include the following information and text:'
        then: 'Sent From: “Medtronic CareLink System Support [donotreply@medtronic.com]”'
        then: 'Subject: “CareLink iPro Username Reminder”'
        then: 'Body:'
        then: '“Your username is [Clinic_Staff_User_LoginID]'
        then: '“Your temporary CareLink iPro password is: [Clinic_Staff_User_Temporary_Password]'
        then: 'For your security, a temporary password has been assigned to the login above. Please access the CareLink system at www.ipro2.com and sign in using the username and temporary password, exactly as shown above. The system will then ask you to create a new password that you will use on subsequent visits to the system.'
        then: 'The temporary password will expire 24 hours after it was issued, so please use it to sign in to the system at your earliest opportunity. If it expires before you have a chance to use it, you can request a new temporary password from the CareLink home page.'
        then: 'This is an automated message; please do not reply. If you need further assistance, please call customer support at [Contact_Us_Info_Phone].'
        then: 'Thank You,'
        then: 'Medtronic Diabetes”'
        MailUtil.waitForLetter(emailAddress, 3)
        MailUtil.checkEmailBoxForgotUsername(emailAddress, adminUs)
    }

    def 'Try to login with Temporary Password 3 ans setup new one'() {
        when: 'Navigate to the login screen.'
        and: 'Try to log in with the username and temporary password3 from the third (latest) Username reminder email.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
        then: 'Verify that system navigates to the Password Reset Required Screen.'
        signInPage.newPasswordScreen('Change Password Page', 'New password required')
        when: 'Enter the new password on the Password Reset Required Screen.'
        and: 'Press the Save button.'
        signInPage.enterPasswordConfirm(newPassword)
        then: 'Verify that system navigates to the Home screen.'
        signInPage.signoutButtonDisplayed()
        then: 'End of test.'
    }
}
