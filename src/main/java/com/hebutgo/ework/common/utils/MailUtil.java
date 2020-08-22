package com.hebutgo.ework.common.utils;

import org.springframework.stereotype.Service;

/**
 * 发邮件工具
 * @author zuozhiwei
 */
@Service("mail")
public class MailUtil {
//
//    Logger logger = LoggerFactory.getLogger(MailUtil.class);
//
//    @Resource
//    private JavaMailSender mailSender;
//
//    @Value("${mail.fromMail.addr}")
//    private String from;
//
//    public void sendSimpleMail(String to, String subject, String content) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(content);
//
//        try {
//            mailSender.send(message);
//            logger.info("简单邮件已经发送。");
//        } catch (Exception e) {
//            logger.error("发送简单邮件时发生异常！", e);
//        }
//
//    }
}
