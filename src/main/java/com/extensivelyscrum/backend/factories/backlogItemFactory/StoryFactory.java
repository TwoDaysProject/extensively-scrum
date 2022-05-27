package com.extensivelyscrum.backend.factories.backlogItemFactory;

import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Story;

public class StoryFactory implements BacklogItemFactory{

    @Override
    public BacklogItem createBacklogItem() {
        return new Story();
    }
}
