package com.kornylo.test.geb.pages

import geb.Page

class AutorizeTestPage extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            carousel.displayed
        }
    }

    static content = {
        // Username
        carousel(required: false) { $("#homeslider > li:nth-child(2) > div") }
        buttonIn(required: false) {$('#header > div.nav > div > div > nav > div.header_user_info > a')}
        verifyPage(required: false) {$('#center_column > h1')}
        address(required: false) {$('#email_create')}
        submit(required: false) {$('#SubmitCreate')}
        result(required: false) {$('#account-creation_form > div:nth-child(1) > h3')}
        reg(required: false) {$('#submitAccount > span')}
        notResult(required: false) {$('#center_column > div > p')}
        first(required: false) {$('#customer_firstname')}
        last(required: false) {$('#customer_lastname')}
        mobile(required: false) {$('#phone_mobile')}
        city(required: false) {$('#city')}
        code(required: false) {$('#postcode')}
        state(required: false) {$('#id_state')}
        alabama(required: false) {$('#id_state > option:nth-child(2)')}
        adr(required: false) {$('#address1')}
        pas(required: false) {$('#passwd')}
        acc(required: false) {$('#center_column > p')}
    }
    public void printPageTitle() {
        driver.getTitle()

    }
    boolean mainPage() {
        carousel.displayed
    }
    void signIn() {
        buttonIn.click()
    }
    boolean signPage() {
        verifyPage.displayed
    }
    void eMail(String string) {
        address = string
    }
    void submitButton() {
        submit.click()
    }
    public void verifyAddress(String Text) {
        waitFor { result.displayed }
        assert result.text().contains(Text)
    }
    void registerButton() {
        reg.click()
    }
    public void notVerifyAddress(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void firstName(String string) {
        first = string
    }
    void register2Button() {
        reg.click()
    }
    public void notVerify2Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void secondName(String string) {
        last = string
    }
    void register3Button() {
        reg.click()
    }
    public void notVerify3Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void phoneNumber(String string) {
        mobile = string
    }
    void register4Button() {
        reg.click()
    }
    public void notVerify4Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void customerCity(String string) {
        city = string
    }
    void register5Button() {
        reg.click()
    }
    public void notVerify5Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void customerCode(String string) {
        code = string
    }
    void register6Button() {
        reg.click()
    }
    public void notVerify6Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void customerState() {
        state.click()
    }
    void customer1State() {
        alabama.click()
    }
    void register7Button() {
        reg.click()
    }
    public void notVerify7Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void customerAddress(String string) {
        adr = string
    }
    void register8Button() {
        reg.click()
    }
    public void notVerify8Address(String Text) {
        waitFor { notResult.displayed }
        assert notResult.text().contains(Text)
    }
    void customerPassword(String string) {
        pas = string
    }
    void register9Button() {
        reg.click()
    }
    boolean enterToAcc() {
        acc.displayed
    }





}
