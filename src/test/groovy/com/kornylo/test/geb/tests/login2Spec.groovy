package com.kornylo.test.geb.tests


import com.kornylo.test.geb.pages.LoginTest2Page

class login2Spec extends CareLinkSpec {
    static LoginTest2Page loginTest2Page

    def 'open rozetka page'() {
        when: 'A user see logo google'
        loginTest2Page = browsers.to LoginTest2Page
        then: 'User can click'
        loginTest2Page.loginButton()
        loginTest2Page.loginBtn()
        loginTest2Page.autorizeLog()
        loginTest2Page.loginField('okotelevets31@gmail.com')
        loginTest2Page.passwordField('14051996Ss')
        loginTest2Page.checkBtn()
        loginTest2Page.chekPage()
        loginTest2Page.cabinetMy()
        loginTest2Page.infoPage()


    }




}
