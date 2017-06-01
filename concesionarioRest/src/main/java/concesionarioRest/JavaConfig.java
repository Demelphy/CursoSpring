package concesionarioRest;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration // Anotamos esta clase como un fichero de configuración para el contexto de Spring
@EnableTransactionManagement // Anotamos la activación de la gestión de Transacciones
@EnableJpaRepositories(basePackages="com.concesionarioRest.persistencia") 	// Anotamos la activación de la gestión de repositorios para el paquete 
																			// que contiene la capa de persistencia.
public class JavaConfig {

	// 1º Necesitamos un Jpa(TransactionManager)
	// 2º Necesitamos un LocalContainer(EntityManagerFactory)Bean para el Jpa(TransactionManager)
	// 3º Necesitamos un DataSource para el LocalContainer(EntityManagerFactory)Bean
	// 4º Dentro del DataSource necesitamos la configuración de la conexión del pull de BBDD
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
	
	@Bean
	public DataSource dataSource (){
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setUrl("jdbc:derby://localhost:1527/concesionario;create=false");
		basicDataSource.setUsername("admin");
		basicDataSource.setPassword("admin");
		basicDataSource.setDriverClassName("org.apache.derby.jdbc.ClientDriver40");
		
		return basicDataSource;
	}
		
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		
		entityManagerFactoryBean.setDataSource(dataSource);
		
		entityManagerFactoryBean.setPackagesToScan("com.atsistemas.entidades");
		
		return(entityManagerFactoryBean);
	}
}
