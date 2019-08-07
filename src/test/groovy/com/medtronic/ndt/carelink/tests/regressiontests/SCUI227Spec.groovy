package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicUsersPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEULAPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterAdminInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicEnterInfoPage
import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.clinicwizard.FinishClinicCreationPage
import com.medtronic.ndt.carelink.pages.clinicwizard.NewClinicRegistrationPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Slf4j
@Title('ScUI227 - Enrollment Administrator Information Screen')
@Stepwise
@Screenshot
class SCUI227Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static ClinicLocalePage clinicLocalePage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static LoginPage loginPage
    static ClinicSettingsPage clinicSettingsPage
    static ClinicUsersPage usersPage

    def 'Enrollment admin info validations' () {
        when: 'Open MMT-7340 (CareLink iPro URL) in a supported browser.'
        signInPage = browsers.to SignInPage
        then: 'Verify that when a user submits the URL it present the user with the Login screen.'
        log.info(driver.getCurrentUrl())
        when: 'Click on Register Clinic link.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Verify the system opens Enrollment Info Screen.'
        then: 'Click on Continue link.'
        newClinicRegistrationPage.clickOnContinue()
        when: 'At the locale page'
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Verify the system navigates to Enrollment Country and Language Selection Screen.'
        then: 'Verify the screen displays a process step indicator.'
        //clinicLocalePage.stepIndicatorIsDisplayed()
        clinicLocalePage.selectLocale()
        when: 'At the EULA page'
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Verify the both checkboxes on the screen.'
        then: 'Check both the checkboxes.'
        then: 'Click on Accept button.'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        when: 'At the Clinic Info page'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: 'Verify the system navigates to Enrollment Clinic Information Screen.'
        then: 'Verify the screen displays a process step indicator.'
        then: 'Enter all the details needed click on Continue button.'
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        when: 'At the Clinic admin info page'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: 'Verify the system navigates to Enrollment Administrator Information Screen.'
        then: 'Verify the screen displays a process step indicator.'
        then: 'Verify the Enrollment Administrator Information screen displays the common screen header elements.'
        then: 'Verify the Enrollment Administrator Information Screen does not display any common screen footer elements.'
        then: 'Verify the Enrollment Clinic Administrator Information Screen displays the text.'
        clinicEnterAdminInfoPage.clinicEnterAdminInfoText('Create the administrative user responsible for maintaining this clinic\'s CareLink system.')
        clinicEnterAdminInfoPage.clickContinue()
        clinicEnterAdminInfoPage.assertUserNameError('User Name is required')
        clinicEnterAdminInfoPage.assertFirstNameError('First Name is required')
        clinicEnterAdminInfoPage.assertLastNameError('Last Name is required')
        clinicEnterAdminInfoPage.assertEmailError('Email is required')
        clinicEnterAdminInfoPage.assertPasswordError('Password should be a minimum of 8 characters')
        clinicEnterAdminInfoPage.assertReenterPasswordError('Password should be a minimum of 8 characters')
        clinicEnterAdminInfoPage.assertSecurityAnswerError('Answer to the security question is required')
        screenshot('Enrollment Admin', 'Validationerror')
//        clinicEnterAdminInfoPage.enterUserNameManual('tes')
//        clinicEnterAdminInfoPage.clickContinue()
//        clinicEnterAdminInfoPage.assertUserNameError('Username should be a minimum of 4 characters')
        clinicEnterAdminInfoPage.usernameAdminValidation()
        clinicEnterAdminInfoPage.firstNameAdminValidation()
        clinicEnterAdminInfoPage.lastNameAdminValidation()
        clinicEnterAdminInfoPage.emailAdminValidation()
        clinicEnterAdminInfoPage.temporaryPasswordValidation()
        clinicEnterAdminInfoPage.securityAnswerAdminValidation()
        clinicEnterAdminInfoPage.verifySecurityQuestions()

        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("akila.agandeswaran@medtronic.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.userNameValueGet()
        clinicEnterAdminInfoPage.clickContinue()
        screenshot('Enrollment Admin', 'ClinicAdminFieldValidation')
        when: 'At the finish clinic registration page'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: 'Finish clinic registration with an administrative user'
        finishClinicCreationPage.clickFinish()
        then: 'Verify that screen displays the user entry fields.'
        then: 'Verify the username field is present.'
        then: 'Type in Username with less than 4 characters long and click continue button.'
        then: 'Verify the system triggers an error message.'
        then: 'Enter upper case and lower case letters in the username field upto 16 characters.'
        then: 'Verify Username field can take up to 16 case in-sensitive alphanumeric characters.'
        then: 'Enter 17 characters in the username.'
        then: 'Verify the username field does not allow less than 4 characters or greater than 16 characters.'
        then: 'Enter as username combination of spaces and underscore characters click on continue button.'
        then: 'Verify username field does not accept any spaces or underscore characters.'
        then: 'Verify the First Name field is present.'
        then: 'Enter in the First Name field 41 characters.'
        then: 'Verify First Name field only allows up to 40 characters.'
        then: 'Verify the Last Name field is present.'
        then: 'Enter in the Last Name field 41 characters.'
        then: 'Verify Last Name field only allows up to 40 characters.'
        then: 'Verify the Email field is present.'
        then: 'Enter 81 characters in Email field.'
        then: 'Verify email field only allows up to 80 characters.'
        then: 'Enter an email id without @ symbol and with spaces.'
        then: 'Verify the email address is not allowed to contain any spaces and includes @ symbol within its text.'
        then: 'Verify password field is present.'
        then: 'Enter a password with less than 8 characters long and click continue button.'
        then: 'Verify the system prevents entering less than 8 characters into password field.'
        then: 'Enter a password with 21 characters long.'
        then: 'Verify the password field does not allow 21 characters to be entered'
        then: 'Verify the security question field is present.'
        then: 'Click on the dropdown list of security question.'
        then: 'Verify the dropdown box should have the following security questions:'
        then: 'Verify the security answer field is present.'
        then: 'Verify the security answer field allows only up to 40 characters.'
        then: 'Update the user name, first name, last name, email and security answer with valid value and < symbol.'
        then: 'Click continue button.'
        then: 'Verify user name cannot be saved with < symbol.'
        then: 'Verify first name cannot be saved with < symbol.'
        then: 'Verify last name cannot be saved with < symbol.'
        then: 'Verify email cannot be saved with < symbol.'
        then: 'Verify security answer cannot be saved with < symbol.'
        then: 'Update the user name, first name, last name, email and security answer with valid value and > symbol.'
        then: 'Click continue button.'
        then: 'Verify user name cannot be saved with > symbol.'
        then: 'Verify first name cannot be saved with > symbol.'
        then: 'Verify last name cannot be saved with > symbol.'
        then: 'Verify email cannot be saved with > symbol.'
        then: 'Verify security answer cannot be saved with > symbol.'
        then: 'Update the user name, first name, last name, email and security answer with valid value and & symbol.'
        then: 'Click continue button.'
        then: 'Verify user name cannot be saved with & symbol.'
        then: 'Verify first name cannot be saved with & symbol.'
        then: 'Verify last name cannot be saved with & symbol.'
        then: 'Verify email cannot be saved with & symbol.'
        then: 'Verify security answer cannot be saved with & symbol.'
        then: 'Update the user name, first name, last name, email and security answer with valid value and = symbol.'
        then: 'Click continue button.'
        then: 'Verify user name cannot be saved with = symbol.'
        then: 'Verify first name cannot be saved with = symbol.'
        then: 'Verify last name cannot be saved with = symbol.'
        then: 'Verify email cannot be saved with = symbol.'
        then: 'Verify security answer cannot be saved with = symbol.'
        then: 'Update the user name, first name, last name, email and security answer with leading and trailing spaces.'
        then: 'Enter a password with combination of special characters and alphanumeric type.'
        then: 'Verify the password field masks each character entered.'
        then: 'Verify the re-enter password field is present.'
        then: 'Enter less than 8 characters and click continue button.'
        then: 'Verify the re-enter password does not allow less than 8 characters.'
        then: 'Re-enter a password with 21 case sensitive character'
        then: 'Verify the re-enter password field does not allow more than 20 characters'
        then: 'Verify the re-enter password field masks each character entered.'
        then: 'Verify error message is displayed when re-enter password does not match password.'
        then: 'Enter a password with valid combination of special characters and alphanumeric type.'
        then: 'Enter the same password in the re-enter password field.'
        then: 'Verify Continue button is present.'
        then: 'Click on continue button'
        then: 'Verify the system navigates to enrollment finish screen'
        when: 'At the login page'
        loginPage = browsers.at LoginPage
        then: 'Sign into the MMT-7340 application using the above credentials'
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        when: 'Navigate to clinic settings'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'Open clinic settings'
        clinicSettingsPage.clickOnClinicSettings()
        when:'Navigate to Users option'
        usersPage = browsers.at ClinicUsersPage
        then: 'Navigate to Clinic settings and click on users tab.'
        usersPage.clickOnUsers()
        then: 'Click on open user'
        then: 'Verify username, firstname, lastname, email and security answer field.'
        usersPage.clickOnCloseUsers()
        then: 'Click on sign out link.'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
        then: 'Click register clinic on the login screen'
        then: 'Proceed to enrollment administrator information screen'
        then: 'Verify cancel link labeling and click on cancel link.'
        then: 'Verify it navigates back to the login screen.'

    }
}
