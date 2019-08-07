package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.Mail
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
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
@Title('ScUI952 - Password Expiration')
class SCUI952Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static Precondition precondition
    static ClinicSettingsPage clinicSettingsPage
    static HomePage homePage

    static final adminUs = "us" + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final adminThai = "tha" + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final adminBel = "bel" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress2 = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final emailAddress3 = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final password1 = "Test1234@"
    static final password2 = "Test12333@"
    static final password3 = "Password123"
    static final password4 = "Password!@2334"

    def 'Register new US clinic'() {
        when: 'Execute ScUI 952 as per test plan.'
        and: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink Software). Record the server URL address below.'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on Register Clinic link. Create new clinic (apply settings for clinic 1) and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below: ' +
                'Username: (example: Username2019)' +
                'Password: (example: Password1)' +
                'Note: Username must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, password1)
        println("Username: " + adminUs + " Password: " + password1)
        then: ''
    }

    def 'Sign in & set up Two-Factor Authentication'() {
        when: 'Sign into the MMT-7340 application using the credentials above.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(password1)
        signInPage.clickOnSignIn()
        then: 'Verify that the system navigates to the MFA Instruction Screen when a HCP user logs in using valid credentials.'

        when: 'Click on [Continue] button.'
        and: 'On MFA Configuration Screen enter valid Medtronic email address into the entry box, then click on [Send email now] button.'
        then: 'Verify that the system navigates to the MFA Email Verification Screen.'
        signInPage.getMFAVerifyCode(emailAddress)
        when: 'Open your email address and copy code to verify code field and click on [Verify Code] button.'
        and: 'Enter valid code and click on [Verify Code] button.'
        MailUtil.waitForLetter(emailAddress, 1)
        MailUtil.checkEmailBoxMFAVerifyCode(emailAddress)
        signInPage.verifyCodeMFAFlow(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').trim())
        then: 'Verify that If HCP login credentials are not expired, than system navigates to the Home Screen.'
    }

    def 'Turn On password expiration'() {
        when: 'Click on Clinic Settings tab.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        and: 'Click on Clinic Information link.'
        clinicSettingsPage.openClinicInformation()
        then: 'Verify that the 90-day Password Expiration labeled as “90-day Password Expiration” is displayed.'
        then: 'Verify that the 90-day Password Expiration value is Off and Two-Factor Authentication is Enabled.'

        when: 'Switch 90-day Password Expiration to On and click on [Save] button, then Save Confirmation Window appears and click on [OK] button.'
        then: 'Verify that the system navigates to the Clinic Information Screen.'
        clinicSettingsPage.passwordExpirationOn("90-day Password Expiration")

        when: 'Click on Sign out link.'
        sleep(1500)
        clinicSettingsPage.clickSignOut()
        then: ''
    }

    def 'Update password expiration on Database'() {
        when: 'Now open SQL Developer program to access a database.'
        and: 'In SQL Developer connect to QA Database and open “QA_Query_90DayPassword Expiration.sql” file located at “J:\\Engineering\\OLS_SW_Test\\Projects\\7340\\2.3A\\QA Queries “.'
        and: 'In the query, update username to the one in the step 2766145 (using lower-case).'
        and: 'Run the statement.'
        DataBaseRequestsUtil.passwordExpiration(adminUs)
        then: ''
    }

    def 'Verify Password Expired screen'() {
        when: 'Sign into the MMT-7340 application using the credentials from step 2766145.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(password1)
        signInPage.clickOnSignIn()
        then: 'Verify that the system navigates to the Password Expired screen, when a HCP user logs in using valid credentials.'
        then: 'Verify that the Password Expired screen display only the Medtronic Logo and CareLink Logo of the Common Screen Header Elements.'
        then: 'Verify that the Password Expired screen displays the following Common Screen Footer Elements: User Guide, Training (if locale is US), Terms of Use, Privacy Statement, Contact Us.'
        then: 'Verify that the Password Expired screen displays the Home link, labeled as “Home”, is to the left of the Terms of Use link, and when pressed, terminates the current user session and navigates to the Login Screen.'
        signInPage.verifyPasswordExpired("Your password has expired.", "A new password must be created now.", "Please enter your new password.")

        when: 'Click on Home link.'
        signInPage.homeClick()
        and: 'Sign into the MMT-7340 application using the credentials from step 2766145.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("Test1234@")
        signInPage.clickOnSignIn()
        then: 'Verify that the Password Expired Screen is displayed and titled “Password Expired”.'
        then: 'Verify If the user’s password has expired, the screen displays the following text: “Your password has expired. A new password must be created now. Please enter your new password.”'
        then: 'Verify that the user input fields for the following elements are provided on the Password Expired Screen. [Clinic_Staff_User_Password] labeled as “New Password”. [Clinic_Staff_User_Password_Confirm] labeled as “Retype New Password”.'
        then: 'Verify that the [Save] button is displayed.'
        signInPage.verifyPasswordExpired("Your password has expired.", "A new password must be created now.", "Please enter your new password.")

        when: 'Enter less than 8 characters into the “New Password” and “Retype New Password” fields and click on [Save] button.'
        then: 'Verify that the system displays the following Message: “Password should be a minimum of 8 characters”.'
        signInPage.verifyPasswordExpiredFields('qwe$5', 'qwe$5', "Password should be a minimum of 8 characters", "Password should be a minimum of 8 characters")

        when: 'Enter more than 8 characters without the following categories: Number, Uppercase character, Special character into the “New Password” and “Retype New Password” fields and click on [Save] button. (e.g. passwordx)'
        then: 'Verify that the system displays the following Message: “Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.”'
        signInPage.verifyPasswordExpiredFields('passwordx', 'passwordx', "Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.", "Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")

        when: 'Enter password from step 2766145 into the “New Password” and “Retype New Password” fields and click on [Save] button.'
        then: 'Verify that the system displays an error message indicating the passwords must be different.'
        signInPage.verifyPasswordExpiredFields(password1, password1, "", "Your new password must be different from your previous password.")

        when: 'Enter new password that is the exactly the same as Username (from step 2766145) into the “New Password” and “Retype New Password” fields. Click on [Save] button.'
        then: 'Verify that if the username exactly matches with entered password value, the system displays a message that username cannot be a subset of the password value.'
        signInPage.verifyPasswordExpiredFields(adminUs, adminUs, "Password must not include the username.", "Password must not include the username.")

        when: 'Enter new password that includes the Username (from step 2766145) into the “New Password” and “Retype New Password” fields.' +
                'Example:' +
                'Username: Username2019' +
                'Password: PassUsername2019' +
                'Click on [Save] button.'
        then: 'Verify that if the username is a subset of entered password value, the system displays a message that username cannot be a subset of the password value.'
        signInPage.verifyPasswordExpiredFields(adminUs, "Pass" + adminUs, "Password must not include the username.", "Password must not include the username.")

        when: 'Enter new password that is the same as Username except upper and lower cases (from step 2766145) into the “New Password” and “Retype New Password” fields.' +
                'Example:' +
                'Username: Username2019' +
                'Password: USERname2019' +
                'Click on [Save] button.'
        then: 'Verify that [Clinic_Staff_User_LoginID] value cannot be a subset of the [Clinic_Staff_User_Password] value (case-insensitive comparison) for new users OR existing users during password update.'
        signInPage.verifyPasswordExpiredFields(adminUs, adminUs.toUpperCase(), "Password must not include the username.", "Password must not include the username.")
    }

    def 'Create new password & sign in'() {
        when: 'Enter new password that partially matches with Username (from step 2759911) into the' +
                '“New Password” and “Retype New Password” fields.' +
                'Example:' +
                'Username: Username2019' +
                'Password: Username201' +
                'Click on [Save] button and record the password.'
        then: 'Verify that if the username is not a complete subset of password value, the system saves the [Clinic_Staff_User_Password] and navigates to the Home Screen.'
        signInPage.addNewPasswordExpiredFlow(password2)

        when: 'Click on Sign out link.'
        homePage = browsers.at HomePage
        sleep(2000)
        homePage.clickSignOutButton()
        and: 'Sign into the MMT-7340 application using the username from step 2766145 and password from step 2766257.'
        signInPage = browsers.at SignInPage
        precondition.signInAsClinicAdmin(adminUs, password2)
        then: 'Verify that the system navigates to the Home screen, when a HCP user logs in using valid non-expired credentials.'
        when: 'Click on Sign out link.'
        homePage = browsers.at HomePage
        homePage.refreshPage()
        homePage.clickSignOutButton()
        then: ''
    }

    def 'Register new Thailand Clinic & turn On password expiration'() {
        when: 'Click on Register Clinic link. Create new clinic (apply settings for clinic 2) and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:' +
                'Username: (example: TC932)' +
                'Password: (example: Password1)'
        precondition.registerClinic(adminThai, "ประเทศไทย", emailAddress2, password3)
        and: 'Sign into the MMT-7340 application using the credentials above.'
        precondition.signInAsClinicAdmin(adminThai, password3)
        then: 'Verify that If a HCP user logs in using valid non-expired credentials and MFA is not required, system navigates to the Home Screen.'

        when: 'Click on Clinic Settings tab.'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        and: 'Click on Clinic Information link.'
        clinicSettingsPage.openClinicInformation()
        and: 'Switch 90-day Password Expiration to On and click on [Save] button, then Save Confirmation Window appears and click on [OK] button.'
        clinicSettingsPage.passwordExpirationOn("90-day Password Expiration")
        and: 'Click on Sign out link.'
        sleep(2000)
        clinicSettingsPage.clickSignOut()
        then: ''
    }

    def 'Update password expiration on Database '() {
        when: 'Now open SQL Developer program to access a database.'
        and: 'In SQL Developer connect to QA Database and open “QA_Query_90DayPassword Expiration.sql” file located at “J:\\Engineering\\OLS_SW_Test\\Projects\\7340\\2.3A\\QA Queries “.'
        and: 'In the query, update username to the one in the step 2766275 (using lower-case).'
        and: 'Run the statement.'
        DataBaseRequestsUtil.passwordExpiration(adminThai)
        then: ''
    }

    def 'Verify Password Expired screen '() {
        when: 'Sign into the MMT-7340 application using the credentials from step 2766275.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminThai)
        signInPage.enterPassword(password3)
        signInPage.clickOnSignIn()
        then: 'Verify that the system navigates to the Password Expired screen, when a HCP user logs in using valid credentials and the password has expired.'
        signInPage.verifyPasswordExpired("Your password has expired.", "A new password must be created now.", "Please enter your new password.")

        when: 'Click on Home link.'
        then: 'Verify that a Home link, labeled as “Home”, is to the left of the Terms of Use button, and when pressed, terminates the current user session and navigates to the Login Screen.'
        signInPage.homeClick()
    }

    def 'Register new Belgique Clinic & set up Two-Factor Authentication'() {
        when: 'Click on Register Clinic link. Create new clinic (apply settings for clinic 3) and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:' +
                'Username: (example: TC932)' +
                'Password: (example: Password1)'
        precondition.registerClinic(adminBel, "Belgique", emailAddress3, password4)
        and: 'Sign into the MMT-7340 application using the credentials above.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminBel)
        signInPage.enterPassword(password4)
        signInPage.clickOnSignIn()
        and: 'Click on [Continue] button.'
        signInPage.getMFAVerifyCode(emailAddress3)
        and: 'On MFA Configuration Screen enter valid Medtronic email address into the entry box, then click on [Send email now] button.'
        and: 'Open your email address and copy code to verify code field and click on [Verify Code] button on MFA Email Verification Screen.'
        MailUtil.waitForLetter(emailAddress3, 1)
        MailUtil.checkEmailBoxMFAVerifyCode(emailAddress3)
        signInPage.verifyCodeMFAFlow(Mail.getAllMessagesWithoutWait(emailAddress3).last.body.split('\n').getAt(3).replace('\r', '').trim())
        and: 'Click on Sign out link.'
        homePage = browsers.at HomePage
        sleep(1500)
        homePage.clickSignOutButton()
        and: 'Sign into the MMT-7340 application using the credentials from step 2766313.'
        signInPage = browsers.at SignInPage
        signInPage.enterUsername(adminBel)
        signInPage.enterPassword(password4)
        signInPage.clickOnSignIn()
        then: 'Verify that if a HCP user logs in using valid credentials and MFA is required and HCP user has MFA credentials saved, then the system navigates to the MFA Email Setup Screen.'
        signInPage.verifyMFAEmailSetupScreen()
        then: 'End of test.'
    }
}