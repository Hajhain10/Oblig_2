package no.oslomet.cs.algdat;


////////////////// class DobbeltLenketListe //////////////////////////////


import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.StringJoiner;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;



public class DobbeltLenketListe<T> implements Liste<T> {

    /**
     * Node class
     * @param <T>
     */
    private static final class Node<T> {
        private T verdi;                   // nodens verdi
        private Node<T> forrige, neste;    // pekere

        private Node(T verdi, Node<T> forrige, Node<T> neste) {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    }

    // instansvariabler
    private Node<T> hode;          // peker til den første i listen
    private Node<T> hale;          // peker til den siste i listen
    private int antall;            // antall noder i listen
    private int endringer;         // antall endringer i listen

    public DobbeltLenketListe() {
        //throw new NotImplementedException();
    }

    public DobbeltLenketListe(T[] a) {
        //dersom arrayen er tom kastes et unntak
        if (a == null) {
            throw new NullPointerException("tabellen er tom");

        }
        //Dette er en peker, som ble brukt til å sette neste og forige node.
        Node<T> tmp = null;

        for (Integer i = 0; i < a.length; i++) {
            if (a.length == 1) {
                if (a[i] != null) {
                    Node<T> node = new Node<T>(a[i]);
                    hode = node;
                    hale = node;
                    antall++;
                }
            } else if (a[i] != null) {
                if (hode == null) {
                    Node<T> node = new Node<T>(a[i]);
                    hode = node;
                    hale = node;
                    hode.forrige = null;
                    hode.neste = null;
                    tmp = node;
                    antall++;
                }

                else {
                    Node<T> node = new Node<T>(a[i]);
                    node.forrige = tmp;
                    tmp.neste = node;
                    tmp = node;
                    hale = node;
                    antall++;
                }
            }

        }

    }

    public Liste<T> subliste(int fra, int til){
        throw new NotImplementedException();
    }

    @Override
    public int antall() {
        return antall;

    }

    @Override
    public boolean tom() {

        return (antall==0);
    }

    @Override
    public boolean leggInn(T verdi) {
      if(verdi == null){
          throw new NullPointerException("Ma ha en verdi");
      }
       //listen er tom
       if(tom()){
           Node<T> nynode = new Node<T>(verdi);
           hode = hale = nynode;
           antall++;
           endringer++;
           return true;
       }else {
           //noden leeges i slutten av listen
           Node<T> nynode = new Node<T>(verdi);
           Node gammel_siste = hale;
           //neste for gammel var null
           gammel_siste.neste = nynode;
           nynode.forrige = gammel_siste;
           hale = nynode;
           antall++;
           endringer++;
           return true;
       }
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T hent(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public int indeksTil(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new NotImplementedException();
    }

    @Override
    public boolean fjern(T verdi) {
        throw new NotImplementedException();
    }

    @Override
    public T fjern(int indeks) {
        throw new NotImplementedException();
    }

    @Override
    public void nullstill() {
        Node start = hode;
        start.verdi = null;
        start = start.neste;
        while(start.neste!=null){
            start.forrige.neste = null;
            start.forrige = null;
            start.verdi = null;
            start = start.neste;
        }
        start.verdi = null;
        start.forrige.neste = null;
        start.forrige = null;
        System.out.println(hale.verdi +"  "+ hode.verdi);

        //throw new NotImplementedException();

    }

    @Override
    public String toString() {
        if (tom() || hode == null){
            return "[]";
        }
        Node forste = hode;
        StringBuilder ut = new StringBuilder("["+forste.verdi);
        forste = forste.neste;
        while(forste != null){
            ut.append(", "+forste.verdi);
            forste = forste.neste;
        }
        ut.append("]");
        return ut.toString();
    }

    public String omvendtString() {
        if (tom() || hode == null){
            return "[]";
        }
        Node sist = hale;
        String ut = "["+sist.verdi;
        sist = sist.forrige;
        while(sist != null){
            ut+=", "+sist.verdi;
            sist = sist.forrige;
        }
        ut+="]";
        return ut;
    }

    @Override
    public Iterator<T> iterator() {
        throw new NotImplementedException();
    }

    public Iterator<T> iterator(int indeks) {
        throw new NotImplementedException();
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator(){
            throw new NotImplementedException();
        }

        private DobbeltLenketListeIterator(int indeks){
            throw new NotImplementedException();
        }

        @Override
        public boolean hasNext(){
            throw new NotImplementedException();
        }

        @Override
        public T next(){
            throw new NotImplementedException();
        }

        @Override
        public void remove(){
            throw new NotImplementedException();
        }

    } // class DobbeltLenketListeIterator

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new NotImplementedException();
    }

} // class DobbeltLenketListe


