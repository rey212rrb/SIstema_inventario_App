package implementacion.Inventario.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServicio {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarNotificacion(String destinatario, String asunto, String mensaje) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setTo(destinatario);
        email.setSubject(asunto);
        email.setText(mensaje);
        email.setFrom("rosales.basurto.eduardorey.ipn@gmail.com");

        mailSender.send(email);
        System.out.println("Notificación enviada por correo a: " + destinatario);
    }
}