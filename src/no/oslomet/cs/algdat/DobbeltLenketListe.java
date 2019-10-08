package no.oslomet.cs.algdat;

////////////////// class DobbeltLenketListe //////////////////////////////

import java.util.*;


////////////////// Oppgave1 //////////////////////////////
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

    ////////////////// Oppgave2 //////////////////////////////
    @Override
    public String toString() {
        if (tom()){
            return "[]";
        }else if(hode.verdi == null){
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
        if (tom()){
            return "[]";
        }else if(hode.verdi == null){
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

    ////////////////// Oppgave3a //////////////////////////////
    private Node<T> finnNode(int indeks){

        // Hvis indeks er større starter vi ved hode
        if(indeks <= (antall/2)){
            Node p = hode;
            for(int i =0; i < indeks; i++){
                p = p.neste;
            }
            return p;
        }

        // Hvis indeks er mindre starter vi ved halen
        else{
            Node q = hale;
            int tmp = antall - indeks;
            for(int i = antall; i <= tmp; i--){
                q = q.forrige;
            }
            return q;
        }

    }

    ////////////////// Oppgave3a //////////////////////////////
    @Override
    public T hent(int indeks) {

        indeksKontroll(indeks,false);
        return finnNode(indeks).verdi;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        if(nyverdi == null){
            throw new NullPointerException("Ny verdi kan ikke være null");
        }
        indeksKontroll(indeks, false);

        Node p = finnNode(indeks);
        T gammel = (T) p.verdi;

        p.verdi = nyverdi;
        endringer++;
        return gammel;

    }

    ////////////////// Oppgave3b //////////////////////////////
    private void fratilKontroll(int fra, int til, int antall){
        if(fra < 0){
            throw new IndexOutOfBoundsException("fra ma være storre enn 0");
        }
        if(til > antall){
            throw new IndexOutOfBoundsException("til kan ikke være større enn antall");
        }
        if(fra>til){
            throw new IllegalArgumentException("fra kan ikke vaere storre enn til");
        }
    }

    public Liste<T> subliste(int fra, int til){
        fratilKontroll(fra,til,antall);
        DobbeltLenketListe<T> subliste = new DobbeltLenketListe<>();
        if(tom()){
            return subliste;
        }
        Node p = hode;
        for(int i = 0; i<til; i++){
            if(i >= fra){
                subliste.leggInn((T) p.verdi);
            }
            p = p.neste;
        }
        return subliste;
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

    ////////////////// Oppgave4 //////////////////////////////
    @Override
    public int indeksTil(T verdi)
    {
        if (verdi == null) return -1;

        Node<T> p = hode;

        for (int indeks = 0; indeks < antall; indeks++, p = p.neste)
        {
            if (p.verdi.equals(verdi)) return indeks;
        }

        return -1;
    }


    @Override
    public boolean inneholder(T verdi)
    {
        return indeksTil(verdi) != -1;
    }


    ////////////////// Oppgave5 //////////////////////////////
    @Override
    public void leggInn(int indeks, T verdi)
    {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);

        if (tom())                              // tom liste
        {
            hode = hale = new Node<>(verdi, null, null);
        }
        else if (indeks == 0)                   // ny verdi forrest
        {
            hode = hode.forrige = new Node<>(verdi, null, hode);
        }
        else if (indeks == antall)              // ny verdi bakerst
        {
            hale = hale.neste = new Node<>(verdi, hale, null);
        }
        else                                    // ny verdi på plass indeks
        {
            Node<T> p = finnNode(indeks);     // ny verdi skal til venstre for p
            p.forrige = p.forrige.neste = new Node<>(verdi, p.forrige, p);
        }

        antall++;            // ny verdi i listen
        endringer++;   // en endring i listen
    }

    ////////////////// Oppgave6 //////////////////////////////
    @Override
    public boolean fjern(T verdi) {
        //Med Iterator kan vi kalle på hasNext() metoden
        Iterator<T> iterator = iterator();

        //Fjerner nullverdier
        if(iterator == null){

            while(iterator.hasNext()){

                if(iterator.next() == null){
                    iterator.remove();
                    return true;
                }
            }
        }

        else {
            while (iterator.hasNext()){

                if(verdi.equals(iterator.next())){
                    iterator.remove();
                    return true;
                }
            }
        }

        endringer++;
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks,false);

        T midlertidig;

        if(indeks == 0){

            midlertidig = hode.verdi;

            if(antall==1){
                hode = hale = null;
            }else {
                hode = hode.neste;
                hode.forrige = null;
            }

        }
        else {
            Node<T> nodeForan = finnNode((indeks - 1));
            Node<T> fjernNode = finnNode(indeks);
            midlertidig = fjernNode.verdi;

            if(fjernNode == hale){
                hale = hale.forrige;
                hale.neste = null;
            }
            else {
                nodeForan.neste = fjernNode.neste;
                nodeForan.neste.forrige = nodeForan;

            }


        }

        antall--;
        endringer++;
        return midlertidig;
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
            endringer++;
        }
        start.verdi = null;
        start.forrige.neste = null;
        start.forrige = null;
        hode.verdi = null;
        antall = 0;

        /*
        {
            Node starten = hode;
            int hjelpeverdi = 0;
            while(starten.neste!=null){
                fjern(hjelpeverdi);
                hjelpeverdi++;
                endringer++;
            }
            fjern(hjelpeverdi);
            hode.verdi = null;
            antall = 0;
        }
        */
    }

    ////////////////// Oppgave8 //////////////////////////////
    @Override
    public Iterator<T> iterator() {

        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks)
    {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
    }

    private class DobbeltLenketListeIterator implements Iterator<T>
    {
        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator()
        {
            denne = hode;     // p starter på den første i listen
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks)
        {
            denne = finnNode(indeks);  // noden med oppgitt indeks;
            fjernOK = false;  // blir sann når next() kalles
            iteratorendringer = endringer;  // teller endringer
        }

        @Override
        public boolean hasNext()
        {
            return denne != null;
        }

        @Override
        public T next()
        {
            if (!hasNext()) throw new NoSuchElementException("Ingen verdier!");

            if (endringer != iteratorendringer)
                throw new ConcurrentModificationException("Listen er endret!");

            T tempverdi = denne.verdi;
            denne = denne.neste;

            fjernOK = true;

            return tempverdi;
        }

        ////////////////// Oppgave9 //////////////////////////////
        @Override
        public void remove(){
            if(!fjernOK){
                throw new IllegalStateException();
            }
            if(endringer != iteratorendringer){
                new ConcurrentModificationException();
            }
            fjernOK = false;
            Node <T> p = null;

            if (antall == 1)    // Når det bare er en node i listen
            {
                hode = null;
                hale = null;
            }
            else if(denne == null){ // Når du skal fjerne den siste
                hale = hale.forrige;
                hale.neste = null;
            }
            else if(denne.forrige==hode){ // Når du skal fjerne den første
                hode=denne;
                hode.forrige = null;
            }
            else{
                //Når du skal fjerne noden som ligger mellom hode og hale
                p = denne.forrige;
                p.forrige.neste = p.neste;
                p.neste.forrige = p.forrige;
            }

            iteratorendringer++;
            endringer++;
            antall--;
            //throw new NotImplementedException();
        }
    } // class DobbeltLenketListeIterator

    ////////////////// Oppgave10 //////////////////////////////
    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c)
    {
        for (int n = liste.antall(); n > 0; n--)
        {
            Iterator<T> iterator = liste.iterator();
            int m = 0;
            T minverdi = iterator.next();
            for (int i = 1; i < n; i++)
            {
                T verdi = iterator.next();
                if (c.compare(verdi,minverdi) < 0)
                {
                    m = i; minverdi = verdi;
                }
            }
            liste.leggInn(liste.fjern(m));
        }
    }
} // class DobbeltLenketListe


