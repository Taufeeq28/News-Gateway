package com.project.newsaggregator;

import java.io.Serializable;

public class Article implements Serializable {
    private String articleAuthor;
    private String articleTitle;
    private String articleDescription;
    private String articleUrl;
    private String articleUrltoimage;
    private String articlePublishedat;

    public Article() {
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getArticleUrltoimage() {
        return articleUrltoimage;
    }

    public void setArticleUrltoimage(String articleUrltoimage) {
        this.articleUrltoimage = articleUrltoimage;
    }

    public String getArticlePublishedat() {
        return articlePublishedat;
    }

    public void setArticlePublishedat(String articlePublishedat) {
        this.articlePublishedat = articlePublishedat;
    }
}

