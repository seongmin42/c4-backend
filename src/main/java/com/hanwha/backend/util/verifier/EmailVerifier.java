package com.hanwha.backend.util.verifier;

import com.hanwha.backend.annotation.Verification;

@Verification
public class EmailVerifier implements Verifier{
    @Override
    public boolean verify(String prompt) {
        return !prompt.matches("(.?\\n?)*" +
                "([a-zA-z0-9\\.]+@[a-zA-z0-9\\.]+\\.[A-Za-z]+)+" +
                "(.?\\n?)*");
    }
}
