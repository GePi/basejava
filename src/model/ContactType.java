package model;

public enum ContactType {
    MOBILE_PHONE_NUMBER("Мобильный"),
    HOME_PHONE_NUMBER("Тел."),
    EMAIL("Почта"),
    SKYPE_LOGIN("Skype"),
    LINKEDIN_PROFILE("Профиль LinkedIn"),
    GITHUB_PROFILE("Профиль GitHub"),
    STACKOVERFLOW_PROFILE("Профиль StackOverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }
}
