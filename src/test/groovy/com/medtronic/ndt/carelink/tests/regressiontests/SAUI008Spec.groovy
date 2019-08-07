package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SaUI008 - Clinic Staff User Window Validations')
@RegressionTest
@Stepwise
@Slf4j
@Screenshot
class SAUI008Spec extends CareLinkSpec implements ScreenshotTrait{

    def 'Clinic staff user validations'(){
        when: 'Open the browser  as specified in the ETP'
        and: 'Open the MMT-7340 application (CareLink System)'
        and: 'Record the server URL address'
        and: 'Click on the Register Clinic link.'
        and: 'Create a new clinic and record the info: Clinic Name.'
        and: 'Create a new administrative user (username must contain numbers)'
        and: 'Sign into the MMT-7340 application using the above credentials'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Click on Clinic settings link and click on Users link.'
        and: 'Select the currently logged in user and navigate to staff user information window screen.'
        and: 'Update the first name, last name, email and security answer with < symbol and save changes.'
        then: 'Verify first name cannot be saved with < symbol.'
        then: 'Verify last name cannot be saved with < symbol.'
        then: 'Verify email cannot be saved with < symbol.'
        then: 'Verify security answer cannot be saved with < symbol.'

        when: 'Update the first name, last name, email and security answer with > symbol and save changes.'
        then: 'Verify first name cannot be saved with > symbol.'
        then: 'Verify last name cannot be saved with > symbol.'
        then: 'Verify email cannot be saved with > symbol.'
        then: 'Verify security answer cannot be saved with > symbol.'

        when: 'Update the first name, last name, email and security answer with & symbol.'
        then: 'Verify first name cannot be saved with & symbol.'
        then: 'Verify last name cannot be saved with & symbol.'
        then: 'Verify email cannot be saved with & symbol.'
        then: 'Verify security answer cannot be saved with & symbol.'

        when: 'Update the first name, last name, email and security answer with = symbol.'
        then: 'Verify first name cannot be saved with = symbol.'
        then: 'Verify last name cannot be saved with = symbol.'
        then: 'Verify email cannot be saved with = symbol.'
        then: 'Verify security answer cannot be saved with = symbol.'

        then: 'Update text fields last name, email, security answer with valid value leave first name value blank and save changes.'
        then: 'Verify first name value is required.'

        when: 'Update the first name field with more than 40 characters'
        then: 'Verify First Name value cannot be more than 40 characters.It\'s not allowed to enter 41 characters into First Name textfield.'

        when: 'Update the first name field with 1 and save changes. Note: avoid using any symbol from <,>,& or ='
        then: 'Verify first name is saved as 1.'

        when: 'User is navigated to Clinic Staff User Accounts screen.'
        and: 'Select currently logged in user and navigate to Staff User Information Window screen.'
        and: 'Update the first name text field with 40 characters and save changes. Note: avoid using any symbol from <,>,& or ='
        then: 'Verify First Name value with 40 characters is saved.' +
                'First Name with 40 characters is saved and user is navigated to Clinic Staff User Accounts screen.'

        and: 'Update First Name textfield: - Enter an character with a space before and after (e.g.: “ name ”)'
        and: 'Save changes.'
        and: 'Note: 1) avoid using any symbol from <,>,& or =' +
                '2) user is redirected to Clinic Staff User Accounts Screen after pressing [Save] button'
        and: 'Select currently logged in user and navigate to Staff User Information Window screen.'
        then: 'Verify First Name value was saved without any leading spaces or trailing spaces.' +
                'First name is saved without any leading or trailing spaces.'

        when: 'Update Last Name textfield:' +
                '- Leave textfield empty' +
                'Save changes.'
        then: 'Verify Last Name value is required. Changes are not saved and error message is displayed.'

        when: 'Update Last Name textfield:' +
                '- Type 1 character' +
                'Save changes.' +
                'Note: avoid using any symbol from <,>,& or ='
        then: 'Verify Last Name value with 1 character is savedLast Name with 1 character is saved and user is navigated to Clinic Staff User Accounts screen.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen.' +
                'Update Last Name textfield:' +
                '- Type 40 characters' +
                'Save changes.' +
                'Note: avoid using any symbol from <,>,& or ='
        then: 'Verify Last Name value with 40 characters is saved.Last Name with 40 characters is saved and user is navigated to Clinic Staff User Accounts screen.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen.' +
                'Update Last Name textfield:' +
                '- Attempt to type more than 40 characters.'
        then: 'Verify Last Name value cannot be more than 40 characters.It\'s not allowed to enter 41 characters into Last Name textfield.'

        when: 'Update Last Name textfield:' +
                '- Enter an character with a space before and after (e.g.: “ name ”)' +
                'Save changes.'
        then: 'Verify Last Name value was saved without any leading spaces or trailing spaces. Last Name is saved without any leading or trailing spaces.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen.\n' +
                'Update Email textfield:' +
                '- Leave textfield empty' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes;'
        then: 'Verify Email value is required.Changes are not saved and error message is displayed.'

        when: 'Update Email textfield:' +
                '- Type valid email address with space between two characters' +
                'Save changes.' +
                'Note:' +
                '1) avoid using any symbol from <,>,& or =' +
                '2) user stays on Staff User Information Window screen after saving changes;'
        then: 'Verify Email value cannot contain any spacesEmail value shall not contain any spaces and include the “@” and a “.” Character.\n' +
                'Error message is displayed.'

        when: 'Update Email textfield:' +
                '- Type character and “@” symbol' +
                'Save changes.' +
                'Note: ' +
                '1) avoid using any symbols from <, >, & or =;' +
                '2) user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify Email value should include the “.”character.Email value shall not contain any spaces and include the “@” and a “.” Character.\n' +
                'Error message is displayed.'

        when: 'Update Email textfield:' +
                '- Type character and “.” symbol' +
                'Save changes.' +
                'Note: ' +
                '1) avoid using any symbols from <, >, & or =;' +
                '2) user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify Email value should include the “@” characterEmail value shall not contain any spaces and include the “@” and a “.” Character.\n' +
                'Error message is displayed.'

        when: 'Update Email textfield:' +
                '- Attempt to type email with more than 80 characters long'
        then: 'Verify Email value cannot be longer than 80 characters. It\'s not allowed to enter 81 characters into Email textfield.'

        when: 'Update Email textfield:' +
                '- Type valid email 80 characters long' +
                'Save changes.' +
                'Note: avoid using any symbol from <,>,& or =.'
        then: 'Verify Email value with 80 characters is saved. ' +
                'Valid Email with 80 characters is saved and user is navigated to Clinic Staff User Accounts screen.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen'
        and: 'Update Security Answer textfield:' +
                '- Leave textfield empty' +
                'Save changes.'
        then: 'Verify Security Answer value is required. Changes are not saved and error message is displayed'

        when: 'Update Security Answer textfield:' +
                '- Type 1 character' +
                'Save changes.' +
                'Note: avoid using any symbol from <,>,& or =.'
        then: 'Verify Security Answer value with at least 1 character is saved. ' +
                'Security Answer with 1 character is saved and user is navigated to Clinic Staff User Accounts screen.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen.' +
                'Update Security Answer textfield:' +
                '- Attempt to type more than 40 characters'
        then: 'Verify Security Answer value cannot be more than 40 characters.' +
                'It\'s not allowed to enter 41 characters into Security Answer.'

        when: 'Update Security Answer textfield:' +
                '- Type 40 characters' +
                'Save changes.' +
                'Note: avoid using any symbol from <,>,& or =.'
        then: 'Verify Security Answer value with 40 characters is saved. ' +
                'Security Answer with 40 characters is saved and user is navigated to Clinic Staff User Accounts screen.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen.' +
                'Update Security Answer textfield:' +
                '- Enter an character with a space before and after (e.g.: “ test ”)' +
                'Save changes.' +
                'Note: avoid using any symbol from <, >, & or =.'
        then: 'Verify Security Answer value was saved without any leading spaces or trailing spaces. Security Answer is saved without any leading or trailing spaces.'

        when: 'Select currently logged in user and navigate to Staff User Information Window screen.' +
                'Set Change password checkbox to be active.'
        and: 'Set Enter Your Current Password matching next conditions:' +
                '- Attempt to type more than 20 characters'
        then: 'Verify Enter Your Current Password value cannot be more than 20 characters.' +
                'It\'s not allowed to enter 21 characters into Enter Your Current Password textfield.'
        then: 'Verify entry fields for Enter Your Current Password mask each character with asterisk entered by instead of the entered character.' +
                'The Entry fields for Password shall mask each character entered by instead of the entered character.'

        when: 'Set Enter Your Current Password, New Password and Re-enter New Password matching next conditions:' +
                '- Leave Enter Your Current Password textfield empty' +
                '- Enter at least 8 characters in the textfield New Password and Re-enter New Password' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify Enter Your Current Password value is required.' +
                'The system shall require the user to enter admin current password if the user chooses to change a user password on Staff User Information.'

        when: 'Set Enter Your Current Password, New Password and Re-enter New Password matching next conditions:' +
                '- Fill in Enter Your Current Password textfield with password not equal to current user password' +
                '- Fill in New Password and Re-enter New Password with at least 8 characters matching a minimum of three of the following character types:' +
                '• Uppercase letter' +
                '• Lowercase letter' +
                '• Number' +
                '• Special Character' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify that the system shall display a validation error “Incorrect current password”. ' +
                'The system shall notify the user if the entered admin password does not match the existing admin password on Staff User Information.'

        when: 'Repeat previous step 4 times '
        then: 'Verify if the user tries to change his password with using incorrect current password 5 times in a row, the system should log out the user.' +
                'The system shall log out the user and navigate to Login if the user enters incorrect admin password for consecutively 5 times on User Staff Information.'

        when: 'Sign into the MMT-7340 application using Username from step ID 2956840 and enter user’s current password.' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Click on Clinic Settings link. Click on Users link.' +
                'Navigate to Staff User Information Window screen for currently logged in user.' +
                'Set Change password checkbox to be active.'
        and: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Leave New Password textfield empty.' +
                'Enter valid password in Re-enter New Password textfield.' +
                'Save changes.'
        then: 'Verify New Password value is required.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Enter valid password in New Password textfield.' +
                'Leave Re-enter New Password textfield empty.' +
                'Save changes.'
        then: 'Verify Re-enter new Password value is required.Changes are not saved and error message is displayed.'

        when: 'Set New Password matching next conditions:' +
                '- Attempt to type more than 20 characters'
        then: 'Verify New Password value cannot be more than 20 characters.It\'s not allowed to enter 21 characters into New Password.'

        when: 'Set Re-enter New Password matching next conditions:' +
                '- Attempt to type more than 20 characters'
        then: 'Verify Re-enter New Password value cannot be more than 20 characters.' +
                'It\'s not allowed to enter 21 characters into Re-enter New Password.'
        then: 'Verify entry fields for New Password mask each character with asterisk instead of the entered character.' +
                'Entry fields for Password shall mask each character entered by instead of the entered character.'
        then: 'Verify entry fields for Re-enter New Password mask each character with asterisk instead of the entered character.' +
                'Entry fields for Password shall mask each character entered by instead of the entered character.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length equal to 7 characters' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password value cannot be less than 8 characters.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '• Uppercase letter' +
                '• Lowercase letter' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password cannot be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '• Uppercase letter' +
                '• Number' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password cannot be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '• Uppercase letter' +
                '• Special Character' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password cannot be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '• Lowercase letter' +
                '• Number' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password cannot be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '• Lowercase letter' +
                '• Special Character' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password cannot be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password and Re-enter New Password matching next conditions:' +
                '- Length between 8 and 20 characters' +
                '- Text shall include only next character types:' +
                '• Number' +
                '• Special Character' +
                'Save changes.' +
                'Note: user stays on Staff User Information Window screen after saving changes.'
        then: 'Verify New Password cannot be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character.Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set New Password matching next conditions:' +
                '- Length equal to 20 characters' +
                '- Text shall include a minimum of three of the following character types:' +
                '• Uppercase letter' +
                '• Lowercase letter' +
                '• Number' +
                '• Special Character' +
                'Set Re-enter New Password to be different from New Password.' +
                'Save changes.'
        then: 'Verify New Password cannot be updated if Re-enter New Password does not match New Password.' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set:' +
                '- New password that is a subset of Username (e.g. username: username1, password: T$username1);' +
                '- Re-enter New Password matching New password.' +
                'Save changes.'
        then: 'Verify new password is not set when New Password and Re-enter New Password is a subset of Username. ' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set:' +
                '- New password that completely matching with Username but some letters in upper case (e.g. username: username1, password: T$USERname1);' +
                '- Re-enter New Password matching New password.' +
                'Save changes.'
        then: 'Verify new password is not set when New Password and Re-enter New Password is a case insensitive in comparison with Username. ' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in Enter Your Current Password textfield with password equal to current user password.' +
                'Set:' +
                '- New password that include Username not completely (e.g. username: username1, password: T$sername1);' +
                '- Re-enter New Password matching New password.' +
                'Save changes.'
        then: 'Verify new password is set when New Password and Re-enter New Password not contain Username completely. ' +
                'Changes are saved and error message is NOT displayed.'
        then: 'End of the test.'
    }
}