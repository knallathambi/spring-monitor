package nk.commons.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import javax.management.MBeanServer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nk.commons.model.GenericResponse;
import nk.commons.model.ResponseException;


@RestController
@RequestMapping("/api")
public class AppRestController {

	@RequestMapping("/ping")
	public ResponseEntity<GenericResponse> welcome() {
		GenericResponse response = new GenericResponse();
		response.setResponseCode("00");
		response.setResponseMessage("Successful");
		return new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
	}
	
	@RequestMapping("/threadinfo")
	public ResponseEntity<ThreadInfo []> getThreadInfo() throws ResponseException{
		try {
			ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
			long[] threadIds = threadMXBean.getAllThreadIds();
			return new ResponseEntity<ThreadInfo[]>(threadMXBean.getThreadInfo(threadIds, true, true), HttpStatus.OK);
		} catch(Exception e){
			throw new ResponseException(e.getMessage());
		}
	}
	
	@ExceptionHandler(ResponseException.class)
	public ResponseEntity<GenericResponse> handleCustomException(ResponseException ex) {

		GenericResponse response = new GenericResponse();
		response.setResponseCode("99");
		response.setResponseMessage(ex.getMessage());
		return new ResponseEntity<GenericResponse>(response, HttpStatus.BAD_REQUEST);

	}
	

	
}
