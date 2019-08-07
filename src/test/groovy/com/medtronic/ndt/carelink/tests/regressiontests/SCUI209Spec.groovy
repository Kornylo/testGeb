package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.Precondition
import groovy.util.logging.Slf4j
import spock.lang.Title

@Slf4j
@Screenshot
@RegressionTest
@Title('SCUI209 - Home Screen – Functionality')
class SCUI209Spec extends CareLinkSpec implements ScreenshotTrait {
    static HelpPage helpPage
    static SignInPage signInPage
    static HomePage homePage
    static CreatePatientPage createPatientPage
    static NewClinicRegistrationPage newClinicRegistrationPage
    static ClinicLocalePage clinicLocalePage
    static ClinicEULAPage clinicEULAPage
    static ClinicEnterInfoPage clinicEnterInfoPage
    static ClinicEnterAdminInfoPage clinicEnterAdminInfoPage
    static FinishClinicCreationPage finishClinicCreationPage
    static Precondition precondition

    static final adminOus = "OUS" + Calendar.getInstance().format('MMMddHHmmss').toString()
    def email = Calendar.getInstance().format('MMMddHHmmss').toString() + "@medtronic.com"
    def pass = "Test1234@"


    def 'Precondition step - Create Clinic and Patients'() {
        when:'Create Clinic'
        precondition = browsers.to Precondition
        precondition.registerClinic(adminOus, "Argentina", email, pass)
        println("Username: " + adminOus + " Password: " + pass)
        and:'Sign in and create patients'
        precondition.signInAsClinicAdmin(adminOus, pass)
        then:''
        println "Logging in as " + adminOus
        precondition.createPatientsForClinic()
        println "Patients for OUS clinic were created"
    }

    def "Sign in as #adminOus with #pass to OUS clinic for searching"() {
        when: 'Open software under test'
        precondition = browsers.to Precondition
        then: 'Sign into the  application using the above credentials'
        precondition.signInAsClinicAdmin(adminOus, pass)
        then: 'Record the Username and Password'
        println "Logging in as " + adminOus
    }

    def 'Patient List search'() {
        when: 'Observe the records currently in the Patient List, scrolling if necessary to view each one'
        and: 'Click on the “Search” entry box'
        homePage = browsers.at HomePage
        homePage.homeScreenIsAppeared()
        waitFor { !$("#loading").displayed }
        then: ''
        homePage.clickOnSearchEntryBox()

        then: 'Enter a text with space into the search field'                      //Step updated due to entering a space is not sufficient
        waitFor { homePage.pageComplete() }
        homePage.searchInput("A a")
        then: 'Verify that the Space is treated as entry separator'
        homePage.searchInputResult("A a")

        when: 'Enter a text with comma into the search field'              //Step updated due to entering a comma is not sufficient
        homePage.searchInput("A,a")
        then: 'Verify that the Comma is treated as entry separator'
        homePage.searchInputResult("A,a")

        when: 'Enter a single letter into the search field.  (For example: “A”)'
        homePage.searchInput("A")
        then: 'Verify that the Patient List automatically narrows down to the records whose last names, first names, or patient IDs contain that letter' //Update Step to any column matches the records
        then: 'Verify that records not containing that letter have been eliminated from the Patient List'                         //Update Step is needed
        homePage.searchInputResult("A")
        homePage.checkPatientListData(11, "A")

        when: 'Enter a single number into the search field.  (For example: “0”)'
        homePage.searchInput("0")
        then: 'Verify that the Patient List automatically narrows down to the records whose patient IDs or date of births contain that number'
        then: 'Verify that no records containing that number have been eliminated from the Patient List'
        homePage.searchInputResult("0")
        homePage.checkPatientListData(10, "0")

        when: 'Enter a singe letter followed by a number into the search field.  (For example:  “A0”)'
        homePage.searchInput("A0")
        then: 'Verify that the Patient List automatically narrows down to the records whose patient IDs contain that letter followed by that number'
        then: 'Verify that no records containing that letter followed by that number have been eliminated from the Patient List'
        homePage.searchInputResult("A0")
        homePage.checkPatientListData(1, "A0")

        when: 'Enter a single number followed by a letter into the search field. (For example: “0A”)'
        homePage.searchInput("0A")
        then: 'Verify that the Patient List automatically narrows down to the records whose patient IDs contain that number followed by that letter'
        then: 'Verify that no records containing that number followed by that letter have been eliminated from the Patient List'
        homePage.searchInputResult("0A")
        homePage.checkPatientListData(3, "0A")

        when: 'Enter a single letter into the search field. (For example: “a”)'
        homePage.searchInput("a")
        homePage.searchInputResult("a")
        and: 'Enter another letter after this into the search field. (For example: “ab”)'
        homePage.searchInput("ab")
        then: 'Verify that the Patient List further narrows down to the records whose last names, first names, or patient IDs contain that letter string'
        then: 'Verify that no records containing that letter string have been eliminated from the Patient List'
        homePage.searchInputResult("ab")
        homePage.checkPatientListData(7, "ab")

        when: 'Enter another letter after this into the search field. (For example: “abc”)'
        homePage.searchInput("abc")
        then: 'Verify that the Patient List further narrows down to the records whose last names, first names, or patient IDs contain that letter string'
        then: 'Verify that no records containing that letter string have been eliminated from the Patient List'
        homePage.searchInputResult("abc")
        homePage.checkPatientListData(7, "abc")

        when: 'Enter a single letter into the search field. (For example: “0”)'
        homePage.searchInput("0")
        homePage.searchInputResult("0")
        and: 'Enter another letter after this into the search field. (For example: “01”)'
        homePage.searchInput("01")
        homePage.searchInputResult("01")
        then: 'Verify that the Patient List automatically narrows down to the records whose patient IDs or date of births contain that number string'
        then: 'Verify that no records containing that number string have been eliminated from the Patient List'
        homePage.checkPatientListData(4, "01")

        when: 'Enter another letter after this into the search field. (For example: “012”)'
        homePage.searchInput("012")
        then: 'Verify that the Patient List automatically narrows down to the records whose patient IDs or date of births contain that number string'
        then: 'Verify that no records containing that number string have been eliminated from the Patient List'
        homePage.searchInputResult("012")
        homePage.checkPatientListData(0, "012")


        when: 'Observe a Patient ID record that contains both letters and numbers. Create a new one if necessary'
        and: 'Enter in this Patient ID into the search field up to the point where both letters and numbers have been included. (For example: “ABC1” or “123A”)'
        homePage.searchInput("ABC1")
        then: 'Verify that the Patient List automatically narrows down to the records whose patient IDs contain that letter and number string'
        then: 'Verify that no records containing that letter and number string have been eliminated from the Patient List'
        homePage.searchInputResult("ABC1")
        homePage.checkPatientListData(1, "ABC1")
    }

    def 'Date of Birth US format - known issue  ESF 2631686'() {
        when: 'Observe a Date of Birth record. Create a new one if necessary.'
        and: 'Enter in this Date of Birth into the search field with month in MMM format included. (For example: “May”)'
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain entered string for the month.'
        and: 'Verify that no records containing that entered string for the month have been eliminated from the Patient List.'

        when: 'Enter in this Date of Birth into the search field with month and day in MMM d format included. (For example: “May 13”)'
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain entered string for the month and day.'
        and: 'Verify that no records containing that entered string for the month and day have been eliminated from the Patient List.'

        when: 'Enter in this Date of Birth into the search field with month and day in MMM d, yyyy format included. (For example: “May 13, 2003”)'
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain entered string for the month, day and year.'
        and: 'Verify that no records containing that entered string for the month, day and year have been eliminated from the Patient List.'

        when: 'Enter in this Date of Birth into the search field such that only the 2 digit is included. (For example: “03”)'
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain entered string for the day or year.'
        and: 'Verify that no records containing that entered string for the day or year have been eliminated from the Patient List.'

        when: 'Enter in this Date of Birth into the search field beginning with a space and 2 digits. (For example: “ 13”)'
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain entered string for the day or year.'
        and: 'Verify that no records containing that entered string for the day or year have been eliminated from the Patient List.'
        println("Known issue in ESF 2631686")
        log.info("Known issue in ESF 2631686")
    }

    def 'Date of Birth OUS format'() {
        when: 'Observe a Date of Birth record. Create a new one if necessary.'
        homePage = browsers.at HomePage
        homePage.homeScreenIsAppeared()
        waitFor { !$("#loading").displayed }
        then: ''
        homePage.clickOnSearchEntryBox()
        and: 'Enter in this Date of Birth into the search field up to the point where a slash has been included dd/ or MM/. (For example: “01/”)'
        homePage.searchInput("01/")
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain that number string for the month or day.'
        homePage.searchInputResult("01/")
        and: 'Verify that no records containing that number string for the month or day have been eliminated from the Patient List.'
        homePage.checkPatientListData(3, "01/")

        when: 'Enter in this Date of Birth into the search field up to the point where two slashes have been included dd/MM/. (For example: “01/01/”)'
        homePage.searchInput("01/01/")
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain that number string month and day.'
        homePage.searchInputResult("01/01/")
        and: 'Verify that no records containing that number string for the month and day have been eliminated from the Patient List.'
        homePage.checkPatientListData(1, "01/01/")

        when: 'Enter in this Date of Birth into the search field entirely dd/MM/yyyy. (For example: “01/01/ 2001”)'
        homePage.searchInput("01/01/1980")
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain that number string.'
        homePage.searchInputResult("01/01/1980")
        and: 'Verify that no records containing that number string have been eliminated from the Patient List.'
        homePage.checkPatientListData(1, "01/01/1980")

        when: 'Enter in this Date of Birth into the search field such that only the 2 digit year is included yy. (For example: “01”)'
        homePage.searchInput("80")
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain that number string.'
        homePage.searchInputResult("80")
        and: 'Verify that no records containing that number string have been eliminated from the Patient List.'
        homePage.checkPatientListData(1, "80")

        when: 'Enter in this Date of Birth into the search field beginning with a slash and 2 digits /MM or /yy. (For example: “/01”)'
        homePage.searchInput("/01")
        then: 'Verify that the Patient List automatically narrows down to the records whose dates of birth contain that number string for the month or year.'
        homePage.searchInputResult("/01")
        and: 'Verify that no records containing that number string for the month or year have been eliminated from the Patient List.'
        homePage.checkPatientListData(1, "/01")
    }

    def 'Last Name and First Name with comma separator'() {
        when: 'Observe patient records containing the same Last Name with different First Names. Create them if necessary'
        homePage = browsers.at HomePage
        homePage.homeScreenIsAppeared()
        waitFor { !$("#loading").displayed }
        then: ''
        homePage.clickOnSearchEntryBox()
        and: 'Enter in the Last Name into the search field followed by a comma separator. (For example: “abcdef,”)'
        homePage.searchInput("abcdef,")
        then: 'Verify that the Patient List automatically narrows down to the records whose Last Names match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("abcdef,")
        homePage.checkPatientListData(3, "abcdef,")

        when: 'Continue to enter a matching First Name for one of the records. (For example: “abcdef, ghijk”)'
        homePage.searchInput("abcdef, ghijk")
        then: 'Verify that the Patient List automatically narrows down to the records whose Last Name and First Name match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("abcdef, ghijk")
        homePage.checkPatientListData(2, "abcdef, ghijk")

        when: 'Observe patient records containing the same First Name with different Last Names. Create them if necessary'
        and: 'Enter in the First Name into the search field followed by a comma separator. (For example: “abcdef,”)'
        homePage.searchInput("abcdef,")
        then: 'Verify that the Patient List automatically narrows down to the records whose First Names match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("abcdef,")
        homePage.checkPatientListData(3, "abcdef,")

        when: 'Continue to enter a matching Last Name for one of the records. (For example: “ghijk,abcdef”)'
        homePage.searchInput("ghijk,abcdef")
        then: 'Verify that the Patient List automatically narrows down to the records whose First Name and Last Name match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("ghijk,abcdef")
        homePage.checkPatientListData(2, "ghijk,abcdef")
    }

    def 'Last Name and First Name with space separator'() {
        when: 'Observe patient records containing the same Last Name with different First Names. Create them if necessary'
        and: 'Enter in the Last Name into the search field followed by a space separator. (For example: “abcdef ”)'
        homePage.searchInput("abcdef ")
        then: 'Verify that the Patient List automatically narrows down to the records whose Last Names match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("abcdef ")
        homePage.checkPatientListData(3, "abcdef ")

        when: 'Continue to enter a matching First Name for one of the records. (For example: “abcdef ghijk”)'
        homePage.searchInput("abcdef ghijk")
        then: 'Verify that the Patient List automatically narrows down to the records whose Last Name and First Name match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("abcdef ghijk")
        homePage.checkPatientListData(2, "abcdef ghijk")

        when: 'Observe patient records containing the same First Name with different Last Names. Create them if necessary'
        and: 'Enter in the First Name into the search field followed by a space separator. (For example: “abcdef ”)'
        homePage.searchInput("abcdef ")
        then: 'Verify that the Patient List automatically narrows down to the records whose First Names match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("abcdef ")
        homePage.checkPatientListData(3, "abcdef ")

        when: 'Continue to enter a matching Last Name for one of the records. (For example: “ghijk abcdef”)'
        homePage.searchInput("ghijk abcdef")
        then: 'Verify that the Patient List automatically narrows down to the records whose First Name and Last Name match the entry'
        then: 'Verify that no records matching the entry have been eliminated from the Patient List'
        homePage.searchInputResult("ghijk abcdef")
        homePage.checkPatientListData(2, "ghijk abcdef")
    }

    def 'Various search methods using comma separator'() {
        when: 'Observe any patient record. Create one if necessary'
        and: 'Search for this patient record using the Last Name comma Patient ID'
        homePage.searchInput("a,20")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("a,20")
        homePage.checkPatientListData(2, "a,20")

        when: 'Search for this patient record using the Last Name comma Date of Birth'
        homePage.searchInput("A,1999")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("A,1999")
        homePage.checkPatientListData(2, "A,1999")

        when: 'Search for this patient record using the First Name comma Patient ID'
        homePage.searchInput("alex,1234")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("alex,1234")
        homePage.checkPatientListData(1, "alex,1234") //todo Patient ID should be not random

        when: 'Search for this patient record using the First Name comma Date of Birth'
        homePage.searchInput("Alex,1980")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("Alex,1980")
        homePage.checkPatientListData(1, "Alex,1980")

        when: 'Search for this patient record using the Patient ID comma Last Name'
        homePage.searchInput("1234,Ale")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("1234,Ale")
        homePage.checkPatientListData(2, "1234,Ale")

        when: 'Search for this patient record using the Patient ID comma First Name'
        homePage.searchInput("1234,Ale")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("1234,Ale")
        homePage.checkPatientListData(2, "1234,Ale")

        when: 'Search for this patient record using the Patient ID comma Date of Birth'
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        println("Known issue in ESF 2631686")
        log.info("Known issue in ESF 2631686")

        when: 'Search for this patient record using the Date of Birth comma Last Name'
        homePage.searchInput("1980,Alex")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("1980,Alex")
        homePage.checkPatientListData(1, "1980,Alex")

        when: 'Search for this patient record using the Date of Birth comma First Name'
        homePage.searchInput("1980,Alex")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("1980,Alex")
        homePage.checkPatientListData(1, "1980,Alex")

        when: 'Search for this patient record using the Date of Birth comma Patient ID'
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        println("Known issue in ESF 2631686")
        log.info("Known issue in ESF 2631686")
    }

    def 'Various search methods using space separator'() {
        when: 'Observe any patient record, Create one if necessary'
        and: 'Search for this patient record using the Last Name space Patient ID'
        homePage.searchInput("ABC1 12345")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("ABC1 12345")
        homePage.checkPatientListData(1, "ABC1 12345")

        when: 'Search for this patient record using the Last Name space Date of Birth'
        homePage.searchInput("ABC1 2000")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("ABC1 2000")
        homePage.checkPatientListData(1, "ABC1 2000")

        when: 'Search for this patient record using the First Name space Patient ID'
        homePage.searchInput("abDula 12345")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("abDula 12345")
        homePage.checkPatientListData(1, "abDula 12345")

        when: 'Search for this patient record using the First Name space Date of Birth'
        homePage.searchInput("abDula 2000")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("abDula 2000")
        homePage.checkPatientListData(1, "abDula 2000")

        when: 'Search for this patient record using the Patient ID space Last Name'
        homePage.searchInput("12345 ABC1")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("12345 ABC1")
        homePage.checkPatientListData(1, "12345 ABC1")

        when: 'Search for this patient record using the Patient ID space First Name'
        homePage.searchInput("12345 abDula")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("12345 abDula")
        homePage.checkPatientListData(1, "12345 abDula")

        when: 'Search for this patient record using the Patient ID space Date of Birth'
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        println("Known issue in ESF 2631686")
        log.info("Known issue in ESF 2631686")

        when: 'Search for this patient record using the Date of Birth space Last Name'
        homePage.searchInput("2000 ABC1")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("2000 ABC1")
        homePage.checkPatientListData(1, "2000 ABC1")

        when: 'Search for this patient record using the Date of Birth space First Name'
        homePage.searchInput("2000 abDula")
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        homePage.searchInputResult("2000 abDula")
        homePage.checkPatientListData(1, "2000 abDula")

        when: 'Search for this patient record using the Date of Birth space Patient ID'
        then: 'Verify that the Patient List automatically narrows down to the proper record'
        println("Known issue in ESF 2631686")
        log.info("Known issue in ESF 2631686")
    }

    def 'Column sort ability'() {
        when: 'Clear the search field of any entry'
        homePage.searchInput(" ")
        homePage.searchInputResult(" ")
        and: 'Click on the column labeled “Last Name”'
        homePage.clickOnLastNameLabel()
        then: 'Verify that the Patient List is sorted in ascending or descending order by last name'
        homePage.checkSortingLastName()

        when: 'Click on the column labeled “First Name”'
        homePage.clickOnFirstNameLabel()
        then: 'Verify that the Patient List is sorted in ascending or descending order by first name'
        homePage.checkSortingFirstName()

        when: 'Click on the column labeled “Patient ID”'
        homePage.clickOnPatientIdLabel()
        then: 'Verify that the Patient List is sorted in ascending or descending order by patient ID'
        homePage.checkSortingIds()

        when: 'Click on the column labeled “Date of Birth”'
        then: 'Verify that the patient list is sorted in chronological or reverse chronological order by date of birth'
        println("Known issue in ESF 2631686")
        log.info("Known issue in ESF 2631686")
    }

    def 'Select ability'() {
        when: 'Single click anywhere on a particular row'
        homePage = browsers.at(HomePage)
        homePage.homeScreenIsAppeared()
        homePage.refreshPage()
        then: 'Verify that user is able to click anywhere on the particular row for it to become highlighted'
        then: 'Verify that the entire row is highlighted for the selected patient record'
        homePage.verifyRowHighlighted()

        when: 'Select the Open patient record button'
        homePage.openPatient()
        then: 'Verify that software opens the correct patient record that was selected'
        homePage.verifyCorrectPatientWasOpened("Patient", "Sample M.")

        when: 'Navigate back to the patient list'
        homePage.navigateBack()
        homePage.refreshPage()
        homePage.homeScreenIsAppeared()
        and: 'Double click anywhere on a particular row'
        waitFor { homePage.selectPatient.displayed }
        homePage.openPatientByDoubleClick()
        then: 'Verify that software opens the correct patient record that was selected'
        homePage.verifyCorrectPatientWasOpened("Patient", "Sample M.")

        when: 'Navigate back to the patient list'
        homePage.navigateBack()
        homePage.refreshPage()
        homePage.homeScreenIsAppeared()
        and: 'Select the Create a new patient record button'
        then: 'Verify that user is taken to the New Patient Record Screen'
        homePage.openCreatePatientScreen()
    }
}