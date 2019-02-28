package it.gdgtorino.hashcode.model;

import java.util.List;

public class Photo {
    private Boolean vertical;

    private Boolean horizontal;

    private List<String> tags;

    public Photo(Boolean vertical, Boolean horizontal, List<String> tags) {
        this.vertical = vertical;
        this.horizontal = horizontal;
        this.tags = tags;
    }

    public Boolean isVertical() {
        return vertical;
    }

    public void setVertical(Boolean vertical) {
        this.vertical = vertical;
    }

    public Boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(Boolean horizontal) {
        this.horizontal = horizontal;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
