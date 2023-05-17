package it.prova.myebay.service;

import java.util.List;

import it.prova.myebay.model.Acquisto;

public interface AcquistoService {

	public List<Acquisto> listAll();
	
	public Acquisto caricaSingoloElemento(Long id);
	
	public Acquisto caricaElementoConUtente(Long id);
	
	public void inserisciNuovoAcquisto(Long idAnnuncio);
	
	public List<Acquisto> findByExampleRicerca(Acquisto example);
	
	public List<Acquisto> miaLista();
	

}
