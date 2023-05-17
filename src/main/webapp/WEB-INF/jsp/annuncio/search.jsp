<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="it" class="h-100" >
<head>
	<jsp:include page="../header.jsp" />
	<title>Ricerca</title>
	
    
</head>
<body class="d-flex flex-column h-100">

	<!--  
	da STUDIARE BENE , i tag authorize possiamo avere dati sull'utente che è in pagine
	come l'username, come se è autenticato 
	è autenticato chiamiamo con una variabile isautenticato e successivamente la mettiamo nell' if
	e giustriamo le due navbar
	-->
	
	 <sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
<c:choose>
   <c:when test="${isAutenticato}"><jsp:include page="../navbar.jsp"></jsp:include></c:when>
   <c:otherwise><jsp:include page="./navbar.jsp"></jsp:include>
	</c:otherwise>
</c:choose>

	
	
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
			  ${errorMessage}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
			
			<div class="container-fluid py-3">
			<h3 class="display-5 fw-bold">Benvenuto su My Ebay</h3><br>
			
			<div class='card'>
			
			    <div class='card-header'>
			        <h5>Cerca degli annunci</h5> 
			    </div>
			    <div class='card-body'>
	
						<form:form modelAttribute="search_annuncio_attr" method="post" action="${pageContext.request.contextPath}/public/annuncio/list" class="row g-3">
						
							<div class="col-md-6">
								<label for="testoAnnuncio" class="form-label">Testo Annuncio</label>
								<input type="text" name="testoAnnuncio" id="testoAnnuncio" class="form-control" placeholder="Inserire il testo dell'annuncio" >
							</div>
							
							<div class="col-md-6">
								<label for="prezzo" class="form-label">Prezzo</label>
								<input type="number" name="prezzo" id="prezzo" class="form-control" placeholder="Inserire il prezzo" >
							</div>
							
							
							
							<div class="col-md-6 form-check" id="categorieDivId">
									<p>Categoria:</p>
									<form:checkboxes itemValue="id" itemLabel="descrizione"  element="div class='form-check'" items="${categorie_totali_attr}" path="categorieIds" />
								</div>
								<script>
									$(document).ready(function(){
										
										$("#categorieDivId :input").each(function () {
											$(this).addClass('form-check-input'); 
										});
										$("#categorieDivId label").each(function () {
											$(this).addClass('form-check-label'); 
										});
										
									});
								</script>
								<%-- fine checkbox ruoli 	--%>
							
							
							
							
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Cerca</button>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
	
							
						</form:form>
			    
				<!-- end card-body -->			   
			    </div>
			</div>
			</div>	
	
		</div>
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>