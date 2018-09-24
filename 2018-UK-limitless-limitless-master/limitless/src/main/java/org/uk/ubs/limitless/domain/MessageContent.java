package org.uk.ubs.limitless.domain;

import java.util.Objects;

public class MessageContent {

    private String symbol;
    private String timestamp;
    private String tradingDay;
    private String open;
    private String high;
    private String low;
    private String close;
    private String volume;
    private String openInterest;
    private String username;
    private String msg;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTradingDay() {
        return tradingDay;
    }

    public void setTradingDay(String tradingDay) {
        this.tradingDay = tradingDay;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getOpenInterest() {
        return openInterest;
    }

    public void setOpenInterest(String openInterest) {
        this.openInterest = openInterest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageContent)) return false;
        MessageContent that = (MessageContent) o;
        return Objects.equals(getSymbol(), that.getSymbol()) &&
                Objects.equals(getTimestamp(), that.getTimestamp()) &&
                Objects.equals(getTradingDay(), that.getTradingDay()) &&
                Objects.equals(getOpen(), that.getOpen()) &&
                Objects.equals(getHigh(), that.getHigh()) &&
                Objects.equals(getLow(), that.getLow()) &&
                Objects.equals(getClose(), that.getClose()) &&
                Objects.equals(getVolume(), that.getVolume()) &&
                Objects.equals(getOpenInterest(), that.getOpenInterest()) &&
                Objects.equals(getUsername(), that.getUsername()) &&
                Objects.equals(getMsg(), that.getMsg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSymbol(), getTimestamp(), getTradingDay(), getOpen(), getHigh(), getLow(), getClose(), getVolume(), getOpenInterest(), getUsername(), getMsg());
    }
}
