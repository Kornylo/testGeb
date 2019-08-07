package com.medtronic.ndt.carelink.pages

import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.util.Validation
import geb.Page
import org.openqa.selenium.By

class ContactUsPage extends Page {


    static at = {
        continueButton.displayed || signinButton.displayed || submitButton.displayed || cancelButton.displayed || newPatient.displayed
    }
    static content = {
        newPatient { $ "input", id: "list:createBtn" }
        continueButton(required: false) { $("input", id: "test:study") }
        submitButton(required: false) { $("input.sv_btn") }
        bannerImage(required: false) { $("div", id: "banner") }

        signinButton(required: false) { $("input", id: "signinbtn") }
        // Google chrome
        passConfigurationPage(required: false) { $("input", id: "test:study") }
        /* contact us */
        reminderUsername(required: false) { $('a[href$="/ipro/hcp/forgot-username.jsf"]') }
        conactUsText(required: false) { $("p") }
        contactUsPageTitle(required: false) { $(id: "contact_us") }
        //required labe
        requiredLabe(required: false) { $("div.float_r") }
        //  Add text in contact form
        contactUsURLButton(required: false) { $('a[href$="/ipro/hcp/contact.jsf"]') }
        // Add text
        contactUsAddText(required: false) { $(id: "contact_us:details") }
        // Submit button
        contactUsSubmitButton(required: false) { $(id: "contact_us:submit") }
        // Contact us
        conactUsSuccessSendTitle(required: false) { $("h2") }
        conactUsSuccessSendText(required: false) { $("div.er_content") }
        contactUsAddUsername(required: false) { $(id: "contact_us:username") }
        contactUsAddClinicname(required: false) { $(id: "contact_us:clinicname") }
        contactUsAddFirstname(required: false) { $(id: "contact_us:firstname") }
        contactUsAddLastname(required: false) { $(id: "contact_us:lastname") }
        contactUsAddEmail(required: false) { $(id: "contact_us:email") }
        contactUsAddPhone(required: false) { $(id: "contact_us:phone") }
        conactUsErrorMES(required: false) { $("p") }
        contactUsPageClose(required: false) { $("span.font_sml") }
        // Home page
        homeScreenNavigation(required: false) { $ "div.nav" }
        homeScreenMain(required: false) { $ "div.box_top_mid.box_top_mid" }
        homeScreenFotter(required: false) { $ "div.btm_info" }
        HhmeScreenLogOupButton(required: false) { $("a", id: "list:logout") }
        clinicName(required: false) { $ "input", id: "enroll_clinic:clinicname" }
        userName(required: false) { $ "input", id: "userform:username" }
        privacyPolicy1(required: false) { $("#tou_form > div > div.scroll_content > p:nth-child(14) > a") }
        contactUsFromPrivacyPolicy(required: false) {
            $(".scroll_content > center:nth-child(38) > a:nth-child(5)")
        }
        privacyStatementHeader(required: false) { $("div", class: "header_er") }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
        privacyStatement(required: false) { $("a", text: "Privacy Statement") }
        footerSubmenu(required: false) { $("ul", id: "footer_submenu_r") }
        contactUs(required: false) { $(".mono > a:nth-child(1)") }
        cancelButton(required: false) { $(".font_sml") }
        contactUsPatientList(required: false) { $(".copyright > li:nth-child(3) > a:nth-child(1)") }
        contactUsDetails(required: false) { $(id: "contact_us:details") }
        submittedContent(required: false) { $(".er_content") }
    }
    /*         contact us                  */

    boolean contactUsPageCloseClick() {
        contactUsPageClose.click()

    }

    boolean homeScreenLogOupButtonClick() {
        HhmeScreenLogOupButton.click()
    }

    boolean homeScreenNavigationIsDisplayed() {
        homeScreenNavigation.isDisplayed()
    }

    boolean homeScreenMainIsDisplayed() {
        homeScreenMain.isDisplayed()
    }

    boolean homeScreenFotterDisplayed() {
        homeScreenFotter.isDisplayed()
    }

    void conactUsErrorMESDisplayed(String msgPart) {
        conactUsErrorMES.text().contains(msgPart)
    }

    boolean contactUsButtonIsDisplayed() {
        contactUsSubmitButton.isDisplayed()
    }

    boolean contactUsButtonClick() {
        contactUsSubmitButton.click()
    }

    boolean contactUsURLButtonClick() {
        contactUsURLButton.click()

    }

    boolean contactUsForm() {
        contactUsAddPhone.displayed
    }

    boolean contactUsPhoneDisplayed() {
        contactUsAddPhone.displayed
    }

    boolean requiredLabeDisplayed() {
        requiredLabe.displayed
    }
    // title on contact page
    boolean contactPageTitleDispalyed() {
        contactUsPageTitle.displayed
    }

    void conactUsTextDisplayed(String msgPart) {
        conactUsText.text().contains(msgPart)
    }

    boolean conactUsSuccessSendTitleDisplayed(String msgPart) {
        conactUsSuccessSendTitle.text().contains(msgPart)
    }

    boolean conactUsSuccessSendTextDisplayed(String msgPart) {
        conactUsSuccessSendText.text().contains(msgPart)
    }

    private void clickToClearForm() {
        driver.findElement(By.id("contact_us:details")).clear()
    }

    void contactUsAddTextAdded(String contactUsText) {
        contactUsAddText = contactUsText
    }

    private void contactUsButtonCloseClick() {
        driver.findElement(By.name("j_id_id7:j_id_id11")).click()
    }

    boolean goToContactUsPage() {
        driver.get("https://build16.ols.minimed.com/ipro/hcp/contact.jsf")
    }

    void enterContactUsUsername(String contactUsUsername) {
        contactUsAddUsername = contactUsUsername
    }

    void enterContactUsClinicname(String contactUsClinicname) {
        contactUsAddClinicname = contactUsClinicname
    }

    void enterContactUsLastname(String contactUsLastname) {
        contactUsAddLastname = contactUsLastname
    }

    void enterContactUsFirstname(String contactUsFirstname) {
        contactUsAddFirstname = contactUsFirstname
    }

    void enterContactUsEmail(String contactUsEmail) {
        contactUsAddEmail = contactUsEmail
    }

    void enterContactUsPhone(String contactUsPhone) {
        contactUsAddPhone = contactUsPhone
    }

    boolean passConfigurationPageClick() {
        passConfigurationPage.click()
    }

    boolean isBannerImageDisplayed() {
        bannerImage.isDisplayed()
    }

    void enterClinicName(String name) {
        clinicName = name
    }

    void enterUserName(String username) {
        userName = username
    }

    void checkContactUsFromPrivacyStatement() {
        browser.withNewWindow({ privacyStatement.click() }, wait: true) {
            waitFor { contactUsFromPrivacyPolicy.displayed }
            assert title == "Privacy Policy"
            assert contactUsFromPrivacyPolicy.getAttribute("href").contains("contact")
            assert contactUsFromPrivacyPolicy.text() == "Contact Us"

            browser.withNewWindow({ contactUsFromPrivacyPolicy.click() }, wait: true) {
                sleep(2000)
                waitFor { browser.getCurrentUrl().contains("/help/contact") }
                assert title.contains("Medtronicdiabetes")
            }

            waitFor { privacyStatementHeader.displayed }
            waitFor { medtronicLogo.displayed }
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header_er > img.iprologo").displayed
            }
            assert browser.getCurrentUrl().contains("ipro/hcp/privacy.jsf")
            assert title == "Privacy Policy"
        }
    }

    void openContactUsAndVerifyUiContent() {
        def signinUrl = browser.getCurrentUrl()
        println(signinUrl)
        def url = contactUs.getAttribute('href')
        browser.go(url)
        waitFor { browser.currentUrl.contains("ipro/hcp/contact.jsf") }
        waitFor { medtronicLogo.displayed }

        assert envisionLogo.displayed
        assert cancelButton.displayed
        assert cancelButton.text() == "Cancel"
        assert footerSubmenu != true

        assert contactUsAddUsername.text().isEmpty()
        assert contactUsAddClinicname.text().isEmpty()
        assert contactUsAddFirstname.text().isEmpty()
        assert contactUsAddLastname.text().isEmpty()
        assert contactUsAddEmail.text().isEmpty()
        assert contactUsAddPhone.text().isEmpty()
        assert contactUsAddText.text() == 'Click here to enter details...'

        contactUsAddUsername.value("Username")
        if (contactUsAddUsername.text().isEmpty()) {
            sleep(3000)
            contactUsAddUsername.value("Username")
        }
        contactUsAddClinicname.value("ClinicName")
        contactUsAddFirstname.value("FirstName")
        contactUsAddLastname.value("LastName")
        contactUsAddEmail.value("Email")
        contactUsAddPhone.value("1234567890")
        contactUsAddText.value("Text")

        assert contactUsAddUsername.value() == "Username"
        assert contactUsAddClinicname.value() == "ClinicName"
        assert contactUsAddFirstname.value() == "FirstName"
        assert contactUsAddLastname.value() == "LastName"
        assert contactUsAddEmail.value() == "Email"
        assert contactUsAddPhone.value() == "1234567890"
        assert contactUsAddText.value() == "Text"

        cancelButton.click()
        if (driver.capabilities['browserName'].toString() == 'internet explorer' || driver.capabilities['browserName'].toString() == 'Internet Explorer') {
            sleep(1000)
            driver.switchTo().alert().dismiss()
        }
        driver.navigate().back()
        driver.navigate().back()
        if (driver.capabilities['browserName'].toString() == 'Internet Explorer') {
            sleep(1000)
            browser.go(signinUrl)
            waitFor { browser.currentUrl.contains("login") }
        }
    }

    void openContactUsAndVerifyUiContentForClinic(String username, String firstName, String lastName, String email, String phone) {
        waitFor { contactUsPatientList.displayed }
        browser.withNewWindow({ contactUsPatientList.click() }, wait: true, close: true) {
            waitFor { browser.currentUrl.contains("ipro/hcp/contact.jsf") }
            waitFor { contactUsPageTitle.displayed }
            assert !contactUsAddUsername.displayed && !contactUsAddClinicname.displayed && !contactUsAddFirstname.displayed && !contactUsAddLastname.displayed && !contactUsAddEmail.displayed
            assert $("fieldset:nth-child(4) > label:nth-child(2) > span:nth-child(2)").text() == username.toLowerCase()
            assert $("fieldset:nth-child(4) > label:nth-child(4) > span:nth-child(2)").text() == firstName
            assert $("fieldset:nth-child(4) > label:nth-child(5) > span:nth-child(2)").text() == lastName
            assert $("fieldset:nth-child(4) > label:nth-child(6) > span:nth-child(2) > b:nth-child(1)").text() == email
            assert $(id: "contact_us:phone").displayed
            assert $(id: "contact_us:phone").value() == phone

            assert footerSubmenu != true
            assert browser.title == ReadProperties.getProperties().get('login.link.contact')
            assert contactUsPageTitle.find("p").text() == ReadProperties.getProperties().get('contact.text')
            assert $("fieldset.detail > label > span:nth-child(1)").text() == ReadProperties.getProperties().get('contact.details') + '*'
            assert contactUsDetails.text() == ReadProperties.getProperties().get('contact.details.text')
            assert contactUsSubmitButton.value() == ReadProperties.getProperties().get('button.submit')
            assert contactUsSubmitButton.attr('disabled')

            contactUsDetails.value("Text")
            println("Text")
            assert !contactUsSubmitButton.attr('disabled')
            assert contactUsDetails.value() == "Text"
            contactUsSubmitButton.click()
            waitFor { browser.page(Validation).pageComplete() }
            waitFor { title == ReadProperties.getProperties().get("contact.finished.title") }
            waitFor { $(type: "submit").displayed }
            assert $('.main-contact').find("h2").text() == ReadProperties.getProperties().get("contact.finished.h1")
            assert $('.er_content > p:nth-child(1)').text() == ReadProperties.getProperties().get("contact.finished.text.1")
            assert $('.er_content > p:nth-child(2)').text() == ReadProperties.getProperties().get("contact.finished.text.2")
            assert $('.button > input:nth-child(1)').displayed
        }
    }

    void verifyLogoCloseButton() {
        browser.withNewWindow({ contactUs.click() }, wait: true, close: false) {
            waitFor { medtronicLogo.displayed }
            assert medtronicLogo.css('float') == 'left'
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header_er > img.iprologo").displayed
            }
            waitFor { cancelButton.displayed }

            if (driver.capabilities['browserName'].toString().contains("Safari")) {
                browser.close()
            } else {
                cancelButton.click()
            }

            if (driver.capabilities['browserName'].toString() == 'internet explorer') {
                sleep(3000)
                driver.switchTo().alert().accept()
            }
        }
        waitFor { title == "iPro Login" }
    }

    void openContactUsLoginScreen(String email) {
        browser.withNewWindow({ contactUs.click() }, wait: true, close: true) {
            waitFor { browser.currentUrl.contains("ipro/hcp/contact.jsf") }
            waitFor { medtronicLogo.displayed }

            assert contactUsAddUsername.text().isEmpty()
            assert contactUsAddClinicname.text().isEmpty()
            assert contactUsAddFirstname.text().isEmpty()
            assert contactUsAddLastname.text().isEmpty()
            assert contactUsAddEmail.text().isEmpty()
            assert contactUsAddPhone.text().isEmpty()
            assert contactUsAddText.text() == ReadProperties.getProperties().get('contact.details.text')

            contactUsAddUsername.value("Username")
            contactUsAddClinicname.value("ClinicName")
            contactUsAddFirstname.value("FirstName")
            contactUsAddLastname.value("LastName")
            contactUsAddEmail.value(email)
            contactUsAddPhone.value("1234567890")
            contactUsAddText.value("Text")

            assert contactUsAddUsername.value() == "Username"
            assert contactUsAddClinicname.value() == "ClinicName"
            assert contactUsAddFirstname.value() == "FirstName"
            assert contactUsAddLastname.value() == "LastName"
            assert contactUsAddEmail.value() == email
            assert contactUsAddPhone.value() == "1234567890"
            assert contactUsAddText.value() == "Text"

            assert !contactUsSubmitButton.attr('disabled')
            contactUsSubmitButton.click()
            waitFor { browser.page(Validation).pageComplete() }
            waitFor { title == ReadProperties.getProperties().get("contact.finished.title") }
            waitFor { $(type: "submit").displayed }
            assert $('.main-contact').find("h2").text() == ReadProperties.getProperties().get("contact.finished.h1")
            assert $('.er_content > p:nth-child(1)').text() == ReadProperties.getProperties().get("contact.finished.text.1")
            assert $('.er_content > p:nth-child(2)').text() == ReadProperties.getProperties().get("contact.finished.text.2")
            assert $(type: "submit").value() == ReadProperties.getProperties().get("button.close")
        }
    }
}
