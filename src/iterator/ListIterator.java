package iterator;

import java.util.List;
import java.util.NoSuchElementException;

public class ListIterator<T> implements Iterator<T> {
    private List<T> list;
    private int position = 0;

    public ListIterator(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return position < list.size();
    }

    @Override
    public T getNext() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(position++);
    }

    @Override
    public void remove() {
        if (position <= 0) {
            throw new IllegalStateException();
        }
        list.remove(--position);
    }
}
