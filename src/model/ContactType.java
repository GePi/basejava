package model;

public enum ContactType {
    MOBILE_PHONE_NUMBER("Мобильный"),
    HOME_PHONE_NUMBER("Тел."),
    EMAIL("Почта") {
        @Override
        protected String toHtml0(String val) {
            return "<a href= \"mailto:" + val + "\">" + val + "</a>";
        }
    },
    SKYPE_LOGIN("Skype") {
        @Override
        protected String toHtml0(String val) {
            return "<a href= \"skype:" + val + "\">" + val + "</a>";
        }
    },
    LINKEDIN_PROFILE("Профиль LinkedIn"),
    GITHUB_PROFILE("Профиль GitHub"),
    STACKOVERFLOW_PROFILE("Профиль StackOverflow"),
    HOMEPAGE("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String val) {
        return title + ":" + val;
    }

    public String toHtml(String val) {
        return (val == null) ? "" : toHtml0(val);
    }
}
