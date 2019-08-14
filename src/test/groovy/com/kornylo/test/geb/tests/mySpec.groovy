package com.kornylo.test.geb.tests


import com.kornylo.test.geb.pages.MyTestPage

class mySpec extends CareLinkSpec {
    static MyTestPage myTestPage

    def 'open my page'() {
        when: 'A user see logo google'
        myTestPage = browsers.to MyTestPage
        then: 'User can click'
        myTestPage.wallPaper()
        myTestPage.searchField()
        myTestPage.sField()
        myTestPage.poshukField('iPhone 8')
        myTestPage.submitForm()
        myTestPage.perev()
        myTestPage.checkMe()
        myTestPage.characters()
        myTestPage.brend()
        myTestPage.vidg()
        myTestPage.vidobr()





    }




}
