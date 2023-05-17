<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Accedi</title>
<!-- Common imports in pages -->
<jsp:include page="./header.jsp" />
<!-- [Bootstrap 5 CSS](poe://www.poe.com/_api/key_phrase?phrase=Bootstrap%205%20CSS&prompt=Tell%20me%20more%20about%20Bootstrap%205%20CSS.) -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Fontawesome CSS -->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	rel="stylesheet">
<!-- Custom styles for login -->
<link href="assets/css/signin.css" rel="stylesheet">
<style> /* Custom styles for login */
.form-floating input:not(:placeholder-shown)+label {
	top: -20px;
	left: 0;
	font-size: 0.8rem;
	opacity: 0.5;
}
basic
Copy
.form-floating input:-webkit-autofill+label, .form-floating input:-webkit-autofill:focus+label
	{
	top: -20px;
	left: 0;
	font-size: 0.8rem;
	opacity: 0.5;
}
.form-check-input:checked ~ .form-check-label::before {
	background-color: #007bff;
	border-color: #007bff;
}
/* Custom styles for alerts */
.alert {
	opacity: 0;
	transition: opacity 0.3s ease-in-out;
}
.alert.show {
	opacity: 1;
}
.alert-dismissible .btn-close {
	position: absolute;
	top: 0;
	right: 0;
	padding: 0.75rem 1.25rem;
	color: inherit;
}
.alert-danger {
	color: #721c24;
	background-color: #f8d7da;
	border-color: #f5c6cb;
}
.alert-info {
	color: #0c5460;
	background-color: #d1ecf1;
	border-color: #bee5eb;
}
.alert-success {
	color: #155724;
	background-color: #d4edda;
	border-color: #c3e6cb;
}
/* Custom styles for buttons */
.btn-primary {
	background-color: #007bff;
	border-color: #007bff;
}
.btn-primary:hover {
	background-color: #0069d9;
	border-color: #0062cc;
}
.btn-primary:focus {
	box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.5);
}
.btn-outline-primary {
	color: #007bff;
	border-color: #007bff;
}
.btn-outline-primary:hover {
	background-color: #007bff;
	border-color: #007bff;
	color: #fff;
}
.btn-outline-primary:focus {
	box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.5);
	color: #fff;
}
/* Custom styles for form elements */
.form-control:focus {
	border-color: #007bff;
	box-shadow: none;
}
/* Custom styles for page layout */
body {
	padding-top: 4rem;
	background-color: #f7f7f7;
	font-family: sans-serif;
}
.form-signin {
	max-width: 400px;
	margin: 0 auto;
}
.form-signin img {
	margin-bottom: 1.5rem;
}
.form-signin label {
	font-size: 0.875rem;
}
.form-check-label {
	font-size: 0.875rem;
}
.form-check-input {
	margin-right: 0.5rem;
}
.form-floating {
	position: relative;
	margin-bottom: 1.5rem;
}
.form-floating input {
	height: 3rem;
	border-radius: 0.25rem;
	border: 1px solid #ced4da;
	padding: 1rem;
	font-size: 1rem;
}
.form-floating label {
	position: absolute;
	top: 50%;
	left: 1rem;
	transform: translateY(-50%);
	padding: 0 0.25rem;
	transition: all 0.2s ease-in-out;
	pointer-events: none;
}
.form-check-label a {
	color: #007bff;
}
.form-check-label a:hover {
	color: #0056b3;
	text-decoration: underline;
}
@media ( max-width : 576px) {
	.form-signin {
		padding: 1rem;
	}
	.form-floating input {
		font-size: 0.9rem;
	}
	.form-floating label {
		font-size: 0.8rem;
	}
	.btn {
		font-size: 0.9rem;
		padding: 0.5rem 1rem;
	}
}
</style>
</head>
<body>
	<main class="form-signin">
		<form name='login' action="login" method='POST'
			novalidate="novalidate">
			<div
				class="mb-3 alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}"
				role="alert">
				${errorMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<div
				class="mb-3 alert alert-info alert-dismissible fade show ${infoMessage==null?'d-none': ''}"
				role="alert">
				${infoMessage}
				<button type="button" class="btn-close" data-bs-dismiss="alert"
					aria-label="Close"></button>
			</div>
			<img class="mb-4" src="./assets/brand/bootstrap-logo.svg" alt=""
				width="72" height="57">
			<h1 class="h3 mb-3 fw-normal">Please sign in</h1>
			<div class="form-floating">
				<input type="text" name="username" class="form-control"
					id="inputUsername" placeholder=" "> <label
					for="inputUsername">Username</label>
			</div>
			<div class="form-floating">
				<input type="password" name="password" class="form-control"
					id="inputPassword" placeholder=" "> <label
					for="inputPassword">Password</label>
			</div>
			<div class="form-check mb-3">
				<input class="form-check-input" type="checkbox" value="remember-me"
					id="rememberMe"> <label class="form-check-label"
					for="rememberMe"> Ricordami <a href="#" class="float-end"> Password
						dimenticata?</a>
				</label>
			</div>
			<button class="w-100 btn btn-lg btn-primary mb-3" type="submit">Sign
				in</button>
			<div class="d-flex justify-content-between">
				<a href="${pageContext.request.contextPath}/utente/signup/registrati"
					class="btn btn-outline-primary w-49"> <i
					class="fas fa-user-plus me-2"></i> Registrati Ora!
				</a> <a href="${pageContext.request.contextPath}/home"
					class="btn btn-link">Registrati più tardi</a>
			</div>
		</form>
	</main>
	<!-- Bootstrap 5 JS -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Fontawesome [JS](poe://www.poe.com/_api/key_phrase?phrase=JS&prompt=Tell%20me%20more%20about%20JS.) -->
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
</body>
</html>