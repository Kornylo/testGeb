package com.medtronic.ndt.carelink.pages.patient

import com.medtronic.ndt.carelink.data.enums.DiabetesType
import geb.Page
import org.openqa.selenium.By
import com.medtronic.ndt.carelink.data.enums.TherapyType

class CreatePatientPage extends Page {
    private static List<String> names = ['Akila', 'Anu', 'Jamie', 'Paul', 'Sean', 'Katie', 'Sri', 'Shelly']
    private static Calendar c = Calendar.getInstance()
//    private static birthday = LocalDate.of(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH))
    static at = {
        saveBtn.displayed || editPatient.displayed || exitFromPatient.displayed
    }
    static content = {
        saveBtn(required: false) { $ "input", id: "patient_details:saveBtn" }
        cancelBtn(required: false) { $ 'a', id: 'patient_details:cancelBtn' }
        cancelDialogYes(required: false) { $ 'input', id: 'j_id_id93:yes' }
        cancelDialogNo(required: false) { $ 'input', id: 'j_id_id93:no' }
        cancelDialogText(required: false) { $(By.xpath('//*[@id=\'ui-dialog-title-cancel_dialog\']')) }
        firstName(required: false) { $ "input", id: "patient_details:firstName" }
        firstNameError(required: false) { $(By.xpath('//label[@for=\'patient_details:firstName\']/span[2]')) }
        lastName(required: false) { $ "input", id: "patient_details:lastName" }
        lastNameError(required: false) { $(By.xpath('//label[@for=\'patient_details:lastName\']/span[2]')) }
        id { $ "input", id: "patient_details:patientId" }
        idError(required: false) { $(By.xpath('//label[@for=\'patient_details:patientId\']/span[2]')) }
        email { $ "input", id: "patient_details:email" }
        emailError(required: false) { $(By.xpath('//label[@for=\'patient_details:email\']/span[2]')) }
        physicianNameLabel(required: false) { $ "input", id: "patient_details:physician" }
        physicianNameError { $(By.xpath('//label[@for=\'patient_details:physician\']/span[2]')) }
        monthDropdown(required: false) { $ "select", id: "patient_details:month" }
        dayDropdown(required: false) { $ "select", id: "patient_details:day" }
        yearDropdown(required: false) { $ "select", id: "patient_details:year" }
        diabetesType(required: false) { $(By.xpath('//input[contains(@name,\'patientDiabetesType\')]')) }
        patientName(required: false) { $("#patientName") }
        titleNewPatient(required: false) { $("#box_con > div.nav_gray > h2") }
        editPatient(required: false) { $(id:'study:details') }
        exitFromPatient(required: false) { $(id: 'study:close') }
        patientTitle(required: false) { $("#box_con > h3") }
        labelSave(required: false) { $("input", id: "patient_details:saveBtn") }
        labelCancel(required: false) { $("a", id: "patient_details:cancelBtn") }
        patientConfigWindow(required: false) { $(id: 'cancel_dialog') }
        patientConfigWindowTitleText(required: false) { $(By.xpath("//*[text()='Cancel Confirmation']")) }
        patientConfigWindowText(required: false) {$('div.confirm_dialog_msg_div:nth-child(2) > ul:nth-child(1) > li:nth-child(1)')}
        configWindowButtonYesValue(required: false) { $("input[Value='Yes']") }
        configWindowButtonNoValue(required: false) { $("input[Value='No']") }
        changeSettingsPatient(required: false) { $("p.change_settings > a") }
        homeTab(required: false, wait: 5) { $("a", id: "study:home") }
        newPatient { $("input", id: "list:createBtn") }
        createNewPatientLabel(required: false, wait: 15) { $(".nav_gray > h2:nth-child(1)") }
        physicianNameEnter { $("input", id: "patient_details:physician") }
    }

    void settingsPatient() {
        waitFor { changeSettingsPatient.displayed }
        changeSettingsPatient.click()
    }

    void physicianEnter(String physicianEnter) {
        physicianNameEnter = (physicianEnter)
    }

    void enterPatientEmail(String fillEmail) {
        email = (fillEmail)
    }

    void patientCancelClick(String CancelText) {
        labelCancel.click()
    }

    void patientLabelCancel(String CancelText) {
        assert labelCancel.text == (CancelText)
    }

    void patientSaveClick() {
        labelSave.click()
    }

    void patientSave() {
        sleep(3000)
        labelSave.isFocused()
    }

    void patientLabelSave(String SaveText) {
        assert labelSave.text == (SaveText)
    }

    void editPatientInformation() {
        waitFor { editPatient.displayed }
    }

    void editPatientInformationLabel(String LabelText) {
        editPatient.text == (LabelText)
    }

    void patientInformationTitle(String patienttitle) {
        assert patientTitle.text == (patienttitle)
    }

    void editPatientInformationClick() {
        waitFor { editPatient.displayed }
        editPatient.click()
    }

    void createPatient() {
        String random = Calendar.getInstance().format('MMddHHmmss').toString()
        String firstname = names[(Math.random() * names.size()).toInteger()]
        String lastname = names[(Math.random() * names.size()).toInteger()]
        firstName = "${firstname} ${random}"
        println("First Name: " + firstname)
        lastName = "${lastname} ${random}"
        println("Last Name: " + lastname)
        id = "${random}"
        monthDropdown.click()
        monthDropdown.value('March')
        dayDropdown.click()
        dayDropdown.value('20')
        yearDropdown.click()
        yearDropdown.value('1978')
        diabetesType = "${DiabetesType.values()[(Math.random() * DiabetesType.values().size()).toInteger()]}"
        int numberOfTherapies = (Math.random() * TherapyType.values().size()).toInteger()
        // We can't have 0 treatments for the patient, so if the random value is 0 reassign it to 1
        numberOfTherapies = numberOfTherapies == 0 ? 1 : numberOfTherapies
        // Use a Set here instead of List to avoid duplicate TherapyTypes in the list
        Set<TherapyType> therapies = []
        numberOfTherapies.times {
            therapies << TherapyType.values()[(Math.random() * TherapyType.values().size()).toInteger()]
        }
    }

    void enterPatientData(String firstNameV, String lastNameV, String month, String day, String year) {
        String random = '12345' + new Random().nextInt(100) + Calendar.getInstance().format('MMMddHHmmss')
        firstName.value(firstNameV)
        lastName.value(lastNameV)
        id.value(random)
        monthDropdown.click()
        monthDropdown.value(month)
        dayDropdown.click()
        dayDropdown.value(day)
        yearDropdown.click()
        yearDropdown.value(year)
    }

    void enterPatientFirstName(String month, String day, String year) {
        int random = (Math.random() * Integer.MAX_VALUE).toInteger()
        String username = names[(Math.random() * names.size()).toInteger()]
        //userName = ${username}${random}.value()
        firstName.value(username + random)
        lastName.value(username + random)
        monthDropdown.click()
        monthDropdown.value(month)
        dayDropdown.click()
        dayDropdown.value(day)
        yearDropdown.click()
        yearDropdown.value(year)
    }

    void enterPatientID(String month, String day, String year) {
        waitFor { id.displayed }
        int random = (Math.random() * Integer.MAX_VALUE).toInteger()
        id = "${random}"
        monthDropdown.click()
        monthDropdown.value(month)
        dayDropdown.click()
        dayDropdown.value(day)
        yearDropdown.click()
        yearDropdown.value(year)
    }
    /*   String getPatientID() {
           String id = id.getAttribute("value")
           return id
       }

       String getPatientFirstName() {
           String userNameVal = firstName.getAttribute("value")
           return userNameVal
       }
       String getPatientLastName() {
           String userLastName= lastName.getAttribute("value")
           return userLastName
       }*/

    void selectDiabetesType() {
        $(By.xpath("(//input[@name='patientDiabetesType'])[2]")).click()
    }

    void selectTherapyType() {
        $(By.cssSelector("input[name=\"patient_details:j_id_id78pc5\"]")).click()
    }

    boolean assertPhysicianNameError(String msgPart) {
        physicianNameError.text().contains(msgPart)
    }

    boolean assertPatientEmailError(String msgPart) {
        emailError.text().contains(msgPart)
    }

    boolean assertPatientIdError(String msgPart) {
        idError.text().contains(msgPart)
    }

    boolean assertFirstNameError(String msgPart) {
        firstNameError.text().contains(msgPart)
    }

    boolean assertLastNameError(String msgPart) {
        lastNameError.text().contains(msgPart)
    }

    void selectTherapyTypeInsulinPump() {
        $("#patientDiabetesTherapyPanel > table > tbody > tr:nth-child(5) > td > table > tbody > tr:nth-child(9) > td:nth-child(1) > input").click()
    }

    void physicianName() {
        List<String> physicianNameInvalid = ["<", ">", "&", "=", " "]
        String physicianName = "ddd"
        for (String invalid : physicianNameInvalid) {
            sleep(3000)
            $(By.id("patient_details:physician")).value(physicianName + invalid)
            String elementval = $(By.id("patient_details:physician")).getAttribute("value")
            if (elementval.length() <= 80) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("patient_details:saveBtn")).click()
            assertPhysicianNameError('Input cannot contain <, >, &, or =')
        }
    }

    void patientEmail() {
        List<String> patientEmailInvalid = ["<", ">", "&", "=", " "]
        String patientEmail = "ddd"
        for (String invalid : patientEmailInvalid) {
            sleep(3000)
            $(By.id("patient_details:email")).value(patientEmail + invalid + "gmail.com")
            String elementval = $(By.id("patient_details:email")).getAttribute("value")
            if (elementval.length() <= 80) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("patient_details:saveBtn")).click()
            assertPatientEmailError('Invalid email id')
        }
    }

    void patientID() {
        List<String> PatientIDInvalid = ["<", ">", "&", "=", " "]
        String patientID = "ddd"
        for (String invalid : PatientIDInvalid) {
            sleep(3000)
            $(By.id("patient_details:patientId")).value(patientID + invalid)
            String elementval = $(By.id("patient_details:patientId")).getAttribute("value")
            if (elementval.length() <= 20) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("patient_details:saveBtn")).click()
            assertPatientIdError('Input cannot contain <, >, &, or =')
        }
    }

    void patientFirsName() {
        List<String> firstName = ["<", ">", "&", "=", " "]
        String patientFirsName = "ddd"
        for (String invalid : firstName) {
            sleep(3000)
            $(By.id("patient_details:firstName")).value(patientFirsName + invalid)
            String elementval = $(By.id("patient_details:saveBtn")).getAttribute("value")
            if (elementval.length() <= 40) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("patient_details:saveBtn")).click()
            assertFirstNameError('Input cannot contain <, >, &, or =')
        }
    }

    void patientLastName() {
        List<String> patientLastNameInvalid = ["<", ">", "&", "=", " "]
        String patientLastName = "ddd"
        for (String invalid : patientLastNameInvalid) {
            sleep(3000)
            $(By.id("patient_details:lastName")).value(patientLastName + invalid)
            String elementval = $(By.id("patient_details:lastName")).getAttribute("value")
            if (elementval.length() <= 40) {
                println("ok")
            } else {
                println("error")
            }
            $(By.id("patient_details:saveBtn")).click()
            assertLastNameError('Input cannot contain <, >, &, or =')
        }
    }

    void clickSaveBtn() {
        waitFor { saveBtn.displayed }
        saveBtn.click()
    }

    void clickCancelBtn() {
        cancelBtn.click()
    }

    void clickCancelDialogYes() {
        System.out.println(cancelDialogText.text())
        cancelDialogYes.click()
    }

    void clickCancelDialogNo() {
        cancelDialogNo.click()
    }
/*    void verifyNewlyCreatedPatient(String id) {
        assert patientName.text()==(id)
    }*/

    void valueNewPatient(String LastName, String FirstName) {
        waitFor { patientName.displayed }
        assert patientName.text().contains(LastName) && patientName.text().contains(FirstName)
    }

    void titleCreateNewPatient(String text) {
        waitFor { titleNewPatient.displayed }
        assert titleNewPatient.text().contains(text)
    }

    void patientNameDisplayed() {
        waitFor { patientName.displayed }
    }

    void verifyNewlyCreatedPatient(String id) {
        waitFor { patientName.displayed }
        assert patientName.text() == (id)
    }

    String getPatientID() {
        id.getAttribute("value")
    }

    String getPatientFirstName() {
        firstName.getAttribute("value")

    }

    String getPatientLastName() {
        lastName.getAttribute("value")
    }

    void valueNewPatient(lastNam, firstName, id) {
        waitFor { patientName.displayed }
        if (patientName.text() == (lastNam + ", " + firstName + "     " + id)) {
            assert patientName.text() == (lastNam + ", " + firstName + "     " + id)
        } else {
            assert patientName.text() == (firstName + ", " + lastNam + "     " + id)
        }

    }


    public boolean сonfigWindowButtonNo() {
        configWindowButtonNoValue.getAttribute("value") == ("No")
    }

    public boolean configWindowButtonNoClick() {
        configWindowButtonNoValue.click()
    }

    public boolean configWindowButtonYes() {
        configWindowButtonYesValue.getAttribute("value") == ("Yes")
    }

    public boolean configWindowButtonYesClick() {
        configWindowButtonYesValue.click()
    }

    public boolean patientConfigWindowTextDisplayed(String сonfigWindowText) {
        patientConfigWindowText.text() == сonfigWindowText
    }

    boolean patientConfigWindowTitle(String configWindowTitle) {
        patientConfigWindowTitleText.text() == configWindowTitle
    }

    boolean patientConfigWindowDisplayed() {
        patientConfigWindow.displayed
    }

    void verifyConfirmationMessage() {
        int count = driver.findElements(By.cssSelector("span.ui-dialog-title")).size()
        if (count > 0) {
            println("The element no exists!")
        }
    }

    void firstNameValue(String firstNameValue) {
        firstName.value(firstNameValue)
    }

    void idValue(String idValue) {
        id.value(idValue)
    }

    void lastNameValue(String lastNameValue) {
        waitFor { lastName.displayed }
        lastName.value(lastNameValue)
    }

    void labelCancelText(String value) {
        waitFor { labelCancel.displayed }
        labelCancel.text() == value
    }

    void labelSaveValue(String value) {
        waitFor { labelSave.displayed }
        labelSave.value() == value
    }

    void patientTitleText(String patientTitleText) {
        waitFor { patientTitle.displayed }
        patientTitle.text() == patientTitleText
    }

    void editPatientText(String text) {
        waitFor { editPatient.displayed }
        editPatient.text() == text
    }

    void goToList() {
        waitFor { homeTab.displayed }
        homeTab.click()
        sleep(3000)
    }

    void openCreatePatientScreen() {
        newPatient.click()
        waitFor 5, { title == "Create New Patient" }
        assert createNewPatientLabel.text() == "Create New Patient"
    }

    void createListOfPatients() {
//        driver.navigate().back()
        waitFor { !$("#loading").displayed }
        browser.at(HomePage)
        def namesMap = ['Alex0': '0Alex', 'LexA_': 'Axel0', 'abDula': 'ABC1', '1abcefgh': 'A0abcd', 'Ale0A': 'ghij', 'abcdef': 'ghijk', 'ghijk': 'abcdef', '“abcde': 'ghijk', '“abcdef': 'f', 'ghij': 'abcde']
        def year = ['1980', '1999', '2000', '2001', '2010', '2015', '2016']
        def monthName = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        def dayN = ['01', '05', '11', '25']
        int i = 0
        int month = 0
        int day = 0
        namesMap.each { firstName, lastName ->
            newPatient.click()

            browser.at(CreatePatientPage)
            waitFor { title == "Create New Patient" }
            assert createNewPatientLabel.text() == "Create New Patient"

            if (i == year.size()) {
                i = 0
            }
            if (month == monthName.size()) {
                month = 0
            }
            if (day == dayN.size()) {
                day = 0
            }
            enterPatientData("${firstName}", "${lastName}", monthName[month], dayN[day], year[i])
            selectDiabetesType()
            selectTherapyType()
            clickSaveBtn()
            waitFor { title == "Patient Record Page" }

            println "${firstName}" + " " + "${lastName}" + " " + dayN[day] + " " + monthName[month] + " " + year[i]

            goToList()
            browser.at(HomePage)
            waitFor { !$("#loading").displayed }
            i++
            month++
            day++
        }
    }

    void createPatientWithoutID(firstname, lastname) {
        firstName = firstname
        lastName = lastname
        monthDropdown.click()
        monthDropdown.value('May')
        dayDropdown.click()
        dayDropdown.value('26')
        yearDropdown.click()
        yearDropdown.value('1966')
        $(By.xpath("(//input[@name='patientDiabetesType'])[1]")).click()
        $(By.cssSelector("input[name=\"patient_details:j_id_id78pc5\"]")).click()
    }

    void createPatientWithoutNames(Id) {
        id = Id
        monthDropdown.click()
        monthDropdown.value('October')
        dayDropdown.click()
        dayDropdown.value('19')
        yearDropdown.click()
        yearDropdown.value('1978')
        $(By.xpath("(//input[@name='patientDiabetesType'])[1]")).click()
        $(By.cssSelector("input[name=\"patient_details:j_id_id78pc5\"]")).click()
    }

    void closePatient(String s) {
        assert $(id: 'last').find('input').value().toString().contains(s)
        $(id: 'last').find('input').click()
    }

    void pageLoaded() {
        waitFor { physicianNameLabel.displayed }
        waitFor { id.displayed }
        waitFor { email.displayed }
    }
}