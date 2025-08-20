# nonsense-generator

`nonsense-generator` è un generatore di frasi casuali[cite: 1]. Partendo da un input testuale fornito dall'utente, l'applicazione genera una frase casuale seguendo una logica di elaborazione interna[cite: 1]. Combina parole estratte dalla frase fornita in input e da un dizionario con template generati randomicamente a partire da SentencesStructures predefinite[cite: 2]. L'applicazione sfrutta le funzionalità delle API Google Cloud Natural Language "analyzeSyntax" e "moderateText" per analizzare la sintassi della frasi in input e validare la tossicità della frase nonsense generata[cite: 3].

## Utilizzo

1. **Inserimento della frase**: all'avvio del programma viene chiesto all'utente di inserire una frase[cite: 4]. Se l'utente inserisce una stringa vuota o una frase in una lingua diversa dall'italiano, viene chiesto all'utente di inserire una nuova frase[cite: 5].
2. **Analisi della frase**: l'applicazione analizza la frase in input dal punto di vista sintattico e ne ricava l'albero sintattico, che può essere mostrato se l'utente lo richiede[cite: 6].
3. **Generazione del Template**: l'applicazione, a partire da un insieme ampio di SentencesStructures della forma "Il %s il %s in un %s e %s.", genera un template[cite: 7]. I segnaposti generici "%s" vengono sostituiti con segnaposti specifici per nomi, verbi e aggettivi inserendo randomicamente 1, 2 o 3 componenti grammaticali secondo una logica associativa grammaticalmente corretta[cite: 7]. Il template ottenuto avrà la forma "Il [NOUN] [ADJECTIVE] il [VERB] in un [NOUN] [VERB] e [NOUN]"[cite: 8].
4. **Scelta delle parole**: una volta che il template è stato generato, il programma prepara una combinazione di parole estratte dalla frase originale dell'utente e parole pescate da un dizionario interno al programma[cite: 9].
5. **Generazione della frase nonsense**: l'applicazione inserisce le parole appena scelte all'interno del template, ognuna all'interno del segnaposto che ne indica la tipologia[cite: 10].
6. **Validazione della tossicità**: una volta che la frase nonsense è stata generata, il programma ne analizza la tossicità[cite: 11]. Qualora venga registrata una tossicità troppo elevata, il programma tenta di generare una nuova frase diminuendo di uno alla volta le parole scelte dalla frase in input[cite: 12]. Tuttavia, se si dovesse arrivare al punto in cui la frase nonsense viene generata utilizzando solamente parole estratte dal dizionario, il programma chiede all'utente di inserire una nuova frase[cite: 13]. Questo assicura che nella frase nonsense sia sempre presente almeno un termine della frase in input[cite: 13].

## Manuale di installazione

Per compilare ed eseguire il progetto è necessario avere un ambiente di sviluppo Java correttamente configurato[cite: 14].

### 1. Prerequisiti

- **Java Development Kit (JDK)**: è richiesta la versione 21 o superiore[cite: 14]. Verificare la versione tramite la riga di comando "java -version"[cite: 15].
- **Apache Maven**: il progetto utilizza Maven per l'esecuzione[cite: 15]. Verificare tramite "mvn -version" che la versione sia 3.11 o superiore[cite: 16].
- **Git**: è necessario per clonare la repository del progetto[cite: 16].
- **API Key di Google Cloud**: è necessario possedere una chiave API valida per i servizi di Natural Language[cite: 16].
- **Connessione internet**: è richiesta una connessione internet per permettere all'applicazione di comunicare con le API di Google Cloud[cite: 16].

### 2. Procedura di esecuzione

- **Clonare la repository**: "git clone <https://github.com/alessiomodonesi/nonsense-generator.git"[cite>: 16].
- **Configurare la chiave API**: spostarsi nella cartella Root del progetto clonato, creare un file di testo chiamato ".api_key" ed inserire al suo interno la propria chiave API di Google Cloud[cite: 16].
- **Compilare ed eseguire il programma**: il file "pom.xml" è già configurato per eseguire l'applicazione tramite il plugin "exec-maven-plugin"[cite: 17]. Per eseguire il programma, dal terminale bisogna portarsi nella cartella Root del progetto e lanciare i comandi "mvn clean install" e "mvn compile exec:java"[cite: 18]. Quest'ultimo comando si occuperà di scaricare tutte le dipendenze necessarie, compilare il codice sorgente e avviare l'applicazione[cite: 18].
