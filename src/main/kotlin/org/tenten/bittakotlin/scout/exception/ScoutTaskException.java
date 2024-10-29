package org.tenten.bittakotlin.scout.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ScoutTaskException extends RuntimeException {
    private final int code;
    private final String message;
}