package com.medtronic.ndt.carelink.pages.patient

import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.pages.components.DropdownModule
import geb.Page
import org.openqa.selenium.By

class PatientStudyPage extends Page {

    static at = {
        waitFor { uploadiPro.displayed || patientInfo.displayed || patientNameInformation.displayed }
        uploadiPro.displayed || patientInfo.displayed || patientNameInformation.displayed
    }
    static content = {
        uploadiPro(required: false) { $ "input", id: "study:study" }
        uploadBGMeter(required: false) { $ "input", name: "study:j_id_id214" }
        patientInfo(required: false) { $ "input", id: "study:details" }
        editButton(required: false) { $(".change_settings > a") }
        otherOptions(required: false) {
            $(By.xpath('//*[contains(@id,\'study:study_actions\')]')).module(DropdownModule)
        }
        studyClose(required: false) { $ "input", id: "study:close" }
        patientName(required: false) { $(id: "patientName")}
        printAllReportsButton(required: false) { $ id: "study:print_general" }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
        patientIprologo(required: false) { $('h1.iprologo') }
        patientHomeLabel(required: false) { $('.tab_home') }
        patientLabelPatient(required: false) { $('#current > a > span') }
        patientLabelMyInfo(required: false) { $('.nav_right > ul > li:nth-child(2)') }
        patientLabelSignOut(required: false) { $('.nav_right > ul:nth-child(1) > li:nth-child(1') }
        patientFooter(required: false) { $('div.btm_info') }
        patientHeader(required: false) { $('.nav_gray') }
        patientNameInformation(required: false) { $(".nav_gray > h2:nth-child(1)") }
        patientIDlabel { $('#patientInfo > fieldset:nth-child(6) > label:nth-child(4) > span:nth-child(1)') }
        firstNamelabel { $('#patientInfo > fieldset:nth-child(6) > label:nth-child(1) > span:nth-child(1)') }
        lastNamelabel { $('#patientInfo > fieldset:nth-child(6) > label:nth-child(2) > span:nth-child(1)') }
        dateofBirthlabel { $('.dob_field > span:nth-child(1)') }
        patientEmaillabel { $('#patientInfo > fieldset:nth-child(7) > label:nth-child(1) > span:nth-child(1)') }
        physicianNamelabel { $('#patientInfo > fieldset:nth-child(7) > label:nth-child(2) > span:nth-child(1)') }
        diabetesTypelabel {
            $('#patientDiabetesTherapyPanel > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(1) > td:nth-child(1) > span:nth-child(1) > span:nth-child(1)')
        }
        therapyTypelabel { $('#patientDiabetesTherapyPanel > table> tbody > tr:nth-child(4) > td ') }
        cancelButton { $(id: "patient_details:cancelBtn") }
        confirmButton { $(id: "patient_details:saveBtn") }
        editPatientButton { $(id: "study:details") }
        patientIdData (required: false) {$('.rightinput').find(id:'patient_details:patientId') }
    }

    void patientFooterDisplayed() {
        waitFor { $('.btm_info').displayed }
    }

    void patientLabelSignOutText(String signOut) {
        waitFor 15, { $('.nav_right > ul:nth-child(1) > li:nth-child(1)').displayed }
        assert $('.nav_right > ul:nth-child(1) > li:nth-child(1)').text().contains(signOut)
    }

    void patientLabelMyInfoText(String myInfo) {
        assert patientLabelMyInfo.text().contains(myInfo)
    }

    void patientLabelPatientText(String patient) {
        assert patientLabelPatient.text().contains(patient)
    }

    void patientHomeLabelText(String home) {
        assert patientHomeLabel.text().contains(home)
    }

    void patientHomeClick() {
        waitFor { patientHomeLabel.displayed }
        patientHomeLabel.find('a').click()
    }

    void patientIprologoDisplayed() {
        assert patientIprologo.displayed
    }

    void patientClinicInfoCss() {
        waitFor { $("div.header > h1:nth-child(3)").css("text-align") == "center" }
    }

    void patientHeaderDisplayed() {
        waitFor { patientHeader.displayed }
        waitFor {patientName.displayed}
    }

    void patientLogoDisplayed() {
        waitFor { medtronicLogo.displayed }
    }

    void isUploadiProDisplayed() {
        waitFor { title == "Patient Record Page" }
        waitFor { uploadiPro.displayed }

    }

    void clickOnUploadPatient() {
        uploadiPro.click()
    }

    void clickOnUploadBGMeter() {
        uploadBGMeter.click()
    }

    void moveEvaluation() {
        waitFor {
            otherOptions.selectOption('Move this evaluation')
        }
    }

    void exitPatient() {
        waitFor { studyClose.displayed }
        studyClose.click()
    }

    void patientNameInformationForFiveBrowser(String lastName, String firstName, String patientID) {
        assert $(".nav_gray > h2:nth-child(1)").text().contains(lastName)
        assert $(".nav_gray > h2:nth-child(1)").text().contains(firstName)
        assert $(".nav_gray > h2:nth-child(1)").text().contains(patientID)
    }

    void patientNameInformationDisplayed() {
        waitFor {$(id:'patient_details:firstName').displayed}
        waitFor { patientNameInformation.displayed }
        sleep(1500)
        String label = ReadProperties.getProperties().get("ip.patient.text.confirm.information")
        assert patientNameInformation.text().contains(label)
    }

    void verifyUserName(String name) {
        waitFor { patientName.displayed }
        assert patientName.text().contains(name)
    }

    void checkPrintAllReports() {
        waitFor { printAllReportsButton.displayed }
        assert printAllReportsButton.getAttribute("href").contains("print=ALL")
    }

    void diabetesTypelabelDisplayed() {
        waitFor {$('.diabetesTypeCheckboxes').displayed}
        assert diabetesTypelabel.text().contains('Diabetes Type')
        assert $('.diabetesTypeCheckboxes>tbody>tr:nth-child(1)').text().contains('Type 1')
        assert $('.diabetesTypeCheckboxes>tbody>tr:nth-child(2)').text().contains('Type 2')
        assert $('.diabetesTypeCheckboxes>tbody>tr:nth-child(3)').text().contains('Other')

        $('.diabetesTypeCheckboxes>tbody>tr:nth-child(1)').find('input', type: 'radio').click()
        $(id:'patient_details:diabetesType').value().toString().contains('TYPE1')
        $('.diabetesTypeCheckboxes>tbody>tr:nth-child(2)').find('input', type: 'radio').click()
        $(id:'patient_details:diabetesType').value().toString().contains('TYPE2')
        $('.diabetesTypeCheckboxes>tbody>tr:nth-child(3)').find('input', type: 'radio').click()
        $(id:'patient_details:diabetesType').value().toString().contains('OTHER')
    }

    void physicianNamelabelDisplayed(String physicianNameText) {
        assert physicianNamelabel.text() == physicianNameText
    }

    void patientEmaillabelDisplayed(String patientEmailText) {
        assert patientEmaillabel.text() == patientEmailText
    }

    void dateofBirthlabelDisplayed(String dateofBirthText) {
        assert dateofBirthlabel.text() == dateofBirthText
        assert $(id: "patient_details:month").displayed
        assert $(id: "birthday").displayed
        assert $(id: "birthyear").displayed
    }

    void lastNamelabelDisplayed(String lastNameText) {
        assert lastNamelabel.text() == lastNameText
    }

    void firstNamelabelDisplayed(String firstNameText) {
        assert firstNamelabel.text() == firstNameText
    }
    void patientFirstName(String name){
        waitFor {$(id:'patient_details:firstName').displayed}
        assert $(id:'patient_details:firstName').value().toString().contains(name)
    }

    void patientIDlabelDisplayed(String patientIDText) {
        waitFor { patientIDlabel.displayed }
        assert patientIDlabel.text() == patientIDText
    }

    void therapyTypeLabelDisplayed() {
        assert therapyTypelabel.text().replace('\n', ' ').contains('Therapy Type')
        assert $("tbody > tr:nth-child(1) > td:nth-child(2) > span").text().contains("Diet and Exercise")
        assert $("tbody > tr:nth-child(2) > td:nth-child(2) > span").text().contains("Oral Medication(s)")
        assert $("tbody > tr:nth-child(4) > td:nth-child(2) > span").text().contains("Non-insulin Injectable")
        assert $("tbody > tr:nth-child(6) > td:nth-child(2) > span").text().contains("Basal Insulin")
        assert $("tbody > tr:nth-child(7) > td:nth-child(2) > span").text().contains("Meal-time Insulin")
        assert $("tbody > tr:nth-child(8) > td:nth-child(2) > span").text().contains("Premix Insulin")
        assert $("tbody > tr:nth-child(9) > td:nth-child(2) > span").text().contains("Insulin Pump")
        assert $("tbody > tr:nth-child(10) > td:nth-child(2) > span").text().contains("Other")
    }

    void clickEditButton() {
        waitFor { editButton.displayed }
        editButton.click()
    }

    void clickCancelButton() {
        waitFor { cancelButton.displayed }
        cancelButton.text() == "Cancel"
        cancelButton.click()
    }

    void closePatientIsNotDisplayed() {
        waitFor { !$("#last > input") }
    }

    void clickConfirmButton() {
        waitFor { confirmButton.displayed }
        confirmButton.value() == ReadProperties.getProperties().get("ip.button.confirm")
        confirmButton.click()
    }

    void clickEditPatientButton() {
        waitFor { editPatientButton.displayed }
        editPatientButton.click()
    }

    void verifyTitle() {
        waitFor { $("#box_con > h3").displayed }
        assert $("#box_con > h3").text() == "Patient Information"
    }

    void verifyCancelButton() {
        waitFor { cancelButton.displayed }
        cancelButton.text() == "Cancel"
    }

    void verifyClosePatientButton() {
        assert $("#last > input").value() == "Close patient"
    }

    void verifyMessage(String text1, String text2, String text3){
        waitFor { $("#box_con > h3").displayed }
        assert $("#patient_details > div.box_con_right > div > p:nth-child(7)").displayed
        assert $("#patient_details > div.box_con_right > div > p:nth-child(7)").text().contains(text1)
        assert $("#patient_details > div.box_con_right > div > p:nth-child(7)").text().contains(text2)
        assert $("#patient_details > div.box_con_right > div > p:nth-child(7)").text().contains(text3)
    }

    void checkPatientID(String id) {
        assert patientIdData.value().toString().contains(id)
    }
    void regenerateReports() {
        $('select.grn_blue').value(2)
    }
    void openLogBookFromPatient() {
        $('#box_con > div.main_left > div:nth-child(5) > div > input').click()
    }
}

