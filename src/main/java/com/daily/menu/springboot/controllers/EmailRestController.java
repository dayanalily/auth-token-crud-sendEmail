package com.daily.menu.springboot.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.daily.menu.springboot.models.apirest.models.dao.IUsuarioDao;
import com.daily.menu.springboot.models.apirest.models.service.UsuarioService;
import com.daily.menu.springboot.models.entity.Usuario;
import com.daily.menu.springboot.models.entity.registro;

import email.EmailBody;

@CrossOrigin(origins = { "http://localhost:4200", "*" })
@RestController
@RequestMapping("/send")
public class EmailRestController {
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private IUsuarioDao usuarioDao;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/**
	 * CREAR
	 */
	// @RequestBody para recibir un json
	@PostMapping("/email")

	public ResponseEntity<?> create(@RequestBody EmailBody email) {

		

		// EmailBody emailNew = null;
		Map<String, Object> response = new HashMap<>();
		Usuario usuario = null;

		usuario = usuarioDao.findByUsername(email.getEmail());
		
	
		
		try {
			
			if(usuario == null) {
				System.out.println("dayanaa" +  usuario);
				response.put("mensaje", "Error al enviar el email Ingresado: " +   email.getEmail()  + " no existe en la Base de Datos" );
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}else {
				email.setSubject("Restablecer la contraseña de Daily");
				email.setContent("¿Restablecer tu contraseña?  " +
				"Si solicitó un restablecimiento de contraseña para " + 
				usuario.getUsername()  + ",  " +
				" haga clic en el Link a continuación. Si no realizó esta solicitud, ignore este correo electrónico." + "  "+ "<a href='https://www.google.com'>Restablecer Contraseña</a>");
				
				sendEmail(email);
				response.put("mensaje", "El email ha sido enviado con èxito!");
				response.put("registro", email);;
			}
		
			// emailNew = EmailService.sendEmail(email);

		} catch (DataAccessException e) {
			// response.put("mensaje", "Error al enviar email");
			// response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	public boolean sendEmail(EmailBody emailBody) {

		return sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject());
	}

	private boolean sendEmailTool(String textMessage, String email, String subject) {
		boolean send = false;
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			helper.setTo(email);
			helper.setText(textMessage, true);
			helper.setSubject(subject);
			sender.send(message);
			send = true;
			// LOGGER.info("Mail enviado!");
		} catch (MessagingException e) {
			// LOGGER.error("Hubo un error al enviar el mail: {}", e);
		}
		return send;
	}

}
