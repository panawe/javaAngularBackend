package com.softenza.training.dao;

import java.util.List; 

import javax.persistence.EntityManager; 
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository; 
import com.softenza.training.model.Reservation; 

@SuppressWarnings("unchecked")
@Repository
public class ReservationDaoImpl implements ReservationDao {
	private static Logger logger = Logger.getLogger(ReservationDaoImpl.class);

	@Autowired
	private EntityManager entityManager;

	public List<Reservation> getReservationsWithFeedback() {
		return entityManager.createQuery("SELECT r FROM Reservation r "
				+ "WHERE r.feedback is not null").getResultList();
	}

}
