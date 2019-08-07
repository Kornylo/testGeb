package com.medtronic.ndt.carelink.pages.clinicwizard

import com.medtronic.ndt.carelink.data.ReadProperties
import geb.Page
import org.openqa.selenium.By

class ClinicEULAPage extends Page {

    static at = { residentCheckbox.displayed || signinButton.displayed || continueBtn.displayed }
    static content = {
        continueBtn(required: false) { $("input", id: "localeform:selectbtn") }
        signinButton(required: false) { $("input", id: "signinbtn") }
        acceptBtn(required: false) { $("input", id: "mainform:acceptbtn") }
        residentCheckbox(required: false) { $("input", id: "challenge1") }
        readAndAcceptCheckbox(required: false) { $("input", id: "challenge2") }
        declineButton(required: false) { $(class: "font_sml") }
        continueBtn(required: false) { $ "input", id: "localeform:selectbtn" }
        country(required: false) { $('span.country') }
        changeLink(required: false) {
            $("div.er_content > p:nth-child(2) > a")
        }
    }

    void waitContinueBtn() {
        waitFor 15, { continueBtn.displayed }
    }

    void clickResident() {
        waitFor 25,{ residentCheckbox.displayed }
        residentCheckbox.click()
    }

    void clickReadAndAccept() {
        waitFor 25,{ readAndAcceptCheckbox.displayed }
        readAndAcceptCheckbox.click()
    }

    void clickAccept() {
        waitFor 25,{ acceptBtn.displayed }
        acceptBtn.click()
        sleep(3000)
        while (browser.getCurrentUrl().contains('enroll/locale.jsf')){
            acceptBtn.click()
            sleep(3000)
        }
        waitFor { browser.getCurrentUrl().contains("enroll/acknowledgement.jsf") }
    }

    void clickChangeCountry() {
        waitFor {$('#mainform > div > div.er_content > p:nth-child(2) > a').displayed}
        $('#mainform > div > div.er_content > p:nth-child(2) > a').click()
    }


    void buttonDeclineClick() {
        declineButton.click()
    }


    void enrollmentInfoScreen() {
        waitFor { $(".mdtlogo").displayed }
        sleep(3000)
        assert browser.getCurrentUrl().contains("enroll/info.jsf")
    }

    void opensEnrollmentTermsAcceptanceScreen() {
        waitFor { residentCheckbox.displayed } && waitFor { readAndAcceptCheckbox.displayed }
        assert browser.getCurrentUrl().contains("enroll/locale.jsf")
        assert $(".step>img")*.getAttribute("src").getAt(2).contains("step_3_off.gif")
        assert $(".step>img")*.getAttribute("alt").getAt(2) == "Step 3"
        assert acceptBtn.value() == "Accept"
    }

    void verifyEnrollmentTermsAcceptanceScreen() {
        assert title.contains("Acknowledgement Page")
        assert $(".country").text().equals("United States")
        assert $(".er_content").find("p>img").getAttribute("src").contains("flags/us.png")
        assert $(".er_content").find("p").getAt(0).text().contains("Please remember that our Terms of Use and Privacy Statement govern your use of and submissions to this site.")
        assert $(".er_content").find("p").getAt(0).text().contains("You must agree to all of the Terms of Use and Privacy Statement in order to use this site.")
        assert $(".er_content").find("p").getAt(0).text().contains("By submitting this form you confirm your agreement with the provisions of the Terms of Use and Privacy Statement.")
        assert $(".er_content > h2:nth-child(3)").text() == "Terms of Use"
        assert $("#accept > p:nth-child(2) > label:nth-child(1)").text() == "I have read, understood, and accept the Terms of Use and Privacy Statement."
        assert $("div.scroll_content2:nth-child(4) > p:nth-child(3)").text().contains("These Terms of Use were last updated on November 1, 2009.")
        assert $("div.scroll_content2:nth-child(4) > p:nth-child(3)").text().contains("Medtronic may change these Terms of Use at any time.")
        assert $("div.scroll_content2:nth-child(4) > p:nth-child(3)").text().contains("Please review the Terms of Use each time you visit the web site.")
        assert $("div.scroll_content2:nth-child(4) > p:nth-child(3)").text().contains("By using this web site, it means you accept the most recent version of the Terms of Use.")
        assert $(".er_content > h2:nth-child(5)").text() == "Privacy Policy"
        assert $("div.scroll_content2:nth-child(6) > p:nth-child(1)").text().contains("Welcome to the Medtronic Diabetes CareLink web site, owned and operated by Medtronic.")
        assert $("div.scroll_content2:nth-child(6) > p:nth-child(1)").text().contains("Medtronic is the name we use to refer to our whole business, which includes Medtronic MiniMed, Inc. (doing business as Medtronic Diabetes), and any other companies that it controls, such as its subsidiaries and affiliates.")
        assert $("div.scroll_content2:nth-child(6) > p:nth-child(1)").text().contains("Privacy is very important to us, and we also understand that privacy is very important to you.")
        assert $("div.scroll_content2:nth-child(6) > p:nth-child(1)").text().contains("This Privacy Policy tells you how we protect and use information that we gather through this web site.")
        assert $("div.scroll_content2:nth-child(6) > p:nth-child(1)").text().contains("This Privacy Policy does not apply to the other Medtronic web sites.")
        assert $("div.scroll_content2:nth-child(6) > p:nth-child(1)").text().contains("You should review the privacy policy posted on those other web sites when you visit them.")
        assert $(".er_content>p:nth-child(1)").text().contains("Please remember that our Terms of Use and Privacy Statement govern your use of and submissions to this site.")
        assert $(".er_content>p:nth-child(1)").text().contains("You must agree to all of the Terms of Use and Privacy Statement in order to use this site.")
        assert $(".er_content>p:nth-child(1)").text().contains("By submitting this form you confirm your agreement with the provisions of the Terms of Use and Privacy Statement.")
        assert $(".er_content > p:nth-child(2) > img").getAttribute("alt") == "United States"
        assert $(".er_content > p:nth-child(2) > img").getAttribute("src").contains("flags/us.png")
    }

    void verifyChangeLink() {
        assert $(".er_content > p:nth-child(2) > a:nth-child(4)").displayed
        assert $(".er_content > p:nth-child(2) > a:nth-child(4)").text() == "change"
        $(".er_content > p:nth-child(2) > a:nth-child(4)").click()
        waitFor { browser.getCurrentUrl().contains("enroll/acknowledgement.jsf") }
        waitFor { $("fieldset").displayed }
        assert $(".main-contact > fieldset:nth-child(5) > label:nth-child(1) > span:nth-child(1)").text() == "Country/Territory"
        assert $(".main-contact > fieldset:nth-child(5) > label:nth-child(2) > span:nth-child(1)").text() == "Language"
    }

    void verifyEnrollmentTermsAcceptanceScreen2() {
        waitFor { continueBtn.displayed }
        continueBtn.click()
        waitFor { browser.getCurrentUrl().contains("enroll/locale.jsf") }
        waitFor { $(".country").text().equals("United States") }
        assert title.equals(ReadProperties.getProperties().get("enroll.acknowledgement.title"))
        assert $(".country").text().equals("United States")
        assert $("#accept > p:nth-child(1) > label:nth-child(1)").text() == "I am a resident of United States."

        //Scrollable areas
        assert $("div.scroll_content2:nth-child(4)").getHeight() >= 211
        assert $("div.scroll_content2:nth-child(6)").getHeight() >= 211
        def termOfUseSize = $("div.scroll_content2:nth-child(4)").find("p")*.getHeight().value.sum()
        def privacyPolicySize = $("div.scroll_content2:nth-child(6)").find("p")*.getHeight().value.sum()
        assert termOfUseSize > $("div.scroll_content2:nth-child(4)").getHeight()
        assert privacyPolicySize > $("div.scroll_content2:nth-child(6)").getHeight()

        assert termOfUseSize / ($("div.scroll_content2:nth-child(4)").getHeight()) > 5
        assert privacyPolicySize / ($("div.scroll_content2:nth-child(6)").getHeight()) > 10
    }

    void verifyAcceptButtonDisable() {
        assert acceptBtn.getAttribute("disabled")
        js.exec("window.scrollBy(0,1000)")
    }

    void verifyAcceptButtonEnable() {
        assert !acceptBtn.getAttribute("disabled")
    }

    void residentCheckboxOn() {
        assert residentCheckbox.module(geb.module.Checkbox).value() == "on"
    }

    void residentCheckboxOff() {
        assert residentCheckbox.module(geb.module.Checkbox).value() == null
        assert $("#accept > p:nth-child(2) > label:nth-child(1)").text() == "I have read, understood, and accept the Terms of Use and Privacy Statement."
    }

    void acceptCheckboxOn() {
        assert readAndAcceptCheckbox.module(geb.module.Checkbox).value() == "on"
    }

    void acceptCheckboxOff() {
        assert readAndAcceptCheckbox.module(geb.module.Checkbox).value() == null
    }

    void selectResidentCheckbox() {
        waitFor {residentCheckbox.displayed}
        residentCheckbox.click()
    }

    void selectAcceptCheckbox() {
        js.exec("window.scrollBy(0,1000)")
        readAndAcceptCheckbox.click()
    }

    void verifyDeclineButton() {
        assert declineButton.text() == "Decline"  //decline button
    }

    void selectPrivacyStatement() {
        $('input#challenge2').click()
    }

    void verifyCountry() {
        assert country.text() == 'United States'
    }

    void clickChangeLink() {
        waitFor { changeLink }
        changeLink.click()
    }
    void clinicInformationScreenFlag(String flag) {
        assert $('.middle_enroll > fieldset:nth-child(2) > label:nth-child(7) > span:nth-child(2) > img').getAttribute("outerHTML").contains(flag)
    }

    void selectProvinceCanada(String provinceText) {
        if ($(id: 'enroll_clinic:provinces').displayed) {
            assert $(id: 'enroll_clinic:provinces').text().replaceAll("\n", "").contains(provinceText)
        }
    }

    void labelProvince(String text) {
        if ($(id: 'enroll_clinic:provinces').displayed){
            assert $(id: 'enroll_clinic:provinces').text().replaceAll("\n", " ").contains(text)
        }
    }
}
