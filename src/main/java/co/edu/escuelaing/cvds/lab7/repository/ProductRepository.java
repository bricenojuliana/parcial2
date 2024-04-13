package co.edu.escuelaing.cvds.lab7.repository;


import co.edu.escuelaing.cvds.lab7.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Optional<Product> findById(Integer id);

}
