package org.uk.ubs.limitless.domain;

import java.util.Objects;

public class Items {
    private String title;
    private String pubDate;
    private String link;
    private String guid;
    private String author;
    private String thumbnail;
    private String description;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Items{" +
                "title='" + title + '\'' +
                ", pubDate='" + pubDate + '\'' +
                ", link='" + link + '\'' +
                ", guid='" + guid + '\'' +
                ", author='" + author + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Items)) return false;
        Items items = (Items) o;
        return Objects.equals(getTitle(), items.getTitle()) &&
                Objects.equals(getPubDate(), items.getPubDate()) &&
                Objects.equals(getLink(), items.getLink()) &&
                Objects.equals(getGuid(), items.getGuid()) &&
                Objects.equals(getAuthor(), items.getAuthor()) &&
                Objects.equals(getThumbnail(), items.getThumbnail()) &&
                Objects.equals(getDescription(), items.getDescription()) &&
                Objects.equals(getContent(), items.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getPubDate(), getLink(), getGuid(), getAuthor(), getThumbnail(), getDescription(), getContent());
    }
}
