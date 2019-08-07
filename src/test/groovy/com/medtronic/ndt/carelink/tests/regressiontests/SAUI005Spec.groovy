package com.medtronic.ndt.carelink.tests.regressiontests

import com.medtronic.ndt.carelink.context.RegressionTest
import com.medtronic.ndt.carelink.pages.clinic.ClinicSettingsPage
import com.medtronic.ndt.carelink.pages.clinic.ClinicUsersPage
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.tests.CareLinkSpec
import groovy.util.logging.Slf4j
import spock.lang.Stepwise
import spock.lang.Title

@Title('SaUI005 - Create new staff user window flow content')
@RegressionTest
@Stepwise
@Slf4j
class SAUI005Spec extends CareLinkSpec {

    static SignInPage signInPage
    static ClinicSettingsPage clinicSettingsPage
    static ClinicUsersPage usersPage

    def 'Create new user window flow' () {
        when: 'Open the browser  as specified in the ETP'
        and: 'Open the MMT-7340 application (CareLink System)'
        and: 'Record the server URL address'
        and: 'Click on the Register Clinic link.'
        and: 'Create a new clinic and record the info: Clinic Name.'
        and: 'Create a new administrative user and record the info:'
        and: 'Sign into the MMT-7340 application using the credentials above.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.\n' +
                'Click on Clinic Settings link and than click on Users link'
        then: 'Verify Users link is highlighted and inactive.'
        then: 'Verify Close Clinic settings link is labeled as Close Clinic Settings.'

        when: 'Click on close clinic settings.'
        then: 'Verify home screen is opened.'

        when: 'Click on Clinic settings link and click on Users link.'
        then: 'Verify [Create New Staff User] button, is labeled as Create new user. A [Create New Staff User] button, labeled as Create new user.'

        when: 'Click on [Create New User] button'
        then: 'Verify that user navigates to the Create New Staff User screen in a modal window. A Create New Staff User Button, shall navigate to the Create New Staff User Window.\n' +
                'The system shall provide the Create New Staff User Window in a modal window where focus is only given to the Create New Staff User Window elements.'
        then: 'Verify that the window displays the title “Create New User”. The Create New Staff User Window shall display the title “Create New User”.'
        then: 'Verify Create New Staff User screen does not contain Common Screen Elements. The window shall not include any of the Common Screen Header elements.\n' +
                'The window shall not include any of the Common screen Footer Elements.'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_LoginID]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window:\n' +
                '[Clinic_Staff_User_LoginID].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_First_Name]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_First_Name].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Last_Name]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Last_Name].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Email]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Email].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Password]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Password].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Password_Confirm]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Password_Confirm].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Security_Question].' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Security_Question].'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Security_Answer].' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Security_Answer].'
        then: 'Verify [Clinic_Staff_User_Security_Question] field is indicated as required field.The system shall indicate that the following fields require user entry to create a new user:\n' +
                '[Clinic_Staff_User_Security_Question].'
        then: 'Verify [Clinic_Staff_User_Security_Answer] field is indicated as required field.' +
                'The system shall indicate that the following fields require user entry to create a new user: [Clinic_Staff_User_Security_Answer].'

        when: 'Click on [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user and fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" fields matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text includes a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Leave Security Answer text field empty.\n' +
                'Save changes.'
        then: 'Verify Security Answer value is required. Security Answer value shall be required.'
        then: 'Verify Security Question drop down contains all required options.\u0001Security Question drop down options:\n' +
                'Mother’s Maiden Name\n' +
                'Mother’s Birth Date [day, month]\n' +
                'Pet’s Name\n' +
                'Favorite Sports Team'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" fields matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Update "Security Answer" text field:\n' +
                '- Type 1 character.\n' +
                'Note: avoid using any symbol from "<, >, & or =".\n' +
                'Save changes.'
        then: 'Verify Security Answer value with at least 1 character is saved. Security Answer value shall be 1 to 40 characters'
        then: 'Verify Clinic Staff User Accounts Screen is displayed. ' +
                'If validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account ' +
                'and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button.\n' +
                'Update "Security Answer" text field:\n' +
                '- Attempt to type more than 40 characters'
        then: 'Verify Security Answer value can not be more than 40 characters. Security Answer value shall be 1 to 40 characters'

        when: 'Enter all the required fields to create a new staff user and fill in "Enter Your Current Password" text field ' +
                'with password equal to current user password that you saved before.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Update "Security Answer" text field:\n' +
                '- Type 40 characters\n' +
                'Note: avoid using any symbol from <,>,& or =".\n' +
                'Save changes.'
        then: 'Verify Security Answer value with 40 characters is saved. Security Answer value shall be 1 to 40 characters'
        then: 'Verify Clinic Staff User Accounts Screen is displayed. ' +
                'If validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user and fill in "Enter Your Current Password" text field with password equal to current user password that you saved before.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Update Security Answer text field:\n' +
                '- Enter character with a space before and after (e.g.: “ test ”).\n' +
                'Note: avoid using any symbol from "<, >, & or =".\n' +
                'Save changes.'
        then: 'Verify Security Answer value was saved without any leading spaces or trailing spaces. ' +
                'Security Answer shall not allow any leading spaces or trailing spaces\n' +
                'If validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'
        then: 'Verify that the system provides the capability to enter user information in [Clinic_Staff_User_Admin_Priviledge]. ' +
                'The system shall provide the capability to enter the following [Clinic_Staff_User] information on Create New Staff User Window: [Clinic_Staff_User_Admin_Priviledge].'
        then: 'Verify [Clinic_Staff_User_LoginID] field is indicated as required field. Username value shall be required.\n' +
                'The system shall indicate that the following fields require user entry to create a new user: [Clinic_Staff_User_LoginID].'
        then: 'Verify [Clinic_Staff_User_First_Name] field is indicated as required field. ' +
                'The system shall indicate that the following fields require user entry to create a new user: [Clinic_Staff_User_First_Name].'
        then: 'Verify [Clinic_Staff_User_Last_Name] field is indicated as required field. ' +
                'The system shall indicate that the following fields require user entry to create a new user: [Clinic_Staff_User_Last_Name].'
        then: 'Verify [Clinic_Staff_User_Email] field is indicated as required field. ' +
                'The system shall indicate that the following fields require user entry to create a new user: [Clinic_Staff_User_Email].'
        then: 'Verify [Clinic_Staff_User_Password] field is indicated as required field. ' +
                'The system shall indicate that the following fields require user entry to create a new user: [Clinic_Staff_User_Password].'
        then: 'Verify [Clinic_Staff_User_Password_Confirm] field is indicated as required field. ' +
                'The system shall indicate that the following fields require user entry to create a new user:\n' +
                '[Clinic_Staff_User_Password_Confirm].'
        then: 'Verify that the [Enter Your Current Password] field is indicated as required field. ' +
                'The system shall require the admin to enter admin current password in order to create a new user.'
        then: 'Verify that the Create New Staff User screen displays the following text associated with the [Clinic_Staff_User_Admin_Privilege] setting. ' +
                'The Create New Staff User screen displays the following text associated with the [Clinic_Staff_User_Admin_Privilege] setting.\n' +
                'Note: This setting allows this user to access the Staff User Accounts features.'

        when: 'Enter all the required fields to create a new staff user and\n' +
                'click on [Cancel] button. '
        then: 'Verify that user is navigated to Clinic Staff User Accounts screen and new user is not created. ' +
                'A Cancel link, labeled as “Cancel”, shall abort any changes entered into the Create New Staff User Widow and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button.\n' +
                'Fill in all required fields:\n' +
                '- Username (e.g. NewUser11ds)\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Enter Your Current Password\n' +
                '- Assign Temporary Password that completely matching with Username (e.g. NewUser11ds)\n' +
                '- Re-enter Temporary Password that completely matching with Username (e.g. NewUser11ds)\n' +
                '- Security Question\n' +
                '- Security Answer\n' +
                'Note: avoid using any symbol from "<, >, & or =" \n' +
                'Save changes.'
        then: 'Verify new password is not set when Assign Temporary Password and Re-enter Temporary Password completely matches with Username. ' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in all required fields:\n' +
                '- Username (e.g. Username)\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Enter Your Current Password\n' +
                '- Assign Temporary Password that is a subset of Username (e.g. NewUsername_11f)\n' +
                '- Re-enter Temporary Password that is a subset of Username (e.g. NewUsername_11f)\n' +
                '- Security Question\n' +
                '- Security Answer\n' +
                'Note: avoid using any symbol from "<, >, & or =" \n' +
                'Save changes'
        then: 'Verify new password is not set when Temporary Password and Re-enter Temporary Password is a subset of Username. ' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in all required fields:\n' +
                '- Username (e.g. NewUsername)\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Enter Your Current Password\n' +
                '- Assign Temporary Password that completely matching with Username but some letters in upper case (e.g. NEWUsername_33f)\n' +
                '- Re-enter Temporary Password that completely matching with Username but some letters in upper case (e.g. NEWUsername_33f)\n' +
                '- Security Question\n' +
                '- Security Answer\n' +
                'Note: avoid using any symbol from "<, >, & or =" \n' +
                'Save changes.'
        then: 'Verify new password is not set when Temporary Password and Re-enter Temporary Password is a case insensitive in comparison with Username. ' +
                'Changes are not saved and error message is displayed.'

        when: 'Fill in all required fields:\n' +
                '- Username (e.g. SecondUsername)\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Enter Your Current Password\n' +
                '- Assign Temporary Password that not contain Username completely (e.g. SecondUsern_5f)\n' +
                '- Re-enter Temporary Password that not contain Username completely (e.g. SecondUsern_5f)\n' +
                '- Security Question\n' +
                '- Security Answer\n' +
                'Note: avoid using any symbol from "<, >, & or =" \n' +
                'Record the Password.\n' +
                'Save changes.'
        then: 'Verify new password is set when Username is not completely included into Temporary Password and Re-enter Temporary Password' +
                'Changes are saved and error message is NOT displayed.'

        when: 'Click on the [Create New User] button.\n' +
                'Tick the Administrative Privileges checkbox to be “ON”.\n' +
                'Fill out the next text fields:\n' +
                '- Username\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Enter Your Current Password\n' +
                '- Assign Temporary Password\n' +
                '- Re-enter Temporary Password\n' +
                '- Security Question\n' +
                '- Security Answer\n' +
                'Note: avoid using any symbol from "<, >, & or =" \n' +
                'Save changes.'
        then: 'Verify the system is navigating user to Clinic Staff User Accounts Screen. ' +
                'If the validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'
        then: 'Verify that new user is created and shown in the Users table. ' +
                'If the validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'
        then: 'Verify the system is navigating user to Clinic Staff User Accounts Screen, new user is created and shown in the Users table. ' +
                'If the validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button from Clinic Staff User Accounts Screen.\n' +
                'Do not fill any information. Click on [Save] button'
        then: 'Verify that the system performs field validation for each entered field and alert the user of any field failing. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.'

        when: 'Do not tick the Administrative Privileges checkbox.\n' +
                'Fill in next text fields on Create New User screen:\n' +
                'Note: Enter Username: The same value as for currently logged in user name. \n' +
                '- Username\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Enter Your Current Password\n' +
                '- Assign Temporary Password\n' +
                '- Re-enter Temporary Password\n' +
                '- Security Question\n' +
                '- Security Answer\n' +
                'Note: avoid using any symbol from "<, >, & or ="\n' +
                'Save changes.'
        then: 'Verify user with already existing username cannot be created. LoginID value shall be unique for all users in the system.'

        when: 'Enter unique value in Username text field.\n' +
                'Note: avoid using any symbol from "<, >, & or =".\n' +
                'Save changes.'
        then: 'Verify user is navigated to Clinic Staff User Accounts screen, new user is created and shown in table.' +
                'If the validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on [Create New User] button.\n' +
                'Enter all the required fields to create a New Staff User.\n' +
                'Update next text fields with valid value and “<” symbol (e.g. “test<”):\n' +
                '- Username\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Security Answer\n' +
                'Note:\n' +
                '1) user stays on Create New User screen after pressing [Save] button. \n' +
                '2) Message in red is shown near text field: “Input cannot contain "<, >, &, or =” (except User name).\n' +
                'Save changes.'
        then: 'Verify "Username", "First Name", "Last Name", "Email" and "Security Answer" cannot be saved with “<” symbol. ' +
                'The system shall perform field validation for each User Entry Field and alert the user of any User Entry Field failing to meet the validation criteria:\n' +
                'Invalid Characters: <,>,& and =\n' +
                'LoginID value shall not allow any spaces or special characters.'

        when: 'Update next text fields with valid value and “>” symbol (e.g. “t>est”):\n' +
                '- Username\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Security Answer\n' +
                'Note:\n' +
                '1) user stays on Create New User screen after pressing [Save] button. \n' +
                '2) message in red is shown near text field: “Input cannot contain "<, >, &, or =” (except User name).\n' +
                'Save changes.'
        then: 'Verify "Username", "First Name", "Last Name", "Email" and "Security Answer" cannot be saved with “>” symbol. ' +
                'The system shall perform field validation for each User Entry Field and alert the user of any User Entry Field failing to meet the validation criteria.\n' +
                'Invalid Characters: <,>,& and =\n' +
                'LoginID value shall not allow any spaces or special characters.'

        when: 'Update next text fields with valid value and “&” symbol (e.g. “&test”):\n' +
                '- Username\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Security Answer\n' +
                'Note:\n' +
                '1) user stays on Create New User screen after pressing [Save] button.\n' +
                '2) message in red is shown near text field: “Input cannot contain "<, >, &, or =” (except User name). \n' +
                'Save changes'
        then: 'Verify "Username", "First Name", "Last Name", "Email" and "Security Answer" cannot be saved with “&” symbol. ' +
                'The system shall perform field validation for each User Entry Field and alert the user of any User Entry Field failing to meet the validation criteria.\n' +
                'Invalid Characters: <,>,& and =\n' +
                'LoginID value shall not allow any spaces or special characters.'

        when: 'Update next text fields with valid value and “=” symbol (e.g. “=test”):\n' +
                '- Username\n' +
                '- First Name\n' +
                '- Last Name\n' +
                '- Email\n' +
                '- Security Answer\n' +
                'Note:\n' +
                '1) user stays on Create New User screen after pressing [Save] button.\n' +
                '2) message in red is shown near text field: “Input cannot contain "<, >, &, or =” (except User name). \n' +
                'Save changes.'
        then: 'Verify "Username", "First Name", "Last Name", "Email" and "Security Answer" cannot be saved with “=” symbol. ' +
                'The system shall perform field validation for each User Entry Field and alert the user of any User Entry Field failing to meet the validation criteria.\n' +
                'Invalid Characters: <,>,& and =\n' +
                'LoginID value shall not allow any spaces or special characters.'

        when: 'Update "Username" text field:\n' +
                '- Attempt to type more than 16 alphanumeric characters.'
        then: 'Verify Username value can not be more than 16 alphanumeric characters. ' +
                'User name value shall be from 4 to 16 case in-sensitive alphanumeric characters.'

        when: 'Update "Username" text field:\n' +
                '- Type 3 alphanumeric characters.\n' +
                'Note: update all the required fields avoid using any symbol from "<, >, & or =" \n' +
                'Save changes.'
        then: 'Verify Username value can not be less than 4 alphanumeric characters. ' +
                'User name value shall be from 4 to 16 case in-sensitive alphanumeric characters'

        when: 'Update "Username" text field:\n' +
                '- Leave text field empty.\n' +
                'Save changes.'
        then: 'Verify Username value is required. Value shall be required.'

        when: 'Update Username text field:\n' +
                '- Enter character with a space before and after (e.g.: “na me”).\n' +
                'Note: user stays on Create New User screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify Username value does not allow any spaces. Value shall not allow any spaces.'

        when: 'Update Username text field:\n' +
                '- Enter 4 case in-sensitive alphanumeric characters.\n' +
                'Save changes.'
        then: 'Verify new user is created with 4 case in-sensitive alphanumeric characters in Username. ' +
                'User name value shall be from 4 to 16 case in-sensitive alphanumeric characters.'
        then: 'Verify Clinic Staff User Accounts Screen is displayed. ' +
                'If the validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update Username text field:\n' +
                '- Enter 16 case in-sensitive alphanumeric characters.\n' +
                'Save changes.'
        then: 'Verify new user is created with 16 case in-sensitive alphanumeric characters in "Username" field. ' +
                'Username value shall be from 4 to 16 case in-sensitive alphanumeric characters'
        then: 'Verify Clinic Staff User Accounts Screen is displayed. ' +
                'If the validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update "First Name" text field:\n' +
                '- Attempt to type more than 40 alphanumeric characters.\n' +
                'Save changes.'
        then: 'Verify First Name value can not be more than 40 alphanumeric characters. ' +
                'First Name value shall be 1 to 40 alphanumeric characters.'

        when: 'Update "First Name" text field:\n' +
                '- Enter character with a space before and after (e.g.: “ name ”).\n' +
                'Note: avoid using any symbol from "<, >, & or ="\n' +
                'Save changes.\n' +
                'Open just created user, using [Open User] button.'
        then: 'Verify First Name value is saved without any leading spaces or trailing spaces. ' +
                'First name value shall not allow any leading spaces or trailing spaces.\n' +
                'If validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen'

        when: 'Click on [Cancel] button.'
        and: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update "First Name" text field:\n' +
                '- Leave text field empty\n' +
                'Save changes.'
        then: 'Verify First Name value is required. First Name value shall be required'

        when: 'Update "First Name" text field:\n' +
                '- Type 1 character.\n' +
                'Note: avoid using any symbol from "<, >, & or =".\n' +
                'Save changes.'
        then: 'Verify First Name value with 1 character is saved. First Name value shall be 1 to 40 characters.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update "First Name" text field:\n' +
                '- Type 40 characters\n' +
                'Note: avoid using any symbol from "<,>,& or =". \n' +
                'Save changes.'
        then: 'Verify First Name value with 40 characters is saved. First Name value shall be 1 to 40 characters.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update "Last Name" text field:\n' +
                '- Attempt to type more than 40 characters.'
        then: 'Verify First Name value with 40 characters is saved. First Name value shall be 1 to 40 characters.'

        when: 'Update "Last Name" text field:\n' +
                '- Enter character with a space before and after (e.g.: “ name ”).\n' +
                ' Note: avoid using any symbol from "<, >, & or =".\n' +
                'Save changes.\n' +
                'Open just created user using [Open User] button.\n' +
                'Click on [Cancel] button.'
        then: 'Verify Last Name value was saved without any leading spaces or trailing spaces. ' +
                'Last name value shall not allow any leading spaces or trailing spaces.\n' +
                'If validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on [Cancel] button.\n' +
                'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update Last Name text field:\n' +
                '- Leave text field empty.\n' +
                'Save changes.'
        then: 'Verify Last Name value is required. Last Name value shall be required.'

        when: 'Update "Last Name" text field:\n' +
                '- Type 1 character.\n' +
                'Note: avoid using any symbol from "<, >, & or =". \n' +
                'Save changes.'
        then: 'Verify Last Name value with 1 character is saved. Last Name value shall be 1 to 40 characters.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update Last Name textfield:\n' +
                '- Type 40 characters\n' +
                'Click on [Save] button.\n' +
                'Note: avoid using any symbol from <,>,& or ='
        then: 'Verify Last Name value with 40 characters is saved. Last Name value shall be 1 to 40 characters.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Update "Email" text field:\n' +
                '- Leave text field empty.\n' +
                'Try to save changes.'
        then: 'Verify Email value is required. Email value shall be required.'

        when: 'Update "Email" text field:\n' +
                '- Type valid email address with space between two characters.\n' +
                'Note:\n' +
                '1) avoid using any symbol from "<, >, & or ="\n' +
                '2) user stays on Create New User screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify Email value can not contain any spaces. Email value shall not contain any spaces and include the “@” and a “.” character.'

        when: 'Update "Email" text field:\n' +
                '- Type at least one character and “@” symbol.\n' +
                'Save changes.\n' +
                'Note:\n' +
                '1) avoid using any symbol from "<, >, & or =".\n' +
                '2) user stays on Create New User screen after pressing [Save] button. '
        then: 'Verify Email value includes the “.” character. Email value shall not contain any spaces and include the “@” and a “.” character.'

        when: 'Update "Email" text field:\n' +
                '- Type at least one character and “.” symbol.\n' +
                'Note:\n' +
                '1) avoid using any symbol from "<, >, & or =".\n' +
                '2) user stays on Create New User screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify Email value includes the “@” character. Email value shall not contain any spaces and include the “@” and a “.” character.'

        when: 'Update "Email" text field:\n' +
                '- Attempt to type email with more than 80 characters long.'
        then: 'Verify Email value can not be longer than 80 characters. Email value shall be 1 to 80 characters.'

        when: 'Update "Email" text field:\n' +
                '- Type valid email 80 characters long.\n' +
                'Note: avoid using any symbol from "<, >, & or =". \n' +
                'Save changes.'
        then: 'Verify Email value with 80 characters is saved. Email value shall be 1 to 80 characters'

        when: 'Click on the [Create New User] button.\n' +
                'Set "Enter Your Current Password" matching next conditions:\n' +
                '- Attempt to type more than 20 characters. '
        then: 'Verify Enter Your Current Password value cannot be more than 20 characters Password value shall be 8 to 20 case sensitive characters.'
        then: 'Verify entry fields for "Enter Your Current Password" mask each character with asterisk entered by instead of the entered character. ' +
                'Entry fields for Password shall mask each character entered by instead of the entered character.'

        when: 'Enter all the required fields to create a new staff user and set "Enter Your Current Password", ' +
                '"Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Leave "Enter Your Current Password" text field empty\n' +
                '- Fill in "Assign Temporary Password" and "Re-enter Temporary Password" with at least 8 characters matching a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Note: user stays on Create New Staff User screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify Enter Your Current Password value is required. The system shall require the admin to enter admin current password in order to create a new user\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Special Character'

        when: 'Set "Enter Your Current Password", "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Fill in "Enter Your Current Password" text field with password not equal to current user password\n' +
                '- Fill in "Assign Temporary Password" and "Re-enter Temporary Password" with at least 8 characters matching a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Note: user stays on Create New Staff User screen after pressing [Save] button.\n' +
                'Save changes.'
        then: 'Verify that the system displays a validation error “Incorrect current password”. The system shall notify ' +
                'the user if the entered admin password does not match the current admin password when creating a new user\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Save changes with filling "Enter Your Current Password" with password not equal to the current user password 4 times in a row'
        then: 'Verify if the user tries to change his password with using incorrect current password 5 times in a row, ' +
                'the system should log out the user. The system shall log out the user and navigate to Login if the user enters incorrect admin password for consecutively 5 times when creating a new user'

        when: 'Sign into the MMT-7340 application using Username from step ID 2957656 and enter user’s current password.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        and: 'Click on Clinic Settings link.\n' +
                'Click on Users link.\n' +
                'Click on the [Create New User] button.'
        and: 'Enter all the required fields to create a new staff user and fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Leave "Assign Temporary Password" text field empty.\n' +
                'Save changes.'
        then: 'Verify Assign Temporary Password value is required The system shall perform field validation for each entered field ' +
                'and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall be required'

        when: 'Enter all the required fields to create a new staff user and fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Leave "Re-enter Temporary Password" text field empty\n' +
                'Save changes.'
        then: 'Verify Re-enter Temporary Password value is required. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall be required'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Attempt to type more than 20 characters.'
        then: 'Verify "Assign Temporary Password" and "Re-enter Temporary Password" values cannot be more than 20 characters. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall be 8 to 20 case sensitive characters'
        then: 'Verify entry fields for "Assign Temporary Password" and "Re-enter Temporary Password" mask each character with asterisk instead of the entered character. ' +
                'Entry fields for Password shall mask each character entered by instead of the entered character.'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length equal to 7 characters\n' +
                '- Passwords should be equal\n' +
                'Save changes.\n' +
                'Note: User stays on Create New User screen after pressing [Save] button. '
        then: 'Verify Assign Temporary Password value cannot be less than 8 characters. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall be 8 to 20 case sensitive characters'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                'Save changes.\n' +
                'Note: User stays on Create New User screen after pressing [Save] button.'
        then: 'Verify that new user can not be created if Assign Temporary Password value consists only of 2 of the following character types: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Uppercase letter\n' +
                '• Number\n' +
                'Note: User stays on Create New User screen after pressing [Save] button.\n' +
                'Save changes.'
        then: 'Verify that new user can not be created if Assign Temporary Password value consists only of 2 of the following character types: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Uppercase letter\n' +
                '• Special Character\n' +
                'Note: User stays on Create New User screen after pressing [Save] button.\n' +
                'Save changes.'
        then: 'Verify that new user cannot be created if Assign Temporary Password value consists only of 2 of the following character types: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                'Note: User stays on Create New User screen after pressing [Save] button.\n' +
                'Save changes.'
        then: 'Verify that new user cannot be created if Assign Temporary Password value consists only of 2 of the following character types: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Lowercase letter\n' +
                '• Special Character\n' +
                'Note: User stays on Create New User screen after pressing [Save] button.\n' +
                'Save changes.'
        then: 'Verify that new user cannot be created if Assign Temporary Password value consists only of 2 of the following character types: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Note: User stays on Create New User screen after pressing [Save] button.\n' +
                'Save changes'
        then: 'Verify that new user cannot be created if Assign Temporary Password value consists only of 2 of the following character types: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Set "Re-enter Temporary Password" to be different from "Assign Temporary Password".\n' +
                'Save changes.'
        then: 'Verify that new user cannot be created if Re-enter Temporary Password does not match to the Assign Temporary Password. ' +
                'The system shall perform field validation for each entered field and alert the user of any field failing to meet the validation criteria on update.\n' +
                'Value shall be an exact match of the Clinic_Staff_User_Password'

        when: 'Fill in "Enter Your Current Password" text field with password equal to current user password.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Save changes. '
        then: 'Verify New Staff User with Assign Temporary Password equal to 8 characters is created. ' +
                'Password value shall be 8 to 20 case sensitive characters\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'
        then: 'Verify Clinic Staff User Accounts Screen is displayed. ' +
                'If validation passes, the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Click on the [Create New User] button.\n' +
                'Enter all the required fields to create a new staff user.\n' +
                'Set "Assign Temporary Password" and "Re-enter Temporary Password" matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Save changes.'
        then: 'Verify New Staff User with Assign Temporary Password length equal to 20 characters is created. ' +
                'Password value shall be 8 to 20 case sensitive characters.'
        then: 'Verify Clinic Staff User Accounts Screen is displayed.If validation passes, ' +
                'the system shall store the entered data in a new [Clinic_Staff_User] account and navigate to the Clinic Staff User Accounts Screen.'

        when: 'Log out from the system using Sign out link in the top right corner.\n' +
                'Sign into the MMT-7340 application using the user name and assign temporary password for newly created user from previous step.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        then: 'Verify that old password is expired and user is navigated to the Password Expired Screen. ' +
                'The system shall navigate to Password Expired screen after the new user logs in for the first time.'
        then: 'Verify the Password Expired Screen displays and titles “Password Expired”. ' +
                'The Password Expired Screen shall be displayed and titled “Password Expired”.'
        then: 'Verify if the user’s password has expired, the screen displays “Your password has expired. ' +
                'A new password must be created now. Please enter your new password.” text. If the user’s password has expired, the screen shall display the following text.\n' +
                '“Your password has expired. A new password must be created now. Please enter your new password.”'
        then: 'Verify that the user input fields for [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm] ' +
                'are provided on the Password Expired Screen. User input fields for the following [Clinic_Staff_User] elements ' +
                'shall be provided on the Password Expired Screen:\n' +
                '[Clinic_Staff_User_Password] labeled as “New Password”\n' +
                '[Clinic_Staff_User_Password_Confirm] labeled as “Retype New Password”'
        then: 'Verify that the Password Expired Screen displays only the Medtronic Logo and CareLink Logo of the Common Screen Header Elements. ' +
                'The Password Expired Screen shall display only the Medtronic Logo and CareLink Logo of the Common Screen Header Elements.'
        then: 'Verify that the Password Expired Screen includes the following Common Screen Footer Elements: ' +
                'User Guide, Training (if locale is US), Terms of Use, Privacy Statement, Contact Us. ' +
                'The Password Expired Screen shall include the following Common Screen Footer Elements: ' +
                'User Guide, Training (if locale is US), Terms of Use, Privacy Statement, Contact Us.'
        then: 'Verify that a Home link, labeled as “Home" and situated to the left of the Terms of Use link. ' +
                'A Home link, labeled as “Home”, shall be to the left of the Terms of Use link'

        when: 'Navigate to the Home screen.'
        then: 'Verify when a Home link pressed, system terminates the current user session and navigate to the Login Screen. ' +
                'A Home link, labeled as “Home”, shall be to the left of the Terms of Use link, and when pressed, shall terminate the current user session and navigate to the Login Screen'

        when: 'Try to sign into the MMT-7340 application using the assign temporary password for newly created user.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link'
        then: 'Verify that user is navigated to the Password Expired Screen. ' +
                'The system shall navigate to Password Expired screen after the new user logs in for the first time.'

        when: 'Set "Retype New Password" matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text includes a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Leave "New Password" text field empty.\n' +
                'Save changes.'
        then: 'Verify New Password value is required. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall be required.'

        when: 'Set "New Password" matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text includes a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Leave "Retype New Password" text field empty.\n' +
                'Save changes.'
        then: 'Verify Retype New Password value is required. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall be required'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Attempt to type more than 20 characters.'
        then: 'Verify New Password and Retype New Password values cannot be more than 20 characters. ' +
                'Password value shall be 8 to 20 case sensitive characters'
        then: 'Verify entry fields for "New Password" and "Retype New Password" mask each character ' +
                'with asterisk instead of the entered character. Entry fields for Password shall mask each character entered by instead of the entered character'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length equal to 7 characters\n' +
                '- Passwords should be equal\n' +
                '- Password should be different from Assign Temporary Password\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes. '
        then: 'Verify New Password value cannot be less than 8 characters. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall be 8 to 20 case sensitive characters'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify New Password value can not be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Uppercase letter\n' +
                '• Number\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify New Password value can not be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Uppercase letter\n' +
                '• Special character\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes. '
        then: 'Verify New Password value can not be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes.'
        then: 'Verify New Password value can not be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Lowercase letter\n' +
                '• Special character\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes. '
        then: 'Verify New Password value can not be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length between 8 and 20 characters\n' +
                '- Text shall include only next character types:\n' +
                '• Number\n' +
                'Note: User stays on Password Expired screen after pressing [Save] button. \n' +
                'Save changes'
        then: 'Verify New Password value can not be set if only 2 of the following character types are present: ' +
                'Uppercase letter, Lowercase letter, Number, Special Character. A Save Button, labeled as “Save”, ' +
                'shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'

        when: 'Set "New Password" matching next conditions:\n' +
                '- Length equal to 20 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Set "Retype New Password" to be different from "New Password".\n' +
                'Save changes.'
        then: 'Verify "New Password" can not be updated if "Retype New Password" does not match "New Password". ' +
                'A Save Button, labeled as “Save”, shall validate the [Clinic_Staff_User_Password] and [Clinic_Staff_User_Password_Confirm].\n' +
                'Value shall be an exact match of the Clinic_Staff_User_Password'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Equal to "Assign Temporary Password" defined for this user\n' +
                'Save changes.'
        then: 'Verify New Password cannot be equal to Assign Temporary Password defined for new user ' +
                'If validation passes and the [Clinic_Staff_User_Password] is not different from the saved [Clinic_Staff_User_Password], ' +
                'the system shall display an error message indicating the passwords must be different.'

        when: 'Set "New Password" and "Retype New Password" matching next conditions:\n' +
                '- Length equal to 8 characters\n' +
                '- Text shall include a minimum of three of the following character types:\n' +
                '• Uppercase letter\n' +
                '• Lowercase letter\n' +
                '• Number\n' +
                '• Special Character\n' +
                'Save changes'
        then: 'Verify "New Password" equal to 8 characters is set for new user. Password value shall be 8 to 20 case sensitive characters\n' +
                'Password value shall include a minimum of three of the following character types:\n' +
                'Uppercase letter\n' +
                'Lowercase letter\n' +
                'Number\n' +
                'Special Character'
        then: 'Verify that new user is redirected to the Home Screen. If validation passes and the [Clinic_Staff_User_Password] ' +
                'is different from the saved [Clinic_Staff_User_Password], the system shall save the [Clinic_Staff_User_Password] and navigate to the Home Screen.'

        when: 'Log out from the system using Sign out link in the top right corner.\n' +
                'Try to sign into the MMT-7340 application using the user name and assign temporary password of newly created user.'
        then: 'Verify the user is redirected to Invalid Login Screen when Assign Temporary password is already changed to New password. ' +
                'If the user name and password fail to match a valid active user account, the system shall navigate to the Incorrect Login Screen.'

        when: 'Sign into the MMT-7340 application using the user name and new password of newly created user.\n' +
                'If MFA Instruction Screen titled Two-Factor Authentication appears, click on Postpone link.'
        then: 'Verify the user has to change assign temporary password only once.' +
                'User is redirected to Home screen'
        then: 'End of test.'
    }

//    def 'User Login' () {
//        when: 'Open software under test'
//        signInPage = browsers.to SignInPage
//        then: 'Create a new patient and login using that User ID and Password'
//        signInPage.enterUsername("Akilaa")
//        signInPage.enterPassword("Test1234")
//        signInPage.clickOnSignIn()
//    }
//    def 'Clinic Settings Tab' () {
//        when: 'Navigate to clinic settings'
//        clinicSettingsPage = browsers.at ClinicSettingsPage
//        clinicSettingsPage.clickOnClinicSettings()
//        then: 'Verify that this section contains a Clinic Report Settings option, labeled as Report Settings'
//        //clinicSettingsPage.assertReportSettingsText('Report Settings')
//        then: 'Verify that this section contains a Clinic Staff User Accounts option, labeled as Users'
//        clinicSettingsPage.assertUsersText('Users')
//        then: 'Verify that this section contains a Clinic Information option labeled as Clinic Information'
//        clinicSettingsPage.assertInfoText('Clinic Information')
//    }
//    def 'Report Settings' (){
//        when:'Click on the report settings option'
//        clinicSettingsPage = browsers.at ClinicSettingsPage
//        then: 'Verify the screen navigates to Clinic Report Screen'
//    }
//    def 'Users' (){
//        when:'Navigate to Users option'
//        usersPage = browsers.at ClinicUsersPage
//        then: 'Verify the system shall provide a clinic staff user accounts screen'
//        usersPage.clickOnUsers()
//    }
//    def 'Select User' (){
//        when: 'At the Clinic staff user screen'
//
//        then: 'Verify that the user can be selected'
//        then: 'Verify that only one user can be selected'
//    }
//    def ' Create new User' (){
//        when: 'Click on create new user button'
//        usersPage.clickNewUser()
//        then: 'Verify that the system provides the create new staff user in a modal window'
//        usersPage.clickCancelNewUser()
//        then: 'Verify that the focus is only given to the create new staff user elements'
//        then: 'Verify that all other application elements may be visible, but are inactive'
//    }
//    def 'Users and Close Clinic Settings' () {
//        when: 'Navigate to Clinic Staff User Accounts Screen'
//        then: 'Verify that there is a Close Clinic Settings link on the screen'
//        assert usersPage.closeUsersDisplayed()
//        usersPage.clickOnCloseUsers()
//        then: 'Verify that the screen navigates to Home Screen'
//    }
//    def 'User Logout' () {
//        when:'A user is logged into carelink'
//        then: 'User should be able to sign out from carelink'
//        assert signInPage.signoutButtonDisplayed()
//        signInPage.clickOnSignOutLink()
//    }
}