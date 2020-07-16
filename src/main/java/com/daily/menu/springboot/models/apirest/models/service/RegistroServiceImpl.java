package com.daily.menu.springboot.models.apirest.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.daily.menu.springboot.models.apirest.models.dao.IregistroDao;
import com.daily.menu.springboot.models.entity.Pais;
import com.daily.menu.springboot.models.entity.Region;
import com.daily.menu.springboot.models.entity.registro;

@Service
public class RegistroServiceImpl implements IregistroServiceImp {
	@Autowired
	private IregistroDao registroDao;
	@Override
	@Transactional(readOnly = true)
	 public List<registro> findall() {
		return (List<registro>) registroDao.findAll();
	}
	 
	 
	@Override
	@Transactional(readOnly = true)
		public Page<registro> findall(Pageable pegeable) {
			return registroDao.findAll(pegeable);
	}
		
	@Override
	@Transactional(readOnly = true)
	public registro finById(Long id) {
		return registroDao.findById(id).orElse(null);
	}
	@Override
	@Transactional
	public registro save(registro registro) {
		return registroDao.save(registro);
	}
	@Override
	@Transactional
	public void  delete(Long id) {
		 registroDao.deleteById(id);
	}


	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
		
		return registroDao.findAllRegiones();
	}


	@Override
	@Transactional(readOnly = true)
	public List<Pais> findAllPais() {
		return registroDao.findAllPais();
	}



	
	

}
