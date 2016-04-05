package com.catdog.util;

import org.json.JSONException;

/**
 * @author lxm
 */
public interface ResponseListener {
	public void getResponse(Object object) throws JSONException;
}
