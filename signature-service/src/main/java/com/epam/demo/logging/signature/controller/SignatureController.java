package com.epam.demo.logging.signature.controller;

import com.epam.demo.logging.signature.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignatureController {

    private static final Logger LOG = LoggerFactory.getLogger(SignatureController.class);

    @PostMapping("/api/v1/signature")
    public String getSignedMessage(@RequestBody Message message) {
        LOG.info("Applying signature to message for user: {}", message.getUsername());
        return message.getHeader() + "\n\n" + message.getBody() + "\n\n"
                + "BR,\n" + message.getUsername();
    }

}
