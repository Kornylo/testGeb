package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Screenshot
@Title('SСUI283 - Clinic Report Settings Screen - Convert Glucose Units Confirmation Window')

class SCUI283Spec extends CareLinkSpec {
    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage

    def 'Clinic Report Settings Screen'() {
        when: 'Open software under test.'
        signInPage = browsers.to SignInPage
        then: 'Login using an existing username and password.'
        signInPage.enterUsername(ApiPropertySpak.hcpAccountUserName1)
        signInPage.enterPassword(ApiPropertySpak.hcpAccountPassword1)
        then: 'Record the Username.'
        then: 'Record the Password.'
        println("Username: " + ApiPropertySpak.hcpAccountUserName1)
        println("Password: " + ApiPropertySpak.hcpAccountPassword1)
        signInPage.clickOnSignIn()
        sleep(1000)
        signInPage.postponeMFA()
        then: 'At the Home Screen, click on the “Clinic Settings” link to navigate to the Clinic Settings Screen: Clinic Report Settings Screen.'
        when: ''
        clinicSettingsPage = browsers.at ClinicSettingsPage
        clinicSettingsPage.clickOnClinicSettings()
        then: ''
        and: 'Ensure that the currently selected [Clinic_Settings_Glucose_Units] is “mg/dL’” .'
        clinicSettingsPage.bgunitMgdlClick()
        and: 'Click on the radio button for “mmol/L”.'
        clinicSettingsPage.selectGlucoseUnitsMmol()
        then: 'Verify that the software invokes the Convert Glucose Units Confirmation Window.'
        then: 'Verify that the Convert Glucose Units Confirmation Window is modal.'
        clinicSettingsPage.confirmationWindowDisplayed()
        then: 'Verify that the dialogue window displays the title “Convert units”.'
        clinicSettingsPage.modalWindowConfirmationTitleDisplays('Convert units')
        then: 'Verify that the dialogue window displays the following text: “Click OK to convert all glucose settings on this page. Please be sure to confirm that each of the new values is correct.'
        and: 'Reminder: When you are finished, click Save to save these settings.” '
        clinicSettingsPage.modalWindowConfirmationTextDisplays()
        clinicSettingsPage.modalWindowConfirmationTextDisplays()
        then: 'Verify that there is a Cancel Button labeled “Cancel”.'
        clinicSettingsPage.modalWindowCancel('Cancel')
        when: 'Click the Cancel Button.'
        clinicSettingsPage.modalWindowCancelClick()
        then: 'Verify that clicking the Cancel Button closes the confirmation window and returns the Glucose Units setting to mg/dL.'
        clinicSettingsPage.modalWindowOKNotDisplayed()
        when: 'Click on the radio button for “mmol/L”.'
        clinicSettingsPage.selectGlucoseUnitsMmol()
        and: 'Observe the Covert Glucose Units Confirmation Window.'
        clinicSettingsPage.modalWindowConfirmationDisplayed()
        then: 'Verify that there is an OK button labeled as “OK”.'
        when: 'Click the OK Button.'
        clinicSettingsPage.modalWindowVerifyClickOK('OK')
        then: 'Verify that the OK Button closes the confirmation window and converts the BG settings to the mmol/L.'
        clinicSettingsPage.modalWindowOKNotDisplayed()
        when: 'Ensure that the currently selected [Clinic_Settings_Glucose_Units] is “mmol/L”.'
        clinicSettingsPage.selectGlucoseUnitsMmolValue('on')
        and: 'Click on the radio button for “mg/dL”.'
        clinicSettingsPage.bgunitMgdlClick()
        then: 'Verify that the software invokes the Convert Glucose Units Confirmation Window.'
        then: 'Verify that the Convert Glucose Units Confirmation Window is modal.'
        clinicSettingsPage.confirmationWindowDisplayed()
        then: 'Verify that the dialogue window displays the title “Convert units”.'
        clinicSettingsPage.modalWindowConfirmationTitleDisplays('Convert units')
        then: 'Verify that the dialogue window displays the following text: “Click OK to convert all glucose settings on this page.Please be sure to confirm that each of the new values is correct.Reminder: When you are finished, click Save to save these settings .”  '
        clinicSettingsPage.modalWindowConfirmationTextDisplays()
        then: 'Verify that there is a Cancel Button labeled “Cancel”.'
        clinicSettingsPage.modalWindowCancel('Cancel')
        when: 'Click the Cancel Button.'
        clinicSettingsPage.modalWindowCancelClick()
        then: 'Verify that clicking the Cancel Button closes the confirmation window and returns the Glucose Units setting to mmol/L .'
        clinicSettingsPage.selectGlucoseUnitsMmolValue('on')
        when: 'Click on the radio button for “ mg/dL ”.'
        clinicSettingsPage.bgunitMgdlClick()
        and: 'Observe the Covert Glucose Units Confirmation Window.'
        clinicSettingsPage.modalWindowConfirmationDisplayed()
        then: 'Verify that there is an OK button labeled as “OK”.'
        when: 'Click the OK Button.'
        clinicSettingsPage.modalWindowVerifyClickOK('OK')
        then: 'Verify that the OK Button closes the confirmation window and converts the BG settings to the mg/dL.'
        clinicSettingsPage.bgunitMgdlValue('on')
        and: 'End of the test.'
    }
}