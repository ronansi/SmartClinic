package br.com.smartclinic.service;

import java.io.Serializable;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.bo.AgendaBO;
import br.com.smartclinic.dao.AgendaDao;
import br.com.smartclinic.model.Agenda;

public class AgendaService implements Serializable{
	private static final long serialVersionUID = -4992661773986502257L;
	private static AgendaService instance;
	private AgendaBO agendaBO;
	private AgendaDao agendaDao;
	
	private AgendaService(){
		agendaBO = AgendaBO.getInstance();
		agendaDao = AgendaDao.getInstance();
	}
	
	public static AgendaService getInstance(){
		if(instance == null){
			instance = new AgendaService();
		}
		return instance;
	}
	
	public Agenda inserir(Agenda agenda, boolean confirmaTransacao) throws RegraNegocioException{
		return agendaBO.inserir(agenda, confirmaTransacao);
	}
	
	public Agenda consultarPorIt(Long id){
		return agendaDao.consultarPorId(id);
	}
}
