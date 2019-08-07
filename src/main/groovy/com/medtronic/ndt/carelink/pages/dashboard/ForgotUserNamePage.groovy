package com.medtronic.ndt.carelink.pages.dashboard

import com.medtronic.ndt.carelink.data.ReadProperties
import geb.Page

class ForgotUserNamePage extends Page {
    static at = {
        usernameTextbox.displayed || passwordTextbox.displayed || forgotUserName.displayed
    }
    static content = {
        medtronicLogo(required: true,wait: true) { $("img", class: "mdtlogo") }
        envisionLogo(required: true, wait: true) { $("h1", id: "envision") }
        forgotUserNameClose() { $('.button').find('input') }
        forgotUserNameLastName() { $(id: 'reminderform:lastname') }
        forgotUserNameEmail() { $(id: 'reminderform:email') }
        forgotUserNameSubmitButton() { $("input.sv_btn") }
        // Username
        usernameTextbox(required: false) { $(id: "j_username") }
        // Password
        passwordTextbox(required: false) { $(id: "j_password") }
        forgotUserName(required: true){$("dl.float_l > dd:nth-child(2) > a:nth-child(1)")}

    }

    void enterDataAndSendExpired(String lastName, String email) {
        browser.withNewWindow({forgotUserName.click()}, wait: true, close: false) {
//            waitFor { medtronicLogo.displayed }
//            waitFor { forgotUserNameLastName.displayed }
            forgotUserNameLastName.value(lastName)
            forgotUserNameEmail.value(email)
            forgotUserNameSubmitButton.click()

            waitFor { $('.main-contact').find('h2').displayed }

            assert $('.main-contact').find('h2').text() == ReadProperties.getProperties().get("username.reminder.title")
            assert $('.main-contact').find('p:nth-child(1)').text() == ReadProperties.getProperties().get("username.reminder.text")
            assert forgotUserNameClose.displayed
            assert forgotUserNameClose.value() == ReadProperties.getProperties().get("button.close")
            if (driver.capabilities['browserName'].toString().contains("Safari")) {
                browser.close()
            } else {
                forgotUserNameClose.click()
            }
            if (driver.capabilities['browserName'].toString() == 'internet explorer') {
                sleep(2000)
                driver.switchTo().alert().accept()
            }
        }
        assert title != ReadProperties.getProperties().get("username.reminder.title")
    }
}
