package com.medtronic.ndt.carelink.pages.HCP

import com.medtronic.ndt.carelink.pages.components.DropdownModule
import geb.Page
import org.junit.Assert
import org.openqa.selenium.By

class MyInfoPage extends Page {

    static at = { myInfo.displayed }
    static content = {
        //My Info link
        myInfo(required: false) { $('.nav_right a', 1) }
        myInfoClose(required: false) { $("a", id: "userform:close") }
        myInfoText(required: false) { $ '.nav_gray h2' }
        username(required: false) { $(By.xpath('//label[@for=\'userform:username\']')) }
        securityQuestions(required: false) { $('select', id: 'userform:question').module(DropdownModule) }
        securityQuestionLabel(required: false) { $(By.xpath('//label[@for=\'userform:question\']/span[1]')) }
        firstName(required: false) { $ 'input', id: 'userform:firstname' }
        firstNameLabel(required: false) { $(By.xpath('//label[@for=\'userform:firstname\']')) }
        lastName(required: false) { $ 'input', id: 'userform:lastname' }
        lastNameLabel(required: false) { $(By.xpath('//label[@for=\'userform:lastname\']')) }
        email(required: false) { $ 'input', id: 'userform:email' }
        emailLabel(required: false) { $(By.xpath('//label[@for=\'userform:email\']')) }
        saveMyInfo(required: false) { $ 'input', id: 'userform:save' }
        firstNameValidationError(required: false) { $(By.xpath('//label[@for=\'userform:firstname\']/span[3]')) }
        lastNameValidationError(required: false) { $(By.xpath('//label[@for=\'userform:lastname\']/span[3]')) }
        emailValidationError(required: false) { $(By.xpath('//label[@for=\'userform:email\']/span[3]')) }
        securityAnswerValidationError(required: false) { $(By.xpath('//label[@for=\'userform:answer\']/span[3]')) }
        labelPasswordStrengthInfo(required: false) { $ '' }
        validationMessageConfirmPassword(required: false) { $ '' }
        securityAnswer(required: false) { $ 'input', id: 'userform:answer' }
        securityAnswerLabel(required: false) { $(By.xpath('//label[@for=\'userform:answer\']')) }
        changePassword(required: false) { $('input', id: 'userform:changePassword') }
        currentPassword(required: false) { $ 'input', id: 'userform:currentpassword' }
        newPassword(required: false) { $ 'input', id: 'userform:password' }
        renterNewPassword(required: false) { $ 'input', id: 'userform:confirmpassword' }
        currentPasswordValidationError(required: false) { $(By.xpath('//*[@id=\'userform:currentPassworderror\']')) }
        newPasswordValidationError(required: false) { $(By.xpath('//*[@id=\'userform:passworderror\']')) }
        confirmPasswordValidationError(required: false) { $(By.xpath('//*[@id=\'userform:passwordConfirmerror\']')) }
        addEmailAddressButton(required: false) {
            $("#box_con2 > div.box_patient_create > fieldset:nth-child(5) > label:nth-child(3) > span.rightinput > a")
        }
        addEmailAddressField(required: false) { $(id: "email:newemail") }
        sendEmailNowButton(required: false) { $(id: "email:confirm-email") }
        titleEmailScreen(required: false) { $("#main > div.main-title > h2") }
        twoFactorAuthenticationEmail(required: false) {
            $("fieldset:nth-child(5) > label:nth-child(3) > span.rightinput.mfa-number")
        }
        changeTwoFactorAuthenticationEmail(required: false) {
            $("fieldset:nth-child(5) > label:nth-child(3) > span.mfa-button > a")
        }
        clinicSettingsTab(required: false) { $(By.xpath("//div[@id='tab']/ul/li[@class='tab_clinic']/a")) }
    }

    void clickOnMyInfo() {
        waitFor { myInfo.displayed }
        myInfo.click()
    }

    void clickOnMyInfoDisplayed() {
        waitFor { myInfo.displayed }
    }

    void clickChangePassword() {
        changePassword.click()
    }

    boolean assertMyInfoText() {
        waitFor 10, 1, {
            myInfoText.text().contains('My Info')
        }
    }

    void myInfoCloseDisplayed() {
        waitFor { myInfoClose.displayed }
    }

    void changeSecurityQuestion() {
        securityQuestions.selectOption('Pets Name')
    }

    boolean verifySecurityQuestions() {
        println(securityQuestions.getOptions().collect { it.text() })
        (securityQuestions.getOptions().collect { it.text() }).contains('Mothers Maiden Name')
        (securityQuestions.getOptions().collect { it.text() }).contains('Mothers Birth Date [day,month]')
        (securityQuestions.getOptions().collect { it.text() }).contains('Pets Name')
        (securityQuestions.getOptions().collect { it.text() }).contains('Favorite Sports Team')
    }

    void enterFirstName(String firstname) {
        waitFor 10, 1, {
            firstName.value(firstname)
        }
    }

    boolean verifyFirstNameLength() {
        Assert.assertEquals(40, firstName.text().length())
    }

    boolean verifyFirstNameLabel() {
        Assert.assertEquals('First Name*', (firstNameLabel.text()).trim())
    }

    boolean verifyLastNameLabel() {
        Assert.assertEquals('Last Name*', (lastNameLabel.text()).trim())
    }

    boolean verifyEmailLabel() {
        Assert.assertEquals('Email*', (emailLabel.text()).trim())
    }

    boolean verifySecurityQuestionLabel() {
        Assert.assertEquals('Security Question*', (securityQuestionLabel.text()).trim())
    }

    boolean verifySecurityAnswerLabel() {
        Assert.assertEquals('Security Answer*', (securityAnswerLabel.text()).trim())
    }

    boolean assertFirstNameError(String msgPart) {
        firstNameValidationError.text().contains(msgPart)
    }

    void enterLastName(String lastname) {
        lastName.value(lastname)
    }

    boolean verifyLastNameLength() {
        Assert.assertEquals(40, lastName.text().length())
    }

    boolean assertLastNameError(String msgPart) {
        lastNameValidationError.text().contains(msgPart)
    }

    void enterEmail(String Email) {
        email.value(Email)
    }

    boolean assertEmailError(String msgPart) {
        emailValidationError.text().contains(msgPart)
    }

    void enterSecurityAnswer(String Answer) {
        securityAnswer.value(Answer)
    }

    boolean assertSecurityAnswerError(String msgPart) {
        securityAnswerValidationError.text().contains(msgPart)
    }

    boolean assertCurrentPasswordError(String msgPart) {
        currentPasswordValidationError.text().contains(msgPart)
    }

    boolean assertNewPasswordError(String msgPart) {
        newPasswordValidationError.text().contains(msgPart)
    }

    boolean assertConfirmPasswordError(String msgPart) {
        confirmPasswordValidationError.text().contains(msgPart)
    }

    void clickSaveMyInfo() {
        saveMyInfo.click()
    }

    void closeMyInfo() {
        waitFor { myInfoClose.displayed }
        myInfoClose.click()
    }

    void firstNameValidation() {
        List<String> invalidFirstName = ["<", ">", "&", "="]
        String firstName = "ddd"
        for (String invalid : invalidFirstName) {
            sleep(3000)
            $(By.id("userform:firstname")).value(firstName + invalid)
            String elementval = $(By.id("userform:firstname")).getAttribute("value")
            if (elementval.length() <= 40) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:save")).click()
            assertFirstNameError('Input cannot contain <, >, &, or =')
        }
    }

    void lastNameValidation() {
        List<String> invalidLastName = ["<", ">", "&", "="]
        String lastName = "ddd"
        for (String invalid : invalidLastName) {
            sleep(3000)
            $(By.id("userform:lastname")).value(lastName + invalid)
            String elementval = $(By.id("userform:lastname")).getAttribute("value")
            if (elementval.length() <= 40) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:save")).click()
            assertLastNameError('Input cannot contain <, >, &, or =')
        }
    }

    void emailValidation() {
        List<String> invalidEmail = ["<", ">", "&", "=", " "]
        String email = "ddd"
        for (String invalid : invalidEmail) {
            sleep(300)
            $(By.id("userform:email")).value(email + invalid + "gmail.com")
            String elementval = $(By.id("userform:email")).getAttribute("value")
            if (elementval.length() <= 80) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:save")).click()
            assertEmailError('Invalid email id')
        }
    }

    void securityAnswerValidation() {
        List<String> securityAnswer = ["<", ">", "&", "="]
        String answer = "test"
        for (String invalid : securityAnswer) {
            sleep(3000)
            $(By.id("userform:answer")).value(answer + invalid)
            String elementval = $(By.id("userform:answer")).getAttribute("value")
            if (elementval.length() <= 40) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:save")).click()
            assertSecurityAnswerError('Input cannot contain <, >, &, or =')
        }
    }

    void passwordValidation() {
        List<String> passwordCheck = ["1222"]
        String currentPassword = "12 "
        String assignTemporaryPassword = "31 "
        String reenterTemporaryPassword = "122 "
        for (String invalid : passwordCheck) {
            sleep(3000)
            $(By.id("userform:currentpassword")).value(currentPassword + invalid)
            $(By.id("userform:password")).value(assignTemporaryPassword + invalid)
            $(By.id("userform:confirmpassword")).value(reenterTemporaryPassword + invalid)
            sleep(500)
            $(By.id("userform:save")).click()
            sleep(4)
            String currentPassworderror = $(By.id("span#userform:currentPassworderror")).value("Incorrect current password")
            String passworderror = $(By.id("span#userform:passworderror")).value("Password should be a minimum of 8 characters")
            String passwordConfirmerror = $(By.id("span#userform:passwordConfirmerror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (passworderror.contains("Password should be a minimum of 8 characters")) {
                currentPassworderror.contains("Incorrect current password")
                passwordConfirmerror.contains("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            } else {
                $(By.id("span#userform:currentPassworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
                $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
                $(By.id("span#userform:passwordConfirmerror")).value("Passwords are not equal")
            }
        }

    }

    void verifyMFA(email, firstname, lastname, answer) {
        waitFor { $(By.id("userform:firstname")).displayed }
        assert $(By.id("userform:firstname")).value() == firstname
        assert $(By.id("userform:lastname")).value() == lastname
        assert $(By.id("userform:email")).value() == email
        assert $(By.id("userform:answer")).value() == answer
        assert !$(By.xpath("//span[@id='two-factor-table']/table/tbody/tr/td/input[@value='true']")).displayed
        assert !$('a', id: ~/(.*?)informationTab/).displayed
        assert !$("#box_con > div.box_staff_left > table > thead > tr > th:nth-child(5)").displayed
        assert !$("#userform > fieldset:nth-child(5) > label:nth-child(4) > span:nth-child(2)").displayed
        assert $("#box_con2 > div.box_patient_create > fieldset:nth-child(5) > label:nth-child(2) > span").displayed
        assert addEmailAddressButton.displayed
    }

    void addEmailAddress(email) {
        addEmailAddressButton.click()
        waitFor { titleEmailScreen.text() == "Two-Factor Authentication Required" }
        addEmailAddressField.value(email)
        waitFor { sendEmailNowButton.displayed }
        sendEmailNowButton.click()
    }

    void verifyEmail(email) {
        waitFor { myInfoText.displayed }
        twoFactorAuthenticationEmail.text() == email
        changeTwoFactorAuthenticationEmail.click()
        titleEmailScreen.text() == "Two-Factor Authentication Required"
        waitFor { $(By.xpath("//div[@class='row-btns']/div/form/a")).displayed }
        $(By.xpath("//div[@class='row-btns']/div/form/a")).click()
        waitFor { myInfoText.displayed }
    }

    void openClinicSettingsTab() {
        waitFor { clinicSettingsTab.displayed }
        clinicSettingsTab.click()
        waitFor { browser.getCurrentUrl().contains("myinfo.jsf") }
    }

    void verifyTwoFactorAuthenSectionIsNotDisplayed() {
        waitFor { $(By.id("userform:firstname")).displayed }
        assert !twoFactorAuthenticationEmail.displayed
    }
}
