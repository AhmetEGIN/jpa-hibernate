package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(), new HashMap());
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
//         EntityManger işlemleri kontol etmemize yarar.
//        Bu Manager Context içerisindeki objeleri yönetir.
//        Entity Manager oluşturmak için EntityManagerFactory'ye ihtiyacımız var.


        try {
            entityManager.getTransaction().begin();

            Product p1 = new Product();
            p1.setId(1);
            p1.setName("Beer");

            entityManager.persist(p1);  // verilen objeyi context'e atar. INSERT Query değildir.
            // Persist -> bir entity'nin yeni bir instance'ını context'e atmadır.

            entityManager.getTransaction().commit();  // bu komut ile context içerisindeki veriler INSERT komutu ile
                                                    // veri tabanında ilgili tabloya eklenir

        }
        finally {

            entityManager.close();
        }


    }
}