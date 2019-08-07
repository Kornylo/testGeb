package com.medtronic.ndt.carelink.pages.dashboard

import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.util.Precondition
import geb.Page
import org.junit.Assert

//import org.omg.CORBA.Object
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.StaleElementReferenceException
import java.util.concurrent.TimeUnit

class SignInPage extends Page {

    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        continueButton.displayed || signinButton.displayed || signoutButton.displayed || closePatientButton.displayed
    }

    static content = {
        // css class named input
        continueButton(required: false) { $("input", id: "test:study") }
        // Div is a table, with id hawkResults
        incompatiblityTable { $('table#hawkResults') }
        unsupportedBrowserText { $('table.content p') }
        // Username
        usernameTextbox(required: false) { $(id: "j_username") }
        // Password
        passwordTextbox(required: false) { $(id: "j_password") }
        heading(required: false) { $("h1", id: "envision") }
        // Sign in button
        signinButton(required: false) { $("input", id: "signinbtn") }
        pageErrorMsg(required: false) { $("div", id: "user_log_fail") }
        //New Patient
        newPatient(required: false) { $ "input", id: "list:createBtn" }
        //Search Patient
        searchPatient(required: false) { $("input", id: "searchPatient") }
        patientInfo(required: false) { $("a", id: "study:details") }
        openPatientButton(required: false) { $("input", id: "list:openPatientBtn") }
        patientListTable(required: false) { $('table', id: 'patientList') }
        closePatientButton(required: false) { $("input", id: "study:close") }
        //Welcome text
        welcomeText(required: false) { $ 'li.welcome' }
        //Sign out button
        signoutButton(required: false) { $("a", id: "list:logout") }
        //Select country and language
        selectCountryLanguage(required: false) { $('a', id: ~/(.*?)location/) }
        registerClinic(required: false) { $(".mono_rev > a:nth-child(1)") }

        //TermOfUseLink
        termOfUse(required: false) { $("#footer_submenu_r > li:nth-child(2) > a") }
        termOfUseContent(required: false) { $(".scroll_content") }
        termOfUseCloseButton(required: false) { $(By.id("tou_form:egress1")) }

        //Privacy Policy from Term of Use
        privacyPolicy1(required: false) { $("#tou_form > div > div.scroll_content > p:nth-child(14) > a") }
        privacyPolicy2(required: false) {
            $("#tou_form > div > div.scroll_content > p:nth-child(16) > a:nth-child(1)")
        }
        privacyPolicy3(required: false) {
            $("#tou_form > div > div.scroll_content > p:nth-child(16) > a:nth-child(2)")
        }
        contactUs(required: false) { $(".mono > a:nth-child(1)") }
        //Privacy Statement content//
        privacyStatement(required: false) { $("#footer_submenu_r > li:nth-child(3) > a:nth-child(1)") }
        footerSubmenu(required: false) { $("ul", id: "footer_submenu_r") }
        privacyStatementHeader(required: false) { $("div", class: "header_er") }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
        privacyStatementCloseButton(required: false) { $("input", id: "pp_form:egress1") }
        privacyStatementTitle(required: false) { $(".er_content > h1") }
        privacyStatementNotice(required: false) { $(".scroll_content > p:nth-child(2) > a:nth-child(1)") }
        privacyStatementContent(required: false) { $("div", class: "scroll_content") }
        termOfUseLinkFromPrivacyPolicy(required: false) {
            $(".scroll_content > p:nth-child(3) > a:nth-child(1)")
        }
        contactUsFromPrivacyPolicy(required: false) {
            $(".scroll_content > center:nth-child(38) > a:nth-child(5)")
        }
        selectCountryLanguage(required: false) { $(id: "location") }
        titleButtonNewPatient(required: false, wait: true) { $(By.id("list:createBtn")) }
        selectParientFromList(required: false) { $(id: "lastNameTd0") }
        userFormPassword(required: false) { $(id: "userform:password") }
        userFormRetypePassword(required: false) { $(id: "userform:retype_password") }
        userFormSave(required: false) { $(id: "userform:savebtn") }
        selectParientFromList(required: false) { $(id: "lastNameTd0") }
        clinicSettingsHomePage(required: false) { $(id: 'list:clinicsettings') }
        verifyForgotuserNameClick { $("#user_log > form > dl > dd:nth-child(2) > a") }
        langValue { $(id: "lang").text() }
        loginWarning() { $(".login_warning") }
        reminderFormWarning() { $(id: "reminderform:emailerr") }
        reminderFormCancel() { $("span.font_sml") }
        forgotUserNameLastName() { $(id: 'reminderform:lastname') }
        forgotUserNameSubmitButton() { $("input.sv_btn") }
        forgotUserNameEmail() { $(id: 'reminderform:email') }
        forgotUserNameClose() { $('.button').find('input') }
        trainingLink() { $(".last") }
        legalText(required: false) { $(id: "legal_left") }
        carelinkLogo(required: false) { $("h1", id: "carelink") }
        forgotPasswordLink { $("#user_log > form > dl > dd:nth-child(4) > a") }
        forgotPasswordName() { $(id: 'reminderform:username') }
        forgotPasswordEmail() { $(id: 'reminderform:email') }
        forgotPasswordSubmitButton() { $("#reminderform > div > input") }
        forgotPasswordUserName() { $(id: 'reminderform:username') }
        postponeLinkOnMFAScreen() { $(id: 'two-step-info').find('.left-align>a') }
        continueMFAScreen(required: false) { $(By.xpath("//div[@id='right-btn']/input[@type='submit']")) }
        emailFieldMFAScreen(required: false) { $(id: "email:newemail") }
        sendEmailNowButton(required: false) { $(id: "email:confirm-email") }
        verifyCodeField(required: false) { $(id: "code:input") }
        verifyCodeButton(required: false) { $(id: "code:verify") }
        helpButtonMFAScreen(required: false) { $(".help-btn > img:nth-child(1)") }
        cancelButtonMFAScreen(required: false) { $(By.xpath("//div[@class='left-align']/form/a")) }
        continueButtonMFAScreen(required: false) { $(".right-align") }
        newPasswordField(required: false) { $(id: "userform:password") }
        retypeNewPasswordField(required: false) { $(id: "userform:retype_password") }
        saveButtonPasswordExpired(required: false) { $(id: "userform:savebtn") }
    }

    void clinicSettingsHomePageClick() {
        sleep(1000)
        waitFor { browser.getCurrentUrl().contains("main/home.jsf") }
        waitFor { clinicSettingsHomePage.displayed }
        clinicSettingsHomePage.click()
    }

    List<PatientRowModule> getDisplayedPatients() {
        patientListTable.$('tr').moduleList(PatientRowModule)
    }

    void clickSelectCountryLanguage() {
        waitFor { $(id: "flag").displayed }
        $(id: "flag").click()
    }

    boolean signInButtonDisplayed() {
        waitFor { signinButton.displayed }
    }

    boolean isWelcomeTextDisplayed() {
        Assert.assertNull(welcomeText.text())
        true
    }

    boolean isIncompatibleTableDisplayed() {
        incompatiblityTable.displayed
    }

    boolean isContinueDisplayed() {
        continueButton.displayed
    }

    void clickOnContinue() {
        continueButton.click()
    }

    boolean unsupportedText1(String text) {
        driver.findElement(By.xpath('//*[@id="test"]/table/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/p[1]')).getText().contains(text)
    }

    boolean unsupportedText2(String text) {
        driver.findElement(By.xpath('//*[@id="test"]/table/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/p[2]')).getText().contains(text)
    }

    void unsupportedTextContent() {
        System.out.println(driver.findElement(By.xpath('//*[@id="test"]/table/tbody/tr[1]/td[2]/table/tbody/tr[2]/td/p[3]')).getText())
        System.out.println(incompatiblityTable.text())
        System.out.println(driver.findElement(By.xpath('//*[@id="test"]/table/tbody/tr[1]/td[2]/table/tbody/tr[6]/td/p[2]')).getText())
        System.out.println(driver.findElement(By.xpath('//*[@id="test"]/table/tbody/tr[1]/td[2]/table/tbody/tr[6]/td/p[3]')).getText())
    }

    void clickNewPatient() {
        sleep(4000)
        waitFor { newPatient.displayed }
        newPatient.click()
    }

    boolean buttonNewPatientDisplayed() {
        newPatient.displayed
    }


    void selectFirstPatient() {
        getDisplayedPatients().first().click()
    }

    void openPatient() {
        waitFor { openPatientButton.displayed }
        openPatientButton.click()
        waitFor { patientInfo.displayed }
    }

    boolean openPatientDisplayed() {
        waitFor { openPatientButton.displayed }
    }


    void closePatient() {
        closePatientButton.click()
    }

    void assertErrorMsg(boolean pageErrorMsgDisplayed) {
        pageErrorMsg.displayed == pageErrorMsgDisplayed
    }

    void assertPageErrorMsgContains(String msgPart) {
        pageErrorMsg.text().contains(msgPart)
    }

    void enterUsername(String username) {
        waitFor { usernameTextbox.displayed }
        usernameTextbox.value(username)
        if (usernameTextbox.value() != username) {
            def i = 1
            while (usernameTextbox.value() != username) {
                println(i + ' Enter LOGIN')
                sleep(500)
                usernameTextbox.value(username)
                i++
            }
        }
    }

    void enterPassword(String password) {
        waitFor { passwordTextbox.displayed }
        passwordTextbox.value(password)
    }

    void selectedFirstPatient() {
        driver.findElement(By.cssSelector("#firstNameTd0 > p")).click()
    }

    void login(String userName, String passWord) {
        waitFor {usernameTextbox.displayed}
        usernameTextbox.value(userName)
        passwordTextbox.value(passWord)
        signinButton.click()
    }

    void clickOnSignIn() {
        waitFor { signinButton.displayed }
        signinButton.click()
    }

    void searchForPatient(String patient) {
        searchPatient.value(patient)
        waitFor 3, {
            try {
                getDisplayedPatients()
                true
            } catch (StaleElementReferenceException staleElementEx) {
                false
            }
        }
    }

    void searchDisplayed() {
        assert searchPatient.displayed
    }


    boolean signoutButtonDisplayed() {
        waitFor { signoutButton.displayed }
    }

    void clickOnSignOutLink() {
        waitFor { signoutButton.displayed }
        signoutButton.click()
    }

    void verifyTitleButtonNewPatient() {
        assert titleButtonNewPatient.getAttribute("value") == ReadProperties.getProperties().get("home.patient.new")
    }

    void openPatientButtonNotActive() {
        $(By.id("list:openPatientBtn")).attr("disabled")
    }

    void checkIncludedFooterElements() {
        waitFor { footerSubmenu.displayed }
        if (langValue != "en") {
            $(id: "flag").click()
            waitFor { $(id: 'localeform:selectbtn').displayed }
            $(id: 'localeform:selectcountry').value('United States')
            $(id: 'localeform:selectbtn').click()
            waitFor { signinButton.displayed }
        }
        assert registerClinic.text() == ReadProperties.getProperties().get("login.enroll")
        assert termOfUse.text() == ReadProperties.getProperties().get("login.link.tou")
        assert privacyStatement.text() == ReadProperties.getProperties().get("login.link.pp")
        assert contactUs.text() == ReadProperties.getProperties().get("login.link.contact")
    }


    void passwordExpiredPage(String enterPass) {
        waitFor { userFormPassword.displayed }
        userFormPassword.value(enterPass)
        userFormRetypePassword.value(enterPass)
        userFormSave.click()
    }

    void checkGuiElements() {
        assert $(id: "carelink").displayed && $(id: "carelink").text() == "CareLinkTM" && $(id: "carelink").getAttribute("style").contains("text-align: center;")

        assert $("img", class: "mdtlogo").displayed
        assert $(id: "banner").find("img").getAttribute("src").contains("banner.jpg")
        assert $(id: "banner").find("img").getAttribute("alt") == "Medtronic Diabetes"
        assert $(id: "flag").displayed
        assert $(id: "location1").getAttribute("href").contains("/ipro/hcp/locale.jsf")
        assert $(id: "location").getAttribute("href").contains("/ipro/hcp/locale.jsf")
        assert $(id: "location").find("span").text().contains("Change country/")
        assert $(".mono_rev > a:nth-child(1)").text() == "Register Clinic"
        assert $("input", id: "j_username").displayed
        assert $("input", id: "j_password").displayed
        assert $("dt")*.text().getAt(0) == "Username"
        assert $("dt")*.text().getAt(1) == "Password"
        assert $("input", id: "signinbtn").displayed
        assert $("a", text: contains("Forgot"))*.displayed
        assert $("a", text: contains("Forgot"))*.getAttribute("href").getAt(0).contains("forgot-username.jsf")
        assert $("a", text: contains("Forgot"))*.getAttribute("href").getAt(1).contains("forgot-password.jsf")

        assert $("#j_username").attr("tabindex") == "1"
        assert $("#j_password").attr("tabindex") == "2"
        assert $("#signinbtn").attr("tabindex") == "3"

        assert $("#j_username").attr("type").equals("text")
        assert $("#j_password").attr("type").equals("password")
        assert $("#signinbtn").attr("type").equals("submit") && $("#signinbtn").attr("value").equals("Sign in")


        assert $("#footer_submenu > li:nth-child(1) > a:nth-child(1)").getAttribute("href").contains("userguides.html")
        assert registerClinic.displayed
        assert termOfUse.displayed
        assert privacyStatement.displayed
        assert contactUs.displayed

        def legalTxtUI = $(id: "legal_left").text().replaceAll("\n", "").replaceAll("[^\\x00-\\x7F]", "")
        def legalTxtFile = getClass().getClassLoader().getResourceAsStream("txtAgreements/legal_left.txt").text.replaceAll("\n|\r", "").replaceAll("[^\\x00-\\x7F]", "")
        assert legalTxtUI.contains(legalTxtFile)

        assert $(id: "legal_right").find("img").getAttribute("src").contains("CEmark.png")
        assert $(id: "legal_right").find("img").getAttribute("alt").equals("CEmark")
        assert $(id: "udi_div").displayed
        assert $(id: "reg_txt").displayed

        assert $(id: "reg_txt").find("p>img")*.getAttribute("src").getAt(0).contains("Location.png")
        assert $(id: "reg_txt").find("p")*.text().getAt(0) == "Medtronic MiniMed, 18000 Devonshire Street, Northridge CA 91325 USA"


        assert $(id: "reg_txt").find("p>img")*.getAttribute("src").getAt(1).contains("Model.png")
        assert $(id: "reg_txt").find("p")*.text().getAt(1) == "MMT-7340"

        assert $(id: "reg_txt").find("p>img")*.getAttribute("src").getAt(2).contains("YearOfManufacture.png")
        assert $(id: "reg_txt").find("p")*.text().getAt(2) == "2019"


    }

    void selectNonSamplePatientFromList() {
        def i = 0
        def lastName = $(id: "lastNameTd" + i).text()
        def firstName = $(id: "firstNameTd" + i).text()
        def listSize = $(id: 'patientList').find('tr').size()

        if (listSize > 1) {
            if ((lastName == 'Patient' && firstName == 'Sample M.') || (firstName == '---' && lastName == '---')) {
                i++
                $(id: "lastNameTd" + i).click()
            } else {
                $(id: "lastNameTd" + i).click()
            }
        } else {
            println('We have only one Sample M. Patient, scenario can not be continue')
        }
    }

    void userFormPasswordEnter(String pass) {
        waitFor { userFormPassword.displayed }
        userFormPassword.value(pass)
    }

    void userFormRetypePasswordEnter(String passRetype) {
        userFormRetypePassword.value(passRetype)
    }

    void userFormSaveClick() {
        userFormSave.click()
    }

    void waitForLoaderDisappear() {
        waitFor(20) { !$("div.shadowbox").displayed }
    }

    void checkAllFunctionsOnPage() {
        browser.withNewWindow({ $(".float_l > dd:nth-child(2) > a:nth-child(1)").click() }, wait: true) {
            //forgot username
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("forgot-username.jsf")
        }
        browser.withNewWindow({ $(".float_l > dd:nth-child(4) > a:nth-child(1)").click() }, wait: true) {
//forgot password
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("forgot-password.jsf")
        }
        browser.go($(id: "location").getAttribute("href"))
        waitFor { $(".header_er").displayed }
        assert browser.getCurrentUrl().contains("locale.jsf")
        driver.navigate().back()
        waitFor { $(id: "location").displayed }

        browser.withNewWindow({ $('.last > a:nth-child(1)').click() }, wait: true) {
            waitFor { title.contains("Medtronic") }
            assert browser.getCurrentUrl().contains("medtronicdiabetes")
        }

        browser.go(registerClinic.getAttribute("href"))
        waitFor { $(".mdtlogo").displayed }
        assert browser.getCurrentUrl().contains("enroll/info.jsf")
        driver.navigate().back()
        waitFor { registerClinic.displayed }

        browser.withNewWindow({ termOfUse.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("termsOfUse.jsf")
        }
        browser.withNewWindow({ privacyStatement.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("privacy.jsf")
        }
        browser.withNewWindow({ contactUs.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("contact.jsf")
        }
    }

    void checkLoginError(String text1, String text2, String text3) {
        waitFor { loginWarning.displayed }
        assert browser.getCurrentUrl().contains("/login-failed")
        assert loginWarning.text().contains(text1)
        assert loginWarning.text().contains(text2)
        assert loginWarning.text().contains(text3)
        assert $(".float_l").find("img").attr("src").contains("caution_yellow.png")
        waitFor { medtronicLogo.displayed }
        assert medtronicLogo.css('float') == 'left'
        usernameTextbox.attr("maxlength") == "16"
        passwordTextbox.attr("maxlength") == "20"
        if (envisionLogo.displayed) {
            assert envisionLogo.css('float') == 'right'
        } else {
            assert $("div.header_er > img.iprologo").displayed
        }
    }

    void forgotUserNamePositiveFlow(String lastName, String email) {
        browser.withNewWindow({ verifyForgotuserNameClick.click() }, wait: true, close: false) {
            waitFor { medtronicLogo.displayed }
            //position of elements
            assert medtronicLogo.css('float') == 'left'
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header > img.iprologo").displayed
            }
            assert $("#reminderform").has("h1")
            assert !$(di: 'legal_btm').displayed
            assert $("#reminderform > p:nth-child(3)").text() == ReadProperties.getProperties().get("username.forgot.text.1")
            assert $("#reminderform > p:nth-child(4)").text() == ReadProperties.getProperties().get("username.forgot.text.2")
            assert $("#reminderform > fieldset > label:nth-child(1) > span:nth-child(1)").text() == ReadProperties.getProperties().get("username.forgot.lastname")
            assert $("#reminderform > fieldset > label:nth-child(2) > span:nth-child(1)").text() == ReadProperties.getProperties().get("username.forgot.email")

            assert $("#reminderform > fieldset > label:nth-child(1) > span:nth-child(1)").css('float') == 'left'
            assert $("#reminderform > fieldset > label:nth-child(2) > span:nth-child(1)").css('float') == 'left'
            assert forgotUserNameSubmitButton.value() == ReadProperties.getProperties().get("button.submit")
            assert reminderFormCancel.displayed

            if (lastName.equals(" ") && email.equals(" ")) {
                if (driver.capabilities['browserName'].toString().contains("Safari")) {
                    browser.close()
                } else {
                    reminderFormCancel.click()
                }
                if (driver.capabilities['browserName'].toString() == 'internet explorer' || driver.capabilities['browserName'].toString() == 'Internet Explorer') {
                    sleep(2000)
                    driver.switchTo().alert().accept()
                }
            } else {
                waitFor { forgotUserNameLastName.displayed }
                forgotUserNameLastName.value(lastName)
                forgotUserNameEmail.value(email)
                forgotUserNameSubmitButton.click()

                waitFor { $('.main-contact').find('h2').displayed }
                assert medtronicLogo.css("float") == "left"
                assert envisionLogo.css("float") == "right"
                assert $('.main-contact').find('h2').text() == "Username reminder sent"
                assert $('.main-contact').find('p:nth-child(1)').text().contains("We have sent your username via email.")
                assert $('.main-contact').find('p:nth-child(1)').text().contains("It may take a few minutes to receive your reminder.")
                assert forgotUserNameClose.displayed
                assert forgotUserNameClose.value() == ReadProperties.getProperties().get("button.close")
                browser.close()
            }
        }
        assert title == ReadProperties.getProperties().get("login.title")
    }

    void forgotUserNameFlowNegative(String lastName, String email) {
        browser.withNewWindow({ verifyForgotuserNameClick.click() }, wait: true, close: false) {
            waitFor { medtronicLogo.displayed }
            //position of elements
            assert medtronicLogo.css('float') == 'left'
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header > img.iprologo").displayed
            }
            assert $("#reminderform").has("h1")
            assert !$(di: 'legal_btm').displayed
            assert $("#reminderform > p:nth-child(3)").text().contains(ReadProperties.getProperties().get("username.forgot.text.1"))
            assert $("#reminderform > p:nth-child(4)").text().contains(ReadProperties.getProperties().get("username.forgot.text.2"))
            assert $("#reminderform > fieldset > label:nth-child(1) > span:nth-child(1)").text() == ReadProperties.getProperties().get("username.forgot.lastname")
            assert $("#reminderform > fieldset > label:nth-child(2) > span:nth-child(1)").text() == ReadProperties.getProperties().get("username.forgot.email")

            assert $("#reminderform > fieldset > label:nth-child(1) > span:nth-child(1)").css('float') == 'left'
            assert $("#reminderform > fieldset > label:nth-child(2) > span:nth-child(1)").css('float') == 'left'
            assert forgotUserNameSubmitButton.value() == "Submit"
            assert reminderFormCancel.displayed

            forgotUserNameLastName.value(lastName)
            forgotUserNameEmail.value(email)
            forgotUserNameSubmitButton.click()
            sleep(1000)
            waitFor { reminderFormWarning.displayed }
            assert reminderFormWarning.text().contains("The information you provided does not match our records.")
            assert reminderFormWarning.text().contains("Please confirm spelling and be sure to use the email address associated with your account.")
            assert reminderFormWarning.text().contains("If you continue to have problems, please call")
            browser.close()
        }
        assert title == "iPro Login"
    }

    void forgotuserNameDisplayed() {
        waitFor { verifyForgotuserNameClick.displayed }
    }

    void forgotuserNameText(String text) {
        verifyForgotuserNameClick.text() == text
    }

    void verifyTrainingLink() {
        if (browser.getCurrentUrl().contains("login")) {
            assert trainingLink.displayed
            def urlTraining = trainingLink.find("a").getAttribute('href')
            browser.go(urlTraining)
            waitFor { browser.getCurrentUrl().contains("medtronicdiabetes") }
            waitFor { $(id: "logoimg").displayed }
            assert $("#block-multiblock-9 > div:nth-child(1) > ul:nth-child(1) > li:nth-child(5) > a:nth-child(1)").displayed
            assert $("#block-multiblock-9 > div:nth-child(1) > ul:nth-child(1) > li:nth-child(5) > a:nth-child(1)").text().contains('iPro™2')
            assert $("#block-multiblock-9 > div:nth-child(1) > ul:nth-child(1) > li:nth-child(5) > a:nth-child(1)").text().contains('Online Training Modules')
            driver.navigate().back()
            waitFor { browser.getCurrentUrl().contains("login") }
            waitFor { trainingLink.displayed }
            assert title == "iPro Login"
        } else {
            assert $(".btm_info > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)").displayed
            def urlTraining2 = $(".btm_info > ul:nth-child(1) > li:nth-child(2) > a:nth-child(1)").getAttribute('href')
            browser.go(urlTraining2)
            waitFor { browser.getCurrentUrl().contains("medtronicdiabetes") }
            waitFor { $(id: "logoimg").displayed }
            assert $("#block-multiblock-9 > div:nth-child(1) > ul:nth-child(1) > li:nth-child(5) > a:nth-child(1)").displayed
            assert $("#block-multiblock-9 > div:nth-child(1) > ul:nth-child(1) > li:nth-child(5) > a:nth-child(1)").text().contains("iPro™2 Online Training Modules")
            driver.navigate().back()
            waitFor { browser.getCurrentUrl().contains("hcp/main/home.jsf") }
            assert title == "Select a Patient"
        }
    }

    void verifyUDI(String s) {
        assert $(id: "udi_div").displayed
        println("UDI: " + $(id: "udi").text())
        assert $(id: "udi").text().contains(s)
    }

    void sessionTimeout() {
        driver.navigate().refresh()
        waitFor { browser.getCurrentUrl().contains("hcp/session-timeout.jsf") }
        waitFor { medtronicLogo.displayed }
        assert medtronicLogo.css('float') == 'left'
        if (envisionLogo.displayed) {
            assert envisionLogo.css('float') == 'right'
        } else {
            assert $("div.header_er > img.iprologo").displayed
        }
        assert $(".login-contact > h1:nth-child(1)").text().contains("Session Timed Out")
        assert loginWarning.text().contains("Your CareLink session has expired.")
        assert loginWarning.text().contains("For your security, CareLink sessions automatically expire after 20 minutes of inactivity.")
        assert loginWarning.text().contains("Enter your username and password to sign in.")
    }

    void homeClick() {
        waitFor { $("#footer_submenu_r > li:nth-child(1) > a").displayed }
        assert $("#footer_submenu_r > li:nth-child(1) > a").text() == "Home"
        $("#footer_submenu_r > li:nth-child(1) > a").click()
        waitFor { title == ReadProperties.getProperties().get("login.title") }
    }

    void checkJapanDisclaimer() {
        assert $("#banner > a:nth-child(1) > img:nth-child(1)").getAttribute("src").contains("JP/banner.jpg")
        assert !$(text: "training").displayed
        assert $(id: "flag").getAttribute("alt") == "JP"
    }

    void clickRegisterClinic() {
        waitFor { registerClinic.displayed }
        registerClinic.click()
        waitFor { browser.getCurrentUrl().contains("enroll/info.jsf") }
    }

    void verifyLegalText() {
        waitFor { legalText.displayed }
        assert $(id: "legal_left").text().contains(ReadProperties.getProperties().get("ip.login.text.legalease.1"))
        assert $(id: "legal_left").text().contains(ReadProperties.getProperties().get("ip.login.text.legalease.2"))
        assert $(id: "legal_left").text().contains(ReadProperties.getProperties().get("ip.login.text.legalease.3"))
    }

    void verifyLogo() {
        waitFor { medtronicLogo.displayed }
        assert medtronicLogo.css('float') == 'left'
        if (carelinkLogo.displayed) {
            $(By.xpath("//h1[@id='carelink'][contains(@style,' center')]")).displayed
        } else {
            assert $("#main > h3 > img").displayed
        }
    }

    void verifyLogoForgotUsernamePage() {
        browser.withNewWindow({ verifyForgotuserNameClick.click() }, wait: true, close: false) {
            waitFor { forgotUserNameLastName.displayed }
            waitFor { forgotUserNameEmail.displayed }
            waitFor { medtronicLogo.displayed }
            assert medtronicLogo.css('float') == 'left'
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header_er > img.iprologo").displayed
            }
            assert $("#reminderform > p:nth-child(3)").displayed
            assert $("#reminderform > p:nth-child(3)").text() == ReadProperties.getProperties().get("username.forgot.text.1")
            assert $("#reminderform > p:nth-child(4)").displayed
            assert $("#reminderform > p:nth-child(4)").text() == ReadProperties.getProperties().get("username.forgot.text.2")

            if (driver.capabilities['browserName'].toString().contains("Safari")) {
                browser.close()
            } else {
                $("#reminderform > div > a > span").click()
            }

            if (driver.capabilities['browserName'].toString() == 'internet explorer' || driver.capabilities['browserName'].toString() == 'Internet Explorer') {
                sleep(3000)
                driver.switchTo().alert().accept()
            }
        }
    }

    void verifyLogoForgotPasswordPage() {
        browser.withNewWindow({ forgotPasswordLink.click() }, wait: true, close: false) {
            waitFor { forgotPasswordName.displayed }
            waitFor { forgotPasswordEmail.displayed }
            waitFor { medtronicLogo.displayed }
            assert medtronicLogo.css('float') == 'left'
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header_er > img.iprologo").displayed
            }

            assert $("#reminderform").has("h1")
            assert !$(di: 'legal_btm').displayed
            assert $("#reminderform > p:nth-child(3)").text() == ReadProperties.getProperties().get("password.forgot.text.1")
            assert $("#reminderform > p:nth-child(4)").text() == com.medtronic.ndt.carelink.data.ReadProperties.getProperties().get("password.forgot.text.2")
            assert $("#reminderform > fieldset > label:nth-child(1) > span:nth-child(1)").text() == ReadProperties.getProperties().get("password.forgot.username")
            assert $("#reminderform > fieldset > label:nth-child(2) > span:nth-child(1)").text() == ReadProperties.getProperties().get("password.forgot.email")
            assert $("#reminderform > fieldset > label:nth-child(1) > span:nth-child(1)").css('float') == 'left'
            assert $("#reminderform > fieldset > label:nth-child(2) > span:nth-child(1)").css('float') == 'left'
            assert forgotPasswordSubmitButton.value() == ReadProperties.getProperties().get("button.submit")
            assert reminderFormCancel.displayed
            assert forgotPasswordUserName.displayed
            assert forgotUserNameEmail.displayed
            browser.close()
        }
    }

    void newPasswordScreen(String titleP, String paragraph) {
        waitFor { browser.getCurrentUrl().contains('password') }
        waitFor { $('form', id: 'userform').displayed }
        assert browser.title == titleP
        assert $('form', id: 'userform').find('h1').text() == paragraph
    }

    void enterPasswordConfirm(String passWord) {
        $(id: 'userform:password').value(passWord)
        $(id: 'userform:passwordConfirm').value(passWord)
        $(id: 'userform:continue').click()
    }

    void navigateBack() {
        driver.navigate().back()
        driver.navigate().refresh()
        waitFor { !$("#loading").displayed }
        assert !searchPatient.displayed || !patientInfo.displayed || !openPatientButton.displayed
    }

    void clickEnterOnKeyboard() {
        signinButton << Keys.chord(Keys.ENTER)
        waitFor { loginWarning.displayed }
    }

    void verifyAbleSimultaneouslyLogin(username, password) {
//        DesiredCapabilities capabilities = DesiredCapabilities.firefox()
//        capabilities.setCapability("browser.privatebrowsing.autostart", true)
//        WebDriver driver = new FirefoxDriver(capabilities)
//        driver = browser.go("https://build15.ols.minimed.com/ipro/hcp/login.jsf?bhcp=1")
//
//        waitFor { $(id: "signinbtn").displayed }
//        usernameTextbox = username
//        passwordTextbox = password
//        signinButton.click()
//        sleep(2000)
//        if (browser.getCurrentUrl().contains('/mfa.jsf')) {
//            waitFor { $(id: 'two-step-info').displayed }
//            while (browser.getCurrentUrl().contains('/mfa.jsf') && postponeLinkOnMFAScreen.displayed) {
//                postponeLinkOnMFAScreen.click()
//                sleep(2000)
//            }
//        }
//        waitFor { browser.getCurrentUrl().contains("home.jsf") }
//        driver.quit()
    }

    void postponeMFA() {
        sleep(2000)
        waitFor { !browser.getCurrentUrl().contains('/login.jsf') }
        sleep(2000)
        if (browser.getCurrentUrl().contains('/mfa.jsf')) {
            waitFor { $(id: 'two-step-info').displayed }
            while (browser.getCurrentUrl().contains('/mfa.jsf')) {
                $(id: 'two-step-info').find('.left-align>a').click()
                sleep(5000)
            }
        }
    }

    void forgotPasswordFlowNegative(String userName, String email) {
        browser.withNewWindow({ forgotPasswordLink().click() }, wait: true, close: true) {
            waitFor { medtronicLogo.displayed }
            forgotPasswordUserName.attr("maxlength") == "16"
            forgotUserNameEmail.attr("maxlength") == "60"
            forgotPasswordUserName.value(userName)
            forgotUserNameEmail.value(email)
            forgotPasswordSubmitButton.click()
            sleep(2000)

            waitFor { reminderFormWarning.displayed }
            assert reminderFormWarning.text().contains("The information you provided does not match our records.")
            assert reminderFormWarning.text().contains("Please confirm spelling and be sure to use the email address associated with your account.")
            assert reminderFormWarning.text().contains("If you continue to have problems, please call")
        }
        assert title == ReadProperties.getProperties().get("login.title")

    }

    void forgotPasswordFlowPositiveFlow(String username, String email) {
        browser.withNewWindow({ forgotPasswordLink().click() }, wait: true, close: false) {
            waitFor { medtronicLogo.displayed }
            forgotPasswordUserName.value(username)
            forgotUserNameEmail.value(email)
            forgotPasswordSubmitButton.click()
            sleep(3000)
            waitFor { $(".er_content").displayed }
            assert $(".er_content").text().contains("We have sent your username via email. It may take a few minutes to receive your reminder.")
            assert $(".er_content").text().contains("It may take a few minutes to receive your reminder.")
            browser.close()
        }
        assert title == ReadProperties.getProperties().get("login.title")
    }

    void verifyUserNameTextBox() {
        assert usernameTextbox.attr("maxlength") == "16"
    }

    void openPatientInformation() {
        patientInfo.click()
        waitFor { browser.currentUrl.contains('ipro/hcp/main/patient/record.jsf') }
    }

    void forgotUserNameFlowNegativeDifferentCountry(String lastName, String email, String phone) {
        browser.withNewWindow({ verifyForgotuserNameClick.click() }, wait: true, close: false) {
            waitFor { medtronicLogo.displayed }
            waitFor { reminderFormCancel.displayed }

            forgotUserNameLastName.value(lastName)
            forgotUserNameEmail.value(email)
            forgotUserNameSubmitButton.click()
            waitFor { reminderFormWarning.displayed }
            assert reminderFormWarning.text().contains(phone)
            reminderFormCancel.click()
            if (driver.capabilities['browserName'].toString() == 'internet explorer' || driver.capabilities['browserName'].toString() == 'Internet Explorer') {
                sleep(2000)
                driver.switchTo().alert().accept()
            }
        }
    }

    void getMFAVerifyCode(email) {
        waitFor { continueMFAScreen.displayed }
        continueMFAScreen.click()
        waitFor { emailFieldMFAScreen.displayed }
        assert emailFieldMFAScreen.value() == ''
        emailFieldMFAScreen.value(email)
        waitFor { sendEmailNowButton.displayed }
        sendEmailNowButton.click()
    }

    void verifyCodeMFAFlow(code) {
        waitFor { verifyCodeField.displayed }
        verifyCodeField.value(code)
        verifyCodeButton.click()
    }

    void verifyMFAEmailSetupScreen() {
        waitFor { browser.getCurrentUrl().contains("mfa-select-method.jsf") }
        waitFor { medtronicLogo.displayed }
        if (envisionLogo.displayed) {
            assert envisionLogo.css('float') == 'right'
        } else {
            assert $("div.header_er > img.iprologo").displayed
        }
        assert $("#footer_submenu > li:nth-child(1) > a:nth-child(1)").getAttribute("href").contains("userguides.html")
        assert termOfUse.displayed
        assert privacyStatement.displayed
        assert contactUs.displayed
        assert $(".main-title > h2:nth-child(1)").text() == "Two-Factor Authentication Required"
        assert $(id: "step2-email").text().contains("You will receive a code at this email address:")
        assert helpButtonMFAScreen.displayed
        browser.withNewWindow({ helpButtonMFAScreen.click() }, wait: true, close: true) {
            waitFor { browser.getCurrentUrl().contains("mfa-help.jsf") }
            waitFor { $("body").displayed }
        }
        browser.getCurrentUrl().contains("mfa-select-method.jsf")
    }

    void clickCancelMFAScreen() {
        waitFor { cancelButtonMFAScreen.displayed }
        cancelButtonMFAScreen.click()
        waitFor { $(id: "two-step-cancel row").displayed }
    }

    void clickContinueMFAScreen() {
        waitFor { continueButtonMFAScreen.displayed }
        continueButtonMFAScreen.click()
    }

    void sendEmailNowFlowMFAScreen() {
        waitFor { browser.getCurrentUrl().contains("mfa-select-method.jsf") }
        driver.navigate().refresh()
        waitFor { !$("#loading").displayed }
        waitFor { $("h2.row").displayed }
        assert $("h2.row").text().contains("eXXXXXXXXXXXXXXXXXXXmedtronictest.mailinator.com")
        $(By.xpath("//div/input[@type='submit']")).click()
    }

    void verifyPasswordExpired(String s, String s2, String s3) {
        waitFor { browser.getCurrentUrl().contains("password-expired.jsf") }
        waitFor { medtronicLogo.displayed }
        if (!envisionLogo.displayed) {
            waitFor { $("div.header_er > img.iprologo").displayed }
        }
        assert $("div.main-contact > div.login-contact > h1").text().contains("Password Expired")
        assert $("#footer_submenu > li:nth-child(1) > a:nth-child(1)").getAttribute("href").contains("userguides.html")
        assert termOfUse.displayed
        assert privacyStatement.displayed
        assert contactUs.displayed
        assert $("#footer_submenu_r > li:nth-child(1) > a").displayed
        assert $("div.main-contact > div.login-contact > div.login_warning").text().contains(s)
        assert $("div.main-contact > div.login-contact > div.login_warning").text().contains(s2)
        assert $("div.main-contact > div.login-contact > div.login_warning").text().contains(s3)
        assert $("#userform > fieldset > dl > dt:nth-child(1)").text() == "New Password"
        assert $("#userform > fieldset > dl > dt:nth-child(3)").text() == "Retype New Password"
    }

    void verifyPasswordExpiredFields(String newPassword, String retypePassword, String error, String retypeError) {
        newPasswordField.value(newPassword)
        retypeNewPasswordField.value(retypePassword)
        saveButtonPasswordExpired.click()
        sleep(5000)
        if ($(id: "userform:passworderror").displayed) {
            assert $(id: "userform:passworderror").text().contains(error)
        }
        waitFor {$(id: "userform:passwordConfirmerror").displayed}
        assert $(id: "userform:passwordConfirmerror").text().contains(retypeError)
    }

    void addNewPasswordExpiredFlow(String newPass) {
        sleep(1500)
        newPasswordField.value(newPass)
        retypeNewPasswordField.value(newPass)
        saveButtonPasswordExpired.click()
    }
    void verifyDateOfBirth(String dateOfBirth) {
        waitFor {$('#firstNameTd0').displayed}
        sleep(3000)
        assert $('#dobTd0 > p').text().contains(dateOfBirth)
    }
}