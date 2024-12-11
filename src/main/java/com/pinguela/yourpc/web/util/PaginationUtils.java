package com.pinguela.yourpc.web.util;


import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.util.Strings;

import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.web.constants.Attributes;
import com.pinguela.yourpc.web.constants.Parameters;

public class PaginationUtils {
	
	public static final int FIRST_PAGE_INDEX = 1;
	public static final int DISPLAYED_PAGE_RANGE = 2;
	public static final int DEFAULT_PAGE_SIZE = 5;
	
	public static final String FIRST_PAGE = "firstPage";
	public static final String PREVIOUS_PAGE = "previousPage";
	public static final String DISPLAYED_PAGES = "displayedPages";
	public static final String NEXT_PAGE = "nextPage";
	public static final String LAST_PAGE = "lastPage";
	
	public static void buildPaginationUrls(HttpServletRequest request) {
		
		String pageStr = request.getParameter(Parameters.PAGE);
		Integer currentPage = Strings.isBlank(pageStr) ? FIRST_PAGE_INDEX : Integer.valueOf(pageStr);
		
		String baseUrl = URLBuilder.getParameterizedUrl(request, Parameters.PAGE);
		int resultCount = ((Results<?>) request.getAttribute(Attributes.RESULTS)).getResultCount();
		
		int pageSize = DEFAULT_PAGE_SIZE;
		int lastPage = resultCount / pageSize + Math.min(1, resultCount % pageSize);
		
		request.setAttribute(FIRST_PAGE, getPageUrl(baseUrl, currentPage, FIRST_PAGE_INDEX));
		request.setAttribute(PREVIOUS_PAGE, getPageUrl(baseUrl, currentPage, Math.max(FIRST_PAGE_INDEX, currentPage -1)));
		request.setAttribute(NEXT_PAGE, getPageUrl(baseUrl, currentPage, Math.min(lastPage, currentPage +1)));
		request.setAttribute(LAST_PAGE, getPageUrl(baseUrl, currentPage, lastPage));
		
		Map<Integer, String> displayedPageLinks = new TreeMap<>();
		
		for (int i = Math.max(FIRST_PAGE_INDEX, currentPage - DISPLAYED_PAGE_RANGE);
				i <= Math.min(lastPage, currentPage + DISPLAYED_PAGE_RANGE); i++) {
			displayedPageLinks.put(i, getPageUrl(baseUrl, currentPage, i));
		}
		
		request.setAttribute(DISPLAYED_PAGES, displayedPageLinks);
	}
	
	private static String getPageUrl(String baseUrl, int currentPage, int page) {
		return currentPage == page ? "#" : URLBuilder.appendParameter(baseUrl, Parameters.PAGE, String.valueOf(page));
	}

}
