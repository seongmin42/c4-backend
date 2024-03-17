package com.hanwha.backend.util.verifier;

import com.hanwha.backend.annotation.Verification;

@Verification
public class PhoneNumberVerifier implements Verifier{
    @Override
    public boolean verify(String prompt) {
        return !prompt.matches("(.?\\n?)*" +
                "(\\d{3}-\\d{3,4}-\\d{4})+" +
                "(.?\\n?)*");
    }
}
