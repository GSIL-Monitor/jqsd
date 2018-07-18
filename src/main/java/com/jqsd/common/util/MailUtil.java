package com.jqsd.common.util;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;

public class MailUtil {
	private static final Logger LOG = LoggerFactory.getLogger(MailUtil.class);
    /* 邮箱配置资源 */
    private static final Prop prop = PropKit.use("mail.properties");

    /* 邮箱配置详情 */
    private static final String MAIL_SMTP_AUTH 			= prop.get("mail.smtp.auth");
    private static final String MAIL_HOST 				= prop.get("mail.host");
    private static final String MAIL_TRANSPORT_PROTOCOL = prop.get("mail.transport.protocol");
    private static final String MAIL_SMTP_PORT 			= prop.get("mail.smtp.port");
    private static final String MAIL_AUTH_NAME 			= prop.get("mail.auth.name");
    private static final String MAIL_AUTH_PASSWORD 		= prop.get("mail.auth.password");
    private static final String MAIL_DISPLAY_SENDNAME 	= prop.get("mail.display.sendname");
    private static final String MAIL_DISPLAY_SENDMAIL 	= prop.get("mail.display.sendmail");
    private static final String MAIL_SEND_CHARSET 		= prop.get("mail.send.charset");

    private static HtmlEmail getHtmlEmail() throws EmailException {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setCharset(MAIL_SEND_CHARSET);
        htmlEmail.setHostName(MAIL_HOST);
        htmlEmail.setAuthentication(MAIL_AUTH_NAME, MAIL_AUTH_PASSWORD);
        htmlEmail.setFrom(MAIL_DISPLAY_SENDMAIL,MAIL_DISPLAY_SENDNAME);
        return htmlEmail;
    }

    /**
     *
     * @param content 邮件内容
     * @param title   邮件标题
     * @param sendTo  收件人
     * @throws MessagingException
     * @throws EmailException
     */
	public  static void sendEmail(String content,String title,String... sendTo) throws MessagingException, EmailException, IOException {
	    sendEmail(content,title,sendTo,null);
	}

    public  static void sendEmail(String content,String title,String[] sendTo,String[] cc) throws MessagingException, EmailException, IOException {
        HtmlEmail htmlEmail = getHtmlEmail();

        htmlEmail.addTo(sendTo);
        if (cc != null && cc.length>0){
            htmlEmail.addCc(cc);
        }
        htmlEmail.setSubject(title);
        htmlEmail.setHtmlMsg(content);

        htmlEmail.send();
    }

    /**
     *
     * @param content  邮件内容
     * @param title    邮件标题
     * @param fileName   附件
     * @param sendTo   收件人
     * @param cc      抄送人
     * @throws MessagingException
     * @throws EmailException
     * @throws IOException
     */
	public  static void sendEmail(String content,String title, String[] fileName,String[] sendTo,String[] cc) throws MessagingException, EmailException, IOException {
		HtmlEmail htmlEmail = getHtmlEmail();

        htmlEmail.addTo(sendTo);
		if(cc != null&&cc.length>0){
            htmlEmail.addCc(cc);
        }
        htmlEmail.setSubject(title);
		htmlEmail.setHtmlMsg(content);

        MimeMultipart mmp = new MimeMultipart();
        for (int i=0;i<fileName.length;i++){
            File f = new File(fileName[i]);
            MimeBodyPart attach = new MimeBodyPart();
            attach.attachFile(f);
            sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
            attach.setFileName("=?GBK?B?"+enc.encode(f.getName().getBytes("GBK")) +"?=");
            mmp.addBodyPart(attach);
        }

        htmlEmail.addPart(mmp);

        htmlEmail.send();
	}

	public static void main(String[] args) throws MessagingException, EmailException, IOException {

    }

}
