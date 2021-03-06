package br.com.smartclinic.controller;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.model.Medico;
import br.com.smartclinic.model.Pessoa;
import br.com.smartclinic.model.Usuario;
import br.com.smartclinic.model.enums.SexoEnum;
import br.com.smartclinic.model.enums.TipoUsuarioEnum;
import br.com.smartclinic.service.MedicoService;

@SessionScoped
@ManagedBean
public class Main {

	private MedicoService medicoService;
	
	private ScheduleModel agenda;
	
	private Medico medico;
	
	public ScheduleModel getAgenda() {
		return agenda;
	}

	public void setAgenda(ScheduleModel agenda) {
		this.agenda = agenda;
	}

	@PostConstruct
	public void init(){
		medicoService = MedicoService.getInstance();
		System.out.println("Bean Executado!");
		
		Medico medico = new Medico();
		medico.setCrm("54321");
		medico.setTempoConsulta(150L);
		
		medico.setPessoa(new Pessoa());
		medico.getPessoa().setNome("Nickson Tavares Camargo");
		medico.getPessoa().setCpf("91247020134");
		medico.getPessoa().setRg("999999");
		medico.getPessoa().setSexo(SexoEnum.FEMININO);
		
		medico.setUsuario(new Usuario());
		medico.getUsuario().setLogin("nickson");
		medico.getUsuario().setSenha("123456");
		medico.getUsuario().setTipoUsuario(TipoUsuarioEnum.ADMIN);
		
		try{
			medico = medicoService.inserir(medico, true);
		}catch(RegraNegocioException e){
			System.out.println(e.getMensagens());
		}
		
		agenda = new DefaultScheduleModel();
		agenda.addEvent(new DefaultScheduleEvent("Alguma Coisa", previousDay8Pm(), previousDay11Pm()));
	}
	
	private Date previousDay8Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 8);
         
        return t.getTime();
    }
	
	private Date previousDay11Pm() {
        Calendar t = (Calendar) today().clone();
        t.set(Calendar.AM_PM, Calendar.PM);
        t.set(Calendar.DATE, t.get(Calendar.DATE) - 1);
        t.set(Calendar.HOUR, 11);
         
        return t.getTime();
    }
	
	private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);
 
        return calendar;
    }
	
	public String getMessage(){
		return "Hello World JSF!";
	}

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}
}
