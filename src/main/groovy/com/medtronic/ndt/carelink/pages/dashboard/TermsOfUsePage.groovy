package com.medtronic.ndt.carelink.pages.dashboard

import com.medtronic.ndt.carelink.util.Validation
import geb.Page
import org.openqa.selenium.By

class TermsOfUsePage extends Page {
    static PrivacyStatementPage privacyStatement
    static at = {
        termOfUseContent.displayed || termOfUseCloseButton.displayed || privacyPolicy1.displayed || contactUs.displayed || medtronicLogo.displayed || envisionLogo.displayed
    }
    static content = {

        termOfUse(required: false) { $("#footer_submenu_r > li:nth-child(2) > a") }
        termOfUseContent(required: false) { $(".scroll_content") }
        termOfUseCloseButton(required: false) { $(By.id("tou_form:egress1")) }
        privacyPolicy1(required: false) { $("#tou_form > div > div.scroll_content > p:nth-child(14) > a") }
        privacyPolicy2(required: false) {
            $("#tou_form > div > div.scroll_content > p:nth-child(16) > a:nth-child(1)")
        }
        privacyPolicy3(required: false) {
            $("#tou_form > div > div.scroll_content > p:nth-child(16) > a:nth-child(2)")
        }
        contactUs(required: false) { $("#pp_form > div > div.scroll_content > center > a") }

        privacyStatement(required: false) { $("a", text: "Privacy Statement") }
        footerSubmenu(required: false) { $("ul", id: "footer_submenu_r") }
        privacyStatementHeader(required: false) { $("div", class: "header_er") }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
        contactUsFromPrivacyPolicy(required: false) { $(".scroll_content > center:nth-child(38) > a:nth-child(5)") }
        closeButton(required: false) { $(id: "tou_form:egress1") }
    }

    void openTermOfUse() {
        termOfUse.click()
    }

    def pageComplete() {
        js.('document.readyState') == 'complete'
    }

    void openContactUs() {
        contactUs.click()
    }

    void checkTermOfUseContent() {
        browser.withNewWindow({ openTermOfUse() }, wait: true) {
            waitFor { termOfUseContent.displayed }
            assert title == "Terms of Use"
            //Logo
            waitFor { privacyStatementHeader.displayed }
            waitFor { medtronicLogo.displayed }
            waitFor { envisionLogo.displayed }
            assert medtronicLogo.getAttribute("alt") == "Medtronic Diabetes"
            assert medtronicLogo.getAttribute("src").contains("/images/logo_medtronicN.png")
            assert envisionLogo.getAttribute("class") == "iprologo"
            //no footer SubMenu
            assert footerSubmenu != true

            //ReplaceAl - just for prevent different line breakers on different OS
            def termOfUseUI = termOfUseContent.text().replaceAll("\n", "").replaceAll("[^\\x00-\\x7F]", "")
            def termOfUseFile = getClass().getClassLoader().getResourceAsStream("txtAgreements/termsOfUse.txt").text.replaceAll("\n|\r", "").replaceAll("[^\\x00-\\x7F]", "")
            //Such way for relative path
            if (driver.capabilities['browserName'].toString().contains("Safari")) {
                assert termOfUseUI.replace(' ','') == termOfUseFile.replace(' ','')
            }else{
                assert termOfUseUI == termOfUseFile
            }
        }
    }

    void checkPrivacyPolicyLinks() {
        browser.withNewWindow({ openTermOfUse() }, wait: true) {
            waitFor { pageComplete() }
            assert title == "Terms of Use"
            termOfUseContent.displayed
            //Will be checked all 3 links
            privacyPolicy1.displayed
            privacyPolicy2.displayed
            privacyPolicy3.displayed

            def privacyPolicy1Url = privacyPolicy1.getAttribute("href")
            def privacyPolicy2Url = privacyPolicy2.getAttribute("href")
            def privacyPolicy3Url = privacyPolicy3.getAttribute("href")

            assert privacyPolicy1.text() == "Privacy Policy"
            assert privacyPolicy2.text() == "Privacy Policy"
            assert privacyPolicy3.text() == "Privacy Policy"

            assert privacyPolicy1Url.contains("ipro/hcp/privacy.jsf")
            assert privacyPolicy2Url.contains("ipro/hcp/privacy.jsf")
            assert privacyPolicy3Url.contains("ipro/hcp/privacy.jsf")

            assert privacyPolicy1Url.replaceAll("/privacy.jsf", "") == browser.getCurrentUrl().replaceAll("/termsOfUse.jsf", "")
            assert privacyPolicy2Url.replaceAll("/privacy.jsf", "") == browser.getCurrentUrl().replaceAll("/termsOfUse.jsf", "")
            assert privacyPolicy3Url.replaceAll("/privacy.jsf", "") == browser.getCurrentUrl().replaceAll("/termsOfUse.jsf", "")
        }
    }

    void openPrivacyPolicyLinks() {
        browser.withNewWindow({ openTermOfUse() }, wait: true) {
            waitFor { browser.page(Validation).pageComplete() }
            waitFor { privacyPolicy1.displayed }
            browser.withNewWindow({ privacyPolicy1.click() }, wait: true) {
                waitFor { browser.page(Validation).pageComplete() }
                waitFor { contactUs.displayed }
                assert title == "Privacy Policy"
                assert browser.getCurrentUrl().contains("/hcp/privacy.jsf")
                assert contactUs.text() == "Contact Us"
            }
        }
    }

    void termOfUseButtonCloseChecking() {
        browser.withNewWindow({ openTermOfUse() }, wait: true, close: false) {
            waitFor { termOfUseCloseButton.displayed }
            assert termOfUseCloseButton.getAttribute("value") == "Close"
            if (driver.capabilities['browserName'].toString().contains("Safari")) {
                browser.close()
            } else {
                termOfUseCloseButton.click()
            }
            if (driver.capabilities['browserName'].toString() == 'internet explorer') {
                sleep(3000)
                driver.switchTo().alert().accept()
            }
        }
        assert title == "iPro Login"
    }

    void openContactUsLink(String url) {
        browser.withNewWindow({ openTermOfUse() }, wait: true) { //from SignInPage open 'TermOfUse'
            waitFor { privacyPolicy1.displayed }
            browser.withNewWindow({ privacyPolicy1.click() }, wait: true) { //from 'TermOfUse' open 'PrivacyPolicy'
                waitFor { contactUsFromPrivacyPolicy.displayed }
                def contactUsUrl = contactUsFromPrivacyPolicy.getAttribute("href")
                assert contactUsUrl.contains("/help/contact/")
                browser.withNewWindow({ openContactUs() }, wait: true) { // from 'PrivacyPolicy' open 'Contact Us'
                    waitFor { browser.currentUrl.contains('medtronicdiabetes') }
                    waitFor {title!=''}
                    assert browser.currentUrl == url
                    assert title.contains("Medtronicdiabetes")
                }
            }
        }
    }

}

