package com.medtronic.ndt.carelink.pages.clinic


import geb.Page
import org.openqa.selenium.By

class ClinicInformationPage extends Page {

    static at = { countryTerritory.displayed }

    static content = {
        clinicInformation(required: false) { $('a', id: ~/(.*?)informationTab/) }
        countryTerritory(required: false) { $(id: 'country-span') }
        languageDropdown(required: false) { $('.fieldset-overflowed').find(id: contains(':language')) }
        clinicNameError(required: false) { $(By.xpath('//*[@id=\'j_id_id23:clinicnameerr\']')) }
        address1Error(required: false) { $(By.xpath('//*[@id=\'j_id_id23:address1err\']')) }
        address2Error(required: false) { $(By.xpath('//*[@id=\'j_id_id23:address2err\']')) }
        cityError(required: false) { $(By.xpath('//*[@id=\'j_id_id23:cityerr\']')) }
    }


    void clickOnClinicInformation() {
        waitFor { clinicInformation.displayed }
        clinicInformation.click()
    }

    boolean assertClinicNameError(String msgPart) {
        clinicNameError.text().contains(msgPart)
    }

    boolean assertAddress1Error(String msgPart) {
        address1Error.text().contains(msgPart)
    }

    boolean assertAddress2Error(String msgPart) {
        address2Error.text().contains(msgPart)
    }

    boolean assertCityError(String msgPart) {
        cityError.text().contains(msgPart)
    }

    void validationInformationClinic() {
        List<String> informationClinicInvalid = ["<", ">", "="]
        String informationClinic = "test"
        for (String invalid : informationClinicInvalid) {
            sleep(3000)
            $(By.id("j_id_id23:clinicName")).value(informationClinic + invalid)
            String elementval = $(By.id("j_id_id23:clinicName")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("j_id_id23:save")).click()
            assertClinicNameError('Input cannot contain <, >, or =')
        }
    }

    void validationAddress1() {
        List<String> informationClinicInvalid = ["<", ">", "="]
        String informationClinic = "test"
        for (String invalid : informationClinicInvalid) {
            sleep(3000)
            $(By.id("j_id_id23:address1")).value(informationClinic + invalid)
            String elementval = $(By.id("j_id_id23:address1")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("j_id_id23:save")).click()
            assertAddress1Error('Input cannot contain <, >, or =')
        }
    }

    void validationAddress2() {
        List<String> informationClinicInvalid = ["<", ">", "="]
        String informationClinic = "test"
        for (String invalid : informationClinicInvalid) {
            sleep(3000)
            $(By.id("j_id_id23:address2")).value(informationClinic + invalid)
            String elementval = $(By.id("j_id_id23:address2")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("j_id_id23:save")).click()
            assertAddress2Error('Input cannot contain <, >, or =')
        }
    }

    void validationCity() {
        List<String> informationClinicInvalid = ["<", ">", "="]
        String informationClinic = "test"
        for (String invalid : informationClinicInvalid) {
            sleep(3000)
            $(By.id("j_id_id23:city")).value(informationClinic + invalid)
            String elementval = $(By.id("j_id_id23:city")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("j_id_id23:save")).click()
            assertCityError('Input cannot contain <, >, or =')
        }
    }

    void validationZipCode() {
        List<String> postalCodeInvalid = ["<", ">", "&", "=", " "]
        String PostalCode = "4342"
        for (String invalid : postalCodeInvalid) {
            sleep(3000)
            $(By.id("j_id_id23:zip")).value(PostalCode + invalid)
            String elementval = $(By.id("j_id_id23:zip")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("j_id_id23:save")).click()
            String alertMessage = $(By.id("span#j_id_id23:ziperr")).value("Input cannot contain <, >, &, or =")
            System.out.println(invalid)
            if (alertMessage.contains("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                $(By.id("span#j_id_id23:ziperr")).value("Clinic zip is required")
                System.out.println("Error displayed")
            }
        }
    }

    void validationPhone() {
        List<String> PhoneInvalid = ["<", ">", "&", "=", " "]
        String Phone = "04366532"
        for (String invalid : PhoneInvalid) {
            sleep(3000)
            $(By.id("j_id_id23:phone")).value(Phone + PhoneInvalid)
            String elementval = $(By.id("j_id_id23:phone")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("j_id_id23:save")).click()
            String alertMessage = $(By.id("span#j_id_id23:phoneerr")).value("Input cannot contain <, >, &, or =")
            System.out.println(invalid)
            if (alertMessage.contains("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                $(By.id("span#j_id_id23:phoneerr")).value("Clinic phone is required")
                System.out.println("Error displayed")
            }

        }
    }
}
