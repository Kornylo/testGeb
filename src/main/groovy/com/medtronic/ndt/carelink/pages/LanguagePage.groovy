package com.medtronic.ndt.carelink.pages

import com.medtronic.ndt.carelink.pages.clinicwizard.ClinicLocalePage
import com.medtronic.ndt.carelink.pages.components.DropdownModule
import com.medtronic.ndt.carelink.pages.dashboard.SignInPage
import com.medtronic.ndt.carelink.util.EnvUtil
import geb.Page
import geb.navigator.Navigator
import groovy.util.logging.Slf4j
import org.openqa.selenium.StaleElementReferenceException

@Slf4j
class LanguagePage extends Page {
    static ClinicLocalePage clinicLocalePage
    static SignInPage signInPage
    static at = {
        selectBtn.displayed || countryDropDown.displayed || languageDropDown.displayed
    }

    static content = {
        selectBtn(required: false) { $(id: 'localeform:selectbtn') }
        countryDropDown(required: false) { $('select', id: 'localeform:selectcountry').module(DropdownModule) }
        languageDropDown(required: false) { $('select', id: 'localeform:selectlanguage').module(DropdownModule) }
        selectCountryDropdown(required: false) { $('select', id: "localeform:selectcountry") }
        selectLanguageDropdown(required: false) { $('select', id: "localeform:selectlanguage") }
        languageLink(required: false) { $(id: "location") }
        languageIcon(required: false) { $(id: "lang") }
        footer(required: false) { $(id: "footer_submenu_r") }
        medtronicLogo(required: false) { $("img", class: "mdtlogo") }
        envisionLogo(required: false) { $("h1", id: "envision") }
    }
    def static listCountry = ["Albania": "English", "Algérie": "français", "Argentina": "español", "Australia": "English", "Bahrain": "English", "Bangladesh": "English", "Belgique": "français", "Bolivia": "English", "Bosnia and Herzegovina": "English", "Botswana": "English", "Brazil": "English", "Cameroun": "français", "Canada": "English", "Chile": "español", "Colombia": "español", "Costa Rica": "español", "Cyprus": "English", "Côte d'Ivoire": "français", "Danmark": "Dansk", "Deutschland": "Deutsch", "Ecuador": "español", "El Salvador": "español", "España": "español", "Estonia": "English", "Ethiopia": "English", "France": "français", "Guatemala": "español", "Honduras": "español", "Hong Kong": "English", "Hrvatska": "hrvatski", "India": "English", "Indonesia": "English", "Iraq": "English", "Ireland": "English", "Italia": "italiano", "Kenya": "English", "Kosovo": "English", "Latvia": "English", "Lebanon": "English", "Liechtenstein": "Deutsch", "Lietuva": "Lietuvių", "Luxembourg": "English", "Macedonia": "English", "Magyarország": "magyar", "Malaysia": "English", "Malta": "English", "Mauritius": "English", "Montenegro": "English", "Morocco": "English", "México": "español", "Namibia": "English", "Nederland": "Nederlands", "New Zealand": "English", "Nicaragua": "español", "Nigeria": "English", "Norge": "norsk", "Pakistan": "English", "Panamá": "español", "Paraguay": "español", "Perú": "español", "Philippines": "English", "Polska": "polski", "Portugal": "português", "Qatar": "English", "República Dominicana": "español", "România": "română", "Schweiz": "Deutsch", "Serbia": "Српски", "Singapore": "English", "Slovenija": "Slovenščina", "Slovenská republika": "Slovenčina", "South Africa": "English", "South Korea": "English", "Sri Lanka": "English", "Suomi": "suomi", "Sverige": "svenska", "Sénégal": "français", "Taiwan": "English", "Tanzania": "English", "Tunisia": "English", "Türkiye": "Türkçe", "Uganda": "English", "United Kingdom": "English", "United States": "English", "Uruguay": "español", "Venezuela": "español", "Vietnam": "English", "Zimbabwe": "English", "Österreich": "Deutsch", "Česká republika": "čeština", "Ελλάδα": "Ελληνικά", "Азербайджан": "русский", "Беларусь": "русский", "България": "български", "Казахстан": "русский", "Россия": "русский", "Узбекистан": "русский", "Украина": "русский", "ישראל": "עברית", "الأردن": "العربية", "الإمارات": "العربية", "السعودية": "العربية", "السودان": "العربية", "الكويت": "العربية", "سلطنة عمان": "العربية", "سوريا": "العربية", "ليبيا": "العربية", "مصر": "العربية", "ประเทศไทย": "ไทย", "日本": "日本語"]
    def static listCountryLetter = ["AL", "DZ", "AR", "AU", "BH", "BD", "BE", "BO", "BA", "BW", "BR", "CM", "CA", "CL", "CO", "CR", "CY", "CI", "DK", "DE", "EC", "SV", "ES", "EE", "ET", "FR", "GT", "HN", "HK", "HR", "IN", "ID", "IQ", "IE", "IT", "KE", "XK", "LV", "LB", "LI", "LT", "LU", "MK", "HU", "MY", "MT", "MU", "ME", "MA", "MX", "NA", "NL", "NZ", "NI", "NG", "NO", "PK", "PA", "PY", "PE", "PH", "PL", "PT", "QA", "DO", "RO", "CH", "RS", "SG", "SI", "SK", "ZA", "KR", "LK", "FI", "SE", "SN", "TW", "TZ", "TN", "TR", "UG", "GB", "US", "UY", "VE", "VN", "ZW", "AT", "CZ", "GR", "AZ", "BY", "BG", "KZ", "RU", "UZ", "UA", "IL", "JO", "AE", "SA", "SD", "KW", "OM", "SY", "LY", "EG", "TH", "JP"]
    def static listLang = ["en", "fr", "es", "en", "en", "en", "fr", "en", "en", "en", "en", "fr", "en", "es", "es", "es", "en", "fr", "da", "de", "es", "es", "es", "en", "en", "fr", "es", "es", "en", "hr", "en", "en", "en", "en", "it", "en", "en", "en", "en", "de", "lt", "en", "en", "hu", "en", "en", "en", "en", "en", "es", "en", "nl", "en", "es", "en", "no", "en", "es", "es", "es", "en", "pl", "pt", "en", "es", "ro", "de", "sr", "en", "sl", "sk", "en", "en", "en", "fi", "sv", "fr", "en", "en", "en", "tr", "en", "en", "en", "es", "es", "en", "en", "de", "cs", "el", "ru", "ru", "bg", "ru", "ru", "ru", "ru", "iw", "ar", "ar", "ar", "ar", "ar", "ar", "ar", "ar", "ar", "th", "ja"]

    static String setCountry(){
        def index = listCountryLetter.indexOf(EnvUtil.COUNTRY)
        def countryName = listCountry.entrySet().toList().get(index).key
        println(countryName)
        return countryName
    }

    void verifyElements(String text, String value) {
        waitFor { $(".mdtlogo").displayed }
        waitFor { $(".iprologo").displayed }
        waitFor { countryDropDown.displayed }
        waitFor { languageDropDown.displayed }
        waitFor { selectBtn.displayed }
        assert browser.getCurrentUrl().contains("locale.jsf")
        assert !footer.displayed
        if (value == "en") {
            assert $(id: "localeform").find("h1").text() == text
            assert title == text
        }
    }

    void checkAllLocales() {
        List<Navigator> countryDropdownOptions = selectCountryDropdown.find("option").collect()
        // 110 languages are supported in CareLink (OUS), add 1 because of the "Select Country" option.
        assert countryDropdownOptions.size() == 111
        listCountry.eachWithIndex { entry, i ->
            String country = entry.key
            println "$i Country is $country ${listCountryLetter[i]}"
            println "___________________________"
            // The first option in the dropdown is "Select Country", so add a +1 offset to skip it.
            assert countryDropdownOptions[i + 1].text() == country
            assert countryDropdownOptions[i + 1].value() == listCountryLetter[i]
        }
    }

    void selectCountryAndLanguageAll() {
        listCountry.eachWithIndex { entry, i ->
            String country = entry.key
            String defaultLang = entry.value
            //setup country
            selectCountryDropdown.value(country)
            waitFor {
                try {
                    selectLanguageDropdown.displayed
                } catch (StaleElementReferenceException staleElementEx) {
                    false
                }
            }

            //get data from UI for console output (lists)
            List<String> langValue = selectLanguageDropdown.find("option")*.value()
            List<String> langText = selectLanguageDropdown.find("option")*.text()

            println "$i Country is $country and default language is $defaultLang ${listLang[i]}"
            println "-- Available languages: $langText ($langValue)"
            println "------------------------------------------------------------"

            assert selectCountryDropdown.value() == listCountryLetter[i]
            assert langText.first() == defaultLang
        }
    }

    void selectCountry(String country) {
        waitFor 15,{selectCountryDropdown
        selectCountryDropdown.value(country) }
    }

    void selectLang(String lang) {
        selectLanguageDropdown.value(lang)
        assert selectLanguageDropdown.value() == "en"
    }

    void verifySelectButton() {
        assert selectBtn.displayed
    }

    void buttonSelectClick() {
        waitFor { selectBtn.displayed }
        selectBtn.click()
        sleep(3000)
        waitFor {browser.getCurrentUrl().contains("hcp/login.jsf")}
    }

    void verifyLogo() {
        waitFor { medtronicLogo.displayed }
        assert medtronicLogo.css('float') == 'left'
        if (envisionLogo.displayed) {
            assert envisionLogo.css('float') == 'right'
        } else {
            assert $("div.header_er > img.iprologo").displayed}
        }
}