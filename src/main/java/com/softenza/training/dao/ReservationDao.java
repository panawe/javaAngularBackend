package com.softenza.training.dao;

import java.util.List;

import com.softenza.training.model.Reservation;
import com.softenza.training.model.User;

public interface ReservationDao {
	public List<Reservation> getReservationsWithFeedback();
}
