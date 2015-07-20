package com.cai310.orm.hibernate;

import org.hibernate.event.SaveOrUpdateEvent;

public interface ExtensionSaveOrUpdateEventListener {

	ExtensionSaveOrUpdateCallBack preSaveOrUpdate(SaveOrUpdateEvent event);
}
