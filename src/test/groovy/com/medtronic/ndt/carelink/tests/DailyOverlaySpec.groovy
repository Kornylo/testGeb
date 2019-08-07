package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper

class DailyOverlaySpec extends CareLinkSpec {
    static SignInPage signInPage
    static DailyOverlayReportPage dailyOverlayReportPage

    def 'User Login'() {
        when: 'A user supplies valid carelink credentials'
        signInPage = browsers.to SignInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.enterUsername("Akilaa")
        signInPage.enterPassword("Test1234")
        signInPage.clickOnSignIn()
    }

    def 'Search Patient'() {
        when: 'A user is logged into Carelink application'

        then: 'User should be able to search for a patient'
        signInPage.searchForPatient("Testing6")
    }

    def 'Select Patient'() {
        when: 'A user is logged into Carelink application'
        then: 'User should be able to select a patient'
        signInPage.selectFirstPatient()
    }

    def 'Open Patient'() {
        when: 'A patient is selected'
        then: 'User should be able to open patient'
        signInPage.openPatient()
    }

    def 'Check Daily Overlay in the pdf'() {
        when: 'A patient is opened and click on one of the reports'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
//        dailyOverlayReportPage.clickDailyOverlayReport()
        then:
        def file = new File("C://Users//aganda2//DailyOverlay.pdf")
        def fis = new FileInputStream(file)
        def parser = new PDFParser(fis)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        PDFTextStripper strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        println(output)
        output.contains "Daily Overlay"
        parser.getPDDocument().close()
    }
}
