package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('SPAK 818 - Get Logbook')
class SPAK818Spec extends CareLinkSpec {
    static Precondition precondition
    static HomePage homePage
    static CreatePatientPage createPatientPage
    static LogbookPage logbookPage
    static PatientStudyPage patientStudyPage

    static final admin = Calendar.getInstance().format('MMddHHmmss').toString().toLowerCase()
    static final admin2 = '2' + admin
    static final email = admin + '@ap1.com'
    static final email2 = admin2 + '@ap1.com'
    def pass = "Test1234@"
    def pass2 = "Test1234@2"

    def 'Create Clinic'() {
        when: 'Open the MMT-7340 application (CareLink Software).'
        precondition = browsers.to(Precondition)
        and: 'Record the server URL address:'
        then:
        'Click on the Register Clinic link. Create a new clinic and record the info:\n' +
                'Clinic name'
        and: 'Create a new administrative user and record the info:'
        precondition.registerClinic(admin, LanguagePage.setCountry(), email, pass)
        println('Username: ' + admin)
        println('Password: ' + pass)
    }

    def 'Sign in the Application'() {
        when: 'Sign in the MMT-7340'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin, pass)
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Create Patient 2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, clinicIdUi, ApiPropertySpak.deviceId1,
                ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def "Recorder Data Upload 1"() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : "$ApiPropertySpak.deviceId1",
                            status       : 30,
                            recorderSn   : "$ApiPropertySpak.recorderSn1",
                            recorderModel: 'MMT-7781',
                            startTime    : '2015-12-21T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, "/snapshot", payload)
    }

    def "Get LogBook 4"() {
        when: 'Get LogBook 4'
        then: 'Check response code = 200'
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def 'SQL Queries'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window observe that AuditEvent is created: there is a row with GET_PATIENT_EVENTS value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_PATIENT_EVENTS')
    }

    def "Event Creation 2, Patient 1"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn1,
                ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, ApiPropertySpak.eventId.get(0))
    }

    def 'End A Study 2'() {
        when: 'End A Study 2'
        then: 'Check response code = 200'
        ApiRequestsSpak.endAStudy(200, "/study/end", admin, pass, ApiPropertySpak.recorderSn1, true)
    }

    def 'Verify patient on UI'() {
        when: 'Navigate to MMT-7340 application and login to clinic using credentials from step 2762502. '
        patientStudyPage = browsers.at(PatientStudyPage)
        homePage.refreshViaSettings()
        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        and: 'Double-click on the newly created patient in the clinic.'
        homePage.openPatientByDoubleClick()
        patientStudyPage.patientNameInformationDisplayed()
        patientStudyPage.patientFirstName(ApiPropertySpak.firstName1)
        and: 'Click on the [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        patientStudyPage.patientHeaderDisplayed()
        createPatientPage = browsers.at CreatePatientPage
        createPatientPage.valueNewPatient(ApiPropertySpak.lastName1, ApiPropertySpak.firstName1)
        assert createPatientPage.patientName.text().contains(ApiPropertySpak.patientID1)
        and: 'Click on [Open Logbook] button.'
        logbookPage = browsers.at LogbookPage
        logbookPage.openLogBook()
        then: 'Click on the [Add] button.'
        logbookPage.clickAddButton()
        logbookPage.addNewRecordDialog()
        and: 'Enter 10:10 AM into the “Time” field, select “Large” meal and enter 100 into the “Carbs” field.'
        logbookPage.enterTime('10:10', "'AM'")
        logbookPage.selectMealSize('large')
        logbookPage.enterCarbs('100')
        then: 'Click on the [Enter] button and close “Add new record” window.'
        logbookPage.enterButton.click()
        logbookPage.confirmationDialog()
        logbookPage.closePopup.click()
        and: 'Click on the [Done] button.'
        logbookPage.closeLogBook()
    }

    def "Get LogBook 1"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 200'
        def dataMap = [type: 'Meal', size: 'L', carbs: 100.0, source: 'LOGBOOK']
        ApiRequestsSpak.getLogBook(200, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, dataMap, 8)
    }

    def 'SQL Queries 2'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window observe that AuditEvent is created: there is a row with GET_PATIENT_EVENTS value in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_PATIENT_EVENTS')
    }

    def "Get LogBook 2"() {
        when: 'Get LogBook 2'
        then: 'Check response code = 401'
        ApiRequestsSpak.getLogBook(401, "/logbook/${ApiPropertySpak.recorderSn1}", '', '', '', '')
    }

    def "Get LogBook 1.1"() {
        when: 'Get LogBook 1'
        then: 'Check response code = 403'
        ApiRequestsSpak.getLogBook(403, "/logbook/${ApiPropertySpak.recorderSn1}", admin, pass, '', '')
    }

    def 'Create new Clinic'() {
        when: ''
        logbookPage.clickSignOut()
        waitFor { browser.getCurrentUrl().contains('login') }
        and: 'Click on the Register Clinic link. Create a new clinic.'
        precondition.registerClinic(admin2, LanguagePage.setCountry(), email2, pass2)
        then: 'Create a new administrative user and record the info:'
        println('Username: ' + admin2)
        println('Password: ' + pass2)
    }

    def 'Sign in the Application 2'() {
        when: 'Sign in the MMT-7340'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        precondition.signInAsClinicAdmin(admin2, pass2)
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        then: 'Record the clinic Id.'
        homePage.getClinicIdFromHomePage()
    }

    def "Account creation 1.2 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/auth/create', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def "Create Patient 2.2 - Successfully created patient"() {
        when: 'Create Patient'
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, clinicIdUi, ApiPropertySpak.deviceId2,
                ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def "Recorder Data Upload 1.2"() {
        when: 'Data Uploading'
        then: 'Check response code = 204'
        def listSnapshots = [rawIsig    : ApiPropertySpak.rawIsig1 + ApiPropertySpak.rawIsig2,
                             rawEis     : ApiPropertySpak.rawEis,
                             startOffset: 1,
                             endOffset  : 10050]
        def listMetadata = [appVersion   : '1.0',
                            deviceId     : "$ApiPropertySpak.deviceId2",
                            status       : 30,
                            recorderSn   : "$ApiPropertySpak.recorderSn2",
                            recorderModel: 'MMT-7781',
                            startTime    : '2012-08-01T18:02:12.567Z']
        def payload = new JsonBuilder(snapshots: [listSnapshots], metadata: listMetadata)
        println(payload.toPrettyString())
        ApiRequestsSpak.recorderDataUploadGeneral(204, ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, "/snapshot", payload)
    }

    def "Event Creation 2, Patient 2"() {
        when: 'Event Creation 2'
        then: 'Check response code = 204'
        ApiRequestsSpak.eventCreation(204, '/logbook', ApiPropertySpak.recorderSn2,
                ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, ApiPropertySpak.eventId2.get(0))
    }

    def "Get LogBook 3"() {
        when: 'Get LogBook 3'
        then: 'Check response code = 400'
        ApiRequestsSpak.getLogBook(400, "/logbook/${ApiPropertySpak.recorderSn1}", ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }
}
