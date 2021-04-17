package ua.com.shyrkov.data;

import lombok.Setter;

@Setter
public class DateFormat {

    public static final String DAY_D = "d";
    public static final String DAY_DD = "dd";
    public static final String MONTH_M = "m";
    public static final String MONTH_MM = "mm";
    public static final String MONTH_MMM = "mmm";
    public static final String YEAR_YY = "yy";
    public static final String YEAR_YYYY = "yyyy";
    public static final String DELIMITER_SLASH = "/";
    public static final String DELIMITER_DASH = "-";

    private boolean isDayD, isDayDD,
            isMonthM, isMonthMM, isMonthMMM,
            isYearYY, isYearYYYY,
            isTimePresent, isSecondPresent, isMillisPresent,
            isDelimiterSlash, isDelimiterDash;
    private String[] order = {DAY_DD, MONTH_MM, YEAR_YYYY};

    public String getDayFormat() {
        if (isDayD) return DAY_D;
        else return DAY_DD;
    }

    public String getMonthFormat() {
        if (isMonthM) return MONTH_M;
        if (isMonthMMM) return MONTH_MMM;
        else return MONTH_MM;
    }

    public String getYearFormat() {
        if (isYearYY) return YEAR_YY;
        else return YEAR_YYYY;
    }

    public String[] getOrder() {
        return order;
    }

    public void setOrder(String format, int index) {
        this.order[index] = format;
    }

    public String getDelimiter() {
        if (isDelimiterDash) return DELIMITER_DASH;
        else return DELIMITER_SLASH;
    }

    public boolean getIsTimePresent() {
        return isTimePresent;
    }

    public boolean getIsSeconds() {
        return isSecondPresent;
    }

    public boolean getIsMillis() {
        return isMillisPresent;
    }

    public void setDefaultFormat(){
        isDayD=true;
        isDayDD=false;
        isMonthM=true;
        isMonthMM=false;
        isMonthMMM=false;
        isYearYY=false;
        isYearYYYY=true;
        isTimePresent=true;
        isSecondPresent=false;
        isMillisPresent=false;
        isDelimiterSlash=true;
        isDelimiterDash=false;
        order[0]=DAY_DD;
        order[1]=MONTH_MM;
        order[2]=YEAR_YYYY;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (isDelimiterSlash)
            stringBuilder.append(order[0]).append(DELIMITER_SLASH).append(order[1]).append(DELIMITER_SLASH)
                         .append(order[2]);
        if (isDelimiterDash)
            stringBuilder.append(order[0]).append(DELIMITER_DASH).append(order[1]).append(DELIMITER_DASH)
                         .append(order[2]);

        stringBuilder.append(" ");
        if (isTimePresent)
            stringBuilder.append("00:00");
        if (isSecondPresent)
            stringBuilder.append(":00");
        if (isMillisPresent)
            stringBuilder.append(":00");

        return stringBuilder.toString();
    }
}
