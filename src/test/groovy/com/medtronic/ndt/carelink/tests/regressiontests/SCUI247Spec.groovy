package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.pages.HelpPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import com.medtronic.ndt.carelink.util.SwitchingUtil
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Slf4j
@Screenshot
@RegressionTest
@Stepwise
@Title('SCUI247 - Context Sensitive Help')
class SCUI247Spec extends CareLinkSpec implements ScreenshotTrait {
    static HelpPage helpPage
    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage
    static SwitchingUtil globalDataUtil

    def 'User Login'() {
        when: 'A user supplies valid carelink credentials'
        signInPage = browsers.to SignInPage
        then: 'User should be able to successfully log into Carelink envision pro'
        signInPage.login('Akilaa', 'Test1234')
        sleep(10)
/*        screenshot("helpPage", "helpPage_login")*/
    }

    def 'Click Clinic Settings'() {
        given: 'When user successfully logged into carelink application'
        clinicSettingsPage = browsers.at ClinicSettingsPage
        when: 'Click on Clinic Settings > Report Settings.'
        sleep(100)
        clinicSettingsPage.clickOnClinicSettings()
        then: 'User is able to view clinic settings'
    }

    def 'Open general report settings help'() {
        given: 'When user successfully logged into carelink application'
        when: 'User view clinic settings'
        helpPage = browsers.at HelpPage
        globalDataUtil = browsers.at SwitchingUtil
        then: 'Verify in General Report Settings section there is context sensitive help.'
        helpPage.isClickOnIconHelpDisplayed()
        then: 'Click on general report settings help'
        helpPage.clickOnIconHelp()
        log.info("Help window is open")
        sleep(3000)
        then: 'User switch to General Report Settings help'
        globalDataUtil.switchToNewTab()
/*        screenshot("helpPage", "helpPage_window")*/
        then: 'Verify that when Help is activated by user it display text in separate browser window or tab .'
        helpPage.isGeneralSettingsHelpTitleDisplayedSelector(ReadProperties.getProperties().get("settings.help.general.title"), helpPage.helpTitleSettingsHelp())
        log.info("Help title displayed - General Report Settings Help")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.general.text"), helpPage.helpTextSettingsHelp())
        log.info("Help text displayed - NOTE: Changes to these settings will only apply to new patients added to the system. (However, changes to Glucose Units and Time Display will apply to all patients in the system).")
        helpPage.isGeneralSettingsHelpTitleDisplayedSelector(ReadProperties.getProperties().get("settings.help.high.glucose.title"), helpPage.helpTitleHighTargetRange())
        log.info("Help title displayed - High value for Glucose Target Range")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.high.glucose.text"), helpPage.helpTextHighTargetRange())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the upper limit of euglycemia (i.e., target) for this patient. The system uses the High value in reports to show the range that is above the euglycemic or glucose target range.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.low.glucose.title"), helpPage.helpTitleLowTargetRange())
        log.info("Help title displayed - Low value for Glucose Target Range")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.low.glucose.text"), helpPage.helpTextLowTargetRange())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the lower limit of euglycemia (i.e., target) for this patient. The system uses the Low value in reports to show the range that is below the euglycemic or glucose target range.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.highest.limit.title"), helpPage.helpTitleHighestLimit())
        log.info("Help title displayed - Highest Limit ")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.highest.limit.text"), helpPage.helpTextHighestLimit())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the Highest Limit for this patient. The software uses the Highest Limit in reports to show glucose excursions that are above the Highest Limit. ")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.lowest.limit.title"), helpPage.helpTitleLowestLimit())
        log.info("Help title displayed - Lowest Limit")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.lowest.limit.text"), helpPage.helpTextLowestLimit())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the Lowest Limit for this patient. The software uses the Lowest Limit in reports to show glucose excursions that are below the Lowest Limit.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.glucose.units.title"), helpPage.helpTitleGlucoseUnits())
        log.info("Help title displayed - Glucose Units")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.glucose.units.text"), helpPage.helpTextGlucoseUnits())
        log.info("Help text displayed - Select the units preferred for displaying blood glucose and sensor glucose measurements for this patient. You can select mg/dL (milligrams per deciliter) or mmol/L (millimoles per Liter).")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.time.display.title"), helpPage.helpTitleTimeDisplay())
        log.info("Help title displayed - Time Display")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.time.display.text"), helpPage.helpTextTimeDisplay())
        log.info("Help text displayed - Select the units preferred for time display. You can select either 12 hour or 24 hour. The system uses the Time Display in the Logbook and on reports to show the time readings.")
        then:'Verify the Context sensitive browser contain a close button labeled as “Close window”.'
        helpPage.isCloseHelpWindowClickDisplated()
        then:'Click on “Close Window ” button.'
        helpPage.closeHelpWindowClick()
        log.info("Close the help browser ")
        globalDataUtil.backToTab()
        sleep(1000)
        log.info("Back to main page ")
        then: 'Verify in Analysis Reports Settings section there is context sensitive help.'
        helpPage.isclickOnIconHelpAnalysisDisplayed()
        then: 'Click on analysis reports settings help.'
        helpPage.clickOnIconHelpAnalysis()
        then: 'User switch to Analysis Reports Settings help'
        globalDataUtil.switchToNewTab()
        then: 'Verify that when Help is activated by user it display following text in separate browser window or tab .'
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.analysis.report.settings.title"), helpPage.helpTitleAnalysisReports())
        log.info("Help title displayed - Analysis Reports Settings Help")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.general.text"), helpPage.helpTextAnalysisReports())
        log.info("Help text displayed -NOTE: Changes to these settings will only apply to new patients added to the system. (However, changes to Glucose Units and Time Display will apply to all patients in the system).")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.analysis.report.title"), helpPage.helpTitleAnalysisReportsSettings())
        log.info("Help title displayed - Analysis Reports Settings")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.analysis.report.text"), helpPage.helpTextAnalysisReportsSettingsPatient())
        log.info("Help text displayed - These fields allow you to enter all time periods and target ranges used by the Overlay by Meal Report and Pattern Snapshot Report.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.glucose.target.title"), helpPage.helpTitleAnalysisReportsSettingsGlucoseTarget())
        log.info("Help title displayed - Glucose Target Ranges")
        helpPage.glucoseTargetRangesText()
        log.info("The Glucose Target Ranges specify a Before meal and After meal target range for each of the three meal periods. The overnight periods of Bedtime and Overnight contain a single target range.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.low.value.sub-text1"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow1())
        log.info("Help text displayed - You may enter a Low value for a target range according to the following rules.")
        helpPage.isHelpTextDisplayedSelector(ReadProperties.getProperties().get("settings.help.target.low.limitsub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow2())
        log.info("Help text displayed - If you have selected mg/dL as your Glucose Units, the format for entering your Low value is xxx (e.g. 123). The range can be from (Lowest Limit + 10) to 140.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.selected.mg/dL.7.8.sub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow3())
        log.info("Help text displayed - If you selected mmol/L as your Glucose Units, the format for entering your Low value is xx.x (e.g. 12.3). The range can be from (Lowest Limit + 0.6) to 7.8.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.high.value"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow4())
        log.info("Help text displayed - You may enter a High value for a target range according to the following rules.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.selected.mg/dL.20.sub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow5())
        log.info("Help text displayed - If you have selected mg/dL as your Glucose Units, the format for entering your High value is xxx (e.g. 123). The range can be from 80 to (Highest Limit - 20).")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.selected.mg/dL.1.1.sub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow6())
        log.info("Help text displayed - If you selected mmol/L as your Glucose Units, the format for entering your High value is xx.x (e.g. 12.3). The range can be from 4.4 to (Highest Limit - 1.1).")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.time.reriods.title"), helpPage.helpTitleAnalysisTimePeriods())
        log.info("Help title displayed - Time Periods")
        helpPage.timePeriodsText()
        log.info("Help text displayed - You may set the Time Periods in any way that makes sense to you so long as they remain in chronological order. The duration of each period must be at least 0.5 hours. The end time of a period is the same as the start time of the next period.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.post.mealanalysis.title"), helpPage.helpTitleAnalysisPostMeal())
        log.info("Help title displayed - Post Meal Analysis")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.post.mealanalysis.text"), helpPage.helpTextAnalysisPostMeal())
        log.info("Help text displayed - The Post Meal Analysis window can extend up to 4.0 hours after a meal event. The duration of the analysis window must be at least 0.5 hours. The start time can range from 0.0 to 3.5 and the end time can range from 0.5 to 4.0.")
        then:'Verify the Context sensitive browser contain a close button labeled as “Close window”.'
        helpPage.isCloseHelpWindowClickDisplated()
        then:'Click on “Close Window ” button.'
        helpPage.closeHelpWindowClick()
        log.info("Close the help browser ")
        globalDataUtil.backToTab()
        log.info("Back to main page ")
        sleep(3000)
    }

    def 'Open general report settings help for parient'() {
        given: 'When user successfully logged into carelink application'
        when: 'User clicks on the clinic settings tab'
        helpPage = browsers.at HelpPage
        globalDataUtil = browsers.at SwitchingUtil
        then: 'User is able to view clinic settings and close it'
        sleep(100)
        then:'Click on “Close Clinic Settings”.'
        helpPage.goToHomePageClick()
        log.info("Back to main page ")
        sleep(1000)
        then:'Select a Patient From the patient List.'
        helpPage.selectPatientFromList()
        log.info("Select patient from list")
        then:'Click on “Open Patient” button.'
        helpPage.openPatientFromList()
        log.info("Open patient from list( ")
        then:'Click on “Edit” on down side of “Patient Report Settings”.'
        helpPage.clickEditPatientSettings()
        log.info("Click on button edit")
        helpPage.clickOnIconHelp()
        log.info("Click on button help")
        globalDataUtil.switchToNewTab()
        then: 'General Report Settings Help - User is able to view help settings and close it'
        sleep(1000)
        helpPage.isGeneralSettingsHelpTitleDisplayedSelector(ReadProperties.getProperties().get("settings.help.general.title"), helpPage.helpTitleSettingsHelp())
        log.info("Help title displayed - General Report Settings Help")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.general.patient.text"), helpPage.helpTextSettingsHelp())
        log.info("Help text displayed - NOTE: Changes to these settings will only apply to new patients added to the system. (However, changes to Glucose Units and Time Display will apply to all patients in the system).")
        helpPage.isGeneralSettingsHelpTitleDisplayedSelector(ReadProperties.getProperties().get("settings.help.high.glucose.title"), helpPage.helpTitleHighTargetRange())
        log.info("Help title displayed - High value for Glucose Target Range")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.high.glucose.text"), helpPage.helpTextHighTargetRange())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the upper limit of euglycemia (i.e., target) for this patient. The system uses the High value in reports to show the range that is above the euglycemic or glucose target range.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.low.glucose.title"), helpPage.helpTitleLowTargetRange())
        log.info("Help title displayed - Low value for Glucose Target Range")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.low.glucose.text"), helpPage.helpTextLowTargetRange())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the lower limit of euglycemia (i.e., target) for this patient. The system uses the Low value in reports to show the range that is below the euglycemic or glucose target range.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.highest.limit.title"), helpPage.helpTitleHighestLimit())
        log.info("Help title displayed - Highest Limit ")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.highest.limit.text"), helpPage.helpTextHighestLimit())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the Highest Limit for this patient. The software uses the Highest Limit in reports to show glucose excursions that are above the Highest Limit. ")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.lowest.limit.title"), helpPage.helpTitleLowestLimit())
        log.info("Help title displayed - Lowest Limit")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.lowest.limit.text"), helpPage.helpTextLowestLimit())
        log.info("Help text displayed - Enter the glucose measurement that is considered to be the Lowest Limit for this patient. The software uses the Lowest Limit in reports to show glucose excursions that are below the Lowest Limit.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.glucose.units.title"), helpPage.helpTitleGlucoseUnits())
        log.info("Help title displayed - Glucose Units")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.glucose.units.text"), helpPage.helpTextGlucoseUnits())
        log.info("Help text displayed - Select the units preferred for displaying blood glucose and sensor glucose measurements for this patient. You can select mg/dL (milligrams per deciliter) or mmol/L (millimoles per Liter).")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.time.display.title"), helpPage.helpTitleTimeDisplay())
        log.info("Help title displayed - Time Display")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.time.display.text"), helpPage.helpTextTimeDisplay())
        log.info("Help text displayed - Select the units preferred for time display. You can select either 12 hour or 24 hour. The system uses the Time Display in the Logbook and on reports to show the time readings.")
        then:'Verify the Context sensitive browser contain a close button labeled as “Close window”.'
        helpPage.isCloseHelpWindowClickDisplated()
        then:'Click on “Close Window ” button.'
        helpPage.closeHelpWindowClick()
        globalDataUtil.backToTab()
        log.info("Back to main page ")
        then: 'Verify in Analysis Reports Settings section there is context sensitive help.'
        helpPage.isclickOnIconHelpAnalysisDisplayed()
        then: 'Click on analysis reports settings help.'
        helpPage.clickOnIconHelpAnalysis()
        log.info("Click on icon help analysis ")
        sleep(100)
        then: 'User switch to Analysis Reports Settings help'
        globalDataUtil.switchToNewTab()
/*        screenshot("helpPage", "helpPage_patient_analysis")*/
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.analysis.report.settings.title"), helpPage.helpTitleAnalysisReports())
        log.info("Help title displayed - Analysis Reports Settings Help")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.general.patient.text"), helpPage.helpTextAnalysisReports())
        log.info("Help text displayed - NOTE: These settings are only applied to this patient record for reports generated after these settings have been saved.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.analysis.report.title"), helpPage.helpTitleAnalysisReportsSettings())
        log.info("Help title displayed - Analysis Reports Settings")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.analysis.report.text"), helpPage.helpTextAnalysisReportsSettingsPatient())
        log.info("Help text displayed - These fields allow you to enter all time periods and target ranges used by the Overlay by Meal Report and Pattern Snapshot Report.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.glucose.target.title"), helpPage.helpTitleAnalysisReportsSettingsGlucoseTarget())
        log.info("Help title displayed - Glucose Target Ranges")
        helpPage.glucoseTargetRangesText()
        log.info("The Glucose Target Ranges specify a Before meal and After meal target range for each of the three meal periods. The overnight periods of Evening and Sleeping contain a single target range.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.low.value.sub-text1"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow1())
        log.info("Help text displayed - You may enter a Low value for a target range according to the following rules.")
        helpPage.isHelpTextDisplayedSelector(ReadProperties.getProperties().get("settings.help.target.low.limitsub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow2())
        log.info("Help text displayed - If you have selected mg/dL as your Glucose Units, the format for entering your Low value is xxx (e.g. 123). The range can be from (Lowest Limit + 10) to 140.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.selected.mg/dL.7.8.sub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow3())
        log.info("Help text displayed - If you selected mmol/L as your Glucose Units, the format for entering your Low value is xx.x (e.g. 12.3). The range can be from (Lowest Limit + 0.6) to 7.8.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.high.value"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow4())
        log.info("Help text displayed - You may enter a High value for a target range according to the following rules.")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.selected.mg/dL.20.sub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow5())
        log.info("Help text displayed - If you have selected mg/dL as your Glucose Units, the format for entering your High value is xxx (e.g. 123). The range can be from 80 to (Highest Limit - 20).")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.target.selected.mg/dL.1.1.sub-text"), helpPage.helpTextAnalysisReportsSettingsGlucoseRow6())
        log.info("Help text displayed - If you selected mmol/L as your Glucose Units, the format for entering your High value is xx.x (e.g. 12.3). The range can be from 4.4 to (Highest Limit - 1.1).")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.time.reriods.title"), helpPage.helpTitleAnalysisTimePeriods())
        log.info("Help title displayed - Time Periods")
        helpPage.timePeriodsText()
        log.info("Help text displayed - You may set the Time Periods in any way that makes sense to you so long as they remain in chronological order. The duration of each period must be at least 0.5 hours. The end time of a period is the same as the start time of the next period.")
        helpPage.isHelpTitleDisplayed(ReadProperties.getProperties().get("settings.help.post.mealanalysis.title"), helpPage.helpTitleAnalysisPostMeal())
        log.info("Help title displayed - Post Meal Analysis")
        helpPage.isHelpTextDisplayed(ReadProperties.getProperties().get("settings.help.post.mealanalysis.text"), helpPage.helpTextAnalysisPostMeal())
        log.info("Help text displayed - The Post Meal Analysis window can extend up to 4.0 hours after a meal event. The duration of the analysis window must be at least 0.5 hours. The start time can range from 0.0 to 3.5 and the end time can range from 0.5 to 4.0.")
        then:'Verify the Context sensitive browser contain a close button labeled as “Close window”.'
        helpPage.isCloseHelpWindowClickDisplated()
        then:'Click on “Close Window ” button.'
        helpPage.closeHelpWindowClick()
        sleep(2000)
        log.info("Close the help browser ")
        globalDataUtil.backToTab()
        sleep(2000)
        helpPage.homePageClinicTab()
        log.info("Back to main page ")
        sleep(1000)
        then:'Click on “ Sign out ”.'
        helpPage.logOutButton()
        log.info("Click on button Sign out")
        sleep(3000)
    }
}


