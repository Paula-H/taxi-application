package com.example.taximetrie.repository;

import com.example.taximetrie.domain.Sofer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class SoferRepository implements Repository<Long, Sofer> {
    String url;
    String username;
    String password;
    Connection connection;

    public SoferRepository(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Optional<Sofer> findOne(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sofer WHERE id = ?");
        statement.setInt(1, Math.toIntExact(id));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String username = resultSet.getString("username");
            String nume = resultSet.getString("nume");
            String indicativMasina = resultSet.getString("indicativ_masina");
            Sofer sofer = new Sofer(username, nume, indicativMasina);
            sofer.setId(id);
            return Optional.of(sofer);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Sofer> findAll() throws SQLException {
        ArrayList<Sofer> soferi = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sofer");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String nume = resultSet.getString("nume");
            String indicativMasina = resultSet.getString("indicativ_masina");
            Sofer sofer = new Sofer(username, nume, indicativMasina);
            sofer.setId(id);
            soferi.add(sofer);
        }
        return soferi;
    }

    @Override
    public Optional<Sofer> save(Sofer entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }
        String insertSQL = "INSERT INTO sofer(username, nume, indicativ_masina) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getNume());
        statement.setString(3, entity.getIndicativMasina());
        int answer = statement.executeUpdate();

        if (answer == 0) {
            return Optional.empty();
        }
        return Optional.of(entity);
    }

    @Override
    public Optional<Sofer> delete(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String deleteSQL = "DELETE FROM sofer WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setLong(1, id);

        Optional<Sofer> foundSofer = findOne(id);
        int answer = 0;

        if (foundSofer.isPresent()) {
            answer = statement.executeUpdate();
        }

        return answer == 0 ? foundSofer : Optional.empty();
    }

    @Override
    public Optional<Sofer> update(Sofer entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }

        String updateSQL = "UPDATE sofer SET username=?, nume=?, indicativ_masina=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(updateSQL);
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getNume());
        statement.setString(3, entity.getIndicativMasina());
        statement.setLong(4, entity.getId());

        int answer = statement.executeUpdate();
        return answer == 0 ? Optional.empty() : Optional.of(entity);
    }
}
