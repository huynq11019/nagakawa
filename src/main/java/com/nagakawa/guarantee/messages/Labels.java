/*
 * Labels.java
 *
 * Copyright (C) 2021 by Evotek. All right reserved.
 * This software is the confidential and proprietary information of Evotek
 */
package com.nagakawa.guarantee.messages;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 01/06/2021 - LinhLH: Create new
 *
 * @author LinhLH
 */
@Slf4j
@Component
public class Labels {
    /** The Constant US. */
    public static final Locale US = Locale.US;

    /** The Constant VN. */
    public static final Locale VN = new Locale("vi", "VN");
    /** The Constant _log. */

    /** The c available locale list. */
    private static List<Locale> cAvailableLocaleList;

    /** The message source. */
    private static MessageSource messageSource;

    @Autowired
    public Labels(MessageSource messageSource) {
        Labels.messageSource = messageSource;
    }

    /**
     * Available locale list.
     *
     * @return the list
     */
    public static List<Locale> availableLocaleList() {

        if (cAvailableLocaleList == null) {

            cAvailableLocaleList = new ArrayList<>();

            cAvailableLocaleList.add(US);
            cAvailableLocaleList.add(VN);
        }

        return cAvailableLocaleList;

    }

    /**
     * Gets the default locale.
     *
     * @return the default locale
     */
    public static Locale getDefaultLocale() {
        return Locale.getDefault();
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @return the labels
     */
    public static String getLabels(String key) {
        return getLabels(key, null, getDefaultLocale());
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @param locale
     *            the locale
     * @return the labels
     */
    public static String getLabels(String key, Locale locale) {
        return getLabels(key, null, locale);
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @param objs
     *            the objs
     * @return the labels
     */
    public static String getLabels(String key, Object[] objs) {
        return getLabels(key, objs, getDefaultLocale());
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @param objs
     *            the objs
     * @param locale
     *            the locale
     * @return the labels
     */
    public static String getLabels(String key, Object[] objs, Locale locale) {
        String ms = null;

        try {
            if (locale == null) {
                locale = getDefaultLocale();
            }

            ms = messageSource.getMessage(key, objs, locale);
        } catch (NoSuchMessageException ex) {
            _log.error("Can not get label for key " + key + " , return default value.", ex);
        }

        return ms;
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @param objs
     *            the objs
     * @param language
     *            the language
     * @return the labels
     */
    public static String getLabels(String key, Object[] objs, String language) {
        String ms = null;

        try {
            Locale locale = new Locale(language);

            ms = messageSource.getMessage(key, objs, locale);
        } catch (NoSuchMessageException ex) {
            _log.error("Can not get label for key " + key + " , return default value.", ex);
        }

        return ms;
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @param objs
     *            the objs
     * @param language
     *            the language
     * @param country
     *            the country
     * @return the labels
     */
    public static String getLabels(String key, Object[] objs, String language, String country) {
        String ms = null;

        try {
            Locale locale = new Locale(language, country);

            ms = messageSource.getMessage(key, objs, locale);
        } catch (NoSuchMessageException ex) {
            _log.error("Can not get label for key " + key + " , return default value.", ex);
        }

        return ms;
    }

    /**
     * Gets the labels.
     *
     * @param key
     *            the key
     * @param language
     *            the language
     * @return the labels
     */
    public static String getLabels(String key, String language) {
        return getLabels(key, null, language);
    }

    /**
     * Gets the locale.
     *
     * @param locale
     *            the locale
     * @return the locale
     */
    public static Locale getLocale(Locale locale) {
        if (locale == null || !isAvailableLocale(locale)) {
            locale = getDefaultLocale();
        }

        return locale;
    }

    /**
     * Gets the locale.
     *
     * @param language
     *            the language
     * @return the locale
     */
    public static Locale getLocale(String language) {
        Locale locale = null;

        if (language != null) {
            locale = new Locale(language);
        }

        return getLocale(locale);
    }

    // public static String getLabels(String key, String language, String
    // country){
    // return getLabels(key, null, language, country);
    // }

    /**
     * Gets the locale.
     *
     * @param language
     *            the language
     * @param country
     *            the country
     * @return the locale
     */
    public static Locale getLocale(String language, String country) {
        Locale locale = language != null && country != null ? new Locale(language, country) : getLocale(language);

        return getLocale(locale);
    }

    /**
     * Gets the message source.
     *
     * @return the message source
     */
    public static MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Checks if is available locale.
     *
     * @param locale
     *            the locale
     * @return true, if is available locale
     */
    public static boolean isAvailableLocale(Locale locale) {

        return availableLocaleList().contains(locale);

    }
}
