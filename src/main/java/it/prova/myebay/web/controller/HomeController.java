package it.prova.myebay.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.service.CategoriaService;
import it.prova.myebay.service.UtenteService;

@Controller
public class HomeController {

	@Autowired
	private UtenteService utenteService;

	@Autowired
	CategoriaService categoriaService;

	@RequestMapping(value = { "/home", "" })
	public String home(Model model) {

		if (utenteService.isAutenticato()) {
			return "utente/index";
		} else

		model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		model.addAttribute("search_annuncio_attr", new AnnuncioDTO());
		return "annuncio/search";
	}
}
