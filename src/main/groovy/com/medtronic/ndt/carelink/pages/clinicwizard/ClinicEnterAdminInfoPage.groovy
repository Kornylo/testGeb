package com.medtronic.ndt.carelink.pages.clinicwizard

import com.medtronic.ndt.carelink.pages.components.DropdownModule
import geb.Page
import org.openqa.selenium.By

class ClinicEnterAdminInfoPage extends Page {

    public static List<String> names = ['Akila', 'Kate', 'Angie', 'Paul', 'Sean', 'Katie', 'Sri', 'Shelly']
    static at = {
        continueBtn.displayed
    }
    static content = {
        clinicEnterAdminInfoText(required: false) { $('div.middle-contact') }
        userNameValue(required: false) { $("input", id: "userform:username") }
        userNameValidationError(required: false) { $(By.xpath('//*[@id=\'userform:usernameerror\']')) }
        firstNameValidationError(required: false) { $(By.xpath('//*[@id=\'userform:firstnameerror\']')) }
        lastNameValidationError(required: false) { $(By.xpath('//*[@id=\'userform:lastNameerror\']')) }
        emailError(required: false) { $(By.xpath('//*[@id=\'userform:emailerror\']')) }
        passwordError(required: false) { $(By.xpath('//*[@id=\'userform:passworderror\']')) }
        reenterPasswordError(required: false) { $(By.xpath('//*[@id=\'userform:passwordConfirmerror\']')) }
        securityAnswerError(required: false) { $(By.xpath('//*[@id=\'userform:answererror\']')) }
        firstName(required: false) { $("input", id: "userform:firstName") }
        lastName(required: false) { $("input", id: "userform:lastName") }
        email { $("input", id: "userform:email") }
        password { $("input", id: "userform:password") }
        confirmPassword { $("input", id: "userform:passwordConfirm") }
        securityQuestions(required: false) { $('select', id: 'userform:question').module(DropdownModule) }
        securityAnswer { $("input", id: "userform:answer") }
        continueBtn { $("input", id: "userform:submit") }
        finishBtn(required: false) { $("input", id: "enroll_finish:finish") }

    }

    void enterUserName() {
        int random = (Math.random() * Integer.MAX_VALUE).toInteger()
        String usernameVal = names[(Math.random() * names.size()).toInteger()]
        userNameValue.value(usernameVal + random)
    }

    String userNameValueGet() {
        String name = userNameValue.getAttribute("value")
        return name
    }

    boolean assertUserNameError(String msgPart) {
        waitFor { userNameValidationError.displayed }
        userNameValidationError.text().contains(msgPart)
    }

    void enterUserNameManual(String userNameManual) {
        waitFor { userNameValue.displayed }
        userNameValue.value(userNameManual)
        if (userNameValue.empty) {
            sleep(2000)
            userNameValue.value(userNameManual)
        }
    }

    void enterFirstName(String FirstName) {
        firstName = FirstName
    }

    boolean assertFirstNameError(String msgPart) {
        firstNameValidationError.text().contains(msgPart)
    }

    void enterLastName(String LastName) {
        lastName = LastName
    }

    boolean assertLastNameError(String msgPart) {
        lastNameValidationError.text().contains(msgPart)
    }

    void enterEmail(String Email) {
        email = Email
    }

    boolean assertEmailError(String msgPart) {
        emailError.text().contains(msgPart)
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

    void enterPassword(String Password) {
        password = Password
    }

    void enterConfirmPassword(String ConfirmPassword) {
        confirmPassword = ConfirmPassword
    }

    boolean assertPasswordError(String msgPart) {
        passwordError.text().contains(msgPart)
    }

    boolean assertReenterPasswordError(String msgPart) {
        reenterPasswordError.text().contains(msgPart)
    }

    void enterSecurityAnswer(String SecurityAnswer) {
        securityAnswer = SecurityAnswer
    }

    boolean assertSecurityAnswerError(String msgPart) {
        securityAnswerError.text().contains(msgPart)
    }

    void clickContinue() {
        waitFor { continueBtn.displayed }
        continueBtn.click()
    }

    boolean clinicEnterAdminInfoText(String text) {
        clinicEnterAdminInfoText.text().contains(text)
    }

    void usernameAdminValidation() {
        List<String> invalidChars = ["#", "!", "@", "%", "^", "&"]
        String username = "ddd"
        for (String invalid : invalidChars) {
            sleep(3000)
            $(By.id("userform:username")).value(username + invalid)
            String elementval = $(By.id("userform:username")).getAttribute("value")
            if (elementval.length() <= 16) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:submit")).click()
            assertUserNameError('Username cannot contain spaces or special characters')
        }
    }

    void firstNameAdminValidation() {
        List<String> invalidFirstName = ["<", ">", "&", "="]
        String firstName = "ddd"
        for (String invalid : invalidFirstName) {
            sleep(3000)
            $(By.id("userform:firstName")).value(firstName + invalid)
            sleep(3000)
            String elementval = $(By.id("userform:firstName")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:submit")).click()
            assertFirstNameError('Input cannot contain <, >, &, or =')
        }
    }

    void lastNameAdminValidation() {
        List<String> invalidLastName = ["<", ">", "&", "="]
        String lastName = "ddd"
        for (String invalid : invalidLastName) {
            sleep(3000)
            $(By.id("userform:lastName")).value(lastName + invalid)
            String elementval = $(By.id("userform:lastName")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:submit")).click()
            assertLastNameError('Input cannot contain <, >, &, or =')
        }
    }

    void emailAdminValidation() {
        List<String> invalidEmail = ["<", ">", "&", "=", " "]
        String email = "ddd"
        for (String invalid : invalidEmail) {
            sleep(300)
            $(By.id("userform:email")).value(email + invalid + "gmail.com")
            String elementval = $(By.id("userform:email")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:submit")).click()
            assertEmailError('Invalid email id')
        }
    }

    void securityAnswerAdminValidation() {
        List<String> securityAnswer = ["<", ">", "&", "="]
        String answer = "test"
        for (String invalid : securityAnswer) {
            sleep(3000)
            $(By.id("userform:answer")).value(answer + invalid)
            sleep(500)
            String elementval = $(By.id("userform:answer")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:submit")).click()
            assertSecurityAnswerError('Input cannot contain <, >, &, or =')
        }
    }

    void temporaryPasswordValidation() {
        List<String> invalidTemporaryPassword = ["@23452345234<"]
        String temporaryPassword = "+"
        for (String invalid : invalidTemporaryPassword) {
            sleep(3000)
            $(By.id("userform:password")).value(temporaryPassword + invalid)
            String elementval = $(By.id("userform:password")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (alertMessage.contains("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")) {
                System.out.println("Password error displayed")
            } else {
                $(By.id("span#userform:passworderror\"")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            }
        }
    }

    void waitForfinishBtn() { waitFor  { finishBtn.displayed } }

    String getUserName() {
        String userNameVal = $(By.id("userform:username")).getAttribute("value")
        return userNameVal
    }

}
