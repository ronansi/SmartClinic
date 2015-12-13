package br.com.smartclinic.dao;

import br.com.smartclinic.model.Cidade;
import br.com.smartclinic.model.Compromisso;

public class CompromissoDao extends ParentDao{
	
	private static final long serialVersionUID = 1L;
	
	private static CompromissoDao instance;
	
	public static CompromissoDao getInstance(){
		if(instance == null){
			instance = new CompromissoDao();
		}
		return instance;
	}
	
	public Compromisso inserir(Compromisso compromisso, boolean confirmaTransacao){
		compromisso = super.incluir(compromisso);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
		
		return compromisso;
	}
	
	public Compromisso alterar(Compromisso compromisso, boolean confirmaTransacao){
		compromisso = super.alterar(compromisso);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
		
		return compromisso;
	}
	
	public Cidade consultarPorId(Long id){
		Cidade result = super.consultarPorId(Cidade.class, id);
		return result;
	}
}
