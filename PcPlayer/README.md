<br>
<div align="center">
   <img src="images/final.png" alt="Logo" >
</div>

# Dog: PC-Beobachter Implementation 

---
Hiermit stellen wir Ihnen den PC-Beobachter des "Dog"-Spiels zur Verfügung.
In den folgenden Abschnitten wird genauer auf den PC-Beobachter eingehen. 
Weitere Informationen finden Sie in unserem Dev-Ops Dokument, welches wir ebenfalls im Repository hinterlegt haben.

## Build With

---
Wir haben den PC-Beobachter mithilfe von folgenden Tools entwickelt:

Java SE 11 - Java SE 11 (Java Standard Edition 11) ist eine Version der Java-Plattform,
die von Oracle Corporation veröffentlicht wurde. <br>

Intellij IDEA - IntelliJ IDEA ist eine integrierte Entwicklungsumgebung (IDE), die
von JetBrains entwickelt und u.a. für die Java-Programmierung konzipiert wurde. <br>

Scenebuilder - Scene Builder ist ein visuelles Layout-Werkzeug, das von Oracle für die
Entwicklung von Benutzeroberflächen mit JavaFX verwendet wird. <br>

JavaFX - JavaFX ist ein plattformübergreifendes Framework für die Entwicklung von
Benutzeroberflächen (GUIs) in Java. JavaFX bietet eine Vielzahl von Funktionen für die
Erstellung interaktiver Anwendungen mit verschiedenen Designs. <br>

Gson - Gson ist eine Java-Bibliothek, die von Google entwickelt wurde und die Konvertierung
zwischen JSON (JavaScript Object Notation) und Java-Objekten erleichtert. <br>

JSON ist ein leicht lesbares Datenformat, das häufig für den Datenaustausch zwischen
Servern und Webanwendungen verwendet wird. Die Gson-Bibliothek ermöglicht es, Java-Objekte in JSON-Strings umzuwandeln (Serialisierung) und umgekehrt JSON-Strings in
Java-Objekte zu konvertieren (Deserialisierung). <br>

JUnit - JUnit ist ein Framework für das Testen von Java-Anwendungen. Es ermöglicht
Entwicklern, automatisierte Tests für ihre Java-Programme zu erstellen und auszuführen <br>


## Weiterentwicklung

---
Mit dem Beobachter liefern wir Ihnen ein Common Package mit. In diesem Common Package befinden sich alle Dto Klassen, die aus dem Interface-Dokument einhergingen.
Um die Nachrichten vom Server zu interpretieren, haben wir einen Deserializer geschrieben. 

Dieser funktioniert wie folgt:

    Deserializer deserializer = new Deserializer(message); // message ist z.B. ein ausgelesener String aus dem InputStream
    Class<?> dtoClass = deserializer.getMessageDtoClass(); // gibt zurück zu welcher Klasse die Nachricht gehört (null, wenn Nachricht nicht zugeordnet werden kann)

Die Nachricht kann dann wie folgt in das jeweilige Objekt umgewandelt werden:

    if(dtoClass == ConnectedToServerDto.class) {
        ConnectedToServerDto dto = (ConnectedToServerDto) deserializer.deserialize();
    }

Die Client-Klasse empfängt Nachrichten innerhalb eines Threads und sendet Nachrichten mit der sendMessage() Methode. 

Um Nachrichten zu senden, gehen wir wie folgt vor:

    client.sendMessage((new ConnectToServerDto(username, true)).toJson());

Wie Sie erkennen können, haben unsere Dto-Objekte eine toJson()-Methode, mit der das Objekt in ein json String serialisiert wird, bevor die Nachricht an den Server gesendet wird.
Das Client-Objekt wird derzeit in dem PCObserverControllerMenu erzeugt und an den PCObserverControllerGameplay weitergegeben. Hier können Sie jetzt über dieses Client-Objekt Nachrichten, wie im Beispiel demonstriert, senden.

## Kontakt

---
Für aufgetretene Fehler und Fragen wenden Sie sich gerne an uns, wir werden unser Bestes geben Ihr Anliegen so schnell wie möglich zu bearbeiten.

Sie erreichen uns wie folgt: 
 - **[Discord-Server](https://discord.gg/DxrWd93Q)**
 - **[Issueboard](https://git.cs.uni-paderborn.de/swtpra2324/beobachter/beobachter8/-/issues)** 
 - E-Mail an **[twickert@mail.upb.de](mailto:twickert@mail.upb.de)**