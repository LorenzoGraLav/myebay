package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Utente;

public class AcquistoDTO {

	// le pagine DTO devono essere una copia del model, mai passare un entità nelle
	// jsp.

	private Long id;

	// nella validazione dobbiamo rendere not blank e not null la descrizione e il
	// prezzp


	private String descrizione;

	private LocalDate dataAcquisto;

	private Double prezzo;

	private UtenteDTO utente;

	// costruttore a due parametri, le altre le setteremo a mano

	public AcquistoDTO() {
		super();
	}

	public AcquistoDTO(@NotBlank(message = "{testoAnnuncio.notblank}") String descrizione, double prezzo) {
		super();
		this.descrizione = descrizione;
		this.prezzo = prezzo;
	}

	// implementiamo i metodi che trasformeranno il model in dto e viceversa

	// con questo metodo , trasformiano il dto in model
	// come parametri prendiamo due boolean per aperto e categorie
	// il primo se l'annuncio è aperto oppure chiuso
	// il secondo, invece se vogliamo includere anche le categorie "caricamento
	// eager"

	public AcquistoDTO(Long id, @NotBlank(message = "{testoAnnuncio.notblank}") String descrizione,
			LocalDate dataAcquisto, @NotNull(message = "{prezzoAnnuncio.notnull}") @Min(0) Double prezzo,
			UtenteDTO utente) {
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

	public UtenteDTO getUtenteDTO() {
		return utente;
	}

	public void setUtenteDTO(UtenteDTO utente) {
		this.utente = utente;
	}

	public Acquisto buildAcquistooModel() {
		
		Utente utenteModel = this.utente != null ? this.utente.buildUtenteModel(true) : null;
		// facciamo una new del model e diamo i valori di this.
		Acquisto result = new Acquisto(this.id, this.descrizione, this.dataAcquisto, this.prezzo, utenteModel);

		return result;
	}

	// in questo invece trasformiano il model in dto
	// inserendo come parametro il model e il boolean per le categorie
	public static AcquistoDTO buildAcquistoDTOFromModel(Acquisto acquistoModel) {

		// una new di dto e diamo i valori dell'annuncio come parametro
		AcquistoDTO result = new AcquistoDTO(acquistoModel.getId(), acquistoModel.getDescrizione(),
				acquistoModel.getDataAcquisto(), acquistoModel.getPrezzo(), UtenteDTO.buildUtenteDTOFromModel(acquistoModel.getUtente(),true));

		return result;
	}

	// una new list dto da una lista model
	public static List<AcquistoDTO> createAcquistoDTOListFromModelList(List<Acquisto> modelListInput) {
		return modelListInput.stream().map(acquistoEntity -> {
			return AcquistoDTO.buildAcquistoDTOFromModel(acquistoEntity);
		}).collect(Collectors.toList());
	}

}
