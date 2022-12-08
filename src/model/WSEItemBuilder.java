package model;

import java.time.LocalDate;

public class WSEItemBuilder {

    private LocalDate from;
    private LocalDate to;
    private String organizationName;
    private String url;
    private String workPosition;
    private String essenceOfWork;
    private boolean isCurrentPosition;

    static public WSEItemBuilder getInstance() {
        return new WSEItemBuilder();
    }

    public WorkStudyExperienceSection.WSEItem getResult( ) {
        return new WorkStudyExperienceSection.WSEItem(from,to,organizationName,url,workPosition,essenceOfWork,isCurrentPosition);
    }
    public WSEItemBuilder() {
    }

    public WSEItemBuilder setFrom(LocalDate from) {
        this.from = from;
        return this;
    }

    public WSEItemBuilder setTo(LocalDate to) {
        this.to = to;
        return this;
    }

    public WSEItemBuilder setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public WSEItemBuilder setUrl(String url) {
        this.url = url;
        return this;
    }

    public WSEItemBuilder setWorkPosition(String workPosition) {
        this.workPosition = workPosition;
        return this;
    }

    public WSEItemBuilder setEssenceOfWork(String essenceOfWork) {
        this.essenceOfWork = essenceOfWork;
        return this;
    }

    public WSEItemBuilder setCurrentPosition(boolean currentPosition) {
        isCurrentPosition = currentPosition;
        return this;
    }

}
