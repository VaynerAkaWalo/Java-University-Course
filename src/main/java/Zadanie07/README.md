## Zadanie 07
### Idea zadania
Zadanie sprowadza się przetwarzania wektora liczb w histogram pokazujący liczbę wystąpień danej liczby w wektorze. Aby histogram został utworzony możliwie szybko, dane z wektora należy przetwarzać za pomocą wskazanej liczby wątków.

### Przetwarzanie danych
W tym zadaniu, w tym samym czasie, przetwarzany jest jeden wektor na jeden histogram. UWAGA: wynik to pojedynczy histogram utworzony ze wszystkich liczb wektora. Przetwarzanie kolejnego wektora może rozpocząć się dopiero po zakończeniu operacji dla wcześniejszego wektora.

Program ma działać tak:

- Użytkownik konfiguruje system za pomocą metody setup
- Użytkownik dostarcza wektor z danymi setVector. Metoda setVector rozpoczyna asynchroniczne wyliczanie histogramu za pomocą określonej w setup liczby wątków.
- Metoda setVector kończy się, a prace nad histogramem nadal trwają
- Użytkownik co jakiś czas pyta o możliwość odebrania wyniku (isReady)
- Jeśli użytkownik otrzyma isReady==true, odbiera histogram
- Użytkownik może przekazać następny wektor do przetworzenia...
- itd.

### Histogram
Jeśli wektor liczb będzie zawierać np.
```
1 2 3 4 1 2 1 2 1 1 1 3 8
```
to wynikiem ma być histogram w postaci:
```
1=>6 2=>3 3=>2 4=>1 8=>1
```
Kluczami w mapie reprezentującej histogram są liczby, które są w wektorze, wartość to liczba powtórek danej liczby.

### Dostarczanie rozwiązania
Proszę o dostarczenie kodu klasy ```FastHistogram```. Klasa ma implementować interfejs ```Histogram``` zgodnie z dokumentacją i opisem. Jeśli pojawi się w niej konstruktor, to musi on być konstruktorem bezparametrowym.