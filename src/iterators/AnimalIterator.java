package iterators;

import java.util.List;
import models.Animal;

public class AnimalIterator implements Iterator<Animal> {
    private List<Animal> animals;
    private int position = 0;

    public AnimalIterator(List<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public boolean hasNext() {
        return position < animals.size();
    }

    @Override
    public Animal getNext() {
        if (!hasNext()) {
            return null;
        }
        return animals.get(position++);
    }

    @Override
    public void remove() {
        if (position <= 0) {
            return;
        }
        animals.remove(--position);
    }
}
