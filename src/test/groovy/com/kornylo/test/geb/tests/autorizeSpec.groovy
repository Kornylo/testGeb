package com.kornylo.test.geb.tests


import com.kornylo.test.geb.pages.AutorizeTestPage


class autorizeSpec extends CareLinkSpec {
    static AutorizeTestPage autorizeTestPage

    def 'open autorize page'() {
        when: 'A user see button autorize'
        autorizeTestPage = browsers.to AutorizeTestPage
        then: 'User can click'
        autorizeTestPage.printPageTitle()
        autorizeTestPage.mainPage()
        autorizeTestPage.signIn()
        autorizeTestPage.signPage()
        autorizeTestPage.eMail('ssssss@gmail.com')
        autorizeTestPage.submitButton()
        autorizeTestPage.verifyAddress('YOUR PERSONAL INFORMATION')
        autorizeTestPage.registerButton()
        autorizeTestPage.notVerifyAddress('There are 8 errors')
        autorizeTestPage.firstName('Sss')
        autorizeTestPage.register2Button()
        autorizeTestPage.notVerify2Address('There are 7 errors')
        autorizeTestPage.secondName('Ggg')
        autorizeTestPage.register3Button()
        autorizeTestPage.notVerify3Address('There are 6 errors')
        autorizeTestPage.phoneNumber('+180111111111')
        autorizeTestPage.register4Button()
        autorizeTestPage.notVerify4Address('There are 5 errors')
        autorizeTestPage.customerCity('City')
        autorizeTestPage.register5Button()
        autorizeTestPage.notVerify5Address('There are 4 errors')
        autorizeTestPage.customerCode('00000')
        autorizeTestPage.register6Button()
        autorizeTestPage.notVerify6Address('There are 3 errors')
        autorizeTestPage.customerState()
        autorizeTestPage.customer1State()
        autorizeTestPage.register7Button()
        autorizeTestPage.notVerify7Address('There are 2 errors')
        autorizeTestPage.customerAddress('lev st, 9, EXP')
        autorizeTestPage.register8Button()
        autorizeTestPage.notVerify8Address('There is 1 error')
        autorizeTestPage.customerPassword('sssss')
        autorizeTestPage.register9Button()
        autorizeTestPage.enterToAcc()


    }




}
