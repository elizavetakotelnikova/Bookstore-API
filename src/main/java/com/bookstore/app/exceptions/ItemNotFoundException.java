package com.bookstore.app.exceptions;

public class ItemNotFoundException extends Exception
{
    public ItemNotFoundException() {}

    public ItemNotFoundException(String message)
    {
        super(message);
    }
}
