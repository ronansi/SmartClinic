package br.com.smartclinic.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.dao.AgendaDao;
import br.com.smartclinic.model.Agenda;

public class AgendaBO implements Serializable{
	
	private static final long serialVersionUID = 6873918009388753159L;
	private static AgendaBO instance;
	private AgendaDao agendaDao;
	
	private AgendaBO(){
		agendaDao = AgendaDao.getInstance();
	}
	
	public static AgendaBO getInstance(){
		if(instance == null){
			instance = new AgendaBO();
		}
		return instance;
	}
	
	public Agenda inserir(Agenda agenda, boolean confirmaTransacao) throws RegraNegocioException{
		validaRegrasNegocioInserir(agenda);
		
		agenda = agendaDao.inserir(agenda, confirmaTransacao);
		
		return agenda;
	}

	/**
	 * Regras de negocio: <br>
	 * 1 - Não podem haver mais de 1 agenda para o mesmo medico<br>
	 * @param cidade
	 * @return
	 * @throws RegraNegocioException 
	 */
	private void validaRegrasNegocioInserir(Agenda agenda)
			throws RegraNegocioException {
		Agenda agendaTemp;
		List<Agenda> agendas;
		List<String> mensagens = new ArrayList<String>();
		
		// Valida Regras de negócio
		//[1]
		agendaTemp = new Agenda();
		agendaTemp.setMedico(agenda.getMedico());
		agendas = agendaDao.listar(agendaTemp);
		if(agendas != null && agendas.size() > 0){
			mensagens.add("Já existe uma agenda para este médico!");
		}
		
		if(mensagens.size() > 0){
			throw new RegraNegocioException(mensagens);
		}
	}

}
