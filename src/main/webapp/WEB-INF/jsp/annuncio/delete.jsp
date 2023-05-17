<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
<head>

<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />

<title>Elimina Elemento</title>

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
					<h5>Elimina Annuncio</h5>
				</div>


				<div class='card-body'>

					<dl class="row">
						<dt class="col-sm-3 text-right">Testo annuncio:</dt>
						<dd class="col-sm-9">${delete_annuncio_attr.testoAnnuncio}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Prezzo:</dt>
						<dd class="col-sm-9">${delete_annuncio_attr.prezzo}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Venditore:</dt>
						<dd class="col-sm-9">${delete_annuncio_attr.utente.nome} ${delete_annuncio_attr.utente.cognome} (${delete_annuncio_attr.utente.username})</dd>
					</dl>
					<dl class="row">
						<dt class="col-sm-3 text-right">Data Inserimento:</dt>
						<dd class="col-sm-9">
							<fmt:parseDate value="${delete_annuncio_attr.dataCreazione}"
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
				<form action="${pageContext.request.contextPath}/annuncio/delete"
						method="post">
						<input type="hidden" value="${delete_annuncio_attr.id}" name="idAnnuncio"
							id="idFilm">
						<button type="submit" name="submit" id="submit"
							class="btn btn-danger">Conferma</button>
					<a
						href="${pageContext.request.contextPath}/annuncio"
						class='btn btn-outline-secondary' style='width: 80px'> <i
						class='fa fa-chevron-left'></i> Back
					</a>
					</form>
				</div>
				<!-- end card -->
			</div>


			<!-- end container -->
		</div>

	</main>

	<!-- Footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>
