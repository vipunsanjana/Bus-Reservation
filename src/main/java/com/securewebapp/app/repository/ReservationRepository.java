package com.securewebapp.app.repository;

import com.securewebapp.app.connection.MySqlConn;
import com.securewebapp.app.repository.impl.IReservationRepository;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationRepository implements IReservationRepository {
    // Logger for the ReservationRepository class
    private static final Logger logger = Logger.getLogger(ReservationRepository.class.getName());

    /**
     * Retrieves reservation details for a specific user.
     *
     * @param userId The ID of the user whose reservations are to be retrieved.
     * @return A list of reservation details in a HashMap, or null if an error occurs.
     */
    public List<HashMap<String, Object>> getReservationsDetails(String userId) {
        try {
            Connection conn = MySqlConn.connect(); // Establish connection to the database

            if (conn != null) {
                // SQL query to select booking details for the given user
                String sql = "SELECT booking_id, date, time, location FROM vehicle_service WHERE username=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, userId); // Set user ID in the query
                ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

                List<HashMap<String, Object>> reservationDataList = new ArrayList<>(); // List to hold reservation data
                while (resultSet.next()) {
                    HashMap<String, Object> reservationData = new HashMap<>(); // Create a map for each reservation
                    // Populate the map with reservation details
                    reservationData.put("bookingId", resultSet.getInt("booking_id"));
                    reservationData.put("date", resultSet.getDate("date"));
                    reservationData.put("time", resultSet.getTime("time").toLocalTime()
                            .format(java.time.format.DateTimeFormatter.ofPattern("hh a")));
                    reservationData.put("location", resultSet.getString("location"));
                    reservationDataList.add(reservationData); // Add the map to the list
                }
                preparedStatement.close(); // Close the prepared statement
                conn.close(); // Close the database connection

                return reservationDataList; // Return the list of reservation details
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any SQL exceptions
        }

        return null; // Return null if an error occurs
    }

    /**
     * Retrieves details for a specific reservation for a given user.
     *
     * @param userId The ID of the user.
     * @param bookingId The ID of the booking to retrieve.
     * @return A HashMap containing reservation details, or null if an error occurs.
     */
    @Override
    public HashMap<String, Object> getReservationDetails(String userId, String bookingId) {
        try {
            Connection conn = MySqlConn.connect(); // Establish connection to the database

            if (conn != null) {
                // SQL query to select reservation details for the given user and booking ID
                String sql = "SELECT booking_id, date, time, location, vehicle_no, mileage, message " +
                        "FROM vehicle_service WHERE username=? AND booking_id=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, userId); // Set user ID in the query
                preparedStatement.setString(2, bookingId); // Set booking ID in the query
                ResultSet resultSet = preparedStatement.executeQuery(); // Execute the query

                HashMap<String, Object> reservationData = new HashMap<>(); // Map to hold reservation details
                while (resultSet.next()) {
                    // Populate the map with reservation details
                    reservationData.put("bookingId", resultSet.getInt("booking_id"));
                    reservationData.put("date", resultSet.getDate("date"));
                    reservationData.put("time", resultSet.getTime("time").toLocalTime()
                            .format(java.time.format.DateTimeFormatter.ofPattern("hh a")));
                    reservationData.put("location", resultSet.getString("location"));
                    reservationData.put("vehicleNo", resultSet.getString("vehicle_no"));
                    reservationData.put("mileage", resultSet.getInt("mileage"));
                    reservationData.put("message", resultSet.getString("message"));
                }
                preparedStatement.close(); // Close the prepared statement
                conn.close(); // Close the database connection

                return reservationData; // Return the map of reservation details
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any SQL exceptions
        }

        return null; // Return null if an error occurs
    }

    /**
     * Adds a new reservation to the database.
     *
     * @param reservationData A HashMap containing reservation data to be added.
     * @return true if the reservation is added successfully, false otherwise.
     */
    public boolean addReservationDetails(HashMap<String, String> reservationData) {
        try {
            Connection conn = MySqlConn.connect(); // Establish connection to the database

            if (conn != null) {
                // SQL query to insert a new reservation
                String sql = "INSERT INTO vehicle_service(" +
                        "date, time, location, vehicle_no, mileage, message, username) VALUES(?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                // Parse and set reservation time
                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH");
                Date formattedTime = simpleTimeFormat.parse(reservationData.get("reservationTime")); // Parse the time
                Time reservationTime = new Time(formattedTime.getTime()); // Convert to SQL Time

                // Parse mileage from reservation data
                int reservationMileage = Integer.parseInt(reservationData.get("reservationMileage"));

                // Set parameters for the SQL query
                preparedStatement.setString(1, reservationData.get("reservationDate"));
                preparedStatement.setTime(2, reservationTime);
                preparedStatement.setString(3, reservationData.get("reservationLocation"));
                preparedStatement.setString(4, reservationData.get("reservationVehicleNo"));
                preparedStatement.setInt(5, reservationMileage);
                preparedStatement.setString(6, reservationData.get("reservationMessage"));
                preparedStatement.setString(7, reservationData.get("userName"));

                int rowsInserted = preparedStatement.executeUpdate(); // Execute the insertion
                boolean response = rowsInserted > 0; // Check if the insertion was successful
                preparedStatement.close(); // Close the prepared statement
                conn.close(); // Close the database connection

                return response; // Return the result of the insertion
            }
        } catch (SQLException | ParseException ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any exceptions
        }

        return false; // Return false if an error occurs
    }

    /**
     * Deletes a reservation based on booking ID for a given user.
     *
     * @param bookingId The ID of the booking to delete.
     * @param userId The ID of the user requesting the deletion.
     * @return true if the deletion was successful, false otherwise.
     */
    public boolean deleteReservationDetailsById(String bookingId, String userId) {
        try {
            Connection conn = MySqlConn.connect(); // Establish connection to the database

            if (conn != null) {
                // SQL query to delete a reservation based on user ID and booking ID
                String sql = "DELETE FROM vehicle_service WHERE username=? AND booking_id=?";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, userId); // Set user ID in the query
                preparedStatement.setString(2, bookingId); // Set booking ID in the query
                preparedStatement.executeUpdate(); // Execute the deletion
                preparedStatement.close(); // Close the prepared statement
                conn.close(); // Close the database connection

                return true; // Return true if the deletion was successful
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An error occurred: " + ex.getMessage(), ex); // Log any SQL exceptions
        }

        return false; // Return false if an error occurs
    }
}
