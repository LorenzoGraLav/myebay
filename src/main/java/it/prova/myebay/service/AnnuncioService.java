package it.prova.myebay.service;

import java.util.List;

import it.prova.myebay.model.Annuncio;

public interface AnnuncioService {

	public List<Annuncio> listAll();
	
	
	public Annuncio caricaSingoloElemento(Long id);
	
	public Annuncio caricaSingoloElementoConCategorie(Long id);
	
	public Annuncio caricaElementoConUtente(Long id);
	
	public void aggiorna (Annuncio annuncioInstance);
	
	public void inserisciNuovo (Annuncio annuncioInstance);
	
	public void rimuovi (Long idAnnuncio);
	
	public List<Annuncio> findByExampleRicerca(Annuncio example);
	
	public List<Annuncio> listaAnnunciAperti();
	
	public List<Annuncio> miaLista();
	

}
