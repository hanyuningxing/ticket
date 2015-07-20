package com.cai310.utils;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class SimpleResponseHandler implements ResponseHandler<String> {
	public static final String DEFAULT_ENCODE = "UTF-8";

	private String encode;

	public SimpleResponseHandler() {
		this(null);
	}

	public SimpleResponseHandler(String encode) {
		if (StringUtils.isNotBlank(encode))
			this.encode = encode;
		else
			this.encode = DEFAULT_ENCODE;

	}

	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		StatusLine statusLine = response.getStatusLine();
		if (statusLine.getStatusCode() >= 300) {
			throw new HttpResponseException(statusLine.getStatusCode(), statusLine.getReasonPhrase());
		}

		HttpEntity entity = response.getEntity();
		return entity == null ? null : EntityUtils.toString(entity, this.encode);
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}
}
