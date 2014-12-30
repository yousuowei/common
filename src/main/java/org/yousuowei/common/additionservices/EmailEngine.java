package org.yousuowei.common.additionservices;

import java.io.File;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.yousuowei.common.CommonThreadPool;
import org.yousuowei.common.Constants;

/**
 * 邮件发送引擎
 * 
 * @ClassName: EmailPoint
 * @Description: TODO
 * @author: jie
 * @date: 2014-4-21 下午6:18:55
 */
@Component
public class EmailEngine {

	@Autowired
	private JavaMailSenderImpl javaMailSender;

	// public void init() {
	// javaMailSender = new JavaMailSenderImpl();
	// javaMailSender.setDefaultEncoding(Constants.COMMON_ENCODING);
	// javaMailSender.setUsername("yousuowei90@163.com");
	// javaMailSender.setPassword("yousuowei");
	// javaMailSender.setHost("smtp.163.com");
	// javaMailSender.setPort(465);
	// Properties p = new Properties();
	// p.setProperty("mail.smtp.auth", "true");
	// p.setProperty("prop", "true");
	// p.setProperty("mail.smtp.timeout", "25000");
	// p.setProperty("mail.smtp.socketFactory.port", "465");
	// p.setProperty("mail.smtp.socketFactory.fallback", "false");
	// p.setProperty("mail.smtp.socketFactory.class",
	// "javax.net.ssl.SSLSocketFactory");
	// javaMailSender.setJavaMailProperties(p);
	// }

	/**
	 * 发送文本内容邮件
	 * 
	 * @param to
	 *            接收者
	 * @param mailSubject
	 *            主题
	 * @param mailBody
	 *            文本内容
	 * @author: jie
	 * @date: 2014-4-24 下午5:54:46
	 */
	public void sendTextMail(String to, String mailSubject, String mailBody) {
		sendFileMail(to, mailSubject, mailBody, false, null, null);
	}

	/**
	 * 发送html内容邮件
	 * 
	 * @param to
	 *            接收者
	 * @param mailSubject
	 *            主题
	 * @param mailBody
	 *            html内容
	 * @author: jie
	 * @date: 2014-4-24 下午5:53:56
	 */
	public void sendHtmlMail(String to, String mailSubject, String mailBody) {
		sendFileMail(to, mailSubject, mailBody, true, null, null);
	}

	/**
	 * 邮件附带附件
	 * 
	 * @param to
	 *            接收者
	 * @param mailSubject
	 *            主题
	 * @param mailBody
	 *            内容
	 * @param isHtml
	 *            内容是否html
	 * @param fileTag
	 * @param files
	 *            附件列表
	 * @author: jie
	 * @date: 2014-4-24 下午5:52:02
	 */
	public void sendFileMail(String to, String mailSubject, String mailBody,
			boolean isHtml, File fileTag, File[] files) {
		CommonThreadPool.linkedExecutor(new EmailRunner(to, mailSubject,
				mailBody, isHtml, fileTag, files));
	}

	public class EmailRunner implements Runnable {
		String to;
		String mailSubject;
		String mailBody;
		boolean isHtml;
		File fileTag;
		File[] files;

		public EmailRunner(String to, String mailSubject, String mailBody,
				boolean isHtml, File fileTag, File[] files) {
			this.to = to;
			this.mailSubject = mailSubject;
			this.mailBody = mailBody;
			this.isHtml = isHtml;
			this.fileTag = fileTag;
			this.files = files;
		}

		public void run() {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			try {
				// 设置utf-8或GBK编码，否则邮件会有乱码
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
						true, Constants.COMMON_ENCODING);
				// 设置发送人名片
				helper.setFrom(new InternetAddress(javaMailSender.getUsername()));
				// 主题
				helper.setSubject(mailSubject);
				// 设置收件人邮箱
				helper.setTo(to);

				// 设置回复地址
				// helper.setReplyTo(new InternetAddress("@qq.com"));

				// 设置收件人抄送的名片和地址(相当于群发了)
				// helper.setCc(InternetAddress.parse(MimeUtility.encodeText("邮箱001")
				// + " <@163.com>," + MimeUtility.encodeText("邮箱002")
				// + " <@foxmail.com>"));

				// 邮件内容，注意加参数true，表示启用html格式
				helper.setText(mailBody, isHtml);

				// 添加附件
				if (null != files && files.length > 0) {
					for (int i = 0; i < files.length; i++)
						// 加入附件
						helper.addAttachment(
								MimeUtility.encodeText(files[i].getName()),
								files[i]);
				}
				// 加入插图
				if (null != fileTag) {
					helper.addInline(MimeUtility.encodeText(fileTag.getName()),
							fileTag);
				}
				// 发送
				javaMailSender.send(mimeMessage);
				System.out.println("send success!");
			} catch (Exception e) {

			}

		}

	}

}
