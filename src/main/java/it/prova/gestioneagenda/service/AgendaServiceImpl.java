package it.prova.gestioneagenda.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestioneagenda.model.Agenda;
import it.prova.gestioneagenda.model.Ruolo;
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
		return repository.findById(id).orElse(null);
	}

	@Override
	public Agenda caricaSingoloElementoEager(Long id) {
		return repository.findSingleAgendaEager(id);
	}

	@Override
	public Agenda aggiorna(Agenda agendaInstance) {
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteInSessione=utenteService.findByUsername(username);
		agendaInstance.setUtente(utenteInSessione);
		return repository.save(agendaInstance);
	}

	@Override
	public Agenda inserisciNuovo(Agenda agendaInstance) {
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteInSessione=utenteService.findByUsername(username);
		agendaInstance.setUtente(utenteInSessione);
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
		for (Ruolo ruoloItem : utenteInSessione.getRuoli())
			if(ruoloItem.getCodice().equals("ROLE_ADMIN")) return (List<Agenda>) repository.findAllAgendaEager();
		return (List<Agenda>) repository.agendeUtente(utenteInSessione.getId());
	}

	@Override
	public void firstInstert() {
		Agenda agendaAdmin=new Agenda("agendaAdmin",LocalDateTime.of(2022,Month.JULY,28,19,30,35),
				LocalDateTime.of(2022,Month.SEPTEMBER,18,19,30,35));
		agendaAdmin.setUtente(utenteService.findByUsername("admin"));
		if(findByDescrizione(agendaAdmin.getDescrizione()).isEmpty()) {
			repository.save(agendaAdmin);
		}
		
		Agenda agendaUser=new Agenda("agendaUser",LocalDateTime.of(2022,Month.JULY,30,19,30,35),
				LocalDateTime.of(2022,Month.SEPTEMBER,18,19,30,35));
		agendaUser.setUtente(utenteService.findByUsername("user"));
		if(findByDescrizione(agendaUser.getDescrizione()).isEmpty()) {
			repository.save(agendaUser);
		}
		
	}

}
