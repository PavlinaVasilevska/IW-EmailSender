package com.example.EmailSender.enumeration;

public enum RepetitionEnum {
    DAILY("daily"),
    WEEKLY("weekly"),
    MONTHLY("monthly"),
    YEARLY("yearly");

    private String repetitionType;

    RepetitionEnum(String repetitionType) {
        this.repetitionType = repetitionType;
    }

    public String getRepetitionType() {
        return repetitionType;
    }

}