package com.medtronic.ndt.carelink.util

import geb.Page
import org.openqa.selenium.By

class Validation extends Page {
    private static List<String> invalidChars = ["#", "!", "@", "%", "^", "&"]
    static at = { wrapper.displayed }
    static content = {
        wrapper(required: false) { $ "div.wrapper" }
    }

    public boolean errorClinicName() {
        $(By.id("enroll_clinic:clinicnameerr")).text() == ("Clinic name is required")
    }

    public boolean errorPostalCode() {
        $(By.id("enroll_clinic:ziperr")).text() == ("Clinic zip is required")
    }

    public boolean errorPhone() {
        $(By.id("enroll_clinic:phoneerr")).text() == ("Clinic phone is required")
    }

    public boolean usernamError() {
        $(By.id("userform:usernameerror")).text() == ("User Name is required")
    }

    public boolean usernamErrorCharacters() {
        $(By.id("userform:usernameerror")).text() == ("Username cannot contain spaces or special characters")
    }

    public boolean usernamExistsError() {
        $(By.id("userform:usernameerror")).text() == ("Username already exists")
    }

    public boolean firstNameError() {
        $(By.id("enroll_clinic:phoneerr")).text() == ("User Name is required")
    }

    public boolean lastNameError() {
        $(By.id("userform:lastNameerror")).text() == ("Last Name is required")
    }

    public boolean emailError() {
        $(By.id("userform:emailerror")).text() == ("Email is required")
    }

    public boolean invalidEemailError() {
        $(By.id("userform:emailerror")).text() == ("Invalid email id")
    }

    public boolean passwordConfirmError() {
        $(By.id("userform:passwordConfirmerror")).text() == ("Password should be a minimum of 8 characters")
    }

    public boolean passwordNotEqualError() {
        $(By.id("userform:passwordConfirmerror")).text() == ("Passwords are not equal")
    }

    public boolean passwordСharacterError() {
        $(By.id("userform:passwordConfirmerror")).text() == ("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
    }

    public boolean passwordConfirmCharacterError() {
        $(By.id("userform:passwordConfirmerror")).text() == ("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
    }

    public boolean answerError() {
        $(By.id("userform:answererror")).text() == ("Answer to the security question is required")
    }

    public boolean newPatientFirstNameLastName() {
        $(By.cssSelector("div > span")).text() == ("Please enter both First Name and Last Name, or leave both blank.")
    }

    public boolean invalidDateofBirth() {
        $(By.cssSelector("form_error")).text() == ("Invalid Date of Birth.")
    }

    public boolean diabetesTypeError() {
        $(By.id("form_error")).text() == ("Please select a Diabetes Type.")
    }

    public boolean therapyTypeError() {
        $(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Other'])[2]/following::span[1]"))*.text() == ("Please select a Therapy Type.")
    }

    public boolean newPatientIDError() {
        $(By.cssSelector("div > span")).text() == ("Please enter a Patient ID and/or First Name and Last Name.")
    }

    public boolean firstNameValidationMessage() {
        $(By.cssSelector("validationmessage")).text() == ('First name is required.')
    }

    public boolean lastNameValidationMessage() {
        $(By.cssSelector("validationmessage")).text() == ('Last name is required.')
    }

    public boolean currentPasswordErrorMyInfo() {
        $(By.id("userform:currentPassworderror")).text() == ("Password should be a minimum of 8 characters")
    }

    public boolean incorrectPasswordErrorMyInfo() {
        $(By.id("userform:currentPassworderror")).text() == ("Incorrect current password")
    }

    public boolean passwordError() {
        $(By.id("userform:passworderror")).text() == ("Password should be a minimum of 8 characters")
    }

    public boolean passwordConfirmErrorMyInfo() {
        $(By.id("userform:passwordConfirmerror")).text() == ("Password should be a minimum of 8 characters")
    }

    public boolean clinicianEditErrorMsg() {
        $(By.cssSelector("clinician_edit_error_msg")).text() == ("Username is required.")
    }
    /*validation Password* - Create new user, Open user, My Info*/

    public void validationPassword() {
        List<String> passwordCheck = ["<", ">", "&", "=", " ", "qazwsx","QAZWSX", "12341234","Test123+"]
        String currentPassword = ""
        String assignTemporaryPassword = ""
        String reenterTemporaryPassword = ""
        for (String invalid : passwordCheck) {
            sleep(3000)
            $(By.id("userform:currentpassword")).value(currentPassword + invalid)
            $(By.id("userform:password")).value(assignTemporaryPassword + invalid)
            $(By.id("userform:confirmpassword")).value(reenterTemporaryPassword + invalid)
            sleep(500)
            $(By.id("userform:save")).click()
            sleep(4)
            String currentPassworderror = $(By.id("span#userform:currentPassworderror")).value("Incorrect current password")
            String passworderror = $(By.id("span#userform:passworderror")).value("Password should be a minimum of 8 characters")
            String passwordConfirmerror = $(By.id("span#userform:passwordConfirmerror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (passworderror.equals("Password should be a minimum of 8 characters")) {
                currentPassworderror.equals("Incorrect current password")
                passwordConfirmerror.equals("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
                $(By.id("span#userform:currentPassworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.").displayed
                $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.").displayed
                $(By.id("span#userform:passwordConfirmerror")).value("Passwords are not equal").displayed
            } else {
                println("Assept")
            }
        }

    }
    /*Security Answer**/

    public void securityAnswerValidation() {
        List<String> securityAnswer = ["", "<>+"]
        String answer = " "
        for (String invalid : securityAnswer) {
            sleep(3000)
            $(By.id("userform:answer")).value(securityAnswer + invalid)
            String elementval = $(By.id("userform:answer")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.cssSelector(" clinician_edit_securty_err")).value("Security answer is required.")
            if (alertMessage.equals("Security answer is required.")) {
                System.out.println("Error displayed")
            } else {
                $(By.cssSelector("clinician_edit_securty_err")).value("Input cannot contain <, >, &, or =")
            }

        }

    }

    /*Enter Your Current Password**/
    /*can used in My info page, Create new user, User Information */
    public  void currentPasswordValidation() {
        List<String> invalidCurrentPassword = ["<", ">", "&", "=", " ", "qazwsx","QAZWSX", "12341234","Test123+"]
        String password = ""
        for (String invalid : invalidCurrentPassword) {
            $(By.id("userform:currentpassword")).value(password + invalid)
            sleep(5000)
            $(By.id("userform:save")).click()
            String alertMessage = $(By.id("span#userform:currentPassworderror")).value("Password should be a minimum of 8 characters")
            if (alertMessage.equals("Password should be a minimum of 8 characters")) {
                System.out.println("Password should be a minimum of 8 characters") ;
            } else {
                System.out.println("Accepted")
            }
        }
    }
    /*Re-enter Temporary Password Validation*/
    /*can used in My info page, Create new user, User Information */
    public void reenterTemporaryPasswordValidation() {
        List<String> invalidReenterTemporaryPassword = ["<", ">", "&", "=", " ", "qazwsx","QAZWSX", "12341234","Test123+"]
        String reenterPassword = ""
        for (String invalid : invalidReenterTemporaryPassword) {
            $(By.id("userform:confirmpassword")).value(reenterPassword + invalid)
            sleep(3000)
            String elementval = $(By.id("userform:confirmpassword")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click() ;
            String alertMessage = $(By.id("userform:confirmpassword")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.") ;
            if (alertMessage.equals("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")) {
                System.out.println("Password error displayed") ;
            } else {
                $(By.id("userform:confirmpassword")).value("Password should be a minimum of 8 characters")
            }
        }
    }


    /*Temporary Password Мalidation*/
    /*can used in My info page, Create new user, User Information */
    public void temporaryPasswordМalidation() {
        List<String> invalidTemporaryPassword = ["<", ">", "&", "=", " ", "qazwsx","QAZWSX", "12341234","Test123+"]
        String temporaryPassword = ""
        for (String invalid : invalidTemporaryPassword) {
            sleep(3000)
            $(By.id("userform:password")).value(temporaryPassword + invalid)
            String elementval = $(By.id("userform:password")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (alertMessage.equals("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")) {
                System.out.println("Password error displayed")
            } else {
                $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            }
        }
    }


    /*Enter Your Current Password**/
    /* public  void currentPasswordValidation() {
         List<String> invalidCurrentPassword = [""]
         String password = "Maluy013+"
         for (String invalid : invalidCurrentPassword) {
             sleep(3000)
             $(By.id("userform:currentpassword")).value(password + invalid)
             sleep(500)
             $(By.id("userform:save")).click()
             String alertMessage = $(By.cssSelector("userform:currentPassworderror")).value("Password should be a minimum of 8 characters")
             if (alertMessage.equals("Password should be a minimum of 8 characters")) {
                 System.out.println("Password should be a minimum of 8 characters") ;
             } else {
                 System.out.println("Accepted")
             }
         }
     }*/

    public void passwordConfirm() {
        List<String> invalidPasswordConfirm = ["<", ">", "&", "=", " ", "qazwsx","QAZWSX", "12341234","Test123+"]
        String temporaryPassword = ""
        for (String invalid : invalidPasswordConfirm) {
            sleep(3000)
            $(By.id("userform:passwordConfirm")).value(temporaryPassword + invalid)
            String elementval = $(By.id("userform:passwordConfirm")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (alertMessage.equals("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")) {
                System.out.println("Password error displayed")
            } else {
                $(By.id("span#userform:passwordConfirm")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            }
        }
    }

    /*Email*/

    public void emailValidation() {
        List<String> invalidEmail = ["<", ">", "&", "=", " "]
        String email = "ddd"
        for (String invalid : invalidEmail) {
            sleep(300)
            $(By.id("userform:email")).value(email + invalid + "gmail.com")
            String elementval = $(By.id("userform:email")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Invalid email id")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =") ;
            } else {
                System.out.println("Error displayed")
            }

        }
    }
    /*Last Name*/

    public void lastNameValidation() {
        List<String> invalidLastName = ["<", ">", "&", "="]
        String lastName = "ddd"
        for (String invalid : invalidLastName) {
            sleep(3000)
            $(By.id("userform:lastname")).value(lastName + invalid)
            String elementval = $(By.id("userform:lastname")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Input cannot contain <, >, &, or =")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted")
            }
        }
    }
    /*First Name*/

    public void firstNameValidation() {
        List<String> invalidFirstName = ["<", ">", "&", "="]
        String firstName = "ddd"
        for (String invalid : invalidFirstName) {
            sleep(3000)
            $(By.id("userform:firstname")).value(firstName + invalid)
            String elementval = $(By.id("userform:firstname")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Input cannot contain <, >, &, or =")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted")
            }
        }
    }

    public void usernameValidation() {
        List<String> invalidChars = ["#", "!", "@", "%", "^", "&"]
        String username = "ddd"
        for (String invalid : invalidChars) {
            sleep(3000)
            $(By.id("userform:username")).value(username + invalid)
            String elementval = $(By.id("userform:username")).getAttribute("value")
            println(elementval.length())
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:save")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Username cannot contain spaces or special characters")
            if (alertMessage.equals("Username cannot contain spaces or special characters")) {
                System.out.println("Username cannot contain spaces or special characters")
            } else {
                System.out.println("Accepted")
            }
        }
    }

    public void physicianName() {
        List<String> physicianNameInvalid = ["<", ">", "&", "=", " "]
        String physicianName = "ddd"
        for (String invalid : physicianNameInvalid) {
            sleep(3000)
            $(By.id("patient_details:email")).value(physicianName + invalid)
            String elementval = $(By.id("patient_details:email")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("patient_details:saveBtn")).click() ;
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Invalid email id")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =") ;
            } else {
                System.out.println("Accepted")
            }

        }
    }

    public void patientEmail() {
        List<String> patientEmailInvalid = ["<", ">", "&", "=", " "]
        String patientEmail = "ddd"
        for (String invalid : patientEmailInvalid) {
            sleep(3000)
            $(By.id("patient_details:email")).value(patientEmail + invalid + "gmail.com")
            String elementval = $(By.id("patient_details:email")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("patient_details:saveBtn")).click() ;
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Invalid email id")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =") ;
            } else {
                System.out.println("Accepted")
            }

        }
    }

    public void patientID() {
        List<String> PatientIDInvalid = ["<", ">", "&", "=", " "]
        String patientID = "ddd"
        for (String invalid : PatientIDInvalid) {
            sleep(3000)
            $(By.id("patient_details:patientId")).value(patientID + invalid)
            String elementval = $(By.id("patient_details:patientId")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("patient_details:saveBtn")).click() ;
            String aMessage = $(By.cssSelector("form_error")).value("Input cannot contain <, >, &, or =") ;
            if (aMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted") ;
            }

        }
    }

    public void patientLastName() {
        List<String> patientLastNameInvalid = ["<", ">", "&", "=", " "]
        String patientLastName = "ddd"
        for (String invalid : patientLastNameInvalid) {
            sleep(3000)
            $(By.id("patient_details:lastName")).value(patientLastName + invalid)
            String elementval = $(By.id("patient_details:lastName")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("patient_details:saveBtn")).click() ;
            String aMessage = $(By.cssSelector("form_error")).value("Input cannot contain <, >, &, or =") ;
            if (aMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted") ;
            }

        }
    }

    public void patientFirsName() {
        List<String> firstName = ["<", ">", "&", "=", " "]
        String patientFirsName = "ddd"
        for (String invalid : firstName) {
            sleep(3000)
            $(By.id("patient_details:firstName")).value(patientFirsName + invalid)
            String elementval = $(By.id("patient_details:saveBtn")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("patient_details:saveBtn")).click() ;
            String aMessage = $(By.cssSelector("form_error")).value("Input cannot contain <, >, &, or =") ;
            if (aMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted") ;
            }

        }
    }

/* information about the clinic*/

    public void validationInformationClinic() {
        List<String> informationClinicInvalid = ["<", ">", "="]
        String informationClinic = "dmytrokornylo"
        for (String invalid : informationClinicInvalid) {
            sleep(3000)
            $(By.id("enroll_clinic:clinicname")).value(informationClinic + invalid)
            String elementval = $(By.id("enroll_clinic:clinicname")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("enroll_clinic:continue")).click() ;
            String alertMessage = $(By.id("span#enroll_clinic:clinicnameerr")).value("Input cannot contain <, >, or =")
            System.out.println(invalid)
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =") ;
            } else {
                $(By.id("span#enroll_clinic:clinicnameerr")).value("Clinic name is required")
            }

        }
    }

    public void validationPostalCode() {
        List<String> postalCodeInvalid = ["<", ">", "&", "="]
        String PostalCode = "4342"
        for (String invalid : postalCodeInvalid) {
            sleep(3000)
            $(By.id("enroll_clinic:zip")).value(PostalCode + invalid)
            String elementval = $(By.id("enroll_clinic:zip")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("enroll_clinic:continue")).click()
            String alertMessage = $(By.id("span#enroll_clinic:ziperr")).value("Input cannot contain <, >, &, or =")
            System.out.println(invalid)
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                $(By.id("span#enroll_clinic:ziperr")).value("Required field")
                System.out.println("Error displayed")
            }

        }
    }

    public void validationPhone() {
        List<String> PhoneInvalid = ["<", ">", "&", "="]
        String Phone = "04366532"
        for (String invalid : PhoneInvalid) {
            sleep(3000)
            $(By.id("enroll_clinic:phone")).value(Phone + PhoneInvalid)
            String elementval = $(By.id("enroll_clinic:phone")).getAttribute("value") ;
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("enroll_clinic:continue")).click() ;
            String alertMessage = $(By.id("span#enroll_clinic:phoneerr")).value("Input cannot contain <, >, &, or =")
            System.out.println(invalid)
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                $(By.id("span#enroll_clinic:phoneerr")).value("Clinic phone is required")
                System.out.println("Error displayed")
            }

        }
    }


    public void usernameAdminValidation() {
        List<String> invalidChars = ["#", "!", "@", "%", "^", "&"]
        String username = "ddd"
        for (String invalid : invalidChars) {
            sleep(3000)
            $(By.id("userform:username")).value(username + invalid)
            String elementval = $(By.id("userform:username")).getAttribute("value") ;
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Username cannot contain spaces or special characters")
            if (alertMessage.equals("Username cannot contain spaces or special characters")) {
                System.out.println("Username cannot contain spaces or special characters")
            } else {
                System.out.println("Accepted")
            }
        }
    }

    public void firstNameAdminValidation() {
        List<String> invalidFirstName = ["<", ">", "&", "="]
        String firstName = "ddd"
        for (String invalid : invalidFirstName) {
            sleep(3000)
            $(By.id("userform:firstName")).value(firstName + invalid)
            sleep(3000)
            String elementval = $(By.id("userform:firstName")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Input cannot contain <, >, &, or =")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted")
            }
        }
    }

    public void lastNameAdminValidation() {
        List<String> invalidLastName = ["<", ">", "&", "="]
        String lastName = "ddd"
        for (String invalid : invalidLastName) {
            sleep(3000)
            $(By.id("userform:lastName")).value(lastName + invalid)
            String elementval = $(By.id("userform:lastName")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Input cannot contain <, >, &, or =")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =")
            } else {
                System.out.println("Accepted")
            }
        }
    }

    public void emailAdminValidation() {
        List<String> invalidEmail = ["<", ">", "&", "=", " "]
        String email = "ddd"
        for (String invalid : invalidEmail) {
            sleep(300)
            $(By.id("userform:email")).value(email + invalid + "gmail.com")
            String elementval = $(By.id("userform:email")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.cssSelector("clinician_edit_error_msg")).value("Invalid email id")
            if (alertMessage.equals("Input cannot contain <, >, &, or =")) {
                System.out.println("Input cannot contain <, >, &, or =") ;
            } else {
                System.out.println("Error displayed")
            }

        }
    }
/*my info*/
    public void temporaryAdminPasswordМalidation() {
        List<String> invalidTemporaryPassword = ["@23452345234<"]
        String temporaryPassword = "+"
        for (String invalid : invalidTemporaryPassword) {
            sleep(3000)
            $(By.id("userform:password")).value(temporaryPassword + invalid)
            String elementval = $(By.id("userform:password")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (alertMessage.equals("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")) {
                System.out.println("Password error displayed")
            } else {
                $(By.id("span#userform:passworderror\"")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            }
        }
    }

    public void passwordAdminConfirm() {
        List<String> invalidPasswordConfirm = ["@23452345234<"]
        String temporaryPassword = "+"
        for (String invalid : invalidPasswordConfirm) {
            sleep(3000)
            $(By.id("userform:passwordConfirm")).value(temporaryPassword + invalid)
            String elementval = $(By.id("userform:passwordConfirm")).getAttribute("value")
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.id("span#userform:passworderror")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            if (alertMessage.equals("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")) {
                System.out.println("Password error displayed")
            } else {
                $(By.id("span#userform:passwordConfirm")).value("Password must contain at least 3 of the following categories: Number, Uppercase character, Lowercase character, Special character.")
            }
        }
    }

    public void securityAnswerAdminValidation() {
        List<String> securityAnswer = ["", "<>+"]
        String answer = " "
        for (String invalid : securityAnswer) {
            sleep(3000)
            $(By.id("userform:answer")).value(securityAnswer + invalid)
            sleep(500)
            String elementval = $(By.id("userform:answer")).getAttribute("value") ;
            if (elementval.length() <= 20) {println("ok")} else {println("error")}
            $(By.id("userform:submit")).click()
            String alertMessage = $(By.cssSelector(" clinician_edit_securty_err")).value("Security answer is required.")
            if (alertMessage.equals("Security answer is required.")) {
                System.out.println("Error displayed")
            } else {
                $(By.cssSelector("clinician_edit_securty_err")).value("Input cannot contain <, >, &, or =")
            }

        }

    }

    /*forgot Password*/
    void validationForgotPassword() {
        $(By.cssSelector("#user_log > form > dl > dd:nth-child(4) > a")).click()
        Set<String> handles = driver.getWindowHandles()
        Iterator<String> it = handles.iterator()
        while (it.hasNext()) {
            String newwin = it.next()
            driver.switchTo().window(newwin)
        }
        List<String> filledData = ["", "@234<", "11111+", ">><<??", "Kornylo>>","dsds@dsd"]
        String exampleForInput = ""
        for (String invalid : filledData) {
            waitFor {$("#reminderform > h1").displayed}
            $(By.id("reminderform:username")).value(exampleForInput + invalid)
            $(By.id("reminderform:email")).value(exampleForInput + invalid)
            fieldLengthVerifying("reminderform:username")
            $(By.name("reminderform:j_id_id23")).click()
            forgotUserNameErrorMas("span#reminderform:usernameerr", "input#reminderform:emailerror", "Username is required.")
            driver.navigate().refresh()

        }
    }

    /* Forgot UserName*/
    void validationForgotUserName() {
        $(By.cssSelector("#user_log > form > dl > dd:nth-child(2) > a")).click()
        Set<String> handles = driver.getWindowHandles()
        Iterator<String> it = handles.iterator()
        while (it.hasNext()) {
            String newwin = it.next()
            driver.switchTo().window(newwin)
        }
        List<String> filledData = ["", "@234<", "11111+", ">><<??", "Kornylo>>","s@dsd"]
        String exampleForInput = ""
        for (String invalid : filledData) {
            waitFor {$("#reminderform > h1").displayed}
            $(By.id("reminderform:lastname")).value(exampleForInput + invalid)
            $(By.id("reminderform:email")).value(exampleForInput + invalid)
            fieldLengthVerifying("reminderform:lastname")
            $(By.name("reminderform:j_id_id23")).click()
            forgotUserNameErrorMas("reminderform:lastnameer", "input#reminderform:emailerror", "Last name is required")
            driver.navigate().refresh()

        }
    }

    void forgotUserNameErrorMas(String lastNameORUserNameID, String emailID, String errorMas) {
        if ($(By.id(lastNameORUserNameID)).value(errorMas).displayed) {
            println("Username is required.")
        } else {
            $(By.id(emailID)).value("Email is required").isDisplayed()
            println("Email error displayed")
        }
    }

    void fieldLengthVerifying(String locator) {
        String elementval = $(By.id(locator)).getAttribute("value")
        if (elementval.length() <= 20) {
            println("ok")
        } else {
            println("error")

        }
    }
/* Contact Us form */
    void validationContactUs() {
        $(By.cssSelector("#footer_submenu_r > li.mono > a")).click()
        Set<String> handles = driver.getWindowHandles()
        Iterator<String> it = handles.iterator()
        while (it.hasNext()) {
            String newwin = it.next()
            driver.switchTo().window(newwin)
        }
        List<String> filledData = ["@234<", "11111+", ">><<??", "Kornylo>>","s@dsd"]
        String exampleForInput = "korn"
        for (String invalid : filledData) {
            waitFor {$("#contact_us > h1").displayed}
            $(By.id("contact_us:username")).value(exampleForInput + invalid)
            $(By.id("contact_us:clinicname")).value(exampleForInput + invalid)
            $(By.id("contact_us:firstname")).value(exampleForInput + invalid)
            $(By.id("contact_us:lastname")).value(exampleForInput + invalid)
            $(By.id("contact_us:email")).value(exampleForInput + invalid)
            $(By.id("contact_us:phone")).value(exampleForInput + invalid)
            $(By.id("contact_us:details")).value(exampleForInput + invalid)
            fieldLengthVerifying("contact_us:username")
            fieldLengthVerifying("contact_us:clinicname")
            fieldLengthVerifying("contact_us:firstname")
            fieldLengthVerifying("contact_us:lastname")
            fieldLengthVerifying("contact_us:email")
            fieldLengthVerifying("contact_us:phone")
            $(By.id("contact_us:submit")).click()
            contactUsErrorMas("reminderform:lastnameer", "span#contact_us:emailerror")
            driver.navigate().refresh()
        }
    }
    void contactUsErrorMas(String lastNameORUserNameID, String emailID ) {
        if ($(By.id(emailID)).value("Invalid email id").displayed) {
            println("Username is required.")
        } else {
            $(By.id(lastNameORUserNameID)).value("Email is required").isDisplayed()
            println("Email error displayed")
        }
    }
    def pageComplete() {
        js.('document.readyState') == 'complete'
    }
}


