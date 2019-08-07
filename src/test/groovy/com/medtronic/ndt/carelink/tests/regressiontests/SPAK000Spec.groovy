package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.pages.reports.PatientReportSettingsPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 000 - No Calibration Algorithm Test')
class SPAK000Spec extends CareLinkSpec {
    static SignInPage signInPage
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static PatientReportSettingsPage patientReportSettingsPage

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, "/patient",
                ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder_Data_Upload_Alg"() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        ApiRequestsSpak.recorderDataUpload(204,ApiPropertySpak.accountUserName1,ApiPropertySpak.accountPassword1,ApiPropertySpak.deviceId1,
                ApiPropertySpak.recorderSn1, ApiPropertySpak.rawEis,ApiPropertySpak.rawIsig1, ApiPropertySpak.rawIsig2,'2012-08-26T01:01:00.567Z',"/snapshot")
    }

    def 'End A Study 2'() {
        when: "End A Study 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200,"/study/end",ApiPropertySpak.hcpAccountUserName1,ApiPropertySpak.hcpAccountPassword1,ApiPropertySpak.recorderSn1,true)
    }

    def 'Check Patient on UI'() {
        when: 'Log into CareLink and choose the newly created patient and click on [Open patient] button.'
        signInPage = browsers.to SignInPage
        signInPage.enterUsername(ApiPropertySpak.hcpAccountUserName1)
        signInPage.enterPassword(ApiPropertySpak.hcpAccountPassword1)
        signInPage.clickOnSignIn()
        homePage = browsers.at HomePage
        homePage.homeScreenIsAppeared()
        homePage.clickOnSearchEntryBox()
        waitFor { homePage.pageComplete() }
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        homePage.selectPatientFromList()
        homePage.openPatient()
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.patientNameInformationDisplayed()
        patientStudyPage.checkPatientID(ApiPropertySpak.patientID1)
        then: 'Click on [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        patientStudyPage.patientHeaderDisplayed()
        when: 'Click on Edit link.'
        patientReportSettingsPage = browsers.at PatientReportSettingsPage
        patientReportSettingsPage.patientEditClick()
        waitFor {patientReportSettingsPage.glucoseUnitsMg.displayed}
        and: 'Set the Glucose Units to mg/dL. When the pop-up appears click on [OK] button.'
        patientReportSettingsPage.glucoseUnitsMg.click()
        and: 'Click on [Save] button. When the pop-up appears click on [Yes] button.'
        patientReportSettingsPage.saveReportSettings()
        patientReportSettingsPage.successRegenerateReportsDialog('SUCCESS!')
        and: 'Click on the Other Options drop down and select the Generate Data Table option.'
        patientReportSettingsPage.openGenerateDataTable("Generate Data Table")
        and: 'Click on the Data Table report link.'
        and: 'Verify the system provides a Data Table.'
        and: 'Verify that Sensor Glucose values are present.'
        and: 'Verify that all values in Table 1: "Expected Glucose Value Outputs" which are expected to be “--- [---]” match the result displayed in the Data Table.'
        then: 'Verify that all values in Table 1: "Expected Glucose Value Outputs" which have values which are not “--- [---]” are within 1 mg/dL of the result displayed in the Data Table and the actual results are not equal to “--- [---]”.'
        patientReportSettingsPage.openDataTableReport("Glucose(mg/dL)", "--- [599.34nA]2:11:00 AM")
    }
}
