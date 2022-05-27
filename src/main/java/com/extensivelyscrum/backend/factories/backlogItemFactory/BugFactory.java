package com.extensivelyscrum.backend.factories.backlogItemFactory;

import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Bug;

public class BugFactory implements BacklogItemFactory{

    @Override
    public BacklogItem createBacklogItem() {
        return new Bug();
    }
}
