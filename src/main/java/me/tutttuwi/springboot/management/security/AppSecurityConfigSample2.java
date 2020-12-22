package me.tutttuwi.springboot.management.security;

/**
 * パスのパターンごとに異なるセキュリティ対策を適用するサンプルクラス
 *
 */
//@EnableWebSecurity
//public class AppSecurityConfigSample2 {
//
//  @Configuration
//  @Order(1)
//  public static class UiWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(WebSecurity web) {
//      web.ignoring().antMatchers("/ui/**");
//    }
//  }
//
//  @Configuration
//  @Order(2)
//  public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    public void configure(WebSecurity web) {
//      web.ignoring().antMatchers("/api/**");
//    }
//  }
//}