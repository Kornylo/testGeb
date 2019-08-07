package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.ContactUsPage
import com.medtronic.ndt.carelink.pages.dashboard.PrivacyStatementPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.perfecto.reportium.test.TestContext
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SCUI236 - Privacy Statement Screen')
@RegressionTest
@Slf4j
@Stepwise
@Screenshot

class SCUI236Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static PrivacyStatementPage privacyStatement
    static ContactUsPage contactUs

    def 'Login screen'() {
        when: 'Launch the MMT – 7340 application under test.'
        signInPage = browsers.to SignInPage
        then: 'Verify the system navigates to Login Screen'
        signInPage.signInButtonDisplayed()

        then: 'Verify there is a Privacy statement link included in the footer elements.'
        signInPage.checkIncludedFooterElements()

        when: 'Click on Privacy Statement link.'
        privacyStatement = browsers.at PrivacyStatementPage
        then: 'Verify the system opens a new browser window or tab called Privacy Statement Screen.'
        then: 'Verify the Privacy Statement Screen only display the Medtronic Logo and CareLink Logo from the Common Screen Header Elements.'
        then: 'Verify the Privacy Statement Screen does not display any of the Common Screen Footer Elements.'
        then: 'Verify the Privacy Statement Screen displays the title as “Privacy Statement”'
        then: 'Verify the Privacy Statement is detailed'
        then: 'Verify that body of this statement contains a link Labeled “ Notice of Privacy Practices”.'
        privacyStatement.checkPrivacyStatementScreen()

        when: 'Click on the Link for “ Notice of Privacy Practices”.'
        then: 'Verify that system navigates to ‘"http://www.minimed.com/notices".'
        then: 'Click to go Back to Privacy Statement screen.'
        then: 'Verify that body of this statement contains a link Labeled “Terms of Use”.'
        privacyStatement.checkNoticeOfPrivacyPolicy()

        when: 'Click on the Link for “Terms of Use ”.'
        then: 'Verify that system navigates to “‘../hcp/termsOfUse.jsf” in another browser window or tab.'
        then: 'Close the "Terms of Use" tab.'
        privacyStatement.checkTermOfUseContentFromPrivacyStatement()

        then: 'Verify that body of the Privacy Statement contains a link Labeled “Contact Us”.'
        when: 'Click on the Link for “Contact Us ”.'
        contactUs = browsers.at ContactUsPage
        then: 'Verify that system navigates to “http://www.minimed.com/help/contact” in another browser window or tab.'
        contactUs.checkContactUsFromPrivacyStatement()

        then: 'Verify there is a close button labeled as “Close”'
        when: 'Click on close button.'
        privacyStatement = browsers.at PrivacyStatementPage
        then: 'Verify the system shall close the browser window the privacy statement screen is displayed in.'
        privacyStatement.checkPrivacyStatementCloseButton()
    }
}
