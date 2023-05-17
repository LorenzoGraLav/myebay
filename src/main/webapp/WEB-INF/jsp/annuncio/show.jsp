<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
<head>

<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />

<title>Visualizza Elemento</title>

</head>
<body class="d-flex flex-column h-100">

	 <sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
<c:choose>
   <c:when test="${isAutenticato}"><jsp:include page="../navbar.jsp"></jsp:include></c:when>
   <c:otherwise><jsp:include page="./navbar.jsp"></jsp:include>
	</c:otherwise>
</c:choose>

	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container">

			<div class='card'>
				<div class='card-header'>
					<h5>Visualizza dettaglio</h5>
				</div>


				<div class='card-body'>

					<dl class="row">
						<dt class="col-sm-3 text-right">Testo annuncio:</dt>
						<dd class="col-sm-9">${show_annuncio_attr.testoAnnuncio}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Prezzo:</dt>
						<dd class="col-sm-9">${show_annuncio_attr.prezzo}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Venditore:</dt>
						<dd class="col-sm-9">${show_annuncio_attr.utente.nome} ${show_annuncio_attr.utente.cognome} (${show_annuncio_attr.utente.username})</dd>
					</dl>
					<dl class="row">
						<dt class="col-sm-3 text-right">Aperto:</dt>
						<dd class="col-sm-9">${show_annuncio_attr.aperto}</dd>
					</dl>
					<dl class="row">
						<dt class="col-sm-3 text-right">Data Inserimento:</dt>
						<dd class="col-sm-9">
							<fmt:parseDate value="${show_annuncio_attr.dataCreazione}"
								pattern="yyyy-MM-dd" var="localDateToBeParsed" type="date" />
							<fmt:formatDate pattern="dd/MM/yyyy"
								value="${localDateToBeParsed}" />
						</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Categorie:</dt>

						<dd class="col-sm-9">
							<ul>
								<c:forEach items="${categorie_totali_attr}" var="categorieItem">
									<li>${categorieItem.descrizione}</li>
								</c:forEach>
							</ul>
						</dd>

					</dl>

				</div>

				<div class='card-footer'>
    <div class="d-flex justify-content-between align-items-center">
        <a href="${pageContext.request.contextPath}/public/annuncio" class='btn btn-outline-secondary'>
            <i class='fa fa-chevron-left'></i> Tutti gli annunci
        </a>

        <sec:authorize access="isAuthenticated()">
            <sec:authentication property="principal.username" var="utenteInPagina"/>
            <c:choose>
    <c:when test="${show_annuncio_attr.utente.username != utenteInPagina}">
        <form action="${pageContext.request.contextPath}/acquisto/compra" method="post">
            <input type="hidden" value="${show_annuncio_attr.id}" name="idAnnuncio" id="idAnnuncio">
            <button type="submit" name="submit" id="submit" class="btn btn-danger">
                <i class='fa fa-shopping-cart'></i> Compra
            </button>
        </form>
    </c:when>
   		 <c:otherwise>
      		  <p>Possiedi questo annuncio.</p>
		  </c:otherwise>
		</c:choose>     
       </sec:authorize>

 			    <sec:authorize access="!isAuthenticated()">
    				     <div>
         			     <form action="${pageContext.request.contextPath}/acquisto/compra" method="post">
            <input type="hidden" value="${show_annuncio_attr.id}" name="idAnnuncio" id="idAnnuncio">
            <button type="submit" name="submit" id="submit" class="btn btn-danger">
                <i class='fa fa-shopping-cart'></i> Cosa aspetti? Accedi ora per comprare!
            </button>
        </form>
      			   </div>
   			  </sec:authorize>
 		</div>
	</div>
				<!-- end card -->
			</div>


			<!-- end container -->

	</main>

	<!-- Footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>