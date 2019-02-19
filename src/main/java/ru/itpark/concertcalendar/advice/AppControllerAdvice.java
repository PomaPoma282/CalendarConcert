package ru.itpark.concertcalendar.advice;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.itpark.concertcalendar.exception.ProductNotFoundException;

@ControllerAdvice
public class AppControllerAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public String handleProductNotFound(Model model) {
        model.addAttribute("message", "Product not found");
        return "error";
    }
}
