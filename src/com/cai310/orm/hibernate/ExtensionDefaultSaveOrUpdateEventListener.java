package com.cai310.orm.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.event.SaveOrUpdateEvent;
import org.hibernate.event.def.DefaultSaveOrUpdateEventListener;

/**
 * 扩展Hibernate的DefaultSaveOrUpdateEventListener.
 * 
 */
public class ExtensionDefaultSaveOrUpdateEventListener extends DefaultSaveOrUpdateEventListener {
	private static final long serialVersionUID = 9188865759470519636L;

	private List<ExtensionSaveOrUpdateEventListener> extensionListenerList;

	public void setExtensionListenerList(List<ExtensionSaveOrUpdateEventListener> extensionListenerList) {
		this.extensionListenerList = extensionListenerList;
	}

	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		if (extensionListenerList != null && !extensionListenerList.isEmpty()) {
			List<ExtensionSaveOrUpdateCallBack> callBackList = new ArrayList<ExtensionSaveOrUpdateCallBack>();
			for (ExtensionSaveOrUpdateEventListener listener : extensionListenerList) {
				ExtensionSaveOrUpdateCallBack cb = listener.preSaveOrUpdate(event);
				if (cb != null)
					callBackList.add(cb);
			}

			super.onSaveOrUpdate(event);

			for (ExtensionSaveOrUpdateCallBack cb : callBackList) {
				cb.run(event);
			}
		} else {
			super.onSaveOrUpdate(event);
		}
	}
}
