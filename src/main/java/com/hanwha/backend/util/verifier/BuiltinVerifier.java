package com.hanwha.backend.util.verifier;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuiltinVerifier {

    List<Verifier> verifiers = new ArrayList<>();

    public void addVerifier(Verifier verifier){
        verifiers.add(verifier);
    }

    public boolean verify(String prompt){
        for(Verifier verifier : verifiers){
            if(!verifier.verify(prompt)){
                return false;
            }
        }
        return true;
    }
}
