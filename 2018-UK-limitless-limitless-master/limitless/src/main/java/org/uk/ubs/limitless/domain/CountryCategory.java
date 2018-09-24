package org.uk.ubs.limitless.domain;

import java.util.Objects;

public class CountryCategory {
    private String country;
    private String items;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountryCategory)) return false;
        CountryCategory that = (CountryCategory) o;
        return Objects.equals(getCountry(), that.getCountry()) &&
                Objects.equals(getItems(), that.getItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountry(), getItems());
    }
}
