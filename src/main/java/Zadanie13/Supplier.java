package Zadanie13;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Supplier implements SupplierInterface{
    final Map<Integer, Integer> supply = new ConcurrentHashMap<>();
    final Object waiter = new Object();


    @Override
    public void add(int objectID, int quantity) {
        synchronized (waiter) {
            supply.compute(objectID, (key, value) -> value == null ? quantity : value + quantity);
            waiter.notifyAll();
        }
    }

    @Override
    public void request(Map<Integer, Integer> order) {
        Map<Integer, Integer> req = new HashMap<>(order);
        while (!req.isEmpty()){
            try {
                Iterator<Integer> iterator = req.keySet().iterator();
                while (iterator.hasNext()) {
                    Integer key = iterator.next();
                    if(req.get(key) > supply.getOrDefault(key, 0)) {
                        throw new NotEnoughItemsException("missing: " + key);
                    }
                    else {
                        supply.compute(key, (k, v) -> v - req.get(key));
                        iterator.remove();
                    }
                }
            }
            catch (NotEnoughItemsException e) {
                synchronized (waiter) {
                    waitHelper(waiter);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public Map<Integer, Integer> getInventory() {
        return new HashMap<>(supply);
    }

    private void waitHelper(Object object) {
        try {
            object.wait();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class NotEnoughItemsException extends Exception {
    public NotEnoughItemsException(String message) {
        super(message);
    }
}

