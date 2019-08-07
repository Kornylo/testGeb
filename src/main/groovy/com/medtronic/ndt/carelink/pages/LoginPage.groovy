package com.medtronic.ndt.carelink.pages

import com.medtronic.ndt.carelink.data.ReadProperties
import geb.Page
import geb.navigator.Navigator
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver


class LoginPage extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            continueButton.displayed || signinButton.displayed || submitButton.displayed || medtronicLogo || signoutButton.displayed
        }
    }

    static content = {
        // Username
        usernameTextbox(required: false) { $("input", id: "j_username") }
        // Password
        passwordTextbox(required: false) { $("input", id: "j_password") }
        // Continue button
        continueButton(required: false, wait: 5) { $("input", id: "test:study") }
        // Reminder URL page
        reminderUsername(required: false) { $('a[href$="/ipro/hcp/forgot-username.jsf"]') }
        // Username Reminder
        userNameTextbox(required: false) { $("input", id: "reminderform:username") }
        // Last Name Reminder rov
        lastNameTextbox(required: false) { $("input", id: "reminderform:lastname") }
        // Email Reminder row
        emailReminderTextbox(required: false) { $("input", id: "reminderform:email") }
        // Reminder URL click usermane
        reminderButtonClickPass(required: false) { $('a[href$="/ipro/hcp/forgot-password.jsf"]') }
        // Reminder button
        submitButton(required: false, wait: 5) { $("input.sv_btn") }
        //Reminder error user
        usernNameClick(required: false) { $("input", id: "reminderform:username") }
        //Reminder success user
        reminderSuccessUser(required: false, wait: 5) { $(id: "j_id_id8") }
        //Reminder error user
        reminderErrorUser(required: false, wait: 5) { $("span", id: "reminderform:emailerr") }
        //Reminder password sent
        passwordReminderSent(required: false, wait: 5) { $("form", id: "j_id_id7") }
        //Reminder password close
        reminderPasswordClose(required: false, wait: 5) { $("div.button") }
        // Error Message login
        signinErrorMessage(required: false, wait: 5) { $("div.login_warning") }
        // Is the login form displayed
        loginForm(required: false, wait: 5) { $(id: "user_log") }
        // Sud-menu footer displayed
        footerSubmenu(required: false, wait: 5) { $(id: "footer_submenu_r") }
        // ladev СЄ0459 displayed
        legalLabel(required: false, wait: 5) { $(id: "legal_right") }
        // Banner image in the home page
        bannerImage(required: false) { $("div", id: "banner") }
        //Signin button
        signinButton(required: false, wait: 5) { $("input", id: "signinbtn") }
        // Config page googel chrome
        buttonContinue(required: false, wait: 5) { $("input", id: "test:study") }
        configTable(required: false, wait: 5) { $(id: "hawkResults") }
        configBrowser(required: false, wait: 5) { $("tr.unsupported") }
        buttonCancel(required: false, wait: 5) { $("span.font_sml") }
        homeLink(required: false, wait: 5) { $("#footer_submenu_r > li:nth-child(1) > a") }
        contactUsLink(required: false, wait: 5) { $(By.cssSelector("#footer_submenu_r > li.mono > a")) }
        privacyStatementLink(required: false, wait: 5) { $(By.cssSelector("#footer_submenu_r > li:nth-child(3) > a")) }
        termsOfUseLink(required: false, wait: 5) { $(By.cssSelector("#footer_submenu_r > li:nth-child(2) > a")) }
        trainingLink(required: false, wait: 5) { $(By.cssSelector("#footer_submenu > li.last > a")) }
        userGuideLink(required: false, wait: 5) { $(By.cssSelector("#footer_submenu > li:nth-child(1) > a")) }
        loginErrorEnvisionLogo(required: false, wait: 5) { $(By.cssSelector("#envision")) }
        loginErrorMedtronicLogo(required: false, wait: 5) {
            $(By.cssSelector("body > div > div > div.header_er > img"))
        }
        titleloginError(required: false, wait: 5) {
            $(By.cssSelector("body > div > div > div.main-contact > div.login-contact > h1"))
        }
        pageErrorMsg(required: false, wait: 5) { $("div", id: "user_log_fail") }
        forgotPasswordLink { $("dd:nth-child(4) > a") }
        forgotUsernameLink { $("dd:nth-child(2) > a") }
        loginErrorForm { $("#user_log_fail > form") }
        signoutButton(required: false) { $("a", id: "list:logout") }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
    }

    /*------------------------- Inactivity warning popup, logout page -------------------------*/
    // Print page title in console
    public void printPageTitle() {
        driver.getTitle()

    }
    // Is inactiv page displayed
    public boolean isLogoutPageDisplayed() {
        driver.findElement(By.cssSelector("div.login_warning")).isDisplayed()
    }
    // Print inactive message on logout page (Print in console)
    public void isErrorMessageLogoutPageDisplayed() {
        driver.findElement(By.cssSelector("div.login_warning")).getText()

    }
    // Print inactive title in the logout (Print in console)
    public boolean sessionTimeoutPageTitle() {
        driver.getTitle().equals("Session Timeout Page")
    }
    // IS inactive title in the popup displayeds
    public boolean isSessionTimeouTitleDisplayed() {
        driver.findElement(By.id("ui-dialog-title-divAjaxTimer")).isDisplayed()
    }
    // Is inactive button in the popup displayed
    public boolean isSessionTimeoutButtonDisplayed() {
        driver.findElement(By.id("resumeBtn")).isDisplayed()
    }
    //Session Timeout Popup Text Displayed
    void isSessionTimeoutPopupTextDisplayed() {
        $(id: "resumeBtn").displayed
    }

    void verifyTimeOutIsNotDisplayed() {
        assert !$(id: "resumeBtn").displayed
    }

    void clickResume() {
        $(By.id("resumeBtn")).click()
        waitFor { !$(By.id("resumeBtn")).displayed }
    }
    /*-------------------------URL to reminder username ------------------------*/

    boolean getUrlReminderUserName() {
        driver.get("https://build16.ols.minimed.com/ipro/hcp/forgot-username.jsf")
    }

    boolean goToTimedOutPage() {
        driver.get("https://build16.ols.minimed.com/ipro/hcp/session-timeout.jsf?bhcp=1")
    }
    /*-------------------------google chrome verification -------------------------*/

    boolean isConfigTableBrowser() {
        configBrowser.displayed
    }

    boolean isCconfigTableDisplayed() {
        configTable.displayed
    }

    boolean isButtonContinueDisplayed() {
        buttonContinue.displayed
    }
    /*-------------------------Veryfi -------------------------*/

    boolean legalLabeDisplayed() {
        legalLabel.displayed
    }

    boolean footerSubmenuDisplayed() {
        footerSubmenu.displayed
    }

    boolean loginFormDisplayed() {
        loginForm.displayed
    }

    /*-------------------------Reminder Pass-------------------------*/

    boolean userNameTextboxClick() {
        usernNameClick.click()
    }

    void userNameTextbox(String username) {
        userNameTextbox = username
    }

    boolean isPasswordReminderSentDisplayed() {
        passwordReminderSent.displayed
    }

    boolean reminderPasswordCloseClick() {
        reminderPasswordClose.click()
    }
    /*-------------------------Reminder-------------------------*/

    void lastNameTextbox(String lastname) {
        lastNameTextbox = lastname
    }

    void emailReminderTextbox(String email) {
        emailReminderTextbox = email
    }

    void isReminderButtonDisplayed() {
        assert submitButton.displayed
    }

    void reminderButtonClick() {
        submitButton.click()
    }

    void reminderButtonCancelClick() {
        buttonCancel.click()
    }

    void reminderPassURLButtonClick() {
      assert   reminderButtonClickPass.click()
    }

    void isReminderUserErrorDisplayed() {
        assert reminderErrorUser.displayed
    }

    void isUsernameReminderSentDisplayed() {
        assert reminderErrorUser.displayed
    }

    void isReminderUserSuccessDisplayed() {
        assert reminderSuccessUser.displayed
    }

    void signInButtonDisplayed() {
        assert signinButton.displayed
    }

    void isBannerImageDisplayed() {
        assert bannerImage.isDisplayed()
    }

    void enterUsername(String username) {
        usernameTextbox = username
    }

    void enterPassword(String password) {
        passwordTextbox = password
    }

    void enterUsernameDisplayed() {
        usernameTextbox.displayed
    }

    void enterPasswordDisplayed() {
        passwordTextbox.displayed
    }

    void clickOnSignIn() {
        signinButton.click()
    }

    void userGuidePageOpen() {
        $(By.xpath("//a[@href='/marcom/ipro2/en/US/userguides.html']")).click()
    }

    void verifyTimeOutError() {
        waitFor { $("div.login_warning").displayed }
        $("div.login_warning").text().contains("Your CareLink session has expired.")
        $("div.login_warning").text().contains("For your security, CareLink sessions automatically expire after 20 minutes of inactivity.")
        $("div.login_warning").text().contains("Enter your username and password to sign in.")
        waitFor { browser.getCurrentUrl().contains("hcp/session-timeout.jsf") }
        waitFor { medtronicLogo.displayed }
        assert $("#footer_submenu_r > li:nth-child(2) > a").text() == "Terms of Use"
        assert $("#footer_submenu_r > li:nth-child(3) > a:nth-child(1)").text() == "Privacy Statement"
        assert $(".mono > a:nth-child(2)") .text() == "Contact Us"
        assert $("#footer_submenu_r > li:nth-child(1) > a").text() == "Home"
        assert forgotPasswordLink.displayed
        assert forgotUsernameLink.displayed
        assert signinButton.displayed
    }

    boolean homeLinkDisplayed() {
        homeLink.displayed
    }

    boolean contactUsLinkDisplayed() {
        contactUsLink.displayed
    }

    boolean privacyStatementLinkDisplayed() {
        privacyStatementLink.displayed
    }

    boolean termsOfUseLinkDisplayed() {
        termsOfUseLink.displayed
    }

    boolean trainingLinkDisplayed() {
        trainingLink.displayed
    }

    boolean UserGuideLinkDisplayed() {
        userGuideLink.displayed
    }

    boolean loginErrorLogoEnvision() {
        loginErrorEnvisionLogo.displayed
    }

    boolean loginErrorLogoMedtronic() {
        loginErrorMedtronicLogo.displayed
    }

    boolean loginErrorPageTitle() {
        titleloginError.displayed
    }

    boolean loginErrorFormDisplayed() {
        loginErrorForm.displayed
    }

    void errorMsgLogin(String title) {
        pageErrorMsg.text().contains(title)
    }

    void loginErrorPageTitle(String title) {
        waitFor { titleloginError.displayed }
        titleloginError.text().contains(title)
    }

    void forgotPasswordLinkClick() {
        forgotPasswordLink.click()
    }

    void forgotUsernameLinkClick() {
        forgotUsernameLink.click()
    }

    public boolean waitForPageLoaded() {
        waitFor { js.('document.readyState') == 'complete' }
    }

    void verifyIsElementsMissing() {
        int count = driver.findElements(By.id("carelink")).size()
        if (count > 0) {
            println("The element exists!!")
        }
    }

    public backToMaimPage() {
        driver.switchTo().window(driver.windowHandles[0])
    }

    void homeClick() {
        waitFor { $("#footer_submenu_r > li:nth-child(1) > a:nth-child(1)").displayed }
        $("#footer_submenu_r > li:nth-child(1) > a").click()
        waitFor { title == ReadProperties.getProperties().get("login.title") }
    }
}
