package com.medtronic.ndt.carelink.pages.dashboard

import com.medtronic.ndt.carelink.util.Validation
import geb.Page

class PrivacyStatementPage extends Page {
    static SignInPage signInPage
    static at = {
        privacyStatementTitle.displayed || medtronicLogo.displayed || envisionLogo.displayed || privacyStatementContent.displayed
    }
    static content = {
        termOfUseContent(required: false) { $(".scroll_content") }
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
        contactUs(required: false) { $(".scroll_content > center:nth-child(38) > a:nth-child(5)") }

    }

    void openContactUs() {
        contactUs.click()
    }

    void checkPrivacyStatementScreen() {
        browser.withNewWindow({ privacyStatement.click() }, wait: true) {
            waitFor { browser.getCurrentUrl().contains("privacy") }
            waitFor { privacyStatementHeader.displayed }
            assert title == "Privacy Policy"
            //Logo
            medtronicLogo.displayed
            envisionLogo.displayed
            assert medtronicLogo.getAttribute("alt") == "Medtronic Diabetes"
            assert medtronicLogo.getAttribute("src").contains("/images/logo_medtronicN.png")
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
                assert envisionLogo.getAttribute("class") == "iprologo"
            } else {
                assert $("div.header_er > img.iprologo").displayed
            }
            //Close button
            privacyStatementCloseButton.displayed
            assert privacyStatementCloseButton.getAttribute("type") == "submit"
            assert privacyStatementCloseButton.getAttribute("name") == "pp_form:egress1"
            assert privacyStatementCloseButton.getAttribute("value") == "Close"
            assert privacyStatementCloseButton.getAttribute("onclick") == "window.close()"
            //no footer SubMenu
            assert footerSubmenu != true
            //Title
            $("#h1").displayed
            assert privacyStatementTitle.text() == "Privacy Statement"
            //Notice of Privacy Practices
            privacyStatementNotice.displayed
            assert privacyStatementNotice.getAttribute("href") == "http://www.minimed.com/notices"
            assert privacyStatementNotice.text() == "Notice of Privacy Practices"
            //Content
            privacyStatementContent.displayed
            def privacyStatementUI = privacyStatementContent.text().replaceAll("\n", "").replaceAll("[^\\x00-\\x7F]", "")
            def privacyStatementFile = getClass().getClassLoader().getResourceAsStream("txtAgreements/privacyStatement.txt").text.replaceAll("\n|\r", "").replaceAll("[^\\x00-\\x7F]", "")
            assert privacyStatementUI == privacyStatementFile
        }
        waitFor { title == "iPro Login" }
    }

    void checkTermOfUseContentFromPrivacyStatement() {
        browser.withNewWindow({ privacyStatement.click() }, wait: true) {
            waitFor { termOfUseLinkFromPrivacyPolicy.displayed }
            assert title == "Privacy Policy"
            assert termOfUseLinkFromPrivacyPolicy.text() == "Terms of Use"
            browser.withNewWindow({ termOfUseLinkFromPrivacyPolicy.click() }, wait: true) {
                waitFor { browser.page(Validation).pageComplete() }
                waitFor { browser.getCurrentUrl().contains("/hcp/termsOfUse.jsf") }
                waitFor { termOfUseContent.displayed }
                assert title == "Terms of Use"
            }
        }
    }

    void checkNoticeOfPrivacyPolicy() {
        browser.withNewWindow({ privacyStatement.click() }, wait: true) {

            waitFor { browser.getCurrentUrl().contains('privacy.jsf') }
            waitFor { privacyStatementNotice.displayed }
            assert title == "Privacy Policy"
            privacyStatementNotice.click()
            waitFor { browser.getCurrentUrl().contains('medtronicdiabetes.com') }
            browser.withWindow({ title == "Notice of Privacy Practices | Medtronic Diabetes" }) {
                assert browser.getCurrentUrl() == "http://www.medtronicdiabetes.com/notices"
                driver.navigate().back()
                waitFor { privacyStatementTitle.displayed }
                waitFor { termOfUseLinkFromPrivacyPolicy.displayed }
                assert termOfUseLinkFromPrivacyPolicy.getAttribute("href").contains("/ipro/hcp/termsOfUse.jsf")
                assert termOfUseLinkFromPrivacyPolicy.text() == "Terms of Use"
            }

        }
    }

    void checkPrivacyStatementCloseButton() {
        browser.withNewWindow({ privacyStatement.click() }, wait: true, close: false) {
            waitFor { privacyStatementContent.displayed }

            assert browser.getCurrentUrl().contains("ipro/hcp/privacy.jsf")
            assert title == "Privacy Policy"
            waitFor { medtronicLogo.displayed }
            assert medtronicLogo.css('float') == 'left'
            if (envisionLogo.displayed) {
                assert envisionLogo.css('float') == 'right'
            } else {
                assert $("div.header_er > img.iprologo").displayed
            }
//            waitFor { privacyStatement.displayed }

            if (driver.capabilities['browserName'].toString().contains("Safari")) {
                browser.close()
            } else {
                privacyStatementCloseButton.click()
            }

            if (driver.capabilities['browserName'].toString() == 'internet explorer') {
                sleep(3000)
                driver.switchTo().alert().accept()
            }
        }
        waitFor { title == "iPro Login" }
    }
}
