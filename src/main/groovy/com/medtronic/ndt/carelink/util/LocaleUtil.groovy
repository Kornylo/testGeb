package com.medtronic.ndt.carelink.util

import com.medtronic.ndt.carelink.util.EnvUtil
/**
 * Utility for determining the language and locale of the device under test
 */
class LocaleUtil {

    private static final String DEFAULT_ENGLISH_LOCALE = 'US'

    /**
     * @return The configured Locale. If the locale is not configured, and the language property is it returns the language property.
     */
    static String getCountry() {
        if (EnvUtil.LANGUAGE != null && EnvUtil.LOCALE == null) {
            if (EnvUtil.LANGUAGE.equalsIgnoreCase('en')) {
                return DEFAULT_ENGLISH_LOCALE
            } else {
                return EnvUtil.LANGUAGE
            }
        }

        EnvUtil.LOCALE
    }

    /**
     * Creates a Locale object based on the configured language and locale
     * @return the Locale object corresponding with the configured language and locale
     */
    static Locale getLocale() {
        String lang = EnvUtil.LANGUAGE
        String country = getCountry()

        // Most locales are blank, so if the locale and language specified are identical then just use the language to
        // prevent confusing the Java Locale class.
        if (lang.equals(country)) {
            return new Locale(lang)
        } else {
            return new Locale(lang, country)
        }
    }
}
