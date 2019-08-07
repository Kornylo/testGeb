package com.medtronic.ndt.carelink.pages.local

class LocalParameters{

    static final Map<String, Map<String, String>> names
    static{
        names = new HashMap<>()
        Map<String, String> english = new HashMap<>()
        english.put("language", "English")
        english.put("title", "Change country/language")
        english.put("username", "Username")
        english.put("countryLanguage", "option[value=\"en\"]")
        names.put("en", english)

        Map<String, String> polski = new HashMap<>()
        polski.put("language", "polski")
        polski.put("title", "Zmień kraj/język")
        polski.put("username", "Nazwa użytkownika")
        polski.put("countryLanguage", "option[value=\"pl\"]")
        names.put("pl", polski)

        Map<String, String> france = new HashMap<>()
        france.put("language", "france")
        france.put("title", "Modifier le pays/la langue")
        france.put("username", "Nom d'utilisateur")
        france.put("countryLanguage", "option[value=\"fr\"]")
        names.put("fr", france)

        Map<String, String> português = new HashMap<>()
        português.put("language", "português")
        português.put("title", "Alterar país/idioma")
        português.put("username", "Nome de utilizador")
        português.put("countryLanguage", "option[value=\"pt\"]")
        names.put("pt", português)

        Map<String, String> español = new HashMap<>()
        español.put("language", "español")
        español.put("title", "Cambiar país/idioma")
        español.put("username", "Nombre de usuario")
        español.put("countryLanguage", "option[value=\"es\"]")
        names.put("es", español)

        Map<String, String> Ελληνικά = new HashMap<>()
        Ελληνικά.put("language", "Ελληνικά")
        Ελληνικά.put("title", "Αλλαγή χώρας/γλώσσας")
        Ελληνικά.put("username", "Όνομα χρήστη")
        Ελληνικά.put("countryLanguage", "option[value=\"el\"]")
        names.put("el", Ελληνικά)

        Map<String, String> Dansk = new HashMap<>();
        Dansk.put("language", "Dansk");
        Dansk.put("title", "Skift land/sprog");
        Dansk.put("username", "Brugernavn");
        Dansk.put("countryLanguage", "option[value=\"da\"]");
        names.put("da", Dansk);

        Map<String, String> Deutsch = new HashMap<>();
        Deutsch.put("language", "Deutsch");
        Deutsch.put("title", "Land/Sprache ändern");
        Deutsch.put("username", "Benutzername");
        Deutsch.put("countryLanguage", "option[value=\"de\"]");
        names.put("de", Deutsch);

        Map<String, String> 中文 = new HashMap<>();
        中文.put("language", "中文");
        中文.put("title", "更改国家（地区）/语言");
        中文.put("username", "用户名");
        中文.put("countryLanguage", "option[value=\"zh\"]");
        names.put("zh", 中文);

        Map<String, String> Lietuvių = new HashMap<>();
        Lietuvių.put("language", "Lietuvių");
        Lietuvių.put("title", "Keisti šalį / kalbą");
        Lietuvių.put("username", "Vartotojo vardas");
        Lietuvių.put("countryLanguage", "option[value=\"lt\"]");
        names.put("lt", Lietuvių);

        Map<String, String> Nederlands = new HashMap<>();
        Nederlands.put("language", "Nederlands");
        Nederlands.put("title", "Land/taal wijzigen");
        Nederlands.put("username", "Gebruikersnaam");
        Nederlands.put("countryLanguage", "option[value=\"nl\"]");
        names.put("nl", Nederlands);

        Map<String, String> magyar = new HashMap<>();
        magyar.put("language", "magyar");
        magyar.put("title", "Ország/nyelv módosítása");
        magyar.put("username", "Felhasználónév");
        magyar.put("countryLanguage", "option[value=\"hu\"]");
        names.put("hu", magyar);

        Map<String, String> norsk = new HashMap<>();
        norsk.put("language", "norsk");
        norsk.put("title", "Endre land/språk");
        norsk.put("username", "Brukernavn");
        norsk.put("countryLanguage", "option[value=\"no\"]");
        names.put("no", magyar)

        Map<String, String> Српски = new HashMap<>();
        Српски.put("language", "Српски");
        Српски.put("title", "Promenite zemlju/jezik");
        Српски.put("username", "Korisničko ime");
        Српски.put("countryLanguage", "option[value=\"sr\"]");
        names.put("sr", Српски)

        Map<String, String> Slovenščina = new HashMap<>();
        Slovenščina.put("language", "Slovenščina");
        Slovenščina.put("title", "Spremeni državo/jezik");
        Slovenščina.put("username", "Uporabniško ime");
        Slovenščina.put("countryLanguage", "option[value=\"sl\"]");
        names.put("sl", Slovenščina)

        Map<String, String> Slovenčina = new HashMap<>();
        Slovenčina.put("language", "Slovenščina");
        Slovenčina.put("title", "Zmeniť krajinu/jazyk");
        Slovenčina.put("username", "Užívateľské meno");
        Slovenčina.put("countryLanguage", "option[value=\"sk\"]");
        names.put("sk", Slovenčina)

        Map<String, String> svenska = new HashMap<>();
        svenska.put("language", "svenska");
        svenska.put("title", "Ändra land/språk");
        svenska.put("username", "Användarnamn");
        svenska.put("countryLanguage", "option[value=\"sv\"]");
        names.put("sv", svenska)

        Map<String, String> suomi = new HashMap<>();
        suomi.put("language", "suomi");
        suomi.put("title", "Vaihda maa/kieli");
        suomi.put("username", "Käyttäjänimi");
        suomi.put("countryLanguage", "option[value=\"fi\"]");
        names.put("fi", suomi)

        Map<String, String> Türkçe = new HashMap<>()
        Türkçe.put("language", "Türkçe")
        Türkçe.put("title", "Ülke/dil değiştir")
        Türkçe.put("username", "Kullanıcı Adı")
        Türkçe.put("countryLanguage", "option[value=\"tr\"]")
        names.put("tr", Türkçe)

        Map<String, String> čeština = new HashMap<>()
        čeština.put("language", "čeština")
        čeština.put("title", "Změnit zemi/jazyk")
        čeština.put("username", "Uživatelské jméno")
        čeština.put("countryLanguage", "option[value=\"cs\"]")
        names.put("cs", čeština)

        Map<String, String> русский = new HashMap<>()
        русский.put("language", "русский")
        русский.put("title", "Изменить страну/язык")
        русский.put("username", "Имя пользователя")
        русский.put("countryLanguage", "option[value=\"ru\"]")
        names.put("ru", русский)
    }
    public static final Map<String, String> countries
    static{
        countries = new HashMap<>()
        countries.put("Australia","en")
        countries.put("Albania","en")
        countries.put("UnitedKingdom","en")
        countries.put("Algérie","fr")
        countries.put("Bahrain","en")
        countries.put("Bangladesh","en")
        countries.put("Belgique","fr")
        countries.put("Botswana","en")
        countries.put("Cameroun","fr")
        countries.put("Canada","fr")
        countries.put("Chile","es")
        countries.put("Colombia","es")
        countries.put("CostaRica","es")
        countries.put("Cyprus","el")
        countries.put("Босна и Херцеговина","sl")
        countries.put("CôtedIvoire",  "fr")
        countries.put("Danmark","da")
        countries.put("Deutschland","de")
        countries.put("ElSalvador","es")
        countries.put("España","es")
        countries.put("Ethiopia","en")
        countries.put("France","fr")
        countries.put("Guatemala","es")
        countries.put("India","en")
        countries.put("Indonesia","en")
        countries.put("Iraq","en")
        countries.put("Ireland","en")
        countries.put("Kosovo","en")
        countries.put("Latvia","en")
        countries.put("Lebanon","en")
        countries.put("Liechtenstein","de")
        countries.put("Lietuva","lt")
        countries.put("Luxembourg","fr")
        countries.put("Macedonia","en")
        countries.put("Magyarország","hu")
        countries.put("Malaysia","en")
        countries.put("Malta","en")
        countries.put("Montenegro","en")
        countries.put("México","es")
        countries.put("Namibia","en")
        countries.put("Nederland","nl")
        countries.put("NewZealand", "en")
        countries.put("Nicaragua","es")
        countries.put("Nigeria","en")
        countries.put("Pakistan","en")
        countries.put("Panamá","es")
        countries.put("Paraguay","es")
        countries.put("Perú","es")
        countries.put("Philippines","en")
        countries.put("Polska","pl")
        countries.put("Portugal","pt")
        countries.put("Qatar","en")
        countries.put("RepúblicaDominicana","es")
        countries.put("Schweiz","de")
        countries.put("Serbia","sr")
        countries.put("Singapore","en")
        countries.put("Slovenija","sl")
        countries.put("SlovenskáRepublika", "sk")
        countries.put("SouthAfrica","en")
        countries.put("SouthKorea","en")
        countries.put("SriLanka","en")
        countries.put("Suomi","fi")
        countries.put("Sverige","sv")
        countries.put("Sénégal","fr")
        countries.put("Taiwan","en")
        countries.put("Tanzania","en")
        countries.put("Tunisia","en")
        countries.put("Türkiye","tr")
        countries.put("Uganda","en")
        countries.put("UnitedStates","en")
        countries.put("Uruguay", "es")
        countries.put("Venezuela","es")
        countries.put("Vietnam","en")
        countries.put("Zimbabwe","en")
        countries.put("Österreich","de")
        countries.put("ČeskáRepublika","cs")
        countries.put("Ελλάδα","el")
        countries.put("Азербайджан","ru")
        countries.put("Беларусь","ru")
        countries.put("Казахстан", "ru")
        countries.put("Россия", "ru")
        countries.put("Босна и Херцеговина","sl")
        countries.put("Узбекистан","ru")
        countries.put("Украина", "ru")
        countries.put("Hong Kong", "zh")
    }
    static String getValueByCountryAndParameter(String country, String parameter){
        return names.get(countries.get(country)).get(parameter)
    }

}
