package com.example.textmoderate.version4.service;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class OpenAIModerationService {

    private final OpenAiService openAiService;
    private static final String MODEL = "gpt-3.5-turbo";

    public OpenAIModerationService(@Value("${openai.api.key}") String openaiApiKey) {
        this.openAiService = new OpenAiService(openaiApiKey);
    }

    public String analyzeTextWithOpenAI(String content) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(),
                """
                        Review the provided statements to determine if they should be approved or rejected based on the \
                        following criteria:
                        Presence of profanity (including in various languages) - reject profanity
                        Agitation against the state - reject agitation
                        Promotion of drugs, alcohol, etc. - reject propaganda
                        Fraudulent operations - reject fraud
                        Sexually explicit content - reject sexual_explicit
                        Severe toxicity - reject severe_toxicity
                        Threats - reject threat
                        Insults - reject insult
                        Identity attacks - reject identity_attack
                        Acceptable statements:
                        Statements that do not include any of the above criteria - accept
                        Respond with 'accept' if a statement meets none of the criteria. If a statement meets any of the criteria, \
                        respond with 'reject' followed by the specific attribute causing the rejection (e.g., 'reject profanity', 'reject agitation'). \
                        Note that messages may be in various languages and need to include relevant commercial content to be approved.
                        """
        );

        ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), content);

        messages.add(systemMessage);
        messages.add(userMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model(MODEL)
                .messages(messages)
                .maxTokens(256)
                .temperature(1.0)
                .topP(1.0)
                .frequencyPenalty(0.0)
                .presencePenalty(0.0)
                .build();

        ChatCompletionResult result = openAiService.createChatCompletion(chatCompletionRequest);

        if (!result.getChoices().isEmpty()) {
            return result.getChoices().get(0).getMessage().getContent().trim();
        } else {
            return "Error: No response from OpenAI API";
        }
    }
}





