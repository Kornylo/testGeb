package com.medtronic.ndt.carelink.pages.components

import geb.Module

class CheckboxModule extends Module{
    boolean isSelected() {
        getAttribute("wasChecked").toBoolean()
    }

    void setSelected(boolean selected) {
        if (isSelected() != selected) {
            click()
        }
    }
}
