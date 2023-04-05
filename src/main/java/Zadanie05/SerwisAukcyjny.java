package Zadanie05;

import java.util.*;

public class SerwisAukcyjny implements Aukcja{
    private HashMap<String, Powiadomienie> users = new HashMap<>();
    private HashMap<Integer, Product> products = new HashMap<>();



    @Override
    public void dodajUżytkownika(String username, Powiadomienie kontakt) {
        users.put(username, kontakt);
    }

    @Override
    public void dodajPrzedmiotAukcji(PrzedmiotAukcji przedmiot) {
        products.put(przedmiot.identyfikator(), new Product(przedmiot.identyfikator(), przedmiot.nazwaPrzedmiotu(), przedmiot.aktualnaCena()));
    }

    @Override
    public void subskrypcjaPowiadomień(String username, int identyfikatorPrzedmiotuAukcji) {
        if(products.containsKey(identyfikatorPrzedmiotuAukcji)) {
            products.get(identyfikatorPrzedmiotuAukcji).addSubscription(username);
        }
    }

    @Override
    public void rezygnacjaZPowiadomień(String username, int identyfikatorPrzedmiotuAukcji) {
        if(products.containsKey(identyfikatorPrzedmiotuAukcji)) {
            products.get(identyfikatorPrzedmiotuAukcji).stopSubscription(username);
        }
    }

    @Override
    public void oferta(String username, int identyfikatorPrzedmiotuAukcji, int oferowanaKwota) {
        if(products.containsKey(identyfikatorPrzedmiotuAukcji) && !products.get(identyfikatorPrzedmiotuAukcji).ended) {
            Offer newOffer = new Offer(username, oferowanaKwota);

            products.get(identyfikatorPrzedmiotuAukcji).addOffer(newOffer);
            Set<String> toNotify = products.get(identyfikatorPrzedmiotuAukcji).getUsersToNotify(newOffer);

            for(String user : toNotify) {
                users.get(user).przebitoTwojąOfertę(products.get(identyfikatorPrzedmiotuAukcji));
            }
        }
    }

    @Override
    public void koniecAukcji(int identyfikatorPrzedmiotuAukcji) {
        if(products.containsKey(identyfikatorPrzedmiotuAukcji)) {
            products.get(identyfikatorPrzedmiotuAukcji).auctionEnd();
        }
    }

    @Override
    public String ktoWygrywa(int identyfikatorPrzedmiotuAukcji) {
        if(products.containsKey(identyfikatorPrzedmiotuAukcji)) {
            return products.get(identyfikatorPrzedmiotuAukcji).whoWins().username;
        }
        return null;
    }

    @Override
    public int najwyższaOferta(int identyfikatorPrzedmiotuAukcji) {
        if(products.containsKey(identyfikatorPrzedmiotuAukcji)) {
            return products.get(identyfikatorPrzedmiotuAukcji).whoWins().price;
        }
        return 0;
    }

    class Product implements Aukcja.PrzedmiotAukcji{

        int id;
        String name;
        int minimalPrice;

        private Set<String> subscriptions = new HashSet<>();

        private List<Offer> offers = new ArrayList<>();

        private boolean ended = false;

        public Product(int id, String name, int minimalPrice) {
            this.id = id;
            this.name = name;
            this.minimalPrice = minimalPrice;
        }

        @Override
        public int identyfikator() {
            return id;
        }

        @Override
        public String nazwaPrzedmiotu() {
            return name;
        }

        @Override
        public int aktualnaOferta() {
            if(offers.isEmpty())
                return 0;
            return Collections.max(offers).price;
        }

        @Override
        public int aktualnaCena() {
            return minimalPrice > aktualnaOferta() ? minimalPrice : aktualnaOferta();
        }

        public void addSubscription(String username) {subscriptions.add(username);}

        public void stopSubscription(String username) {
            subscriptions.remove(username);
        }

        public void addOffer (Offer offer) {
            if(!ended) {
                offers.add(offer);
            }
        }

        public void auctionEnd() {
            ended = true;
        }

        public Offer whoWins() {
            if(offers.isEmpty())
                return new Offer("", 0);
            return Collections.max(offers);
        }

        public Set<String> getUsersToNotify(Offer newOffer) {
            Set<String > result = new HashSet<>();
            for(Offer offer : offers) {
                if(newOffer.price > offer.price) {
                    if(subscriptions.contains(offer.username))
                        result.add(offer.username);
                }
            }
            result.remove(newOffer.username);

            return result;
        }
    }

    record Offer (String username, Integer price) implements Comparable<Offer> {

        @Override
        public int compareTo(Offer o) {
            return price.compareTo(o.price);
        }
    }
}

