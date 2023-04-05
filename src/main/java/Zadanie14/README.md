## Zadanie 14
### Gra w zycie
Zadanie polega na napisaniu klasy ```LifeGame```, ktora zaimplementuje interfejs ```LifeGameInterface```. Klasa ma posiadać bezparametrowy konstruktor, który nie deklaruje użycia wyjątków weryfikowalnych.

Klasa ```LifeGame``` ma pracować jako silnik gry w zycie. Zadanie polega na generowaniu przejść pomiędzy kolejnymi stanami pewnego systemu komórek. Gra opisana jest w np. w [wikipedii](http://pl.wikipedia.org/wiki/Gra_w_%C5%BCycie). Na [tej stronie](http://www.mimuw.edu.pl/~ajank/zycie/) mozna zobaczyc jej klasyczna postac.

W przypadku tego zadania tablica, ktora przechowuje stan ukladu ma zmieniac swoj rozmiar tak, by wszystkie zywe komorki mogly zostac w niej zapisane - oczywiscie, zakładamy, że mamy wystarczająco dużo pamięci by taką tablicę móc utworzyć.

Kolejną zmianą w stosunku do wersji klasycznej jest uogólnienie reguł gry. W wywolaniu metody ```oneStep``` użytkownik podaje parametry dotyczące warunkow przetwania żywej komórki oraz narodzenia nowej