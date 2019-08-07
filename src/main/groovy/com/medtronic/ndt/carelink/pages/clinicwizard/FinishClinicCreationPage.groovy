package com.medtronic.ndt.carelink.pages.clinicwizard


import geb.Page
import org.openqa.selenium.By

class FinishClinicCreationPage extends Page{

    private static By finishBtnBy = By.xpath('//input[contains(@id,\'enroll_finish\')]')
    static content = {
        finishBtn(required:false){$("input", id:"enroll_finish:finish")}
    }
    static at = {
        waitInIE()  || driver.findElement(finishBtnBy).displayed
    }
    void clickFinish() {
        waitFor {finishBtn.displayed}
        finishBtn.click()
    }

    void waitInIE() {
            waitFor 15, {finishBtn.displayed}
    }
    void verifyEnrollmentCompleteScreen(String congrats) {
        waitFor { browser.getCurrentUrl().contains("enroll/user.jsf") }
        waitFor{finishBtn.displayed}
        assert title == "Enroll Finish Page"
        assert  $(".mdtlogo").displayed
        assert  $(".iprologo").displayed
        assert !$(id: "footer_submenu_r").displayed
        assert $(id:"enroll_finish").find("h2").text()=="Enrollment Complete"
        assert $(id:"enroll_finish").find(".middle-contact>p").getAt(0).text()== congrats
        assert $(id:"enroll_finish").find(".middle-contact>p").getAt(1).text()=="Click on the Finish button to return to the System Welcome page where you can login using your username and password"
    }

    void chechFinishButton() {
        assert finishBtn.value() == "Finish"
    }
}
