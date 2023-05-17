package it.prova.myebay.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.model.Ruolo;
import it.prova.myebay.model.StatoUtente;
import it.prova.myebay.model.Utente;
import it.prova.myebay.validation.ValidationNoPassword;
import it.prova.myebay.validation.ValidationWithPassword;

public class UtenteDTO {

	// impostiamo dei parametri
	
	private Long id;

	// da studiare bene groups##################################
	@NotBlank(message = "{username.notblank}", groups = {ValidationWithPassword.class, ValidationNoPassword.class })
	@Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri")
	private String username;

	@NotBlank(message = "{password.notblank}", groups = ValidationWithPassword.class)
	@Size(min = 8, max = 15, message = "Il valore inserito deve essere lungo tra {min} e {max} caratteri")
	private String password;

	private String confermaPassword;

	@NotBlank(message = "{nome.notblank}", groups = { ValidationWithPassword.class, ValidationNoPassword.class })
	private String nome;

	@NotBlank(message = "{cognome.notblank}", groups = { ValidationWithPassword.class, ValidationNoPassword.class })
	private String cognome;

	private Double creditoResiduo;

	private LocalDate dateCreated;

	private StatoUtente stato;

	private Long[] ruoliIds;

	private Set<Annuncio> annunci = new HashSet<>();

	private Set<Acquisto> acquisti = new HashSet<>();

	public UtenteDTO() {
	}

	public UtenteDTO(Long id, @NotBlank(message = "{username.notblank}", groups = { ValidationWithPassword.class,
			ValidationNoPassword.class }) @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String username,
			@NotBlank(message = "{nome.notblank}", groups = { ValidationWithPassword.class,
					ValidationNoPassword.class }) String nome,
			@NotBlank(message = "{cognome.notblank}", groups = { ValidationWithPassword.class,
					ValidationNoPassword.class }) String cognome,
			StatoUtente stato) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.stato = stato;
	}

	public UtenteDTO(Long id, @NotBlank(message = "{username.notblank}", groups = { ValidationWithPassword.class,
			ValidationNoPassword.class }) @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String username,
			@NotBlank(message = "{nome.notblank}", groups = { ValidationWithPassword.class,
					ValidationNoPassword.class }) String nome,
			@NotBlank(message = "{cognome.notblank}", groups = { ValidationWithPassword.class,
					ValidationNoPassword.class }) String cognome,
			Double creditoResiduo, StatoUtente stato, Set<Annuncio> annunci) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.creditoResiduo = creditoResiduo;
		this.stato = stato;
		this.annunci = annunci;
	}

	public UtenteDTO(Long id, @NotBlank(message = "{username.notblank}", groups = { ValidationWithPassword.class,
			ValidationNoPassword.class }) @Size(min = 3, max = 15, message = "Il valore inserito '${validatedValue}' deve essere lungo tra {min} e {max} caratteri") String username,
			@NotBlank(message = "{nome.notblank}", groups = { ValidationWithPassword.class,
					ValidationNoPassword.class }) String nome,
			@NotBlank(message = "{cognome.notblank}", groups = { ValidationWithPassword.class,
					ValidationNoPassword.class }) String cognome,
			Double creditoResiduo, StatoUtente stato, Set<Annuncio> annunci, Set<Acquisto> acquisti) {
		super();
		this.id = id;
		this.username = username;
		this.nome = nome;
		this.cognome = cognome;
		this.creditoResiduo = creditoResiduo;
		this.stato = stato;
		this.annunci = annunci;
		this.acquisti = acquisti;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Double getCreditoResiduo() {
		return creditoResiduo;
	}

	public void setCreditoResiduo(Double creditoResiduo) {
		this.creditoResiduo = creditoResiduo;
	}

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}

	public StatoUtente getStato() {
		return stato;
	}

	public void setStato(StatoUtente stato) {
		this.stato = stato;
	}

	public String getConfermaPassword() {
		return confermaPassword;
	}

	public void setConfermaPassword(String confermaPassword) {
		this.confermaPassword = confermaPassword;
	}

	public Long[] getRuoliIds() {
		return ruoliIds;
	}

	public void setRuoliIds(Long[] ruoliIds) {
		this.ruoliIds = ruoliIds;
	}

	public Set<Annuncio> getAnnunci() {
		return annunci;
	}

	public void setAnnunci(Set<Annuncio> annunci) {
		this.annunci = annunci;
	}

	
	
	public Set<Acquisto> getAcquisti() {
		return acquisti;
	}

	public void setAcquisti(Set<Acquisto> acquisti) {
		this.acquisti = acquisti;
	}

	public boolean isAttivo() {
		return this.stato != null && this.stato.equals(StatoUtente.ATTIVO);
	}

	public Utente buildUtenteModel(boolean includeIdRoles) {
		Utente result = new Utente(this.id, this.username, this.password, this.nome, this.cognome, this.dateCreated,
				this.stato, this.creditoResiduo, this.annunci, this.acquisti);
		if (includeIdRoles && ruoliIds != null)
			result.setRuoli(Arrays.asList(ruoliIds).stream().map(id -> new Ruolo(id)).collect(Collectors.toSet()));

		return result;
	}

	// niente password...
	public static UtenteDTO buildUtenteDTOFromModel(Utente utenteModel, boolean includeRoles) {
		UtenteDTO result = new UtenteDTO(utenteModel.getId(), utenteModel.getUsername(), utenteModel.getNome(),
				utenteModel.getCognome(), utenteModel.getCreditoResiduo(), utenteModel.getStato(),
				utenteModel.getAnnunci(), utenteModel.getAcquisti());

		if (includeRoles && !utenteModel.getRuoli().isEmpty())
			result.ruoliIds = utenteModel.getRuoli().stream().map(r -> r.getId()).collect(Collectors.toList())
					.toArray(new Long[] {});

		return result;
	}

	public static List<UtenteDTO> createUtenteDTOListFromModelList(List<Utente> modelListInput, boolean includeRoles) {
		return modelListInput.stream().map(utenteEntity -> {
			return UtenteDTO.buildUtenteDTOFromModel(utenteEntity, includeRoles);
		}).collect(Collectors.toList());
	}
}
