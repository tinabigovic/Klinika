<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix='s' uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Izvestaj o pregledu</title>
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
	<form:form action="/Klinika/pregledi/sacuvajIzvestaj" method="post"
		modelAttribute="pregled">
		<table class="tabela-izvestaj">
			<tr>
				<td><form:input type="hidden" path="lekar"
						value="${lekar.idLekar}" /></td>
			</tr>
			<tr>
				<td><b>Novi pacijent?</b> <a
					href="/Klinika/pacijenti/unosPacijenta">Kreiranje kartona
						pacijentu</a></td>
			</tr>
			<tr>

				<td>Odabir pacijenta: <form:select path="pacijent">
					<c:forEach items="${pacijenti}" var="p">
						<option value="${p.idPacijent}">${p.ime} ${p.prezime} |${p.jmbg}|</option>
					</c:forEach>
					</form:select>
				</td>
			</tr>

			<tr>
				<td><b>Opis pregleda:</b></td>
			</tr>
			<tr>
				<td><form:input type="text" path="opis" class="opis-pregleda" />
				</td>
			</tr>
			<tr>
					<td>Odabir dijagnoze: <form:select path="dijagnoza"
						items="${dijagnoze}" itemValue="idDijagnoza" itemLabel="opis" /></td>
			</tr>
			<tr>
				<td>Odabir leka: <form:select path="lek" items="${lekovi}"
						itemValue="idLeka" itemLabel="naziv" />
				</td>
			</tr>

			<tr>
				<td>Datum upisa: <form:input type="date" path="datum"
						value='<fmt:formatDate
								pattern="yyyy-MM-dd" value="${today}"/>' />
				</td>
			</tr>
			
			<tr>
				<td>Odabir usluge: <form:select path="usluga" items="${usluge}"
						itemValue="idUsluge" itemLabel="opis" />
				</td>
			</tr>
			<tr>
				<td><input type="submit" value="Sacuvaj" /></td>
			</tr>
		</table>
	</form:form>
	<c:if test="${!empty pregledSaved}">
	<div class="tabela-predstojeceZ">
		Pregled je uspesno sacuvan. Sacuvaj izvestaj? <br>
		<form action="/Klinika/pregledi/izvestajStampa.pdf" method="get">
			<input type="hidden" name="idPregleda" value="${pregledSaved.idPregleda}">
			<input type="submit" value="Sacuvaj">
		</form> 
	</div>
	</c:if>
</body>
</html>