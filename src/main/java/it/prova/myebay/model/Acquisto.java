package it.prova.myebay.model;

import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "acquisto")
public class Acquisto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "descrizione")
	private String descrizione;
	@Column(name = "dataacquisto")
	private LocalDate dataAcquisto;
	@Column(name = "prezzo")
	private Double prezzo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	private Utente utente;

	public Acquisto() {
		super();
	}

	public Acquisto(String descrizione, Double prezzo) {
		super();
		this.descrizione = descrizione;
		this.prezzo = prezzo;
	}

	

	public Acquisto(Long id, String descrizione, LocalDate dataAcquisto, Double prezzo, Utente utente) {
		super();
		this.id = id;
		this.descrizione = descrizione;
		this.dataAcquisto = dataAcquisto;
		this.prezzo = prezzo;
		this.utente = utente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public LocalDate getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(LocalDate dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

}
