package Zadanie01;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa dekodera.
 */
public class Decoder {

    private final List<Byte> input = new ArrayList<>();

    /**
     * Metoda pozwalająca na wprowadzanie danych.
     * @param value dostarczona wartość
     */
    public void input( byte value ) {
        if(value >= 0 && value < 10) {
            input.add(value);
        }

    }

    /**
     * Metoda pozwalająca na pobranie wyniku dekodowania danych.
     * @return wynik działania
     */
    public String output() {
        StringBuilder result = new StringBuilder();
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.size(); i++) {
            if (input.get(i) != 0) {
                buffer.append(input.get(i));
            }
            else {
                if (i + 4 >= input.size()) {
                    return result.toString();
                }
                else {
                    int repeats = input.get(i + 1) * 1000
                            + input.get(i + 2) * 100
                            + input.get(i + 3) * 10
                            + input.get(i + 4);
                    result.append(buffer.toString().repeat(repeats));
                    buffer.setLength(0);
                    i += 4;
                }
            }
        }
        return result.toString();
    }

    /**
     * Przywrócenie początkowego stanu obiektu.
     */
    public void reset() { input.clear();}
}
