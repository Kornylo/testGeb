package com.kornylo.test.geb.tests


import com.kornylo.test.geb.pages.GoogleTestPage


class testSpec extends CareLinkSpec {
    static GoogleTestPage googleTestPage

    def 'open google page'() {
        when: 'A user see logo google'
        googleTestPage = browsers.to GoogleTestPage
        then: 'User can click'
        googleTestPage.searchButton()
        googleTestPage.saearch('geb')
        googleTestPage.button()
        googleTestPage.verifyTextAfterSearch('Geb - Very Groovy Browser Automation')

    }




}
