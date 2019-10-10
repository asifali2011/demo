package com.scp.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.scp.bean.RestStructureBean;
import com.scp.sessionauth.entities.SessionAuth;
import com.scp.util.ApiResponse;
import com.scp.util.CommonUtil;
import com.scp.util.Logger4j;
import com.scp.util.Utilities;

@RestController
@RequestMapping(value = "/")
public class RestStructureController {

	private Utilities utilities;

	@Autowired
	public RestStructureController(Utilities utilities) {
		this.utilities = utilities;
	}

	@RequestMapping(value = "rest", method = RequestMethod.POST)
	public ResponseEntity<?> syncData(@RequestBody @Valid RestStructureBean restStructureBean,
			HttpServletResponse response) {
		ApiResponse apiResponse = new ApiResponse();

		try {

			// verify session authentication if condition is true then go forward for next
			SessionAuth sessionAuth = utilities.checkUserSessionExistOrNot(restStructureBean.getAuth());
			if (sessionAuth == null) {
				apiResponse.setCode(HttpStatus.FORBIDDEN.value());
				apiResponse.setMessage(HttpStatus.FORBIDDEN.getReasonPhrase());
				return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.FORBIDDEN);
			}
			
			//continure your work here...

			apiResponse.setCode(HttpStatus.NOT_FOUND.value());
			apiResponse.setMessage("Record not found");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			Logger4j.getLogger().error("Exception : ", e);
			apiResponse.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			String message = "Somethingwent wrong  => " + CommonUtil.getRootCause(e).getMessage();
			apiResponse.setMessage(message);

			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
