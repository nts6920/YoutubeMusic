package com.nts.youtubemusic.common.model;

public class ChangeLanguage {
    private String codeLocale;
    private String nameLanguage;

    public ChangeLanguage() {
    }


    public ChangeLanguage(String codeLocale, String nameLanguage) {
        this.codeLocale = codeLocale;
        this.nameLanguage = nameLanguage;
    }

    public String getCodeLocale() {
        return codeLocale;
    }

    public void setCodeLocale(String codeLocale) {
        this.codeLocale = codeLocale;
    }

    public String getNameLanguage() {
        return nameLanguage;
    }

    public void setNameLanguage(String nameLanguage) {
        this.nameLanguage = nameLanguage;
    }
}
