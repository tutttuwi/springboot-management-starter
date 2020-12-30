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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException.Forbidden;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.constant.WebConst;

@Slf4j
@ControllerAdvice
@RequestMapping("")
public class AppErrorUiController implements ErrorController {

  @Override
  public String getErrorPath() {
    return WebConst.ERROR_URL;
  }

  /**
   * ハンドリングエラー(GETリクエスト).<br/>
   * 認証処理など前段のフィルターでエラーとなった場合こちらに流れる。
   *
   * @param request HttpServletRequest
   * @return
   */
  @GetMapping(WebConst.ERROR_URL_ROOT)
  public String getHandleError(HttpServletRequest request) {
    log.info("WRITE ERROR LOG --- METHOD:GET");
    request.getParameterMap().forEach((key, value) -> {
      log.info("param >> key:{} value:{}", key, value);
    });

    return judgeStatusCode(request);
  }

  /**
   * ハンドリングエラー(POSTリクエスト). <br/>
   * 認証処理など前段のフィルターでエラーとなった場合こちらに流れる。
   *
   * @return
   */
  @PostMapping(WebConst.ERROR_URL_ROOT)
  public String postHandleError(HttpServletRequest request) {
    log.info("WRITE ERROR LOG --- METHOD:POST");
    request.getParameterMap().forEach((key, value) -> {
      log.info("param >> key:{} value:{}", key, value);
    });
    return judgeStatusCode(request);
  }

  /**
   * エラーハンドリング. <br/>
   * エラー種別ごとに該当するエラーページを表示
   *
   * @return
   */
  @GetMapping(WebConst.ERROR_URL_ROOT + "/{code}")
  public String getAnyHandleError(HttpServletRequest request, @PathVariable("code") String code) {
    return WebConst.ERROR_URL_ROOT + "/" + code;
  }

  /**
   * エラーハンドリング. <br/>
   * エラー種別ごとに該当するエラーページを表示
   *
   * @return
   */
  @PostMapping(WebConst.ERROR_URL_ROOT + "/{code}")
  public String postAnyHandleError(HttpServletRequest request, @PathVariable("code") String code) {
    return WebConst.ERROR_URL_ROOT + "/" + code;
  }

  /**
   * リクエストステータス判定.
   *
   * @param request HttpServletRequest
   * @return
   */
  private String judgeStatusCode(HttpServletRequest request) {
    // HTTP ステータスを決める ここでは 404 以外は全て 500 にする
    Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    if (statusCode != null && statusCode.toString().equals("404")) {
      status = HttpStatus.NOT_FOUND;
      return WebConst.NOTFOUND_URL;
    }
    return getErrorPath();
  }

  @ExceptionHandler(Throwable.class)
  public String handleThrowable(Throwable exception, Model model) {
    model.addAttribute("", "");
    return getErrorPath();
  }

  @ExceptionHandler(Exception.class)
  public String handleException(Exception exception, Model model) {
    model.addAttribute("", "");
    return getErrorPath();
  }

  /**
   * handleIOException.
   *
   * @param exception IOException
   * @param model Model
   * @return
   */
  @ExceptionHandler(IOException.class)
  public String handleIOException(IOException exception, Model model) {
    model.addAttribute("", "");
    return getErrorPath();
  }

  /**
   * handleIOException.
   *
   * @param exception NullPointerException
   * @param model Model
   * @return
   */
  @ExceptionHandler(NullPointerException.class)
  public String handleIOException(NullPointerException exception, Model model) {
    model.addAttribute("", "");
    log.error(exception.getStackTrace().toString());
    return getErrorPath();
  }

  /**
   * handleNotFoundException.
   *
   * @param exception NotFoundException
   * @param model Model
   * @return
   */
  @ResponseStatus(code = HttpStatus.NOT_FOUND)
  @ExceptionHandler(NotFoundException.class)
  public String handleNotFoundException(NotFoundException exception, Model model) {
    model.addAttribute("", "");
    log.debug("----- NOT FOUND PROC -----");
    log.error(exception.getStackTrace().toString());
    return WebConst.NOTFOUND_URL;
  }

  /**
   * handleNotFoundException.
   *
   * @param exception NoHandlerFoundException
   * @param model Model
   * @return
   */
  @ExceptionHandler(NoHandlerFoundException.class)
  public String handleNotFoundException(NoHandlerFoundException exception, Model model) {
    model.addAttribute("", "");
    log.debug("----- NOT FOUND PROC -----");
    log.error(exception.getStackTrace().toString());
    return WebConst.NOTFOUND_URL;
  }

  @ExceptionHandler(Forbidden.class)
  public String handleForbiddenException(Forbidden exception, Model model) {
    model.addAttribute("", "");
    return WebConst.FORBIDDEN_URL;
  }

  @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
  @ExceptionHandler(MethodNotAllowedException.class)
  public String handleMethodNotAllowException(MethodNotAllowedException exception, Model model) {
    model.addAttribute("", "");
    return getErrorPath();
  }
}
