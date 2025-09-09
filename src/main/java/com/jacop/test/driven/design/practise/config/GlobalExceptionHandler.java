package com.jacop.test.driven.design.practise.config;

import com.jacop.test.driven.design.practise.newGen.model.exceptions.InsufficientStockException;
import com.jacop.test.driven.design.practise.newGen.model.exceptions.UnknownProductTypeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnknownProductTypeException.class)
    public ResponseEntity<ProblemDetail> handleException(UnknownProductTypeException e, HttpServletRequest req) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        problemDetail.setTitle("Unknown Product Type");
        problemDetail.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ProblemDetail> handleException(InsufficientStockException e, HttpServletRequest req) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setTitle("Insufficient Stock");
        problemDetail.setProperty("path", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

}
