package org.fmek;

import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.activemq.ActiveMQConnectionFactory;
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

import javax.jms.MessageListener;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;


@Configuration
@ComponentScan
@EnableTransactionManagement
public class EmulateJ2eeContainerConfiguration {

  @Bean
  public DataSource dataSource()throws Exception{
    DataSource ds = new EmbeddedDatabaseBuilder()
        .setType(EmbeddedDatabaseType.H2)
        .build();
    ds.getConnection().setAutoCommit(false);
    return ds;
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
    hibernateJpaVendorAdapter.setShowSql(true);
    hibernateJpaVendorAdapter.setGenerateDdl(true);
    hibernateJpaVendorAdapter.setDatabase(Database.H2);
    return hibernateJpaVendorAdapter;
  }

  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
    LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
    lef.setDataSource(dataSource);
    lef.setJpaVendorAdapter(jpaVendorAdapter);
    lef.setPackagesToScan("org.fmek.example.domain");
    return lef;
  }

  @Bean
  public TransactionManager transactionManager() {
    return new UserTransactionManager();
  }

  @Bean
  public PlatformTransactionManager platformTransactionManager(TransactionManager transactionManager){
    return new JtaTransactionManager(transactionManager);
  }

  @Bean
  public ActiveMQConnectionFactory activeMQConnectionFactory() {
    return new ActiveMQConnectionFactory("vm://localhost"); //?broker.persistent=false");
  }

  @Bean
  public DefaultMessageListenerContainer defaultMessageListenerContainer(MessageListener messageListener) {
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setConnectionFactory(activeMQConnectionFactory());
    container.setDestinationName("test");
    container.setMessageListener(messageListener);
    return container;
  }

}