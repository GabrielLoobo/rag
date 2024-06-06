package com.estudos.rag.config;

import io.hypersistence.utils.hibernate.naming.CamelCaseToSnakeCaseNamingStrategy;
import liquibase.integration.spring.SpringLiquibase;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.Properties;


@Profile("!test")
@Configuration
public class LiquibaseConfig implements WebMvcConfigurer {

  @Autowired
  private DataSource dataSource;

  @Value("${spring.liquibase.change-log}")
  private String liquibaseChangelog;

  @Value("${spring.jpa.properties.hibernate.dialect}")
  private String hibernateDialect;

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String hibernateDdlAuto;

  @Value("${spring.jpa.show-sql}")
  private Boolean hibernateShowSql;

  @Bean
  public SpringLiquibase liquibase() {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(dataSource);
    liquibase.setChangeLog(liquibaseChangelog);
    return liquibase;
  }

  @Bean
  @DependsOn("liquibase")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
    entityManagerFactoryBean.setPackagesToScan("com.estudos.rag.domain.entity");
    entityManagerFactoryBean.setJpaProperties(hibernateProperties());
    return entityManagerFactoryBean;
  }

  private Properties hibernateProperties() {
    Properties props = new Properties();
    props.put("hibernate.dialect", hibernateDialect);
    props.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);
    props.put("hibernate.show_sql", hibernateShowSql);
    props.put("hibernate.physical_naming_strategy",
        CamelCaseToSnakeCaseNamingStrategy.class.getName());
    return props;
  }
}
