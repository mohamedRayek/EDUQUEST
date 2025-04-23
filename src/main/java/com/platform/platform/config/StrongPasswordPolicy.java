package com.platform.platform.config;

import org.passay.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class StrongPasswordPolicy {

    public static void validatePassword(String password) {
        PasswordValidator validator = new PasswordValidator(
                new LengthRule(8, 30),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 1),
                new CharacterRule(EnglishCharacterData.Special, 1),
                new WhitespaceRule()
        );

        RuleResult result = validator.validate(new PasswordData(password));
        if (!result.isValid()) {
            String message = validator.getMessages(result).stream()
                    .map(m -> {
                        if (m.contains("length")) return "Password must be between 8 and 30 characters";
                        if (m.contains("uppercase")) return "Must contain at least one uppercase letter";
                        if (m.contains("lowercase")) return "Must contain at least one lowercase letter";
                        if (m.contains("digit")) return "Must contain at least one digit";
                        if (m.contains("special")) return "Must contain at least one special character";
                        if (m.contains("whitespace")) return "Whitespace characters are not allowed";
                        return m;
                    })
                    .collect(Collectors.joining(", "));

            throw new IllegalArgumentException(message);
        }
    }
}
