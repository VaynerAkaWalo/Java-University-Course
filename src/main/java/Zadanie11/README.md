## Zadanie 11
### Idea zadania
Z bazy danych SQLite należy odczytać dane umożliwiające utworzenie prostego zdania.

### Baza
Schemat (struktura) bazy danych składa się z 4-ech tabel:

```sql
CREATE TABLE IF NOT EXISTS "Czynnosc" (
"CzynnoscID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
"Nazwa"	TEXT NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS "Przedmiot" (
"PrzedmiotID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
"Nazwa"	TEXT NOT NULL UNIQUE
);
CREATE TABLE IF NOT EXISTS "Zdanie" (
"ZdanieID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
"ImieID"	INTEGER NOT NULL,
"CzynnoscID"	INTEGER NOT NULL,
"PrzedmiotID"	INTEGER NOT NULL
);
CREATE TABLE IF NOT EXISTS "Imie" (
"ImieID"	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
"Imie"	TEXT NOT NULL UNIQUE,
"Plec"	INTEGER NOT NULL
);
```

Tabele w dołączonej bazie zawierają przykładowe wpisy.

### Przepływ danych
Ja przekazuję do Państwa programu informację o położeniu pliku z bazą. Następnie wysyłam numer "ZdanieID". Odbieram zdanie utworzone na podstawie danych zapisanych w bazie.

UWAGA: cześć imion to imiona męskie, część kobiece. O przypisaniu imienia do płci decyduje pole "Plec" w tabli "Imie". 0 wskazuje imię kobiece, 1 męskie. Tworzone zdanie należy odpowiednio przekształcić.

### Obsługa bazy - odczyt danych
Zakładam tu minimalną Państwa wiedzę o bazach danych. Odpowiednie zapytanie SQL podam poniżej. Państwa zadaniem jest poskładanie z nich działającego kodu w Java.

Najpierw użycie samej bazy. W przypadku SQLite można użyć programu konsolowego sqlite3 lub sqlitebrowser. Drugi z programów ma GUI!

### Odczytanie danych z tabeli:

Najprostsza postać polecenie pobierającego dane z tabeli o nazwie X to:

```sql
select * from X;
```
Pobranie konkretnego wiersza (krotki) z tabeli X, gdy znamy wartość V klucza o nazwie K.
```sql
select * from X where K = V;
```

W przypadku tabeli Zdanie, będzie to coś takiego:

```sql
select * from Zdanie where ZdanieID = V;
```
### Przykłady
W tabeli Zdanie znajdują się następujące wpisy:

```sql
sqlite> select * from Zdanie;
1|1|1|1
2|1|2|3
```
Mają one doprowadzić do wygenerowania następujących zdań:

```sql
1 -> "Adam kupil krzeslo."
2 -> "Adam sprzedal cyrkiel."
```

W przypadku imienia kobiecego do czynności dodajemy na końcu literę "a".

Np. dla ImieID=2, CzynnoscID=3, PrzedmiotID=2 mamy:

```Barbara pomalowala fortepian.```

Tak, na końcu zdania dodajemy kropkę.

### Polskie literki.
Nie ma...

### Sterownik do bazy danych.
Sterownik zostanie przeze mnie dostarczony do użycia. Tj. przed wywołaniem Państwa kodu ja wykonam:

```
Class.forName( "org.sqlite.JDBC" );
```

### Dostarczanie rozwiązania
Proszę o dostarczenie kodu klasy ```ZdaniaSQL```. Klasa ma implementować interfejs ```GeneratorZdan``` zgodnie z dokumentacją i opisem. Jeśli pojawi się w niej konstruktor, to musi on być konstruktorem bezparametrowym.