package br.com.smartclinic.controller;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.model.Agenda;
import br.com.smartclinic.model.Compromisso;
import br.com.smartclinic.model.Consulta;
import br.com.smartclinic.model.Medico;
import br.com.smartclinic.model.Paciente;
import br.com.smartclinic.model.enums.TipoCompromissoEnum;
import br.com.smartclinic.service.AgendaService;
import br.com.smartclinic.service.CompromissoService;
import br.com.smartclinic.utils.Util;

@ManagedBean
@ViewScoped
public class AgendaController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{main.medico}")
	private Medico medico;
	
	private Paciente paciente;
	
	private ScheduleModel agenda;
	private ScheduleEvent event;
	private CompromissoService compromissoService;
	private AgendaService agendaService;
	
	@PostConstruct
	private void init(){
		agenda = new DefaultScheduleModel();
		
		if(medico != null){
			populateSchedule(medico.getAgenda());
		}
		
		compromissoService = CompromissoService.getInstance();
		agendaService = AgendaService.getInstance();
	}
	
	public void onDateSelect(SelectEvent selectEvent) {
        event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
    }
	
	public void addEvent(ActionEvent actionEvent){
		if(medico.getAgenda() == null){
			Agenda agenda = new Agenda();
			agenda.setMedico(medico);
			try {
				agendaService.inserir(agenda, true);
			} catch (RegraNegocioException e) {
				e.printStackTrace();
			}
			medico.setAgenda(agenda);
		}
		
		Consulta consultaTemp = new Consulta();
		consultaTemp.setPaciente(paciente);
		
		Compromisso compromisso = new Compromisso();
		compromisso.setConsulta(consultaTemp);
		compromisso.setDataInicial(event.getStartDate());
		compromisso.setDataFinal(Util.addMinutesToDate(medico.getTempoConsulta().intValue(), event.getStartDate()));
		compromisso.setTitulo("Consulta " + paciente.getPessoa().getNome());
		compromisso.setDescricao("Consulta " + paciente.getPessoa().getNome());
		compromisso.setAgenda(medico.getAgenda());
		compromisso.setTipo(TipoCompromissoEnum.CONSULTA);
		
		if(event.getId() != null && !event.getId().equals("")){
			compromisso.setId(Long.valueOf(event.getId()));
			compromissoService.alterar(compromisso, true);
		}else{
			compromissoService.inserir(compromisso, true);
		}
		
		if(event.getId() != null){
			agenda.updateEvent(getScheduleEvent(compromisso));
		}else{
			agenda.addEvent(getScheduleEvent(compromisso));
		}
		
		medico.setAgenda(agendaService.consultarPorIt(medico.getAgenda().getId()));
		event = new DefaultScheduleEvent();
		paciente = null;
	}

	public ScheduleModel getAgenda() {
		return agenda;
	}

	public void setAgenda(ScheduleModel agenda) {
		this.agenda = agenda;
	}
	
	private ScheduleEvent getScheduleEvent(Compromisso compromisso){
		ScheduleEvent event = new DefaultScheduleEvent(compromisso.getTitulo(), compromisso.getDataInicial(), compromisso.getDataFinal(), compromisso.getConsulta());
		if(compromisso.getId() != null)
			event.setId(compromisso.getId().toString());
		return event;
	}
	
	private void populateSchedule(Agenda agenda){
		if(agenda != null && agenda.getCompromissos() != null){
			for(Compromisso compromisso : agenda.getCompromissos()){
				ScheduleEvent event = new DefaultScheduleEvent(compromisso.getTitulo(), compromisso.getDataInicial(), compromisso.getDataFinal(), compromisso.getConsulta());
				event.setId(compromisso.getId().toString());
				this.agenda.addEvent(event);
			}
		}
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}
}
