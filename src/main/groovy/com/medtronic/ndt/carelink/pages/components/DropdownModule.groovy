package com.medtronic.ndt.carelink.pages.components

import geb.Module
import geb.navigator.Navigator

class DropdownModule extends Module {

    List<Navigator> getOptions() {
        $('option').collect {it}
    }
    void selectOption(String option) {
        click()
        getOptions().find {it.text() == option}.click()
    }

}
