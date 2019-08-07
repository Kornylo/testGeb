package com.medtronic.ndt.carelink.pages.clinicwizard

import com.medtronic.ndt.carelink.data.ReadProperties
import geb.Page

class NewClinicRegistrationPage extends Page {
    static at = { registerClinic.displayed || continueBtn.displayed }
    static content = {
        registerClinic(required: false) { $('a[href$="enroll/info.jsf"]') }
        continueBtn(required: false) { $("a", id: "info:first_focus") }
        cancelButton(required: false) { $('.button_sign').find(type:'submit','.sv_btn') }
        clinicRegistrationTitle(required: false) { $('form#info h2') }
        enrollmentInfoText(required: false) { $('div.middle-contact') }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
    }

    void clickRegisterClinic() {
        registerClinic.click()
    }

    void enrollmentText() {
        waitFor { title == ReadProperties.getProperties().get("enroll.info.title") }
        waitFor { enrollmentInfoText.displayed }
        System.out.println(enrollmentInfoText.text())
        assert clinicRegistrationTitle.text().contains(ReadProperties.getProperties().get("enroll.info.h1"))
        assert enrollmentInfoText.text().contains(ReadProperties.getProperties().get("enroll.info.note"))
        assert enrollmentInfoText.text().contains(ReadProperties.getProperties().get("enroll.info.text.1").toString().replace('&trade;', '™'))
        assert enrollmentInfoText.text().contains(ReadProperties.getProperties().get("enroll.info.text.2").toString().replaceAll('<b>|<...', ''))
        assert enrollmentInfoText.text().contains(ReadProperties.getProperties().get("enroll.info.text.3").toString().replace('&trade;', '™').replaceAll('<b>|<...', ''))

    }

    void clickOnContinue() {
        waitFor { continueBtn.displayed }
        continueBtn.click()
    }

    void continueButtonText() {
        assert continueBtn.text().contains(ReadProperties.getProperties().get("button.continue"))
    }

    void cancelButtonDisplayed() {
        waitFor { cancelButton.displayed }
        assert cancelButton.value().contains(ReadProperties.getProperties().get("button.cancel"))
    }

    void verifyEnrollmentInfo() {
        waitFor { enrollmentInfoText.displayed }
    }

    void verifyLogo() {
        waitFor { medtronicLogo.displayed }
        assert medtronicLogo.css('float') == 'left'
        if (envisionLogo.displayed) {
            assert envisionLogo.css('float') == 'right'
        } else {
            assert $("div.header_er > img.iprologo").displayed
        }
    }

}
