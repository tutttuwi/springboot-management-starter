package me.tutttuwi.springboot.management.request;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Component
public class ApiParameterRequest<R extends ApiCommonRequest> extends AbstractApiRequestParameter
    implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 固有リクエストデータ. */
  private R requestData;

}
