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

    @Override
    public String toString() {
        return repetitionType;
    }

    public static RepetitionEnum fromString(String repetitionType) {
        if (repetitionType == null) {
            throw new IllegalArgumentException("Repetition type cannot be null");
        }
        for (RepetitionEnum repetition : RepetitionEnum.values()) {
            if (repetition.repetitionType.equalsIgnoreCase(repetitionType)) {
                return repetition;
            }
        }
        throw new IllegalArgumentException("No enum constant with text " + repetitionType);
    }

}