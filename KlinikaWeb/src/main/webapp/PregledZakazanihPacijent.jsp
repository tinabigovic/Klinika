<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='s' uri="http://www.springframework.org/security/tags" %>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Zakazani termini</title>
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
				<li><a href="/Klinika/rezervacijeSestra/zakaziPacijentuPregled">Zakazivanje
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
	<br><br><br><br><br><br>
	<c:if test="${!empty rezervacijeA}">
		<table class="tabela-predstojeceZ">
			<tr>
				<th colspan="5">Z A K A Z A N O :</th>
			</tr>
			<tr>
				<th>IME</th>
				<th>PREZIME</th>
				<th>DATUM</th>
				<th>ODELJENJE</th>
				<th>LEKAR</th>
			</tr>
			<c:forEach items="${rezervacijeA}" var="r">
				<tr>
					<td>${r.pacijent.ime}</td>
					<td>${r.pacijent.prezime}</td>
					<td>${r.datum}</td>
					<td>${r.lekar.odeljenje.naziv}</td>
					<td>${r.lekar.ime} ${r.lekar.prezime}</td>
				</tr>
			</c:forEach>
		</table>
		<form action="/Klinika/rezervacije/otkaziPregled" method="POST" class="tabela-predstojeceO">
			<select name="idRezervacije">
				<c:forEach items="${rezervacijeA}" var="r">
					<option value="${r.idRezervacije}">${r.datum} - ${r.lekar.odeljenje.naziv} - kod dr ${r.lekar.prezime}</option>
				</c:forEach>
			</select>
			<input type="submit" value="otkazi">
		</form>
	</c:if>
</body>
</html>