package com.hanwha.backend.method;

import com.hanwha.backend.util.verifier.BuiltinVerifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.InvocationTargetException;

public class VerificationUtilTest {
    @MockBean
    private BuiltinVerifier builtinVerifier;
//    @Test
//    public void plainPromptTest() throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
//        String prompt = "Event Source has some handlers: onerror, onmessage, and onopen. You can read more about it on the MDN website. In our use case, we only use onerror and onmessage.\n" +
//                "\n" +
//                "The onerror handler is used to validate or log what causes error, then closes the connection using sse.onclose() method to prevent more errors and guide user to do actions that needed, e.g. retry to upload.";
//        UserChatRequestDto dto = new UserChatRequestDto("userId", "roomId",prompt, ChatOption.Creative);
//        Assertions.assertTrue(dto.isVerify());
//    }
//
//    @Test
//    public void plainPromptEmailTest(){
//        String prompt = "This is not my email.\n" +
//                "here : XXXXXXXXXXXXXXXXXXXX\n" +
//                "Thank you.";
//        Assertions.assertTrue(VerificationUtil.containsEmail(prompt));
//    }
//
//    @Test
//    public void plainPromptEmailTest2(){
//        String prompt = "This is not my email.\n" +
//                "here : seongmin42@\n" +
//                "Thank you.";
//        Assertions.assertTrue(VerificationUtil.containsEmail(prompt));
//    }
//
//    @Test
//    public void plainPromptPhoneNumberTest(){
//        String prompt = "Calculate 4532-1422+2433.\n";
//        Assertions.assertTrue(VerificationUtil.containsPhoneNumber(prompt));
//    }
//
//    @Test
//    public void plainPromptPersonalIdTest(){
//        String prompt = "This is not my email.\n" +
//                "here : seongmin42@\n" +
//                "Thank you.";
//        Assertions.assertTrue(VerificationUtil.containsPersonalId(prompt));
//    }
//
    @Test
    public void plainPromptJavaCodeTest(){
        String prompt = "How can I fix the bug?\n" +
                "It is too hard to deal with it.\n";
        Assertions.assertTrue(builtinVerifier.verify(prompt));
    }

    @Test
    public void emailContainsTest() {
        String prompt = "This is my email.\n" +
                "here : seongmin42@naver.com\n" +
                "Thank you.";
        Assertions.assertFalse(builtinVerifier.verify(prompt));
    }

    @Test
    public void javaMethodContainsTest() throws InvocationTargetException, IllegalAccessException, ClassNotFoundException {
        String prompt = "How can I fix the bug?\n" +
                "    @Bean\n" +
                "    @Qualifier(\"openaiRestTemplate\")\n" +
                "    public RestTemplate openaiRestTemplate() {\n" +
                "        RestTemplate restTemplate = new RestTemplate();\n" +
                "        restTemplate.getInterceptors().add((request, body, execution) -> {\n" +
                "            request.getHeaders().add(\"Authorization\", \"Bearer \" + openaiApiKey);\n" +
                "            return execution.execute(request, body);\n" +
                "        });\n" +
                "        return restTemplate;\n" +
                "    }" +
                "I don't know what is the problem.\n";
        Assertions.assertFalse(builtinVerifier.verify(prompt));
    }
}
