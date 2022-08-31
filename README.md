# Klinika
Spring boot projekat rađen u sklopu predmeta <i>Razvoj informacionih sistema</i>. <br>
Aplikacija je zamišljena kao sistem s namenom za zdravstvene ustanove. 
<h3>Uloge i funkcionalnost</h3>
Postoje 4 uloge: <b>lekar, pacijent, medicinska sestra i admin.</b> U odnosu na njih su raspodeljene raličite funkcionalnosti i uloge.<br><br>
<b>Admin</b> kreira korisnika i određuje mu korisničko ime. O ograničenjima jedinstvenosti korinika u sistemu, vodi računa sistem. Pored toga, admin ima uvid u listu korisnka, može da ažurira podatke o njima, briše korisnike i zakazane termine. <br><br>
<b>Lekar</b> kreira izveštaj o pregledu, zakazuje pregled za pacijenta, pregleda predstojeće zakazane i otkazane termine kao i obavljene preglede u proizvoljnom vremenskom intervalu.<br><br>
<b>Pacijent</b> ima uvid u svoje predstojeće zakazane i otkazane termine, može da zakaže pregled kod željenog lekara (sistem vodi računa o odabiru termina koji je slobodan).<br><br>
<b>Medicinska sestra</b> može da zakaže termin pacijentima. <br><br>
Korisnik koji nije autentifikovan može da pregleda cenovnik usluga, kao i svi ostali korisnici.<br><br>
<i>Za upravljanje podacima u sklopu ovog projekta podignuta je baza na lokalu kojom se za čije je upravljanje korišćen MySQL WorkBench.</i>
