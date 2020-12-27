package me.tutttuwi.springboot.management.controller.ui;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import lombok.val;
import lombok.extern.slf4j.Slf4j;
import me.tutttuwi.springboot.management.bean.UserCsv;
import me.tutttuwi.springboot.management.controller.BaseController;
import me.tutttuwi.springboot.management.view.CsvView;
import me.tutttuwi.springboot.management.view.PdfView;

@Slf4j
@Controller
@RequestMapping("/config")
public class AppConfigUiController extends BaseController {

  /**
   * social.
   *
   * @return HTML Path String
   * @throws Throwable Any Exception
   */
  @GetMapping(value = "/social")
  public String social() throws Throwable {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    String name = auth.getName();
    log.info(name);

    return "page/config/config_social";
  }

  /**
   * CSVダウンロード.
   *
   * @param filename filename
   * @return
   */
  @GetMapping("/download/{filename:.+\\.csv}")
  public ModelAndView downloadCsv(@PathVariable String filename) {
    // 全件取得する
    // val users = userService.findAll(new UserCriteria(), Pageable.NO_LIMIT);
    final List<UserCsv> fromUsers = new ArrayList<UserCsv>();
    val user = new UserCsv();
    user.setId(1L);
    user.setFirstName("FirstName");
    user.setLastName("LastName");
    user.setEmail("email@example.com");
    user.setAddress("address");
    user.setPassword("password");
    user.setTel("090-1111-1111");
    user.setZip("123-1234");
    fromUsers.add(user);

    // List<UserCsv> users = new ArrayList<UserCsv>();
    // val boundMapperFacadeInfo = mapperFactory.getMapperFacade(List<UserCsv>, List<UserCsv>);
    // users = boundMapperFacadeInfo.map(fromUsers);

    // 詰め替える
    // List<UserCsv> csvList = modelMapper.map(users.getData(), toListType(UserCsv.class));

    // CSVスキーマクラス、データ、ダウンロード時のファイル名を指定する
    val view = new CsvView(UserCsv.class, fromUsers, filename);
    ModelAndView v = new ModelAndView();

    return new ModelAndView(view);
  }

  // TODO: 要実装
  /**
   * Excelダウンロード.
   *
   * @param filename
   * @return
   */
  // @GetMapping(path = "/download/{filename:.+\\.xlsx}")
  // public ModelAndView downloadExcel(@PathVariable String filename) {
  // // 全件取得する
  // val users = userService.findAll(new UserCriteria(), Pageable.NO_LIMIT);
  //
  // // Excelプック生成コールバック、データ、ダウンロード時のファイル名を指定する
  // val view = new ExcelView(new UserExcel(), users.getData(), filename);
  //
  // return new ModelAndView(view);
  // }

  /**
   * PDFダウンロード.
   *
   * @param filename filename
   * @return
   */
  @GetMapping(path = "/download/{filename:.+\\.pdf}")
  public ModelAndView downloadPdf(@PathVariable String filename) {
    // 全件取得する
    // val users = userService.findAll(new UserCriteria(), Pageable.NO_LIMIT);

    // 帳票レイアウト、データ、ダウンロード時のファイル名を指定する
    val view = new PdfView("report/users.jrxml", null, filename);

    return new ModelAndView(view);
  }

}
