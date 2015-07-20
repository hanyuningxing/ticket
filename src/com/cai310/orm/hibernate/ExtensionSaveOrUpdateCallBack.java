package com.cai310.orm.hibernate;

import org.hibernate.event.SaveOrUpdateEvent;

public interface ExtensionSaveOrUpdateCallBack {

	void run(SaveOrUpdateEvent event);

}
