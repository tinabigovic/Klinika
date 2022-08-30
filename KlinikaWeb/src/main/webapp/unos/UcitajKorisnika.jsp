<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='s' uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ucitavanje korisnika</title>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
	<div class="horizontal-nav">
		<ul>
			<s:authorize access="isAuthenticated()">
				<li><a href="/Klinika/pocetna.jsp">Home page</a></li>
			</s:authorize>
			<s:authorize access="!isAuthenticated()">
				<li><a href="/Klinika/index.jsp">Home page</a></li>
			</s:authorize>
			<li><a href="/Klinika/Onama.jsp">O nama</a></li>
			<li><a href="/Klinika/usluge/getUsluge">Cenovnik</a></li>
			<s:authorize access="isAuthenticated()">
				<s:authorize access="hasRole('lekar')">
					<li><a href="/Klinika/pregledi/kreiranjeIzvestaja">Kreiraj
							izvestaj</a></li>
					<li><a href="/Klinika/rezervacije/kreiranjeRezervacije">Zakazi
							pregled</a></li>
					<li><a href="/Klinika/rezervacije/prikazPredstojecih">Predstojeci
							pregledi</a></li>
					<li><a href="/Klinika/PreglediUPeriodu.jsp">Izvestaj za
							period</a></li>
				</s:authorize>
				<s:authorize access="hasRole('pacijent')">
					<li><a href="/Klinika/rezervacije/pregledZakazanihPacijent">Pregled
							zakazanih termina</a></li>
					<li><a href="/Klinika/rezervacije/pregledOtkazanihPacijent">Pregled
							otkazanih termina</a></li>
					<li><a href="/Klinika/most/inicijalnoZakazivanje">Zakazi
							termin</a></li>
				</s:authorize>
				<s:authorize access="hasRole('sestra')">
					<li><a
						href="/Klinika/rezervacijeSestra/zakaziPacijentuPregled">Zakazivanje
							pregleda</a></li>
				</s:authorize>
				<s:authorize access="hasRole('admin')">
					<li><a href="/Klinika/korisnikKontroler/ucitajKorisnika">Kreiranje
							korisnika</a></li>
					<li><a href="/Klinika/korisnikKontroler/izmeniKorisnike">Brisanje
							korisnika</a></li>
					<li><a href="/Klinika/brisanjeRezervacije/obrisiRezervaciju">Brisanje
							zakazanog termina</a></li>
				</s:authorize>
				<s:authorize access="isAuthenticated()">
					<li><a href="/Klinika/auth/logout">Log out</a></li>
				</s:authorize>
			</s:authorize>

		</ul>
	</div>
	<br>
	<br>
	<br>
	<br>
	<div class="center" style="font-size: 20px; text-align: center;">
	</div>
	<form action="/Klinika/korisnikKontroler/preusmeriNaFormu" method="GET"
		class="tabela-autentifikacija">
		<p>Izaberite ulogu:</p>
		<select name="uloga">
			<c:forEach items="${uloge}" var="u">
				<option value="${u}">${u}</option>
			</c:forEach>
		</select> <input type="submit" value="sacuvaj">
	</form>
</body>
</html>