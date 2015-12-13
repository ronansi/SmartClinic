package br.com.smartclinic.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.smartclinic.model.Paciente;
import br.com.smartclinic.model.Pessoa;
import br.com.smartclinic.service.PacienteService;

@ManagedBean
@ViewScoped
public class PacienteController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PacienteService pacienteService;
	private Paciente paciente;
	
	@PostConstruct
	private void init(){
		pacienteService = PacienteService.getInstance();
	}
	
	public List<Paciente> pacientesAutoComplete(String query){
		Paciente pacienteTemp = new Paciente();
		pacienteTemp.setPessoa(new Pessoa());
		pacienteTemp.getPessoa().setNome(query);
		return pacienteService.listar(pacienteTemp);
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
