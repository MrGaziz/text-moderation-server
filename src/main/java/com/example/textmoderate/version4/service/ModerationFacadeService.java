package com.example.textmoderate.version4.service;


import com.example.textmoderate.version4.model.ModeratedMessage;
import com.example.textmoderate.version4.model.ModerationResult;
import com.example.textmoderate.version4.repository.ModeratedMessageRepository;
import com.example.textmoderate.version4.repository.RejectReasonRepository;
import com.example.textmoderate.version4.utils.TextModerationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModerationFacadeService {

    private static final Logger log = LoggerFactory.getLogger(ModerationFacadeService.class);

    private final DatabaseModerationService databaseModerationService;
    private final ModeratedMessageRepository moderatedMessageRepository;
    private final RejectReasonRepository rejectReasonRepository;
    private final GoogleModerationService googleModerationService;
    private final OpenAIModerationService openAIModerationService;

    public ModerationFacadeService(DatabaseModerationService databaseModerationService,
                                   ModeratedMessageRepository moderatedMessageRepository,
                                   RejectReasonRepository rejectReasonRepository,
                                   GoogleModerationService googleModerationService,
                                   OpenAIModerationService openAIModerationService) {
        this.databaseModerationService = databaseModerationService;
        this.moderatedMessageRepository = moderatedMessageRepository;
        this.rejectReasonRepository = rejectReasonRepository;
        this.googleModerationService = googleModerationService;
        this.openAIModerationService = openAIModerationService;
    }

    public ModerationResult moderateTextMessage(String msisdn, String textMessage, String language) {
        int status = moderateWithDatabase(textMessage);

        if (status == 1) {
            String googleResult = moderateWithGoogleAPI(textMessage);
            status = TextModerationUtils.mapRejectionReasonToStatus(googleResult, true);
        }

        if (status == 1) {
            String openAIResult = moderateWithOpenAI(textMessage);
            status = TextModerationUtils.mapRejectionReasonToStatus(openAIResult, false);
        }

        persistModeratedMessage(msisdn, textMessage, status);
        String reason = getRejectReason(status, language);
        boolean isApproved = "accept".equals(reason);
        return new ModerationResult(isApproved, isApproved ? null : reason);
    }

    private int moderateWithDatabase(String text) {
        return databaseModerationService.checkTextAgainstDatabase(text);
    }

    private String moderateWithGoogleAPI(String text) {
        try {
            String analysisResult = googleModerationService.analyzeText(text);
            return googleModerationService.analyzeTextWithGoogle(analysisResult);
        } catch (Exception e) {
            log.error("Google API moderation failed: {}", e.getMessage());
            return "accept";
        }
    }

    private String moderateWithOpenAI(String text) {
        try {
            return openAIModerationService.analyzeTextWithOpenAI(text);
        } catch (Exception e) {
            log.error("OpenAI moderation failed: {}", e.getMessage());
            return "accept";
        }
    }

    private void persistModeratedMessage(String msisdn, String content, int status) {
        ModeratedMessage message = new ModeratedMessage();
        message.setMsisdn(msisdn);
        message.setMessage(content);
        message.setCreatedAt(LocalDateTime.now());
        message.setStatus(status);
        moderatedMessageRepository.save(message);
    }

    private String getRejectReason(int status, String language) {
        return rejectReasonRepository.findById(status)
                .map(reason -> switch (language) {
                    case "kk" -> reason.getReasonKazakh();
                    case "ru" -> reason.getReasonRussian();
                    default -> reason.getReason();
                })
                .orElse("Unknown reason");
    }

}


