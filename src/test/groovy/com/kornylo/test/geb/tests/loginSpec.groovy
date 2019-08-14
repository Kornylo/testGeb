package com.kornylo.test.geb.tests


import com.kornylo.test.geb.pages.GoogleTestPage
import com.kornylo.test.geb.pages.LoginTestPage


class loginSpec extends CareLinkSpec {
    static LoginTestPage loginTestPage

    def 'open google page'() {
        when: 'A user see logo google'
        loginTestPage = browsers.to LoginTestPage
        then: 'User can click'
        loginTestPage.menuButton()
        loginTestPage.menuList()
        loginTestPage.loginLine()
        loginTestPage.loginEnter('okotelevets31@gmail.com')
        loginTestPage.enterButton()

    }




}
