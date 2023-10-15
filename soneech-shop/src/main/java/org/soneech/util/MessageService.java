package org.soneech.util;

import org.springframework.validation.BindingResult;

public class MessageService {
    public static String prepareFieldsErrorMessage(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (var error: bindingResult.getFieldErrors()) {
            stringBuilder.append(error.getField()).append(":");
            stringBuilder.append(error.getDefaultMessage()).append(";");
        }

        return stringBuilder.toString();
    }
}
