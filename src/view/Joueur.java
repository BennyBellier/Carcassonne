package view;

public class Joueur<T,P,C>{
    
    private final T first;
    private final P second;
    private final C third;

    public Joueur(T first, P second, C third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public T getFirst() { return first; }
    public P getSecond() { return second; }
    public C getThird() { return third; }
}
