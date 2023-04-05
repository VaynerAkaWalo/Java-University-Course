## Zadanie 13
### Opis zadania
Tworzony w tym zadaniu program ma być pewnego rodzaju magazynem. Ma on zarówno gromadzić dobra jak i realizować zamówienia użytkowników.

Typy przekazywanych obiektów to zwykłe liczby całkowite. Aby działać poprawnie System musi przechowywać informacje o aktualnym stanie magazynu.

Realizacja zamówienia polaga na obsłudze metody request. Za jej pomocą użytkownik przekazuje do systemu informacje o tym ile obiektów i jakiego typu potrzebuje.

Z chwilą zakończenia metody request uznaje się, że zamówienie zostało zrealizowane i z magazynu wydano odpowiednią liczbę obiektów.

Początkowy stan magazynu wynosi 0 - brak obiektów.

### Praca współbieżna
Oczekuje się, że system będzie pracować poprawnie w przypadku współbieżnego użycia wszystkich jego metod.

System nie może niepotrzebnie używać CPU - obciążenie CPU w okresie, gdy użytkownik nie wykonuje na systemie żadnych działań powinno być praktycznie równe zero.

System nie musi realizować zamówień w żadnej konkretnej kolejności. Zamówienia późniejsze mogą być zrealizowane przed wcześniejszymi.

Zamówienia, które mogą być zrealizowane muszą być zrealizowane. Niedopuszczalna jest sytuacja, gdy w systemie znajduje się odpowiednia ilość obiektów, a zamówienia na nie nie są wykonywane.

Obiekty nie mogą ginąć ani się powielać. Liczba obiektów w systemie musi być równa różnicy pomiędzy liczbą obiektów dostarczonych za pomocą add oraz liczbą wydanych poprzez realizację zamówień.

Metody add/getInventory nie mogą zablokować wątków użytkownika na dłuższy okres czasu (chodzi o okres czasu rzędu np. 1 sekundy)). Metoda request może trwale zablokować wątek użytkownika, tylko jeśli system nie będzie w stanie zrealizować zamówienia. Trwale oznacza "od wywołania do końca trwania testu".

Jeśli metoda getInventory zostanie wykonana po zakończenie metod add/request, to jej wynik musi uwzględniać zmiany spowodowane przez zakończone metody add/request.

### Przykłady
```
request( {0->5, 1->3} ) - wątek 1 wstrzymany
add( 0, 5 );
add( 1, 3 );
wątek 1 uaktywniony
```
```
add(0,2)
add(5,2)
request( {0->1, 5->1} ) - natychmiastowa realizacja zamówienia
getInventory zwraca mapę {0->1, 5->1}
```

```
request( {0->2, 1->2 }) - wątek 1 wstrzymany
request( {0->2, 1->2 }) - wątek 2 wstrzymany
request( {0->3, 1->3 }) - wątek 3 wstrzymany
add(0,5)
add(1,4)
wątki 1 i 2 obudzone LUB wątek 3 obudzony
```

### Dostarczanie rozwiązania
Proszę dostarczyć kod źródłowy klasy ```Supplier```. Ma ona implementować interfejs ```SupplierInterface```. Klasa ```Cache``` musi posiadać konstruktor bezparametrowy - to on zostanie użyty do wytworzenia obiektu. Kodu samego interfejsu proszę nie dodawać do własnych rozwiązań.