package com.kornylo.test.geb.pages

import geb.Page

class LoginTestPage extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            menuButton.displayed
        }
    }

    static content = {
        // Username
        menuButton(required: false) { $('#gb_70') }
        menu(required: false) { $('#gb_70') }
        login(required: false) { $('#identifierId')}
        enter(required: false) { $('#identifierId')}
        button(required: false) {$('#identifierNext > div.ZFr60d.CeoRYc')}
    }

    public void printPageTitle() {
        driver.getTitle()

    }
    boolean menuButton() {
        menuButton.displayed
    }
    void menuList() {
        menu.click()
    }
    boolean loginLine() {
        login.displayed
    }
    void loginEnter(String string) {
        enter = string
    }
    void enterButton() {
        button.click()
    }


}
