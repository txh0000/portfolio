package com.example.fit2081_week4.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "Book_ID")
    String Book_ID;
    @ColumnInfo(name = "Book_Title")
    String Book_Title;
    @ColumnInfo(name = "ISBN")
    String ISBN;
    @ColumnInfo(name = "Author")
    String Author;
    @ColumnInfo(name = "Description")
    String Description;
    @ColumnInfo(name = "Price")
    String Price;
    String Position;

    public Item(String Book_ID, String Book_Title, String ISBN, String Author, String Description, String Price) {
        this.Book_ID = Book_ID;
        this.Book_Title = Book_Title;
        this.ISBN = ISBN;
        this.Author = Author;
        this.Description = Description;
        this.Price = Price;
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
