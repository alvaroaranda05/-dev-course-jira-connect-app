package com.deiser.jira.connect.model;

import java.util.List;

public class PaginatedEntity<T> {
    String self;
    String nextPage;
    int startAt;
    int maxResults;
    int total;
    boolean isLast;
    List<T> values;

    public PaginatedEntity() {
    }

    public PaginatedEntity(String self, String nextPage, int startAt, int maxResults, int total, boolean isLast, List<T> values) {
        this.self = self;
        this.nextPage = nextPage;
        this.startAt = startAt;
        this.maxResults = maxResults;
        this.total = total;
        this.isLast = isLast;
        this.values = values;
    }

    public String getSelf() {
        return self;
    }

    public String getNextPage() {
        return nextPage;
    }

    public int getStartAt() {
        return startAt;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public int getTotal() {
        return total;
    }

    public boolean isLast() {
        return isLast;
    }

    public List<T> getValues() {
        return values;
    }
}
