package com.medtronic.ndt.carelink.pages

import geb.Page
import org.openqa.selenium.By

class HelpPage extends Page{

    static at = { header.displayed ||  logo.displayed }
    static content = {
        logo(required:false){$  id: "envision"}
        clinicSettingsPage (required:false){$ "a", id: "list:clinicsettings"}
        header(required: false, wait: 5) { $(id: "j_id_id14") }
        submitButton(required: false, wait: 5) { $("input.sv_btn") }
        bannerImage(required: false , wait: 5 ){ $("div", id: "banner") }
        signinButton(required: false, wait: 5) { $("input", id: "signinbtn") }
        // Google chrome
        passConfigurationPage(required: false, wait: 5) { $("input", id: "test:study") }
        generalSettingsHelp (required:false, wait: 5) {$( "p", div:"rp_help")}
        clickOnHalp (required:false, wait: 5) { $( "a#helpUrl")}
        toClinicSettings(required:false, wait: 5) {$( "span", id:"list:clinicsettings")}
        closeHelp (required:false, wait: 5) {$( "a.close")}
        goToHomePage (required:false, wait: 5) {$( id:"j_id_id14:home")}
        selectPatient (required:false, wait: 5) {$( id:"studyStatusTd0")}
        openPatient(required:false, wait: 5) {$( id:"list:openPatientBtn")}
        logOut  (required:false,wait:5) {$( id: "list:logout")}
        backHomePagePatient  (required:false,wait:5) {$( "a",id:"j_id_id17:home")}
        editPatientSettings (required:false,wait:5) {$( id: "bgunit_mmoll")}


        glucoseUnitsMmol(required:false){$ "input", id: "bgunit_mmoll"}
        glucoseUnitsMmolPopup(required:false){$ "input", id: "j_id_id48:convert"}
        timeDisplay24(required:false){$ "input", id: "timedisplay_hr24"}
        reportSettings(required:false){$ "input", id: "reportpreference:savePrefs"}
        glucoseUnitsMg(required:false){$ "input", id: "bgunit_mgdl"}
        glucoseTargetLow(required:false){$ "input", id: "reportpreference:glucoseTargetLow"}
        glucoseTargetHigh(required:false){$ "input", id: "reportpreference:glucoseTargetHigh"}
    }

    boolean openPatientFromList(){
        openPatient.click()
    }
    boolean selectPatientFromList(){
        selectPatient.click()
    }
    boolean goToHomePageClick() {
        goToHomePage.click()
    }
    boolean closeHelpWindowClick() {
        closeHelp.click()
    }
    boolean isCloseHelpWindowClickDisplated() {
        closeHelp.isDisplayed()
    }
    boolean backToHomePagePatient() {
        backHomePagePatient.click()
    }
    void clickEditPatientSettings() {
        driver.findElement(By.cssSelector("p.change_settings > a")).click()
    }
    boolean isClickOnIconHelpDisplayed() {
        $(By.cssSelector("#helpUrl > img")).isDisplayed()
    }
    private void clickOnIconHelp() {
        driver.findElement(By.cssSelector("img[alt=\"Help\"]")).click()
    }
    private void clickOnIconHelpAnalysis() {
        driver.findElement(By.cssSelector("#mealsection > tr > td > a > img[alt=\"Help\"]")).click()
    }
    private void isclickOnIconHelpAnalysisDisplayed() {
        driver.findElement(By.cssSelector("#mealsection > tr > td > a > img[alt=\"Help\"]")).isDisplayed()
    }
    boolean logOutButton() {
        logOut.click()
    }

    private boolean timePeriodsText() {
        return $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Post Meal Analysis'])[1]/preceding::p[1]"))*.text() == ["You may set the Time Periods in any way that makes sense to you so long as they remain in chronological order. The duration of each period must be at least 0.5 hours. The end time of a period need not be the same as the start time of the next period, so gaps between periods may exist. The one exception to this rule is that the end of Evening must be the same as the start of Sleeping."]
    }

    private boolean glucoseTargetRangesText() {
        return $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Glucose Target Ranges'])[1]/following::p[1]"))*.text() == ["The Glucose Target Ranges specify a Before meal and After meal target range for each of the three meal periods. The overnight periods of Evening and Sleeping contain a single target range."]
    }
    boolean logOutButtonDisplayed() {
        logOut.displayed()
    }
    boolean isCloseHelpButtondisplayed() {
        closeHelp.displayed()
    }
    /*---------General report settings help title---------------*/
    public String helpTitleSettingsHelp() {
        return  "div.section_title2"
    }
    public String helpTitleHighTargetRange() {
        return  "div.section_title"
    }
    public String helpTitleLowTargetRange() {
        return  "//div[3]"
    }
    public String helpTitleHighestLimit() {
        return  "//div[4]"
    }
    public String helpTitleLowestLimit() {
        return  "//div[5]"
    }
    public String helpTitleGlucoseUnits() {
        return  "//div[6]"
    }
    public String helpTitleTimeDisplay() {
        return  "//div[7]"
    }
    /*---------General patient settings help text---------------*/
    public String helpTextSettingsHelp() {
        return  "//p[3]"
    }
    public String helpTextHighTargetRange() {
        return  "//p[4]"
    }
    public String helpTextLowTargetRange() {
        return  "//p[5]"
    }
    public String helpTextHighestLimit() {
        return  "//p[6]"
    }
    public String helpTextLowestLimit() {
        return  "//p[7]"
    }
    public String helpTextGlucoseUnits() {
        return  "//p[8]"
    }
    public String helpTextTimeDisplay() {
        return  "//p[9]"
    }
    /*---------Analysis reports settings help title---------------*/
    public String helpTitleAnalysisReports() {
        return  "//div[9]"
    }
    public String helpTitleAnalysisReportsSettings() {
        return  "//div[10]"
    }
    public String helpTitleAnalysisTimePeriods() {
        return  "//div[12]"
    }
    public String helpTitleAnalysisPostMeal() {
        return  "//div[13]"
    }
    public String helpTitleAnalysisReportsSettingsGlucoseTarget() {
        return  "//div[11]"
    }
    /*---------Analysis patient settings help text---------------*/
    public String helpTextAnalysisReports() {
        return  "//p[3]"
    }
    public String helpTextAnalysisSettingsGlucoseTarget() {
        return  "//p[12]"
    }
    public String helpTextAnalysisTimePeriods() {
        return  "//p[17]"
    }
    public String helpTextAnalysisPostMeal() {
        return  "//p[18]"
    }
    public String helpTextAnalysisReportsSettingsPatient() {
        return  "//p[11]"
    }
    public String helpTextAnalysisReportsSettingsGlucoseRow1() {
        return  "//p[13]"
    }
    public String helpTextAnalysisReportsSettingsGlucoseRow2() {
        return  "li"
    }
    public String helpTextAnalysisReportsSettingsGlucoseRow3() {
        return  "//li[2]"
    }
    public String helpTextAnalysisReportsSettingsGlucoseRow4() {
        return  "//p[15]"
    }
    public String helpTextAnalysisReportsSettingsGlucoseRow5() {
        return  "//ul[2]/li"
    }
    public String helpTextAnalysisReportsSettingsGlucoseRow6() {
        return  "//ul[2]/li[2]"
    }
    /*Text waiting function*/
    public void isHelpTitleDisplayed(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (helpTitleText.equals(driver.findElement(By.xpath(helpTitleElements)).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    public void isGeneralSettingsHelpTitleDisplayedSelector(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (helpTitleText.equals(driver.findElement(By.cssSelector(helpTitleElements)).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    public void isHelpTextDisplayed(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (helpTitleText.equals(driver.findElement(By.xpath(helpTitleElements)).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    public void isHelpTextDisplayedSelector(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (helpTitleText.equals(driver.findElement(By.cssSelector(helpTitleElements)).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    public void glucoseTargetRange() {
        String valueAttribute = driver.findElement(By.id(lowValue())).getAttribute("value") ;
        for (int second = 0 ; ; second++) {
            if (second >= 60) fail("timeout") ;
            try {if (valueAttribute.equals(driver.findElement(By.id(lowValue())).getAttribute("value"))) break ;
            } catch (Exception e) {
            }
            Thread.sleep(1000) ;
        }
    }
    public void backToMainPage() {
        driver.findElement(By.xpath("//a[@id='j_id_id14:home']/span")).click()
    }
    public String highValue() {
        return "reportpreference:glucoseTargetHigh"
    }
    public String lowValue() {
        return "reportpreference:glucoseTargetHigh"
    }
    public String glucoseTargetXtremeHigh() {
        return "reportpreference:glucoseTargetXtremeHigh"
    }
    public String glucoseTargetXtremeLow() {
        return "reportpreference:glucoseTargetXtremeLow"
    }
    public String beforeBreakfastGlucoseTargetLow() {
        return "reportpreference:beforeBreakfastGlucoseTargetLow"
    }
    public String beforeBreakfastGlucoseTargetHigh() {
        return "reportpreference:beforeBreakfastGlucoseTargetHigh"
    }
    public String eveningGlucoseTargetLow() {
        return "reportpreference:eveningGlucoseTargetLow"
    }
    public String eveningGlucoseTargetHigh() {
        return "reportpreference:eveningGlucoseTargetHigh"
    }
    public String sleepingGlucoseTargetLow() {
        return "reportpreference:sleepingGlucoseTargetLow"
    }
    public String sleepingGlucoseTargetHigh() {
        return "reportpreference:sleepingGlucoseTargetHigh"
    }
    public String bgunit_mgdl() {
        return "bgunit_mgdl"
    }
    public String timedisplay_hr12() {
        return "timedisplay_hr12"
    }
    public void isTextGlucoseRangesDisplayedCssSelector(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (helpTitleText.equals(driver.findElement(By.cssSelector(helpTitleElements)).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    public void isTextGlucoseRangesDisplayedXpath(String helpTitleText, String helpTitleElements) throws InterruptedException {
        for (int second = 0;; second++) {
            if (second >= 60) fail("timeout");
            try { if (helpTitleText.equals(driver.findElement(By.xpath(helpTitleElements)).getText())) break; } catch (Exception e) {}
            Thread.sleep(1000);
        }
    }
    public String breakfasrCss() {
        return "option[value=\"6:00 AM\"]"
    }
    public String breakfasrToXpath() {
        return "(//option[@value='11:00 AM'])[2]"
    }
    public String breakfastMealTo() {
        return "(//option[@value='6'])[2]"
    }
    public String lunchTimeFrom() {
        return "(//option[@value='11:00 AM'])[3]"
    }
    public String lunchTimeTo() {
        return "(//option[@value='4:00 PM'])[4]"
    }
    public String LunchPostAnalysisTo() {
        return "(//option[@value='2'])[3]"
    }
    public String LunchPostAnalysisFrom() {
        return "(//option[@value='6'])[4]"
    }
    public String dinnerTimeFrom() {
        return "(//option[@value='4:00 PM'])[5]"
    }
    public String dinnerTimeTo() {
        return "(//option[@value='8:00 PM'])[6]"
    }
    public String dinnerPostAnalysisTo() {
        return "(//option[@value='2'])[5]"
    }
    public String dinnerPostAnalysisFrom() {
        return "(//option[@value='6'])[6]"
    }
    public String eveningTimePeriodFrom() {
        return "(//option[@value='12:00 AM'])[9]"
    }
    public String eveningTimePeriodTo() {
        return "(//option[@value='4:00 AM'])[10]"
    }
    public String sleepingTimePeriodTrom() {
        return "(//option[@value='4:00 AM'])[11]"
    }
    public String sleepingTimePeriodTo() {
        return "(//option[@value='6:00 AM'])[12]"
    }
    boolean selectGlucoseUnitsMmol() {
        glucoseUnitsMmol.click()
    }
    boolean selectGlucoseUnitsMg() {
        glucoseUnitsMg.click()
    }
    boolean glucoseUnitsMmolUprrove() {
        glucoseUnitsMmolPopup.click()
    }
    boolean selectTimeDisplay24() {
        timeDisplay24.click()
    }
    boolean saveReportSettings() {
        reportSettings.click()
    }
    public String hour24BreakfasrCss() {
        return "option[value=\"6:00\"]"
    }
    public String hour24BreakfasrToXpath() {
        return "(//option[@value='11:00'])[2]"
    }
    public String hour24LunchTimeFrom() {
        return "(//option[@value='11:00'])[3]"
    }
    public String hour24LunchTimeTo() {
        return "(//option[@value='16:00'])[4]"
    }
    public String hour24DinnerTimeFrom() {
        return "(//option[@value='16:00'])[5]"
    }
    public String hour24DinnerTimeTo() {
        return "(//option[@value='20:00'])[6]"
    }
    public String hour24EveningTimePeriodFrom() {
        return "(//option[@value='0:00'])[9]"
    }
    public String hour24EveningTimePeriodTo() {
        return "(//option[@value='4:00'])[10]"
    }
    public String hour24SleepingTimePeriodTrom() {
        return "(//option[@value='4:00'])[11]"
    }
    public String hour24SleepingTimePeriodTo() {
        return "(//option[@value='6:00'])[12]"
    }
    public String errorGlucoseHigh() {
        return "ul.error > li"
    }
    public String errorGlucoseHighest() {
        return "//div[@id='box_con_left2']/table/tbody/tr[5]/td/ul/li[2]"
    }
    public String errorGlucoseLow() {

        return "//div[@id='box_con_left2']/table/tbody/tr[5]/td/ul/li[3]"
    }

    void enterGlucoseTargetLow(String low) {
        driver.findElement(By.id("reportpreference:glucoseTargetLow")).clear() ;
        glucoseTargetLow =low
    }
    void enterGlucoseTargetHigh(String High) {
        driver.findElement(By.id("reportpreference:glucoseTargetHigh")).clear() ;
        glucoseTargetHigh =High
    }
    void yesMmolAprrove() {
        driver.findElement(By.id("j_id_id28:yes")).click()
    }
    void mmollPopupApprove() {
        driver.findElement(By.id("j_id_id34:convert")).click()
    }
    void homePageClinicTab() {
        $(By.id("j_id_id16:home")).click()
    }
    void bgunit_mmollApprove() {
        driver.findElement(By.id("bgunit_mmoll")).click()
    }
    public void verifyMmolHomePage() {
        for (int second = 0 ; ; second++) {
            if (second >= 60) fail("timeout") ;
            try {
                if ("7.8  mmol/L – 11.1  mmol/L".equals(driver.findElement(By.cssSelector("p")).getText())) break ;
            } catch (Exception e) {
            }
            Thread.sleep(1000) ;
        }
    }
    public void verifyMmolHomePageText() {
        for (int second = 0 ; ; second++) {
            if (second >= 60) fail("timeout") ;
            try {
                if ("7.8  mmol/L – 10.0  mmol/L".equals(driver.findElement(By.cssSelector("p")).getText())) break ;
            } catch (Exception e) {
            }
            Thread.sleep(1000) ;
        }
    }

    public void verifyMgHomePage() {
        for (int second = 0 ; ; second++) {
            if (second >= 60) fail("timeout") ;
            try {
                if ("140  mg/dL – 200  mg/dL".equals(driver.findElement(By.cssSelector("p")).getText())) break ;
            } catch (Exception e) {
            }
            Thread.sleep(1000) ;
        }
    }
}