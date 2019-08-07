package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiBodySpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Slf4j
@RegressionTest
@Title('SPAK 807 - Create Patient')
class SPAK807Spec extends CareLinkSpec {

    static Precondition precondition
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static CreatePatientPage createPatientPage
    static final admin1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString().toLowerCase()
    static final emailAddress1 = '1' + Calendar.getInstance().format('MMMddHHmmss').toString() + '@1medtronictest.com'
    static final password = 'Test1234@'

    def 'Create Patient'() {
        when: "Create Patient"
        then: 'Check response code = 401'
        ApiRequestsSpak.createPatientEmpty(401, '/patient')
    }

    def 'Account Creation 1'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def 'Create Patient 2'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'SQL Queries 1'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'GET_ACCOUNT')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName1, 'NEW_PATIENT_AND_STUDY')
    }

    def 'Sign in via UI and verify created Patient'() {
        when: 'Navigate back to the Carelink application and click on “Home” tab.'
        precondition = browsers.to(Precondition)
        precondition.signInAsClinicAdmin(ApiPropertySpak.hcpAccountUserName1, ApiPropertySpak.hcpAccountPassword1)
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        then: 'Verify that the newly created patient is bolded.'
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        homePage.selectBoldPatientFromList('bold')
        homePage.openPatient()
        when: ''
        patientStudyPage = browsers.at(PatientStudyPage)
        and: 'Verify that the software navigates to the Confirm Patient Information Screen.'
        patientStudyPage.patientNameInformationDisplayed()
        and: 'Click on [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        then: ''
        patientStudyPage.patientHeaderDisplayed()
    }

    def 'Create Patient 2-2'() {
        when: "Create Patient 2"
        then: 'Check response code = 500'
        ApiRequestsSpak.createPatient(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'Create Patient 2-3'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn2, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1, ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'Verify created Patient '() {
        when: 'Navigate back to the Carelink application and click on “Home” tab.'
        patientStudyPage.patientHomeClick()
        homePage.listLoaded()
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        then: 'Verify that the newly created patient is bolded.'
        homePage.selectBoldPatientFromList('bold')
        and: 'Select bolded patient and click on [Open patient] button.'
        homePage.openPatient()
        and: 'Verify that the software navigates to the Confirm Patient Information Screen.'
        patientStudyPage.patientNameInformationDisplayed()
        and: 'Click on [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        patientStudyPage.patientHeaderDisplayed()
    }

    def 'Create Patient 2-4'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn3, ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId1, 'FirstName', ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'Verify created Patient 2'() {
        when: 'Navigate back to the Carelink application and click on “Home” tab.'
        patientStudyPage.patientHomeClick()
        homePage.listLoaded()
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        then: 'Verify that the newly created patient is bolded.'
        homePage.selectBoldPatientFromList('bold')
        and: 'Select bolded patient and click on [Open patient] button.'
        homePage.openPatient()
        and: 'Verify that the software navigates to the Confirm Patient Information Screen.'
        patientStudyPage.patientNameInformationDisplayed()
        and: 'Verify that Patient record information was updated according the JSON file.'
        patientStudyPage.patientFirstName('FirstName')
    }

    def 'Account Creation 1-2'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2, ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 6'() {
        when: "Create Patient 6"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1 + '2', ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2, ApiPropertySpak.firstName2, ApiPropertySpak.lastName2, ApiPropertySpak.patientID2, ApiPropertySpak.accountUserName2)
    }

    def 'Account Creation 1-3'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '2', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 3'() {
        when: "Create Patient 6"
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1 + '3', ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2.replace('22', '44'), ApiPropertySpak.firstName2 + '2', ApiPropertySpak.lastName2 + '2',
                ApiPropertySpak.patientID2 + '2', ApiPropertySpak.accountUserName2 + '2')
    }

    def 'Patient Login 2'() {
        when: "Patient Login 2"
        then: 'Verify that the API provided a method for the client to verify an account\'s credentials and determined if the mobile device in use has changed for the account\'s active study.'
        ApiRequestsSpak.patientLogin(200, '/auth/login/patient', ApiPropertySpak.accountUserName2 + '2', ApiPropertySpak.accountPassword2,
                ApiPropertySpak.deviceId2.replace('22', '44'), ApiPropertySpak.recorderSn1 + '3', ApiPropertySpak.patientID2 + '2', ApiPropertySpak.clinicId1)
    }

    def 'SQL Queries 1-2'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '2', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '2', 'GET_ACCOUNT')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '2', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-4'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '3', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 4'() {
        when: "Create Patient 4"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(ApiPropertySpak.recorderSn1 + '4',
                ApiPropertySpak.deviceId2.replace('22', '55'),
                ApiPropertySpak.firstName2 + '2', ApiPropertySpak.lastName2 + '2',
                ApiPropertySpak.patientID2 + '3', ApiPropertySpak.accountUserName2 + '3', '60', 'ja@ty.sk', '2015-03-21', 'Other', 'MMT-7781', 'Dr.House', 'ORAL', '')
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Create Patient 5'() {
        when: "Create Patient 5"
        then: 'Check response code = 401'
        ApiRequestsSpak.createPatient(401, ApiPropertySpak.envisionAccountUserName, 'eNpzcgz2dFZILC3JSM0ryUxOLMnMz1NIS8zMSU3RUShKLSktyktNUSguSSwpLVZIzk9JVTAxMAAAA2US0A',
                '/patient', ApiPropertySpak.recorderSn1 + '3', ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2.replace('22', '44'), ApiPropertySpak.firstName2 + '2', ApiPropertySpak.lastName2 + '2',
                ApiPropertySpak.patientID2 + '2', ApiPropertySpak.accountUserName2 + '3')
    }

    def 'Account Creation 1-5'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '4', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 7'() {
        when: "Create Patient 7"
        then: 'Check response code = 400'
        ApiRequestsSpak.createPatient(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1 + '4', ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2.replace('22', '55'), '', ApiPropertySpak.lastName2 + '3',
                ApiPropertySpak.patientID2 + '3', ApiPropertySpak.accountUserName2 + '4')
    }

    def 'Account Creation 1-6'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '5', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 8'() {
        when: "Create Patient 8"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(ApiPropertySpak.recorderSn1 + '4',
                ApiPropertySpak.deviceId2.replace('22', '55'),
                ApiPropertySpak.firstName2 + '2', ApiPropertySpak.lastName2 + '2',
                ApiPropertySpak.patientID2 + '3', ApiPropertySpak.accountUserName2 + '5', '6.0', 'ja@ty.sk', '2015-03-21', 'Other', 'MMT-7781', 'Dr.House', 'ORAL', ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-7'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '6', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Account Creation 1-9'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '7', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 10'() {
        when: "Create Patient 10"
        then: 'Check response code = 500'
        ApiRequestsSpak.createPatient(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1 + '5', ApiPropertySpak.clinicId1, ApiPropertySpak.deviceId2.replace('22', '55'), 'Jakolakjnkfiosdnhgionadsflsdnkgpiasndpioansfpadfnpaomadsfgfgd',
                ApiPropertySpak.lastName2 + '3',
                ApiPropertySpak.patientID2 + '7', ApiPropertySpak.accountUserName2 + '7')
    }

    def 'Account Creation 1-10'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '8', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 11'() {
        when: "Create Patient 10"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '8',
                ApiPropertySpak.deviceId2.replace('22', '48'),
                'Lolodwgdfhgfjrtwjhkghkhkhfdjrgsfjfsghkghlljhfjlhfjlhjfdasfrg',
                'hsgfjgfsjrskjsghhjgfsjgfjfhjtajstyjxvhkgtsdgdsgdsgsgghtashgf',
                'ID2022196168498161846131684984564319848961631964984561655555',
                ApiPropertySpak.accountUserName2 + '8',
                '60',
                'jaerfhdfhdfshdgfjdfgdsfddfsgdfhdfshdgfshgdfhjgfjsasgsdg@tydsggfjgfjgfjgf@jdgdfgdfaehadafadfghafdh.sk',
                '2015-03-21',
                'TYPE_1',
                'MMT-7781',
                'Dr.Housedfnhgfsjjjjjjjjjjjjjdfabdgsjrgfskghfskffghkgfngfaktulghmnartkudjasfasfdgsgsgfaegreayhraejdjs',
                'DIET_EXERCISE',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-3'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '8', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '8', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '8', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-11'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '9', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 12'() {
        when: "Create Patient 12"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '9',
                ApiPropertySpak.deviceId2.replace('22', '49'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '9',
                ApiPropertySpak.accountUserName2 + '9',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'TYPE_2',
                'MMT-7781',
                'Dr.House',
                'INJECTABLE',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-4'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '9', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '9', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '9', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-12'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '10', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 13'() {
        when: "Create Patient 13"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '10',
                ApiPropertySpak.deviceId2.replace('22', '50'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '10',
                ApiPropertySpak.accountUserName2 + '10',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'GESTATIONAL',
                'MMT-7781',
                'Dr.House',
                'PREMIX',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-5'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '10', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '10', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '10', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-13'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '11', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 14'() {
        when: "Create Patient 14"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '11',
                ApiPropertySpak.deviceId2.replace('22', '51'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '11',
                ApiPropertySpak.accountUserName2 + '11',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                '123sdfgs546',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-6'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '11', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '11', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '11', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-14'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '12', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 15'() {
        when: "Create Patient 15"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '12',
                ApiPropertySpak.deviceId2.replace('22', '52'),
                '<',
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '12',
                ApiPropertySpak.accountUserName2 + '12',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-15'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '13', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 16'() {
        when: "Create Patient 16"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '13',
                ApiPropertySpak.deviceId2.replace('22', '53'),
                '>',
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '13',
                ApiPropertySpak.accountUserName2 + '13',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-16'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '14', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 17'() {
        when: "Create Patient 17"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '14',
                ApiPropertySpak.deviceId2.replace('22', '54'),
                '&',
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '14',
                ApiPropertySpak.accountUserName2 + '14',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-17'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '15', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 18'() {
        when: "Create Patient 18"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '15',
                ApiPropertySpak.deviceId2.replace('22', '55'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '15',
                ApiPropertySpak.accountUserName2 + '15',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'BASAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-7'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '15', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '15', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '15', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-18'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '16', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 19'() {
        when: "Create Patient 19"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '16',
                ApiPropertySpak.deviceId2.replace('22', '56'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '16',
                ApiPropertySpak.accountUserName2 + '16',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'BOLUS',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-8'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '16', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '16', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '16', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-19'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '17', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 20'() {
        when: "Create Patient 20"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '17',
                ApiPropertySpak.deviceId2.replace('22', '57'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '17',
                ApiPropertySpak.accountUserName2 + '17',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'PUMP',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-9'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '17', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '17', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '17', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-20'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '18', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 21'() {
        when: "Create Patient 21"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '18',
                ApiPropertySpak.deviceId2.replace('22', '58'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '18',
                ApiPropertySpak.accountUserName2 + '18',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'Other',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-10'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '18', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '18', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '18', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-21'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '19', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 22'() {
        when: "Create Patient 22"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '19',
                ApiPropertySpak.deviceId2.replace('22', '59'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '19',
                ApiPropertySpak.accountUserName2 + '19',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                '')
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-22'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '20', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 23'() {
        when: "Create Patient 23"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '20',
                ApiPropertySpak.deviceId2.replace('22', '60'),
                '',
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '20',
                ApiPropertySpak.accountUserName2 + '20',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-23'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '21', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 24'() {
        when: "Create Patient 24"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '21',
                ApiPropertySpak.deviceId2.replace('22', '61'),
                '=',
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '21',
                ApiPropertySpak.accountUserName2 + '21',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-24'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '22', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 25'() {
        when: "Create Patient 25"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '22',
                ApiPropertySpak.deviceId2.replace('22', '62'),
                ApiPropertySpak.firstName1,
                '',
                ApiPropertySpak.patientID1 + '22',
                ApiPropertySpak.accountUserName2 + '22',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-25'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '23', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 26'() {
        when: "Create Patient 26"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '23',
                ApiPropertySpak.deviceId2.replace('22', '63'),
                ' ',
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '23',
                ApiPropertySpak.accountUserName2 + '23',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-26'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '24', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 27'() {
        when: "Create Patient 27"
        then: 'Check response code = 500'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '24',
                ApiPropertySpak.deviceId2.replace('24', '64'),
                ApiPropertySpak.firstName1,
                'hsgfjgfsjrskjsghhjgfsjgfjfhjtajstyjxvhkgtsdgdsgdsgsgghtashgfs',
                ApiPropertySpak.patientID1 + '24',
                ApiPropertySpak.accountUserName2 + '24',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-27'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '25', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 28'() {
        when: "Create Patient 28"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '25',
                ApiPropertySpak.deviceId2.replace('24', '65'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                '',
                ApiPropertySpak.accountUserName2 + '25',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-11'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '25', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '25', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '25', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-28'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '26', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 29'() {
        when: "Create Patient 29"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '26',
                ApiPropertySpak.deviceId2.replace('24', '66'),
                ApiPropertySpak.firstName1,
                '<',
                ApiPropertySpak.patientID1 + '26',
                ApiPropertySpak.accountUserName2 + '26',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-29'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '27', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 30'() {
        when: "Create Patient 30"
        then: 'Check response code = 500'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '27',
                ApiPropertySpak.deviceId2.replace('24', '67'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                'ID20221961684981618461316849845613198489616319649845616555556',
                ApiPropertySpak.accountUserName2 + '27',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-30'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '28', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 31'() {
        when: "Create Patient 31"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '28',
                ApiPropertySpak.deviceId2.replace('24', '68'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '28',
                ApiPropertySpak.accountUserName2 + '28',
                '60',
                '',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-12'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '28', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '28', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '28', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-31'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '29', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 32'() {
        when: "Create Patient 32"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '29',
                ApiPropertySpak.deviceId2.replace('24', '69'),
                ApiPropertySpak.firstName1,
                '>',
                ApiPropertySpak.patientID1 + '29',
                ApiPropertySpak.accountUserName2 + '29',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-32'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '30', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 33'() {
        when: "Create Patient 33"
        then: 'Check response code = 500'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '30',
                ApiPropertySpak.deviceId2.replace('24', '70'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '30',
                ApiPropertySpak.accountUserName2 + '30',
                '60',
                'jaerfhdfhdfshdgdfjdfgdsfddfsgdfhdfshdgfshgdfhjgfjsasgsdg@tydsggfjgfjgfjgfsjdgdfgdfaehadafadfghafdh.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-33'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '31', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 34'() {
        when: "Create Patient 34"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '31',
                ApiPropertySpak.deviceId2.replace('24', '71'),
                ApiPropertySpak.firstName1,
                '&',
                ApiPropertySpak.patientID1 + '31',
                ApiPropertySpak.accountUserName2 + '31',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-34'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '32', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 35'() {
        when: "Create Patient 35"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '32',
                ApiPropertySpak.deviceId2.replace('24', '72'),
                ApiPropertySpak.firstName1,
                '=',
                ApiPropertySpak.patientID1 + '32',
                ApiPropertySpak.accountUserName2 + '32',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-35'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '33', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 36'() {
        when: "Create Patient 36"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '33',
                ApiPropertySpak.deviceId2.replace('24', '73'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '33',
                ApiPropertySpak.accountUserName2 + '33',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                '',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'SQL Queries 1-13'() {
        when: 'Run the statement.'
        then: 'In the “Query Result” window ensure that AuditEvent is created: Check the columns in the “Query Result” window. There are NEW _PATIENT_AND_STUDY, GET_ACCOUNT, GET_CLINIC values in the SUB_TYPE column.'
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '33', 'NEW_PATIENT_AND_STUDY')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '33', 'GET_CLINIC')
        DataBaseRequestsUtil.qa_Queries_1(ApiPropertySpak.accountUserName2 + '33', 'GET_ACCOUNT')
    }

    def 'Account Creation 1-36'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '34', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 37'() {
        when: "Create Patient 37"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '34',
                ApiPropertySpak.deviceId2.replace('24', '74'),
                ApiPropertySpak.firstName1,
                ' as',
                ApiPropertySpak.patientID1 + '34',
                ApiPropertySpak.accountUserName2 + '34',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-37'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '35', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 38'() {
        when: "Create Patient 38"
        then: 'Check response code = 500'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '35',
                ApiPropertySpak.deviceId2.replace('24', '75'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '35',
                ApiPropertySpak.accountUserName2 + '35',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.Housedfnhgfsjjjjjjjjjjjjjdfabdgsjrgfskghafskffghkgfngfaktulghmnartkudjasfasfdgsgsgfaegreayhraedjdj',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-38'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '36', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 39'() {
        when: "Create Patient 39"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                '',
                ApiPropertySpak.deviceId2.replace('24', '76'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '36',
                ApiPropertySpak.accountUserName2 + '36',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-39'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '37', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 40'() {
        when: "Create Patient 40"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '37',
                ApiPropertySpak.deviceId2.replace('24', '77'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                '<',
                ApiPropertySpak.accountUserName2 + '37',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-40'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '38', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 41'() {
        when: "Create Patient 41"
        then: 'Check response code = 500'
        String body = ApiBodySpak.createPatientGeneral(
                '15470467165156161615656198111559632565614',
                ApiPropertySpak.deviceId2.replace('24', '78'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '38',
                ApiPropertySpak.accountUserName2 + '38',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-41'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '39', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 42'() {
        when: "Create Patient 42"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '39',
                ApiPropertySpak.deviceId2.replace('24', '79'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '39',
                ApiPropertySpak.accountUserName2 + '39',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                '',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-42'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '40', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 43'() {
        when: "Create Patient 43"
        then: 'Check response code = 200'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '40',
                ApiPropertySpak.deviceId2.replace('24', '80'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '40',
                ApiPropertySpak.accountUserName2 + '40',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7782',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-43'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '41', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 44'() {
        when: "Create Patient 44"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '41',
                ApiPropertySpak.deviceId2.replace('24', '81'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '41',
                ApiPropertySpak.accountUserName2 + '41',
                'abcdefg',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-44'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '42', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 45'() {
        when: "Create Patient 45"
        then: 'Check response code = 400'
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '42',
                ApiPropertySpak.deviceId2.replace('24', '82'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '42',
                ApiPropertySpak.accountUserName2 + '42',
                true,
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                ApiPropertySpak.clinicId1)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Register new clinic'() {
        when: ''
        patientStudyPage.patientHomeClick()
        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        homePage.clickSignOutButton()
        waitFor { browser.getCurrentUrl().contains('login') }
        and: 'Navigate to the application.'
        then: 'Click on Register Clinic link.'
        and: 'Create a new clinic and record the info below:'
        precondition.registerClinic(admin1, LanguagePage.setCountry(), emailAddress1, password)
        println('Login: ' + admin1 + ' Password: ' + password)
    }

    def 'Sign in to application'() {
        when: 'Sign into the MMT-7340 application using the credentials above.'
        precondition.signInAsClinicAdmin(admin1, password)
        homePage = browsers.at(HomePage)
        then: 'Record clinicId.'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        println('ClinicId: ' + clinicIdUi)
        and: 'Click on [New patient] button.'
        homePage.clickNewPatientBtn()
        when: ''
        createPatientPage = browsers.at CreatePatientPage
        then: """Fill in all required fields and record the info below:
            First name:
            Last name:
            Patient ID:
            Date of birth:
            Diabetes Type:
            Therapy Type:"""
        createPatientPage.firstName.value('FirstName')
        createPatientPage.lastName.value('LastName')
        createPatientPage.id.value(ApiPropertySpak.patientID1 + '43')
        createPatientPage.monthDropdown.click()
        createPatientPage.monthDropdown.value('March')
        createPatientPage.dayDropdown.click()
        createPatientPage.dayDropdown.value('20')
        createPatientPage.yearDropdown.click()
        createPatientPage.yearDropdown.value('1978')
        createPatientPage.selectDiabetesType()
        createPatientPage.enterPatientEmail(ApiPropertySpak.accountUserName2 + '43')
        createPatientPage.physicianEnter('test')
        createPatientPage.selectTherapyTypeInsulinPump()
        and: 'Click on [Save] button.'
        createPatientPage.clickSaveBtn()
        createPatientPage.valueNewPatient('LastName', 'FirstName')
        assert createPatientPage.patientName.text().contains(ApiPropertySpak.patientID1 + '43')
        createPatientPage.goToList()
        homePage.getClinicIdFromHomePage()
    }

    def 'Account Creation 1-45'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '43', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 2-1'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '43',
                ApiPropertySpak.deviceId2.replace('24', '83'),
                'FirstName',
                'LastName',
                ApiPropertySpak.patientID1 + '43',
                ApiPropertySpak.accountUserName2 + '43',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'TYPE_2',
                'MMT-7781',
                'Dr.House',
                'PUMP',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Verify patient on UI'() {
        when: 'Navigate back to the application and click on “Home” tab.'
        patientStudyPage = browsers.at(PatientStudyPage)
        homePage.refreshViaSettings()
        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        and: 'From the patient list choose the patient which was created by API and click on [Open patient] button.'
        homePage.searchInput(ApiPropertySpak.patientID1 + '43')
        homePage.searchInputResult(ApiPropertySpak.patientID1 + '43')
        homePage.selectPatientFromList()
        homePage.openPatient()
        patientStudyPage.patientNameInformationDisplayed()
        patientStudyPage.patientFirstName('FirstName')
        and: 'Click on the [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        patientStudyPage.patientHeaderDisplayed()
        createPatientPage.valueNewPatient('LastName', 'FirstName')
        assert createPatientPage.patientName.text().contains(ApiPropertySpak.patientID1 + '43')
        and: 'Click on “Edit patient information” link.'
        createPatientPage.editPatientInformationClick()
        createPatientPage.pageLoaded()
        then: 'Verify that patient record information was updated to match the information provided by client and data are consistent.'
        assert createPatientPage.physicianNameLabel.value().toString().contains('Dr.House')
        assert createPatientPage.id.value().toString().contains(ApiPropertySpak.patientID1 + '43')
        assert createPatientPage.email.value().toString().contains('ja@ty.sk')
        and: 'Click on [Close patient] button.'
        createPatientPage.closePatient('Close patient')
    }

    def 'Create new Patient from UI'() {
        when: 'Click on [New patient] button.'
        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        homePage.clickNewPatientBtn()
        then: """Fill in all required fields and record the info below:
            First name:
            Last name:
            Patient ID:
            Date of birth:
            Diabetes Type:
            Therapy Type:"""
        createPatientPage.pageLoaded()
        createPatientPage.firstName.value('Vasya')
        createPatientPage.lastName.value('Pupik')
        createPatientPage.id.value(ApiPropertySpak.patientID1 + '44')
        createPatientPage.monthDropdown.click()
        createPatientPage.monthDropdown.value('May')
        createPatientPage.dayDropdown.click()
        createPatientPage.dayDropdown.value('20')
        createPatientPage.yearDropdown.click()
        createPatientPage.yearDropdown.value('2000')
        createPatientPage.selectDiabetesType()
        createPatientPage.enterPatientEmail(ApiPropertySpak.accountUserName2 + '44')
        createPatientPage.physicianEnter('test')
        createPatientPage.selectTherapyTypeInsulinPump()
        then: 'Click on [Save] button.'
        createPatientPage.clickSaveBtn()
        createPatientPage.valueNewPatient('Vasya', 'Pupik')
        createPatientPage.goToList()
        homePage.getClinicIdFromHomePage()
    }

    def 'Create Patient 2-5'() {
        when: "Create Patient 2"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '44',
                ApiPropertySpak.deviceId2.replace('24', '83'),
                'PupikFirst',
                'VasyaLast',
                ApiPropertySpak.patientID1 + '44',
                '',
                '60',
                'justemail@email1.sk',
                '1946-08-27',
                'TYPE_2',
                'MMT-7781',
                'Dr.House',
                'PUMP',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Verify patient on UI 2'() {
        when: 'Navigate back to the application and click on “Home” tab.'
        homePage.refreshViaSettings()

        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        and: 'From the patient list choose the patient which was created by API and click on [Open patient] button.'
        homePage.searchInput('PupikFirst')
        homePage.searchInputResult('PupikFirst')
        homePage.selectPatientFromList()
        homePage.openPatient()
        patientStudyPage.patientNameInformationDisplayed()
        patientStudyPage.patientFirstName('PupikFirst')
        and: 'Click on the [Confirm] button.'
        patientStudyPage.clickConfirmButton()
        patientStudyPage.patientHeaderDisplayed()
        createPatientPage.valueNewPatient('VasyaLast', 'PupikFirst')
        and: 'Click on “Edit patient information” link.'
        createPatientPage.editPatientInformationClick()
        createPatientPage.pageLoaded()
        then: 'Verify that patient record information was updated to match the information provided by client and data are consistent.'
        assert createPatientPage.physicianNameLabel.value().toString().contains('Dr.House')
        assert createPatientPage.id.value().toString().contains('')
        assert createPatientPage.email.value().toString().contains('justemail@email1.sk')
        and: 'Click on [Close patient] button.'
        createPatientPage.closePatient('Close patient')
        homePage.listLoaded()
        homePage.getClinicIdFromHomePage()
        homePage.clickNewPatientBtn()
    }

    def 'Account Creation 1-46'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '45', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 46'() {
        when: "Create Patient 46"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '45',
                ApiPropertySpak.deviceId2.replace('24', '84'),
                '<',
                '>',
                ApiPropertySpak.patientID1 + '45',
                ApiPropertySpak.accountUserName2 + '45',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-47'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '46', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 47'() {
        when: "Create Patient 47"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '46',
                ApiPropertySpak.deviceId2.replace('24', '85'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                '>',
                ApiPropertySpak.accountUserName2 + '46',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-48'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '47', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 48'() {
        when: "Create Patient 48"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '47',
                ApiPropertySpak.deviceId2.replace('24', '86'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                '&',
                ApiPropertySpak.accountUserName2 + '47',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-49'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '48', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 49'() {
        when: "Create Patient 49"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '48',
                ApiPropertySpak.deviceId2.replace('24', '87'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                '=',
                ApiPropertySpak.accountUserName2 + '48',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-50'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '49', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 50'() {
        when: "Create Patient 50"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '49',
                ApiPropertySpak.deviceId2.replace('24', '88'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ' f  s',
                ApiPropertySpak.accountUserName2 + '49',
                '60',
                'ja@ty.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-51'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '50', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 51'() {
        when: "Create Patient 51"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '50',
                ApiPropertySpak.deviceId2.replace('24', '89'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '50',
                ApiPropertySpak.accountUserName2 + '50',
                '60',
                '<',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-52'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '51', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 52'() {
        when: "Create Patient 52"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '51',
                ApiPropertySpak.deviceId2.replace('24', '90'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '51',
                ApiPropertySpak.accountUserName2 + '51',
                '60',
                '>',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-53'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '52', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 53'() {
        when: "Create Patient 53"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '52',
                ApiPropertySpak.deviceId2.replace('24', '91'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '52',
                ApiPropertySpak.accountUserName2 + '52',
                '60',
                '&',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-54'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '53', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 54'() {
        when: "Create Patient 54"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '53',
                ApiPropertySpak.deviceId2.replace('24', '92'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '53',
                ApiPropertySpak.accountUserName2 + '53',
                '60',
                '=',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-55'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '54', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 55'() {
        when: "Create Patient 55"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '54',
                ApiPropertySpak.deviceId2.replace('24', '93'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '54',
                ApiPropertySpak.accountUserName2 + '54',
                '60',
                ' ',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-56'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '55', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 56'() {
        when: "Create Patient 56"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '54',
                ApiPropertySpak.deviceId2.replace('24', '93'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '54',
                ApiPropertySpak.accountUserName2 + '54',
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                '<',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-57'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '56', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 57'() {
        when: "Create Patient 57"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '56',
                ApiPropertySpak.deviceId2.replace('24', '94'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '56',
                ApiPropertySpak.accountUserName2 + '56',
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                '>',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-58'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '57', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 58'() {
        when: "Create Patient 58"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '57',
                ApiPropertySpak.deviceId2.replace('24', '95'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '57',
                ApiPropertySpak.accountUserName2 + '57',
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                '&',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-59'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '58', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 59'() {
        when: "Create Patient 59"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '58',
                ApiPropertySpak.deviceId2.replace('24', '96'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '58',
                ApiPropertySpak.accountUserName2 + '58',
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                '=',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-60'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '59', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 60'() {
        when: "Create Patient 60"
        then: 'Check response code = 200'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '59',
                ApiPropertySpak.deviceId2.replace('24', '97'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '59',
                ApiPropertySpak.accountUserName2 + '59',
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                '  f sd',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-61'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '60', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 61'() {
        when: "Create Patient 61"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '60',
                ApiPropertySpak.deviceId2.replace('24', '98'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '60',
                'slovakia@slovak.sk',
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }

    def 'Account Creation 1-62'() {
        when: "Account Creation 1"
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/auth/create', ApiPropertySpak.accountUserName2 + '61', ApiPropertySpak.accountPassword2, '', '')
    }

    def 'Create Patient 62'() {
        when: "Create Patient 62"
        then: 'Check response code = 400'
        String clinicIdUi = homePage.clinicId.text().replaceAll('.*: |\\)', '')
        String body = ApiBodySpak.createPatientGeneral(
                ApiPropertySpak.recorderSn1 + '61',
                ApiPropertySpak.deviceId2.replace('24', '99'),
                ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1,
                ApiPropertySpak.patientID1 + '61',
                admin1,
                '60',
                'justemail@email1.sk',
                '2015-03-21',
                'Other',
                'MMT-7781',
                'Dr.House',
                'ORAL',
                clinicIdUi)
        ApiRequestsSpak.createPatientGeneral(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword, '/patient', body)
    }
}
