package br.com.smartclinic.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.smartclinic.RegraNegocioException;
import br.com.smartclinic.bo.SecretarioBO;
import br.com.smartclinic.bo.PessoaBO;
import br.com.smartclinic.bo.UsuarioBO;
import br.com.smartclinic.dao.SecretarioDao;
import br.com.smartclinic.model.Secretario;
import br.com.smartclinic.model.enums.TipoUsuarioEnum;

public class SecretarioService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private static SecretarioService instance;
	private SecretarioDao secretarioDao;
	private SecretarioBO secretarioBO;
	private PessoaBO pessoaBO;
	private UsuarioBO usuarioBO;
	
	private SecretarioService(){
		secretarioDao = SecretarioDao.getInstance();
		secretarioBO = SecretarioBO.getInstance();
		pessoaBO = PessoaBO.getInstance();
		usuarioBO = UsuarioBO.getInstance();
	}
	
	public static SecretarioService getInstance(){
		if(instance == null){
			instance = new SecretarioService();
		}
		return instance;
	}
	
	public List<Secretario> listar(Secretario secretario){
		return secretarioDao.listar(secretario, false);
	}
	
	public Secretario inserir(Secretario secretario, boolean confirmaTransacao) throws RegraNegocioException{
		List<String> mensagens = new ArrayList<String>();
		
		try {
			secretario.getUsuario().setTipoUsuario(TipoUsuarioEnum.SECRETARIA);
			usuarioBO.validaRegrasNegocioInserir(secretario.getUsuario());
		} catch (RegraNegocioException e) {
			mensagens.addAll(e.getMensagens());
		}
		
		try{
			pessoaBO.validaRegrasNegocioInserirPessoa(secretario.getPessoa());
		}catch(RegraNegocioException e){
			mensagens.addAll(e.getMensagens());
		}
		
		if(mensagens.size() > 0){
			throw new RegraNegocioException(mensagens);
		}
		
		secretario = secretarioBO.inserir(secretario, confirmaTransacao);
		
		return secretario;
	}

}
