package com.example.taximetrie.repository;

import java.sql.SQLException;

public class RepositoryFactory {
    public Repository createRepository(RepositoryType repositoryType) throws SQLException {
        switch (repositoryType) {
            case Persoana -> {
                return new PersoanaRepository("jdbc:postgresql://localhost:5432/taximetrie", "postgres", "1");
            }

            case Sofer -> {
                return new SoferRepository("jdbc:postgresql://localhost:5432/taximetrie", "postgres", "1");
            }

            case Comanda -> {
                return new ComandaRepository("jdbc:postgresql://localhost:5432/taximetrie", "postgres", "1");
            }

            default -> {
                return null;
            }
        }
    }
}
