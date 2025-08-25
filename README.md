# nonsense-generator

`nonsense-generator` è un generatore di frasi casuali.  
Partendo da un input testuale fornito dall'utente, l'applicazione genera una frase nonsense seguendo una logica di elaborazione interna.  
Combina parole estratte dalla frase fornita in input e da un dizionario con template generati randomicamente a partire da `SentenceStructures` predefinite.  

L'applicazione sfrutta le API **Google Cloud Natural Language**:

- `analyzeSyntax` → analizza la sintassi della frase in input e costruisce l’albero sintattico.
- `moderateText` → valida la tossicità della frase nonsense generata.

---

## Funzionamento (logica interna)

1. **Inserimento della frase**  
   L’utente inserisce una frase. Se la frase è vuota o non contiene lettere, viene richiesto un nuovo input.
2. **Analisi sintattica**  
   L’app analizza la frase e ricava l’albero sintattico (opzionale a scelta dell’utente).
3. **Generazione del template**  
   Da un insieme di `SentenceStructures` (es. `"Il %s il %s in un %s e %s."`), l’app genera un template con placeholder grammaticali (`[NOUN]`, `[VERB]`, `[ADJ]`).
4. **Selezione parole**  
   Il template viene riempito con parole tratte sia dall’input sia dal dizionario interno.
5. **Generazione nonsense**  
   Il template completato diventa una frase nonsense.
6. **Validazione tossicità**  
   Se la frase generata è troppo tossica, l’app prova a rigenerarla riducendo progressivamente i termini dell’input.  
   Se restano solo parole di dizionario, viene richiesto un nuovo input.

---

## Requisiti

- **Java Development Kit (JDK)**: versione **21** o superiore  
  Verifica con:

  ```bash
  java --version
  ```

- **Apache Maven**: versione **3.11+**  
  Verifica con:

  ```bash
  mvn --version
  ```

- **Git**: per clonare la repository.
- **API Key di Google Cloud**: necessaria per i servizi *Natural Language*.
- **Connessione Internet**: indispensabile per l’uso delle API.

---

## Installazione

1. Scarica la release v1.0.1 da GitHub con `curl`:

   ```bash
   curl -L -o release-v1.0.1.zip https://github.com/alessiomodonesi/nonsense-generator/archive/refs/tags/v1.0.1.zip
   ```

2. Unzippa la cartella con `unzip`:

   ```bash
   unzip release-v1.0.1.zip
   ```
   
   Questo comando funziona solo su `Linux/MacOS`, in alternativa utilizza un tool integrato nel tuo OS.

3. Entra nella cartella:

   ```bash
   cd nonsense-generator-1.0.1
   ```

4. Configura la tua API Key creando un file `.api_key` nella root del progetto:

   Utilizza direttamente `shell`:
   
   ```bash
   echo "YOUR_API_KEY" > .api_key
   ```

   Oppure crea il file con `nano`:

   ```bash
   nano .api_key
   ```

   E incollaci poi la tua API Key.
   
---

## Esecuzione

Il progetto può essere eseguito in due modalità: **CLI** (da terminale) oppure **WebApp** (interfaccia web con Spring Boot + Thymeleaf).

### 🔹 Modalità CLI (terminale)

La versione CLI utilizza la classe `App` e viene eseguita da Maven con l’`exec-maven-plugin`.

#### Opzione A — 2 passaggi
   
1. Compila/impacchetta:

   ```bash
   mvn -q -DskipTests clean package
   ```
   
2. Avvia la CLI con Maven:

   ```bash
   mvn exec:java -Dexec.mainClass=com.gmms.App
   ```

### Opzione B — One-liner

1. Compila e avvia in un colpo:

   ```bash
   mvn -DskipTests clean compile exec:java -Dexec.mainClass=com.gmms.App
   ```
   
   Nota: `exec:java` non esegue la compilazione da solo. Per questo negli esempi sopra richiamiamo anche le fasi `compile/package`.
   
👉 In entrambi i casi ti verrà richiesto l’input direttamente da terminale.

---

### 🔹 Modalità WebApp (Spring Boot)

La versione web utilizza la classe `WebApp` e fornisce un’interfaccia browser.

1. (A) Avvio rapido in sviluppo, esegui direttamente con Maven:

   ```bash
   mvn spring-boot:run
   ```

Oppure:

1. (B) Avvio da JAR, prima compila ed impacchetta l’applicazione:

   ```bash
   mvn -q -DskipTests clean package
   ```

   Questo comando genera il file `target/nonsense-generator-1.0-SNAPSHOT.jar`
  
   A questo punto puoi avviare la WebApp con:

   ```bash
   java -jar target/nonsense-generator-1.0-SNAPSHOT.jar
   ```

2. L'applicazione dispone di un controllo automatico delle porte occupate.

   Controlla quindi la shell e cerca il log contenente la porta del server:

   ```bash
   Tomcat started on port 8080 (http)
   ```

3. Apri il browser su:

   ```bash
   http://localhost:PORT_NUMBER/nonsense   # <--- sostituisci PORT_NUMBER con la tua porta
   ```

   👉 La porta di default su cui si espone il servizio resta comunque la `8080`.

4. Dall’interfaccia potrai:
   - inserire la frase in input
   - decidere se mostrare il Syntactic Tree
   - visualizzare template, parole selezionate, tossicità e frase nonsense.

---

### 🔹 Report dei test

Modalità di running dei vari test:

1. Compilare tutti i test:

   ```bash
   mvn clean test
   ```

   Prima esegue la fase clean (cancella la cartella `/target`), poi la fase test.

2. Generare il report totale in formato hmtl:

   ```bash
   mvn surefire-report:report
   ```

   👉 Otterrai un report HTML in `target/site/surefire-report.html`

---

## Dipendenze principali

Dal file `pom.xml`:

- **Spring Boot Starter Web** → server web embedded (Tomcat).
- **Spring Boot Starter Thymeleaf** → template engine per le viste HTML.
- **Spring Boot Starter Validation** → validazione degli input.
- **Google Cloud Language API** → analisi sintattica e moderazione testo.
- **Gson** → serializzazione/deserializzazione JSON.
- **JUnit Jupiter** → per i test unitari.

---

## Note finali

- È consigliato eseguire la WebApp direttamente con `java -jar` per evitare i warning di Maven.  
- Durante lo sviluppo puoi usare `mvn spring-boot:run` per sfruttare l’hot-reload.  
- La modalità CLI resta utile per debug o ambienti senza interfaccia grafica.
