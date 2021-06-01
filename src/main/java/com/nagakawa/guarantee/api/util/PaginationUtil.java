package com.nagakawa.guarantee.api.util;

import java.text.MessageFormat;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

import com.nagakawa.guarantee.util.StringPool;
import com.nagakawa.guarantee.util.URLEncodePool;

/**
 * Utility class for handling pagination.
 * <p>
 * Pagination uses the same principles as the <a href="https://developer.github.com/v3/#pagination">GitHub API</a>, and
 * follow <a href="http://tools.ietf.org/html/rfc5988">RFC 5988 (Link header)</a>.
 */
public final class PaginationUtil {

    private PaginationUtil() {
    }

    /**
     * Generate pagination headers for a Spring Data {@link Page} object.
     *
     * @param uriBuilder
     *            The URI builder.
     * @param page
     *            The page.
     * @param <T>
     *            The type of object.
     * @return http header.
     */
    public static <T> HttpHeaders generatePaginationHttpHeaders(UriComponentsBuilder uriBuilder, Page<T> page) {
        HttpHeaders headers = new HttpHeaders();

        headers.add(ApiConstants.HttpHeaders.X_TOTAL_COUNT, Long.toString(page.getTotalElements()));

        int pageNumber = page.getNumber();
        int pageSize = page.getSize();

        StringBuilder link = new StringBuilder();

        if (pageNumber < page.getTotalPages() - 1) {
            link.append(prepareLink(uriBuilder, pageNumber + 1, pageSize, ApiConstants.Pagination.NEXT))
                    .append(StringPool.COMMA);
        }

        if (pageNumber > 0) {
            link.append(prepareLink(uriBuilder, pageNumber - 1, pageSize, ApiConstants.Pagination.PREV))
                    .append(StringPool.COMMA);
        }

        link.append(prepareLink(uriBuilder, page.getTotalPages() - 1, pageSize, ApiConstants.Pagination.LAST))
                .append(StringPool.COMMA).append(prepareLink(uriBuilder, 0, pageSize, ApiConstants.Pagination.FIRST));

        headers.add(HttpHeaders.LINK, link.toString());

        return headers;
    }

    private static String prepareLink(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize, String relType) {
        return MessageFormat.format(ApiConstants.HttpHeaders.LINK_FORMAT,
                preparePageUri(uriBuilder, pageNumber, pageSize), relType);
    }

    private static String preparePageUri(UriComponentsBuilder uriBuilder, int pageNumber, int pageSize) {
        return uriBuilder.replaceQueryParam(ApiConstants.Pagination.PAGE, Integer.toString(pageNumber))
                .replaceQueryParam(ApiConstants.Pagination.SIZE, Integer.toString(pageSize)).toUriString()
                .replace(StringPool.COMMA, URLEncodePool.COMMA).replace(StringPool.SEMICOLON, URLEncodePool.SEMICOLON);
    }
}
