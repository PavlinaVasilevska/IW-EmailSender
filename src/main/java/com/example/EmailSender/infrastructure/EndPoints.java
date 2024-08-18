package com.example.EmailSender.infrastructure;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndPoints {
    public static final String BASE = "/api";

    public static final String EMAIL_JOBS = BASE + "/emailjobs";
    public static final String EMAIL_TEMPLATES = BASE + "/email-templates";
    public static final String OCCURRENCES = BASE + "/occurrences";
    public static final String REPETITIONS = BASE + "/repetitions";
    public static final String ROLES = BASE + "/roles";
    public static final String USERS = BASE + "/users";
}