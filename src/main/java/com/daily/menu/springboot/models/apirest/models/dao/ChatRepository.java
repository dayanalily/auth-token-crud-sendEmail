package com.daily.menu.springboot.models.apirest.models.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bolsadeideas.springboot.backend.chat.models.documents.Mensaje;

public interface ChatRepository extends MongoRepository<Mensaje, String>{
	
    public List<Mensaje> findFirst10ByOrderByFechaDesc();
}
