package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.data.api.ApiPropertyMH
import com.medtronic.ndt.carelink.data.api.ApiRequestsMH
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.json.JsonBuilder
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import spock.lang.Stepwise
import spock.lang.Title

@RegressionTest
@Stepwise
@Slf4j
@Title('MHAPI 1007 - Account Login')
class MHAPI1007Spec extends CareLinkSpec {
    def uid = ApiPropertyMH.uid
    def url = ApiPropertyMH.baseAPI + '/auth/login'

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 200'
        def payload = new JsonBuilder(username: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1)
        println(payload.toPrettyString())
        ApiRequestsMH.accountCreation(200, '/auth/create', payload)
    }

    def 'Account Login 1 - Valid username, password'() {
        when: 'Account Login 1'
        then: 'Check response code = 200'
        def data = [account: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, uid: uid, url: url]
        ApiRequestsMH.accountLogin(200, data)
    }
    final static String characters = RandomStringUtils.randomNumeric(36)

    def "Account creation 1.2 - Username should include 60 characters."() {
        when: 'Account is created'
        then: 'Check response code = 200'
        def payload = new JsonBuilder(username: characters + ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1)
        println(payload.toPrettyString())
        ApiRequestsMH.accountCreation(200, '/auth/create', payload)
    }

    def 'Account Login 2 - Valid Email address format – 60 characters'() {
        when: 'Account Login 2'
        then: 'Check response code = 200'
        def data = [account: characters + ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, uid: uid, url: url]
        ApiRequestsMH.accountLogin(200, data)
    }

    def 'Account Login 3 - Valid User name which does not exists in database'() {
        when: 'Account Login 3'
        then: 'Check response code = 401'
        def data = [account: 'someCharacters' + ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, uid: uid, url: url]
        ApiRequestsMH.accountLogin(401, data)
    }

    def 'Account Login 4 - Valid user name which exists in DB and invalid password'() {
        when: 'Account Login 4'
        then: 'Check response code = 401'
        def data = [account: ApiPropertyMH.accountUserName1, password: 'WrongPassword', uid: uid, url: url]
        ApiRequestsMH.accountLogin(401, data)
    }

    def 'Account Login 5 - HCP account and valid password'() {
        when: 'Account Login 5'
        then: 'Check response code = 400'
        def data = [account: ApiPropertyMH.hcpAccountUserName1, password: ApiPropertyMH.hcpAccountPassword1, uid: uid, url: url]
        ApiRequestsMH.accountLogin(400, data)
    }

    def 'Account Login 6 - Invalid password – empty string'() {
        when: 'Account Login 6'
        then: 'Check response code = 401'
        def data = [account: ApiPropertyMH.accountUserName1, password: '', uid: uid, url: url]
        ApiRequestsMH.accountLogin(401, data)
    }

    def 'Account Login 7 - Unsecured HTTP connection'() {
        when: 'Account Login 6'
        then: 'Check response code = 404'
        def data = [account: ApiPropertyMH.accountUserName1, password: ApiPropertyMH.accountPassword1, uid: uid, url: url.replace('https', 'http')]
        ApiRequestsMH.accountLogin(404, data)
    }
}
