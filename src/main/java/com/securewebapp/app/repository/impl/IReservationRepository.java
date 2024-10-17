package com.securewebapp.app.repository.impl;

import java.util.HashMap;
import java.util.List;

/**
 * Interface for managing reservation-related operations.
 * This interface defines the methods for retrieving, adding, and deleting reservations.
 */
public interface IReservationRepository {

    /**
     * Retrieves a list of reservations for a specific user.
     *
     * @param userId The ID of the user whose reservations are to be retrieved.
     * @return A list of HashMaps, each containing reservation details for the user.
     */
    List<HashMap<String, Object>> getReservationsDetails(String userId);

    /**
     * Retrieves detailed information for a specific reservation for a given user.
     *
     * @param userId The ID of the user.
     * @param bookingId The ID of the booking to retrieve.
     * @return A HashMap containing detailed information about the reservation, or null if not found.
     */
    HashMap<String, Object> getReservationDetails(String userId, String bookingId);

    /**
     * Adds a new reservation to the system.
     *
     * @param reservationData A HashMap containing the reservation data to be added.
     * @return true if the reservation is added successfully, false otherwise.
     */
    boolean addReservationDetails(HashMap<String, String> reservationData);

    /**
     * Deletes a reservation based on the booking ID for a given user.
     *
     * @param bookingId The ID of the booking to delete.
     * @param userId The ID of the user requesting the deletion.
     * @return true if the deletion was successful, false otherwise.
     */
    boolean deleteReservationDetailsById(String bookingId, String userId);
}
