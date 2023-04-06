package Zadanie07;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FastHistogram implements Histogram{
    Map<Integer, Integer> map = new ConcurrentHashMap<>();
    volatile boolean[] done = new boolean[0];
    Vector vector;
    int threads = 0;

    @Override
    public void setup(int threads, int bins) {
        this.threads = Math.max(threads, 0);
        map = new ConcurrentHashMap<>(16, 0.75f, bins);
    }

    @Override
    public void setVector(Vector vector) {
        if(isReady())
            map.clear();

        if(vector == null)
            return;
        this.vector = vector;

        done = new boolean[threads];
        Arrays.fill(done, false);

        for (int i = 0; i < threads; i++) {
            int threadNumber = i;
            Thread thread = new Thread(() -> processVector(threadNumber, threads));
            thread.start();
        }
    }

    @Override
    public boolean isReady() {
        for (boolean b : done) {
            if(!b) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Integer, Integer> histogram() {
        return map;
    }

    private void processVector(int threadNumber, int step) {
        for (int i = threadNumber; i < vector.getSize(); i += step) {
            int num = vector.getValue(i);
            map.compute(num, (key, value) -> value == null ? 1 : value + 1);
        }

        done[threadNumber] = true;

    }
}

