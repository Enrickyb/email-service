package org.enricky.emailservice.infra.ses;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.*;
import org.enricky.emailservice.adapters.EmailSenderGateway;
import org.enricky.emailservice.core.exceptions.EmailServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
public class SesEmailSender implements EmailSenderGateway {


    private final AmazonSimpleEmailService amazonSimpleEmailService;




    @Autowired
    public SesEmailSender(AmazonSimpleEmailService amazonSimpleEmailService) {
        this.amazonSimpleEmailService = amazonSimpleEmailService;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        SendEmailRequest request = new SendEmailRequest()
                .withSource("enrickyaraujob@hotmail.com")
                 .withDestination(new Destination().withToAddresses(to))
                    .withMessage(new Message()
                      .withSubject(new Content(subject))
                         .withBody(new Body().withText(new Content(body))));


        try {
            this.amazonSimpleEmailService.sendEmail(request);
        } catch (AmazonServiceException ex) {
            throw new EmailServiceException("Failure while sending email", ex);
        }

    }


}
