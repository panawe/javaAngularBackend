package com.softenza.training.service;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.softenza.training.util.Constants;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

@Service(value="reportService")
public class ReportServiceImpl implements ReportService{

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	DataSource datasource;
	
	public String createReport(Long userId, Short role, String reportName) throws SQLException {
		try {

			Resource resource = resourceLoader.getResource("classpath:/reports/" + reportName + ".jasper");
			InputStream reportStream = resource.getInputStream();

			Map parameters = new HashMap();
			parameters.put("userId", userId);
			parameters.put("role", Integer.valueOf(role));
					
			parameters.put("SUBREPORT_DIR", resourceLoader.getResource("classpath:/reports").getFile().getAbsolutePath()
					+ java.io.File.separator);
			// this is for some reports needing pictures.

			parameters.put("USER_IMAGE_DIR", Constants.PIC_FOLDER + java.io.File.separator + "assets"
					+ java.io.File.separator + "images" + java.io.File.separator + "user" + java.io.File.separator);

			parameters.put("REPORT_LOCALE", Constants.LOCALE);

			OutputStream ouputStream = new ByteArrayOutputStream();
			
			Connection conn = this.datasource.getConnection();

			byte[] reportBytes = JasperRunManager.runReportToPdf(reportStream, parameters, conn); 		 

			String newReportName = reportName + "_" + System.currentTimeMillis() + ".pdf";

			FileOutputStream fileOuputStream = new FileOutputStream(Constants.REPORT_RESULT_FOLDER + newReportName);
			fileOuputStream.write(reportBytes);
			fileOuputStream.close();

			return newReportName;

		} catch (JRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
