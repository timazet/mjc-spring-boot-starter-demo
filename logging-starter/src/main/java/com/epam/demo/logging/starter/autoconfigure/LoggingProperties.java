package com.epam.demo.logging.starter.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Validated
@ConfigurationProperties("demo.logging")
public class LoggingProperties {

    /**
     * Message prefix that should be used.
     */
    @NotBlank
    @Size(min = 4, max = 20)
    private String messagePrefix = "Starter > ";
    @Valid
    private Payload payload = new Payload();

    public String getMessagePrefix() {
        return messagePrefix;
    }

    public void setMessagePrefix(String messagePrefix) {
        this.messagePrefix = messagePrefix;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public static class Payload {

        /**
         * Payload's max length that will be logged.
         */
        @Positive
        @Max(100)
        private int maxLength = 100;

        public int getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(int maxLength) {
            this.maxLength = maxLength;
        }
    }
}
