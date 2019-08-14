package com.kornylo.test.geb.pages

import geb.Page
import geb.waiting.Wait
import geb.waiting.WaitTimeoutException
import org.openqa.selenium.Keys
import org.openqa.selenium.interactions.Actions

import javax.swing.Action
import java.util.concurrent.TimeoutException

class MyTestPage extends Page {


    // Synchronization check, gets looped for the duration of the configured wait timeout (see: GebConfig) until this resolves true
    static at = {
        waitFor {
            wallP.displayed
        }
    }

    static content = {
        // Username
        wallP(required: false) { $('body > div.wrapper > div.home__slider-wrap > div > div > div') }
        field(required: false) {$('body > div.wrapper > header > div.header__topline > div > div > div.col.header__topline_right.d-flex.justify-content-end > span > span')}
        sfield(required: false) {$('#search')}
        search(required: false) {$('#search')}
        perevirka(required: false) {$('#product-list > div.product-list.product-list_category.d-flex > div:nth-child(1) > div.product-card__image-holder > a > div > img')}
        check(required: false) {$('#product-list > div.product-list.product-list_category.d-flex > div:nth-child(1) > div.product-card__image-holder > a > div > img')}
        chars(required: false) {$('#pills-parameters-tab')}
        mark(required: false) {$('#product-features > tbody > tr:nth-child(1) > td.value')}
        vidguk(required: false) {$('#pills-reviews-tab')}
        nonv(required: false) {$('#pills-reviews > div > section > h4 > a')}
    }

    public void printPageTitle() {
        driver.getTitle()
    }
    boolean wallPaper() {
        wallP.displayed
    }
    void searchField() {
        field.click()
    }
    boolean sField() {
        sfield.displayed
    }
    void poshukField(String string) {
        search = string
    }
    void submitForm() {
        search<<Keys.ENTER
    }
    boolean perev() {
        perevirka.displayed
    }
    void checkMe() {
        check.click()
    }
    void characters() {
        chars.click()
    }

    boolean brend() {
        mark.displayed
    }

    void vidg()  {
        vidguk.click()
    }
    boolean vidobr() {
        nonv.displayed
    }



}
