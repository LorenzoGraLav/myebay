package it.prova.myebay.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.exception.UtenteNonTrovatoException;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.annuncio.AnnuncioRepository;


@Service
public class AnnuncioServiceImpl implements AnnuncioService{

	
	@Autowired
	private AnnuncioRepository annuncioRepository;
	
	@Autowired
	private UtenteService utenteService;
	
	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> listAll() {
		return (List<Annuncio>) annuncioRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio caricaSingoloElemento(Long id) {
		return annuncioRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio caricaSingoloElementoConCategorie(Long id) {
		return annuncioRepository.findByIdConCategorie(id).orElse(null);
	}

	// quando aggiorniamo dobbiamo controllare se l'utente che vuole fare questa operaione
	// esista nel db
	// creare una eccezione apposta da richiamare con il try catch nei controller
	// un annuncio può essere modificato se l'utente è lo stesso ad averlo creato
	// se è aperto
	// settare la data nuova
	@Override
	@Transactional
	public void aggiorna(Annuncio annuncioInstance) {
		//riga di codice che ci permette di risalire all'username/email (per il login)
		// e salvaro in una stringa
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Utente utenteFromDb = utenteService.findByUsername(username);
		
		if (utenteFromDb == null)
		throw new UtenteNonTrovatoException();
		
		// verificare se l'annuncio effettivamente è aperto!!
		// lanciare eccezione
		if (!annuncioInstance.isAperto()) {
			throw new AnnuncioChiusoException();
		} 
		
		annuncioInstance.setAperto(true);
		annuncioInstance.setDataCreazione(LocalDate.now());
		annuncioInstance.setUtente(utenteFromDb);
		annuncioRepository.save(annuncioInstance);
	}

	// per inserire un annuncio devi essere loggato
	// l'annuncio deve per forza avere un utente dentro
	// settare l'annuncio come aperto e data attuale
	@Override
	@Transactional
	public void inserisciNuovo(Annuncio annuncioInstance) {
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteFromDb = utenteService.findByUsername(username);
		
		if (utenteFromDb == null)
		throw new UtenteNonTrovatoException();
		
		annuncioInstance.setUtente(utenteFromDb);
		annuncioInstance.setAperto(true);
		annuncioInstance.setDataCreazione(LocalDate.now());
		annuncioRepository.save(annuncioInstance);
	}

	// per rimuovere un annuncio deve essere aperto
	// devi essere loggato e stessa persona
	@Override
	@Transactional
	public void rimuovi(Long idAnnuncio) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Utente utenteFromDb = utenteService.findByUsername(username);
		
		Annuncio annuncioFromDB= this.caricaSingoloElemento(idAnnuncio);
		
		if (utenteFromDb == null)
			throw new UtenteNonTrovatoException();
		
		if (!annuncioFromDB.isAperto()) {
			throw new AnnuncioChiusoException();
		}
		// controlla se utente è lo stesso dell'annuncio
		if (annuncioFromDB.getUtente() != utenteFromDb) {
			throw new RuntimeException();
		}
		annuncioRepository.deleteById(idAnnuncio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> findByExampleRicerca(Annuncio example) {
		return annuncioRepository.findByExampleRicerca(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Annuncio caricaElementoConUtente(Long id) {
		return annuncioRepository.findByIdConUtente(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> listaAnnunciAperti() {
		return annuncioRepository.listAllAperti();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Annuncio> miaLista() {	
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return annuncioRepository.myList(utenteService.findByUsername(username).getId());
		
	}
	
	

}
