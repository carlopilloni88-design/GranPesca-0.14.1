# 🎣 GranPesca (v0.14.1)

Giochino di pesca per Android: sei il pescatore su una barchetta (con la **tua faccia**) lungo la costa orientale della Sardegna. Si parte da **Santa Maria Navarrese** e si naviga intorno a tutta la Sardegna: 66 luoghi reali e 16 porti; Bosa, il più lontano, si raggiunge da nord o da sud.

- **Leva del motore** (6 posizioni, anche retromarcia) e **ancora** (serve per il bolentino da fermi).
- **Carburante**: consumo in base alla posizione della leva (zero da fermi o all'ancora), autonomia stimata, avviso di riserva al 15%, prezzo al litro diverso per porto e settimana. «Torna in porto» porta a Santa Maria Navarrese se il carburante basta, altrimenti al primo porto raggiungibile; a serbatoio vuoto c'è solo il rimorchio. L'uscita successiva parte dal porto d'arrivo.
- **Campo base a Santa Maria Navarrese**: cantiere della barca (motore, serbatoio, ancora, canne, vernice), deposito illimitato degli oggetti e gestione delle abilità. Il porto è una mappa vista dall'alto con Molo, Mercato, Negozio, Bar e (solo a Santa Maria Navarrese) Cantiere; più il porto è lontano più costano le cose e più è pagato il pesce (fino a +25% a Bosa). Zaino a bordo con posti limitati.
- **Il vecchio pescatore**: in caso di bancarotta in mare ti riporta a casa e ti lascia un indizio su un esemplare unico.
- **250 avventori e rivali generati** per partita (nome e soprannome, porto di casa, effetti), disegnati a pezzi con piccole animazioni al bar; **tornei del mese** (anche Re del bolentino, Re del jig e Pesce del mese) e sfida finale di dicembre contro il rivale col livello più alto 👑.
- **Pausa automatica**: se l'app resta in pausa in mare per più di 30 minuti (anche chiusa) la barca rientra da sola nel porto più vicino; prima dei 30 minuti si riprende da dove eri.
- **Difficoltà** (Opzioni → Difficoltà, solo in porto): mare opaco, calendario stagionale, periodi di chiusura, taglie minime, specie protette; ogni regola aumenta guadagno ed esperienza. Preimpostazioni Rilassato, Esperto e Pro completo (con ricompense esclusive).
- **Calendario**: ogni uscita è una settimana (52 uscite = un anno), bollettino della settimana con specie vietate, taglie minime e specie protette.
- **Equipaggiamento**: 11 slot più Aspetto, confronto tra oggetti, ecoscandaglio in 3 livelli nello slot Barca; l'equipaggiamento si vede sul pescatore e sulla barca.
- **Abilità passive**: 6 abilità da 100 livelli, tutte sempre attive; azzeramento a Santa Maria Navarrese (1 punto perso ogni 10 livelli).
- **Bar dei porti**: avventori che, per un caffè, raccontano leggende vere ma vaghe su pesci rari; «Diario delle leggende» e sfide con gli avventori.
- **Cinque rarità** per pesci e oggetti (Comune, Raro, Epico, Leggendario, Unico); oggetti generati in stile «Diablo» (4 modelli per tipo, nomi generati, da 1 a 5 bonus secondo la rarità, bonus delle tecniche solo sulla loro attrezzatura, bonus speciali di zona ★); ogni pesce raro o più dà un oggetto; forzieri con i pesci, più frequenti sugli scogli e vicino ai relitti.
- **Giorno e notte**: uscite separate di giorno e di notte, con ore di luce secondo il calendario; a fine uscita si rientra a motore verso i porti raggiungibili o col rimorchio.
- **Combattimento** con tensione, frizione regolabile, strappi annunciati e stanchezza del pesce.
- **Motori da 12 a 40 nodi**, tre livelli di ancora (l'ultimo è il motore elettrico di prua), sonar a colori secondo la rarità.
- **Guida al gioco** completa nelle Opzioni.
- **Mappa della Sardegna** 🗺️ dal menu di pausa: dove sei, i porti e i luoghi raggiungibili con il carburante che hai.
- **3 tecniche**: bolentino (di base), vertical jig e traina (kit acquistabili nel negozio). La traina si fa a lento moto e parte dalla superficie: «Fila lenza» fa scendere l'artificiale, tenendo premuto lo schermo risale piano; accelerando sale, rallentando scende. Lasciata sul fondo si incaglia e al terzo incaglio la paratura si rompe fino alla partenza successiva dal porto.
- **Diffidenza dei pesci**: i più grandi e rari vanno cercati con l'esca vicina; abilità e oggetti aiutano.
- **Livello senza limite**, abilità, oggetti, esemplari unici con album a premi, notti in mare con la lampara, stagioni, mercato della settimana e torneo del mese.
- **Economia**: i pesci valgono € in base al prezzo reale al kg; vendi dalla stiva al Mercato.
- **Mare pulito**: i rifiuti galleggiano in mare, si raccolgono toccandoli (5 posti in stiva l'uno) e si consegnano al Molo; trofeo Custode del Mare a 100.
- **Foto ricordo** della cattura, album nel Diario e condivisione con il menu di Android (nessun permesso aggiuntivo).
- **Nuova partita** con doppia conferma e copia di sicurezza ripristinabile dalle Opzioni.
- **Inglese/Italiano** scelto al primo avvio e modificabile dalle Opzioni; gioco anche in orizzontale; risparmio batteria.
- **Foto solo dalla galleria**: nessun permesso fotocamera.
- **Android**: compileSdk/targetSdk 36 (Android 16), AGP 8.10.1, Gradle 8.11.1.
- **Audio**: effetti e musichetta originale generati con WebAudio (nessun copyright).
- 77 specie da pescare (più 13 "intrusi"), 29 trofei, diario con avvistamenti e indizi.

## Play Store
La build produce `GranPesca.aab` (Android App Bundle, da caricare nel Play Console) e `GranPesca.apk` di rilascio, firmati con `app/granpesca.jks`, che per Google Play è la **chiave di caricamento**. La versione Android per il Play Store non contiene strumenti di debug.

La build produce anche `GranPesca-debug.apk`: versione di prova con gli strumenti di debug, nome «GranPesca debug» e pacchetto `com.carlo.granpesca.debug`, che si installa accanto a quella del Play Store senza toccarne i salvataggi. Non va caricata sul Play Store.

## Come ottenere l'APK (senza installare nulla)

1. Nel repository GitHub (meglio privato) carica, con *Add file → Upload files*:
   - il file **`GranPesca.zip`** così com'è, senza estrarlo (se c'è già, viene sostituito);
   - il file **`.github/workflows/build.yml`** (la build usa sempre `GranPesca.zip`, anche se nel repository ci sono altri zip vecchi).
2. Vai nella scheda **Actions**: parte da sola la build "Build APK" (3-5 minuti).
   Se non parte: Actions → Build APK → *Run workflow*.
3. A fine build trovi l'APK in due posti:
   - scheda **Releases** (a destra nella pagina del repo): scarichi `GranPesca.apk` direttamente dal telefono;
   - nella pagina della build, sezione **Artifacts** (`GranPesca-apk`, è uno zip).
4. Sul telefono apri il file e consenti l'installazione da "origini sconosciute" quando richiesto.

> L'APK è firmato con la chiave fissa del progetto (`app/granpesca.jks`), così ogni aggiornamento si installa sopra il precedente. Per il Play Store serve un bundle *release* (.aab) firmato.

## In alternativa: Android Studio

Apri la cartella con Android Studio, lascia che sincronizzi Gradle e premi *Build → Build APK(s)*
(oppure collega il telefono e premi ▶ Run).

## Struttura

- `app/src/main/assets/index.html` → tutto il gioco (HTML5 canvas): qui cambi pesci, velocità, grafica.
- `app/src/main/java/.../MainActivity.kt` → WebView a schermo intero + scelta foto dalla galleria.
- `.github/workflows/build.yml` → compilazione automatica dell'APK.

## Aggiornamenti e salvataggi
- Il registro delle modifiche verrà pubblicato a partire dalla versione 0.8.0.
- Ogni versione ha numero MAGGIORE.MINORE.CORREZIONE e `versionCode` crescente: Android installa l'aggiornamento sopra la versione precedente senza cancellare i dati.
- I salvataggi portano la versione che li ha scritti e vengono aggiornati in automatico all'avvio; prima viene fatta una copia di sicurezza.
- **0.5.0**: compatibile con i salvataggi della 0.4.0; la barca riparte da Santa Maria Navarrese e gli oggetti oltre i posti dello zaino vanno nel deposito.
- **Eccezione della 0.4.0**: per usare le nuove funzioni si inizia una partita nuova. I progressi delle versioni precedenti non vengono caricati (ne resta una copia di sicurezza nel telefono); restano la foto del viso e le impostazioni. I codici di esportazione delle versioni precedenti non si possono importare.

## Firma dell'app e aggiornamenti senza disinstallare
- Dalla 0.3.0 l'APK è firmato sempre con la stessa chiave (`app/granpesca.jks`, password in `gradle.properties`).
  Ogni nuova versione (con `versionCode` più alto) si installa **sopra** quella presente e i salvataggi restano.
- Le versioni fino alla 0.2.1 erano firmate con una chiave diversa a ogni build di GitHub: per passare alla 0.3.0
  serve disinstallare **un'ultima volta** (prima esporta il salvataggio da Opzioni → Esporta, poi reimportalo).
- **Conserva una copia di `app/granpesca.jks`**: senza quel file gli aggiornamenti futuri non si potranno installare sopra.
- Se il repository GitHub è pubblico, chiunque può vedere la chiave: meglio renderlo privato
  (Settings → General → Danger Zone → Change visibility).
