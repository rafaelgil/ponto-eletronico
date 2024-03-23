package br.com.fiap.postech.pontoeletronico.pontoeletronico.service

import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart


@Service
class EmailService {

    fun enviarEmail(destinatario_email: String, assunto: String, mensagem: String) {
        val properties = Properties().apply {
            put("mail.smtp.host", "smtp.gmail.com")
            put("mail.smtp.socketFactory.port", "465")
            put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            put("mail.smtp.auth", "true")
            put("mail.smtp.port", "465") // Substitua pela porta SMTP (geralmente 587 para TLS)
        }

        val session = Session.getInstance(properties, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication("pontoeletronicofiap@gmail.com", "pcbp wtik vpib tjlu")
            }
        })

        val destinatario = InternetAddress.parse(destinatario_email)

        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress("pontoeletronicofiap@gmail.com"))
            message.setRecipients(javax.mail.Message.RecipientType.TO, destinatario)
            message.subject = assunto
            val htmlPart = MimeBodyPart().apply {
                setContent(mensagem, "text/html; charset=utf-8")
            }
            val multipart = MimeMultipart().apply {
                addBodyPart(htmlPart)
            }
            message.setContent(multipart)

            Transport.send(message)
            println("E-mail enviado com sucesso!")
        } catch (e: MessagingException) {
            e.printStackTrace()
        }
    }
}