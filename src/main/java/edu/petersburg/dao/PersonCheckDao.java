package edu.petersburg.dao;

import edu.petersburg.domain.PersonRequest;
import edu.petersburg.domain.PersonResponse;
import edu.petersburg.exception.PersonCheckException;

import java.sql.*;

public class PersonCheckDao {

    private static final String SQL_REQUEST = "select temporal from city_register_address_person ap " +
            "join city_register_person p on p.person_id = ap.person_id " +
            "join city_register_address a on a.address_id = ap.address_id " +
            "where " +
            "current_date >= ap.start_date_of_registration and (CURRENT_DATE <= ap.end_date_of_registration is null) " +
            "and upper(p.sur_name) = upper(?) and upper(p.given_name) = upper(?) " +
            "and upper(p.patronymic) = upper(?) and p.date_of_birth = ? " +
            "and a.street_code = ? and upper(a.building) = upper(?) ";

    private ConnectionBuilder connectionBuilder;

    public void setConnectionBuilder(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    private Connection getConnection() throws SQLException {
        return connectionBuilder.getConnection();
    }

    public PersonResponse checkPerson(PersonRequest personRequest) throws PersonCheckException {
        PersonResponse personResponse = new PersonResponse();
        String sql = SQL_REQUEST;
        if (personRequest.getExtension() != null) {
            sql += "and upper(a.extension) = upper(?) ";
        } else {
            sql += "and extension is null ";
        }
        if (personRequest.getApartment() != null) {
            sql += "and upper(a.apartment) = upper(?) ";
        } else {
            sql += "and apartment is null ";
        }

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            int count = 1;
            statement.setString(count++, personRequest.getSurName());
            statement.setString(count++, personRequest.getGivenName());
            statement.setString(count++, personRequest.getPatronymic());
            statement.setDate(count++, java.sql.Date.valueOf(personRequest.getDateOfBirth()));
            statement.setInt(count++, personRequest.getStreetCode());
            statement.setString(count++, personRequest.getBuilding());
            if (personRequest.getExtension() != null) {
                statement.setString(count++, personRequest.getExtension());
            }
            if (personRequest.getApartment() != null) {
                statement.setString(count, personRequest.getApartment());
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                personResponse.setRegistered(true);
                personResponse.setTemporal(resultSet.getBoolean("temporal"));
            }

        } catch (SQLException ex) {
            throw new PersonCheckException(ex);
        }
        return personResponse;
    }
}
