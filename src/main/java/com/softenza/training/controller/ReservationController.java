package com.softenza.training.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.softenza.training.domain.BasicResponse;
import com.softenza.training.model.BaseEntity;
import com.softenza.training.model.Configuration;
import com.softenza.training.model.Reservation;
import com.softenza.training.model.User;
import com.softenza.training.service.GenericService;
import com.softenza.training.service.ReportService;
import com.softenza.training.service.UserService;
import com.softenza.training.util.Constants;
import com.softenza.training.util.SimpleMail;


@RestController
@RequestMapping(value="/service/reservation")
@CrossOrigin
public class ReservationController {
 
	@Autowired 
	@Qualifier("userService")
	UserService userService;
	
	@Autowired 
	@Qualifier("genericService")
	GenericService genericService;
	
	@Autowired
	@Qualifier("reportService")
	ReportService reportService;
	
  
    @RequestMapping(value="/getUserReservations/{userId}", method = RequestMethod.GET)
    public List<Reservation> getUserReservations(@PathVariable Long userId) {
  
        return this.userService.getReservations(userId);
    }
    
    @RequestMapping(value="/getAllReservations", method = RequestMethod.GET)
    public List<BaseEntity> getAllReservations() {
  
        return this.genericService.getAll(Reservation.class);
    }
    
    @RequestMapping(value="/reserver", method = RequestMethod.POST)
    public BaseEntity reserver(@RequestBody Reservation reservation) {
    	BaseEntity savedReservation = this.genericService.save(reservation);
    	
		return savedReservation;
	}
    
    @RequestMapping(value="/review", method = RequestMethod.POST)
    public BaseEntity review(@RequestBody Reservation reservation) {
    	BaseEntity savedReservation = this.genericService.save(reservation);
    	
		return savedReservation;
	}
    
    @RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
    public BasicResponse delete(@PathVariable Long id) {
    	
    	String result = Constants.SUCCESS;
    	
    	try {
    		this.genericService.delete(Reservation.class, id);
    	}
    	catch(Exception e) {
    		result = e.getMessage();
    	}
    	
    	return new BasicResponse(result);
    }
    
    @RequestMapping(value="/confirmer", method = RequestMethod.POST)
    public BasicResponse confirmer(@RequestBody Reservation reservation) {
    	
    	String result = Constants.SUCCESS;
    	
    	try {
    		this.genericService.save(reservation);
    	}
    	catch(Exception e) {
    		result = e.getMessage();
    	}
    	
    	return new BasicResponse(result);
    	
	}
  
    @RequestMapping(value="/terminer", method = RequestMethod.POST)
    public BasicResponse terminer(@RequestBody Reservation reservation) {
    	
    	String result = Constants.SUCCESS;
    	
    	try {
    		this.genericService.save(reservation);
    	}
    	catch(Exception e) {
    		result = e.getMessage();
    	}
    	
    	return new BasicResponse(result);
    	
	}
    
    @RequestMapping(value="/getUnitCost", method = RequestMethod.GET)
    public Double getUnitCost(@PathVariable Long id) {
    	
    	Double result = null;
    	
    	try {
    		result = this.genericService.findByColumn(Configuration.class, "NAME", "UNIT_PRICE");
    	}
    	catch(Exception e) {
    		result = null;
    	}
    	
    	return result;
    }
    
    
    @RequestMapping(value = "/printReservations", method = RequestMethod.POST)
	public BasicResponse printReport(@RequestBody User user) throws SQLException {
		String reportName = this.reportService.createReport(user.getId(), user.getRole(), "reservations");
		return new BasicResponse(reportName);

	}
    
    @RequestMapping(value = "/sendPassword", method = RequestMethod.POST)
	public @ResponseBody String sendPassword(@PathVariable("entity") String entity, @RequestBody User user) {

		if (user == null || (user.getEmail() == null && user.getEmail() == null)) {
			return "Failure";
		}

		User storedUser = this.userService.getUser(user.getEmail(), null);

		if (storedUser == null) {
			return "Failure";
		}

		try {

			String mail = "<blockquote><h2><b>Bonjour "
					+ (storedUser.getSex() != null && storedUser.getSex().equals("M") ? "Madame" : "Monsieur")
					+ "</b></h2><h2>Votre Mot de passe est:" + storedUser.getPassword()
					+ "  </h2><h2>Veuillez le garder secret en supprimant cet e-mail.</h2><h2>Encore une fois, merci de votre interet en notre organisation.</h2><h2><b>Le Directeur.</b></h2></blockquote>";
			SimpleMail.sendMail("Votre Mot de passe sur le site de ",
					mail, "ericgbekou@gmail.com", "ericgbekou@hotmail.com",
					"smtp.gmail.com", "softenzainc@gmail.com",
					"softenza123", false);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Failure";
		}

		return "Success";
	}
}