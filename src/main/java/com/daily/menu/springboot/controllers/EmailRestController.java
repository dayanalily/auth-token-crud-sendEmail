package com.daily.menu.springboot.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	Date date = new Date();
	
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

		usuario = usuarioDao.findByEmail(email.getEmail());
				
		try {

			if (usuario == null) {
				System.out.println("dayanaa" + usuario);
				response.put("mensaje",
						"Error al enviar el email Ingresado: " + email.getEmail() + " no existe en la Base de Datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {

				
			String mensaje =	CrearMensaje(email ,  usuario);
				email.setSubject("Restablecer la contraseña de Daily");
				email.setContent(mensaje);
				sendEmail(email);
				response.put("mensaje", "El email ha sido enviado con èxito!");
				;
			}

			// emailNew = EmailService.sendEmail(email);

		} catch (DataAccessException e) {
			// response.put("mensaje", "Error al enviar email");
			// response.put("error", e.getMessage().concat(":
			// ").concat(e.getMostSpecificCause().getMessage()));
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
		} catch (MessagingException e) {
			
		}
		return send;
	}

	public String CrearMensaje(EmailBody emailBody, Usuario usuario) {
		DateFormat hourdateFormat = new SimpleDateFormat("HHmmssddMMyyyy");
		System.out.println("Hora y fecha: "+hourdateFormat.format(date));
		
		String mensaje = "<div style='border: 1px solid #4c309a; border-radius: 30px; width: 431px;text-align: center;position: relative; margin-right: auto; margin-left: auto;'>"
				+ "<span><img align='center; margin-top: 11px; width: 44px' src='https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTCKbvjdhSWuXxajCVzbgde3wQEUq3rnv_B6g&usqp=CAU'></span>" + "<span><br></span>"
				+ "<span ><H1 align='center\' ; color: blue> ¿Restablecer tu contraseña?</H1></span> "
				+ "<span><br></span>" + "<span ><H4 align='center\' ; color: black> " + "Hola  "
				+ usuario.getNombre().substring(0, 1).toUpperCase()
				+ usuario.getNombre().substring(1).toLowerCase() + " "
				+ usuario.getApellido().substring(0, 1).toUpperCase()
				+ usuario.getApellido().substring(1).toLowerCase()
				+ ", pediste recuperar tu Contraseña para el Usuario  </H4>" + "<H4 style='color: #4c309a'> " + usuario.getEmail() + "</H4>"
				+ "</span>"
				+ "<span><H4 align='center\' ; color: black>  haz clic en el Boton a continuación. </H1></span> "
				+ "<span align='center'> <button style=\"margin-left: auto;margin-right: auto; display: block; margin-top: 2%; margin-bottom: 0%;background: #4c309a;width: 200px; height: 41px; border-radius: 9px; border-color: #4c309a;\" name='button'><a style='color: #f7f8fb; text-decoration: none;' href='http://localhost:4200/restablecer/" + usuario.getId() + hourdateFormat.format(date) +"'"+ " >Restablecer Contraseña</a></button> </span> "
				+ "<span ><H4 align='center\' ; color: blue> Si no has pedido esta clave, ignora este correo."
				+ "</H1></span> "
				+ "<span ><H4 align='center\' ; color: blue> En caso de consulta llámanos al 600 000 DAILY"
				+ "</H1></span> " + "<span ><H4 align='center\' ; color: blue> Gracias" + "</H1></span> "
				+ " </div>";

		return mensaje ;
	}
}
