package com.softenza.training.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.softenza.training.model.BaseEntity;
import com.softenza.training.model.Reservation;
import com.softenza.training.model.User;


@Service(value="reservationService")
public interface ReservationService {	
	public List<Reservation> getReservationsWithFeedback();
}
