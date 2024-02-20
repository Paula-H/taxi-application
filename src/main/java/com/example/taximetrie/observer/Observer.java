package com.example.taximetrie.observer;

import com.example.taximetrie.event.Event;
import java.sql.SQLException;

public interface Observer<E extends Event> {
    void update(E e) throws SQLException;
}