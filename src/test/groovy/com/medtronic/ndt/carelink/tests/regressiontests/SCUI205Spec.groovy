package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
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
@Title('ScUI 205 - Login Screen.')
@Screenshot
@RegressionTest
@Stepwise
class SCUI205Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LanguagePage languagePage
    static HomePage homePage
    static Precondition precondition

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'

    def 'Verify Login page'() {
        when: 'Execute ScUI 205 - Login Screen per test plan.'
        and:
        'Open the MMT-7340 application (CareLink iPro).' +
                'Record the server URL address.'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        then: 'Verify that when a user submits the CareLink_iPro_URL* into a browser’s address bar with a connection to the internet, the software system presents the user with the Login Screen'
        signInPage.signInButtonDisplayed()
        then:
        'Verify that the layout and implementation of UI controls are intuitive and consistent throughout the CareLink iPro Login page.' +
                'Note: This step should be executed manually.'

        when: 'Make a the screenshot of Login page and attach it to the test session'
        screenshot('SCUI205', 'Login page')
        then: 'Verify that CareLink iPro Login page display Common Screen Header Element: Medtronic MiniMed Logo in the upper left corner of the screen.'
        signInPage.verifyLogo()
        then: 'Verifying MMT-7340 Login Page GUI elements'
        then: 'Verify that Login Screen showing Common Screen Footer Elements: User Guide, Register Clinic, Terms of Use, Privacy Statement, Contact Us.'
        signInPage.checkIncludedFooterElements()
        when: 'Mark 2790793 as skipped if Spark_Flag is True for selected country.'
        then: 'Verify that the "CareLink TM" logo is displayed when Spark_Flag is set to False.'
        when: 'Mark 2790768 as skipped if Spark_Flag is False for selected country.'
        then: 'Verify that the "CareLink Therapy Management Software for Diabetes" logo is displayed when Spark_Flag is set to True.'
        then: 'Verify that two entry fields Username and Password are present on the Login page.'
        then: 'Verify that “Forgot username?” and “Forgot password?” links are present.'
        then: 'Verify that the button Sign In is present.'
        then: 'Verify that the Login page has the link “Change country/language”'
        then: 'Verify the User Guide link is provided.'
        then:
        'Verify the following is displayed:' +
                '- “Register Clinic” link;' +
                '- “Terms of Use” link;' +
                '- “Privacy Statement” link;' +
                '- “Contact Us” link;' +
                '- National Law Disclaimer text;' +
                '- Device Availability text.'
        then: 'Verify on the Login screen the CE Mark is displayed.'
        signInPage.checkGuiElements()
        signInPage.checkAllFunctionsOnPage()

        when: 'Click on "Change country/language" link.'
        signInPage.clickSelectCountryLanguage()
        then: 'Verify that after clicking on “Change country/language” link user will be navigated to the screen “Select Country Language Screen” for following user selection for supported country and language according with the Internationalization and Localization Specification.'
        when: ''
        languagePage = browsers.at LanguagePage
        then: ''

        when:
        'Select any country and language' +
                'Click on [Select] button.'
        languagePage.selectCountry("United States")
        languagePage.buttonSelectClick()
        then: 'Verify that after country and language selection are done and user click on “Select” button, user will be navigated back to the Login screen.'
        when: ''
        signInPage = browsers.at SignInPage
        then: ''
    }

    def 'Register new clinic'() {
        when: 'Click on Register Clinic link'
        then: 'Verify that after clicking on Register Clinic button on the down right corner of the Login screen User will be navigated to the Enrollment Info Screen.'
        when:
        'Create a new clinic.' +
                'Record the info: - Clinic Name.'
        and:
        'Create a new administrative user (use your medtronic email for email)' +
                'Record the info:' +
                '- Username (example: TC1709);' +
                '- Password (example: Password1);' +
                '- Email;' +
                '- Last name.' +
                'Finish clinic registration process.'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, "Test1234@")
        println("Username: " + adminUs + " Password: Test1234@")
        println("Email: " + emailAddress)
        println("Last name: Stadnik")
        then: 'Verify that after user will complete the enrollment process and will get new clinic account, user will be navigated back to the Login screen.'
        then: 'Verify that the default focus is in the username entry field.'
    }

    def 'Sign in with correct credentials (check case-insensitive)'() {
        when:
        'Enter the username in the uppercase format and password for just registered Clinic' +
                'Click on [Sign in] button'
        precondition.signInAsClinicAdmin(adminUs.toUpperCase(), "Test1234@")
        println "Logging in as " + adminUs.toUpperCase()
        then: 'Verify that the HCP user can enter Username and Password and then press Sign in.'
        then: 'Verify username is case-insensitive.'
        then: 'Verify that when the valid username and password values are entered, the system navigates the user to the Home Screen.'
        when: ''
        homePage = browsers.at HomePage
        then: ''

        when: 'Click on [Sign out] button.'
        sleep(2000)
        homePage.clickSignOutButton()
        then: 'Verify that after logging off CareLink iPro and clicking on Browsers back button, application will not keep you still logged in.'
        when: ''
        signInPage = browser.at SignInPage
        signInPage.navigateBack()
        then: ''
    }

    def 'Sign in with correct credentials'() {
        when:
        'Enter the valid username and password' +
                'Click on [Sign in] button'
        precondition.signInAsClinicAdmin(adminUs, "Test1234@")
        println "Logging in as " + adminUs
        then: 'Verify that, on successful login verification, MMT-7340 serves the “Patient List” home screen page.'
        then: 'Verify that after logging off from CareLink iPro and coming back to the Login page, the user is able to successfully login.'
        when: ''
        homePage = browser.at HomePage
        then: ''
    }

    def 'Sign in with wrong credentials'() {
        when: 'Click on [Sign out] button.'
        homePage.clickSignOutButton()
        and:
        'Attempt to log in with the following credentials:' +
                'Username: username;' +
                'Password: password.'
        precondition.signInAsClinicAdmin("username", "password")
        then: 'Verify that using incorrect credentials, the system navigates the user to the Incorrect Login Screen.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Sing in with wrong credentials: empty password field'() {
        when:
        'Navigate back to the Login Screen.' +
                'Enter valid username and click "Enter" button on keyboard.'
        signInPage.navigateBack()
        signInPage.enterUsername(adminUs)
        signInPage.clickEnterOnKeyboard()
        then: 'Verify that when you typed valid username and hit the Enter, if cursor was still been located in username text field, then CareLink iPro will behave properly and shall navigate to “Incorrect Login Screen ”.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Verify simultaneously login with the same credentials'() {
        when: 'Navigate back to the Login Screen.'
        signInPage.navigateBack()
        then: 'Verify that the any other single user or multi-users are able simultaneously to login successfully with the same username and password.'
        precondition.signInAsClinicAdmin(adminUs, "Test1234@")
        when: ''
        homePage = browsers.at HomePage
        //signInPage.verifyAbleSimultaneouslyLogin(adminUs, "Password1")
        then: ''
    }

    def 'Forgot username'() {
        when: 'Navigate back to the Login Screen.'
        homePage.navigateBack()
        homePage.navigateBack()
        signInPage = browser.at SignInPage
        and: 'Click on "Forgot username?" link.'
        then: 'Verify “Forgot username?” link navigates user to Forgot Username screen.'
        then: 'Verify that after clicking on ”Forgot Username?” link, software will require from user to enter Last Name and Email address.'

        when:
        'Entering any non-existent Last Name and Email address.' +
                'Click on [Submit] button.'
        then:
        'Verify application shows error message:' +
                '“The information you provided does not match our records. Please confirm spelling and be' +
                'sure to use the email address associated with your account. If you continue to have' +
                'problems, please call [Contact_Us_Info_Phone] for assistance.”'
        signInPage.forgotUserNameFlowNegative("wrong", "blabla@bla.com")

        when:
        'Enter valid Last name and Email from step 3129825.' +
                'Click on [Submit] button.'
        then: 'Verify that system navigates user to Username Reminder Submitted Screen after entering valid Last name and username'
        signInPage.forgotUserNamePositiveFlow("Stadnik", emailAddress)
    }

    def 'Forgot password'() {
        when:
        'Navigate back to the Login Screen.' +
                'Click on "Forgot password?" link'
        then: 'Verify “Forgot password?” link navigates user to Forgot Password screen.'
        then: 'Verify that in case of clicking on ”Forgot Password?” link, CareLink iPro require from user to enter Username and Email address in the Reset Password dialog'
        then: 'Verify that CareLink iPro serves the Reset Password dialog which contains two entry fields Username and Email and two buttons Cancel and Submit.'
        then: 'Verify that if HCP clicks on the “Cancel” control, CareLink iPro closes the new browser window or tab session.'
        signInPage.verifyLogoForgotPasswordPage()

        when: 'Click on ”Forgot Password?” link.'
        and:
        'Enter non-existent username and email.' +
                'Click [Submit] button.'
        then:
        'Verify that if the username is not found or the email address does not match the stored value in the user account, CareLink iPro serves the Password Reset Page with following error message' +
                '“The information you provided does not match our records. Please confirm spelling and be sure to use the email address associated with your account. If you continue to have problems, please call [Contact_Us_Info_Phone] for assistance.”.'
        then: 'Verify that the Username field in the Reset dialog has maximum 16 characters entry length.'
        then: 'Verify that the Email field in the Reset dialog has maximum 80 characters entry length.'
        signInPage.forgotPasswordFlowNegative("NameWrong", "incorrect123@pes.com") //failed todo
//        https://build15.ols.minimed.com/ipro/hcp/500.jsf?errorID=1559919852935
//        An unknown error occurred. Please try again later. (1559920013813)

        when:
        'Enter valid Username and Email from step 3129825.' +
                'Click on [Submit] button.'
        then: 'Verify that system navigates user to Password Reset Submitted screen after entering valid username and email.'
        signInPage.forgotPasswordFlowPositiveFlow(adminUs, emailAddress)
//        https://build15.ols.minimed.com/ipro/hcp/500.jsf?errorID=1559920107129
//        An unknown error occurred. Please try again later. (1559920107129)
    }

    def 'Try to login with old password and see error'() {
        when: 'Enter Username and old password on the Login Screen.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("Test1234@")
        signInPage.clickOnSignIn()
        then: 'Verify that when the user received the temporary password, then the old password is deactivated so that the user is navigated to Incorrect Login screen.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Login with temporary password and setup new'() {
        when:
        'Navigate back to the Login Screen.' +
                'Enter Username and new temporary password that was sent to the email and click [Log in] button.'
        signInPage.navigateBack()
        then:
        'Verify that the sent temporary password value is case-sensitive.' +
                'User will then select a new password of their choice.'
        MailUtil.waitForLetter(emailAddress, 1)
        MailUtil.checkEmailBoxForgotUsername(emailAddress, adminUs)
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        then: 'Verify that, CareLink iPro requests the user to change their password on next login.'
        then: 'Verify that the password is masked when the user types it in.'
        signInPage.newPasswordScreen('Change Password Page', 'New password required')

        when:
        'Enter the new password (e.g. Password2).' +
                'Click [Save] button.' +
                'Click Sign out button and navigate back to the Login Screen.'
        signInPage.enterPasswordConfirm("Password2")
        println('The New Password: Password2')
        signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
        then: ''
    }

    def 'Try to login with temporary password'() {
        when: 'Enter the correct username and temporary password.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(Mail.getAllMessagesWithoutWait(emailAddress).last.body.split('\n').getAt(3).replace('\r', '').replaceAll('.*:', ''))
        signInPage.clickOnSignIn()
        then: 'Verify that the software does not open if given an incorrect password and displays a warning window.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Sign in with correct credentials '() {
        when:
        'Navigate back to the Login Screen.' +
                'Enter the correct username and new password.'
        signInPage.navigateBack()
        precondition.signInAsClinicAdmin(adminUs, "Password2")
        then: 'Verify that the software functions normally after a correct username and password have been entered and invoking the correct “Select a Patient “ page.'
        when: ''
        homePage = browsers.at HomePage
        then: ''
    }

    def 'Sing in with wrong credentials: empty username field'() {
        when: 'Click on Sign out link'
        sleep(2000)
        homePage.clickSignOutButton()
        signInPage = browsers.at SignInPage
        then: 'CareLink iPro Login page field validation, boundary value analysis on username and password.'
        then: 'Verify the Username field has a max of 16 characters entry length.'
        signInPage.verifyUserNameTextBox()

        when:
        'Enter password and leave Username field empty.' +
                'Click on [Sign in] button.'
        signInPage.enterPassword("Password2")
        signInPage.clickOnSignIn()
        then: 'Verify that Username is required field.'
        then: 'Verify the password value is masked when the user types it in.'
        then: 'Verify that Password field has a max of 20 characters entry length.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Sing in with wrong credentials: empty password field '() {
        when:
        'Enter valid username.' +
                'Leave password value as blank.' +
                'Click on [Sign in] button.'
        signInPage.enterUsername(adminUs)
        signInPage.clickOnSignIn()
        then: 'Verify that Password is required field.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Verify password value is case-sensitive'() {
        when:
        'Enter valid username.' +
                'Type valid password with one letter to be uppercased/lowercased.' +
                'Click on [Sign in] button.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("PaSsword2")
        signInPage.clickOnSignIn()
        sleep(1000)
        then: 'Verify password value is case-sensitive.'
        signInPage.checkLoginError("We do not recognize your username and/or password.", "Please try again.", "Enter your username and password to sign in.")
    }

    def 'Sing in with correct credentials'() {
        when:
        'Enter valid username and password.' +
                'Click on [Sign in] button.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword("Password2")
        signInPage.clickOnSignIn()
        sleep(2000)
        signInPage.postponeMFA()
        and:
        'Record the URL address for Home page.' +
                'Click on Sign out link.'
        homePage = browsers.at HomePage
        def homePageURL = getCurrentUrl()
        homePage.clickSignOutButton()
        sleep(2000)
        and: 'In the browser\'s URL address enter the URL from step 2791012 and click "Enter" button.'
        signInPage = browsers.at SignInPage
        browser.go(homePageURL)
        then: 'Verify that user not able to bypass the login procedure by using a captured URL.'
        signInPage.signInButtonDisplayed()
        then: 'End of test.'
    }
}