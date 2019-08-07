package com.medtronic.ndt.carelink.util


import com.medtronic.ndt.carelink.pages.clinicwizard.*
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.patient.CreatePatientPage
import com.medtronic.ndt.carelink.pages.patient.HomePage
import geb.Page

class Precondition extends Page{

    void registerClinic(String username, String country, String email, String pass) {
        browser.page(SignInPage).checkIncludedFooterElements()
        browser.page(NewClinicRegistrationPage).clickRegisterClinic()
        browser.page(ClinicEULAPage).enrollmentInfoScreen()
        browser.page(NewClinicRegistrationPage).clickOnContinue()
        browser.page(ClinicLocalePage).enrollmentCountryAndLanguageSelectScreen()
        browser.page(ClinicLocalePage).selectCountryAndLanguage(country, "English")
        browser.page(ClinicLocalePage).selectLocale()
        browser.page(ClinicEULAPage).opensEnrollmentTermsAcceptanceScreen()
        browser.page(ClinicEULAPage).selectResidentCheckbox()
        browser.page(ClinicEULAPage).residentCheckboxOn()
        browser.page(ClinicEULAPage).selectAcceptCheckbox()
        browser.page(ClinicEULAPage).acceptCheckboxOn()
        browser.page(ClinicEULAPage).clickAccept()
        browser.page(ClinicEnterInfoPage).enrollmentClinicInformationScreen()
        browser.page(ClinicEnterInfoPage).configureClinic()
        browser.page(ClinicEnterInfoPage).getClinicName()
        browser.page(ClinicEnterInfoPage).clickContinue()
        browser.page(ClinicEnterAdminInfoPage).enterUserNameManual(username)
        browser.page(ClinicEnterAdminInfoPage).enterFirstName("Dmytro")
        browser.page(ClinicEnterAdminInfoPage).enterLastName("Stadnik")
        browser.page(ClinicEnterAdminInfoPage).enterEmail(email)
        browser.page(ClinicEnterAdminInfoPage).enterPassword(pass)
        browser.page(ClinicEnterAdminInfoPage).enterConfirmPassword(pass)
        browser.page(ClinicEnterAdminInfoPage).enterSecurityAnswer("Amazing")
        browser.page(ClinicEnterAdminInfoPage).clickContinue()
        browser.page(FinishClinicCreationPage).verifyEnrollmentCompleteScreen("Congratulations! You have completed enrollment in the Medtronic CareLink Software.")
        browser.page(FinishClinicCreationPage).clickFinish()
        println 'Precondition step - Done, Clinic created'
    }
    void signInAsClinicAdmin(String username, String pass){
        def signInPage = browser.page(SignInPage)
        waitFor {browser.getCurrentUrl().contains("/hcp/login.jsf")}
        signInPage.checkIncludedFooterElements()
        signInPage.enterUsername(username)
        signInPage.enterPassword(pass)
        signInPage.clickOnSignIn()
        sleep(1500)
        signInPage.postponeMFA()
    }

    void createPatientsForClinic() {
        def homePage = browser.page(HomePage)
        def createPatientPage = browser.page(CreatePatientPage)
        homePage.homeScreenIsAppeared()
//        homePage.openCreatePatientScreen()
        //create many patients
        createPatientPage.createListOfPatients()
        homePage.clickSignOutButton()
    }
}
