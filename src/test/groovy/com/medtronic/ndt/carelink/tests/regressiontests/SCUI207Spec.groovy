package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.LanguagePage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Slf4j
@Title('SCUI 207 - Select Country and Language Screen')
@Stepwise
@Screenshot
class SCUI207Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static LanguagePage languagePage

    def 'Select Country and Language Screen'() {
        when: 'Launch the MMT -7340 application.'
        and: 'Navigate to the Log-in Screen.'
        signInPage = browsers.to SignInPage
        then: 'Verify there is a small flag symbol and change country/territory and language link on the upper right corner of the log-in screen.'
        signInPage.checkIncludedFooterElements()
        signInPage.checkGuiElements()
        def langValue=signInPage.langValue
        when: 'Click on “Change country/territory and language” link.'
        signInPage.clickSelectCountryLanguage()
        languagePage = browsers.at LanguagePage
        then: 'Verify it navigates to the new screen called Select Country/Territory and Language Screen.'
        then: 'Verify the system shall display only the Medtronic Logo and the CareLink Logo of the Common Screen Header Elements.'
        then: 'Verify that the System is not including any of the Common Screen Footer Elements.'
        then: 'Verify that the Screen Title for Country/Territory and Language Screen shall be displayed and titled as “ Select Country/Territory and Language”.'
        then: 'Verify there is a selection for each supported country/territory and language, as detailed in the Internationalization and Localization Specification, shall be available to the user for selection.'
        languagePage.verifyElements("Select Country/Territory and Language",langValue)
        languagePage.checkAllLocales()

        when: 'Click on the drop down button to view the list of the countries available.'
        and: 'Select a country.'
        languagePage.selectCountry("United States")
        and: 'Click on the drop down button to view the list of the languages available.'
        and: 'Select a Language.'
        languagePage.selectLang("English")
        then: 'Verify there is a “Select” button under Language dropdown menu.'
        languagePage.verifySelectButton()

        when: 'Click on the “Select” button.'
        languagePage.buttonSelectClick()
        signInPage = browsers.at SignInPage
        then: 'Verify upon selecting a Country/Territory and Language, the system shall return to the Login- Screen with the Selected Country and Language settings '
        signInPage.signInButtonDisplayed()
        signInPage.checkIncludedFooterElements()

        when: 'Navigate back to the Country/Territory and Language Screen.'
        signInPage.clickSelectCountryLanguage()
        languagePage = browsers.at LanguagePage
        then: 'Verify there is a selection for each supported country/territory, as detailed in the Internationalization and Localization Specification, labeled of “Country/Territory”.'
        then: 'Verify there is a selection for each supported language associated with the selected country/territory, as detailed in the Internationalization and Localization Specification, labeled of “Language”.'
        then: 'Verify there is a Select button, labeled as “Select” that shall apply the selected Country/Territory and Language settings and navigate to the Login Screen.'
        languagePage.selectCountryAndLanguageAll()
        and: 'End of test.'
    }
}



