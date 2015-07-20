package com.cai310.orm.hibernate;

import org.hibernate.Session;

public interface ExecuteCallBack {

	Object execute(Session session);
}
