package me.tutttuwi.springboot.management.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import me.tutttuwi.springboot.management.dao.combined.SearchUserRepository;
import me.tutttuwi.springboot.management.dto.combined.SearchUserList;
import me.tutttuwi.springboot.management.request.SearchUserFormApiRequest;

@Transactional
@Service
public class AppSearchUserApiService {

  @Autowired
  SearchUserRepository searchUserDao;

  /**
   * ユーザ情報一覧取得.
   *
   * @param inputForm ForgotpasswordFormRequest
   */
  @Transactional
  public List<SearchUserList> getUserList(SearchUserFormApiRequest inputForm) throws Throwable {
    List<SearchUserList> result = searchUserDao.selectUserList(inputForm.getFstNm(),
        inputForm.getLstNm(), inputForm.getUserId(), inputForm.getEmail());
    return result;
  }

}
