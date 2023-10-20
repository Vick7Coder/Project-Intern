package com.hieuph.todosmanagement.service.Impl;

import com.hieuph.todosmanagement.entity.User;
import com.hieuph.todosmanagement.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendVerificationEmail(User user, String url) {
        try {
            String subject = "Kích hoạt tài khoản";
            String senderName = "Todo";
            String mailContent = MailForm(user, url, subject);
            MimeMessage message = javaMailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("contact.lwind@gmail.com", senderName);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            javaMailSender.send(message);
            log.info("Send email verify account [" + user.getEmail() + "]");
        }catch (Exception exception){
            log.error(exception.getMessage());

        }
    }


    @Override
    public void sendPasswordResetVerificationEmail(User user, String url) {
        try {
            String subject = "Đặt lại mật khẩu";
            String senderName = "Todo";
            String mailContent = MailForm(user, url, subject);
            MimeMessage message = javaMailSender.createMimeMessage();
            var messageHelper = new MimeMessageHelper(message);
            messageHelper.setFrom("contact.lwind@gmail.com", senderName);
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject(subject);
            messageHelper.setText(mailContent, true);
            javaMailSender.send(message);
            log.info("Send email reset password account [" + user.getEmail() + "]");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
        }
    }
    public String MailForm(User user, String url, String req){
        return "<div style=\"font-family: Helvetica,Arial,sans-serif;min-width:1000px;overflow:auto;line-height:2\">\n" +
                "  <div style=\"margin:50px auto;width:70%;padding:20px 0\">\n" +
                "    <div style=\"border-bottom:1px solid #eee\">\n" +
                "      <a href=\"\" style=\"font-size:1.4em;color: #00466a;text-decoration:none;font-weight:600\">Todo - What you think is what you do</a>\n" +
                "    </div>\n" +
                "    <p style=\"font-size:1.1em\">Chào "+user.getUsername()+",</p>\n" +
                "    <p>Bạn đã yêu cầu "+req+". Vui lòng nhấp vào liên kết bên dưới để hoàn thành quá trình: </p>\n" +
                "    <h2 style=\"background: ##34ebdb;margin: 0 auto;width: max-content;padding: 0 10px;color: #fff;border-radius: 4px;\"><a href =\""+url+"\" target = \"_blank\">xác thực tài khoản</a></h2>\n" +
                "    <p style=\"font-size:0.9em;\">Trân trọng,<br />Todo</p>\n" +
                "    <hr style=\"border:none;border-top:1px solid #eee\" />\n" +
                "    <div style=\"float:right;padding:8px 0;color:#aaa;font-size:0.8em;line-height:1;font-weight:300\">\n" +
                "      <p>Todo Inc</p>\n" +
                "      <p>01 Vo Van Ngan Street</p>\n" +
                "      <p>Thu Duc City, HCMC</p>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>";
    }
}
