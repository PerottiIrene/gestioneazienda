package it.prova.gestioneagenda.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestioneagenda.service.AgendaService;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {
	
	@Autowired
	private AgendaService agendaService;

}
