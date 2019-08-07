package com.medtronic.ndt.carelink.pages.patient

import com.medtronic.ndt.carelink.data.ReadProperties
import com.medtronic.ndt.carelink.data.api.ApiPropertyMH
import geb.Page
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

class HomePage extends Page {
    // The URL to add to the baseUrl from the GebConfig when this page is used in browser.to
    // static url = 'main/home.jsf'
    def firstNames = ['Sample M.', 'Alex0', 'LexA_', 'abDula', '1abcefgh', 'Ale0A', 'abcdef', 'ghijk', '“abcde', '“abcdef', 'ghij']
    def lastNames = ['Patient', '0Alex', 'Axel0', 'ABC1', 'A0abcd', 'ghij', 'ghijk', 'abcdef', 'ghijk', 'f', 'abcde']
    def idS = ['---', '1473032335', '951881062', '1575498019', '651683984', '307170922', '779040600', '1266742671', '44539780', '627787300', '2147013332']
    def dateOfBirthS = ['Apr 16, 1989', 'Jan 1, 1980', 'Feb 5, 1999', 'Mar 11, 2000', 'Apr 25, 2001', 'May 1, 2010', 'Jun 5, 2015', 'Jul 11, 2016', 'Aug 25, 1980', 'Sep 1, 1999', 'Oct 5, 2000']
    // static url = 'main/home.jsf'
    static at = { waitFor { userGuide.displayed || newPatient.displayed } }
    static content = {
        newPatient { $("input", id: "list:createBtn") }
        heading { $("h1").text() }
        //User guide link
        userGuide(required: false) { $('a[href$="userguides.html"]') }
        //Training link
        training(required: false) { $('a[href$="ipro2-professional-cgm#sixthvert"]') }
        //Resources link
        resources(required: false) { $('a[href$="resources.html"]') }
        //Order Supplies link
        orderSupplies(required: false) { $('a[href$="supplies.html"]') }
        //Terms of use link
        termsOfUse(required: false) { $('a[href$="termsOfUse.jsf"]') }
        //Privacy statement link
        privacyStatement(required: false) { $('a[href$="privacy.jsf"]') }
        //Contact us link
        contactUs(required: false) { $('a[href$="contact.jsf"]') }
        patientListLabel(required: false) { $('.nav_gray') }
        homeTab(required: false) { $("a", id: "list:home") }
        searchFieldResult(required: false) { $(id: "searchPatientOld") }
        lastNameLabel(required: false) { $(id: "td01") }
        firstNameLabel(required: false) { $(id: "td02") }
        patientIdLabel(required: false) { $(id: "td03") }
        dateOfBirthLabel(required: false) { $(id: "td04") }
        lastNameList(required: false) { $(id: startsWith("lastNameTd")) }
        firstNameList(required: false) { $(id: startsWith("firstNameTd")) }
        patientIdList(required: false) { $(id: startsWith("idTd")) }
        openPatientButton(required: false) { $(name: "list:openPatientBtn") }
        clinicName(required: false) { $('#list > div.wrapper > div > div.header > h1:nth-child(3)') }
        patientList(required: false) { $('div.P_list P_listbot') }
        pleaseWait(required: true, wait: true) { $('.shadowcontent') } //div.shadowcontent
        lastNamePatientText(required: false) { $("#lastNameTd0 > p") }
        firstNamePatientText(required: false) { $("#firstNameTd0 > p") }
        patientIDText(required: false) { $("#idTd0 > p") }
        titleButtonNewPatient(required: false) { $(By.id("list:createBtn")) }
        titleNewPatient(required: false) { $("#box_con > div.nav_gray > h2") }
        headingText(required: false) { $("h1", id: "envision") }
        selectPatient(required: false) { $(id: "lastNameTd0") }
        openPatientBtn(required: false) { $(id: 'list:openPatientBtn') }
        searchField(required: false) { $(id: "searchPatient") }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
        signOutButton(required: false) { $("a", id: "list:logout") }
        clinicId(required: false) { $("h1:nth-child(3) > span") }
        clinicSettingsTab { $(id: 'list:clinicsettings') }
    }

    void clickSignOutButton() {
        waitFor { signOutButton.displayed }
        signOutButton.click()
    }

    void patientListDisplayed() {
        waitFor { patientList.displayed }
    }

    void clinicNameDisplayed() {
        waitFor { clinicName.displayed }
    }

    boolean HeaderIsDisplayed() {
        heading.displayed
    }

    void clickOnUserGuide() {
        userGuide.click()
    }

    void clickOnTraining() {
        training.click()
    }

    void clickOnResources() {
        waitFor { resources.click() }
    }

    boolean resourcesDisplayed() {
        waitFor { resources.displayed }
    }

    boolean resourcesTextDisplayed(String resourcesText) {
        waitFor { resources.text().contains(resourcesText) }
    }

    boolean orderSuppliesDisplayed() {
        waitFor { orderSupplies.displayed }
    }

    boolean orderSuppliesTextDisplayed(String orderSuppliesText) {
        waitFor { orderSupplies.text().contains(orderSuppliesText) }
    }

    void clickOnOrderSupplies() {
        orderSupplies.click()
    }

    void clickOnTermsOfUse() {
        termsOfUse.click()
    }

    boolean termsOfUseDisplayed() {
        waitFor { termsOfUse.displayed }
    }

    boolean termsOfUseTextDisplayed(String termsOfUseText) {
        waitFor { termsOfUse.text().contains(termsOfUseText) }
    }

    boolean privacyStatementTextDisplayed(String privacyStatementText) {
        waitFor { privacyStatement.text().contains(privacyStatementText) }
    }

    void clickOnPrivacyStatement() {
        waitFor { privacyStatement.displayed }
        privacyStatement.click()

    }

    boolean privacyStatementDisplayed() {
        waitFor { privacyStatement.displayed }
    }

    void clickOnContactUs() {
        waitFor { contactUs.click() }
    }

    boolean contactUsDisplayed() {
        waitFor { contactUs.displayed }
    }

    boolean contactUsDisplayed(String contactUsText) {
        waitFor { contactUs.text().contains(contactUsText) }
    }

    boolean userGuideDisplayed() {
        waitFor { userGuide.displayed }
    }

    boolean userGuideTextDisplayed(String userGuideText) {
        waitFor { userGuide.text().contains(userGuideText) }
    }

     void waitForPageLoaded() {
        waitFor { js.('document.readyState') == 'complete' }
    }

    void verifyResourcesLink() {
        browser.withNewWindow({ resources.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("resources.html")
        }
    }

    void verifyUserGuideLink() {
        browser.withNewWindow({ userGuide.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("userguides.html")
        }
    }

    void verifyOrderSuppliesLink() {
        browser.withNewWindow({ orderSupplies.click() }, wait: true) {
            if (browser.getCurrentUrl().contains("about:blank")) {
                refreshPage()
            }
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("supplies.html")
        }
    }

    void verifyPrivacyStatementLink() {
        browser.withNewWindow({ privacyStatement.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("privacy.jsf")
        }
    }

    void verifyTermsOfUseLink() {
        browser.withNewWindow({ termsOfUse.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("termsOfUse.jsf")
        }
    }

    void verifyContactUsLink() {
        browser.withNewWindow({ contactUs.click() }, wait: true) {
            waitFor { $(".mdtlogo").displayed }
            assert browser.getCurrentUrl().contains("contact.jsf")
        }
    }

    void pleaseWaitPopup() {
        if (pleaseWait.displayed) {
            assert pleaseWait.displayed
        } else {
            println('Please Wait Popup appear too fast')
        }
    }

    void verifyTitleButtonNewPatient() {
        assert titleButtonNewPatient.getAttribute("value") == ("New patient")
    }

    void verifyLastNamePatient() {
        waitFor { lastNamePatientText.text() == ("---") }
    }

    void verifyFirstNamePatient() {
        waitFor { firstNamePatientText.text() == ("---") }
    }

    void verifyPatientID() {

        waitFor { patientIDText.text() == ("---") }
    }

    void patientListlastName(String lastName) {
        headingText.text().equals(lastName)
    }

    void patientListFirstName(String firstName) {
        headingText.text().equals(firstName)
    }

    void patientListPatientID(String PatientID) {
        headingText.text().contains(PatientID)
    }

    void patientListDateOfBirth(String DateOfBirth) {
        headingText.text().contains(DateOfBirth)
    }

    void titleCreateNewPatient(String text) {
        titleNewPatient.text().contains(text)
    }

    public String highlightedPatientRow() {
        driver.findElement(By.cssSelector("td#lastNameTd0")).getCssValue("color")
    }

    void sortPatientID() {
        waitFor { $(id: 'td03').displayed }
        $(id: 'td03').click()
    }


    void sortdDateOfBirth() {
        waitFor { $(id: 'td03').displayed }
        $(id: 'td04').click()
    }


    void sortFirstName() {
        waitFor { $(id: 'td02').displayed }
        $(id: 'td02').click()
    }

    void sortLastName() {
        waitFor { $(id: 'td01').displayed }
        $(id: 'td01').click()
    }

    public void verifyLastNameSorted() {
        if ($("td#td01.jp_gridtoptd01").displayed) {
            $("#sort_image_2").displayed
        } else {
            println('last name list is not sorted')
        }
    }

    public void verifyFirstNameSorted() {
        if ($("td#td02.jp_gridtoptd02").displayed) {
            $("#sort_image_1").displayed
        } else {
            println(' first name list is not sorted')
        }
    }

    public void verifyLlistDateOfBirthSorted() {
        if ($("td#td04.jp_gridtoptd04").displayed) {
            $("#sort_image_3").displayed
        } else {
            println('date of birth list is not sorted')
        }
    }

    public void verifyPatientListSorted() {
        if ($("td#td03.jp_gridtoptd03").displayed) {
            $("#sort_image_4").displayed
        } else {
            println('Patient ID list is not sorted')
        }
    }

    void selectPatientFromList() {
        waitFor { selectPatient.displayed }
        selectPatient.click()
    }

    void openContactUsPage() {
        contactUs.click()
    }

    void homeScreenIsAppeared() {
        waitFor { title == ReadProperties.getProperties().get('home.title') }
        waitFor { newPatient.displayed }
        waitFor { patientListLabel.displayed }
        waitFor { homeTab.displayed }
        assert patientListLabel.text() == ReadProperties.getProperties().get('home.patient.tab.patientlist')
        assert homeTab.text() == ReadProperties.getProperties().get('link.home')
        assert newPatient.getAttribute("value") == ReadProperties.getProperties().get('home.patient.new')
    }

    void openCreatePatientScreen() {
        waitFor { !$("#loading").displayed }
        newPatient.click()
        waitFor { !$("#loading").displayed }
        String label = ReadProperties.getProperties().get("patient.new.h1")
        waitFor { title == label }
        assert patientListLabel.text().contains(label)
    }

    void clickOnSearchEntryBox() {
        waitFor { !$("#loading").displayed }
        searchField.click()
    }

    void searchInput(String searchCriteria) {
        waitFor { searchField.displayed }
        searchField.value(searchCriteria)
    }

    void searchInputResult(String inputData) {
        waitFor { searchFieldResult.getAttribute("value") != "" }
        waitFor { searchFieldResult.getAttribute("value") == inputData }
        assert searchFieldResult.getAttribute("value") == inputData
    }

    void checkPatientListData(Integer amountOfRecords, String inputValue) {
        //amount of records
        assert $(id: 'patientList').find('tr').size() >= amountOfRecords
        def records = $(id: 'patientList').find('tr')*.text()
        assert records.size() >= amountOfRecords
        def value = inputValue.replaceAll(' ', ',').split(',')
        def index = 0
        def i = 0
        records.each {
            if (i == value.size()) {
                i = 0
            }
            assert records.getAt(index).toLowerCase().replaceAll('\n', '').contains(value.getAt(i).toLowerCase())
            if (records.size() == 1 && value.size() > 1) {
                assert records.getAt(0).toLowerCase().replaceAll('\n', '').contains(value.getAt(i++).toLowerCase())
            }
            index++
            i++
        }
    }

    def pageComplete() {
        js.('document.readyState') == 'complete'
    }

    void clickOnLastNameLabel() {
        lastNameLabel.click()
    }

    void clickOnPatientIdLabel() {
        patientIdLabel.click()
    }

    void checkSortingLastName() {
        def ui = lastNameList*.text().removeAll { it.contains("abcde") && it.contains("ghijk") }
        def list = lastNames.sort { it.toLowerCase() }
        assert ui == list.removeAll { it.contains("abcde") && it.contains("ghijk") }
    }

    void checkSortingIds() {
        waitFor { patientIdList*.text() == patientIdList*.text().sort(false) }
    }

    void clickOnFirstNameLabel() {
        firstNameLabel.click()
    }

    void checkSortingFirstName() {
        waitFor { firstNameLabel.displayed }
        def ui = $(id: startsWith("firstNameTd"))*.text().removeAll { it.contains("abcde") }
        def list = firstNames.sort { it.toLowerCase() }
        assert ui == list.removeAll { it.contains("abcde") }
    }

    void refreshPage() {
        driver.navigate().refresh()
        waitFor { !$("#loading").displayed }
    }

    void verifyRowHighlighted() {
        waitFor { selectPatient.displayed }
        def highColor = "rgb(236, 236, 236)"

        $("#lastNameTd0").find(text: "Patient").click()
        assert $("#lastNameTd0").getAttribute("style").contains(highColor)
        assert $("#lastNameTd0").find("p").text() == "Patient"

        $("#firstNameTd0").find(text: "Sample M.").click()
        assert $("#firstNameTd0").getAttribute("style").contains(highColor)
        assert $("#firstNameTd0").find("p").text() == "Sample M."

        $("#idTd0").find(text: "---").click()
        assert $("#idTd0").getAttribute("style").contains(highColor)
        assert $("#idTd0").find("p").text() == "---"
    }

    void openPatient() {
        openPatientButton.click()
    }

    void verifyCorrectPatientWasOpened(String name1, String name2) {
        waitFor { title == "Patient Record Page" }
        waitFor { pageComplete() }
        def name = $(id: "patientName").text()
        assert name.contains(name1) && name.contains(name2)
    }

    void navigateBack() {
        driver.navigate().back()
    }

    void openPatientByDoubleClick() {
        waitFor { !$("#loading").displayed }
        waitFor { $(id: 'lastNameTd0').displayed }
        sleep(5000)
        WebElement patientInfoRecord = driver.findElement(By.id("lastNameTd0"))
        ((JavascriptExecutor) driver).executeScript("var evt = document.createEvent('MouseEvents');" +
                "evt.initMouseEvent('dblclick',true, true, window, 0, 0, 0, 0, 0, false, false, false, false, 0,null);" +
                "arguments[0].dispatchEvent(evt);", patientInfoRecord)
        waitFor { $(id: 'patientName').displayed ||$(id:'patient_details:firstName').displayed}
    }

    void doudbleClickerGeb() {
        browser.interact { doubleClick($(id: 'lastNameTd0')) }
    }

    void verifyOpenPatientBtn() {
        assert openPatientBtn.displayed
        assert openPatientBtn.value() == "Open patient"
    }

    void verifyOpenPatientBtnIsInactive() {
        assert openPatientBtn.attr('disable') == ""
    }

    void verifyNewPatientBtn() {
        assert newPatient.displayed
        assert newPatient.value() == "New patient"
    }

    void clickNewPatientBtn() {
        sleep(4000)
        waitFor { newPatient.displayed }
        newPatient.click()
    }

    void clickOpenPatientBtn() {
        openPatientBtn.click()
    }

    void verifySamplePatientDemo() {
        waitFor { firstNamePatientText.displayed || lastNamePatientText.displayed }
        assert firstNamePatientText.text() == ("Sample M.")
        assert lastNamePatientText.text() == ("Patient")
    }

    void verifyLogo() {
        waitFor { medtronicLogo.displayed }
        assert medtronicLogo.css('float') == 'left'
        if (envisionLogo.displayed) {
            assert envisionLogo.css('float') == 'right'
        } else {
            assert $("div.header > img.iprologo").displayed
            assert $("div.header > img.iprologo").css('float') == 'right'
        }
    }

    def getClinicIdFromHomePage() {
        waitFor { userGuide.displayed }
        waitFor { clinicId.displayed }
        String getTextId = clinicId.text().replaceAll('.*: |\\)', '')
        ApiPropertyMH.clinicIdUi = getTextId
        println("Clinic Id: " + getTextId)
        return getTextId
    }

    void verifyColumnForSparkFalse() {
        assert !$("#td05").displayed
        assert !$("#td06").displayed
    }

    void selectBoldPatientFromList(String style) {
        waitFor { openPatientBtn.displayed }
        waitFor { selectPatient.displayed }
        selectPatient.click()
        assert $('#patientList > tr').getAttribute('style').contains(style)
    }


    void addressOrderSuppliesLinkDisplayed(countryURL) {
        if ($('.btm_info > ul:nth-child(1) > li:nth-child(3) > a').getAttribute("outerHTML").contains(countryURL)) {
            assert $('.btm_info > ul:nth-child(1) > li:nth-child(3) > a').getAttribute("outerHTML").contains(countryURL)
        } else
            assert $('li.last:nth-child(4) > a:nth-child(1)').getAttribute("outerHTML").contains(countryURL)
    }

    void addressResourcesLinkDisplayed(countryURL) {
        if ($('.btm_info > ul:nth-child(1) > li:nth-child(1) > a').getAttribute("outerHTML").contains(countryURL)) {
            assert $('.btm_info > ul:nth-child(1) > li:nth-child(1) > a').getAttribute("outerHTML").contains(countryURL)
        } else {
            assert $('.btm_info > ul:nth-child(1) > li:nth-child(3) > a:nth-child(1)').getAttribute("outerHTML").contains(countryURL)
        }

    }

    void addressUserGuideLinkdisplayed(countryURL) {
        assert $('.btm_info > ul:nth-child(1) > li:nth-child(1) > a:nth-child(1)').getAttribute("outerHTML").contains(countryURL)
    }

    void mouseOverUserGuide() {
        Actions action = new Actions(driver)
        action.moveToElement(driver.findElement(By.cssSelector('a[href$="userguides.html"]'))).build().perform()
    }

    void mouseOverResources() {
        Actions action = new Actions(driver)
        action.moveToElement(driver.findElement(By.cssSelector('a[href$="resources.html"]'))).build().perform()
    }

    void mouseOverOrderSupply() {
        Actions action = new Actions(driver)
        action.moveToElement(driver.findElement(By.cssSelector('a[href$="supplies.html"]'))).build().perform()
    }

    void listLoaded() {
        waitFor { openPatientButton.displayed }
        waitFor { !pleaseWait.displayed }
        sleep(2000)
    }

    void refreshViaSettings() {
        $(id: 'list:clinicsettings').click()
        waitFor { $(id: 'reportpreference:savePrefs').displayed }
//        $('.box_mid_nav_right').click()
        waitFor {$('#tab>ul>li.tab_home>a').displayed}
        $('#tab>ul>li.tab_home>a').click()
    }
    void selectSecondPatientFromList() {
        $(id: "lastNameTd1").click()
    }

    def getClinicNameFromHomePage() {
        waitFor { userGuide.displayed }
        waitFor { clinicId.displayed }
        //no ID`s
        ApiPropertyMH.clinicName = $('.header > h1').getAt(1).text().replaceAll('.\\(ID.*','')
        return ApiPropertyMH.clinicName
    }
}
