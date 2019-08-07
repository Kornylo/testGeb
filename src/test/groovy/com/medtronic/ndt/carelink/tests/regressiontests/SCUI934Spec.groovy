package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.DataBaseRequestsUtil
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title
import spock.lang.Unroll

@Title('ScUI934 - Patient information and confirmation')
@RegressionTest
@Unroll
@Screenshot
@Stepwise
@Slf4j
class SCUI934Spec extends CareLinkSpec implements ScreenshotTrait {

    static SignInPage signInPage
    static Precondition precondition
    static HomePage homePage
    static PatientStudyPage patientStudyPage
    static CreatePatientPage createPatientPage

    static final adminUs = "US" + Calendar.getInstance().format('MMMddHHmmss').toString()
    static final emailAddress = "email1" + Calendar.getInstance().format('MMM.dd.HHmmss').toString() + '@medtronictest.mailinator.com'
    static final password1 = "Test1234@"

    def 'Register new clinic & sign in'() {
        when: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink system). Record the server URL address below:'
        signInPage = browsers.to SignInPage
        log.info(driver.getCurrentUrl())
        signInPage.checkIncludedFooterElements()
        and: 'Click on Register Clinic link. Create new clinic and record the info below: Clinic Name:'
        and: 'Create a new administrative user and record the info below:'
        and: 'Username: (example: TC934)'
        and: 'Password: (example: Password1)"'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminUs, LanguagePage.setCountry(), emailAddress, password1)
        println("Username: " + adminUs + " Password: " + password1)
        and: 'Sign into the MMT-7340 application using the credentials above.'
        and: 'Record the clinicID:'
        precondition.signInAsClinicAdmin(adminUs, password1)
        homePage = browsers.at HomePage
        homePage.getClinicIdFromHomePage()
        then: ''
    }

    def 'Account Creation 1'() {
        when: 'Account creation 1'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def 'Create Patient 2'() {
        when: 'Create Patient 2.json'
        then: 'Check response code = 200'
        ApiRequestsSpak.createPatient(200, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                '/patient', ApiPropertySpak.recorderSn1, homePage.getClinicIdFromHomePage(), ApiPropertySpak.deviceId1, ApiPropertySpak.firstName1,
                ApiPropertySpak.lastName1, ApiPropertySpak.patientID1, ApiPropertySpak.accountUserName1)
    }

    def 'Verify the “Confirm Patient Information” screen'() {
        when: 'Navigate back to the “Home page” screen and click on “Home” tab.'
        homePage.refreshPage()
        then: 'Verify that the newly created pending patient is bolded.'
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        when: 'Select bolded patient and click on [Open patient] button.'
        homePage.selectBoldPatientFromList('bold')
        homePage.openPatient()
        patientStudyPage = browsers.at PatientStudyPage
        then: 'Verify that the “Confirm Patient Information” screen is displayed.'
        then: 'Verify that the Confirm Patient Information Screen displays the title “Confirm Patient Information”.'
        patientStudyPage.patientNameInformationDisplayed()
        then: 'Verify that there is editable field for first name labeled as “First Name”.'
        patientStudyPage.firstNamelabelDisplayed('First Name*')
        then: 'Verify that there is editable field for last name labeled as “Last Name”.'
        patientStudyPage.lastNamelabelDisplayed('Last Name*')
        then: 'Verify that there is editable field for patient id labeled as “Patient ID”.'
        patientStudyPage.patientIDlabelDisplayed('Patient ID*')
        then: 'Verify that there is editable field for Date of Birth labeled as “Date of Birth”.'
        then: 'Verify that the order of the selector fields for Date of Birth “Month/Day/Year”.'
        patientStudyPage.dateofBirthlabelDisplayed('Date of Birth*')
        then: 'Verify that there is editable field for patient email labeled as “Patient Email”.'
        patientStudyPage.patientEmaillabelDisplayed('Patient Email')
        then: 'Verify that there is editable field for physician name labeled as “Physician Name”.'
        patientStudyPage.physicianNamelabelDisplayed('Physician Name')
        then: 'Verify that there are 3 diabetes types: Type 1 Type 2 Other Which are selectable via radio buttons.'
        patientStudyPage.diabetesTypelabelDisplayed()
        then: 'Verify that there are this therapy types: Diet and Exercise Oral Medication(s) Non-insulin injectable Premix insulin Long-acting insulin (Basal) Short/Rapid-acting insulin (Bolus) Insulin Pump Other Which are selectable via checkboxes.'
        patientStudyPage.therapyTypeLabelDisplayed()
        then: 'Verify that there is [Cancel] link labeled as “Cancel”.'
        when: 'Click on the [Cancel] link.'
        then: 'Verify that Cancel link, labeled as “Cancel”, navigates to the Home Screen'
        patientStudyPage.clickCancelButton()
        sleep(2000)
    }

    def 'Verify the “Edit patient information” screen'() {
        when: 'Select the bolded patient again and click on [Open patient] button.'
        homePage = browsers.at HomePage
        sleep(1500)
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        homePage.selectBoldPatientFromList('bold')
        homePage.clickOpenPatientBtn()
        patientStudyPage = browsers.at PatientStudyPage
        then: 'Verify that there is no “Close Patient” Button.'
        patientStudyPage.closePatientIsNotDisplayed()
        then: 'Verify that there is Confirm button labeled as “Confirm” and click on the “Confirm” button.'
        patientStudyPage.clickConfirmButton()
        then: 'Verify that button validates each User Entry Field and alerts the user of any field failing to meet the validation criteria.'
        then: 'Verify that if validation passes, the system stores the entered data in the [Patient_Info] record and navigates to the Patient Record Screen for the patient.'
        patientStudyPage.patientHeaderDisplayed()

        when: 'Click on the “Edit patient information” link.'
        patientStudyPage.clickEditPatientButton()
        then: 'Verify that the Patient Information Screen displays title “Patient Information”.'
        patientStudyPage.verifyTitle()
        then: 'Verify that there is editable field for first name labeled as “First Name”.'
        patientStudyPage.firstNamelabelDisplayed('First Name*')
        then: 'Verify that there is editable field for last name labeled as “Last Name”.'
        patientStudyPage.lastNamelabelDisplayed('Last Name*')
        then: 'Verify that there is editable field for patient id labeled as “Patient ID”.'
        patientStudyPage.patientIDlabelDisplayed('Patient ID*')
        then: 'Verify that there is editable field for Date of Birth labeled as “Date of Birth”.'
        then: 'Verify that the order and format of the selector fields for Date of Birth are in the format “MM DD YYYY”.'
        patientStudyPage.dateofBirthlabelDisplayed('Date of Birth*')
        then: 'Verify that there is editable field for patient email labeled as “Patient Email”.'
        patientStudyPage.patientEmaillabelDisplayed('Patient Email')
        then: 'Verify that there is editable field for physician name labeled as “Physician Name”.'
        patientStudyPage.physicianNamelabelDisplayed('Physician Name')
        then: 'Verify that there are 3 diabetes types: Type 1 Type 2 Other Which are selectable via radio buttons.'
        patientStudyPage.diabetesTypelabelDisplayed()
        then: 'Verify that there are this therapy types: Diet and Exercise Oral Medication(s) Non-insulin injectable Premix insulin Long-acting insulin (Basal) Short/Rapid-acting insulin (Bolus) Insulin Pump Other Which are selectable via checkboxes.'
        patientStudyPage.therapyTypeLabelDisplayed()
        then: 'Verify that there is [Cancel] link labeled as “Cancel”.'
        patientStudyPage.verifyCancelButton()
        then: 'Verify that there is [Close patient] button labeled as “Close patient”.'
        patientStudyPage.verifyClosePatientButton()
        when: 'Click on the “Home” tab.'
        patientStudyPage.patientHomeClick()
        homePage = browsers.at HomePage
        then: 'Verify that the Home screen is displayed and the patient is no longer bolded.'
        homePage.searchInput(ApiPropertySpak.patientID1)
        homePage.searchInputResult(ApiPropertySpak.patientID1)
        homePage.selectBoldPatientFromList('')

        when: 'Select the patient from the step ID 2760457 again and click on [Open patient] button.'
        homePage.selectPatientFromList()
        homePage.openPatient()
        patientStudyPage = browsers.at PatientStudyPage
        then: 'Verify that the Patient record screen is displayed.'
        patientStudyPage.patientFooterDisplayed()
    }

    def "Create new patient & check Patient Therapy Type"() {
        when: 'Click on the “Home” tab.'
        patientStudyPage.patientHomeClick()
        and: 'Click on the [New patient] button.'
        homePage = browsers.at HomePage
        homePage.clickNewPatientBtn()
        and: 'Fill out the required information for the new patient and click on the [Save] button.'
        createPatientPage = browsers.at CreatePatientPage
        createPatientPage.createPatient()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        String patientId = createPatientPage.getPatientID()
        createPatientPage.clickSaveBtn()
        and: 'Click on the “Home” tab again and record the info of newly created patient below:'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.patientHomeClick()
        sleep(1500)
        and: 'Clinic Info ID: (example: US84-9857)'
        and: 'Patient ID: (example: ID1234)'
        println("PatientID: " + patientId)
        and: 'Run the statement “QA_Queries_2.sql”. ' +
                'In the query, update p.external_id (as Patient ID) and c.clinic_identifier (as Clinic Info ID) as in step ID 2760543.'
        def clinicId = homePage.getClinicIdFromHomePage()
        DataBaseRequestsUtil.qa_Queries_2(patientId, clinicId)
        and: 'Click on the “Home” tab and select the newly created patient and click on the [Open patient] button.'
        homePage.searchInput(patientId)
        sleep(1000)
        homePage.selectPatientFromList()
        homePage.openPatient()
        and: 'Click on the “Edit patient information” link.'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.clickEditPatientButton()
        then: 'Verify that the following message is displayed when the Patient Therapy Type is “Multiple Daily Injection (3 or more insulin shots/day)” “This patient was previously assigned therapy type ‘Multiple Daily Injection (3 or more insulin shots/day)’.  Please update the therapy type as more options are available now.” (This applies to patient created in 1.10 and earlier.)'
        patientStudyPage.verifyMessage('This patient was previously assigned therapy type', 'Multiple Daily Injections (3 or more insulin shots/day)', 'Please update the therapy type as more options are available now.')

        when: 'Run the statement “QA_Queries_3.sql”. ' +
                'In the query, update p.external_id (as Patient ID) and c.clinic_identifier (as Clinic Info ID) as in step ID 2760543.'
        DataBaseRequestsUtil.qa_Queries_3(patientId, clinicId)
        and: 'Click on the “Home” tab and select the same patient as in step ID 2760553 and click on the [Open patient] button.'
        patientStudyPage.patientHomeClick()
        homePage = browsers.at HomePage
        sleep(1000)
        homePage.searchInput(patientId)
        sleep(1000)
        homePage.selectPatientFromList()
        homePage.openPatient()
        and: 'Click on the “Edit patient information” link.'
        patientStudyPage = browsers.at PatientStudyPage
        patientStudyPage.clickEditPatientButton()
        then: 'Verify that the following message is displayed when the Patient Therapy Type is “1-2 insulin shots/day” ' +
                '“This patient was previously assigned therapy type ‘1-2 insulin shots/day’. ' +
                'Please update the therapy type as more options are available now.” ' +
                '(This applies to patient created in 1.10 and earlier.)'
        patientStudyPage.verifyMessage('This patient was previously assigned therapy type', '1-2 insulin shots/day', 'Please update the therapy type as more options are available now.')

        when: 'Click on the Home tab.'
        patientStudyPage.patientHomeClick()
        and: 'Click on the New patient button in the upper-left corner.'
        homePage = browsers.at HomePage
        homePage.clickNewPatientBtn()
        patientStudyPage = browsers.at PatientStudyPage
        then: 'Verify that there are 3 diabetes types: Type 1 Type 2 Other Which are selectable via radio buttons.'
        patientStudyPage.diabetesTypelabelDisplayed()
        then: 'Verify that everything was correct if running on Windows and Mozilla Firefox 38.'
        println("Next steps should be verify manually from the Perfecto logs:\n" +
        "Verify that everything was correct if running on Windows and Mozilla Firefox 38.")
        then: 'End of test.'
    }
}