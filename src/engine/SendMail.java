package engine;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public static void sendMail(String to1, String sadrzaj) {
		System.out.println("Poruka se salje....");

		String from = "asdf88039@gmail.com"; // currentUser.getMail();
		String password = "mnp7RCshJ34a7V6";

		// Get system properties
		Properties properties = new Properties();

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		// Get the default Session object.
		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication(from, password);
			}
		});

		try {
			Message message = prepareMessage(session, from, to1, sadrzaj);

			// Send message
			Transport.send(message);
			System.out.println("Poruka poslana....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private static Message prepareMessage(Session session, String from, String to1, String sadrzaj) {

		try {
			Message m = new MimeMessage(session);
			m.setFrom(new InternetAddress(from));
			m.setRecipient(Message.RecipientType.TO, new InternetAddress(to1));
			m.setText(sadrzaj);
			return m;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return null;
	}
}
