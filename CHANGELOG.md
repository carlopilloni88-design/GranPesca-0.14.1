# Registro delle modifiche · GranPesca

Versioni dalla più recente. Per ogni versione: modifiche raggruppate per tipo ed effetto sui salvataggi della versione precedente.

Numerazione: MAGGIORE.MINORE.CORREZIONE. Le versioni 0.0.x sono state numerate a posteriori.

## [0.14.1] · 2026-10-20 · Backup in una cartella tua, un solo file

### Aggiunto

- Puoi scegliere la cartella dei backup (consigliata «Documenti»): il gioco crea la cartella «GranPesca», che resta anche se disinstalli l'app.
- La copia interna della partita usa due file alternati con controllo, e sostituisce la partita locale se questa è illeggibile; entra anche nel backup di Android.
- Protezioni sul salvataggio: non scrive valori impossibili e non sovrascrive una partita avanzata con una molto più bassa (chiede di ricaricare).

### Modificato

- I backup sono un solo file, GranPesca.gp, sempre sovrascritto (più GranPesca-precedente.gp con la partita prima dell'ultimo aggiornamento): niente più decine di copie in Download.
- Il file viene scritto in sicurezza (file temporaneo, controllo, poi sostituzione) e se è danneggiato il gioco usa l'ultimo valido.

### Corretto

- Dopo gli aggiornamenti o la reinstallazione l'app non crea più copie «(1)», «(2)» e cancella i vecchi file GranPesca*.gp che può raggiungere.
- Se la conversione dei dati dopo un aggiornamento ha un errore, la parte che fallisce viene saltata senza lasciare dati a metà e il gioco te lo segnala.

**Salvataggi:** Compatibili. Al primo avvio il gioco propone di scegliere la cartella; i file GranPesca*.gp rimasti in Download da installazioni precedenti vanno cancellati a mano.

## [0.14.0] · 2026-10-20 · Salvataggi più sicuri e pesca più impegnativa

### Aggiunto

- Scomparto separato per esche e artificiali, con un limite che cresce con la barca; le scorte oltre il limite vengono ridotte.
- Pulsante «Carica file .gp» nelle Opzioni, con scelta del file e anteprima di livello e soldi.
- Recupero parziale dei file .gp non compatibili (diario, livello e abilità, soldi, equipaggiamento), con conferma.
- Al primo avvio dopo l'aggiornamento il gioco salva in Download una copia della partita precedente.

### Modificato

- I backup .gp hanno data e ora nel nome e il gioco carica il più recente; si conservano gli ultimi 8.
- Pesci unici (circa 1 su 600) e leggendari (0,5%) più rari e molto più difficili da catturare.
- Ai primi livelli i pesci rari sono più rari e i giganti molto rari sotto il livello 20.
- I pesci restano nella loro area: non spariscono più appena escono dallo schermo e non compaiono all'improvviso.
- In corsa nascono pochi pesci nuovi e lo scandaglio legge peggio ad alta velocità.

### Corretto

- I pesci non si sovrappongono più al fondale quando cambia la profondità.
- La barra della vista per mancini è nella posizione giusta.
- Il nome lungo nella scheda della cattura non viene più tagliato.
- Le impostazioni «Pescatore Pro» si scorrono fino all'ultima opzione.
- Gli oggetti con bonus rovinati vengono riparati a ogni avvio, non solo alle importazioni.

**Salvataggi:** Compatibili. Le scorte di esche e artificiali oltre il nuovo limite vengono ridotte; gli oggetti rovinati vengono riparati.

## [0.13.1] · 2026-10-19 · Porti più belli

### Modificato

- Porti più curati: macchia mediterranea e pini, rilievo del terreno, case con tetti a due falde e comignoli, strade con bordo, bitte e lampioni sulle banchine, ombrelloni sulle spiagge.

### Corretto

- I bonus degli oggetti nuovi risultavano NaN (e a zero dopo il salvataggio), il che rompeva i combattimenti: sparivano barca, lenza e pesce. Corretto il calcolo e riparati gli oggetti già salvati.
- I tasti «Cambia» delle missioni sono allineati nella stessa colonna.
- Nelle Opzioni le sei schede stanno tutte nello schermo, anche sui telefoni piccoli.
- In tedesco e in francese alcuni pulsanti e indicatori uscivano dallo schermo; testi e contrasti più leggibili.
- Il luogo «Il vecchio» restava in inglese in tedesco, francese e spagnolo.
- La lenza si vede con qualsiasi colore, grazie a un sottile bordo scuro (prima l'azzurro chiaro spariva sul mare).

**Salvataggi:** Compatibili. Gli oggetti con bonus rovinati vengono riparati al caricamento.

## [0.13.0] · 2026-10-18 · Leggende dei porti

### Aggiunto

- Ogni porto ha la sua leggenda: una variante speciale di un pesce, da pescare una sola volta nelle acque del porto, con la sua storia nel Diario. A Bosa ce ne sono due.
- Il Cantiere è anche a Santa Teresa Gallura, oltre che a Santa Maria Navarrese.
- Ogni volta che esci dal gioco, anche solo in background, il file .gp in Download viene aggiornato; all'avvio l'app lo confronta con la partita e, se il file è più avanzato, ti chiede di ripristinarlo.
- Controlli automatici di coerenza in fase di compilazione.
- Traduzioni complete in inglese, tedesco, francese e spagnolo: storie del vecchio pescatore, racconti del bar, leggende dei porti, storie degli esemplari unici e norme sulla privacy.

### Modificato

- La rarità Epico non esiste più. Tutte le specie sono Comuni o Rare: i pesci che erano leggendari sono ora normali specie Rare, pescabili ovunque vivano.
- Oggetti e pesci hanno quattro livelli di rarità, da 1 a 4 stelle: Comune, Raro, Leggendario, Unico.
- Nel Diario le sezioni sono Comuni, Rari, Leggendari (17, uno per porto) ed Esemplari unici (uno per ognuna delle 77 specie).
- Probabilità di uscita dei pesci circa 85% Comuni, 14% Rari, meno dell'1% tra Leggendari e Unici; una leggenda vale 2,5 volte il prezzo del pesce.
- Le taglie minime non sono più nel bollettino: si consultano dal Mercato.
- Al bar si possono tenere al massimo 3 leggende aperte, una per pesce: per ascoltarne altre bisogna prima catturare quei pesci. Chi parla di un leggendario o di un unico lo lascia intendere.
- Pagina Salvataggi più semplice: «Crea ripristino» salva il file .gp in Download e lo rilegge per verificare che coincida con la partita; «Ripristina» lo trova e lo carica da solo.
- Guida riscritta in forma più breve, con 16 porti e quattro rarità.

### Rimosso

- La voce Consigli dal menu Info.

**Salvataggi:** Compatibili. Gli oggetti epici del salvataggio diventano leggendari.

## [0.12.0] · 2026-10-17 · Mare più bello

### Aggiunto

- Tramonto e alba con colori più ricchi.
- Scintille sul pescatore alla cattura e scheda con cornice e luce legate alla rarità.
- Gabbiani e riflessi nelle mappe dei porti.

### Modificato

- Notte più chiara con luna e stelle, fondale sempre leggibile.
- I pesci si leggono meglio sul fondo e nuotano in modo più fluido.
- Onde con schiuma, cerchi di pioggia e nebbia che scorre.
- Icone più coerenti e barra superiore più compatta, con carburante ben visibile.

**Salvataggi:** Compatibili. Il tuo salvataggio attuale resta com'è.

## [0.11.1] · 2026-10-17 · Salvataggi blindati e più comfort

### Aggiunto

- Modalità di recupero all'avvio se il gioco non parte, con ripristino dei backup automatici.
- Backup a rotazione e controllo del salvataggio; i dati rovinati vengono riparati da soli.
- «Copia diagnostica» per segnalare i problemi.
- Sezione Schermo nelle Opzioni (luminosità, contrasto, testo, meno animazioni, pesca accessibile).
- Vendita rapida a bordo e avviso di stiva quasi piena.
- Segnale visivo più chiaro quando il pesce abbocca.
- Piccoli bonus per le prime catture e per chi torna dopo una pausa.

### Modificato

- Nei primi livelli le abboccate arrivano più in fretta e c'è più tempo per ferrare.
- Un pesce che ha mangiato l'esca può tornare.
- Avvisi più brevi e meno numerosi; si chiudono toccandoli.
- Le missioni completate si registrano appena entri in porto; vendere il pesce non le annulla.

### Rimosso

- La rete dal peschereccio.

### Corretto

- Banchine e barche ora sono allineate e le barche non navigano più sulla terra.

**Salvataggi:** Compatibili. Il tuo salvataggio attuale resta com'è.

## [0.11.0] · 2026-10-17 · Profili e salvataggi al sicuro

### Aggiunto

- 3 profili con schermata iniziale; partita, faccia e album foto separati.
- File .gp unico con i 3 profili, salvato da solo in Download quando serve, più importazione.
- Copia interna dell'app e backup Android; la partita torna se i dati vengono svuotati.
- Diagnostica come immagine in Download (non in galleria).
- Le Bocche di Bonifacio sono chiuse: «il vento è troppo forte» e la barca torna indietro.
- Regola «Pescatore Pro» (lotta senza aiuti), inclusa nel preset Pro.
- Voce «Oggetti» nel menu di pausa.
- Le esche residue (e gli artificiali) si vedono sul pulsante della tecnica.
- Il vecchio pescatore sulla bitta di ogni porto racconta storie sempre diverse su dove, quando e con quale esca pescare i pesci comuni.
- I personaggi del bar hanno le gambe.
- Guida del porto alla prima visita e capitolo «Il porto» nella Guida.
- Suggerimenti nelle prime 5 calate di ogni kit (bolentino, jig, traina).

### Modificato

- Motore acceso = consumo minimo a tempo; navigare lenti non conviene più.
- In traina la lenza segue il fondale con più cautela.
- La frizione è sempre automatica.
- Esche (solo bolentino, facoltative) e artificiali consumabili: i pesci grossi possono strapparli.
- Il diario registra solo i pesci tenuti (i pesci unici sempre).
- La scheda della cattura mostra il pesce appeso alla lenza e non si chiude da sola.
- Dopo il recupero compare il riepilogo di rientro; il rimorchio compare solo se serve (senza carburante).
- I porti hanno la forma reale (moli, banchine, edifici, strade) ricalcata dalla mappa della Sardegna.
- Il bar ha un tavolo tondo a quattro posti; ogni avventore ha la sua voce e racconta storie più complesse.
- I pesci unici sono casi quasi reali (rosa per i gamberetti, 400 anni, amo arrugginito…); i leggendari hanno una marca lieve diversa a ogni cattura.
- Menu Opzioni a sezioni (Personaggio, Comandi, Sistema) e menu di pausa riordinato.
- «Tocca l'acqua» sparisce dopo 10 calate.
- Ogni barca ha una grafica sua (Gozzo, Lancia, Open con T-top, Cabinato, Peschereccio) e ogni motore la sua (fuoribordo, entrobordo, doppio).
- Le foto dell'album mostrano la tua barca e uno sfondo diverso per luogo e ora (alba, giorno, tramonto, notte).

### Rimosso

- «Dove si prende?» del bar: ora lo racconta il vecchio.

### Corretto

- L'album foto apriva una connessione a ogni operazione.
- Schiuma e onde scorrono con la velocità e il verso della barca.
- I pulsanti dei luoghi del porto non si accavallano più e rispondono sempre.
- Nella creazione del personaggio lo scroll col dito non funzionava.
- La medusa non vola più sopra il pelo dell'acqua.
- Il pescatore sotto sforzo non ha più tre gambe.

**Salvataggi:** Compatibili. Il tuo salvataggio attuale resta nel Profilo 1.

## [0.10.0] · 2026-10-15 · Grande revisione dopo i test

### Aggiunto

- Con lo zaino o la stiva pieni scegli tu cosa lasciare in mare.
- Bussola N, S, E, O quando giri la barca; nome grande solo per i luoghi nuovi.
- L'esemplare unico si annuncia con luccichio, bolle dorate e bordo dorato in lotta.
- Cambio gratuito di una missione al giorno; la sfida del bar si vede nel 🎯.

### Modificato

- Menu più semplici; nuova scheda Info nelle Opzioni e ⚙️ anche dal porto.
- Avvisi più brevi, in coda e senza ripetizioni; i consigli si chiudono da soli.
- Economia riequilibrata: prezzo del pesce, rivendita degli oggetti e missioni.
- Esemplari unici più frequenti, con una garanzia contro la sfortuna.
- Jig più facile, esche speciali consumate solo se peschi.

### Corretto

- La foto del viso torna a funzionare; pulsante del rimorchio a serbatoio vuoto.

**Salvataggi:** Compatibili. I codici di esportazione hanno un nuovo formato senza la foto del viso.

## [0.9.3] · 2026-10-14 · Abilità in ogni porto e jig più semplice

### Modificato

- Le abilità si assegnano e si azzerano in qualsiasi porto.
- Niente più scritta fissa «Ferma la barca per calare il jig»; l'avviso compare solo se tocchi l'acqua con la barca in moto.

### Rimosso

- Lo slow jig; il jig ha un solo ritmo e prende pelagici e pesci di fondo.

**Salvataggi:** Compatibili: nessun cambiamento ai salvataggi.

## [0.9.2] · 2026-10-14 · Sfondo più calmo

### Modificato

- Quando giri la barca la visuale si sposta piano, senza far correre lo sfondo.

### Corretto

- Gli scogli a pelo d'acqua restano al loro posto lungo la costa.

**Salvataggi:** Compatibili: nessun cambiamento ai salvataggi.

## [0.9.1] · 2026-10-14 · Comandi della barca in colonna

### Modificato

- Il tasto della modalità di pesca è in cima alla colonna, sopra Ancora e Gira.

**Salvataggi:** Compatibili: nessun cambiamento ai salvataggi.

## [0.9.0] · 2026-10-14 · Comandi nuovi, grafica viva e cinque lingue

### Aggiunto

- Lingue tedesco, francese e spagnolo, con scelta al primo avvio e nelle Opzioni.
- Aspetto del pescatore (uomo o donna, pelle, capelli, barba e viso) all'inizio e nelle Opzioni.
- Stiva a schermo con scarto dei pesci e mappa a tutto schermo con zoom.
- Tutorial guidato con il comando evidenziato e mano animata nel primo combattimento.
- Metronomo del jig, frizione automatica, comandi per mancini e testo grande nelle Opzioni.
- Foto ricordo con il pescatore che tiene il pesce, la tua barca e la zona della cattura.
- Animazioni del pescatore, mare con riflessi, fondale vivo e porto animato.
- Gesti e oggetti sul tavolo degli avventori, vecchio lupo di mare al bar nei giorni di pioggia.
- Al bar «Dove si prende?» per le specie del Diario.
- Il rivale finale passa davanti al bar e gli dai tu il nome.
- Lampo e suono quando sei nelle condizioni di una leggenda.
- Grandi sfide con premi in soldi e oggetti.
- Animazione e fanfara alla salita di livello; suoni di onde, gabbiani, motore e mulinello.
- Nome scientifico nella scheda della specie.
- Avviso con suono al punto di non ritorno del carburante.

### Modificato

- Si cala toccando l'acqua; leva con R, 0 e 1-4; comandi della barca in colonna sopra la leva.
- Barra in alto su due righe con Zaino, Stiva, Mappa, missioni, meteo, zona e bussola.
- Indietro in basso a sinistra; pulsanti più grandi; menu con Gioca, Opzioni e Guida.
- Nuova partita in Opzioni > Salvataggi.
- Scheda della cattura in basso e messaggi uno alla volta.
- Si comincia in modalità Rilassata; dopo 3 uscite si propongono le regole.
- Multe con il motivo ben visibile.
- Leggende raccontate con lo stile di ogni avventore.
- Premio delle sfide al bar in base al livello dell'avventore e alla difficoltà.
- Missioni con soldi, esperienza e a volte un oggetto; posizione nel torneo al rientro.
- Finestra per ferrare più larga nei primi livelli.
- Traina con più abboccate e resa diversa secondo l'ora; consigli per l'uscita notturna.
- Fortuna del cercatore più forte per gli esemplari unici.
- Rifiuti meno frequenti, con una piccola ricompensa per pezzo consegnato.
- Guida a capitoli con immagini.
- Prezzi del giorno al Mercato e «Vendi tutti i comuni».
- Testi inglesi rivisti.

**Salvataggi:** Compatibili: al primo avvio scegli l'aspetto del pescatore, il resto della partita resta com'è.

## [0.8.0] · 2026-10-14 · Porto a mappa, oggetti generati e avventori

### Aggiunto

- Oggetti generati con 4 modelli per tipo, bonus in base a rarità e livello, nomi generati e colore della rarità.
- Bonus delle tecniche solo sull'attrezzatura della tecnica.
- Bonus speciali di zona (★), più forti lontano da Santa Maria Navarrese.
- 250 avventori e rivali generati per partita, con porto di casa, effetti e rivale finale con la corona.
- Avventori disegnati a pezzi con piccole animazioni al bar.
- Porto come mappa vista dall'alto con Molo, Mercato, Negozio, Bar e Cantiere.
- Rifiuti galleggianti da raccogliere e consegnare al Molo; trofeo Custode del Mare.
- Foto ricordo della cattura, album nel Diario e condivisione.
- Rientro automatico dopo 30 minuti di pausa in mare.
- Nuova partita con doppia conferma e copia di sicurezza; Ripristina nelle Opzioni.
- Scelta della lingua al primo avvio.
- Tornei Re del bolentino, Re del jig e Pesce del mese.
- Interruttore della vibrazione.

### Modificato

- Menu principale con Gioca, Nuova partita, Opzioni e Guida.
- Opzioni in quattro schede: Gioco, Difficoltà, Audio, Salvataggi.
- Pescatore con Zaino, Equipaggiamento, Abilità e Diario; zaino ordinabile.
- Negozio a categorie con filtro per rarità.
- In mare una barra sottile con data, ora, soldi, fondale e stiva.
- Liste a scorrimento al posto delle pagine.
- Indietro in alto a sinistra in ogni pagina.

### Rimosso

- Rifiuti tra le catture.

**Salvataggi:** Compatibili: gli oggetti vengono convertiti al nuovo sistema e gli avventori vengono generati al primo avvio.
