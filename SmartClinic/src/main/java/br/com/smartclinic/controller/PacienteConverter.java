package br.com.smartclinic.controller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.smartclinic.model.Paciente;
import br.com.smartclinic.service.PacienteService;

@FacesConverter("pacienteConverter")
public class PacienteConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value != null && value.trim().length() > 0){
			return PacienteService.getInstance().consultarPorId(new Long(value));
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value != null){
			return ((Paciente)value).getId().toString();
		}
		return null;
	}

}
