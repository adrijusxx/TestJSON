package com.example.testrestapi;

class Book {
    int id;
    String title;
    String isbn;
    int price;
    String currencyCode;
    String author;
    String description;

    public Book(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public Book() {
    }

    public Book(int id, String title, String isbn, String description, int price, String currencyCode, String author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.currencyCode = currencyCode;
        this.author = author;
        this.description = description;
    }

    public Book(int id, String title, String isbn, int price, String currencyCode, String author) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.price = price;
        this.currencyCode = currencyCode;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }
}
