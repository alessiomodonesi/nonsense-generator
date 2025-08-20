# nonsense-generator

`nonsense-generator` è un generatore di frasi casuali. Partendo da un input testuale fornito dall'utente, l'applicazione genera una frase casuale seguendo una logica di elaborazione interna. Combina parole estratte dalla frase fornita in input e da un dizionario con template generati randomicamente a partire da SentencesStructures predefinite. L'applicazione sfrutta le funzionalità delle API Google Cloud Natural Language "analyzeSyntax" e "moderateText" per analizzare la sintassi della frasi in input e validare la tossicità della frase nonsense generata.

## Utilizzo

1. **Inserimento della frase**: all'avvio dell'app viene chiesto all'utente di inserire una frase. Se l'utente inserisce una stringa vuota o non contenente lettere, gli viene chiesto di inserire nuovamente una frase come input.
2. **Analisi della frase**: l'app analizza la frase in input dal punto di vista sintattico e ne ricava il rispettivo albero, che può essere mostrato se l'utente lo richiede.
3. **Generazione del Template**: l'app, a partire da un ampio insieme di sentence structures, del tipo "Il %s il %s in un %s e %s.", genera un template. I segnaposti generici "%s" vengono sostituiti con segnaposti specifici per nomi, verbi e aggettivi inserendo randomicamente 1, 2 o 3 componenti grammaticali secondo una logica associativa grammaticalmente corretta. Il template ottenuto avrà quindi questa forma: "Il [NOUN] [ADJECTIVE] il [VERB] in un [NOUN] [VERB] e [NOUN]".
4. **Scelta delle parole**: una volta che il template è stato generato, l'app prepara una combinazione di parole estratte dalla frase originale, data in input dall'utente, e parole selezionate a caso da un dizionario interno all'app.
5. **Generazione della frase nonsense**: l'app inserisce le parole appena estratte all'interno del template, sostituendole ai rispettivi segnaposti che ne indicano la tipologia.
6. **Validazione della tossicità**: una volta che la frase nonsense è stata generata, l'app ne analizza la tossicità. Qualora venga registrata una tossicità troppo elevata, l'app tenta di ri-generare una nuova frase diminuendo man mano le parole selezionate dalla frase in input. Tuttavia, se si dovesse arrivare al punto in cui la frase in output presenti solamente parole estratte dal dizionario, l'app chiederebbe all'utente di re-inserire una nuova frase. Questo assicura che nella frase nonsense sia sempre presente almeno un termine della frase in input.

## Manuale di installazione

Per compilare ed eseguire il progetto è necessario avere un ambiente di sviluppo Java correttamente configurato.

### 1. Prerequisiti

- **Java Development Kit (JDK)**: è richiesta la versione 21 o superiore. Verificare la versione tramite la riga di comando "java --version".
- **Apache Maven**: il progetto utilizza Maven per l'esecuzione. Verificare tramite "mvn --version" che la versione sia 3.11 o superiore.
- **Git**: è necessario per clonare la repository del progetto.
- **API Key di Google Cloud**: è necessario possedere una chiave API valida per i servizi di Natural Language.
- **Connessione internet**: è richiesta una connessione internet per permettere all'applicazione di comunicare con le API di Google Cloud.

### 2. Procedura di esecuzione

- **Clonare la repository**: "git clone <https://github.com/alessiomodonesi/nonsense-generator.git">.
- **Configurare la chiave API**: spostarsi nella cartella Root del progetto clonato, creare un file di testo chiamato ".api_key" ed inserire al suo interno la propria chiave API di Google Cloud.
- **Compilare ed eseguire il programma**: il file "pom.xml" è già configurato per eseguire l'applicazione tramite il plugin "exec-maven-plugin". Per fare ciò, dal terminale bisogna sportarsi nella cartella Root del progetto e lanciare i comandi "mvn clean install" e "mvn compile exec:java". Quest'ultimo comando si occuperà di scaricare tutte le dipendenze necessarie, compilare il codice sorgente e avviare l'applicazione.
