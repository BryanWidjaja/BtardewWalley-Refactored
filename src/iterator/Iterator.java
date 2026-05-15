package iterator;

public interface Iterator<T> {
    boolean hasNext();
    T getNext();
    void remove();
}
