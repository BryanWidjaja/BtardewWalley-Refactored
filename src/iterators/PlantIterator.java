package iterators;

import java.util.List;
import models.Plant;

public class PlantIterator implements Iterator<Plant> {
    private List<Plant> plants;
    private int position = 0;

    public PlantIterator(List<Plant> plants) {
        this.plants = plants;
    }

    @Override
    public boolean hasNext() {
        return position < plants.size();
    }

    @Override
    public Plant getNext() {
        if (!hasNext()) {
            return null;
        }
        return plants.get(position++);
    }

    @Override
    public void remove() {
        if (position <= 0) {
            return;
        }
        plants.remove(--position);
    }
}
