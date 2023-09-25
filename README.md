# BI-TJV Semestrální práce
Serverová aplikace v Javě nebo Kotlinu s minimálně dvěma asociovanými entitami, s funkcionalitou zveřejněnou jako webová restová služba a persistentní vrstvou v JDBC nebo JPA.

## Požadavky
Aplikace je typu klient–server. Pracuje se 3 doménovými typy a implementuje nad nimi kompletní CRUD (základní datové operace vložení, čtení, aktualizace, smazání).

### Serverová část
- je třívrstvá aplikace
- je postavená na Spring frameworku a napsaná v Javě
- využívá objektově-relační mapování v persistentní vrstvě
    - lze použít libovolnou relační databázi s persistentním úložištěm dat, která obslouží více požadavků (DB server)
        - nepočítá se tedy in-memory ani jednoduchá vestavěná souborová databáze (např. SQLite)
    - pracuje s alespoň 3 entitami z relační databáze (všechny CRUD operace, min. 1 vazba many-to-many, tedy v relační databázi budou 4 tabulky; s použitím ORM)
    - s použitím ORM implementuje dotaz navíc (nad rámec CRUD a manipulace s M:N vazbou, dotaz realizovaný ideálně v JPQL)
- vrstva aplikační logiky umožňuje všechny operace datové vrstvy (CRUD nad všemi entitami, manipulaci s M:N vazbou, dotaz navíc)
- vhodně navržené a zdokumentované REST API zpřístupňuje všechny operace vrstvy aplikační logiky (včetně CRUD nad všemi entitami)
    - dodržení webových standardů vč. HTTP status
    - dodržení zásad RESTful bude součástí hodnocení
    - kompletní, dobře zpracovaná a strojově čitelná dokumentace API (ideálně OpenAPI): všechny zdroje (adresy), operace i datové formáty
    - je důrazně doporučený separátní model dat pro API (třídy „DTO“)
- obsahuje automatické testy
    - tři různé typy testů, které byly probírány v předmětu
- je vhodně sestavovaná (Gradle) a v rámci sestavení jsou spouštěné a vyhodnocené testy
- je verzovaná s použitím systému git
    - repozitář umístěný na Gitlab FIT (gitlab.fit.cvut.cz/<username>/<server_repo>) by měl být použitý k vývoji i odevzdání

### Klientská část
- libovolný programovací/skriptovací jazyk
- libovolné rozhraní (GUI, webová aplikace, interaktivní konzolová aplikace)
- přistupuje k RESTful webové službě zpřístupněné serverovou částí
- implementuje komplexní business operaci pomocí serverové části
    - tato operace (z pohledu např. uživatele jde o jedinou akci, např. spuštěnou tlačítkem ve formuláři) je z pohledu systému složená z více dílčích kroků, např. databázových operací nebo rozhodování (if) na straně klienta
- je verzovaná
    - repozitář umístěný na Gitlab FIT (gitlab.fit.cvut.cz/<username>/<klient_repo>, může jít o stejný repozitář jako pro server) by měl být použitý k vývoji i odevzdání
