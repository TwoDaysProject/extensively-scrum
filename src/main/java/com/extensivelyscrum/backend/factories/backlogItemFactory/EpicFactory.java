package com.extensivelyscrum.backend.factories.backlogItemFactory;

import com.extensivelyscrum.backend.model.BacklogItem;
import com.extensivelyscrum.backend.model.Epic;

public class EpicFactory implements BacklogItemFactory {

    @Override
    public BacklogItem createBacklogItem() {
        return new Epic();
    }
}
