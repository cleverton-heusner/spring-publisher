package cleverton.heusner.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpStatusCode {

    public static final String OK = "200";
    public static final String BAD_REQUEST = "400";
    public static final String NOT_FOUND = "404";
    public static final String CREATED = "201";
    public static final String NO_CONTENT = "204";
    public static final String CONFLICT = "409";
}