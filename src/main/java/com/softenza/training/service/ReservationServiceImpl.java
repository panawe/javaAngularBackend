package com.softenza.training.service;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.softenza.training.dao.ReservationDao; 
import com.softenza.training.model.Reservation; 


@Service(value="reservationService")
public class ReservationServiceImpl  implements ReservationService {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	ReservationDao reservationDao;
	
	public List<Reservation> getReservationsWithFeedback() {		
		return this.reservationDao.getReservationsWithFeedback();
	}
}
