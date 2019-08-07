package com.medtronic.ndt.carelink.pages.reports

import geb.Page
import org.openqa.selenium.By

class PSReportPage extends Page{

    static at ={
        patientInfo.displayed
    }
    static content ={
        patientInfo(required:false){$"input", id:"study:details"}
        psReport(required: false){$ (By.xpath('//*[contains(@id,\'study:PS_title\')]'))}
    }

    void clickPSReport() {
        psReport.click()
    }

}
