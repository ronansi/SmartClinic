package br.com.smartclinic.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.bo.PacienteBO;
import br.com.smartclinic.bo.PessoaBO;
import br.com.smartclinic.dao.PacienteDao;
import br.com.smartclinic.model.Paciente;

public class PacienteService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static PacienteService instance;
	private PacienteDao pacienteDao;
	private PessoaBO pessoaBO;
	private PacienteBO pacienteBO;
	
	private PacienteService(){
		pessoaBO = PessoaBO.getInstance();
		pacienteDao = PacienteDao.getInstance();
	}
	
	public static PacienteService getInstance(){
		if(instance == null){
			instance = new PacienteService();
		}
		return instance;
	}
	
	public List<Paciente> listar(Paciente paciente){
		return pacienteDao.listar(paciente, false);
	}
	
	public Paciente inserir(Paciente paciente, boolean confirmaTransacao) throws RegraNegocioException{
		List<String> mensagens = new ArrayList<String>();
		
		try{
			pessoaBO.validaRegrasNegocioInserirPessoa(paciente.getPessoa());
		}catch(RegraNegocioException e){
			mensagens.addAll(e.getMensagens());
		}
		
		if(mensagens.size() > 0){
			throw new RegraNegocioException(mensagens);
		}
		
		paciente = pacienteBO.inserir(paciente, confirmaTransacao);
		
		return paciente;
	}

	public Paciente consultarPorId(Long id) {
		return pacienteDao.consultarPorId(id);
	}

}
