package com.ssafy.mereview.api.service.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;
    private String authNum;

    public void createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for(int i=0;i<8;i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0 :
                    key.append((char) ((int)random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int)random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        authNum = key.toString();
    }

    //메일 양식 작성
    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(); // 인증 코드 생성
        String senderEmail = "youremail@example.com"; // Replace with your email address (sender)
        String senderName = "Your Name"; // Replace with your name (sender)

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
        helper.setTo(email); // Set recipient email address
        helper.setFrom(new InternetAddress(senderEmail, senderName)); // Set sender email and name
        helper.setSubject("Verification Code for Your Account"); // Set email subject

        // Email content with the generated verification code
        String emailContent = "<html><body style=\"font-family: Arial, sans-serif;\">"
                + "<h2>안녕하세요!!</h2>"
                + "<p>Mereview 사이트에 회원가입을 해주셔서 감사합니다!</p>"
                + "<p>인증코드입니다.:</p>"
                + "<h3 style=\"background-color: #f0f0f0; padding: 10px;\">" + authNum + "</h3>"
                + "<p>Please use this code to verify your account.</p>"
                + "<p>Best regards,<br/>Your Website Team</p>"
                + "</body></html>";

        helper.setText(emailContent, true); // Set email content as HTML

        return message;
    }

    //실제 메일 전송
    public String sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        mailSender.send(emailForm);

        return authNum; //인증 코드 반환
    }


}

