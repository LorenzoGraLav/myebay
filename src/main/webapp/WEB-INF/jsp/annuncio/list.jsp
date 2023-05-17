<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!doctype html>
<html lang="it" class="h-100">
<head>
	<jsp:include page="../header.jsp" />
	<title>Pagina dei risultati</title>
	
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
	
			<div class="alert alert-success alert-dismissible fade show  ${successMessage==null?'d-none':'' }" role="alert">
			  ${successMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
			  ${errorMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Lista dei risultati</h5> 
			    </div>
			 <div class='d-flex justify-content-between align-items-center'>
		    <div>
		        <a href="${pageContext.request.contextPath}/public/annuncio/search" class='btn btn-outline-secondary'>
		            <i class='fa fa-chevron-left'></i> Torna alla Ricerca
		        </a>
		    </div>
			    <div>
		     	   <sec:authorize access="isAuthenticated()">
		         		   <a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/annuncio/insert">Add New</a>
		    	    </sec:authorize>
		  			  </div>
				</div>
			        <div class='table-responsive'>
			            <table class='table table-striped ' >
			                <thead>
			                    <tr>
			                        <th>Testo Annuncio</th>
			                        <th>Prezzo </th>
			                        <th>Data inserimento</th>
			                        <th>Azioni</th>
			                    </tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${annuncio_list_attr }" var="annuncioItem">
									<tr>
										<td>${annuncioItem.testoAnnuncio }</td>
										<td>${annuncioItem.prezzo} €</td>
										<td><fmt:parseDate value="${annuncioItem.dataCreazione}"
												pattern="yyyy-MM-dd" var="localDateToBeParsed" type="date" />
											<fmt:formatDate pattern="dd/MM/yyyy"
												value="${localDateToBeParsed}" /></td>
										<td>
										<c:choose>
												<c:when test="${!isAutenticato}"><a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/public/annuncio/show/${annuncioItem.id }">Visualizza</a></c:when>
												<c:otherwise>
												<a class="btn btn-sm btn-outline-secondary" href="${pageContext.request.contextPath}/public/annuncio/show/${annuncioItem.id }">Visualizza</a>
												<sec:authentication property="principal.username" var="utenteInPagina"/>
												<c:if test="${annuncioItem.utente.username == utenteInPagina }">
												<a class="btn btn-sm btn-outline-danger" href="${pageContext.request.contextPath}/annuncio/delete/${annuncioItem.id }">Delete</a>
												<a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/annuncio/edit/${annuncioItem.id }">Edit</a>
												<p> Possiedi questo annuncio</p>
												</c:if>
												</c:otherwise>
										</c:choose>
											
										</td>
									</tr>
								</c:forEach>
			                </tbody>
			            </table>
			        </div>
			   
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
	
	
	
	
</body>
</html>