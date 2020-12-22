package me.tutttuwi.springboot.management.controller.ui;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequestMapping("")
public class AppErrorUiController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error/500";
    }

    @GetMapping("/error")
    public String getHandleError(HttpServletRequest request) {
        log.info("WRITE ERROR LOG --- METHOD:GET");

        // HTTP ステータスを決める
        // ここでは 404 以外は全部 500 にする
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (statusCode != null && statusCode.toString().equals("404")) {
            status = HttpStatus.NOT_FOUND;
            return "/error/404";
        }
        return "/error/500";
    }

    @PostMapping("/error")
    public String postHandleError() {
        log.info("WRITE ERROR LOG --- METHOD:POST");
        return "/error/500";
    }

    @ExceptionHandler(Throwable.class)
    public String handleThrowable(Throwable exception, Model model) {
        model.addAttribute("", "");
        return "/error/500";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception exception, Model model) {
        model.addAttribute("", "");
        return "/error/500";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException exception, Model model) {
        model.addAttribute("", "");
        return "/error/500";
    }

    @ExceptionHandler(NullPointerException.class)
    public String handleIOException(NullPointerException exception, Model model) {
        model.addAttribute("", "");
        log.error(exception.getStackTrace().toString());
        return "/error/500";
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException(NotFoundException exception, Model model) {
        model.addAttribute("", "");
        log.debug("----- NOT FOUND PROC -----");
        log.error(exception.getStackTrace().toString());
        return "/error/404";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFoundException(NoHandlerFoundException exception, Model model) {
        model.addAttribute("", "");
        log.debug("----- NOT FOUND PROC -----");
        log.error(exception.getStackTrace().toString());
        return "/error/404";
    }

    @ExceptionHandler(Forbidden.class)
    public String handleForbiddenException(Forbidden exception, Model model) {
        model.addAttribute("", "");
        return "/error/500";
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(MethodNotAllowedException.class)
    public String handleMethodNotAllowException(MethodNotAllowedException exception, Model model) {
        model.addAttribute("", "");
        return "/error/500";
    }
}
