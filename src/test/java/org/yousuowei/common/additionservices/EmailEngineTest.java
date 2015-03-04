package org.yousuowei.common.additionservices;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:email-context.xml" })
public class EmailEngineTest {

    @Autowired
    private EmailEngine sender;

    @Test
    public void test() {
	String to = "xljie2@163.com";
	String mailSubject = "yousuowei 测试邮件";
	String mailBody = "测试邮件请勿回复";
	String mailHtml = "<img src='http://img.sccnn.com/bimg/337/24778.jpg' border='0' alt='新品上市海报' rec_layer_index='1' act_name='img' clk_pos='-1' bdyt_gallery_id='3847140080851717553' bdyt_img_id='-5188214218861575528' log_id='1398162949795'>";
	File[] files = new File[] { new File(
		"C:/Users/Public/Pictures/Sample Pictures/爱奇艺.zip") };
	File fileTag = new File(
		"C:/Users/Public/Pictures/Sample Pictures/pan zi.jpg");
	sender.sendTextMail(to, mailSubject, mailBody);
	sender.sendHtmlMail(to, mailSubject, mailHtml);
	sender.sendFileMail(to, mailSubject, mailBody, false, fileTag, files);
	sender.sendFileMail(to, mailSubject, mailHtml, true, fileTag, files);
    }

}
