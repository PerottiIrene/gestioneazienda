package it.prova.gestioneagenda.repository.agenda;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.prova.gestioneagenda.model.Agenda;

public class CustomAgendaRepositoryImpl implements CustomAgendaRepository{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Agenda> findByExample(Agenda example) {
		// TODO Auto-generated method stub
		return null;
	}

}
