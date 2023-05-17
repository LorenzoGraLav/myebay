package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Categoria;
import it.prova.myebay.model.Utente;

public class AnnuncioDTO {

	// le pagine DTO devono essere una copia del model, mai passare un entità nelle jsp.
	
	private Long id;

	// scelgo che il testo è il prezzo (minimo0) non devono essere lasciati vuoi
	// all'interno del valore del messaggio abbiamo un valore di default che scriveremo in messagesProperties
	
	@NotBlank(message = "{testo.notblank}")
	@Size(min = 4, max = 40, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String testoAnnuncio;

	@NotNull(message = "{prezzoAnnuncio.notnull}")
	@Min(0)
	private Double prezzo;

	private LocalDate dataCreazione;

	private boolean aperto;

	private UtenteDTO utente;

	private Long[] categorieIds;

	public AnnuncioDTO() {
		super();
	}

	// creazione dei costruttori
	
	public AnnuncioDTO(Long id, @NotBlank(message = "{testoAnnuncio.notblank}") String testoAnnuncio,
			@NotNull(message = "{prezzoaannuncio.notnull}") @Min(0) Double prezzo, LocalDate dataCreazione,
			UtenteDTO utente) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.dataCreazione = dataCreazione;
		this.utente = utente;
	}

	public AnnuncioDTO(Long id, @NotBlank(message = "{testoAnnuncio.notblank}") String testoAnnuncio,
			@NotNull(message = "{prezzoaannuncio.notnull}") @Min(0) Double prezzo, LocalDate dataCreazione,
			boolean aperto,UtenteDTO utente, Long[] categorieIds) {
		super();
		this.id = id;
		this.testoAnnuncio = testoAnnuncio;
		this.prezzo = prezzo;
		this.dataCreazione = dataCreazione;
		this.aperto = aperto;
		this.utente = utente;
		this.categorieIds = categorieIds;
	}

	// metodi get e set
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTestoAnnuncio() {
		return testoAnnuncio;
	}

	public void setTestoAnnuncio(String testoAnnuncio) {
		this.testoAnnuncio = testoAnnuncio;
	}

	public Double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}

	public LocalDate getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(LocalDate dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public boolean isAperto() {
		return aperto;
	}

	public void setAperto(boolean aperto) {
		this.aperto = aperto;
	}

	public UtenteDTO getUtente() {
		return utente;
	}
	
	public void setUtente(UtenteDTO utente) {
		this.utente = utente;
	}


	public Long[] getCategorieIds() {
		return categorieIds;
	}

	public void setCategorieIds(Long[] categorieIds) {
		this.categorieIds = categorieIds;
	}
	
	//implementiamo i metodi che trasformeranno il model in dto e viceversa

	//con questo metodo , trasformiano il dto in model
	//come parametri prendiamo due boolean per aperto e categorie
	//il primo se l'annuncio è aperto oppure chiuso
	//il secondo, invece se vogliamo includere anche le categorie "caricamento eager"
	
	public Annuncio buildAnnuncioModel(boolean aperto, boolean includesCategories) {
		
		Utente utenteModel = this.utente != null ? this.utente.buildUtenteModel(true) : null;
		//facciamo una new del model e diamo i valori di this.
		Annuncio result = new Annuncio(this.id, this.testoAnnuncio, this.prezzo, this.dataCreazione, utenteModel);
		if (includesCategories && categorieIds != null) {
			result.setCategorie(
					Arrays.asList(categorieIds).stream().map(id -> new Categoria(id)).collect(Collectors.toSet()));
		}
		if (aperto) {
			result.setAperto(true);
		}
		return result;
	}

	
	
	// in questo invece trasformiano il model in dto
	// inserendo come parametro il model e il boolean per le categorie
	public static AnnuncioDTO buildAnnuncioDTOFromModel(Annuncio annuncioModel, boolean includesCategorie) {

		
		
		// una new di dto e diamo i valori dell'annuncio come parametro
		AnnuncioDTO result = new AnnuncioDTO(annuncioModel.getId(), annuncioModel.getTestoAnnuncio(),
				annuncioModel.getPrezzo(), annuncioModel.getDataCreazione(), UtenteDTO.buildUtenteDTOFromModel(annuncioModel.getUtente(),true));

		if (includesCategorie && !annuncioModel.getCategorie().isEmpty()) {
			result.categorieIds = annuncioModel.getCategorie().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});
		}
		if (annuncioModel.isAperto()) {
			result.aperto = true;
		} else {
			result.aperto = false;
		}
		return result;
	}

	//una new list dto da una lista model
	public static List<AnnuncioDTO> createAnnuncioDTOListFromModelList(List<Annuncio> modelListInput,
			boolean includesCategorie) {
		return modelListInput.stream().map(annuncioEntity -> {
			return AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioEntity, includesCategorie);
		}).collect(Collectors.toList());
	}

	

}
