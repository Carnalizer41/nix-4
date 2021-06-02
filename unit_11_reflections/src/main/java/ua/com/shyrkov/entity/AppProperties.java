package ua.com.shyrkov.entity;

import ua.com.shyrkov.annotation.PropertyKey;
import ua.com.shyrkov.enums.AppLevel;

public class AppProperties {

    @PropertyKey("company.name")
    public String companyName;
    @PropertyKey("application.name")
    public String appName;
    @PropertyKey("application.version")
    public String appVersion;
    @PropertyKey("application.dev.level")
    public AppLevel appLevel;
    @PropertyKey("contributors.amount")
    public int contributorsAmount;

    @Override
    public String toString() {
        return "AppProperties{" +
                "companyName='" + companyName + '\'' +
                ", appName='" + appName + '\'' +
                ", appVersion='" + appVersion + '\'' +
                ", appLevel=" + appLevel +
                ", contributorsAmount=" + contributorsAmount +
                '}';
    }
}
