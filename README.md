# proof-of-concept-services Project Kundendokumentation

## Einleitung

Das Projekt "proof-of-concept-services" soll die beiden Java-Frameworks Spring-Boot und Quarkus vergleichen. Fachliche Anforderungen der beiden Softwarelösungen sind hierbei gleich. Diese werden im Projekt in verschiedenen Branches gelagert.

## Starten der Anwendung

Um die Anwendung zu starten, müssen folgende Schritte durchgegangen werden:

1. Falls nötig, auf den Branch **master-quarkus** wechseln.
2. Einen Command-Prompt öffnen und auf das Verzeichnis des Projektes wechseln.
3. Folgenden Befehl ausführen:

   **path/to/java.exe -Dfile.encoding=windows-1252 -jar -path/to/jar-file**

## Ausführen einer API-Anfrage:

Nachdem der Service erfolgreich hochgefahren ist, kann die API via HTTP angefragt werden. Hierfür wird ein GET-Request benötigt und kann mit curl umgesetzt werden oder in der API-Spezifikation der Software ausprobiert werden. Diese kann, falls die Anwendung gestartet ist, unter folgendem Link gefunden werden:

http://localhost:8080/q/swagger-ui/

## Durchführen von Lasttests

Um lokale Lasttests auf die API durchführen zu können, kann das mitgelieferte Template im Projekt verwendet werden. Dieses ist in Scala umgesetzt und muss dementsprechend auch geändert werden.

Mit diesem Template lassen sich individuelle Lasttests starten und somit verschiedene Experimente durchführen. Gatling generiert nach erfolgreichem Abschluss der Tests eine HTML-Datei, in welcher die Testergebnisse grafisch aufbereitet werden. Der Pfad zur Datei wird von der Software ausgeloggt.

