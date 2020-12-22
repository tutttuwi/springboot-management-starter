package me.tutttuwi.apiflier.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import me.tutttuwi.springboot.management.config.AppConfig;
import me.tutttuwi.springboot.management.service.MessageService;

//@RunWith(MockitoJUnitRunner.class)
// TODO: Integrationテスト用のクラスは分けるべき？
// @TestPropertySource(properties = "auth.failureCountToLock=3")
// @TestPropertySource(locations = "/test.properties")
//@ContextConfiguration(classes = { AppConfig.class, TestConfig.class }) // この順番で記載するとDataSource定義を上書きできる
@Transactional
//  @Commit // テスト完了後、コミットしたい場合に指定する
@ContextHierarchy({ @ContextConfiguration(classes = AppConfig.class) }) // 階層関係があればHierarchyの方を使用すること
@WebAppConfiguration
class MessageServiceTest {

  // TODO: 永続化コンテキストを明示的にフラッシュすることを忘れないこと

  @InjectMocks
  MessageService service;
  @Mock
  MessageSource mockMessageSource;

  //  @Value("${auth.failureCountToLock:5}")
  //  int failureCountToLock;

  //  @Test
  //  final void testGetMessageByCode() {
  //    doReturn("Hello!!").when(mockMessageSource)
  //        .getMessage("greeting", null, Locale.getDefault());
  //    String actualMessage = service.getMessageByCode("greeting");
  //    assertThat(actualMessage, is("Hello!!"));
  //  }

  /**
   * Sqlを付与することで、テストメソッド実行前に任意のSQL文を実行することができる
   */
  //  @Test
  //  @Sql({ "/account-delete.sql", "/account-insert-data.sql" })
  //  final void testFindOne() {
  //    Account account = accountRepositry.findOne("001");
  //  }

}
