package com.sangis.corejava.domain.infrastructure.persistence;

import com.sangis.corejava.domain.core.models.BaseMunicipality;
import com.sangis.corejava.domain.core.models.BaseMunicipalityPart;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public class PostgreMunicipalityRepository implements IMunicipalityRepository {
    //TODO : implement persistence
    //TODO : test persistence

    private final String url;
    private final String user;
    private final String password;
    private final boolean ssl;

    private static final String MUNICIPALITY_TABLE = "municipality";
    private static final String MUNICIPALITY_PART_TABLE = "municipality_part";

    private static final String CODE_COLUMN = "code";
    private static final String MUNICIPALITY_CODE_COLUMN = "municipality_code";
    private static final String NAME_COLUMN = "name";

    private static final String insertMunicipality =
            String.format("INSERT INTO %s (%s, %s) VALUES (?, ?);",
                    MUNICIPALITY_TABLE,
                    CODE_COLUMN,
                    NAME_COLUMN);
    private static final String insertMunicipalityPart =
            String.format("INSERT INTO %s (%s, %s, %s) " +
                            "VALUES (?, ?, ?);",
                    MUNICIPALITY_PART_TABLE,
                    CODE_COLUMN,
                    NAME_COLUMN,
                    MUNICIPALITY_CODE_COLUMN
            );
    private static final String selectAllMunicipalities =
            String.format("SELECT * FROM %s;",
                    MUNICIPALITY_TABLE
            );
    private static final String selectMunicipalityPartByMunicipalityCode =
            String.format("SELECT * FROM %s WHERE %s = ?;",
                    MUNICIPALITY_PART_TABLE,
                    MUNICIPALITY_CODE_COLUMN
            );


    public PostgreMunicipalityRepository(String url, String user, String password, boolean ssl) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.ssl = ssl;
    }

    private Connection getConnection() throws PersistenceException {
        Properties properties = new Properties();
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        properties.setProperty("ssl", Boolean.toString(ssl));

        try {
            Class.forName("org.postgresql.Driver");

            return DriverManager.getConnection(url, properties);
        } catch (SQLException e) {
            throw new PersistenceException(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new PersistenceException(e.getMessage());
        }
    }


    public BaseMunicipality saveMunicipality(BaseMunicipality municipality) throws PersistenceException {

        Connection connection = getConnection();
        PreparedStatement insertMunicipalitySmt = null;
        PreparedStatement insertPartSmt = null;
        try {
            connection.setAutoCommit(false);

            insertMunicipalitySmt = connection.prepareStatement(insertMunicipality);
            insertMunicipalitySmt.setInt(1, municipality.getCode());
            insertMunicipalitySmt.setString(2, municipality.getName());
            int affectedRows = insertMunicipalitySmt.executeUpdate();

            if (affectedRows > 0) {
                Iterator<BaseMunicipalityPart> partsIterator = municipality.getParts();
                insertPartSmt = connection.prepareStatement(insertMunicipalityPart);

                int counter = 0;
                while (partsIterator.hasNext()) {
                    BaseMunicipalityPart part = partsIterator.next();

                    insertPartSmt.setInt(1, part.getCode());
                    insertPartSmt.setString(2, part.getName());
                    insertPartSmt.setInt(3, part.getMunicipalityCode());

                    insertPartSmt.addBatch();
                    counter++;
                }
                if (counter > 0) {

                    int[] affected = insertPartSmt.executeBatch();
                    if (affected.length != counter) {
                        connection.rollback();
                    }
                }

            } else {
                connection.rollback();
            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException e) {

            try {
                if (insertMunicipalitySmt != null) insertMunicipalitySmt.close();
                if (insertPartSmt != null) insertPartSmt.close();
                connection.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

            throw new PersistenceException(e.getMessage());
        }


        return municipality;
    }

    public BaseMunicipalityPart saveMunicipalityPart(BaseMunicipalityPart municipalityPart) throws PersistenceException {
        Connection connection = null;
        PreparedStatement insertMunicipalityPartSmt = null;
        try {
            connection = getConnection();
            insertMunicipalityPartSmt = connection.prepareStatement(insertMunicipalityPart);

            insertMunicipalityPartSmt.setInt(1, municipalityPart.getCode());
            insertMunicipalityPartSmt.setString(2, municipalityPart.getName());
            insertMunicipalityPartSmt.setInt(3, municipalityPart.getMunicipalityCode());

            insertMunicipalityPartSmt.executeUpdate();

        } catch (SQLException sqlException) {

            try {
                if (insertMunicipalityPartSmt != null) insertMunicipalityPartSmt.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            throw new PersistenceException(sqlException.getMessage());
        }
        return municipalityPart;
    }

    public Iterable<BaseMunicipality> fetchMunicipalities() throws PersistenceException {

        Connection connection = getConnection();
        PreparedStatement selectMunicipalitiesSmt = null;
        try {
            selectMunicipalitiesSmt = connection.prepareStatement(selectAllMunicipalities);
            ResultSet rs = selectMunicipalitiesSmt.executeQuery();
            ArrayList<BaseMunicipality> results = new ArrayList<BaseMunicipality>();
            while (rs.next()) {
                BaseMunicipality municipality = parseMunicipality(rs);

                Iterable<BaseMunicipalityPart> children = fetchMunicipalityPartsByMunicipalityCode(municipality.getCode());
                municipality.addParts(children.iterator());

                results.add(municipality);
            }
            rs.close();
            return results;

        } catch (SQLException sqlException) {
            try {
                if (selectMunicipalitiesSmt != null) selectMunicipalitiesSmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            throw new PersistenceException(sqlException.getMessage());
        }
    }

    public Iterable<BaseMunicipalityPart> fetchMunicipalityPartsByMunicipalityCode(int municipalityCode) throws PersistenceException {
        Connection connection = getConnection();
        PreparedStatement selectMunicipalityPartsSmt = null;
        try {
            selectMunicipalityPartsSmt = connection.prepareStatement(selectMunicipalityPartByMunicipalityCode);
            selectMunicipalityPartsSmt.setInt(1, municipalityCode);

            ResultSet rs = selectMunicipalityPartsSmt.executeQuery();
            ArrayList<BaseMunicipalityPart> results = new ArrayList<BaseMunicipalityPart>();
            while (rs.next()) {
                BaseMunicipalityPart municipalityPart = parseMunicipalityPart(rs);
                results.add(municipalityPart);
            }
            rs.close();
            return results;

        } catch (SQLException sqlException) {
            try {
                if (selectMunicipalityPartsSmt != null) selectMunicipalityPartsSmt.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }


            throw new PersistenceException(sqlException.getMessage());
        }
    }


    private BaseMunicipality parseMunicipality(ResultSet rs) throws SQLException {
        return new BaseMunicipality(rs.getInt(CODE_COLUMN), rs.getString(NAME_COLUMN));
    }

    private BaseMunicipalityPart parseMunicipalityPart(ResultSet rs) throws SQLException {
        return new BaseMunicipalityPart(rs.getInt(CODE_COLUMN), rs.getInt(MUNICIPALITY_CODE_COLUMN), rs.getString(NAME_COLUMN));
    }


}
