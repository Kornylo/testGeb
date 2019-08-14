package com.kornylo.test.geb.tests


import com.kornylo.test.geb.pages.AutorizeCrashTestPage


class autorizeCrashSpec extends CareLinkSpec {
    static AutorizeCrashTestPage autorizeCrashTestPage

    def 'open autorize page'() {
        when: 'A user see button autorize'
        autorizeCrashTestPage = browsers.to AutorizeCrashTestPage
        then: 'User can click'
        autorizeCrashTestPage.printPageTitle()
        autorizeCrashTestPage.mainPage()
        autorizeCrashTestPage.signIn()
        autorizeCrashTestPage.signPage()
        autorizeCrashTestPage.eMail('sss')
        autorizeCrashTestPage.submitButton()
        autorizeCrashTestPage.verifyAddress('Invalid email address.')


    }




}
