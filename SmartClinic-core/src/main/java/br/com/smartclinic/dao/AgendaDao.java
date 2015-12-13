package br.com.smartclinic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.smartclinic.model.Agenda;

public class AgendaDao extends ParentDao{
	
	private static final long serialVersionUID = 1L;
	
	private static AgendaDao instance;
	
	public static AgendaDao getInstance(){
		if(instance == null){
			instance = new AgendaDao();
		}
		return instance;
	}
	
	public Agenda inserir(Agenda agenda, boolean confirmaTransacao){
		agenda = super.incluir(agenda);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
		
		return agenda;
	}
	
	public Agenda alterar(Agenda agenda, boolean confirmaTransacao){
		agenda = super.alterar(agenda);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
		
		return agenda;
	}
	
	public Agenda consultarPorId(Long id){
		Agenda result = super.consultarPorId(Agenda.class, id);
		return result;
	}
	
	public List<Agenda> listar(Agenda agenda){
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(" select bean from ").append(Agenda.class.getName()).append(" bean ");
		hql.append(" inner join bean.medico medico ");
		hql.append(" where 1 = 1 ");
		
		if(agenda.getMedico() != null){
			if(agenda.getMedico().getId() != null){
				hql.append(" and medico.id = :medicoId ");
				parametros.put("medicoId", agenda.getMedico().getId());
			}
			
			if(agenda.getMedico().getCrm() != null){
				hql.append(" and medico.crm = :medicoCrm ");
				parametros.put("medicoCrm", agenda.getMedico().getCrm());
			}
		}
		
		return super.listar(Agenda.class, hql, parametros);
	}
}
