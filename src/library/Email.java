package library;

import com.sun.mail.smtp.SMTPMessage;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;


public class Email {

	private static String fromAddress = "tawelibrary@gmail.com";
	private static String fromPassowrd = "polik123!";

	private static Session accountConnection() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "805");


		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromAddress, fromPassowrd);
			}
		});
		return session;
	}

	public static void returnNotification(String address, String resourceTitle, String returnDate){

		try{
			SMTPMessage message = new SMTPMessage(accountConnection());
			message.setFrom(new InternetAddress(fromAddress));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address));

			message.setSubject("Tawe Library - Return Request");
			message.setText("Hello\n " +
					"This is a quick notification that the resource: " + resourceTitle + "\n" +
					"needs to be return back to us before: " + returnDate + "\n" +
					"\n\n" +
					"Thank you\n Tawe Library Team");
			message.setNotifyOptions(SMTPMessage.NOTIFY_SUCCESS);
			int returnOption = message.getReturnOption();
			System.out.println(returnOption);
			Transport.send(message);
			System.out.println("sent");

		}catch (AddressException e){
			System.out.println("Address error");
		}catch (MessagingException e ){
			System.out.println("Message error");
		}
	}
}