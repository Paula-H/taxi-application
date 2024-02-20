package com.example.taximetrie.repository;


import com.example.taximetrie.domain.Persoana;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class PersoanaRepository implements Repository<Long, Persoana> {
    String url;
    String username;
    String password;
    Connection connection;

    public PersoanaRepository(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Optional<Persoana> findOne(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM persoana WHERE id = ?");
        statement.setInt(1, Math.toIntExact(id));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String username = resultSet.getString("username");
            String nume = resultSet.getString("nume");
            Persoana persoana = new Persoana(username, nume);
            persoana.setId(id);
            return Optional.of(persoana);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Persoana> findAll() throws SQLException {
        ArrayList<Persoana> persoane = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM persoana");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String nume = resultSet.getString("nume");
            Persoana persoana = new Persoana(username, nume);
            persoana.setId(id);
            persoane.add(persoana);
        }
        return persoane;
    }

    @Override
    public Optional<Persoana> save(Persoana entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        String insertSQL = "INSERT INTO persoana(username, nume) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getNume());
        int answer = statement.executeUpdate();

        if (answer == 0) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Persoana> delete(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String deleteSQL = "DELETE FROM persoana WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setLong(1, id);

        Optional<Persoana> foundPersoana = findOne(id);
        int answer = 0;

        if (foundPersoana.isPresent()) {
            answer = statement.executeUpdate();
        }

        return answer == 0 ? foundPersoana : Optional.empty();
    }

    @Override
    public Optional<Persoana> update(Persoana entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }

        String updateSQL = "UPDATE persoana SET username=?, nume=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(updateSQL);
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getNume());
        statement.setLong(3, entity.getId());

        int answer = statement.executeUpdate();
        return answer == 0 ? Optional.empty() : Optional.of(entity);
    }
}
