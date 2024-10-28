package com.example.fit2081_week4;

public class Item {
    private String Book_ID;
    private String Book_Title;
    private String ISBN;
    private String Author;
    private String Description;
    private String Price;
    private String Position;

    public Item(String id, String title, String ISBN, String author, String description, String price, String position) {
        this.Book_ID = id;
        this.Book_Title = title;
        this.ISBN = ISBN;
        this.Author = author;
        this.Description = description;
        this.Price = price;
        this.Position = position;
    }

    public String getID() {
        return Book_ID;
    }

    public String getTitle() {
        return Book_Title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return Author;
    }

    public String getDescription() {
        return Description;
    }

    public String getPrice() {
        return Price;
    }
    public String getPosition() {
        return Position;
    }
}
