package com.medtronic.ndt.carelink.pages.clinic

import geb.Page
import org.openqa.selenium.By

class ClinicSettingsPage extends Page {

    static at = { clinicSettingsPage.displayed || closeClinicSettings.displayed || cancelButton.displayed }
    static content = {
        clinicSettingsPage(required: false) { $ "a", id: "list:clinicsettings" }
        reportSettingsText(required: false) { $ "a", id: 'staff:clinicReportSettings' }
        closeClinicSettings(required: false) { $ ".box_mid_nav_right > a" }
        clinicSettings() { $(id: "list:clinicsettings") }
        goToHomePage(required: false) { $("li.tab_home > a") }
        сlinicSettingsHeader(required: false) { $(".header") }
        сlinicSettingsLogo(required: false) { $(".header > img") }
        сlinicSettingsUser(required: false) { $(".header > h1:nth-child(3)") }
        сlinicSettingsIprologo(required: false) { $(id: "envision") }
        clinicSettingsText(required: false) { $("#current > a") }
        reportSettings(required: false) { $("input", id: "reportpreference:savePrefs") }
        noteText(required: false) { $ "#box_con > div.box_con_right > div > p" }
        reportClinicSettingsTitle(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(1) > td > span"
        }
        glucoseTargetRangeTitle(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(1) > span"
        }
        glucoseTargetRangeHigh(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(3) > td:nth-child(1) > label"
        }
        glucoseTargetRangeLow(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(4) > td:nth-child(1) > label"
        }
        glucoseUnits(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(3) > span"
        }
        timeDisplay(required: false) {
            $ "#box_con_left2 > table.box_con_left_table > tbody > tr:nth-child(2) > td:nth-child(4) > span"
        }
        sectionAnalysisReportsSettings(required: false) { $ "#mealsection > tr:nth-child(1) > td > span" }
        analysisReportsGlucoseTargetRangeText(required: false) {
            $ "#mealsection > tr:nth-child(2) > td:nth-child(2) > span"
        }
        analysisReportsTimePeriodText(required: false) { $ "#mealsection > tr:nth-child(2) > td:nth-child(3) > span" }
        analysisReportsPostMealAnalysisText(required: false) {
            $ "#mealsection > tr:nth-child(2) > td:nth-child(4) > span"
        }
        userAvailableLabe(required: false) { $ "#mealsection > tr:nth-child(2) > td:nth-child(4) > span" }
        clinicInformationLabe(required: false) { $ "#mealsection > tr:nth-child(2) > td:nth-child(4) > span" }
        reportSettingsClinicText(required: false) { $ "li.box_mid_nav_now.box_mid_nav_now_c > a > span > b" }
        userGuideClinicSettings(required: false) { $('a[href$="userguides.html"]') }
        resourcesClinicSettings(required: false) { $('a[href$="resources.html"]') }
        orderSuppliesClinicSettings(required: false) { $('a[href$="supplies.html"]') }
        privacyStatementClinicSettings(required: false) { $('a[href$="privacy.jsf"]') }
        termsOfUseClinicSettings(required: false) { $('a[href$="termsOfUse.jsf"]') }
        contactUsClinicSettings(required: false) { $('a[href$="contact.jsf"]') }
        usersList(required: false) { $('div.box_mid_record_c') }
        clinicSettingsPageUsers(required: false) { $ "a", id: "staff:clinicsettings" }
        buttonPrint(required: false, wait: 5) { $(id: ~/(.*?)print/) }
        closeClinicSettingsTextLabe(required: false, wait: 5) { $('box_mid_nav_right > a') }
        enterCurrentPassword(required: false) { $(By.id("userform:currentpassword")) }
        enterUserPassword(required: false) { $(By.id("userform:password")) }
        confirmPassword(required: false) { $(By.id("userform:confirmpassword")) }
        userFormSave(required: false) { $(By.id("userform:save")) }
        usersText(required: false) { $('a', id: ~/(.*?)staffTab/) }
        cliniInfoText(required: false) { $('a', id: ~/(.*?)informationTab/) }
        loginEnvisionLogo(required: false, wait: 5) { $(By.cssSelector("#envision")) }
        loginMedtronicLogo(required: false, wait: 5) {
            $(By.cssSelector("#list > div.wrapper > div > div.header > img"))
        }
        homeUrlText(required: false, wait: 5) { $("li#current > a") }
        clinickName(required: false, wait: 5) { $('#list > div.wrapper > div > div.header > h1:nth-child(3)') }
        confirmationWindow { $(id: "convert_dialog") }
        modalWindowConfirmationTitle { $(By.xpath("//*[text()='Convert units']")) }
        modalWindowConfirmationText { $(id: 'convert_dialog') }
        bgunitMgdl { $(id: "bgunit_mgdl") }
        glucoseUnitsMmol { $(id: "bgunit_mmoll") }
        glucoseUnitsMgDL { $(id: "bgunit_mgdl") }
        modalWindowCancelButton { $("input[Value='Cancel']") }
        modalWindowOKButton { $('input', id: ~/(.*?):convert/) }
        QRcode(required: false, wait: 5) {
            $('div.box_info_right > table > tbody > tr:nth-child(2) > td:nth-child(2) > img')
        }
        time12radioButton(required: false) { $("#timedisplay_hr12") }
        cancelButton(required: false) { $(id: "reportpreference:cancelPrefs") }
        openUserButton(required: false) { $(".box_staff_btns > input:nth-child(1)") }
        saveButtonUserTab(required: false) { $(id: "userform:save") }
        cancelButtonUserTab(required: false) { $("a.font_sml:nth-child(2)") }
        resetTwoFactorAuthenticationCheckBox(required: false) {
            $("#userform > fieldset:nth-child(5) > label:nth-child(4) > span:nth-child(2)")
        }
        selectSecondUserOnUserTab(required: false) {
            $(By.xpath("//tr[2]/td[@class='regular']/table/tbody/tr/td/input[@type='radio']"))
        }
        helpButtonUserTab(required: false) {
            $("#userform > fieldset:nth-child(5) > label:nth-child(4) > span:nth-child(1) > a > img")
        }
        helpButtonInformationTab(required: false) { $("#two-factor-help > img") }
        okButtonPopUpWindow(required: false) { $(id: "saved:okBtn") }
        signOutButton(required: false) { $("div.nav_right > ul > li:nth-child(1) > a") }
    }

    void QRCodeDisplayed() {
        waitFor { QRcode.displayed }
    }

    void closeClinicSettingsTextLabeClick(String text) {
        closeClinicSettingsTextLabe.text() == text
    }

    void homePageAvailable() {
        waitFor { goToHomePage.displayed }
        assert goToHomePage.displayed
    }

    void reportSettingsAvailable() {
        assert reportSettings.displayed
    }

    void userAvailable() {
        sleep(2000)
        waitFor { usersList.displayed }
    }

    void clinicInformationAvailable() {
        assert cliniInfoText.displayed
    }


    void analysisReportsGlucoseTargetRange(String title) {
        assert analysisReportsGlucoseTargetRangeText.text() == title
    }

    void analysisReportsTimePeriod(String title) {
        assert analysisReportsTimePeriodText.text() == title
    }

    void analysisReportsPostMealAnalysis(String title) {
        assert analysisReportsPostMealAnalysisText.text() == title
    }

    void sectionAnalysisReportsSettingsText(String title) {
        assert sectionAnalysisReportsSettings.text() == title
    }

    void timeDisplayTitle(String title) {
        assert timeDisplay.text() == title
    }

    void glucoseUnitsTitle(String title) {
        assert glucoseUnits.text() == title
    }

    void glucoseTargetRangeHighTitle(String title) {
        glucoseTargetRangeHigh.text() == title
    }

    void glucoseTargetRangeLowTitle(String title) {
        glucoseTargetRangeLow.text() == title
    }

    void glucoseTargetRangeTitle(String title) {
        assert reportClinicSettingsTitle.text() == title
    }

    void reportClinicSettings(String title) {
        assert glucoseTargetRangeTitle.text() == title
    }

    void noteTextDisplayed(String text) {
        noteText.text() == text
    }

    void saveButtonText(String text) {
        waitFor { reportSettings.displayed }
        reportSettings.value() == text
    }

    void сlinicSettingsIprologoDisplayed() {
        waitFor { сlinicSettingsIprologo.displayed }
    }

    void сlinicSettingsUserDisplayed() {
        waitFor { сlinicSettingsUser.displayed }
    }

    void сlinicSettingsLogoDisplayed() {
        waitFor { сlinicSettingsLogo.displayed }
    }

    void сlinicSettingsHeaderDisplayed() {
        sleep(3000)
        waitFor { сlinicSettingsHeader.displayed }
    }

    void selectGlucoseUnitsMmolValue(String value) {
        assert glucoseUnitsMmol.value() == value
    }

    void bgunitMgdlValue(String value) {
        assert bgunitMgdl.value() == value
    }

    void modalWindowOKNotDisplayed() {
        !($("#div:nth-child(3)")).displayed
    }

    void modalWindowVerifyClickOK(String text) {
        waitFor { modalWindowOKButton.displayed }
        assert modalWindowOKButton.value() == text
        modalWindowOKButton.click()
    }

    void modalWindowCancelClick() {
        modalWindowCancelButton.click()
    }

    void modalWindowCancel(String text) {
        assert modalWindowCancelButton.value() == text
    }

    void selectGlucoseUnitsMmol() {
        glucoseUnitsMmol.click()

    }

    void bgunitMgdlClick() {
        waitFor { bgunitMgdl.displayed }
        bgunitMgdl.click()
    }

    void modalWindowConfirmationTextDisplays() {
        assert modalWindowConfirmationText.text().contains("Click OK to convert all glucose settings on this page. Please be sure to confirm that each of the new values is correct.")
        assert modalWindowConfirmationText.text().contains("Reminder: When you are finished, click Save to save these settings")
    }

    void modalWindowConfirmationTitleDisplays(String text) {
        assert modalWindowConfirmationTitle.text() == text
    }

    void modalWindowConfirmationDisplayed() {
        assert modalWindowConfirmationTitle.displayed
    }

    void confirmationWindowDisplayed() {
        waitFor { confirmationWindow.displayed }
    }

    void goToHomePageClick() {
        goToHomePage.click()
        waitFor { homeUrlText.displayed }
    }

    void goToHomePageDisplayed(String text) {
        assert goToHomePage.text().contains(text)
    }

    void homeUrlTextDisplayed(String text) {
        waitFor { homeUrlText.displayed }
        assert homeUrlText.text().contains(text)
    }

    void clinicSettingsDisplayed(String text) {
        waitFor { clinicSettings.displayed }
        assert clinicSettings.text().contains(text)
    }

    void clinicSettingsTextDisplayed(String text) {
        waitFor { clinicSettingsText.displayed }
        assert clinicSettingsText.text().contains(text)
    }


    void clinicSettingsClick() {
        waitFor { clinicSettings.displayed }
        if ($('.ui-widget-overlay.ui-front').displayed) {
            waitFor { !$('.ui-widget-overlay.ui-front').displayed }
        }
        clinicSettings.click()
    }

    void clinickNameDisplayed() {
        waitFor { clinickName.displayed }
    }

    void buttonPrintDisplayed() {
        waitFor { buttonPrint.displayed }
    }

    void loginLogoEnvision() {
        waitFor { loginEnvisionLogo.displayed }
    }

    void loginLogoMedtronicDisplayed() {
        waitFor { loginMedtronicLogo.displayed }
    }

    void assertReportSettingsText(String text) {
        waitFor { reportSettingsClinicText.displayed }
        assert reportSettingsClinicText.text().contains(text)
    }

    void assertUsersText(String msgPart) {
        waitFor { usersText.displayed }
        usersText.text().contains(msgPart)
    }

    void assertUsersClick() {
        waitFor { usersText.displayed }
        usersText.click()
    }

    def usersTexNotdisplayed() {
        !usersText.displayed
    }

    void assertInfoText(String msgPart) {
        cliniInfoText.text().contains(msgPart)
    }

    void openClinicInformation() {
        waitFor { cliniInfoText.displayed }
        cliniInfoText.click()
    }

    void checkClinicCountry(String country) {
        waitFor { $(id: 'country-span').displayed }
        assert $(id: 'country-span').text().contains(country)
    }

    void closeClinicSettingsIsDisplayed() {
        waitFor { title == "Manage Clinic Page" }
        closeClinicSettings.displayed
    }

    void clickOnClinicSettings() {
        sleep(2000)
        waitFor { clinicSettingsPage.displayed }
        clinicSettingsPage.click()
    }

    void clickOnClinicSettingsFromUsers() {
        waitFor { clinicSettingsPageUsers.displayed }
        clinicSettingsPageUsers.click()
    }

    void closeClinicSettings() {
        closeClinicSettings.click()
    }

    void closeClinicSettingsDisplayed() {
        sleep(3000)
        waitFor { closeClinicSettings.displayed }
        closeClinicSettings.displayed
    }

    void clickOnClinicSettingsFromClinicInformation() {
        waitFor { clinicSettingsText.displayed }
        clinicSettingsText.click()
    }

    void resourcesDisplayedTextClinicSettings(String resourcesText) {
        waitFor { resourcesClinicSettings.displayed }
        assert resourcesClinicSettings.text().contains(resourcesText)
    }

    void userGuideDisplayedTextClinicSettings(String userGuideText) {
        waitFor { userGuideClinicSettings.displayed }
        assert userGuideClinicSettings.text().contains(userGuideText)
    }

    boolean orderSuppliesDisplayedTextClinicSettings(String orderSuppliesText) {
        orderSuppliesClinicSettings.text().contains(orderSuppliesText)
    }

    boolean privacyStatementDisplayedTextClinicSettings(String privacyStatementText) {
        privacyStatementClinicSettings.text().contains(privacyStatementText)
    }

    boolean termsOfUseDisplayedTextClinicSettings(String termsOfUseText) {
        termsOfUseClinicSettings.text().contains(termsOfUseText)
    }

    boolean contactUsDisplayedTextClinicSettings(String contactUsText) {
        contactUsClinicSettings.text().contains(contactUsText)
    }

    void verifyResourcesLinkClinicSettings() {
        waitFor { resourcesClinicSettings.displayed }
        browser.withNewWindow({ resourcesClinicSettings.click() }, wait: true) {
            sleep(3000)
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("resources.html")
        }
    }

    void verifyUserGuideLinkClinicSettings() {
        waitFor { userGuideClinicSettings.displayed }
        browser.withNewWindow({ userGuideClinicSettings.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("userguides.html")
        }
    }

    void verifyOrderSuppliesLinkClinicSettings() {
        waitFor { orderSuppliesClinicSettings.displayed }
        browser.withNewWindow({ orderSuppliesClinicSettings.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("supplies.html")
        }
    }

    void verifyPrivacyStatementLinkClinicSettings() {
        waitFor { privacyStatementClinicSettings.displayed }
        browser.withNewWindow({ privacyStatementClinicSettings.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("privacy.jsf")
        }
    }

    void verifyTermsOfUseLinkClinicSettings() {
        waitFor { termsOfUseClinicSettings.displayed }
        browser.withNewWindow({ termsOfUseClinicSettings.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("termsOfUse.jsf")
        }
    }

    void verifyContactUsLinkClinicSettings() {
        waitFor { contactUsClinicSettings.displayed }
        browser.withNewWindow({ contactUsClinicSettings.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("contact.jsf")
        }
    }

    void createNewUser(email) {
        waitFor { $("#box_con > div.box_staff_left > div > input[type=submit]:nth-child(2)").displayed }
        $("#box_con > div.box_staff_left > div > input[type=submit]:nth-child(2)").click()
        def names = ['Akila', 'Kate', 'Angie', 'Paul', 'Sean', 'Katie', 'Sri', 'Shelly']
        int random = (Math.random() * Integer.MAX_VALUE).toInteger()
        String username = names[(Math.random() * names.size()).toInteger()]
        waitFor { $("div.box_staff_create2").displayed }
        $(By.id("userform:username")).value(username + random)
        $(By.id("userform:firstname")).value(username + random)
        $(By.id("userform:lastname")).value(username + random)
        $(By.id("userform:email")).value(email)
        $(By.id("userform:answer")).value('Maluy013+')
    }

    String getUserName() {
        String userNameVal = $(By.id("userform:username")).getAttribute("value")
        return userNameVal
    }

    void saveOnCreateUserPage() {
        $(By.id("userform:save")).click()
    }

    void enterCurrentPass(String currentPass) {
        enterCurrentPassword.value(currentPass)
    }

    void waitForUserButton() {
        waitFor { $("#box_con > div.box_staff_left > div > input[type=submit]:nth-child(2)").displayed }
    }

    void enterPass(String getPass) {
        waitFor { enterCurrentPassword.displayed }
        enterUserPassword.value(getPass)
        confirmPassword.value(getPass)
        userFormSave.click()
    }

    void swicthLangToJapan(List<String> langList) {
        waitFor { $('.fieldset-overflowed').find('label:nth-child(8)>span>select').displayed }
        assert $('.fieldset-overflowed').find('label:nth-child(8)>span>select').value() == 'en'
        assert $('.fieldset-overflowed').find('label:nth-child(8)>span>select').find("option")*.text() == langList
        assert $('.fieldset-overflowed').find('label:nth-child(8)>span>select').find("option")*.value() == ['ja', 'en']
        $('.fieldset-overflowed').find('label:nth-child(8)>span>select').value("日本語")
        assert $('.fieldset-overflowed').find('label:nth-child(8)>span>select').value() == 'ja'
        $('.box_info_right').find('input:nth-child(2)').click() // save
        waitFor {
            $('.ui-dialog-titlebar.ui-corner-all.ui-widget-header.ui-helper-clearfix.ui-draggable-handle').displayed
        }
        assert $('.ui-dialog-titlebar.ui-corner-all.ui-widget-header.ui-helper-clearfix.ui-draggable-handle').find('span.ui-dialog-title').text() == "保存完了"
        assert $('.confirm_dialog_btn_div').find(id: 'saved:okBtn').value() == "OK"
        $(id: 'saved:okBtn').click()
        waitFor { $('.fieldset-overflowed').find('label:nth-child(8)>span>select').displayed }
    }

    void openReportSettings() {
        waitFor { $('.box_mid_nav.box_mid_nav_c').find('ul>li:nth-child(1)').displayed }
        $('.box_mid_nav.box_mid_nav_c').find('ul>li:nth-child(1)').click()
        waitFor { glucoseTargetRangeTitle.displayed }
    }

    void enter100toAllGlucoseInput() {
        $(id: 'reportpreference:glucoseTargetHigh').value("100")
        $(id: 'reportpreference:glucoseTargetLow').value("100")
        $(id: 'reportpreference:glucoseTargetXtremeHigh').value("100")
        $(id: 'reportpreference:glucoseTargetXtremeLow').value("100")
        $(id: 'reportpreference:savePrefs').click()
    }

    void verifyJapaneseError() {
        def japanError = ['上限は80～最高値 - 20でなければなりません。', '最高値は上限 + 20～400でなければなりません。', '下限は50～上限 - 2でなければなりません。', '最低値は40～下限 - 10でなければなりません。']
        waitFor { $("ul.error:nth-child(2)").find("li")*.displayed }
        waitFor { $("ul.error:nth-child(2)").find("li")*.text().get(0) == japanError[0] }
        assert $("ul.error:nth-child(2)").find("li")*.text().get(0) == japanError[0]
        assert $("ul.error:nth-child(2)").find("li")*.text().get(1) == japanError[1]
        assert $("ul.error:nth-child(2)").find("li")*.text().get(2) == japanError[2]
        assert $("ul.error:nth-child(2)").find("li")*.text().get(3) == japanError[3]
    }

    void selectTime12() {
        time12radioButton.click()
    }

    void clickSaveButton() {
        reportSettings.click()
        waitFor { $(By.xpath("//form/div[2]/input[@value='Yes']")).displayed }
        $(By.xpath("//form/div[2]/input[@value='Yes']")).click()
        waitFor { driver.getCurrentUrl().contains("/patient/report_settings.jsf") }
    }

    void resetTwoFactorAuthentication() {
        waitFor { openUserButton.displayed }
        openUserButton.click()
        browser.getCurrentUrl().contains("clinic/clinician.jsf")
        waitFor { resetTwoFactorAuthenticationCheckBox.displayed }
        resetTwoFactorAuthenticationCheckBox.click()
        saveButtonUserTab.click()
        waitFor { browser.getCurrentUrl().contains("clinic/clinician.jsf") }
    }

    void verifyPatientForm(email, firstname, lastname, answer) {
        waitFor { openUserButton.displayed }
        openUserButton.click()
        browser.getCurrentUrl().contains("clinic/clinician.jsf")
        waitFor { $("#userform > fieldset:nth-child(5) > label:nth-child(4) > span:nth-child(1)").displayed }
        assert $("#userform > fieldset:nth-child(5) > label:nth-child(4) > span:nth-child(1)").text() == "Reset Two-Factor Authentication"
        assert $(By.id("userform:firstname")).value() == firstname
        assert $(By.id("userform:lastname")).value() == lastname
        assert $(By.id("userform:email")).value() == email
        assert $(By.id("userform:answer")).value() == answer
        assert helpButtonUserTab.displayed
        browser.withNewWindow({ helpButtonUserTab.click() }, wait: true, close: true) {
            waitFor { browser.getCurrentUrl().contains("mfa-help.jsf") }
            waitFor { $("body").displayed }
        }
        browser.getCurrentUrl().contains("clinic/clinician.jsf")
        cancelButtonUserTab.click()
    }

    void selectAdminUser() {
        waitFor { selectSecondUserOnUserTab.displayed }
        selectSecondUserOnUserTab.click()
    }

    void verifyClinicInformationTab() {
        waitFor { browser.getCurrentUrl().contains("clinic/clinician.jsf") }
        waitFor { $(By.xpath("//span[@id='two-factor-table']/table/tbody/tr/td/input[@value='true']")).displayed }
        assert $("div.header > h1").text().contains($("fieldset > label:nth-child(1) > span.rightinput > input").value())
        assert $(By.xpath("//span[@id='password-expiration']/table/tbody/tr/td/input[@value='true']")).displayed
        assert helpButtonInformationTab.displayed
        browser.withNewWindow({ helpButtonInformationTab.click() }, wait: true, close: true) {
            waitFor { browser.getCurrentUrl().contains("mfa-help.jsf") }
            waitFor { $("body").displayed }
        }
        browser.getCurrentUrl().contains("clinic/clinician.jsf")
        assert $(id: "two-factor-disclaimer").displayed
    }

    void disableTwoFactorAuthenClinicInformationTab() {
        waitFor { browser.getCurrentUrl().contains("preferences.jsf") }
        waitFor { $(By.xpath("//span[@id='two-factor-table']/table/tbody/tr/td/label[text()=' Off']")).displayed }
        $(By.xpath("//span[@id='two-factor-table']/table/tbody/tr/td/label[text()=' Off']")).click()
        $(By.xpath("//*[@id='box_con']/div[2]/input[2]")).click()
        waitFor { okButtonPopUpWindow.displayed }
        okButtonPopUpWindow.click()
    }

    void verifyTwoFactorAuthenIsNotDisplayed() {
        waitFor { openUserButton.displayed }
        openUserButton.click()
        browser.getCurrentUrl().contains("clinic/clinician.jsf")
        assert !$("#userform > fieldset:nth-child(5) > label:nth-child(4) > span:nth-child(1)").displayed
        waitFor { cancelButtonUserTab.displayed }
        cancelButtonUserTab.click()
    }

    void passwordExpirationOn(String s) {
        waitFor { browser.getCurrentUrl().contains("preferences.jsf") }
        waitFor { $(By.xpath("//span[@id='password-expiration']/table/tbody/tr/td/label[text()=' Off']")).displayed }
        assert $("fieldset > label:nth-child(11) > span:nth-child(1)").text().contains(s)
        $(By.xpath("//span[@id='password-expiration']/table/tbody/tr/td/label[text()=' On']")).click()
        $(By.xpath("//*[@id='box_con']/div[2]/input[2]")).click()
        waitFor { okButtonPopUpWindow.displayed }
        okButtonPopUpWindow.click()
        waitFor { browser.getCurrentUrl().contains("clinic/information.jsf") }
    }

    void clickSignOut() {
        waitFor { signOutButton.displayed }
        signOutButton.click()
    }

    void verifyLanguageOption() {
        def lang = $('.fieldset-overflowed').find('label').getAt(8).text()
        assert lang.contains('English')&&lang.contains('български')&&!lang.contains('русский')
    }
}
