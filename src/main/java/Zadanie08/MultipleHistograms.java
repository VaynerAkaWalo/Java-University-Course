package Zadanie08;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MultipleHistograms implements Histogram{
    Consumer<HistogramResult> consumer;

    @Override
    public void setup(int bins, Consumer<HistogramResult> histogramConsumer) {
        consumer = histogramConsumer;
    }

    @Override
    public void addVector(int vectorID, Vector vector) {
        if(consumer != null) {
            Thread thread = new Thread(() -> processVector(vectorID, vector));
            thread.start();
        }
    }

    private void processVector(int id, Vector vector) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < vector.getSize(); i++) {
            int num = vector.getValue(i);
            map.compute(num, (key, value) -> value == null ? 1 : value + 1);
        }

        Thread thread = new Thread(() -> sendHistogram(id, map));
        thread.start();
    }

    private void sendHistogram(int id, Map<Integer, Integer> map) {
        consumer.accept(new HistogramResult(id, map));
    }
}
