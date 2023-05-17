package it.prova.myebay.web.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.dto.CategoriaDTO;
import it.prova.myebay.exception.AnnuncioChiusoException;
import it.prova.myebay.exception.UtenteNonTrovatoException;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.AnnuncioService;
import it.prova.myebay.service.CategoriaService;

@Controller
@RequestMapping("/annuncio")
public class AnnuncioController {

	@Autowired
	private AnnuncioService annuncioService;

	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public ModelAndView listAllAnnunci() {
		ModelAndView mv = new ModelAndView();
		List<AnnuncioDTO> result = AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.listaAnnunciAperti(),
				false);
		if (result.isEmpty()) {
			mv.addObject("successMessage", "la lista degli annunci è vuota! crea il tuo annuncio!");
			mv.setViewName("annuncio/list");
			return mv;
		}

		mv.addObject("annuncio_list_attr",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.listaAnnunciAperti(), false));
		mv.setViewName("annuncio/list");
		return mv;
	}

	@GetMapping("/miaLista")
	public String miaLista(Model model) {
		List<AnnuncioDTO> result = AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.miaLista(), true);
		if (result.isEmpty()) {
			model.addAttribute("errorMessage", "la lista annunci è vuota , crea ora il tuo annuncio!");
			return "annuncio/list";
		}
		
		model.addAttribute("annuncio_list_attr",
				AnnuncioDTO.createAnnuncioDTOListFromModelList(annuncioService.miaLista(), true));
		return "annuncio/list";
	}

	@GetMapping("/search")
	public String searchAnnuncio(Model model) {
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		model.addAttribute("search_annuncio_attr", new AnnuncioDTO());
		return "annuncio/search";
	}

	@PostMapping("/list")
	public String listAnnunci(AnnuncioDTO annuncioExample, ModelMap model) {
		List<AnnuncioDTO> result = AnnuncioDTO.createAnnuncioDTOListFromModelList(
				annuncioService.findByExampleRicerca(annuncioExample.buildAnnuncioModel(true, true)), true);
		if (result.isEmpty()) {
			model.addAttribute("errorMessage", "la lista annunci è vuota , crea tu un annuncio!");
			return "annuncio/list";
		}
		
		model.addAttribute("annuncio_list_attr", AnnuncioDTO.createAnnuncioDTOListFromModelList(
				annuncioService.findByExampleRicerca(annuncioExample.buildAnnuncioModel(true, true)), true));
		return "annuncio/list";
	}

	@GetMapping("/insert")
	public String create(Model model) {
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		model.addAttribute("insert_annuncio_attr", new AnnuncioDTO());
		return "annuncio/insert";
	}

	@PostMapping("/save")
	public String save(@Validated @ModelAttribute("insert_annuncio_attr") AnnuncioDTO annuncioDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
			return "annuncio/insert";
		}
		try {
			annuncioService.inserisciNuovo(annuncioDTO.buildAnnuncioModel(true, true));
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (UtenteNonTrovatoException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/annuncio";
		}
		return "redirect:/annuncio";

	}

	@GetMapping("/show/{idAnnuncio}")
	public String show(@PathVariable(required = true) Long idAnnuncio, Model model) {
		Annuncio annuncioModel = annuncioService.caricaSingoloElementoConCategorie(idAnnuncio);
		AnnuncioDTO annuncioDTO = AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel, true);
		model.addAttribute("show_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr", annuncioModel.getCategorie());
		return "annuncio/show";
	}

	@GetMapping("delete/{idAnnuncio}")
	public String deleteAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		Annuncio annuncioModel = annuncioService.caricaSingoloElementoConCategorie(idAnnuncio);
		AnnuncioDTO annuncioDTO = AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel, true);
		model.addAttribute("delete_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr", annuncioModel.getCategorie());
		return "annuncio/delete";
	}

	@PostMapping("/delete")
	public String delete(Long idAnnuncio, RedirectAttributes redirectAttrs) {
		try {
			annuncioService.rimuovi(idAnnuncio);
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (AnnuncioChiusoException e) {
			redirectAttrs.addFlashAttribute("errorMessage",
					"Attenzione! L'annuncio che stai cercando di eliminare è già chiuso.");
			return "redirect:/annuncio";
		} catch (UtenteNonTrovatoException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/annuncio";

		} catch (RuntimeException er) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Non puoi eliminare un annuncio non tuo.");
			return "redirect:/annuncio";
		}
		return "redirect:/annuncio";

	}

	@GetMapping("edit/{idAnnuncio}")
	public String editAnnuncio(@PathVariable(required = true) Long idAnnuncio, Model model) {
		Annuncio annuncioModel = annuncioService.caricaSingoloElementoConCategorie(idAnnuncio);
		AnnuncioDTO annuncioDTO = AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel, true);
		model.addAttribute("edit_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr",
				CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
		return "annuncio/edit";
	}

	@PostMapping("/edit")
	public String edit(@Validated @ModelAttribute("edit_annuncio_attr") AnnuncioDTO annuncioDTO, BindingResult result,
			Model model, RedirectAttributes redirectAttrs) {

		if (result.hasErrors()) {
			model.addAttribute("categorie_totali_attr",
					CategoriaDTO.createCategoriaDTOListFromModelList(categoriaService.listAll()));
			return "annuncio/edit";
		}
		try {
			annuncioService.aggiorna(annuncioDTO.buildAnnuncioModel(true, true));
			redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		} catch (AnnuncioChiusoException e) {
			redirectAttrs.addFlashAttribute("errorMessage",
					"Attenzione! L'annuncio che stai cercando di modificare è già chiuso.");
			return "redirect:/annuncio";
		} catch (UtenteNonTrovatoException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione! Utente non loggato.");
			return "redirect:/annuncio";
		}

		return "redirect:/annuncio";

	}
	

}
