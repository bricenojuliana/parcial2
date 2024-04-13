package co.edu.escuelaing.cvds.lab7;

import co.edu.escuelaing.cvds.lab7.config.CorsConfig;
import co.edu.escuelaing.cvds.lab7.model.*;
import co.edu.escuelaing.cvds.lab7.repository.UserRepository;
import co.edu.escuelaing.cvds.lab7.service.ConfigurationService;
import co.edu.escuelaing.cvds.lab7.service.EmployeeService;
import co.edu.escuelaing.cvds.lab7.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
@Slf4j
@Import(CorsConfig.class)
public class Lab7Application {
	private final ConfigurationService configurationService;

	private final UserRepository userRepository;

	private final EmployeeService employeeService;

	private final ProductService productService;

	@Autowired
	public Lab7Application(
			ConfigurationService configurationService,
			UserRepository userRepository,
			EmployeeService employeeService,
			ProductService productService) {
		this.configurationService = configurationService;
		this.userRepository = userRepository;
		this.employeeService = employeeService;
		this.productService = productService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Lab7Application.class, args);
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			log.info("Adding Products....");
			productService.addProduct(new Product(1, "Papa pastusa", "Papa típica colombiana muy consumida en la región cundiboyacense", Category.Food, (short) 5, 2000, 100));
			productService.addProduct(new Product(3, "Camiseta", "Camiseta de algodón", Category.Clothes, (short) 4, 25000, 20));
			productService.addProduct(new Product(4, "Smartphone", "Teléfono inteligente con pantalla táctil", Category.Technology, (short) 5, 1000000, 10));
			productService.addProduct(new Product(5, "Analgesico", "Medicamento para aliviar el dolor", Category.Drugstore, (short) 3, 5000, 50));

			log.info("Adding Employees....");
			employeeService.addEmployee(new Employee("1", "Cristian", "Naranjo", "Gerente", 1));
			employeeService.addEmployee(new Employee("2", "Juliana", "Briceño", "CEO", 2));
			employeeService.addEmployee(new Employee("3", "Jorge", "Useche", "Presidente", 10));

			log.info("Adding Configurations....");
			configurationService.addConfiguration(new Configuration("premio", "810000"));
			configurationService.addConfiguration(new Configuration("descuento", "0.1"));
			configurationService.addConfiguration(new Configuration("app-name", "Miraculous: Las Aventuras de Ladybug"));

			log.info("\nGetting all configurations....");
			configurationService.getAllConfigurations().forEach(configuration -> System.out.println(configuration));

			log.info("\nAdding admin@site.org user with Password: admin");
			userRepository.save(new User("admin@site.org", "admin", Arrays.asList(UserRole.ADMINISTRADOR, UserRole.CLIENTE)));
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
