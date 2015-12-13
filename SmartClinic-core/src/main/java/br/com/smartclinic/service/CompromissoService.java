package br.com.smartclinic.service;

import java.io.Serializable;

import br.com.smartclinic.dao.CompromissoDao;
import br.com.smartclinic.model.Compromisso;

public class CompromissoService implements Serializable{
	private static final long serialVersionUID = -4992661773986502257L;
	private static CompromissoService instance;
	private CompromissoDao compromissoDao;
	
	private CompromissoService(){
		compromissoDao = CompromissoDao.getInstance();
	}
	
	public static CompromissoService getInstance(){
		if(instance == null){
			instance = new CompromissoService();
		}
		return instance;
	}
	
	public Compromisso inserir(Compromisso compromisso, boolean confirmaTransacao){
		return compromissoDao.inserir(compromisso, confirmaTransacao);
	}
	
	public Compromisso alterar(Compromisso compromisso, boolean confirmaTransacao){
		return compromissoDao.alterar(compromisso, confirmaTransacao);
	}
}
