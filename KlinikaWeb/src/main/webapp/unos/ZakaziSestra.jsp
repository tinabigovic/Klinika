<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='s' uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Zakazi pregled</title>
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
	<br>
	<br>
	<br>
	<br>
	<form:form action="/Klinika/rezervacijeSestra/sacuvajRezervaciju"
		method="post" modelAttribute="rezervacija">
		<table class="tabela-izvestaj">
			<tr>
				<td><b>Novi pacijent?</b> <a
					href="/Klinika/pacijenti/unosPacijenta">Kreiranje kartona
						pacijentu</a></td>
			</tr>
			<tr>

				<td>Odabir pacijenta: <form:select path="pacijent"
						items="${pacijenti}" itemValue="idPacijent" itemLabel="ime" /></td>
			</tr>
			<tr>
				<td>Odabir lekara: <form:select path="lekar">
						<c:forEach items="${lekari}" var="l">
							<option value="${l.idLekar}">${l.odeljenje.naziv}:Dr
								${l.ime} ${l.prezime}</option>
						</c:forEach>
					</form:select>
				</td>
			</tr>
			<tr>
				<td>Datum pregleda: <form:input type="date" path="datum"
						value='<fmt:formatDate
								pattern="yyyy-MM-dd" value="${today}"/>' />
				</td>
			</tr>
			<tr>
				<td>Vreme pregleda: <input type="time" name="vreme"
					value='<fmt:formatDate
								value="${now}"/>' />
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Sacuvaj"></td>
			</tr>
		</table>
	</form:form>
	<c:if test="${!empty poruka}">
	<div class="tabela-predstojeceZ">
		<p class="poruka">${poruka}</p>
	</div>
	</c:if>
</body>
</html>