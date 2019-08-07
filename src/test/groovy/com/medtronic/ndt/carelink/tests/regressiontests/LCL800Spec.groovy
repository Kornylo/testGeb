package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.LoginPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.logbookevents.LogbookPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.pages.patient.PatientStudyPage
import com.medtronic.ndt.carelink.pages.reports.DailyOverlayReportPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import org.apache.pdfbox.cos.COSDocument
import org.apache.pdfbox.pdfparser.PDFParser
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.util.PDFTextStripper
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@RegressionTest
@Stepwise
@Screenshot
@Title('LCL 800 - Locales')
class LCL800Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LanguagePage languagePage
    static LoginPage loginPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static HomePage homePage
    static ClinicSettingsPage clinicSettingsPage
    static DailyOverlayReportPage dailyOverlayReportPage
    static LogbookPage logBookPage
    static CreatePatientPage createPatientPage
    static PatientStudyPage patientStudyPage


    static final adminUs = "AdminUS" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4)
    static final adminCanada = "CA" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4)
    static final adminJapan = "JA" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4)
    static final loginPassword = "MaluyScUI221"
    static final adminUser = "Admin1" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4)
    static final emailAddress = "email" + new Random().nextInt(1000) + RandomStringUtils.randomAlphanumeric(4) + '@medtronictest.mailinator.com'


    static final countryCanada = ["Canada","call 1-800-284-4416","en/CA/banner.xml","English", "images/flags/ca.png",'/en/CA/userguides.html','/en/CA/userguides.html', '20-Mar-1978','/en/CA/supplies.html','28/09/09 – 04/10/09',"OK",'AM','PM','Time']
    static final countryJapan = ["日本","(+81)-3-6776-0019","/ja/JP/banner.xml","日本", "/images/flags/jp.png",'/en/JP/userguides.html','/en/JP/userguides.html', '1978 6. 20','/en/JP/supplies.html','2009/09/28 – 2009/10/04',"OK",'午前','午後','時刻']
    static final countryUS = ["United States","call 1-800-646-4633","en/US/banner.xml","English", "images/flags/us.png",'/en/US/userguides.html','/en/US/userguides.html', 'Mar 20, 1978','/en/US/supplies.html','9/28/09 – 10/4/09',"OK",'AM','PM','Time']
///////////////Canaad///////////////
    def 'iPro Login Screen - Canada'() {
        when: 'In a workstation with the Operating System Under Test (Item-E) installed, launch the Browser Under Test (Item-F).'
        then: 'In the Browser’s Address Field, enter the HTTP Address to the iPro under Test (Item-C).'
        when: 'If the security page is displayed saying there is a problem with the website’s security certificate, click “Continue to this website (not recommended)” link to go to the iPro Site.'
        signInPage = browsers.to SignInPage
        then: 'Confirm the IPro Login Screen is displayed.'
        signInPage.medtronicLogo.displayed
        then: 'Contact Us Phone Number'
        then: 'From the login screen, change the country/language to the Country and Language under Test (Item-G and Item-H), by clicking the highlighted link at the top right corner of the screen.'
        signInPage.selectCountryLanguage.click()
        when:''
        languagePage = browsers.at LanguagePage
        languagePage.selectCountry(countryCanada[0])
        languagePage.buttonSelectClick()
        then: 'Once the Country/Language is changed, from the Login Screen, click the “Forgot Username” link. (Link under the first text input field)'
        then: 'Enter any information for both last name and email fields. (Email must include an @ and a .com to work)'
        then: 'Click Submit button.'
        then: 'Verify the phone number displayed in the text message matches the value of Contact Us Phone # ( Item-I ) .'
        then: 'Select the Cancel button.'
        waitFor { signInPage.medtronicLogo.displayed}
        signInPage.forgotUserNameFlowNegativeDifferentCountry("wrong", "blabla@bla.com", countryCanada[1])
        then: 'Use the following image as an example as to what you are looking for in the following steps.'
        then: 'System Communication Area'
        then: 'Locate the System Communication Area Link value. Right click anywhere on the background of the web page (not on top of any objects). Select the “View Source” option from the right-mouse menu.'
        then: 'A text file is opened, and the sixth line should look something simliar to.'
        then: 'url: \'/marcom/ipro2/ ** / ** /banner.xml'
        then: 'where * represents any single character'
        then: 'Verify the 6th line in the file matches the value of System Communication Area Link Address (Item-W) .'
        waitFor {signInPage.medtronicLogo.displayed}
        driver.getPageSource().contains("/banner.xml")
        then: 'Right click on the image ([2] in the picture above), and select the properties option.'
        then: 'Verify the System Communication Area matches the value of System Communication Area Image Address (item-V) .'
        driver.getPageSource().contains(countryCanada[2])

        then: 'User Guide Link'
        then: 'Mouse over the User Guide link right under the Sign-In button ([3] in picture above) .'
        then: 'Verify the address appear s on the web browser’s status bar ([4] in the picture above, might be in a different location for non-IE browsers) is the same as the value of Item-U User Guide Link Address .'

    }
    def 'Clinic Registration - Canada'() {
        when: 'Click on the Registe r Clinic Link (highlighted in blue in the picture above) .'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Select the “Continue” link (the link to the left of the only button on the screen) .'
        newClinicRegistrationPage.clickOnContinue()
        then: 'Select the drop-down menu.'
        when: ''
        languagePage = browsers.at LanguagePage
        then: 'Verify the pull down includes the country under test (Item-G) in the language a native user can always recognize.'
        languagePage.countryDropDown.text().contains(countryCanada[0])
        then: 'If the Language Under Test (Item-H) is English, verify the Language Pull-down list shows English as “English”'
        languagePage.languageDropDown.text().contains(countryCanada[3])
        when: ''
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Select the “Continue” button.'
        clinicLocalePage.selectLocale()
        then: 'Verify the correct flag is being displayed.'
        clinicLocalePage.countryFlagBeingDisplayed(countryCanada[4])
        then: 'Scroll to the bottom of the screen.'
        waitFor { $('#accept > p:nth-child(1)').text().contains(countryCanada[0]) }
        then: 'Verify the Country under Test (Item-G) can be found as part of the texts to the right of the upper check box (see picture below)'
        clinicLocalePage.countryUnderTestTexts(countryCanada[0])
        when:
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Click to select checkboxes. The Accept button should only become enabled when both are selected.'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        then: 'Verify in the Clinic Information Screen that the correct flag is being displayed in the Country field .'
        clinicEULAPage.clinicInformationScreenFlag(countryCanada[4])
        then: 'Verify a pull-down field with the label “ Province ” is displayed right below City and above Zip/Postal Code if the Country under test is Canada . (Note: If step does not apply, write NA)'
        clinicEULAPage.labelProvince('Province')
        then: 'Verify that [Clinic_Info_State] labeled as “Prefecture/Region” for Japan Locale.(Note : this step applicable to Japan Locale Only )'
        then: 'Verify only the following selections are available in the pull-down list if the Country under test is Canada. : (Note: If step does not apply, write NA)'
        then: 'Select Province'
        then: 'AB BC MB NB NL NT NS NU ON PE QC SK YT'
        clinicEULAPage.selectProvinceCanada('Select Province AB BC MB NB NL NT NS NU ON PE QC SK YT')
        when: 'Fill in all the required fields, and then click the “Continue” button.'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then: ''
        clinicEnterInfoPage.enrollmentClinicInformationScreen()
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.getClinicName()
        clinicEnterInfoPage.clickContinue()
        then: 'The Create Administrative User screen should be displayed. Fill in all the required fields and record the username and password entered:'
        when: 'Tag ID Variable Value Entered, Username,Password'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then: ''
        clinicEnterAdminInfoPage.enterUserNameManual(adminCanada)
        clinicEnterAdminInfoPage.enterFirstName("kornylo")
        clinicEnterAdminInfoPage.enterLastName("dmytro")
        clinicEnterAdminInfoPage.enterEmail(emailAddress)
        clinicEnterAdminInfoPage.enterPassword(loginPassword)
        clinicEnterAdminInfoPage.enterConfirmPassword(loginPassword)
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        println("Username: " + adminCanada + " Password: " + loginPassword)
        then: 'Click Continue when done.'
        clinicEnterAdminInfoPage.clickContinue()
        when: 'The Enrollment Complete screen is displayed. Click the Finish button to return to the Login Screen.'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then: ''
        finishClinicCreationPage.clickFinish()
        when: 'Log in as the user you just created by entering the Username (Item-Y) and Password (Item-Z) in the Username field ([5] in picture below) and Password ([6] in picture below) field:'
        signInPage = browsers.at SignInPage
        then: 'Login to an already existing clinic.'
        signInPage.enterUsername(adminCanada)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        signInPage.clickNewPatient()
        when:
        createPatientPage = browsers.at CreatePatientPage
        then: 'Create patient with the First name and Last name and keep the patient ID blank.'
        createPatientPage.titleCreateNewPatient("Create New Patient")
        createPatientPage.enterPatientFirstName('March', '20', '1978')
        String FirstName = createPatientPage.getPatientFirstName()
        String LastName = createPatientPage.getPatientLastName()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        createPatientPage.valueNewPatient(LastName, FirstName)
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        sleep(2000)
        then: ''
        patientStudyPage.exitPatient()
        when: ''
        signInPage = browsers.at SignInPage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        when:
        homePage = browsers.at HomePage
        then:
        signInPage.searchDisplayed()
        signInPage.searchForPatient(FirstName)
        then: 'Verify the Date of Birth in the Patient List is displayed in the mediu m format ( Item- N ) .'
        sleep(3000)
        signInPage.verifyDateOfBirth(countryCanada[7])
    }
    def 'Patient List - Canada'() {
        when: 'Mouse over the “User Guide” link (see in picture below).'
        homePage = browsers.at HomePage
        then:''
        homePage.mouseOverUserGuide()
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the User Guide Link Address (Item-U).'
        homePage.addressUserGuideLinkdisplayed(countryCanada[5])
        then: 'Mouse over the “ Resources ” link (see in picture below).'
        homePage.mouseOverResources()
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the Resources Link Address (Item-T).'
        homePage.addressResourcesLinkDisplayed(countryCanada[6])
        then: 'Mouse over the “Order supply” link (see in picture above ).'
        homePage.mouseOverOrderSupply()
        then: 'Order supplies link and notice the address in the bottom left hand corner.'
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the Order Supplies Link Address (Item-X)'
        homePage.addressOrderSuppliesLinkDisplayed(countryCanada[8])
   }
    def 'Clinic Settings - Canada'() {
        when: 'Select the Clinic Settings tab (2 nd Tab from the top left).'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'Select the Report Settings sub-tab if not already selected.'
        then: 'Clinic Settings – Time Display Format'
        clinicSettingsPage.clickOnClinicSettings()
        when: 'Verify in the Time Display Area ( in the picture above) , the format selected matches the Default Time Format (Item-K) .'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:''
        dailyOverlayReportPage.timeDisplayAreaDefault()
        then: 'Verify all timestamps in the screen are displayed in the same format as the selected format with the correct Time Hour Separator (Item-L) for the Country under test.'
        then: 'Change the Time Formate to the other value. Click the Save button to activate the change.'
        dailyOverlayReportPage.timeDisplayFormat()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.dialogSuccessWinPatient()
        dailyOverlayReportPage.successPopupSavedOkBtnvalue(countryCanada[10])
        dailyOverlayReportPage.successPopupSavedOkBtn()
        when: 'Verify that all timestamps in the screen are now displayed in the newly selected Time Display Format.'
        dailyOverlayReportPage.verifyBreakfastСonverted24hour30min()
        dailyOverlayReportPage.verifyLunchСonverted24hour30min()
        dailyOverlayReportPage.verifyDinnerСonverted24hour30min()
        dailyOverlayReportPage.verifyEveningСonverted24hour30min()
        dailyOverlayReportPage.verifySleepingСonverted24hour30min()
        then: 'Verify the Time Hour Separator (Item-L) remains the same as before.'
        then: 'Change the Time Display Format to the default value and save the change.'
        dailyOverlayReportPage.timeDisplayFormat()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.dialogSuccessWinPatient()
        dailyOverlayReportPage.successPopupSavedOkBtnvalue(countryCanada[10])
        dailyOverlayReportPage.successPopupSavedOkBtn()
        then: 'Clinic Settings - Glucose Unit'
        then: 'Verify in the Glucose Units Area ( in the picture above) , the glucose units format selected matches the Default Glucose Unit (Item-J) .'
        dailyOverlayReportPage.glucoseUnitsAreaСannotСhange()
        then: 'Verify all the numbers displayed in this screen using the Decimal Separator for Numbers (Item-P) and Group Separator for Number (Item-Q) where ver applicable.'
        dailyOverlayReportPage.decimalSeparatorForNumbersGroupSeparator()
        then: 'Clinic Settings - Glucose Unit ( Non Canada )'
        then: 'For country other than Canada, change the Glucose Units to a different value .'
        then: 'Verify the numbers in the Glucose target Range are changed to match the newly selected unit .'
        then: 'Verify all the numbers displayed in this screen using the Decimal Separator for Numbers (Item-P) and Group Separator for Number (Item-Q) wherever applicable.'
        then: 'Change the Glucose Units back to the original default value.'
        dailyOverlayReportPage.backOriginalGlucoseUnits()
        then: 'Clinic Settings - Glucose Unit ( Canada Only )'
        then: 'Verify the Glucose Units Area ( in the picture above) is disable such that user cannot change the unit to a different value.'
        dailyOverlayReportPage.glucoseUnitsAreaСannotСhange()
        then: 'Open Patient Record Page'
        then: 'Select the Home tab.'
        dailyOverlayReportPage.patientHomeTabClick()
        then: 'Open the Sample Patient by double clicking the s a mple patient row or by one click on the sample patient row and followed by clicking the Open patient button at the bottom left of the screen.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        then: 'Verify the date range displayed above the Reports section ( in picture above) is displayed in short date format (Item-M)'
        dailyOverlayReportPage.dateRangeDisplayed(countryCanada[9])
        then: 'Verify the Glucose Target Range values in the Patient Report Settings section ( in picture above) are displayed in the Default Glucose Unit (Item-J) with the Decimal Separator (Item-P) .'
        dailyOverlayReportPage.valuesGlucoseTargetRange()
        then: 'Verify the timestamps of each meal period in the Patient Report Settings Section ( in picture above) are display ed in the Default Time format (Item-K) with the specific Time Hour Separator (Item-L)'
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(5)', '6:00 AM – 10:00 AM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(7)', '11:00 AM – 3:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(9)', '5:00 PM – 8:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(11)', '5:00 PM – 8:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(13)', '3:00 AM – 6:00 AM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(15)', '5:00 AM – 7:00 AM')
        then: 'Select the Edit link at the bottom right of the Patient Report Settings section ( in picture above)'
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then: 'Verify the Default Time format (Item-K) is selected under the Time Display Section and all timestamps displayed under the Time Period section are displayed in the Default Time format (Item-K) using the specific Time Hour Separator (Item-L)'
        dailyOverlayReportPage.timeDisplayAreaDefault()
        dailyOverlayReportPage.verifyBreakfastСonverted12hour30min()
        dailyOverlayReportPage.verifyLunchСonverted12hour30min()
        dailyOverlayReportPage.verifyDinnerСonverted12hour30min()
        dailyOverlayReportPage.verifyEveningСonverted12hour30min()
        dailyOverlayReportPage.verifySleepingСonverted12hour30min()
        then: 'Verify the Default Glucose Unit (Item-J) is selected in the Glucose Units Section .'
        dailyOverlayReportPage.selectedDefaultGlucoseUnit()
        then: 'Verify the Glucose target Rage values under both the General Report Settings and Overlay by Meal Report Settings sections are displayed with the correct Decimal Separator (Item-P) and, when applicable, Group Separator (Item-Q) .'
        then: 'Verify in the Glucose Units section that the user can select a different value when the Country under test is not Canada and the user cannot select a different value when the Country Under Test is Canada.'
        dailyOverlayReportPage.sectionGlucoseUnitsDifferentValue()
        dailyOverlayReportPage.patientHomeTabClick()
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        dailyOverlayReportPage.goToLogbook()
 }
    def 'Logbook - Canada'() {
        when: 'Click the Open Logbook button.'
        logBookPage = browsers.at LogbookPage
        then: 'Verify all the timestamps in the Time Column shows the value in the Default Time Format (Item-K) with the correct Time Hour Separator (Item-L) .'
        waitFor { $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains(countryCanada[11]) }
        waitFor { $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains(countryCanada[12]) }
        //  logBookPage.LogbookColumnShowsDefaultTimeFormat()
        then: 'Verify the glucose units displayed in the logbook (in the column header) matches the  Default Glucose Unit(Item-J) .'
        logBookPage.glucoseUnitsDisplayed()
        then: 'Verify all the dates are displayed in short date format (Item-M) with a four digit year .'
        logBookPage.logbookDateFormat()
        then: 'Verify all the BG values in the BG column are displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the correct Group Separator for numbers (Item-Q) .'
        then: 'Select any row with data.'
        logBookPage.logbookSelectAnyRow()
        then: 'Select the Edit button (Second button from top to the left of the table) to bring up the pop-up Editor'
        logBookPage.logbookEditButton()
        then: 'Verify the Date value is displayed in the short date format with a four digit year .'
        logBookPage.logbookDateValue('9/29/2009')
        then: 'Verify the Time section:'
        then: 'Time Label shows the Default Time format (Item-K)'
        logBookPage.logbookTimeLabel(countryCanada[13])
        then: 'The Hour field shows the value corresponding to the Default Time format (Item-K).'
        logBookPage.logbookCorrespondingDefaultTimeFormat(countryCanada[11], countryCanada[12])
        then: 'The correct Time Hour Separator (Item-L) is displayed between the Hour and the Miniute fields.'
        logBookPage.logbookTimeHourSeparator()
        then: 'The AM/PM field (3 rd field in the Time section) shows the correct value matching the Default Time format (Item-K)'
        logBookPage.logbookCorrespondingDefaultTimeFormat(countryCanada[11], countryCanada[12])
        then: 'Verify the the BG section:'
        then: 'The Label in the column header should show the Default Glucose Unit (Item-J) .'
        logBookPage.logbookDefaultGlucoseUnitLabel()
        then: 'The value displayed should have the correct Decimal Separator (Item-P) .'
        logBookPage.logbookDecimalSeparator()
        logBookPage.logbookDecimalSeparatorMmolL()
        then: 'Select the “X” button on the top right corner of the pop-up to return to the Logbook Data Page .'
        logBookPage.logbookSelectXbutton()
        then: 'Select the Cancel link to return to the Patient Record Page .'
        logBookPage.logbookReturnPatientRecordPage()
        then: 'Data Table Report'
        then: 'In the Patient Record Page, click on the pull-down menu and select Regenerate Reports (3 rd item from the top, see picture below).'
        dailyOverlayReportPage.selectRegenerateReports()
        then: 'From the same pull-down menu, select Generate Data Table (2 nd option from the top).'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date > in medium date format (Item-N) - <End Date > in medium date format (Item-N)'
        then: 'where <Start Date> does not show the Year except when the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Navigate to the dropdown menu and choose the second option from there (Generate data table).'
        then: 'Verify the dates displayed in the table under the “Date and Time” appears in long date format (Item-O) .'
        then: 'Verify the timestamps in the Report:'
        then: 'The timestamps in the Date and Time column are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L)'
        then: 'Verify the all numbers displayed in the report are in displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the Group Separator for numbers (Item-Q) .'
        then: 'The Printed: timestamp displayed at the bottom of each page is in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L)'
        then: 'Verify the glucose units are displayed in the Default Glucose Unit (Item-J) throughout the report.'
        dailyOverlayReportPage.selectGenerateDataTable()
        dailyOverlayReportPage.waitForDataLoaded()
    }
    def 'Data Table - Canada'(){
        when:''
       def pdfLink = downloadStream($('td.data_table_r>ul>li>a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains("4-Oct-2009")
        assert output.contains("Date and Time")
        assert output.contains("1:00:02 AM")
        assert output.contains("12:20:02 PM")
        assert output.contains("4:40:02 AM")
        assert output.contains("PM")
        assert output.contains("AM")
        assert output.contains("Sensor Glucose")
        assert output.contains("(mmol/L)")
        assert output.contains("Blood Glucose")
        assert output.contains("(mmol/L)")
        assert output.contains("5.8 [22.00nA]")
        assert output.contains("5.7 [21.70nA]")
        parser.getPDDocument().close()
        then: 'Close the Data Table Report.'
    }
    def 'Daily Overlay Report - Canada'() {
        when: 'Click the Daily Overlay link to o pen the Daily Overlay Report.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> does not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify all timestamps:'
        then: 'The timestamps displayed on the x-axis of the Sensor Data Chart should be displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L) . If the 12 hour format is used, then “a” and “p” should be used to represent “AM” and “PM” respectively.'
        then: 'The Printed: timestamp at the bottom of each page is displayed per the Default Time format (Item-K)'
        then: 'Verify the glucose units are displayed correctly throughout the report :'
        then: 'The title of the Sensor Data Section should include the Default Glucose Unit (Item-I)'
        then: 'The Title of the Excursion Summary section should include the Default Glucose Unit (Item- J )'
        then: 'Verify the date s in the following locations are are displayed in medium date format (Item-N) excluding the year .'
        then: 'The Dates in the Sensor Data section above the Chart'
        then: 'The Dates in the column headers in the Sensor Data section'
        then: 'The Dates in the column headers in the Excursion Summary Section'
        then: 'Verify all numerical values in the report are displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the correct Group Separator for Numbers (Item-Q):'
        then: 'The left Y-axis marker values of the Sensor Data Chart'
        then: 'The right Y-axis marker values of the Sensor Data Chart'
        then: 'The values in the data table in the Sensor Data Section'
        then: 'The values in the Excursion Summary data table'
        then: 'The values in the row header of the Duration Distrbution Table'
        then: 'Verify the Title of the Duration Distribution section is always “Duration Distribution (H:mm) regardless what the Default Time format (Item-K) and Time Hour Separator (Item-L) are for the Country Under Test.'
        then: 'Verify that regardless the what the country’s Default Time format (Item-K) and Time Hour Separator (Item-L) are, the duration v alues in the Duration Distribution table are always displayed in H:mm format, where ‘H’ is total hours in short format and ‘mm’ is remainder minutes.'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(3) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Daily Overlay for Sample M. Patient")
        assert output.contains ("28-Sep - 4-Oct-2009 (7 days)")
        assert output.contains ("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        assert output.contains ("Sensor Data (mmol/L)")
        assert output.contains ("Excursion Summary (mmol/L/day)")
        assert output.contains ("3.9")
        assert output.contains ("8.3")
        assert output.contains ("5.0")
        assert output.contains ("10.0")
        assert output.contains ("15.0")
        assert output.contains ("20.0")
        assert output.contains ("≤2.2")
        assert output.contains ("≥22.2")
        assert output.contains ("Sensor Data (mmol/L)")
        assert output.contains ("Excursion Summary (mmol/L/day)")
        assert output.contains ("Duration Distribution (hh:mm)")
        parser.getPDDocument().close()
        then: 'Close the Daily Overlay Report.'
    }
    def 'Overlay by Meal Report - Canada'() {
        when: 'From the Patien t Record Page, click the Overlay by Meal link to o pen the report.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> doesn not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify the time stamps in the following areas of the report are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L):'
        then: 'The header of the Meal Event Table'
        then: 'The x-axis markers of the Night Time Sensor Data Graph. These timestamps should have “a” and “p” representing “AM” and “PM” when the 12-hour time format is used'
        then: 'The Printed: timestamp at the bottom of each page.'
        then: 'Verify the glucose units in the following areas are displayed with the Default Glucose Unit (Item- J ):'
        then: 'The Title of the Overlay by Meal Event Section'
        then: 'The Title of the Daily Average by Meal Event Section'
        then: 'The Title of the Night Time Sensor Data Section'
        then: 'Verify the following Graph Legends includes an unambiguous day-of-the week abbreviation followed by date s in m edium date format (Item-N) without the year :'
        then: 'Graph Legend next to the Meal Event Graph in the Overlay by Meal Event Section'
        then: 'Graph Levend above the Night Time Sensor Graph in the Night Time Sensor Data Section'
        then: 'Verify all values in the reports are displayed with the correct Decimal Separator (Item-P) and, if applicable, the Group Separator for the Numbers (Item-Q) .'
        then: 'Close the Overlay by Meal Report .'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(4) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Overlay by Meal for Sample M. Patient")
        assert output.contains ("28-Sep - 4-Oct-2009")
        assert output.contains ("11:00p 12:00a 1:00a 2:00a 3:00a 4:00a 5:00a 6:00")
        assert output.contains ("PM - 3:00 AM")
        assert output.contains ("Evening 11:00")
        assert output.contains ("3.9")
        assert output.contains ("8.3")
        assert output.contains ("5.0")
        assert output.contains ("10.0")
        assert output.contains ("15.0")
        assert output.contains ("20.0")
        assert output.contains ("≤2.2")
        assert output.contains ("≥22.2")
        assert output.contains ("Target RangeDaily Average by Meal Event (mmol/L)")
        assert output.contains ("Night Time Sensor Data (mmol/L)")
        assert output.contains ("Duration Distribution (hh:mm)")
        assert output.contains ("Tue 29-Sep")
        assert output.contains ("Mon 28-Sep")
        assert output.contains ("Wed 30-Sep")
        assert output.contains ("Thu 1-Oct")
        assert output.contains ("Fri 2-Oct")
        assert output.contains ("Sat 3-Oct")
        assert output.contains ("Sun 4-Oct")
        parser.getPDDocument().close()

    }
    def 'Daily Summary Report - Canada'() {
        when: 'From the Patient Report Page, click on the Daily Summary link to o pen the re port.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> doesn not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify the timestamps in the following areas of the report are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L):'
        then: 'The Title of each daily graph'
        then: 'The x-axis markers of each Daily Graph. These timestamps should have “a” and “p” representing “AM” and “PM” when the 12-hour time format is used'

        then: 'The Printed: timestamp at the bottom of each page .'
        then: 'Verify the glucose units in the following areas are displayed with the Default Glucose Unit (Item-I):'
        then: 'The Title of each daily graph .'
        then: 'Verify the title of each Daily Graph includes an unambiguous day-of-the week abbreviation followed by a date in medium date format (Item-N) without the year'
        then: 'Verify the following values in the reports are displayed with the correct Decimal Separator (Item-P) and, if applicable, the Group Separator for the Numbers (Item-Q) :'
        then: 'The value of the left y-axis markers in each Daily Graph'
        then: 'The value of the right y-axis markers in each Daily Graph'
        then: 'Note: (if required) To arrange data select Ansicht Optionen Kein Umbrunch.'
        then: 'Close the Daily Summary Report.'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(5) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Daily Summary for Sample M. Patient")
        assert output.contains ("28-Sep - 4-Oct-2009")
        assert output.contains ("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        assert output.contains ("Tue 29-Sep (mmol/L) Sensor")
        assert output.contains ("Wed 30-Sep (mmol/L) Sensor")
        assert output.contains ("Thu 1-Oct (mmol/L) Sensor")
        assert output.contains ("Fri 2-Oct (mmol/L) Sensor")
        assert output.contains ("Sat 3-Oct (mmol/L) Sensor")
        assert output.contains ("Sun 4-Oct (mmol/L) Sensor")
        assert output.contains ("3.9")
        assert output.contains ("8.3")
        assert output.contains ("5.0")
        assert output.contains ("10.0")
        assert output.contains ("15.0")
        assert output.contains ("20.0")
        assert output.contains ("≤2.2")
        assert output.contains ("≥22.2")
        assert output.contains ("A thicker flat sensor trace at 2.2 or 22.2 mmol/L indicates CGM values can be outside these limits.")
        parser.getPDDocument().close()
    }
    def 'CSV Export File - Canada'() {
        when: 'From the Patient Record Page, click on the Other Options pull-down menu, and select Export Data .If the security warning comes up, select the option to allow file downloading, select the Export Data option from the pull-down menu again.'
        then: 'Click Save to save the file in your workstation’s desktop, and then o pen the file using Microsoft WordPad.'
        then: 'Verify the date format in the “Report Range” appears as short format . (Step 7)'
        then: 'Verify the time format is displayed as HH:mm:ss using a colon as a separator.'
        then: 'Verify the time format is displayed in 24 hour format.'
        then: 'Verify the glucose units are displayed correctly throughout the report (Column G) .'
        then: 'Verify the date formats are displayed correctly throughout the report.'
        then: 'Verify the number formats are displayed correctly throughout the report.'
        then: 'Verify the columns are separated by a TAB value by default .'
        then: 'Verify that value is determined by user account’s Clinic Info Country.'
        then: 'Note: Check CareLink iPro SRS - Internationalization Specification ( ES9926) for Clinic_Info_Country'
        then: 'Verify the rows are left justified and are displayed in order and start with the Data Index.'
        then: 'Close the Export Report and the application.'
        $('div.study').find(class: 'grn_blue').find('option:nth-child(4)').click()
        then: 'End of the test.'


    }
////////////////US////////////////////
    def 'iPro Login Screen - US'() {
        when: 'In a workstation with the Operating System Under Test (Item-E) installed, launch the Browser Under Test (Item-F).'
        then: 'In the Browser’s Address Field, enter the HTTP Address to the iPro under Test (Item-C).'
        when: 'If the security page is displayed saying there is a problem with the website’s security certificate, click “Continue to this website (not recommended)” link to go to the iPro Site.'
        signInPage = browsers.to SignInPage
        then: 'Confirm the IPro Login Screen is displayed.'
        signInPage.medtronicLogo.displayed
        then: 'Contact Us Phone Number'
        then: 'From the login screen, change the country/language to the Country and Language under Test (Item-G and Item-H), by clicking the highlighted link at the top right corner of the screen.'
        signInPage.selectCountryLanguage.click()
        when:''
        languagePage = browsers.at LanguagePage
        languagePage.selectCountry(countryUS[0])
        languagePage.buttonSelectClick()
        then: 'Once the Country/Language is changed, from the Login Screen, click the “Forgot Username” link. (Link under the first text input field)'
        then: 'Enter any information for both last name and email fields. (Email must include an @ and a .com to work)'
        then: 'Click Submit button.'
        then: 'Verify the phone number displayed in the text message matches the value of Contact Us Phone # ( Item-I ) .'
        then: 'Select the Cancel button.'
        waitFor { signInPage.medtronicLogo.displayed}
        signInPage.forgotUserNameFlowNegativeDifferentCountry("wrong", "blabla@bla.com", countryUS[1])
        then: 'Use the following image as an example as to what you are looking for in the following steps.'
        then: 'System Communication Area'
        then: 'Locate the System Communication Area Link value. Right click anywhere on the background of the web page (not on top of any objects). Select the “View Source” option from the right-mouse menu.'
        then: 'A text file is opened, and the sixth line should look something simliar to.'
        then: 'url: \'/marcom/ipro2/ ** / ** /banner.xml'
        then: 'where * represents any single character'
        then: 'Verify the 6th line in the file matches the value of System Communication Area Link Address (Item-W) .'
        waitFor {signInPage.medtronicLogo.displayed}
        driver.getPageSource().contains("/banner.xml")
        then: 'Right click on the image ([2] in the picture above), and select the properties option.'
        then: 'Verify the System Communication Area matches the value of System Communication Area Image Address (item-V) .'
        driver.getPageSource().contains(countryUS[2])

        then: 'User Guide Link'
        then: 'Mouse over the User Guide link right under the Sign-In button ([3] in picture above) .'
        then: 'Verify the address appear s on the web browser’s status bar ([4] in the picture above, might be in a different location for non-IE browsers) is the same as the value of Item-U User Guide Link Address .'
        //    $('#footer_submenu > li > a').getAttribute("outerHTML").contains(countryCanada[2])
    }
    def 'Clinic Registration - US'() {
        when: 'Click on the Registe r Clinic Link (highlighted in blue in the picture above) .'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Select the “Continue” link (the link to the left of the only button on the screen) .'
        newClinicRegistrationPage.clickOnContinue()
        then: 'Select the drop-down menu.'
        when:''
        languagePage = browsers.at LanguagePage
        then: 'Verify the pull down includes the country under test (Item-G) in the language a native user can always recognize.'
        languagePage.countryDropDown.text().contains(countryUS[0])
        then: 'If the Language Under Test (Item-H) is English, verify the Language Pull-down list shows English as “English”'
        languagePage.languageDropDown.text().contains(countryUS[3])
        when:''
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Select the “Continue” button.'
        clinicLocalePage.selectLocale()
        then: 'Verify the correct flag is being displayed.'
        clinicLocalePage.countryFlagBeingDisplayed(countryUS[4])
        then: 'Scroll to the bottom of the screen.'
        waitFor { $('#accept > p:nth-child(1)').text().contains(countryUS[0])}
        then: 'Verify the Country under Test (Item-G) can be found as part of the texts to the right of the upper check box (see picture below)'
        clinicLocalePage.countryUnderTestTexts(countryUS[0])
        when:
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Click to select checkboxes. The Accept button should only become enabled when both are selected.'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        then: 'Verify in the Clinic Information Screen that the correct flag is being displayed in the Country field .'
        clinicEULAPage.clinicInformationScreenFlag(countryUS[4])
        then: 'Verify a pull-down field with the label “ Province ” is displayed right below City and above Zip/Postal Code if the Country under test is Canada . (Note: If step does not apply, write NA)'
        clinicEULAPage.labelProvince('Province')
        then: 'Verify that [Clinic_Info_State] labeled as “Prefecture/Region” for Japan Locale.(Note : this step applicable to Japan Locale Only )'
        then: 'Verify only the following selections are available in the pull-down list if the Country under test is Canada. : (Note: If step does not apply, write NA)'
        then: 'Select Province'
        then: 'AB BC MB NB NL NT NS NU ON PE QC SK YT'
        clinicEULAPage.selectProvinceCanada('Select Province AB BC MB NB NL NT NS NU ON PE QC SK YT')
        when: 'Fill in all the required fields, and then click the “Continue” button.'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then:''
        clinicEnterInfoPage.enrollmentClinicInformationScreen()
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.getClinicName()
        clinicEnterInfoPage.clickContinue()
        then: 'The Create Administrative User screen should be displayed. Fill in all the required fields and record the username and password entered:'
        when: 'Tag ID Variable Value Entered, Username,Password'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then:''
        clinicEnterAdminInfoPage.enterUserNameManual(adminUs)
        clinicEnterAdminInfoPage.enterFirstName("kornylo")
        clinicEnterAdminInfoPage.enterLastName("dmytro")
        clinicEnterAdminInfoPage.enterEmail(emailAddress)
        clinicEnterAdminInfoPage.enterPassword(loginPassword)
        clinicEnterAdminInfoPage.enterConfirmPassword(loginPassword)
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        println ("Username: " + adminUs + " Password: " + loginPassword)
        then: 'Click Continue when done.'
        clinicEnterAdminInfoPage.clickContinue()
        when: 'The Enrollment Complete screen is displayed. Click the Finish button to return to the Login Screen.'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then:''
        finishClinicCreationPage.clickFinish()
        when: 'Log in as the user you just created by entering the Username (Item-Y) and Password (Item-Z) in the Username field ([5] in picture below) and Password ([6] in picture below) field:'
        signInPage = browsers.at SignInPage
        then: 'Login to an already existing clinic.'
        signInPage.enterUsername(adminUs)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        signInPage.clickNewPatient()
        when:
        createPatientPage = browsers.at CreatePatientPage
        then: 'Create patient with the First name and Last name and keep the patient ID blank.'
        createPatientPage.titleCreateNewPatient("Create New Patient")
        createPatientPage.enterPatientFirstName('March', '20', '1978')
        String FirstName = createPatientPage.getPatientFirstName()
        String LastName = createPatientPage.getPatientLastName()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        createPatientPage.valueNewPatient(LastName, FirstName)
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        sleep(2000)
        then: ''
        patientStudyPage.exitPatient()
        when: ''
        signInPage = browsers.at SignInPage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        when:
        homePage = browsers.at HomePage
        then:
        signInPage.searchDisplayed()
        signInPage.searchForPatient(FirstName)
        then: 'Verify the Date of Birth in the Patient List is displayed in the mediu m format ( Item- N ) .'
        signInPage.verifyDateOfBirth(countryUS[7])
    }
    def 'Patient List - US'() {
        when: 'Mouse over the “User Guide” link (see in picture below).'
        homePage = browsers.at HomePage
        then:''
        homePage.mouseOverUserGuide()
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the User Guide Link Address (Item-U).'
        homePage.addressUserGuideLinkdisplayed(countryUS[5])
        then: 'Mouse over the “ Resources ” link (see in picture below).'
        homePage.mouseOverResources()
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the Resources Link Address (Item-T).'
        homePage.addressResourcesLinkDisplayed(countryUS[6])
        then: 'Mouse over the “Order supply” link (see in picture above ).'
        homePage.mouseOverOrderSupply()
        then: 'Order supplies link and notice the address in the bottom left hand corner.'
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the Order Supplies Link Address (Item-X)'
        homePage.addressOrderSuppliesLinkDisplayed(countryUS[8])
    }
    def 'Clinic Settings - US'() {
        when: 'Select the Clinic Settings tab (2 nd Tab from the top left).'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'Select the Report Settings sub-tab if not already selected.'
        then: 'Clinic Settings – Time Display Format'
        clinicSettingsPage.clickOnClinicSettings()
        when: 'Verify in the Time Display Area ( in the picture above) , the format selected matches the Default Time Format (Item-K) .'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:''
        dailyOverlayReportPage.timeDisplayAreaDefault()
        //todo  then: 'Verify all timestamps in the screen are displayed in the same format as the selected format with the correct Time Hour Separator (Item-L) for the Country under test.'
        then: 'Change the Time Formate to the other value. Click the Save button to activate the change.'
        dailyOverlayReportPage.timeDisplayFormat()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.dialogSuccessWinPatient()
        dailyOverlayReportPage.successPopupSavedOkBtnvalue(countryUS[10])
        dailyOverlayReportPage.successPopupSavedOkBtn()
        when: 'Verify that all timestamps in the screen are now displayed in the newly selected Time Display Format.'
        dailyOverlayReportPage.verifyBreakfastСonverted24hour30min()
        dailyOverlayReportPage.verifyLunchСonverted24hour30min()
        dailyOverlayReportPage.verifyDinnerСonverted24hour30min()
        dailyOverlayReportPage.verifyEveningСonverted24hour30min()
        dailyOverlayReportPage.verifySleepingСonverted24hour30min()
        then: 'Verify the Time Hour Separator (Item-L) remains the same as before.'
        then: 'Change the Time Display Format to the default value and save the change.'
        dailyOverlayReportPage.timeDisplayFormat()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.dialogSuccessWinPatient()
        dailyOverlayReportPage.successPopupSavedOkBtnvalue(countryUS[10])
        dailyOverlayReportPage.successPopupSavedOkBtn()
        then: 'Clinic Settings - Glucose Unit'
        then: 'Verify in the Glucose Units Area ( in the picture above) , the glucose units format selected matches the Default Glucose Unit (Item-J) .'
        dailyOverlayReportPage.glucoseUnitsAreaСannotСhange()
        then: 'Verify all the numbers displayed in this screen using the Decimal Separator for Numbers (Item-P) and Group Separator for Number (Item-Q) where ver applicable.'
        dailyOverlayReportPage.decimalSeparatorForNumbersGroupSeparator()
        then: 'Clinic Settings - Glucose Unit ( Non Canada )'
        then: 'For country other than Canada, change the Glucose Units to a different value .'
        then: 'Verify the numbers in the Glucose target Range are changed to match the newly selected unit .'
        then: 'Verify all the numbers displayed in this screen using the Decimal Separator for Numbers (Item-P) and Group Separator for Number (Item-Q) wherever applicable.'
        then: 'Change the Glucose Units back to the original default value.'
        dailyOverlayReportPage.backOriginalGlucoseUnits()
        then: 'Clinic Settings - Glucose Unit ( Canada Only )'
        then: 'Verify the Glucose Units Area ( in the picture above) is disable such that user cannot change the unit to a different value.'
        dailyOverlayReportPage.glucoseUnitsAreaСannotСhange()
        then: 'Open Patient Record Page'
        then: 'Select the Home tab.'
        dailyOverlayReportPage.patientHomeTabClick()
        then: 'Open the Sample Patient by double clicking the s a mple patient row or by one click on the sample patient row and followed by clicking the Open patient button at the bottom left of the screen.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        then: 'Verify the date range displayed above the Reports section ( in picture above) is displayed in short date format (Item-M)'
        dailyOverlayReportPage.dateRangeDisplayed(countryUS[9])
        then: 'Verify the Glucose Target Range values in the Patient Report Settings section ( in picture above) are displayed in the Default Glucose Unit (Item-J) with the Decimal Separator (Item-P) .'
        dailyOverlayReportPage.valuesGlucoseTargetRange()
        then: 'Verify the timestamps of each meal period in the Patient Report Settings Section ( in picture above) are display ed in the Default Time format (Item-K) with the specific Time Hour Separator (Item-L)'
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(5)', '6:00 AM – 10:00 AM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(7)', '11:00 AM – 3:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(9)', '5:00 PM – 8:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(11)', '5:00 PM – 8:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(13)', '3:00 AM – 6:00 AM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(15)', '5:00 AM – 7:00 AM')
        then: 'Select the Edit link at the bottom right of the Patient Report Settings section ( in picture above)'
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then: 'Verify the Default Time format (Item-K) is selected under the Time Display Section and all timestamps displayed under the Time Period section are displayed in the Default Time format (Item-K) using the specific Time Hour Separator (Item-L)'
        dailyOverlayReportPage.timeDisplayAreaDefault()
        dailyOverlayReportPage.verifyBreakfastСonverted12hour30min()
        dailyOverlayReportPage.verifyLunchСonverted12hour30min()
        dailyOverlayReportPage.verifyDinnerСonverted12hour30min()
        dailyOverlayReportPage.verifyEveningСonverted12hour30min()
        dailyOverlayReportPage.verifySleepingСonverted12hour30min()
        then: 'Verify the Default Glucose Unit (Item-J) is selected in the Glucose Units Section .'
        dailyOverlayReportPage.selectedDefaultGlucoseUnit()
        then: 'Verify the Glucose target Rage values under both the General Report Settings and Overlay by Meal Report Settings sections are displayed with the correct Decimal Separator (Item-P) and, when applicable, Group Separator (Item-Q) .'
        then: 'Verify in the Glucose Units section that the user can select a different value when the Country under test is not Canada and the user cannot select a different value when the Country Under Test is Canada.'
        dailyOverlayReportPage.sectionGlucoseUnitsDifferentValue()
        dailyOverlayReportPage.patientHomeTabClick()
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        dailyOverlayReportPage.goToLogbook()
    }
    def 'Logbook - US'() {
        when: 'Click the Open Logbook button.'
        logBookPage = browsers.at LogbookPage
        then: 'Verify all the timestamps in the Time Column shows the value in the Default Time Format (Item-K) with the correct Time Hour Separator (Item-L) .'
        waitFor { $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains(countryUS[11]) }
        waitFor { $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains(countryUS[12]) }
        //  logBookPage.LogbookColumnShowsDefaultTimeFormat()
        then: 'Verify the glucose units displayed in the logbook (in the column header) matches the  Default Glucose Unit(Item-J) .'
        logBookPage.glucoseUnitsDisplayed()
        then: 'Verify all the dates are displayed in short date format (Item-M) with a four digit year .'
        logBookPage.logbookDateFormat()
        then: 'Verify all the BG values in the BG column are displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the correct Group Separator for numbers (Item-Q) .'
        then: 'Select any row with data.'
        logBookPage.logbookSelectAnyRow()
        then: 'Select the Edit button (Second button from top to the left of the table) to bring up the pop-up Editor'
        logBookPage.logbookEditButton()
        then: 'Verify the Date value is displayed in the short date format with a four digit year .'
        logBookPage.logbookDateValue('9/29/2009')
        then: 'Verify the Time section:'
        then: 'Time Label shows the Default Time format (Item-K)'
        logBookPage.logbookTimeLabel(countryUS[13])
        then: 'The Hour field shows the value corresponding to the Default Time format (Item-K).'
        logBookPage.logbookCorrespondingDefaultTimeFormat(countryUS[11], countryUS[12])
        then: 'The correct Time Hour Separator (Item-L) is displayed between the Hour and the Miniute fields.'
        logBookPage.logbookTimeHourSeparator()
        then: 'The AM/PM field (3 rd field in the Time section) shows the correct value matching the Default Time format (Item-K)'
        logBookPage.logbookCorrespondingDefaultTimeFormat(countryUS[11], countryUS[12])
        then: 'Verify the the BG section:'
        then: 'The Label in the column header should show the Default Glucose Unit (Item-J) .'
        logBookPage.logbookDefaultGlucoseUnitLabel()
        then: 'The value displayed should have the correct Decimal Separator (Item-P) .'
        logBookPage.logbookDecimalSeparator()
        logBookPage.logbookDecimalSeparatorMmolL()
        then: 'Select the “X” button on the top right corner of the pop-up to return to the Logbook Data Page .'
        logBookPage.logbookSelectXbutton()
        then: 'Select the Cancel link to return to the Patient Record Page .'
        logBookPage.logbookReturnPatientRecordPage()
        then: 'Data Table Report'
        then: 'In the Patient Record Page, click on the pull-down menu and select Regenerate Reports (3 rd item from the top, see picture below).'
        dailyOverlayReportPage.selectRegenerateReports()
        then: 'From the same pull-down menu, select Generate Data Table (2 nd option from the top).'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date > in medium date format (Item-N) - <End Date > in medium date format (Item-N)'
        then: 'where <Start Date> does not show the Year except when the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Navigate to the dropdown menu and choose the second option from there (Generate data table).'
        then: 'Verify the dates displayed in the table under the “Date and Time” appears in long date format (Item-O) .'
        then: 'Verify the timestamps in the Report:'
        then: 'The timestamps in the Date and Time column are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L)'
        then: 'Verify the all numbers displayed in the report are in displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the Group Separator for numbers (Item-Q) .'
        then: 'The Printed: timestamp displayed at the bottom of each page is in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L)'
        then: 'Verify the glucose units are displayed in the Default Glucose Unit (Item-J) throughout the report.'
        dailyOverlayReportPage.selectGenerateDataTable()
        dailyOverlayReportPage.waitForDataLoaded()
    }
    def 'Data Table - US'(){
        when:''
        def pdfLink = downloadStream($('td.data_table_r>ul>li>a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains("Oct 4, 2009")
        assert output.contains("Date and Time")
        assert output.contains("1:00:02 AM")
        assert output.contains("12:20:02 PM")
        assert output.contains("4:40:02 AM")
        assert output.contains("PM")
        assert output.contains("AM")
        assert output.contains("Sensor Glucose")
        assert output.contains("(mg/dL)")
        assert output.contains("Blood Glucose")
        parser.getPDDocument().close()
        then: 'Close the Data Table Report.'
    }
    def 'Daily Overlay Report - US'() {
        when: 'Click the Daily Overlay link to o pen the Daily Overlay Report.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> does not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify all timestamps:'
        then: 'The timestamps displayed on the x-axis of the Sensor Data Chart should be displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L) . If the 12 hour format is used, then “a” and “p” should be used to represent “AM” and “PM” respectively.'
        then: 'The Printed: timestamp at the bottom of each page is displayed per the Default Time format (Item-K)'
        then: 'Verify the glucose units are displayed correctly throughout the report :'
        then: 'The title of the Sensor Data Section should include the Default Glucose Unit (Item-I)'
        then: 'The Title of the Excursion Summary section should include the Default Glucose Unit (Item- J )'
        then: 'Verify the date s in the following locations are are displayed in medium date format (Item-N) excluding the year .'
        then: 'The Dates in the Sensor Data section above the Chart'
        then: 'The Dates in the column headers in the Sensor Data section'
        then: 'The Dates in the column headers in the Excursion Summary Section'
        then: 'Verify all numerical values in the report are displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the correct Group Separator for Numbers (Item-Q):'
        then: 'The left Y-axis marker values of the Sensor Data Chart'
        then: 'The right Y-axis marker values of the Sensor Data Chart'
        then: 'The values in the data table in the Sensor Data Section'
        then: 'The values in the Excursion Summary data table'
        then: 'The values in the row header of the Duration Distrbution Table'
        then: 'Verify the Title of the Duration Distribution section is always “Duration Distribution (H:mm) regardless what the Default Time format (Item-K) and Time Hour Separator (Item-L) are for the Country Under Test.'
        then: 'Verify that regardless the what the country’s Default Time format (Item-K) and Time Hour Separator (Item-L) are, the duration v alues in the Duration Distribution table are always displayed in H:mm format, where ‘H’ is total hours in short format and ‘mm’ is remainder minutes.'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(3) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Daily Overlay for Sample M. Patient")
        assert output.contains ("Oct 4, 2009 (7 days)")
        assert output.contains ("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        assert output.contains ("Sensor Data (mg/dL)")
        assert output.contains ("70")
        assert output.contains ("150")
        assert output.contains ("100")
        assert output.contains ("200")
        assert output.contains ("300")
        assert output.contains ("≤40")
        assert output.contains ("≥400")
        assert output.contains ("Sensor Data (mg/dL)")
        assert output.contains ("Excursion Summary (mg/dL/day)")
        assert output.contains ("Duration Distribution (hh:mm)")
        parser.getPDDocument().close()
        then: 'Close the Daily Overlay Report.'
    }
    def 'Overlay by Meal Report - US'() {
        when: 'From the Patien t Record Page, click the Overlay by Meal link to o pen the report.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> doesn not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify the time stamps in the following areas of the report are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L):'
        then: 'The header of the Meal Event Table'
        then: 'The x-axis markers of the Night Time Sensor Data Graph. These timestamps should have “a” and “p” representing “AM” and “PM” when the 12-hour time format is used'
        then: 'The Printed: timestamp at the bottom of each page.'
        then: 'Verify the glucose units in the following areas are displayed with the Default Glucose Unit (Item- J ):'
        then: 'The Title of the Overlay by Meal Event Section'
        then: 'The Title of the Daily Average by Meal Event Section'
        then: 'The Title of the Night Time Sensor Data Section'
        then: 'Verify the following Graph Legends includes an unambiguous day-of-the week abbreviation followed by date s in m edium date format (Item-N) without the year :'
        then: 'Graph Legend next to the Meal Event Graph in the Overlay by Meal Event Section'
        then: 'Graph Levend above the Night Time Sensor Graph in the Night Time Sensor Data Section'
        then: 'Verify all values in the reports are displayed with the correct Decimal Separator (Item-P) and, if applicable, the Group Separator for the Numbers (Item-Q) .'
        then: 'Close the Overlay by Meal Report .'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(4) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Overlay by Meal for Sample M. Patient")
        assert output.contains ("Oct 4, 2009")
        assert output.contains ("11:00p 12:00a 1:00a 2:00a 3:00a 4:00a 5:00a 6:00")
        assert output.contains ("PM - 3:00 AM")
        assert output.contains ("Evening 11:00")
        assert output.contains ("70")
        assert output.contains ("150")
        assert output.contains ("100")
        assert output.contains ("200")
        assert output.contains ("300")
        assert output.contains ("≤40")
        assert output.contains ("400")
        assert output.contains ("Target RangeDaily Average by Meal Event (mg/dL)")
        assert output.contains ("Night Time Sensor Data (mg/dL)")
        assert output.contains ("Duration Distribution (hh:mm)")
        assert output.contains ("Mon Sep 28")
        assert output.contains ("Wed Sep 30")
        assert output.contains ("Thu Oct 1")
        assert output.contains ("Fri Oct 2")
        assert output.contains ("Sat Oct 3")
        assert output.contains ("Sun Oct 4")
        parser.getPDDocument().close()

    }
    def 'Daily Summary Report - US'() {
        when: 'From the Patient Report Page, click on the Daily Summary link to o pen the re port.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> doesn not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify the timestamps in the following areas of the report are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L):'
        then: 'The Title of each daily graph'
        then: 'The x-axis markers of each Daily Graph. These timestamps should have “a” and “p” representing “AM” and “PM” when the 12-hour time format is used'
        then: 'The Printed: timestamp at the bottom of each page .'
        then: 'Verify the glucose units in the following areas are displayed with the Default Glucose Unit (Item-I):'
        then: 'The Title of each daily graph .'
        then: 'Verify the title of each Daily Graph includes an unambiguous day-of-the week abbreviation followed by a date in medium date format (Item-N) without the year'
        then: 'Verify the following values in the reports are displayed with the correct Decimal Separator (Item-P) and, if applicable, the Group Separator for the Numbers (Item-Q) :'
        then: 'The value of the left y-axis markers in each Daily Graph'
        then: 'The value of the right y-axis markers in each Daily Graph'
        then: 'Note: (if required) To arrange data select Ansicht Optionen Kein Umbrunch.'
        then: 'Close the Daily Summary Report.'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(5) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Daily Summary for Sample M. Patient")
        assert output.contains ("Oct 4, 2009 (7 days)")
        assert output.contains ("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        assert output.contains ("Tue Sep 29 (mg/dL) Sensor")
        assert output.contains ("Wed Sep 30 (mg/dL) Sensor")
        assert output.contains ("Thu Oct 1 (mg/dL) Sensor")
        assert output.contains ("Fri Oct 2 (mg/dL) Sensor")
        assert output.contains ("Sat Oct 3 (mg/dL) Sensor")
        assert output.contains ("Sun Oct 4 (mg/dL) Sensor")
        assert output.contains ("70")
        assert output.contains ("150")
        assert output.contains ("100")
        assert output.contains ("200")
        assert output.contains ("300")
        assert output.contains ("≤40")
        assert output.contains ("400")
        assert output.contains ("A thicker flat sensor trace at 40 or 400 mg/dL indicates CGM values can be outside these limits.")
        parser.getPDDocument().close()
    }
    def 'CSV Export File - US'() {
        when: 'From the Patient Record Page, click on the Other Options pull-down menu, and select Export Data .If the security warning comes up, select the option to allow file downloading, select the Export Data option from the pull-down menu again.'
        then: 'Click Save to save the file in your workstation’s desktop, and then o pen the file using Microsoft WordPad.'
        then: 'Verify the date format in the “Report Range” appears as short format . (Step 7)'
        then: 'Verify the time format is displayed as HH:mm:ss using a colon as a separator.'
        then: 'Verify the time format is displayed in 24 hour format.'
        then: 'Verify the glucose units are displayed correctly throughout the report (Column G) .'
        then: 'Verify the date formats are displayed correctly throughout the report.'
        then: 'Verify the number formats are displayed correctly throughout the report.'
        then: 'Verify the columns are separated by a TAB value by default .'
        then: 'Verify that value is determined by user account’s Clinic Info Country.'
        then: 'Note: Check CareLink iPro SRS - Internationalization Specification ( ES9926) for Clinic_Info_Country'
        then: 'Verify the rows are left justified and are displayed in order and start with the Data Index.'
        then: 'Close the Export Report and the application.'
        then: 'End of the test.'


    }
    ////////////////Japan////////////////////
    def 'iPro Login Screen - Japan'() {
        when: 'In a workstation with the Operating System Under Test (Item-E) installed, launch the Browser Under Test (Item-F).'
        then: 'In the Browser’s Address Field, enter the HTTP Address to the iPro under Test (Item-C).'
        when: 'If the security page is displayed saying there is a problem with the website’s security certificate, click “Continue to this website (not recommended)” link to go to the iPro Site.'
        signInPage = browsers.to SignInPage
        then: 'Confirm the IPro Login Screen is displayed.'
        signInPage.medtronicLogo.displayed
        then: 'Contact Us Phone Number'
        then: 'From the login screen, change the country/language to the Country and Language under Test (Item-G and Item-H), by clicking the highlighted link at the top right corner of the screen.'
        signInPage.selectCountryLanguage.click()
        when:''
        languagePage = browsers.at LanguagePage
        languagePage.selectCountry(countryJapan[0])
        languagePage.buttonSelectClick()
        then: 'Once the Country/Language is changed, from the Login Screen, click the “Forgot Username” link. (Link under the first text input field)'
        then: 'Enter any information for both last name and email fields. (Email must include an @ and a .com to work)'
        then: 'Click Submit button.'
        then: 'Verify the phone number displayed in the text message matches the value of Contact Us Phone # ( Item-I ) .'
        then: 'Select the Cancel button.'
        waitFor { signInPage.medtronicLogo.displayed}
        signInPage.forgotUserNameFlowNegativeDifferentCountry("wrong", "blabla@bla.com", countryJapan[1])
        then: 'Use the following image as an example as to what you are looking for in the following steps.'
        then: 'System Communication Area'
        then: 'Locate the System Communication Area Link value. Right click anywhere on the background of the web page (not on top of any objects). Select the “View Source” option from the right-mouse menu.'
        then: 'A text file is opened, and the sixth line should look something simliar to.'
        then: 'url: \'/marcom/ipro2/ ** / ** /banner.xml'
        then: 'where * represents any single character'
        then: 'Verify the 6th line in the file matches the value of System Communication Area Link Address (Item-W) .'
        waitFor {signInPage.medtronicLogo.displayed}
        driver.getPageSource().contains("/banner.xml")
        then: 'Right click on the image ([2] in the picture above), and select the properties option.'
        then: 'Verify the System Communication Area matches the value of System Communication Area Image Address (item-V) .'
        driver.getPageSource().contains(countryJapan[2])

        then: 'User Guide Link'
        then: 'Mouse over the User Guide link right under the Sign-In button ([3] in picture above) .'
        then: 'Verify the address appear s on the web browser’s status bar ([4] in the picture above, might be in a different location for non-IE browsers) is the same as the value of Item-U User Guide Link Address .'
        //    $('#footer_submenu > li > a').getAttribute("outerHTML").contains(countryCanada[2])
    }
    def 'Clinic Registration - Japan'() {
        when: 'Click on the Registe r Clinic Link (highlighted in blue in the picture above) .'
        newClinicRegistrationPage = browsers.at NewClinicRegistrationPage
        newClinicRegistrationPage.clickRegisterClinic()
        then: 'Select the “Continue” link (the link to the left of the only button on the screen) .'
        newClinicRegistrationPage.clickOnContinue()
        then: 'Select the drop-down menu.'
        when:''
        languagePage = browsers.at LanguagePage
        then: 'Verify the pull down includes the country under test (Item-G) in the language a native user can always recognize.'
        languagePage.countryDropDown.text().contains(countryJapan[0])
        then: 'If the Language Under Test (Item-H) is English, verify the Language Pull-down list shows English as “English”'
        languagePage.languageDropDown.text().contains(countryJapan[3])
        when:''
        clinicLocalePage = browsers.at ClinicLocalePage
        then: 'Select the “Continue” button.'
        clinicLocalePage.selectLocale()
        then: 'Verify the correct flag is being displayed.'
        clinicLocalePage.countryFlagBeingDisplayed(countryJapan[4])
        then: 'Scroll to the bottom of the screen.'
        waitFor { $('#accept > p:nth-child(1)').text().contains(countryJapan[0])}
        then: 'Verify the Country under Test (Item-G) can be found as part of the texts to the right of the upper check box (see picture below)'
        clinicLocalePage.countryUnderTestTexts(countryJapan[0])
        when:
        clinicEULAPage = browsers.at ClinicEULAPage
        then: 'Click to select checkboxes. The Accept button should only become enabled when both are selected.'
        clinicEULAPage.clickResident()
        clinicEULAPage.clickReadAndAccept()
        clinicEULAPage.clickAccept()
        then: 'Verify in the Clinic Information Screen that the correct flag is being displayed in the Country field .'
        clinicEULAPage.clinicInformationScreenFlag(countryJapan[4])
        then: 'Verify a pull-down field with the label “ Province ” is displayed right below City and above Zip/Postal Code if the Country under test is Canada . (Note: If step does not apply, write NA)'
        clinicEULAPage.labelProvince('Province')
        then: 'Verify that [Clinic_Info_State] labeled as “Prefecture/Region” for Japan Locale.(Note : this step applicable to Japan Locale Only )'
        then: 'Verify only the following selections are available in the pull-down list if the Country under test is Canada. : (Note: If step does not apply, write NA)'
        then: 'Select Province'
        then: 'AB BC MB NB NL NT NS NU ON PE QC SK YT'
        clinicEULAPage.selectProvinceCanada('Select Province AB BC MB NB NL NT NS NU ON PE QC SK YT')
        when: 'Fill in all the required fields, and then click the “Continue” button.'
        clinicEnterInfoPage = browsers.at ClinicEnterInfoPage
        then:''
        clinicEnterInfoPage.enrollmentClinicInformationScreen()
        clinicEnterInfoPage.configureClinic()
        clinicEnterInfoPage.getClinicName()
        clinicEnterInfoPage.clickContinue()
        then: 'The Create Administrative User screen should be displayed. Fill in all the required fields and record the username and password entered:'
        when: 'Tag ID Variable Value Entered, Username,Password'
        clinicEnterAdminInfoPage = browsers.at ClinicEnterAdminInfoPage
        then:''
        clinicEnterAdminInfoPage.enterUserNameManual(adminJapan)
        clinicEnterAdminInfoPage.enterFirstName("kornylo")
        clinicEnterAdminInfoPage.enterLastName("dmytro")
        clinicEnterAdminInfoPage.enterEmail(emailAddress)
        clinicEnterAdminInfoPage.enterPassword(loginPassword)
        clinicEnterAdminInfoPage.enterConfirmPassword(loginPassword)
        clinicEnterAdminInfoPage.enterSecurityAnswer("Amazing")
        assert ("Username: " + adminJapan + " Password: " + loginPassword)
        then: 'Click Continue when done.'
        clinicEnterAdminInfoPage.clickContinue()
        when: 'The Enrollment Complete screen is displayed. Click the Finish button to return to the Login Screen.'
        finishClinicCreationPage = browsers.at FinishClinicCreationPage
        then:''
        finishClinicCreationPage.clickFinish()
        when: 'Log in as the user you just created by entering the Username (Item-Y) and Password (Item-Z) in the Username field ([5] in picture below) and Password ([6] in picture below) field:'
        signInPage = browsers.at SignInPage
        then: 'Login to an already existing clinic.'
        signInPage.enterUsername(adminJapan)
        signInPage.enterPassword(loginPassword)
        signInPage.clickOnSignIn()
        signInPage.postponeMFA()
        signInPage.clickNewPatient()
        when:
        createPatientPage = browsers.at CreatePatientPage
        then: 'Create patient with the First name and Last name and keep the patient ID blank.'
        createPatientPage.titleCreateNewPatient("Create New Patient")
        createPatientPage.enterPatientFirstName('March', '20', '1978')
        String FirstName = createPatientPage.getPatientFirstName()
        String LastName = createPatientPage.getPatientLastName()
        createPatientPage.selectDiabetesType()
        createPatientPage.selectTherapyType()
        createPatientPage.clickSaveBtn()
        createPatientPage.valueNewPatient(LastName, FirstName)
        when: ''
        patientStudyPage = browsers.at PatientStudyPage
        sleep(2000)
        then: ''
        patientStudyPage.exitPatient()
        when: ''
        signInPage = browsers.at SignInPage
        then: 'Verify that a “Please wait…” message shall be temporarily displayed.'
        when:
        homePage = browsers.at HomePage
        then:
        signInPage.searchDisplayed()
        signInPage.searchForPatient(FirstName)
        then: 'Verify the Date of Birth in the Patient List is displayed in the mediu m format ( Item- N ) .'
        signInPage.verifyDateOfBirth(countryJapan[7])
    }
    def 'Patient List - Japan'() {
        when: 'Mouse over the “User Guide” link (see in picture below).'
        homePage = browsers.at HomePage
        then:''
        homePage.mouseOverUserGuide()
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the User Guide Link Address (Item-U).'
        homePage.addressUserGuideLinkdisplayed(countryJapan[5])
        then: 'Mouse over the “ Resources ” link (see in picture below).'
        homePage.mouseOverResources()
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the Resources Link Address (Item-T).'
        homePage.addressResourcesLinkDisplayed(countryJapan[6])
        then: 'Mouse over the “Order supply” link (see in picture above ).'
        homePage.mouseOverOrderSupply()
        then: 'Order supplies link and notice the address in the bottom left hand corner.'
        then: 'Verify the address displayed in the Web Browser Status Bar (bottom left) ends with the Order Supplies Link Address (Item-X)'
        homePage.addressOrderSuppliesLinkDisplayed(countryJapan[8])
    }
    def 'Clinic Settings - Japan'() {
        when: 'Select the Clinic Settings tab (2 nd Tab from the top left).'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        then: 'Select the Report Settings sub-tab if not already selected.'
        then: 'Clinic Settings – Time Display Format'
        clinicSettingsPage.clickOnClinicSettings()
        when: 'Verify in the Time Display Area ( in the picture above) , the format selected matches the Default Time Format (Item-K) .'
        dailyOverlayReportPage = browsers.at DailyOverlayReportPage
        then:''
        dailyOverlayReportPage.timeDisplayAreaDefault()
        then: 'Verify all timestamps in the screen are displayed in the same format as the selected format with the correct Time Hour Separator (Item-L) for the Country under test.'
        then: 'Change the Time Formate to the other value. Click the Save button to activate the change.'
        dailyOverlayReportPage.timeDisplayFormat()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.dialogSuccessWinPatient()
        dailyOverlayReportPage.successPopupSavedOkBtnvalue(countryJapan[10])
        dailyOverlayReportPage.successPopupSavedOkBtn()
        when: 'Verify that all timestamps in the screen are now displayed in the newly selected Time Display Format.'
        dailyOverlayReportPage.verifyBreakfastСonverted24hour30min()
        dailyOverlayReportPage.verifyLunchСonverted24hour30min()
        dailyOverlayReportPage.verifyDinnerСonverted24hour30min()
        dailyOverlayReportPage.verifyEveningСonverted24hour30min()
        dailyOverlayReportPage.verifySleepingСonverted24hour30min()
        then: 'Verify the Time Hour Separator (Item-L) remains the same as before.'
        then: 'Change the Time Display Format to the default value and save the change.'
        dailyOverlayReportPage.timeDisplayFormat()
        dailyOverlayReportPage.saveReportSettings()
        dailyOverlayReportPage.dialogSuccessWinPatient()
        dailyOverlayReportPage.successPopupSavedOkBtnvalue(countryJapan[10])
        dailyOverlayReportPage.successPopupSavedOkBtn()
        then: 'Clinic Settings - Glucose Unit'
        then: 'Verify in the Glucose Units Area ( in the picture above) , the glucose units format selected matches the Default Glucose Unit (Item-J) .'
        dailyOverlayReportPage.glucoseUnitsAreaСannotСhange()
        then: 'Verify all the numbers displayed in this screen using the Decimal Separator for Numbers (Item-P) and Group Separator for Number (Item-Q) where ver applicable.'
        dailyOverlayReportPage.decimalSeparatorForNumbersGroupSeparator()
        then: 'Clinic Settings - Glucose Unit ( Non Canada )'
        then: 'For country other than Canada, change the Glucose Units to a different value .'
        then: 'Verify the numbers in the Glucose target Range are changed to match the newly selected unit .'
        then: 'Verify all the numbers displayed in this screen using the Decimal Separator for Numbers (Item-P) and Group Separator for Number (Item-Q) wherever applicable.'
        then: 'Change the Glucose Units back to the original default value.'
        dailyOverlayReportPage.backOriginalGlucoseUnits()
        then: 'Clinic Settings - Glucose Unit ( Canada Only )'
        then: 'Verify the Glucose Units Area ( in the picture above) is disable such that user cannot change the unit to a different value.'
        dailyOverlayReportPage.glucoseUnitsAreaСannotСhange()
        then: 'Open Patient Record Page'
        then: 'Select the Home tab.'
        dailyOverlayReportPage.patientHomeTabClick()
        then: 'Open the Sample Patient by double clicking the s a mple patient row or by one click on the sample patient row and followed by clicking the Open patient button at the bottom left of the screen.'
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        then: 'Verify the date range displayed above the Reports section ( in picture above) is displayed in short date format (Item-M)'
        dailyOverlayReportPage.dateRangeDisplayed(countryJapan[9])
        then: 'Verify the Glucose Target Range values in the Patient Report Settings section ( in picture above) are displayed in the Default Glucose Unit (Item-J) with the Decimal Separator (Item-P) .'
        dailyOverlayReportPage.valuesGlucoseTargetRange()
        then: 'Verify the timestamps of each meal period in the Patient Report Settings Section ( in picture above) are display ed in the Default Time format (Item-K) with the specific Time Hour Separator (Item-L)'
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(5)', '6:00 AM – 10:00 AM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(7)', '11:00 AM – 3:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(9)', '5:00 PM – 8:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(11)', '5:00 PM – 8:00 PM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(13)', '3:00 AM – 6:00 AM')
        dailyOverlayReportPage.timestampsEachMealPeriod('#report_settings > p:nth-child(15)', '5:00 AM – 7:00 AM')
        then: 'Select the Edit link at the bottom right of the Patient Report Settings section ( in picture above)'
        dailyOverlayReportPage.clickEditNewPatientSettings()
        then: 'Verify the Default Time format (Item-K) is selected under the Time Display Section and all timestamps displayed under the Time Period section are displayed in the Default Time format (Item-K) using the specific Time Hour Separator (Item-L)'
        dailyOverlayReportPage.timeDisplayAreaDefault()
        dailyOverlayReportPage.verifyBreakfastСonverted12hour30min()
        dailyOverlayReportPage.verifyLunchСonverted12hour30min()
        dailyOverlayReportPage.verifyDinnerСonverted12hour30min()
        dailyOverlayReportPage.verifyEveningСonverted12hour30min()
        dailyOverlayReportPage.verifySleepingСonverted12hour30min()
        then: 'Verify the Default Glucose Unit (Item-J) is selected in the Glucose Units Section .'
        dailyOverlayReportPage.selectedDefaultGlucoseUnit()
        then: 'Verify the Glucose target Rage values under both the General Report Settings and Overlay by Meal Report Settings sections are displayed with the correct Decimal Separator (Item-P) and, when applicable, Group Separator (Item-Q) .'
        then: 'Verify in the Glucose Units section that the user can select a different value when the Country under test is not Canada and the user cannot select a different value when the Country Under Test is Canada.'
        dailyOverlayReportPage.sectionGlucoseUnitsDifferentValue()
        dailyOverlayReportPage.patientHomeTabClick()
        homePage.selectPatientFromList()
        homePage.clickOpenPatientBtn()
        dailyOverlayReportPage.goToLogbook()
    }
    def 'Logbook - Japan'() {
        when: 'Click the Open Logbook button.'
        logBookPage = browsers.at LogbookPage
        then: 'Verify all the timestamps in the Time Column shows the value in the Default Time Format (Item-K) with the correct Time Hour Separator (Item-L) .'
        waitFor {  $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains(countryJapan[11])}
        waitFor {  $(id: 'logbooks').find("td")*.text().toString().replaceAll(" , .", "").contains(countryJapan[12])}
        //  logBookPage.LogbookColumnShowsDefaultTimeFormat()
        then: 'Verify the glucose units displayed in the logbook (in the column header) matches the  Default Glucose Unit(Item-J) .'
        logBookPage.glucoseUnitsDisplayed()
        then: 'Verify all the dates are displayed in short date format (Item-M) with a four digit year .'
        logBookPage.logbookDateFormat()
        then: 'Verify all the BG values in the BG column are displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the correct Group Separator for numbers (Item-Q) .'
        then: 'Select any row with data.'
        logBookPage.logbookSelectAnyRow()
        then: 'Select the Edit button (Second button from top to the left of the table) to bring up the pop-up Editor'
        logBookPage.logbookEditButton()
        then: 'Verify the Date value is displayed in the short date format with a four digit year .'
        logBookPage.logbookDateValue('9/29/2009')
        then: 'Verify the Time section:'
        then: 'Time Label shows the Default Time format (Item-K)'
        logBookPage.logbookTimeLabel(countryJapan[13])
        then: 'The Hour field shows the value corresponding to the Default Time format (Item-K).'
        logBookPage.logbookCorrespondingDefaultTimeFormat(countryJapan[11], countryJapan[12])
        then: 'The correct Time Hour Separator (Item-L) is displayed between the Hour and the Miniute fields.'
        logBookPage.logbookTimeHourSeparator()
        then: 'The AM/PM field (3 rd field in the Time section) shows the correct value matching the Default Time format (Item-K)'
        logBookPage.logbookCorrespondingDefaultTimeFormat(countryJapan[11], countryJapan[12])
        then: 'Verify the the BG section:'
        then: 'The Label in the column header should show the Default Glucose Unit (Item-J) .'
        logBookPage.logbookDefaultGlucoseUnitLabel()
        then: 'The value displayed should have the correct Decimal Separator (Item-P) .'
        logBookPage.logbookDecimalSeparator()
        logBookPage.logbookDecimalSeparatorMmolL()
        then: 'Select the “X” button on the top right corner of the pop-up to return to the Logbook Data Page .'
        logBookPage.logbookSelectXbutton()
        then: 'Select the Cancel link to return to the Patient Record Page .'
        logBookPage.logbookReturnPatientRecordPage()
        then: 'Data Table Report'
        then: 'In the Patient Record Page, click on the pull-down menu and select Regenerate Reports (3 rd item from the top, see picture below).'
        dailyOverlayReportPage.selectRegenerateReports()
        then: 'From the same pull-down menu, select Generate Data Table (2 nd option from the top).'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date > in medium date format (Item-N) - <End Date > in medium date format (Item-N)'
        then: 'where <Start Date> does not show the Year except when the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Navigate to the dropdown menu and choose the second option from there (Generate data table).'
        then: 'Verify the dates displayed in the table under the “Date and Time” appears in long date format (Item-O) .'
        then: 'Verify the timestamps in the Report:'
        then: 'The timestamps in the Date and Time column are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L)'
        then: 'Verify the all numbers displayed in the report are in displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the Group Separator for numbers (Item-Q) .'
        then: 'The Printed: timestamp displayed at the bottom of each page is in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L)'
        then: 'Verify the glucose units are displayed in the Default Glucose Unit (Item-J) throughout the report.'
        dailyOverlayReportPage.selectGenerateDataTable()
        dailyOverlayReportPage.waitForDataLoaded()
    }
    def 'Data Table - Japan'(){
        when:''
        def pdfLink = downloadStream($('td.data_table_r>ul>li>a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains("2009 10. 04")
        assert output.contains("較正用血糖値")
        assert output.contains("午前 5:10:02")
        assert output.contains("午後 2:00:02")
        assert output.contains("午前 11:15:02")
        assert output.contains("午後")
        assert output.contains("午前")
        assert output.contains("(mg/dL)")
        assert output.contains("その他の情報およびコメントイベント血糖値(mg/dL)センサグルコース(mg/dL)日時")
        assert output.contains("[13.70nA]")
        assert output.contains("[19.70nA]")
        parser.getPDDocument().close()
        then: 'Close the Data Table Report.'
    }
    def 'Daily Overlay Report - Japan'() {
        when: 'Click the Daily Overlay link to o pen the Daily Overlay Report.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> does not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify all timestamps:'
        then: 'The timestamps displayed on the x-axis of the Sensor Data Chart should be displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L) . If the 12 hour format is used, then “a” and “p” should be used to represent “AM” and “PM” respectively.'
        then: 'The Printed: timestamp at the bottom of each page is displayed per the Default Time format (Item-K)'
        then: 'Verify the glucose units are displayed correctly throughout the report :'
        then: 'The title of the Sensor Data Section should include the Default Glucose Unit (Item-I)'
        then: 'The Title of the Excursion Summary section should include the Default Glucose Unit (Item- J )'
        then: 'Verify the date s in the following locations are are displayed in medium date format (Item-N) excluding the year .'
        then: 'The Dates in the Sensor Data section above the Chart'
        then: 'The Dates in the column headers in the Sensor Data section'
        then: 'The Dates in the column headers in the Excursion Summary Section'
        then: 'Verify all numerical values in the report are displayed with the correct Decimal Separator (Item-P) and, wheres applicable, the correct Group Separator for Numbers (Item-Q):'
        then: 'The left Y-axis marker values of the Sensor Data Chart'
        then: 'The right Y-axis marker values of the Sensor Data Chart'
        then: 'The values in the data table in the Sensor Data Section'
        then: 'The values in the Excursion Summary data table'
        then: 'The values in the row header of the Duration Distrbution Table'
        then: 'Verify the Title of the Duration Distribution section is always “Duration Distribution (H:mm) regardless what the Default Time format (Item-K) and Time Hour Separator (Item-L) are for the Country Under Test.'
        then: 'Verify that regardless the what the country’s Default Time format (Item-K) and Time Hour Separator (Item-L) are, the duration v alues in the Duration Distribution table are always displayed in H:mm format, where ‘H’ is total hours in short format and ‘mm’ is remainder minutes.'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(4) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Patient Sample M.さんの食事によるオーバーレイ")
        assert output.contains ("2009 10. 04")
        assert output.contains ("11:00p 12:00a 1:00a 2:00a 3:00a 4:00a 5:00a 6:00")
        assert output.contains ("3:00～午前")
        assert output.contains ("午後 11:00")
        assert output.contains ("70")
        assert output.contains ("150")
        assert output.contains ("100")
        assert output.contains ("200")
        assert output.contains ("300")
        assert output.contains ("≤40")
        assert output.contains ("400")
        assert output.contains ("目標範囲以上目標範囲未満食事による1日の平均(mg/dL)")
        assert output.contains ("夜間のセンサグルコースデータ(mg/dL)")
        assert output.contains ("夜間夕食後夕食前昼食後昼食前朝食後朝食前就寝中")
        assert output.contains ("月 9. 28")
        assert output.contains ("火 9. 29")
        assert output.contains ("水 9. 30")
        assert output.contains ("木 10. 01")
        assert output.contains ("土 10. 03")
        assert output.contains ("日 10. 04")
        then: 'Close the Daily Overlay Report.'
    }
    def 'Overlay by Meal Report - Japan'() {
        when: 'From the Patien t Record Page, click the Overlay by Meal link to o pen the report.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> doesn not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify the time stamps in the following areas of the report are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L):'
        then: 'The header of the Meal Event Table'
        then: 'The x-axis markers of the Night Time Sensor Data Graph. These timestamps should have “a” and “p” representing “AM” and “PM” when the 12-hour time format is used'
        then: 'The Printed: timestamp at the bottom of each page.'
        then: 'Verify the glucose units in the following areas are displayed with the Default Glucose Unit (Item- J ):'
        then: 'The Title of the Overlay by Meal Event Section'
        then: 'The Title of the Daily Average by Meal Event Section'
        then: 'The Title of the Night Time Sensor Data Section'
        then: 'Verify the following Graph Legends includes an unambiguous day-of-the week abbreviation followed by date s in m edium date format (Item-N) without the year :'
        then: 'Graph Legend next to the Meal Event Graph in the Overlay by Meal Event Section'
        then: 'Graph Levend above the Night Time Sensor Graph in the Night Time Sensor Data Section'
        then: 'Verify all values in the reports are displayed with the correct Decimal Separator (Item-P) and, if applicable, the Group Separator for the Numbers (Item-Q) .'
        then: 'Close the Overlay by Meal Report .'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(5) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Patient Sample M.さんの1日のサマリ")
        assert output.contains ("2009 10. 04")
        assert output.contains ("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        assert output.contains ("月 9. 28 (mg/dL) センサ")
        assert output.contains ("火 9. 29 (mg/dL) センサ")
        assert output.contains ("水 9. 30 (mg/dL) センサ")
        assert output.contains ("木 10. 01 (mg/dL) センサ")
        assert output.contains ("金 10. 02 (mg/dL) センサ")
        assert output.contains ("土 10. 03 (mg/dL) センサ")
        assert output.contains ("日 10. 04 (mg/dL) センサ")
        assert output.contains ("70")
        assert output.contains ("150")
        assert output.contains ("100")
        assert output.contains ("200")
        assert output.contains ("300")
        assert output.contains ("≤40")
        assert output.contains ("400")
        assert output.contains ("40または400 mg/dLでセンサトレースが太く平らな場合は、CGMの値がこれらの限界値を超えている可能性があります。")
        parser.getPDDocument().close()

    }
    def 'Daily Summary Report - Japan'() {
        when: 'From the Patient Report Page, click on the Daily Summary link to o pen the re port.'
        then: 'Verify the date range displayed under the report title is showing the start date and end date in the following format:'
        then: '<Start Date> in medium date format (Item-N) - <End Date> in medium date format (Item-N)'
        then: 'where <Start Date> doesn not show the Year if the medium date format begins with year and both the Start Date and End Date are in the same Year .'
        then: 'Verify the timestamps in the following areas of the report are displayed in the Default Time format (Item-K) with the correct Time Hour Separator (Item-L):'
        then: 'The Title of each daily graph'
        then: 'The x-axis markers of each Daily Graph. These timestamps should have “a” and “p” representing “AM” and “PM” when the 12-hour time format is used'

        then: 'The Printed: timestamp at the bottom of each page .'
        then: 'Verify the glucose units in the following areas are displayed with the Default Glucose Unit (Item-I):'
        then: 'The Title of each daily graph .'
        then: 'Verify the title of each Daily Graph includes an unambiguous day-of-the week abbreviation followed by a date in medium date format (Item-N) without the year'
        then: 'Verify the following values in the reports are displayed with the correct Decimal Separator (Item-P) and, if applicable, the Group Separator for the Numbers (Item-Q) :'
        then: 'The value of the left y-axis markers in each Daily Graph'
        then: 'The value of the right y-axis markers in each Daily Graph'
        then: 'Note: (if required) To arrange data select Ansicht Optionen Kein Umbrunch.'
        then: 'Close the Daily Summary Report.'
        def pdfLink = downloadStream($('.study > table:nth-child(2) > tbody > tr > td > ul> li:nth-child(5) > a').@href)
        PDFParser parser = new PDFParser(pdfLink)
        parser.parse()
        COSDocument cos = parser.getDocument()
        PDDocument pdDoc = new PDDocument(cos)
        def strip = new PDFTextStripper()
        String output = strip.getText(pdDoc)
        assert output.contains ("Patient Sample M.さんの1日のサマリ")
        assert output.contains ("2009 10. 04")
        assert output.contains ("12:00a 2:00a 4:00a 6:00a 8:00a 10:00a 12:00p 2:00p 4:00p 6:00p 8:00p 10:00p 12:00a")
        assert output.contains ("月 9. 28 (mg/dL) センサ")
        assert output.contains ("火 9. 29 (mg/dL) センサ")
        assert output.contains ("水 9. 30 (mg/dL) センサ")
        assert output.contains ("木 10. 01 (mg/dL) センサ")
        assert output.contains ("金 10. 02 (mg/dL) センサ")
        assert output.contains ("土 10. 03 (mg/dL) センサ")
        assert output.contains ("日 10. 04 (mg/dL) センサ")
        assert output.contains ("70")
        assert output.contains ("150")
        assert output.contains ("100")
        assert output.contains ("200")
        assert output.contains ("300")
        assert output.contains ("≤40")
        assert output.contains ("400")
        assert output.contains ("40または400 mg/dLでセンサトレースが太く平らな場合は、CGMの値がこれらの限界値を超えている可能性があります。")
        parser.getPDDocument().close()
    }
    def 'CSV Export File - Japan'() {
        when: 'From the Patient Record Page, click on the Other Options pull-down menu, and select Export Data .If the security warning comes up, select the option to allow file downloading, select the Export Data option from the pull-down menu again.'
        then: 'Click Save to save the file in your workstation’s desktop, and then o pen the file using Microsoft WordPad.'
        then: 'Verify the date format in the “Report Range” appears as short format . (Step 7)'
        then: 'Verify the time format is displayed as HH:mm:ss using a colon as a separator.'
        then: 'Verify the time format is displayed in 24 hour format.'
        then: 'Verify the glucose units are displayed correctly throughout the report (Column G) .'
        then: 'Verify the date formats are displayed correctly throughout the report.'
        then: 'Verify the number formats are displayed correctly throughout the report.'
        then: 'Verify the columns are separated by a TAB value by default .'
        then: 'Verify that value is determined by user account’s Clinic Info Country.'
        then: 'Note: Check CareLink iPro SRS - Internationalization Specification ( ES9926) for Clinic_Info_Country'
        then: 'Verify the rows are left justified and are displayed in order and start with the Data Index.'
        then: 'Close the Export Report and the application.'
        then: 'End of the test.'


    }
    }