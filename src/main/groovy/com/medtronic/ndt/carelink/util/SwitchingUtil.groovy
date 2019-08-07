package com.medtronic.ndt.carelink.util

import geb.Page

class SwitchingUtil extends Page {
    static at = { header.displayed }
    static content = {
        header(required: false) { $ "div.header" }
    }
    //switch to new tab - 1 first tab
    void switchToNewTab() {
        Set<String> handles = driver.getWindowHandles()
        Iterator<String> it = handles.iterator()
        while (it.hasNext()) {
            String newwin = it.next()
            driver.switchTo().window(newwin)
        }
    }

    void backToTab() {
        Set<String> handles = driver.getWindowHandles()
        Iterator<String> it = handles.iterator()
        while (it.hasNext()) {
            String parent = it.next()
            driver.switchTo().window(parent)
        }
    }
}