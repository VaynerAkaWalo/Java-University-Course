package Zadanie01;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecoderTest {

    private Decoder decoder;

    @BeforeEach
    void setUp() {
        decoder = new Decoder();
    }

    @Test
    void withoutInputShouldReturnEmptyString() {
        assertEquals("", decoder.output());
    }

    @Test
    void onlyZeroBytesShouldReturnEmptyString() {
        byte[] input = {(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1};

        for (byte b : input) {
            decoder.input(b);
        }

        assertEquals("", decoder.output());
    }

    @Test
    void simpleCorrectInputRepetedOneTime() {
        byte[] input = {(byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1};

        for (byte b : input) {
            decoder.input(b);
        }

        assertEquals("1", decoder.output());
    }

    @Test
    void simpleCorrectInputRepetedTwoTimes() {
        byte[] input = {(byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 2};

        for (byte b : input) {
            decoder.input(b);
        }

        assertEquals("11", decoder.output());
    }

    @Test
    void advancedCorrectInput() {
        byte[] input = {(byte) 3,(byte) 2,(byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 2,
                (byte) 4,(byte) 5,(byte) 6, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 3,
                (byte) 7,(byte) 8,(byte) 9, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 4};

        for (byte b : input) {
            decoder.input(b);
        }

        assertEquals("321321456456456789789789789", decoder.output());
    }



    @Test
    void twoSetsOfData() {
        byte[] input = {(byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1, (byte) 1, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 1};

        for (byte b : input) {
            decoder.input(b);
        }

        assertEquals("11", decoder.output());
    }
}