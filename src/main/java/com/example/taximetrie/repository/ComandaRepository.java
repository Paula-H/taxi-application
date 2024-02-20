package com.example.taximetrie.repository;

import com.example.taximetrie.domain.Comanda;
import com.example.taximetrie.domain.Persoana;
import com.example.taximetrie.domain.Sofer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class ComandaRepository implements Repository<Long, Comanda> {
    String url;
    String username;
    String password;
    Connection connection;

    public ComandaRepository(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    private Optional<Persoana> findPersoana(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM persoana WHERE id = ?");
        statement.setLong(1, id);
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

    private Optional<Sofer> findSofer(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM sofer WHERE id = ?");
        statement.setLong(1, id);
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
    public Optional<Comanda> findOne(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comanda WHERE id = ?");
        statement.setLong(1, id);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Timestamp data = resultSet.getTimestamp("data");
            String locatie = resultSet.getString("locatie");
            Persoana persoana = findPersoana(resultSet.getLong("persoana_id")).get();
            Sofer sofer = findSofer(resultSet.getLong("sofer_id")).get();
            Comanda comanda = new Comanda(persoana, sofer, data.toLocalDateTime(), locatie);
            comanda.setId(id);
            return Optional.of(comanda);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Comanda> findAll() throws SQLException {
        ArrayList<Comanda> comenzi = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM comanda");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Timestamp data = resultSet.getTimestamp("data");
            String locatie = resultSet.getString("locatie");
            Persoana persoana = findPersoana(resultSet.getLong("persoana_id")).get();
            Sofer sofer = findSofer(resultSet.getLong("sofer_id")).get();
            Comanda comanda = new Comanda(persoana, sofer, data.toLocalDateTime(), locatie);
            comanda.setId(id);
            comenzi.add(comanda);
        }
        return comenzi;
    }

    @Override
    public Optional<Comanda> save(Comanda entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }

        // On conflict do nothing
        String insertSQL = "INSERT INTO comanda(persoana_id, sofer_id, data, locatie) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        statement.setLong(1, entity.getPersoana().getId());
        statement.setLong(2, entity.getSofer().getId());
        statement.setTimestamp(3, Timestamp.valueOf(entity.getData()));
        statement.setString(4, entity.getLocatie());
        int answer = statement.executeUpdate();

        if (answer == 0) {
            return Optional.empty();
        } else {
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                entity.setId(id);
                return Optional.of(entity);
            } else {
                return Optional.empty();
            }
        }
    }

    @Override
    public Optional<Comanda> delete(Long id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null");
        }

        String deleteSQL = "DELETE FROM comanda WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.setLong(1, id);

        Optional<Comanda> foundComanda = findOne(id);
        int answer = 0;

        if (foundComanda.isPresent()) {
            answer = statement.executeUpdate();
        }

        return answer == 0 ? foundComanda : Optional.empty();
    }

    @Override
    public Optional<Comanda> update(Comanda entity) throws SQLException {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null!");
        }

        // Update both timestamp and locatie
        String updateSQL = "UPDATE comanda SET data=?, locatie=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(updateSQL);
        statement.setTimestamp(1, Timestamp.valueOf(entity.getData()));
        statement.setString(2, entity.getLocatie());
        statement.setLong(3, entity.getId());

        int answer = statement.executeUpdate();
        return answer == 0 ? Optional.empty() : Optional.of(entity);
    }

}

