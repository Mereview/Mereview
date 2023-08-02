package com.ssafy.mereview.api.service.member;

import com.ssafy.mereview.api.service.member.dto.request.EmailCheckCode;
import com.ssafy.mereview.common.util.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private final JwtUtils jwtUtils;
    private Map<String, EmailCheckCode> emailCheckCodeMap = new HashMap<>();


    //실제 메일 전송
    public void sendEmail(String toEmail) throws MessagingException, UnsupportedEncodingException {

        //메일전송에 필요한 정보 설정
        MimeMessage emailForm = createEmailForm(toEmail);
        //실제 메일 전송
        mailSender.send(emailForm);
    }

    public boolean checkEmail(String email, String verificationCode) {
        EmailCheckCode checkCode = emailCheckCodeMap.getOrDefault(email, null);

        if(checkCode == null){
            throw new IllegalArgumentException("인증 코드가 존재하지 않습니다.");
        }

        if(JwtUtils.isTokenExpired(checkCode.getJwtToken())){
            throw new IllegalArgumentException("인증 시간이 만료되었습니다.");
        }

        if (!email.equals(jwtUtils.getUsernameFromJwt(checkCode.getJwtToken())) || !checkCode.getVerificationCode().equals(verificationCode)) {
            return false;
        }
        emailCheckCodeMap.remove(email);
        return true;
    }

    private void createCode(String email) {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(3);

            switch (index) {
                case 0:
                    key.append((char) ((int) random.nextInt(26) + 97));
                    break;
                case 1:
                    key.append((char) ((int) random.nextInt(26) + 65));
                    break;
                case 2:
                    key.append(random.nextInt(9));
                    break;
            }
        }
        EmailCheckCode emailCheckcode = EmailCheckCode.builder()
                .jwtToken(jwtUtils.generateEmailToken(email))
                .verificationCode(key.toString())
                .build();

        emailCheckCodeMap.put(email, emailCheckcode);
    }

    //메일 양식 작성
    private MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        createCode(email); // 인증 코드 생성
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
                + "<h3 style=\"background-color: #f0f0f0; padding: 10px;\">" + emailCheckCodeMap.get(email).getVerificationCode() + "</h3>"
                + "<p>Please use this code to verify your account.</p>"
                + "<p>Best regards,<br/>Your Website Team</p>"
                + "</body></html>";

        helper.setText(emailContent, true); // Set email content as HTML

        return message;
    }
}

