package me.tutttuwi.springboot.management.interceptor;

import org.springframework.web.context.request.async.CallableProcessingInterceptor;

public class CustomCallableProcessingInterceptor implements CallableProcessingInterceptor {

  // TODO: 非同期処理でエラー返却するエラー画面を制御したい場合設定
  // @Override
  // public <T> Object handleTimeout(NativeWebRequest request, Callable<T> task) {
  // return "error/timeout";
  // }

}
