package com.pelagusit.store.web.rest.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Sort;

public class RequestProcessor {
	
	public static Sort sorting(HttpServletRequest request) {
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			if (key.startsWith("sorting")) {
				String field = key.substring(key.indexOf("[") + 1,
						key.indexOf("]"));
				String direction = request.getParameter(key);
				Sort sort = new Sort(Sort.Direction.fromString(direction),
						field);
				return sort;
			}
		}
		return new Sort("id");
	}

}
