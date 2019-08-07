package com.medtronic.ndt.carelink.pages.clinicwizard

import geb.Page
import com.medtronic.ndt.carelink.data.Clinic

import groovy.util.logging.Slf4j
import org.openqa.selenium.By

@Slf4j
class ClinicEnterInfoPage extends Page {
    private static List<String> names = ['Akila', 'Anu', 'Jamie', 'Paul', 'Sean', 'Katie', 'Sri', 'Shelly']
    private List<String> citySuffixes = ['ville', ' City', ' Land']
    private List<String> streetSuffixes = [' Street', ' Road', ' Ave']
    static at = {
        waitFor { browser.getCurrentUrl().contains("enroll/") }
    }
    static content = {
        clinicEnterInfoText(required: false) { $ ('div.middle-contact') }
        clinicName(required: false) { $ "input", id: "enroll_clinic:clinicname" }
        clinicNameError(required: false) { $(By.xpath('//*[@id=\'enroll_clinic:clinicnameerr\']')) }
        address1(required: false) { $ "input", id: "enroll_clinic:address1" }
        address2(required: false) { $ "input", id: "enroll_clinic:address2" }
        city(required: false) { $ "input", id: "enroll_clinic:city" }
        state(required: false) { $ "select", id: "enroll_clinic:state" }
        zipCode(required: false) { $ "input", id: "enroll_clinic:zip" }
        zipCodeError(required: false) { $(By.xpath('//*[@id=\'enroll_clinic:ziperr\']')) }
        phone(required: false) { $ "input", id: "enroll_clinic:phone" }
        phoneError(required: false) { $(By.xpath('//*[@id=\'enroll_clinic:phoneerr\']')) }
        continueBtn(required: false) { $ "input", id: "enroll_clinic:continue" }
        cancelLink(required: false) { $ 'div.button_sign a' }
    }

    boolean clinicEnterInfoText(String text) {
        sleep(2000)
        waitFor { clinicEnterInfoText.displayed }
        clinicEnterInfoText.text().contains(text)
    }

    boolean assertClinicNameError(String msgPart) {
        clinicNameError.text().contains(msgPart)
    }

    void enterZipCode(String ZipCode) {
        zipCode = ZipCode
    }

    void enterPhone(String Phone) {
        phone = Phone
    }

    void clickContinue() {
        waitFor { browser.getCurrentUrl().contains("enroll/acknowledgement.jsf") }
        waitFor { continueBtn.displayed }
        continueBtn.click()
    }

    void clickCancelLink() {
        cancelLink.click()
    }

    void configureClinic() {
        int random = (Math.random() * Integer.MAX_VALUE).toInteger()
        String name = names[(Math.random() * names.size()).toInteger()]
        String otherName = names[(Math.random() * names.size()).toInteger()]
        String citySuffix = citySuffixes[(Math.random() * citySuffixes.size()).toInteger()]
        String streetSuffix = streetSuffixes[(Math.random() * streetSuffixes.size()).toInteger()]
        clinicName = "${name}'s Clinic ${random}"
        address1 = "${random} ${otherName}${streetSuffix}"
        zipCode = '55369'
        phone = '777-345-6789'
    }

    void validationInformationClinic() {
        List<String> informationClinicInvalid = ["<", ">", "="]
        String informationClinic = "test"
        for (String invalid : informationClinicInvalid) {
            sleep(3000)
            $(By.id("enroll_clinic:clinicname")).value(informationClinic + invalid)
            String elementval = $(By.id("enroll_clinic:clinicname")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("enroll_clinic:continue")).click()
            assertClinicNameError('Input cannot contain <, >, or =')
        }
    }

    void validationPostalCode() {
        List<String> postalCodeInvalid = ["<", ">", "&", "=", " "]
        String PostalCode = "4342"
        for (String invalid : postalCodeInvalid) {
            sleep(3000)
            $(By.id("enroll_clinic:zip")).value(PostalCode + invalid)
            String elementval = $(By.id("enroll_clinic:zip")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("enroll_clinic:continue")).click()
            String alertMessage = $(By.id("span#enroll_clinic:ziperr")).value("Input cannot contain <, >, &, or =")
            System.out.println(invalid)
            if (alertMessage.contains("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                $(By.id("span#enroll_clinic:ziperr")).value("Clinic zip is required")
                System.out.println("Error displayed")
            }
        }
    }

    void validationPhone() {
        List<String> PhoneInvalid = ["<", ">", "&", "=", " "]
        String Phone = "04366532"
        for (String invalid : PhoneInvalid) {
            sleep(3000)
            $(By.id("enroll_clinic:phone")).value(Phone + PhoneInvalid)
            String elementval = $(By.id("enroll_clinic:phone")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("enroll_clinic:continue")).click();
            String alertMessage = $(By.id("span#enroll_clinic:phoneerr")).value("Input cannot contain <, >, &, or =")
            System.out.println(invalid)
            if (alertMessage.contains("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                $(By.id("span#enroll_clinic:phoneerr")).value("Clinic phone is required")
                System.out.println("Error displayed")
            }

        }
    }

    void enrollmentClinicInformationScreen() {
        waitFor { browser.getCurrentUrl().contains("enroll/acknowledgement.jsf") }
        waitFor { clinicName.displayed }
    }

    void getClinicName() {
        println("Clinic Name: " + clinicName.getAttribute("value"))
    }
}
