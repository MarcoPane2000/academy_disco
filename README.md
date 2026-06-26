openapi-docs: http://localhost:8080/swagger-ui/index.html

# Configurazione SSH per GitHub

Questa guida descrive come:

* Generare una chiave SSH
* Configurarla su GitHub
* Clonare un repository tramite SSH

---

## 1. Verificare la presenza di una chiave SSH

Aprire il terminale ed eseguire:

```bash
ls -la ~/.ssh
```

Se sono presenti file come:

```
id_ed25519
id_ed25519.pub
```

è già disponibile una chiave SSH.

---

## 2. Generare una nuova chiave SSH

Eseguire il comando:

```bash
ssh-keygen -t ed25519 -C "tuo@email.com"
```

Se `ed25519` non è supportato:

```bash
ssh-keygen -t rsa -b 4096 -C "tuo@email.com"
```

Quando richiesto:

* Premere **Invio** per utilizzare il percorso predefinito (`~/.ssh/id_ed25519`).
* Inserire una passphrase (opzionale).

---

## 3. Avviare l'SSH Agent

```bash
eval "$(ssh-agent -s)"
```

Aggiungere la chiave:

```bash
ssh-add ~/.ssh/id_ed25519
```

---

## 4. Copiare la chiave pubblica

Visualizzare la chiave:

```bash
cat ~/.ssh/id_ed25519.pub
```

Copiare l'intero contenuto.

Esempio:

```text
ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAI... tuo@email.com
```

---

## 5. Aggiungere la chiave a GitHub

1. Accedere al proprio account GitHub.
2. Aprire **Settings** → **SSH and GPG keys**.
3. Fare clic su **New SSH key**.
4. Compilare i campi:

   * **Title**: nome del dispositivo (es. *Laptop Aziendale*)
   * **Key**: incollare la chiave pubblica.
5. Salvare con **Add SSH key**.

---

## 6. Verificare la connessione

Eseguire:

```bash
ssh -T git@github.com
```

Alla prima connessione confermare digitando:

```text
yes
```

Se la configurazione è corretta verrà visualizzato un messaggio simile al seguente:

```text
Hi <username>! You've successfully authenticated...
```

---

## 7. Clonare un repository

Dal repository GitHub selezionare **Code** → **SSH** e copiare l'URL.

Esempio:

```text
git@github.com:utente/nome-repository.git
```

Clonare il repository:

```bash
git clone git@github.com:utente/nome-repository.git
```

Entrare nella cartella del progetto:

```bash
cd nome-repository
```

---

## Riepilogo dei comandi

```bash
# Genera una chiave SSH
ssh-keygen -t ed25519 -C "tuo@email.com"

# Avvia l'SSH Agent
eval "$(ssh-agent -s)"

# Aggiunge la chiave all'agent
ssh-add ~/.ssh/id_ed25519

# Visualizza la chiave pubblica
cat ~/.ssh/id_ed25519.pub

# Verifica la connessione con GitHub
ssh -T git@github.com

# Clona un repository
git clone git@github.com:utente/nome-repository.git
```
