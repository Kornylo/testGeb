package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.data.api.ApiRequestsSpak
import com.medtronic.ndt.carelink.data.api.ApiPropertySpak
import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Stepwise
import spock.lang.Title

@Stepwise
@Slf4j
@Title('SPAK 802 - Account Creation')
class SPAK802Spec extends Specification {

    def "Account creation 1 - Successfully created account"() {
        when: 'Account is created'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '', '')
    }

    def "Account creation 1 - The account already exists. "() {
        when: 'The account already exists.'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountPassword1, '4', 'The account already exists')
    }


    def "Account creation 2 - 60  symbols for email"() {
        when: 'Account creation 2 - Email 60 symbols'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", 'cfssxfsxfswfcwfxwxwxfwxfwfewzxfwxfwr' + ApiPropertySpak.accountUserName1 + '2', ApiPropertySpak.accountPassword1, '', '')
    }


    def "Account creation 3 - 61 symbols for email"() {
        when: 'Account creation 2 - Email 61 symbols'
        then: 'Check response code = 500'
        ApiRequestsSpak.accountCreation(500, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", 'cfssxfsxfswfcwfxwxwxfwxfwfewzxfwxfwr' + ApiPropertySpak.accountUserName1 + '22', ApiPropertySpak.accountPassword1, '6', 'An unexpected error occurred.')


    }

    def "Account creation 4 - Wrong credentials for user"() {
        when: 'Wrong credentials for user'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", 'user.name(at)mail.com', ApiPropertySpak.accountPassword1, '2', 'Email address is invalid.')
    }

    def "Account creation 5 - Unsupported symbol in username"() {
        when: 'Unsupported symbol in username'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", '@%&@@%$^.co.!@<>=', ApiPropertySpak.accountPassword1, '2', 'Email address is invalid.')
    }

    def "Account creation 6 - Password doesn`t meet requirements"() {
        when: 'Password doesn`t meet requirements'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'testpassword1', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 7 - Username with 0 characters"() {
        when: 'Username with 0 characters'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", '', ApiPropertySpak.accountPassword1, '11', 'An unexpected error occurred.')

    }

    def "Account creation 8 - Password with 0 characters"() {
        when: 'Password with 0 characters'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, '', '11', 'An unexpected error occurred.')
    }

    def "Account creation 9 - Space is present in username"() {
        when: 'Space is present in username'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", 'boss as@ty.sk', ApiPropertySpak.accountPassword1, '2', 'Email address may not contain ‘&’, ‘<’, ‘>’, or ‘=’, or any spaces.')
    }


    def "Account creation 10 - Password doesn`t meet requirements"() {
        when: 'Password doesn`t meet requirements'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'Aaaatuakws', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 11 - Invalid Password (UpperCase)"() {
        when: 'Invalid Password'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'TESTPASSWORD1', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 12 - Invalid Password (Invalid character)"() {
        when: 'Invalid Password'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'TESTPASSWORD$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 13 - Invalid Password (Invalid character and LowerCase)"() {
        when: 'Invalid Password'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'testpassword$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 14 - Invalid Password (Invalid character and Numbers)"() {
        when: 'Invalid Password'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, '9592559925$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 15 - Invalid Password (Invalid character and 7 symbols)"() {
        when: 'Invalid Password'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'Testor$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 16 - Invalid Password (Invalid character and 21 symbols)"() {
        when: 'Invalid Password'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, 'Testpasswordpassword$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 17 - Password matches with username"() {
        when: 'Password matches with username'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountUserName1.replace('@ap1.com', ''), '3', 'The password strength criteria were not met.')
    }

    def "Account creation 18 - Password matches with username and has invalid character"() {
        when: 'Password matches with username and has invalid character'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountUserName1.replace('@ap1.com', '') + '$$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 19 - Password matches with username and has invalid character and UpperCase"() {
        when: 'Password matches with username and has invalid character and UpperCase'
        then: 'Check response code = 400'
        ApiRequestsSpak.accountCreation(400, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1, ApiPropertySpak.accountUserName1.replace('@ap1.com', '').toUpperCase() + '$$', '3', 'The password strength criteria were not met.')

    }

    def "Account creation 20 - Username is not completely included password and invalid character in password"() {
        when: 'Username is not completely included password and invalid character in password'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName1 + '1', ApiPropertySpak.accountUserName1.replace('@ap1.com', '').substring(4) + '$$$', '3', 'The password strength criteria were not met.')
    }

    def "Account creation 21 - Unique Username contains password"() {
        when: 'Unique Username contains password'
        then: 'Check response code = 204'
        ApiRequestsSpak.accountCreation(204, ApiPropertySpak.envisionAccountUserName, ApiPropertySpak.envisionAccountPassword,
                "/auth/create", ApiPropertySpak.accountUserName2.replace('ap1.com', 'gmail-test.comTTT'), 'gmail-test.comTTT', '3', 'The password strength criteria were not met.')
    }
}

