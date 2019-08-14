package com.kornylo.test.geb.pages

import geb.Page

class LoginTest2Page extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            loginButton.displayed
        }
    }

    static content = {
        // Username
        loginButton(required: false) { $('body > app-root > div > div:nth-child(2) > div.app-rz-header > header > div > div.header-topline > div.header-topline__user.js-rz-auth > div.header-topline__user-wrapper > p > a') }
        btn(required: false) { $('body > app-root > div > div:nth-child(2) > div.app-rz-header > header > div > div.header-topline > div.header-topline__user.js-rz-auth > div.header-topline__user-wrapper > p > a')}
        logField(required: false) {$('#auth_email')}
        login(required: false) {$('#auth_email')}
        password(required: false) {$('#auth_pass')}
        check(required: false) {$('body > app-root > div > div:nth-child(2) > div.app-rz-common > auth-modal > modal-window > div > div > div > auth-content > div > form > div > button > span')}
        page(required: false) {$('body > app-root > div > div:nth-child(2) > div.app-rz-header > header > div > div.header-topline > div.header-topline__user.js-rz-auth > div.header-topline__user-wrapper > p > a')}
        name(required: false) {$('body > app-root > div > div:nth-child(2) > div.app-rz-header > header > div > div.header-topline > div.header-topline__user.js-rz-auth > div.header-topline__user-wrapper > p > a')}
        info(required: false) {$('#personal_information > div > div > div:nth-child(2) > div > div > div:nth-child(2) > div.addit-f-i-field > div')}
    }

    public void printPageTitle() {
        driver.getTitle()

    }
    boolean loginButton() {
        loginButton.displayed
    }
    void loginBtn() {
        btn.click()
    }
    boolean autorizeLog() {
        logField.displayed
    }
    void loginField(String string) {
        login = string
    }
    void passwordField(String string) {
        password = string
    }
    void checkBtn() {
        check.click()
    }
    boolean chekPage() {
        page.displayed
    }
    void cabinetMy() {
       name.click()
    }
    boolean infoPage() {
        info.displayed
    }




}
