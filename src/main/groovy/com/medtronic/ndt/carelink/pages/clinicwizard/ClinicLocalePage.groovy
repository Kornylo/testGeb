package com.medtronic.ndt.carelink.pages.clinicwizard

import com.medtronic.ndt.carelink.pages.components.DropdownModule
import geb.Page
import org.openqa.selenium.By

class ClinicLocalePage extends Page {
    static at = { selectBtn.displayed }
    static content = {
        selectBtn(required: false) { $('input', id: 'localeform:selectbtn') }
        countryDropDown(required: false) { $('select', id: 'localeform:selectcountry').module(DropdownModule) }
        languageDropDown(required: false) { $('select', id: 'localeform:selectlanguage').module(DropdownModule) }
        cancelLink(required: false) { $ 'div.button_sign a' }
        stepIndicator(required: false) { $(By.xpath('//*[contains(@img,\'/ipro/2.2.003/images/step_1.gif\')]')) }
        mdtLogo(required: false) { $(".mdtlogo") }
        commonScreenFooterElements(required: false) { $(id: 'footer_submenu_r') }
        continueBtn(required: false) { $ "input", id: "localeform:selectbtn" }
        stepMenu(required: false) { $('.step') }
    }

    void selectLocale() {
        waitFor { selectBtn.displayed }
        selectBtn.click()
    }

    void selectCountry(String country) {
        waitFor { countryDropDown.displayed }
        countryDropDown.selectOption(country)
    }

    void selectLanguage(String lang) {
        waitFor { languageDropDown.displayed }
        languageDropDown.selectOption(lang)
        println(languageDropDown.getOptions().collect { it.text() })
    }

    void selectBulgarianLanguage() {
        languageDropDown.selectOption('български')
    }

    void verifyLanguageOption() {
        sleep(5000)
        waitFor { $(id: 'localeform:selectlanguage').displayed }
        def lang = $(id: 'localeform:selectlanguage').text()
        assert lang.contains('English') && lang.contains('български') && !lang.contains('русский')
    }

    void clickCancelLink() {
        cancelLink.click()
    }

    void enrollmentCountryAndLanguageSelectScreen() {
        waitFor { $(".mdtlogo").displayed }
        waitFor { countryDropDown.displayed }
        waitFor { languageDropDown.displayed }
        assert cancelLink.displayed
        assert selectBtn.displayed
        assert browser.getCurrentUrl().contains("enroll/info.jsf")
    }


    void selectCountryAndLanguage(String country, String lang) {
        waitFor {
            $(id: "localeform:selectcountry").displayed
            $(id: "localeform:selectcountry").value(country)
        }
        waitFor { $(id: "localeform:selectlanguage").displayed }
        $(id: "localeform:selectlanguage").value(lang)
        if (country == "United States") {
            assert $(id: "localeform:selectcountry").value() == "US"
            assert $(id: "localeform:selectlanguage").value() == "en"
        }
    }

    void verifyOnEnrollmentSelectCountryAndLanguageScreen() {
        assert mdtLogo.displayed
        assert !commonScreenFooterElements.displayed
        assert stepMenu.displayed
        assert selectBtn.displayed
        assert languageDropDown.displayed
        countryDropDown.value('United States')
        assert countryDropDown.find('option').getAt(84).text() == "United States"
        languageDropDown.value("en")
        assert languageDropDown.find('option').getAt(0).text() == "English"
        assert continueBtn.displayed
        assert continueBtn.value() == "Continue"
        assert cancelLink.displayed
        assert cancelLink.text() == "Cancel"
    }

    void clickContinueBtn() {
        continueBtn.click()
        waitFor { browser.getCurrentUrl().contains("enroll/locale.jsf") }
    }

    void countryFlagBeingDisplayed(String countryFlag) {
        assert $('.er_content > p:nth-child(2) > img:nth-child(1)').getAttribute("outerHTML").contains(countryFlag)
    }

    void countryUnderTestTexts(String texts) {
        waitFor { $('#accept > p:nth-child(1)').text().contains(texts) }
    }
}
