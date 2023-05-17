package it.prova.myebay.repository.acquisto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import it.prova.myebay.model.Acquisto;

public class CustomAcquistoRepositoryImpl implements CustomAcquistoRepository{
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Acquisto> findByExample(Acquisto example) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
