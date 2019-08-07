package com.medtronic.ndt.carelink.tests

import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.context.SmokeTest
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@SmokeTest
@Stepwise
@Slf4j
@Title('SCUI258 - Patient Record Screen - Upload iPro button')
class SCUI258Spec extends CareLinkSpec{
    static SignInPage signInPage
    static PatientStudyPage patientStudyPage
    def 'User Login' () {
        when: 'A user supplies valid carelink credentials'
        signInPage = browsers.to SignInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.enterUsername("Akilaa")
        signInPage.enterPassword("Test1234")
        signInPage.clickOnSignIn()
    }
    def 'Search Patient' () {
        when: 'A user is logged into Carelink application'

        then: 'User should be able to search for a patient'
        signInPage.searchForPatient("Testing7")
    }
    def 'Select Patient' () {
        when: 'A user is logged into Carelink application'
        then: 'User should be able to select a patient'
        signInPage.selectFirstPatient()
    }
    def 'Open Patient' () {
        when: 'A patient is selected'
        then: 'User should be able to open patient'
        signInPage.openPatient()
        sleep(100)
    }
    def 'Upload iPro patient data' () {

        when:
        patientStudyPage = browsers.at PatientStudyPage
        then:
        patientStudyPage.clickOnUploadPatient()
        Runtime.getRuntime().exec("C:\\Users\\aganda2\\Uploader.exe")
        sleep(1000)
        Runtime.getRuntime().exec("C:\\Users\\aganda2\\Uploader.exe").destroy()
        browsers.page PatientStudyPage
        report "Patient study page"
    }
    def 'Upload BG Meter data' () {

        when:
        patientStudyPage = browsers.at PatientStudyPage
        then:
        patientStudyPage.clickOnUploadBGMeter()
        Runtime.getRuntime().exec("C:\\Users\\aganda2\\BGMeterUploader.exe")
        sleep(1000)
        browsers.page PatientStudyPage
    }
    def 'Close Patient' () {
        when: 'A patient is opened'
        then: 'User should be able to close patient'
        signInPage.closePatient()
    }
    def 'User Logout' () {
        when:'A user is logged into carelink'

        then: 'User should be able to sign out from carelink'
        assert signInPage.signoutButtonDisplayed()
        signInPage.clickOnSignOutLink()
    }
}
