package br.com.smartclinic.bo;

import java.io.Serializable;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.dao.PacienteDao;
import br.com.smartclinic.model.Paciente;

public class PacienteBO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static PacienteBO instance;
	private PacienteDao pacienteDAO;
	
	private PacienteBO(){
		this.pacienteDAO = PacienteDao.getInstance();
	}
	
	public static PacienteBO getInstance(){
		if(instance == null){
			instance = new PacienteBO();
		}
		return instance;
	}
	
	public Paciente inserir(Paciente paciente, boolean confirmaTransacao) throws RegraNegocioException{
		paciente = pacienteDAO.inserir(paciente, confirmaTransacao);
		
		return paciente;
	}
}
