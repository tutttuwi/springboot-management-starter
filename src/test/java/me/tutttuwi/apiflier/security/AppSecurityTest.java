package me.tutttuwi.apiflier.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import me.tutttuwi.springboot.management.config.AppConfig;

//@RunWith(MockitoJUnitRunner.class)
//TODO: Integrationテスト用のクラスは分けるべき？
//@TestPropertySource(properties = "auth.failureCountToLock=3")
//@TestPropertySource(locations = "/test.properties")
//@ContextConfiguration(classes = { AppConfig.class, TestConfig.class }) // この順番で記載するとDataSource定義を上書きできる
@Transactional
//@Commit // テスト完了後、コミットしたい場合に指定する
@ContextHierarchy({ @ContextConfiguration(classes = AppConfig.class) }) // 階層関係があればHierarchyの方を使用すること
@WebAppConfiguration
public class AppSecurityTest {

  @Autowired
  WebApplicationContext context;

  MockMvc mockMvc;

  //  @Before
  //  public void setupMockMvc() {
  //    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity())build();
  //  }
  //
  //  @Test
  //  public void testHome() throws Exception {
  //    mockMvc.perform(get("/"))
  //        .andExpect(status().isOk())
  //        .andExpect(forwardedUrl("/WEB-INF/index.jsp"));
  //  }
  //
  //  @Test
  //  public void testBooks() throws Exception {
  //    mockMvc.perform(get("/books")
  //        .param("name", "Spring")
  //        .accept(MediaType.APPLICATION_JSON)
  //        .header("X-Track-Id", UUID.randomUUID().toString()))
  //        .andExpect(status().isOk());
  //
  //    mockMvc.perform(get("/books"))
  //        .andExpect(status().isOk())
  //        .andDo(log());
  //  }

}
