package co.salutary.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe para envio de email
 * Para envio utilizando uma conta do gmail, considerar este link https://support.google.com/mail/answer/14257?rd=1
 *
 */
public class Email {
	/**
	 * Logger
	 */
	private static final Log logger = LogFactory.getLog(Email.class);
	
	/**
	 * Arquivo de propriedades
	 */
	private Properties properties;

	/**
	 * Construtor
	 * @throws IOException
	 */
	public Email() throws IOException {
		InputStream is = null;
		try {		
			properties = new Properties();
			is = this.getClass().getResourceAsStream("email.properties");
			properties.load(is);			
		} catch (Exception e) { 
			logger.error("Erro inesperado carregando email.properties.", e);
			throw e;
		}finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * Enviar email
	 * @param destinatario
	 * @param assunto
	 * @param mensagem
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void enviarEmail(String destinatario, String assunto, String mensagem)  
			throws AddressException, MessagingException {	
		enviarEmail(null, destinatario, assunto, mensagem);
	}

	/**
	 * Enviar email
	 * @param destinatario
	 * @param assunto
	 * @param mensagem
	 * @param anexo
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void enviarEmail(String destinatario, String assunto, String mensagem, byte[] anexo)  
			throws AddressException, MessagingException {
		enviarEmail(null, destinatario, assunto, mensagem, anexo);
	}

	/**
	 * Enviar email
	 * @param remetente
	 * @param destinatario
	 * @param assunto
	 * @param mensagem
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void enviarEmail(String remetente, String destinatario, String assunto, String mensagem)  
			throws AddressException, MessagingException {	

		try {
			Session session = Session.getDefaultInstance(properties, 
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.pass"));
				}
			});
			session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("content-Type", "text/html;charset=\"ISO-8859-1\"");
			msg.setSentDate(new Date());
			msg.setFrom(new InternetAddress(remetente != null ? remetente : properties.getProperty("mail.from")));
			msg.setSubject(assunto);                
			msg.setText(mensagem, "ISO-8859-1");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(properties.getProperty("mail.smtp.host"), properties.getProperty("mail.user"), properties.getProperty("mail.pass"));
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		}
		catch (Exception e)		{
			logger.error("Erro inesperado ao enviar email.", e);
			throw e;
		}
	}

	/**
	 * Enviar email
	 * @param remetente
	 * @param destinatario
	 * @param assunto
	 * @param mensagem
	 * @param anexo
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void enviarEmail(String remetente, String destinatario, String assunto, String mensagem, byte[] anexo)  
			throws AddressException, MessagingException {

		try{
			Session session = Session.getDefaultInstance(properties, 
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(properties.getProperty("mail.user"), properties.getProperty("mail.pass"));
				}
			});
			session.setDebug(true);

			MimeMessage msg = new MimeMessage(session);
			msg.setHeader("content-Type", "text/html;charset=\"ISO-8859-1\"");
			msg.setSentDate(new Date());
			msg.setFrom(new InternetAddress(remetente != null ? remetente : properties.getProperty("mail.from")));
			msg.setSubject(assunto);                
			msg.setText(mensagem, "ISO-8859-1");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));		

			// anexo
			MimeBodyPart messageBodyPart = new MimeBodyPart();  
			messageBodyPart.setText(mensagem);  
			Multipart multipart = new MimeMultipart("mixed");  
			multipart.addBodyPart(messageBodyPart);  
			messageBodyPart = new MimeBodyPart();  
			DataSource source = new ByteArrayDataSource(anexo, "application/pdf");  
			messageBodyPart.setDataHandler(new DataHandler(source));  
			messageBodyPart.setFileName("i-Voucher");  
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart); 
			msg.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(properties.getProperty("mail.smtp.host"), properties.getProperty("mail.user"), properties.getProperty("mail.pass"));
			transport.sendMessage(msg, msg.getAllRecipients());
			transport.close();
		} catch (Exception e)		{
			logger.error("Erro inesperado ao enviar email.", e);
			throw e;
		}
	}

	/**
	 * Teste
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Email email = new Email();
			email.enviarEmail("contact@salutary.co", "tpbvieira@gmail.com", "assunto novo", "corpo da mensagem");
		} catch (AddressException e) {
			
		} catch (MessagingException e) {	     
			
		} catch (Exception e) { 
			
		}
	}
}