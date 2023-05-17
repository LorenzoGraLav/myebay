package it.prova.myebay.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.myebay.dto.AcquistoDTO;
import it.prova.myebay.dto.AnnuncioDTO;
import it.prova.myebay.exception.AcquistoStessoAnnuncioException;
import it.prova.myebay.exception.CreditoInsufficienteException;
import it.prova.myebay.exception.UtenteNonTrovatoException;
import it.prova.myebay.model.Acquisto;
import it.prova.myebay.model.Annuncio;
import it.prova.myebay.service.AcquistoService;
import it.prova.myebay.service.AnnuncioService;

@Controller
@RequestMapping("/acquisto")
public class AcquistoController {

	@Autowired
	private AcquistoService acquistoService;
	
	
	@Autowired
	private AnnuncioService annuncioService;

	

	@GetMapping
	public ModelAndView listAllAcquisti() {
		ModelAndView mv = new ModelAndView();
		List<AcquistoDTO> acquisto_list_attr = AcquistoDTO.createAcquistoDTOListFromModelList(acquistoService.miaLista());
		if(acquisto_list_attr.isEmpty()) {
			mv.addObject("errorMessage", "la tua lista acquisti è vuota!");
			mv.setViewName("acquisto/list");
			return mv;
		}
		
		mv.addObject("acquisto_list_attr", AcquistoDTO.createAcquistoDTOListFromModelList(acquistoService.miaLista()));
		mv.setViewName("acquisto/list");
		return mv;
	}

	@PostMapping("/compra")
	public String compra(Long idAnnuncio, ModelMap model, RedirectAttributes redirectAttrs) {

		// ------------------ FARE TRY CATCH DEL METODO ---------------------------

		
		try {
			acquistoService.inserisciNuovoAcquisto(idAnnuncio);
		} catch (CreditoInsufficienteException e) {
			redirectAttrs.addFlashAttribute("errorMessage", "Attenzione, credito insufficiente !!");
			return "redirect:/annuncio";
		} catch(UtenteNonTrovatoException e) {
			return "redirect:/home";
		} catch(AcquistoStessoAnnuncioException e) {
		redirectAttrs.addFlashAttribute("errorMessage", "Attenzione, stai provando ad acquistare un tuo annuncio !!");
		return "redirect:/annuncio"; }
		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/acquisto";
	}

	@GetMapping("/myList")
	public String myList(ModelMap model) {

		acquistoService.miaLista();

		List<Acquisto> result = acquistoService.miaLista();

		if (result.isEmpty())
			return "home";

		model.addAttribute("acquisto_list_attr",
				AcquistoDTO.createAcquistoDTOListFromModelList(acquistoService.miaLista()));
		return "acquisto/list";
	}
	
	
	@GetMapping("/trucco/{idShow}")
	public String trucco(Long idShow,ModelMap model) {
		
		Annuncio annuncioModel = annuncioService.caricaSingoloElementoConCategorie(idShow);
		
		AnnuncioDTO annuncioDTO = AnnuncioDTO.buildAnnuncioDTOFromModel(annuncioModel, true);
		
		model.addAttribute("show_annuncio_attr", annuncioDTO);
		model.addAttribute("categorie_totali_attr", annuncioModel.getCategorie());
		return "annuncio/show";
		}
	}
	
