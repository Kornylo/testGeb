package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.context.Screenshot
import com.medtronic.ndt.carelink.screenshot.ScreenshotTrait
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SaUI004 - Clinic Staff User Accounts/Window Screen flow content')
@RegressionTest
@Stepwise
@Slf4j
@Screenshot
class SAUI004Spec extends CareLinkSpec implements ScreenshotTrait{

    def 'Clinic Staff User flow'(){
        when: 'Execute SaUI004 per test plan.'
        and: 'Open the browser as specified in the ETP.'
        and: 'Open the MMT-7340 application (CareLink system).\n' +
                'Record the server URL address: '
        and: 'Click on Register Clinic link.\n' +
                'Create a new clinic and record the info:\n' +
                'Clinic Name:'
        and: 'Create a new administrative user and record the info:\n' +
                'Username: (example: TC001)\n' +
                'Password: (example: Password1) '
        and: 'Sign into the MMT-7340 application using the credentials above.'
        and: 'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Click on Clinic Settings link.'
        then: 'Verify that this section contains a Clinic Report Settings option, labeled as “Report Settings”. ' +
                'A Clinic Report Settings Link, labeled as “Report Settings”, shall navigate to the Clinic Report ' +
                'Settings Screen , and any unsaved changes are discarded.'

        when: 'Click on the “Report Settings” option.'
        then: 'Verify that the screen navigates to Clinic Report Settings screen. A Clinic Report Settings Link, ' +
                'labeled as “Report Settings”, shall navigate to the Clinic Report Settings Screen , and any unsaved changes are discarded.'
        then: 'Verify that Clinic Settings screen contains a Clinic Information option, labeled as “Clinic Information”. ' +
                'A Clinic Information Link, labeled as “Clinic Information”, shall navigate to the Clinic Information Screen, and any unsaved changes are discarded.'

        when: 'Click on “Clinic Information”'
        then: 'Verify that the screen navigates to Clinic Information screen. A Clinic Information Link, labeled as ' +
                '“Clinic Information”, shall navigate to the Clinic Information Screen , and any unsaved changes are discarded.'
        then: 'Verify tab labeled “Users” is available for user with Administrative Privileges set ot ON. ' +
                'The Clinic Staff User Accounts Screen is available for currently logged in user.'

        when: 'Click on Users link.'
        then: 'Verify Users link is highlighted and inactive.The Users link is highlighted and inactive'
        then: 'Verify Close Clinic Settings link is labeled as “Close clinic settings”.A Close Clinic Settings link is labeled as “Close clinic settings”.'

        when: 'Click on Close clinic settings link.'
        then: 'Verify that Close Clinic Settings link navigates to the Home Screen. Home screen is opened.'

        when: 'Click on Clinic Settings link.\n' +
                'Click on Users link'
        then: 'Verify the Users table contains only currently logged in user with corresponding First Name.' +
                'Users table contains only one record with First Name corresponding to currently logged in user.'
        then: 'Verify the Users table contains only currently logged in user with corresponding Last Name.' +
                'Users table contains only one record with Last Name corresponding to currently logged in user.'
        then: 'Verify the Users table contains only currently logged in user with corresponding Username.' +
                'Users table contains only one record with Username corresponding to currently logged in user.'
        then: 'Verify the Users table contains only currently logged in user with Admin Privileges marked as ON.' +
                'Users table contains only one record with Admin Privileges marked as ON.'

        when: 'Click on [Open User] button'
        then: 'Verify Staff User Information Window is shown on the screen for the selected [Clinic_Staff_User].' +
                'Staff User Information Window is opened for currently logged in user.'
        then: 'Verify that Staff User Information Window displays the title “User information”.' +
                'The Staff User Information Window shall display the title “User information”.'
        then: 'Verify Staff User Information Window is shown as modal window.The system shall provide a modal Staff User Information Window.'
        then: 'Verify that the [Clinic_Staff_User_LoginID] is pre-populated with existing data.' +
                'Username is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify that the [Clinic_Staff_User_First_Name] is pre-populated with existing data.' +
                'First Name is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify that the [Clinic_Staff_User_Last_Name] is pre-populated with existing data.' +
                'Last Name is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify that the [Clinic_Staff_User_Email] is pre-populated with existing data.' +
                'Email is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify that the [Clinic_Staff_User_Security_Question] is pre-populated with existing data.' +
                'Security Question is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify that the [Clinic_Staff_User_Security_Answer] is pre-populated with existing data.' +
                'Security Answer is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify that the [Clinic_Staff_User_Security_Question] field is indicated as required field.' +
                'Security Question is indicated as required field.'
        then: 'Verify that the [Clinic_Staff_User_Security_Answer] field is indicated as required field' +
                'Security Answer is indicated as required filed.'
        then: 'Verify Security Question dropdown contains all required options.Security Question dropdown options:\n' +
                'Mother’s Maiden Name\n' +
                'Mother’s Birth Date [day, month]\n' +
                'Pet’s Name\n' +
                'Favorite Sports Team'

        when: 'Change value in Security Question dropdown\n' +
                'Click on [Save] button\n' +
                'Click on [Open User] button against currently logged in user'
        then: 'Verify new Security Question dropdown value was saved.Security Question dropdown option corresponds to value in previous step.'
        then: 'Verify that the [Clinic_Staff_User_Admin_Privilege] is pre-populated with existing data.' +
                'Admin Privileges is pre-populated with information corresponding to currently logged in user.'
        then: 'Verify valid text associated with the [Clinic_Staff_User_Admin_Privilege] is displayed.' +
                'The following text is displayed associated with the [Clinic_Staff_User_Admin_Privilege] setting:\n' +
                'NOTE: This setting allows this user to access the Staff User Accounts features.'
        then: 'Verify that the [Clinic_Staff_User_First_Name] field is indicated as required field.First Name is indicated as required field'
        then: 'Verify that the [Clinic_Staff_User_Last_Name] field is indicated as required field.Last Name is indicated as required field.'
        then: 'Verify that the [Clinic_Staff_User_Email] field is indicated as required field.Email is indicated as required field'
        then: 'Verify Enter Your Current Password, Password New Password and Re-enter New Password textfields are inactive.' +
                'The system shall default the capability to change password to no selection on Staff User Information.'
        then: 'Verify that change password checkbox is unchecked by default on Staff User Information Window. ' +
                'The system shall default the capability to change password to no selection on Staff User Information.'

        when: 'Tick Change Password checkbox to be ON.'
        then: 'Verify Enter Your Current Password, New Password and Re-enter New Password textfields become active for user input.' +
                'Enter Your Current Password, New Password and Re-enter New Password textfields are active for user input.'
        then: 'Verify that the Enter Your Current Password field is indicated as required field.Enter Your Current Password is indicated as required field.'
        then: 'Verify that the [Clinic_Staff_User_Password] field is indicated as required field.New Password is indicated as required field.'
        then: 'Verify that the [Clinic_Staff_User_Password_Confirm] field is indicated as required field.Re-enter New Password is indicated as required field.'

        when: 'Tick Change Password checkbox to be OFF. '
        then: 'Verify that the [Save] button is provided and labeled "Save".A [Save] button shall be provided labeled “Save”.'

        when: 'Update user information in all editable textfields.\n' +
                'Click on the Cancel link.'
        then: 'Verify user is redirected to Clinic Staff User Accounts Screen.User is redirected to the Clinic Staff User Accounts Screen.'

        when: 'Click [Open User] button against currently logged in user in table.'
        then: 'Verify user information was not updated with values entered before clicking Cancel link.User information entered before clicking Cancel link was not saved.'

        when: 'Click on the [Cancel] button.\n' +
                'Click on the [Create New User] button.\n' +
                'Set Change password checkbox to be active\n' +
                'Fill in all required information:\n' +
                '- Username:\n' +
                '- First Name:\n' +
                '- Last Name:\n' +
                '- Email:\n' +
                '- Enter Your Current Password:\n' +
                '- Assign Temporary Password:\n' +
                '- Re-enter Temporary Password:\n' +
                '- Security Question:\n' +
                '- Security Answer:\n' +
                'Make sure Administrative Privileges checkbox is set as OFF.\n' +
                'Click on [Save] button.\n' +
                'Note: avoid using any symbol from <,>,& or ='
        then: 'Verify that If validation passes, the system stores the entered data in a new [ Clinic_ Staff_ User ] ' +
                'account and navigate to the Clinic Staff User Accounts Screen.If validation passes, the system shall store the entered data in a new [ Clinic_ Staff_ User ] ' +
                'account and navigate to the Clinic Staff User Accounts Screen.'
        then: 'Verify only one user can be selected at a time using radio button in Select column on Clinic Staff User Accounts Screen.' +
                'Selecting a row in the Staff User Account List indicates the selected [Clinic_Staff_User].'
        then: 'Verify only one user can be selected at a time using radio button in Select column on Clinic Staff User Accounts Screen.' +
                'Selectable user input fields for each [Clinic_Staff_User], titled as “Select”, is provided where only one user may be selected at a time.'

        when: 'Select newly created user using radio button in Select column.\n' +
                'Click on [Open User] button.'
        then: 'Verify Staff User Information Window for the selected [Clinic_Staff_User] is opened.S' +
                'taff User Information Window is oped with information corresponding to newly created [Clinic_Staff_User].'

        when: 'Click on the [Cancel] button.'
        and: 'Select currently logged in user.\n' +
                'Click on [Open User] button. '
        then: 'Verify [Delete user] button is not active so that user cannot delete his own account.' +
                'The "Delete User Button" is inactive and not allow a user to delete their own account.'

        when: 'Click on the [Cancel] button.\n' +
                'Select newly created user.\n' +
                'Click on [Open User] button.'
        then: 'Verify [Delete User] button is active.Delete User Confirmation Window is displayed in a modal window.'

        when: 'Click on [Delete User] button.'
        then: 'Verify that upon clicking on [Delete User] button Delete User Confirmation Window displayed in a modal window.' +
                'Delete User Confirmation Window is displayed in a modal window.'
        then: 'Verify Delete User Confirmation Window is correctly displayed.The dialogue window displays the title “Delete User?”.'
        then: 'Verify Delete User Confirmation Window is correctly displayed.The dialogue window displays the following text:\n' +
                '“Are you sure you want to delete this user?”.'

        when: 'Click on Cancel link'
        then: 'Verify Cancel link, labeled as “Cancel”, closes the confirmation window, discard any changes, ' +
                'and navigate to the Staff User Information Window.User is navigated to Staff User Information Window.'

        when: 'Click on the [Delete User] button.\n' +
                'Click on the [Delete User] button in Delete User Confirmation Window.'
        then: 'Verify deleted user is no longer shown in table on Users tab.Deleted user is no longer shown in table on Users tab.'

        when: 'Log out of the system'
        and: 'Attempt to login to the system using the previously deleted user.'
        then: 'Verify that the deleted user is unable to login to the clinic. A Delete User Button, labeled as “ Delete User ”, ' +
                'shall set the [Clinic_Staff_User_Account_Status ] to Deleted.'

        when: 'Login to the clinic using credentials from Step 2958602.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Attempt to re-create the previously deleted user.'
        then: 'Verify that the Login ID of the deleted user is available to be created. ' +
                'A Delete User Button, labeled as “ Delete User ”, shall set the [Clinic_Staff_User_Account_Status ] to Deleted.'

        when: 'Click on [Open User] button against currently logged in user'
        and: 'Fill in Enter Your Current Password textfield with password equal to current user password.\n' +
                'Set New Password _______________(password#1) and Re-enter New Password matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Click on [Save] button.'
        then: 'Verify user is navigated to the Clinic Staff User Accounts Screen.User is navigated to Clinic Staff User Accounts Screen.'

        when: 'Open new browser using the link from step ID 2958600.\n' +
                'Sign into the MMT-7340 application using the credentials from step ID 2958602.'
        then: 'Verify user is not able to login with old password and system navigates to the Incorrect Login Screen.' +
                'The user is navigated to Incorrect Login Screen.'

        when: 'Sign into the MMT-7340 application using the New Password (password#1).\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        then: 'Verify that user is navigated to the Home Screen.User is navigated to the Home Screen'

        when: 'Click on Sign out link and close new browser.\n' +
                'Click on [Open User] button against currently logged in user.\n' +
                'Set Change password checkbox to be active'
        and: 'Fill in Enter Your Current Password textfield with password equal to current user password.\n' +
                'Set New Password _____________ (password#2) and Re-enter New Password matching next conditions:\n' +
                '- Different from password#1\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Click on [Save] button.'
        then: 'Verify user is navigated to the Clinic Staff User Accounts Screen.User is navigated to the Clinic Staff User Accounts Screen.'

        when: 'Open new browser using the link from step ID 2958600 .\n' +
                'Sign into the MMT-7340 application using the credentials that you created before (password#1)'
        then: 'Verify user is not able to login with old password and system navigates to the Incorrect Login Screen.' +
                'User is navigated to the Incorrect Login Screen.'

        when: 'Sign into the MMT-7340 application using the credentials of the newly created user (password#2).\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        then: 'Verify that user successfully logs in and is navigated to the Home screen.' +
                'User is logged in with new password and navigated to Home screen'

        when: 'Click on Sign out link and close new browser.\n' +
                'Click on the [Create New User] button.\n' +
                'Set Change password checkbox to be active\n' +
                'Fill in all required information:\n' +
                '- Username:\n' +
                '- First Name:\n' +
                '- Last Name:\n' +
                '- Email:\n' +
                '- Enter Your Current Password:\n' +
                '- Assign Temporary Password :\n' +
                '- Re-enter Temporary Password:\n' +
                '- Security Question:\n' +
                '- Security Answer:\n' +
                'Make sure Administrative Privileges checkbox is set as OFF.\n' +
                'Click on [Save] button.'
        and: 'Select newly created user using radio button in Select column.\n' +
                'Click on [Open User] button.\n' +
                'Set Change password checkbox to be active\n' +
                'Fill in Enter Your Current Password textfield with password equal to current user password.\n' +
                'Set Assign Temporary Password ____________ (password#3) and Re-enter Temporary Password matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Click on [Save] button'
        then: 'Verify user is navigated to the Clinic Staff User Accounts Screen. User is navigated to the Clinic Staff User Accounts Screen.'

        when: 'Select newly created user using radio button in Select column.\n' +
                'Click on [Open User] button.'
        and: 'Set Change password checkbox to be active\n' +
                'Fill in Enter Your Current Password textfield with password equal to current user password.\n' +
                'Set Assign Temporary Password _____________ (password#4) and Re-enter Temporary Password matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Click on [Save] button.'
        then: 'Verify user is navigated to the Clinic Staff User Accounts Screen.User is navigated to the Clinic Staff User Accounts Screen.'

        when: 'Open new browser using the link from step ID 2958600.\n' +
                'Sign into the MMT-7340 application using the credentials of the newly created user (use password#3)'
        then: 'Verify user is not able to login with previous assign temporary password and system navigates user to the Incorrect Login Screen.' +
                'User is navigated to Incorrect Login Screen.'

        when: 'Sign into the MMT-7340 application using the credentials of the newly created user (use password#4).\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        then: 'Verify that user password is expired and user is navigated to the Password Expired Screen.' +
                'User is navigated to Password Expired screen when the existing user logs in for the first time after his password was changed.'

        when: 'Set New Password and Retype New Password matching the Assign Temporary Password (use password#4).\n' +
                'Save changes.\n' +
                'Note: the user stays on the Password Expired Screen.'
        then: 'Verify the system does not allow to set New password equal to Assign Temporary passwordChanges are not saved and error message is displayed.'

        when: 'Set New Password _________________________and Retype New Password matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Click on [Save] button'
        then: 'Verify New Password is set and user is navigated to the Home Screen.User is navigated to the Home Screen.'

        when: 'Click on Clinic Settings tab.'
        then: 'Verify Users tab is not available on Clinic Settings tab for users with Administrative Privileges checkbox being set as OFF.' +
                'The Clinic Staff User Accounts Screen is not available for the user.'

        when: 'Click on Sign Out link\n' +
                'Close all browsers.'
        then: 'End of test'
    }
}
