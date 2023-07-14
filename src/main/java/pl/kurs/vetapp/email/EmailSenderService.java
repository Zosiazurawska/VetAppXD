package pl.kurs.vetapp.email;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
@AllArgsConstructor
public class EmailSenderService {

    private JavaMailSender mailSender;

    public void sendEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("davvidcapar@gmail.com");
        message.setTo(to);
        message.setText("Your visit has been created. Please confirm it by clicking the link: http://localhost:8080/visit/confirm/" + token);
        message.setSubject("Visit confirmation");

        mailSender.send(message);
    }
}
