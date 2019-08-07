package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HCP.MyInfoPage
import com.medtronic.ndt.carelink.pages.LoginPage
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
import org.junit.Assert
import spock.lang.Stepwise
import spock.lang.Title

@Title('SaUI001 - My Info validations')
@RegressionTest
@Stepwise
@Slf4j
@Screenshot
class SAUI001Spec extends CareLinkSpec implements ScreenshotTrait{
    static SignInPage signInPage
    static MyInfoPage myInfoPage
    static LoginPage loginPage
    static ClinicLocalePage clinicLocalePage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage

    def 'My info validations' () {
        when: 'Open the MMT-7340 application (CareLink System)'
        signInPage = browsers.to SignInPage
        then: 'Record the server URL address'
        log.info(driver.getCurrentUrl())
        when: 'Create a new administrative user and record the info: ' +
                '- Username (obligatory to include digit: e.g. username1);' +
                '- Password (example: Password1);' +
                '- First Name;' +
                '- Last Name;' +
                '- Email;' +
                '- Security Question;' +
                '- Security Answer.'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        newClinicRegistrationPage.clickOnContinue()
        clinicLocalePage = browsers.at ClinicLocalePage
        clinicLocalePage.selectLocale()
        clinicEULAPage = browsers.at ClinicEULAPage
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.clickContinue()
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        clinicEnterAdminInfoPage.enterUserName()
        clinicEnterAdminInfoPage.enterFirstName("Akila")
        clinicEnterAdminInfoPage.enterLastName("Agandeswaran")
        clinicEnterAdminInfoPage.enterEmail("test@medtronictest.mailiniator.com")
        clinicEnterAdminInfoPage.enterPassword("Test1234@")
        clinicEnterAdminInfoPage.enterConfirmPassword("Test1234@")
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        String adminUser = clinicEnterAdminInfoPage.userNameValueGet()
        clinicEnterAdminInfoPage.clickContinue()
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        finishClinicCreationPage.clickFinish()
        then: ''

        when: 'Sign into the MMT-7340 application using the credentials above.' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link'
        loginPage = browsers.at LoginPage
        loginPage.enterUsername(adminUser)
        loginPage.enterPassword('Test1234@')
        loginPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Navigate to the Home Page common screen header elements'
        then: 'Observe the current signed in user with the text Hi,  is not displayed.'
        signInPage.isWelcomeTextDisplayed()
        then: 'Navigate to new patient record screen'
        then: 'Observe the current signed in user with the text Hi,  is not displayed.'
        then: 'At the new patient enter in a valid data for the user input fields. Click on Save button and observe the patient record screen'
        then: 'Observe the current signed in user with the text Hi,  is not displayed.'
        when: 'Navigate to the My Info screen by clicking the My Info link.'
        myInfoPage = browsers.at MyInfoPage
        def firstName = "Firstnamecannotbemorethanfortycharacters"
        def lastName = "Lastnamecannotbemorethanfortycharacters "
        def email = "emailcannotbemorethaneightycharactersemailcannotbemorethaneightych@medtronic.com"
        myInfoPage.clickOnMyInfo()
        log.info('My info page is displayed')
        sleep(10000)
        myInfoPage.verifyFirstNameLabel()
        log.info('Verified First name label')
        myInfoPage.verifyLastNameLabel()
        log.info('Verified Last name label')
        myInfoPage.verifyEmailLabel()
        log.info('Verified the email label')
        myInfoPage.verifySecurityQuestionLabel()
        log.info('Verified the security question label')
        myInfoPage.verifySecurityAnswerLabel()
        log.info('Verified the security answer label')
        myInfoPage.enterFirstName(' ')
        myInfoPage.enterLastName(' ')
        myInfoPage.enterEmail(' ')
        myInfoPage.enterSecurityAnswer(' ')
        myInfoPage.clickChangePassword()
        myInfoPage.clickSaveMyInfo()
        myInfoPage.assertFirstNameError('First name is required.')
        log.info('Verified first name cannot be left blank')
        myInfoPage.assertLastNameError('Last name is required.')
        log.info('Verified last name cannot be left blank')
        myInfoPage.assertEmailError('Invalid email id')
        log.info('Verified email cannot be left blank')
        myInfoPage.assertSecurityAnswerError('Security answer is required.')
        log.info('Verified security answer cannot be left blank')
        myInfoPage.assertCurrentPasswordError('Password should be a minimum of 8 characters')
        log.info('Verified current password cannot be left blank')
        myInfoPage.assertNewPasswordError('Password should be a minimum of 8 characters')
        log.info('Verified new password cannot be left blank')
        myInfoPage.assertConfirmPasswordError('Password should be a minimum of 8 characters')
        log.info('Verified confirm password cannot be left blank')
        screenshot('My Info', 'Validationerror')
        myInfoPage.closeMyInfo()
        myInfoPage.clickOnMyInfo()
        myInfoPage.firstNameValidation()
        myInfoPage.lastNameValidation()
        myInfoPage.emailValidation()
        myInfoPage.securityAnswerValidation()
        myInfoPage.clickChangePassword()
        myInfoPage.passwordValidation()
        screenshot('My Info', 'MyInfofieldvalidation')
        myInfoPage.closeMyInfo()
        myInfoPage.clickOnMyInfo()
        myInfoPage.enterFirstName(firstName)
        myInfoPage.enterLastName(lastName)
        myInfoPage.enterEmail(email)
        myInfoPage.clickSaveMyInfo()
        Assert.assertEquals(40, firstName.length())
        log.info('Verified first name can only 40 characters')
        Assert.assertEquals(40,lastName.length())
        log.info('Verified last name can only 40 characters')
        Assert.assertEquals(80,email.length())
        log.info('Verified email can only 80 characters')
        myInfoPage.clickOnMyInfo()
        myInfoPage.enterFirstName('Akila')
        myInfoPage.enterLastName('Agandeswaran')
        myInfoPage.enterEmail('akila.agandeswaran@medtronic.com')
        myInfoPage.clickSaveMyInfo()
        myInfoPage.clickOnMyInfo()
        myInfoPage.assertMyInfoText()
        log.info('The screen displayed the title My Info')
        myInfoPage.myInfoCloseDisplayed()
        log.info('Close link is displayed from the my info page')
        myInfoPage.verifySecurityQuestions()
        screenshot('My Info', 'securityquestions')
        log.info('Verified all the my info security questions')
        myInfoPage.closeMyInfo()
        log.info('My info screen is closed when clicking the close link')
        then: 'Verify My Info screen displays the information entered during administrative user registration.'
        then: 'Verify there is no possibility to edit UserName.'
        then: 'Verify the Close link is labeled as Close.'

        when: 'Click on the close link and user should be navigated to Home screen.'
        and: 'Navigate to My Info screen and enter all valid text input values and click close link'
        and: 'Navigate again to My Info screen and verify the text fields contains previously entered values.'
        then: 'Verify the change password checkbox is set to OFF by default.'
        then: 'Verify First Name field is indicated as required field.'
        then: 'Verify Last Name field is indicated as required field.'
        then: 'Verify Email field is indicated as required field.'

        when: 'Set Change password checkbox to be ON.'
        then: 'Verify the enter your current password field is indicated as required field.'
        then: 'Verify the new password field is indicated as required field.'
        then: 'Verify the re-enter new password field is indicated as required field.'

        when: 'Set change password checkbox to be OFF.'
        then: 'Verify the security question field is indicated as required field.'
        then: 'Verify the security answer field is indicated as required field.'

        when: 'Update the first name, last name, email and security answer with < symbol.'
        then: 'Verify first name cannot be saved with < symbol.'
        then: 'Verify last name cannot be saved with < symbol.'
        then: 'Verify email cannot be saved with < symbol.'
        then: 'Verify security answer cannot be saved with < symbol.'

        when: 'Update the first name, last name, email and security answer with > symbol.'
        then: 'Verify first name cannot be saved with > symbol.'
        then: 'Verify last name cannot be saved with > symbol.'
        then: 'Verify email cannot be saved with > symbol.'
        then: 'Verify security answer cannot be saved with > symbol.'

        when: 'Update the first name, last name, email and security answer with & symbol.'
        then: 'Verify first name cannot be saved with & symbol.'
        then: 'Verify last name cannot be saved with & symbol.'
        then: 'Verify email cannot be saved with & symbol.'
        then: 'Verify security answer cannot be saved with & symbol.'

        when: 'Update the first name, last name, email and security answer with = symbol.'
        then: 'Verify first name cannot be saved with = symbol.'
        then: 'Verify last name cannot be saved with = symbol.'
        then: 'Verify email cannot be saved with = symbol.'
        then: 'Verify security answer cannot be saved with = symbol.'

        when: 'Update first name field by leaving it empty and save changes'
        then: 'Verify first name value is required.'

        when: 'Update the first name field with more than 40 characters'
        then: 'Verify first name cannot be more than 40 characters'

        when: 'Update the first name field with 1 and save changes'
        then: 'Verify first name is saved as 1.'

        when: 'Click on My Info link and update the first name field with 40 characters'
        then: 'Verify first name is saved with 40 characters'

        when: 'Update first name field with leading and trailing spaces'
        and: 'Save changes.'
        and: 'Click on My Info link from Home Page.'
        then: 'Verify first name values was saved without any leading or trailing spaces'

        when: 'Update last name field by leaving it empty and save changes'
        then: 'Verify last name value is required.'

        when: 'Update the last name field with more than 40 characters'
        then: 'Verify last name cannot be more than 40 characters'

        when: 'Update the last name field with 1 and save changes'
        then: 'Verify last name is saved as 1.'

        when: 'Click on My Info link and update the last name field with 40 characters'
        then: 'Verify last name is saved with 40 characters'

        when: 'Update last name field with leading and trailing spaces'
        then: 'Verify last name values was saved without any leading or trailing spaces'

        when: 'Update email field by leaving it empty and save changes'
        then: 'Verify email value is required.'

        when: 'Update the email field: type valid email address with space between two characters and save changes'
        then: 'Verify Email value cannot contain any spaces.'

        when: 'Update Email field: type character and @ symbol like example test@ and save changes'
        then: 'Verify email should contain @ and . changes are not saved.'

        when: 'Update Email field: type character and . symbol like example test. and save changes'
        then: 'Verify email should contain both @ and . changes are not saved.'

        when: 'Update the email field with more than 80 characters long'
        then: 'Verify Email value cannot be more than 80 characters'
        then: 'Verify security question dropdown contains all required options.'

        when: 'Update value in security question dropdown and save changes.'
        and: 'Click on My Info link from home page'
        then: 'Verify Security Question dropdown contains all required options.' +
                'Security Question dropdown contain options:' +
                'Mother’s Maiden Name' +
                'Mother’s Birth Date [day, month]' +
                'Pet’s Name' +
                'Favorite Sports Team'

        when: 'Update value in Security Question dropdown.'
        and: 'Save changes.'
        and: 'Click on My Info link from Home page'
        then: 'Verify Security Question dropdown value is changed.Security Question dropdown value is changed.'

        when: 'Update security answer field: leave the field empty and save changes.'
        then: 'Verify security answer value is required.'

        when: 'Update security answer text field: type character 1 and save changes.'
        and: 'Click on My Info link from home page'
        then: 'Verify security answer with 1 character is saved.'

        when: 'Update security answer text field with more than 40 characters.'
        then: 'Verify security answer value cannot be more than 40 characters.'

        when: 'Update security answer field with just 40 characters and save changes.'
        and: 'Click on My Info link from home page.'
        then: 'Verify security answer with 40 characters is saved.'

        when: 'Update security answer text field with leading and trailing spaces and save changes.'
        and: 'Click on My Info link from Home page.'
        then: 'Verify security answer was saved without any leading and trailing spaces.'

        when: 'Set Change password checkbox to be active.'
        and: 'Enter more than 20 characters on current password field.'
        then: 'Verify enter current password cannot be more than 20 characters.'
        then: 'Verify entry fields for enter your current password are masked with asterisk instead of entered character.'

        when: 'Fill in new password and re-enter password with atleast 8 characters.'
        and: 'Leave the current password field empty and save changes'
        then: 'Verify enter your current password field is required.'

        when: 'Fill in new password and re-enter password with atleast 8 characters.'
        and: 'Fill in enter your current password with password not equal to current user password and save changes'
        then: 'Verify new password is not saved and error message is displayed.'

        when: 'Open a tab in any other browser using the link from Step 2898910.'
        and: 'Sign into the MMT-7340 application using the username from step 2898922 and password equal to New Password that was entered in previous step.'
        then: 'Verify user is not able to login with new password and directed to incorrect login screen'

        when: 'Close new browser.'
        and: 'Fill in the new password and re-enter password with minimum of 8 characters and all password criteria.'
        and: 'Fill in the current password with password equal to current user password and save changes.'
        and: 'Log out from the system using the Sign Out link.'
        and: 'Sign into MMT-7340 application using the username and the new password.'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        then: 'Verify user is able to login with new password after password was successfully changed. User is navigated to Home page.'

        when: 'Open a tab in a new browser and login with old password.'
        then: 'Verify the user cannot login using the old password.'

        when: 'Close the tab.'
        and: 'Navigate to My Info screen by clicking on the My Info link from home page.'
        and: 'Set change password checkbox to active.'
        and: 'Fill in a new password and re-enter password not matching the password criteria and save changes.'
        and: 'Repeat the above step for 4 times and the 5th time the system should logout the user to the login page.'
        and: 'Sign into the MMT-7340 application using the correct password.'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Go to My Info page.'
        and: 'Set change password checkbox to active.'
        and: 'Fill in Enter Your Current Password textfield with password equal to current password. Leave New password textfield empty and save changes.'
        then: 'Verify New Password value is required. Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password. Leave re-enter password textfield empty and save changes.'
        then: 'Verify Re-enter New Password value is required. Changes are not saved and error message is displayed.'

        when: 'Set New Password matching next conditions: - Attempt to type more than 20 characters'
        then: 'Verify New Password value cannot be more than 20 characters. It\'s not allowed to enter 21 characters into New Password textfield.'
        then: 'Verify entry fields for New Password are masked with asterisk instead of the entered character' +
                'Entry fields for New Password are masked with asterisk instead of the entered character'

        when: 'Set Re-enter New Password matching next conditions: ' +
                '- Attempt to type more than 20 characters'
        then: 'Verify Re-enter New Password value cannot be more than 20 characters. It\'s not allowed to enter 21 characters into Re-enter New Password textfield.'
        then: 'Verify entry fields for Re-enter New Password are masked with asterisk instead of the entered character.' +
                'Entry fields for Re-enter New Password are masked with asterisk instead of the entered character'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password. ' +
                'Set New Password and Re-enter New Password matching next conditions: - Length equal to 7 characters' +
                'Save changes.'
        then: 'Verify new password is not set when New Password and Re-enter New Password length is less than 8 characters.' +
                'Changes are not saved and error message is displayed'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password. Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '· Uppercase letter;' +
                '· Lowercase letter.' +
                'Save changes.'
        then: 'Verify new password value cannot be set if only 2 of the following character types are present: Uppercase letter, Number.' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password. Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '· Uppercase letter;' +
                '· Special character.' +
                'Save changes.'
        then: 'Verify new password value cannot be set if only 2 of the following character types are present: Uppercase letter, Special character.' +
                'Changes are not saved and error message is displayed'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '· Lowercase letter;' +
                '· Number.' +
                'Save changes.'
        then: 'Verify new password value cannot be set if only 2 of the following character types are present: Lowercase letter, Number.' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '· Lowercase letter;' +
                '· Special character.' +
                'Save changes.'
        then: 'Verify new password value cannot be set if only 2 of the following character types are present: Lowercase letter, Special character.' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '· Special character;' +
                '· Number.' +
                'Save changes.'
        then: 'Verify new password value cannot be set if only 2 of the following character types are present: Special character, Number.' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password with password equal to current user password.' +
                'Fill in New Password and Re-enter Password matching next conditions: ' +
                '- Length equal to 8 characters; ' +
                '- Text shall include a minimum of three of the following character types:' +
                '· Uppercase letter;' +
                '· Lowercase letter;' +
                '· Number;' +
                '· Special Character.' +
                'Save changes.'
        then: 'Verify new password with 8 characters is saved.' +
                'User is navigated to Home page.'

        when: 'Click on My Info link from Home Page. '
        and: 'Set Change password checkbox to be active.' +
                'Fill in Enter Your Current Password with password equal to current user password.' +
                'Fill in New Password and Re-enter Password matching next conditions:' +
                '- Length equal to 20 characters; ' +
                '- Text shall include a minimum of three of the following character types:' +
                '· Uppercase letter;' +
                '· Lowercase letter;' +
                '· Number;' +
                '· Special Character.' +
                'Save changes.'
        then: 'Verify new password with 20 characters is saved.' +
                'User is navigated to Home page.'

        when: 'Click on My Info link from Home Page.' +
                'Set Change password checkbox to be active.' +
                'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Fill in New Password and Re-enter Password matching next conditions: ' +
                '- Length equal to 20 characters;' +
                '- Text shall include a minimum of three of the following character types:' +
                '· Uppercase letter;' +
                '· Lowercase letter;' +
                '· Number;' +
                '· Special Character.' +
                '- Password should be different.' +
                'Save changes.'
        then: 'Verify new password is not set when Re-enter New Password does not match New Password.' +
                'Changes are not saved and error message is displayed.'

        when: 'On My Info page set Change Password checkbox to be active.' +
                'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Fill in New Password and Re-enter new Password (e.g. username1$T, username1$T) which is a subset of Username (e.g. Username1).' +
                'Save changes.'
        then: 'Verify new password is not set when New Password and Re-enter New Password is a subset of Username.' +
                'Changes are not saved and error message is displayed.'

        when: 'On My Info page set Change Password checkbox to be active.' +
                'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Fill in New Password and Re-enter new Password (e.g. USERname1$T, USERname1$T) which matches with Username (e.g. Username1) but some letters in upper case.' +
                'Save changes.'
        then: 'Verify new password is not set when New Password and Re-enter New Password is a case insensitive in comparison with Username.' +
                'Changes are not saved and error message is displayed.'

        when: 'On My Info page set Change Password checkbox to be active.' +
                'Fill in Enter Your Current Password text field with password equal to current user password.' +
                'Fill in New Password and Re-enter new Password (e.g. username$T, username$T) wich is not contain Username completely (e.g. Username1).' +
                'Record New Password.' +
                'Save changes.'
        then: 'Verify new password is set when New Password and Re-enter New Password not contain Username completely.' +
                'Changes are saved and error message is NOT displayed.'
        then: 'End of the test.'
    }

    def 'User Logout' () {
        when:'A user is logged into carelink'
        signInPage = browsers.at SignInPage
        then: 'User should be able to sign out from carelink'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
        log.info('Signed out of the carelink application')
    }
}
