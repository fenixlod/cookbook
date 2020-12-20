package com.lunix.cookbook.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OperationResult {
	private Object result;
	private HttpStatus status;

	private OperationResult(Object resultObject, HttpStatus resultStatus) {
		this.result = resultObject;
		this.status = resultStatus;
	}

	public ResponseEntity<?> get() {
		return new ResponseEntity<Object>(result, status);
	}

	static public OperationResult ok(String resultMessage) {
		return ok(new ResponseMessage(resultMessage));
	}

	static public OperationResult ok(Object resultObject) {
		return new OperationResult(resultObject, HttpStatus.OK);
	}

	static public OperationResult invalid(String resultMessage) {
		return invalid(new ResponseMessage(resultMessage));
	}

	static public OperationResult invalid(Object resultObject) {
		return new OperationResult(resultObject, HttpStatus.BAD_REQUEST);
	}

	static public OperationResult error(String resultMessage) {
		return error(new ResponseMessage(resultMessage));
	}

	static public OperationResult error(Object resultObject) {
		return new OperationResult(resultObject, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	static public OperationResult accepted() {
		return new OperationResult(null, HttpStatus.ACCEPTED);
	}

	static public OperationResult created(Object resultObject) {
		return new OperationResult(resultObject, HttpStatus.CREATED);
	}

	static public OperationResult notFound(String resultMessage) {
		return new OperationResult(new ResponseMessage(resultMessage), HttpStatus.NOT_FOUND);
	}
}
