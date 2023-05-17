package it.prova.myebay.repository.annuncio;

import java.util.List;

import it.prova.myebay.model.Annuncio;

public interface CustomAnnuncioRepository {

	List<Annuncio> findByExampleRicerca (Annuncio example);
}