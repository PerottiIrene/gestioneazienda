package it.prova.gestioneagenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestioneagenda.model.Agenda;
import it.prova.gestioneagenda.model.Utente;
import it.prova.gestioneagenda.repository.agenda.AgendaRepository;
import it.prova.gestioneagenda.security.JWTAuthEntryPoint;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService{
	
	@Autowired
	private AgendaRepository repository;
	
	@Autowired
	private UtenteService utenteService;

	@Override
	public List<Agenda> listAllElements(boolean eager) {
		if (eager)
			return (List<Agenda>) repository.findAllAgendaEager();

		return (List<Agenda>) repository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteInSessione=utenteService.findByUsername(username);
		return repository.findById(id).orElse(null);
	}

	@Override
	public Agenda caricaSingoloElementoEager(Long id) {
		return repository.findSingleAgendaEager(id);
	}

	@Override
	public Agenda aggiorna(Agenda agendaInstance) {
		return repository.save(agendaInstance);
	}

	@Override
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		return repository.save(agendaInstance);
	}

	@Override
	public void rimuovi(Long idToRemove) {
		repository.deleteById(idToRemove);
	}

	@Override
	public List<Agenda> findByExample(Agenda example) {
		return repository.findByExample(example);
	}

	@Override
	public List<Agenda> findByDescrizione(String descrizione) {
		return repository.findByDescrizione(descrizione);
	}

	@Override
	public List<Agenda> agendeByUtente() {
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteInSessione=utenteService.findByUsername(username);
		return (List<Agenda>) repository.agendeUtente(utenteInSessione.getId());
	}

}
