package it.prova.myebay.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
	
	@RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
	public String loginPage(@RequestParam(value = "error", required = false) String error,
			Model model, HttpServletRequest request) {
		if (error != null) {
			model.addAttribute("errorMessage", 
                     getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		
//		 String referer = request.getHeader("Referer");
//		    request.getSession().setAttribute("url_prior_login", referer);
//		    // otteniamo l'URL di reindirizzamento dalla sessione
//		    String redirectUrl = (String) request.getSession().getAttribute("url_prior_login");
//
//		    if (redirectUrl != null) {
//		        // rimuoviamo l'URL di reindirizzamento dalla sessione
//		        request.getSession().removeAttribute("url_prior_login");
//
//		        return "redirect:" + redirectUrl;
//		    } else {
//		        // l'URL di reindirizzamento non è stato impostato, quindi reindirizziamo a una pagina predefinita
//		        return "redirect:/login";
//		    }
		    
		    return "login";
	}
	
	//questo mi serve per fare il display di un messaggio diverso in caso di account bloccato
    private String getErrorMessage(HttpServletRequest request, String key){
    
        Exception exception = 
                   (Exception) request.getSession().getAttribute(key);
        
        String error = "";
        if (exception instanceof BadCredentialsException) {
            error = "Invalid username and password!";
        }else if(exception instanceof LockedException) {
            error = "Attenzione! Account disabilitato";
        }else if(exception instanceof DisabledException) {
            error = "Attenzione! Account non abilitato";
        }else{
            error = "Invalid username and password!";
        }
        
        return error;
    }

	@RequestMapping(value = "/executeLogout", method = RequestMethod.GET)
	public String logoutPage(Model model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//dovrebbe essere già impostato a null dalle impostazioni invalidateHttpSession(true)
		//nel SecSecurityConfig ma il controllo si fa comunque
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		model.addAttribute("infoMessage", "You have been successfully logged out !!");
		return "login";
	}
	
	@RequestMapping(value = "/accessDenied", method = {RequestMethod.POST,RequestMethod.GET})
	public String accessDenied(Model model) {
		model.addAttribute("errorMessage", "Attenzione! Non si dispone delle autorizzazioni per accedere alla funzionalità richiesta.");
		return "utente/index";
	}

}

