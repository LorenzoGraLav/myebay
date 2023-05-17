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
			        <h5>Miei acquisti</h5> 
			    </div>
			    <div class='card-body'>

			    
			        <div class='table-responsive'>
			            <table class='table table-striped ' >
			                <thead>
			                    <tr>
			                        <th>Descrizione</th>
			                        <th>Prezzo </th>
			                        <th>Data acquisto</th>
			                    </tr>
			                </thead>
			                <tbody>
			                	<c:forEach items="${acquisto_list_attr }" var="acquistoItem">
									<tr>
										<td>${acquistoItem.descrizione}</td>
										<td>${acquistoItem.prezzo} €</td>
										<td><fmt:parseDate value="${acquistoItem.dataAcquisto}"
												pattern="yyyy-MM-dd" var="localDateToBeParsed" type="date" />
											<fmt:formatDate pattern="dd/MM/yyyy"
												value="${localDateToBeParsed}" /></td>
										<td>
										
											
										</td>
									</tr>
								</c:forEach>
								
					
			                </tbody>
			            </table>
			        </div>
			   
				<!-- end card-body -->			   
			    </div>
			</div>	
				<div class='card-footer' style='display: flex; justify-content: space-between;'>
					<a href="${pageContext.request.contextPath}/home" class='btn btn-outline-secondary' style='width: 80px'>
						<i class='fa fa-chevron-left'></i> Back
					</a>
					<a class="btn btn-primary btn-sm" href="${pageContext.request.contextPath}/annuncio/search">Torna alla Ricerca</a>
				</div>
		</div>	
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
	
	
	
	
</body>
</html>
