package com.medtronic.ndt.carelink.pages.clinic

import geb.Page

class ClinicUsersPage extends Page{
    static  at = { users.displayed || closeUsers.displayed }
    static content = {
        users (required:false){$ "a", id: "j_id_id15:staffTab"}
        closeUsers(required:false){$ "a", id: "staff:close"}
        newUser(required: false){$"input", name: "staff:j_id_id48"}
        cancelNewUser(required: false){$'#userform a'}
        openUser(required: false){$"input", name:"staff:j_id_id47"}
    }
    void clickNewUser() {
        newUser.click()
    }
    void clickCancelNewUser() {
        cancelNewUser.click()
    }
    void clickOnUsers() {
        users.click()
    }
    boolean closeUsersDisplayed() {
        closeUsers.displayed
    }
    void clickOnCloseUsers() {
        closeUsers.click()
    }
}
