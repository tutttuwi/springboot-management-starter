package me.tutttuwi.springboot.management.config;

import org.springframework.context.annotation.PropertySource;

// @Configuration
// @PropertySource("classpath:jdbc.properties")
@PropertySource("classpath:application-dev.yml")
public class PoolingDataSourceConfig {

  // @Bean
  // @Primary
  // @ConfigurationProperties("spring.datasource")
  // public DataSource dataSource() {
  // return (DataSource) DataSourceBuilder
  // .create().build();
  // }

  // @Bean(destroyMethod = "close")
  // public DataSource dataSource(@Value("${database.driverClassName}") String driverClassName,
  // @Value("${database.url}") String url,
  // @Value("${database.username}") String username,
  // @Value("${database.password}") String password,
  // @Value("${cp.maxTotal}") int maxTotal,
  // @Value("${cp.maxIdle}") int maxIdle,
  // @Value("${cp.minIdle}") int minIdle,
  // @Value("${cp.maxWaitMillis}") long maxWaitMillis) {
  // BasicDataSource dataSource = new BasicDataSource();
  // dataSource.setDriverClassName(driverClassName);
  // dataSource.setUrl(url);
  // dataSource.setUsername(username);
  // dataSource.setPassword(password);
  // dataSource.setMaxTotal(maxTotal);
  // dataSource.setMaxIdle(maxIdle);
  // dataSource.setMinIdle(minIdle);
  // dataSource.setMaxWaitMillis(maxWaitMillis);
  // return dataSource;
  // }
  //
  // @Bean
  // public JdbcTemplate jdbcTemplate(DataSource dataSource) {
  // return new JdbcTemplate(dataSource);
  // }
}
