package it.prova.myebay.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import it.prova.myebay.exception.CreditoInsufficienteException;
import it.prova.myebay.model.StatoUtente;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.utente.UtenteRepository;

@Service
public class UtenteServiceImpl implements UtenteService {

	@Autowired
	private UtenteRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public List<Utente> listAll() {
		return (List<Utente>) repository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Utente caricaSingoloUtente(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Utente caricaSingoloUtenteConRuoli(Long id) {
		return repository.findByIdConRuoli(id).orElse(null);
	}

	@Override
	@Transactional
	public void aggiorna(Utente utenteInstance) {
		// deve aggiornare solo nome, cognome, username, ruoli
		Utente utenteReloaded = repository.findById(utenteInstance.getId()).orElse(null);
		if (utenteReloaded == null)
			throw new RuntimeException("Elemento non trovato");
		utenteReloaded.setNome(utenteInstance.getNome());
		utenteReloaded.setCognome(utenteInstance.getCognome());
		utenteReloaded.setUsername(utenteInstance.getUsername());
		utenteReloaded.setRuoli(utenteInstance.getRuoli());
		repository.save(utenteReloaded);

	}

	@Override
	@Transactional
	public void inserisciNuovo(Utente utenteInstance) {
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(LocalDate.now());
		utenteInstance.setCreditoResiduo(1200D);
		repository.save(utenteInstance);

	}

	@Override
	@Transactional
	public void rimuovi(Long idUtente) {
		repository.deleteById(idUtente);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Utente> findByExample(Utente example) {
		return repository.findByExample(example);
	}

	@Override
	@Transactional(readOnly = true)
	public Utente eseguiAccesso(String username, String password) {
		return repository.findByUsernameAndPasswordAndStato(username, password, StatoUtente.ATTIVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Utente findByUsernameAndPassword(String username, String password) {
		return repository.findByUsernameAndPassword(username, password);
	}

	@Override
	@Transactional
	public void changeUserAbilitation(Long utenteInstanceId) {
		Utente utenteInstance = caricaSingoloUtente(utenteInstanceId);
		if (utenteInstance == null)
			throw new RuntimeException("Elemento non trovato.");

		if (utenteInstance.getStato() == null || utenteInstance.getStato().equals(StatoUtente.CREATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
		else if (utenteInstance.getStato().equals(StatoUtente.ATTIVO))
			utenteInstance.setStato(StatoUtente.DISABILITATO);
		else if (utenteInstance.getStato().equals(StatoUtente.DISABILITATO))
			utenteInstance.setStato(StatoUtente.ATTIVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Utente findByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<String> ruoliUtenteSession() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {

			Utente utenteInstance = repository.findByUsername(auth.getName()).orElse(null);
			if (utenteInstance == null) {
				return new ArrayList<String>();
			}

			return utenteInstance.getRuoli().stream().map(ruolo -> ruolo.getCodice()).collect(Collectors.toList());
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isAutenticato() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
	
		if (auth != null) {
			Utente utenteInstance = repository.findByUsername(auth.getName()).orElse(null);
			if (utenteInstance == null) {
				return false;
			}
			
			// deve avere almeno un ruolo!
			return utenteInstance.getRuoli().size() > 0;

		}
		return false;
	}

	@Override
	public void registrazione(Utente utenteInstance) {
		utenteInstance.setCreditoResiduo(5D);
		utenteInstance.setStato(StatoUtente.CREATO);
		utenteInstance.setPassword(passwordEncoder.encode(utenteInstance.getPassword()));
		utenteInstance.setDateCreated(LocalDate.now());
		repository.save(utenteInstance);
		
	}

	@Override
	@Transactional
	public void ricarica(Double creditoRicarica) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Utente utente = repository.findByUsername(auth.getName()).orElse(null);
		
		
		
		if( utente.getCreditoResiduo() == null) {
			utente.setCreditoResiduo(0D);
			throw new CreditoInsufficienteException();
		}
		
		Double creditoDaInserie = utente.getCreditoResiduo() + creditoRicarica;
		utente.setCreditoResiduo( creditoDaInserie);
		
		
		if(utente.getCreditoResiduo() < 0) {
			utente.setCreditoResiduo(0D);
		}
		
	}

}