package com.medtronic.ndt.carelink.pages.patient

import geb.Page

class MoveEvaluationPage extends Page{

    static at ={
        moveStudyCancel.displayed
    }
    static content ={
        moveStudyFirstName(required: false){$"input", id:"move_study_dialog:first_name"}
        moveStudyLastName(required: false){$"input", id:"move_study_dialog:last_name"}
        moveStudyPatientId(required: false){$"input", id:"move_study_dialog:patient_ID"}
        moveStudySearch(required: false){$"input", id:"move_study_dialog:search_now"}
        moveStudySelectPatient(required: false){$"input", id:"list:openPatientBtn"}
        moveStudyConfirmationDialog(required: false){$"input", id:"move_study_confirmation_dialog:search_now"}
        moveStudyCancel(required:false){$ "a", id:"move_study_dialog:cancel"}
        cancelLink(required: false){$"a", id:"list:cancel"}
    }
    void enterMoveStudyFirstName() {
        moveStudyFirstName.value('test')
    }
    void enterMoveStudyLastName() {
        moveStudyLastName.value('test')
    }
    void enterMoveStudyPatientID() {
        moveStudyPatientId.value('121211')
    }
    void clickMoveStudySearch() {
        moveStudySearch.click()
    }
    void clickSelectPatient() {
        moveStudySelectPatient.click()
    }
    void clickConfirmationDialog() {
        moveStudyConfirmationDialog.click()
    }
    void clickCancelLink() {
        cancelLink.click()
    }
    void clickOnMoveStudyCancel() {
        moveStudyCancel.click()
    }
}
