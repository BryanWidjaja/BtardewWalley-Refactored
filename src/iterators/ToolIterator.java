package iterators;

import java.util.List;
import models.items.Tool;

public class ToolIterator implements Iterator<Tool> {
    private List<Tool> tools;
    private int position = 0;

    public ToolIterator(List<Tool> tools) {
        this.tools = tools;
    }

    @Override
    public boolean hasNext() {
        return position < tools.size();
    }

    @Override
    public Tool getNext() {
        if (!hasNext()) {
            return null;
        }
        return tools.get(position++);
    }

    @Override
    public void remove() {
        if (position <= 0) {
            return;
        }
        tools.remove(--position);
    }
}
