package com.hanwha.backend.util.verifier;

import com.hanwha.backend.annotation.Verification;

@Verification
public class JavaMethodVerifier implements Verifier{
    @Override
    public boolean verify(String prompt) {
        return !prompt.matches("(.?\\n?)*" +
                "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])" +
                "(.?\\n?)*");
    }
}
