package br.com.smartclinic.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.smartclinic.model.Paciente;
import br.com.smartclinic.model.Pessoa;

public class PacienteDao extends ParentDao{
	
	private static final long serialVersionUID = 1L;
	
	private static PacienteDao instance;
	
	public static PacienteDao getInstance(){
		if(instance == null){
			instance = new PacienteDao();
		}
		return instance;
	}
	
	public Paciente inserir(Paciente paciente, boolean confirmaTransacao){
		paciente = super.incluir(paciente);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
		
		return paciente;
	}
	
	public Paciente alterar(Paciente paciente, boolean confirmaTransacao){
		paciente = super.alterar(paciente);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
		
		return paciente;
	}
	
	public void excluir(Paciente paciente, boolean confirmaTransacao){
		super.excluir(paciente);
		
		if(confirmaTransacao){
			super.confirmaTransacao();
		}
	}
	
	public Paciente consultarPorId(Long id){
		Paciente result = super.consultarPorId(Paciente.class, id);
		return result;
	}
	
	public List<Paciente> listar(Paciente paciente, boolean isExato){
		StringBuilder hql = new StringBuilder();
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		hql.append(" select bean from ").append(Paciente.class.getName()).append(" bean ");
		hql.append(" inner join bean.pessoa pessoa ");
		hql.append(" where 1 = 1 ");
		
		if(paciente.getPessoa() != null){
			Pessoa pessoa = paciente.getPessoa();
			
			if(pessoa.getNome() != null && !pessoa.getNome().equals("")){
				if(isExato){
					hql.append(" and LOWER(pessoa.nome) = LOWER(:pessoaNome) ");
					parametros.put("pessoaNome", pessoa.getNome());
				}else{
					hql.append(" and LOWER(pessoa.nome) like LOWER(:pessoaNome) ");
					parametros.put("pessoaNome", "%" + pessoa.getNome() + "%");
				}
			}
			
			if(pessoa.getCpf() != null && !pessoa.getCpf().equals("")){
				if(isExato){
					hql.append(" and pessoa.cpf = :medicoCpf ");
					parametros.put("medicoCpf", pessoa.getCpf());
				}else{
					hql.append(" and pessoa.cpf like :medicoCpf ");
					parametros.put("medicoCpf", "%" + pessoa.getCpf() + "%");
				}
			}
			
			if(pessoa.getRg() != null && !pessoa.getRg().equals("")){
				if(isExato){
					hql.append(" and pessoa.rg = :medicoRg ");
					parametros.put("medicoRg", pessoa.getRg());
				}else{
					hql.append(" and pessoa.rg like :medicoRg ");
					parametros.put("medicoRg", "%" + pessoa.getRg() + "%");
				}
			}
		}
		
		return super.listar(Paciente.class, hql, parametros);
	}

}
