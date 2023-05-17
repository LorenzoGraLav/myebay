package it.prova.myebay.repository.annuncio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.myebay.model.Annuncio;

public class CustomAnnuncioRepositoryImpl implements CustomAnnuncioRepository{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Annuncio> findByExampleRicerca(Annuncio example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select a from Annuncio a left join fetch a.categorie c where a.id = a.id ");

		if (example.getCategorie() != null && !example.getCategorie().isEmpty()) {
			whereClauses.add(" c in :categorie ");
			paramaterMap.put("categorie", example.getCategorie());
		}
		if (StringUtils.isNotEmpty(example.getTestoAnnuncio())) {
			whereClauses.add(" a.testoAnnuncio  like :testoAnnuncio ");
			paramaterMap.put("testoAnnuncio", "%" + example.getTestoAnnuncio() + "%");
		}
		if (example.getPrezzo() != null && example.getPrezzo()>0) {
			whereClauses.add(" a.prezzo >= :prezzo ");
			paramaterMap.put("prezzo",example.getPrezzo());
		}
	
		whereClauses.add(" a.aperto = true ");
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Annuncio> typedQuery = entityManager.createQuery(queryBuilder.toString(), Annuncio.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}

		return typedQuery.getResultList();
	}
	
}
