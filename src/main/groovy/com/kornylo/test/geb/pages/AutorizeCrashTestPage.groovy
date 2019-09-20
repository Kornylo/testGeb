package com.kornylo.test.geb.pages

import geb.Page

class AutorizeCrashTestPage extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            carousel.displayed
        }
    }

    static content = {
        // Username
        carousel(required: false) { $("#homeslider > li:nth-child(2) > div") }
        signIn(required: false) {$('#header > div.nav > div > div > nav > div.header_user_info > a')}
        verifyPage(required: false) {$('#center_column > h1')}
        address(required: false) {$('#email_create')}
        submit(required: false) {$('#SubmitCreate')}
        result{$('#create_account_error > ol > li')}

    }
     void printPageTitle() {
        driver.getTitle()

    }
    boolean mainPage() {
        carousel.displayed
    }
    void clickOnSignInButton() {
        signIn.click()
    }
    boolean signPage() {
        verifyPage.displayed
    }
    void eMail(String string) {
        address = string
    }
    void submitButton() {
        submit.click()
    }
     void verifyAddress(String Text) {
        waitFor { result.displayed }
        assert result.text().contains(Text)
    }




}
