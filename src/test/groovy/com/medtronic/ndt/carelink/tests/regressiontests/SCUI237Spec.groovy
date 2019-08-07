package com.medtronic.ndt.carelink.tests.regressiontests


import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.pages.ContactUsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.pages.dashboard.TermsOfUsePage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import spock.lang.Stepwise
import spock.lang.Title

@Title('SCUI237Spec - Terms of Use Screen')
@RegressionTest
@Stepwise
@Slf4j
@Screenshot
class SCUI237Spec extends CareLinkSpec implements ScreenshotTrait {
    static SignInPage signInPage
    static TermsOfUsePage termsOfUse

    def 'Login screen'() {
        when: 'Launch the MMT – 7340 application under test.'
        signInPage = browsers.to SignInPage
        then: "Verify the system navigates to Login Screen."
        signInPage.signInButtonDisplayed()
        then: 'Verify there is a Terms of Use link is included in the footer elements.'
        signInPage.checkIncludedFooterElements()

        when: 'Click on Terms of Use link.'
        termsOfUse = browsers.at TermsOfUsePage
        then: 'Verify the system opens Terms of Use Screen in a new browser window or tab .'
        then: 'Verify the Terms of Use Screen only display s the Medtronic Logo and CareLink Logo from the Common Screen Header Elements.'
        then: 'Verify the Terms of Use Screen does not display any of the Common Screen Footer Elements.'
        then: 'Verify the Terms of Use Screen display s the title as “Terms of Use”'
        termsOfUse.checkTermOfUseContent()

        then: 'Verify that body of this statement contains a link Labeled “Privacy Policy”'
        termsOfUse.checkPrivacyPolicyLinks()

        when: 'Click on the Link for “ Privacy Policy ”.'
        then: 'Verify that system navigates to ‘../hcp/privacy.jsf’ in another browser window or tab.'
        then: 'Verify that body of this statement contains a link Labeled “Contact Us” .'
        termsOfUse.openPrivacyPolicyLinks()

        when: 'Click on the Link for “Contact Us ”.'
        then: 'Verify that system navigates to “http://www.minimed.com/help/contact” in another browser window or tab.'
        termsOfUse.openContactUsLink("http://www.medtronicdiabetes.com/help/contact")


        then: 'Verify there is a close button labeled as “Close”'
        when: 'Click on close button.'
        termsOfUse = browsers.at TermsOfUsePage
        then: 'Verify the system closes the browser window the "Terms of Use" screen is displayed in.'
        termsOfUse.termOfUseButtonCloseChecking()
    }

}
