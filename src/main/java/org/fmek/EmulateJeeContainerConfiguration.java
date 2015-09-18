package org.fmek;

import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.jms.ConnectionFactory;
import javax.jms.MessageListener;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;


@Configuration
@ComponentScan
@EnableTransactionManagement
public class EmulateJeeContainerConfiguration {

  @Bean
  public Log log() {
    return LogFactory.getLog("FMEK");
  }

  @Bean
  public DataSource dataSource(Log log)throws Exception{
    log.info("serve DataSource");
    DataSource ds = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .build();
    ds.getConnection().setAutoCommit(false);
    return ds;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter(Log log) {
    log.info("serve JpaVendorAdapter");
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setGenerateDdl(true);
    hibernateJpaVendorAdapter.setDatabase(Database.H2);
    return hibernateJpaVendorAdapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter, Log log) {
    log.info("serve LocalContainerEntityManagerFactoryBean");
    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setDataSource(dataSource);
    lef.setJpaVendorAdapter(jpaVendorAdapter);
    lef.setPackagesToScan("org.fmek.example.domain");
    return lef;
  }

  @Bean
  public TransactionManager transactionManager(Log log) {
    log.info("serve TransactionManager");
    return new UserTransactionManager();
  }

  @Bean
  public PlatformTransactionManager platformTransactionManager(TransactionManager transactionManager, Log log){
    log.info("serve PlatformTransactionManager");
    return new JtaTransactionManager(transactionManager);
  }

  @Bean
  public ConnectionFactory connectionFactory(Log log) {
    log.info("serve ConnectionFactory");
    return new ActiveMQConnectionFactory("vm://localhost");
  }

  @Bean
  public DefaultMessageListenerContainer defaultMessageListenerContainer(MessageListener messageListener, ConnectionFactory connectionFactory, Log log) {
    log.info("serve DefaultMessageListenerContainer");
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(connectionFactory);
    container.setDestinationName("test");
    container.setMessageListener(messageListener);
    return container;
  }

}