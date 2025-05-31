package com.habitplay.session.model;

public enum SessionDuration {
    SEVEN_DAYS(7),
    FOURTEEN_DAYS(14),
    TWENTY_ONE_DAYS(21),
    TWENTY_EIGHT_DAYS(28);

    private final int days;

    SessionDuration(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public static SessionDuration fromDays(int days) {
        return switch (days) {
            case 7 -> SEVEN_DAYS;
            case 14 -> FOURTEEN_DAYS;
            case 21 -> TWENTY_ONE_DAYS;
            case 28 -> TWENTY_EIGHT_DAYS;
            default -> throw new IllegalArgumentException("Invalid duration: " + days);
        };
    }
}
