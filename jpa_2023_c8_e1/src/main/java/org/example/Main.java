package org.example;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Book;
import org.example.entities.ElectronicDevice;
import org.example.entities.Product;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;


public class Main {
    public static void main(String[] args) {

        String puName = "pu-name";
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
//        props.put("hibernate.hbm2ddl.auto", "create");   // create, update, none
        // create -> veri tabanında belirtilen tablo varsa onu siler ve yeni bir tane oluşturur
        //update -> gerekli durumlarda güncelleme işlemlerini yapar

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

//            Book book = new Book();
//            book.setId(1L);
//            book.setAuthor("Laur Spilca");
//
//            ElectronicDevice electronicDevice = new ElectronicDevice();
//            electronicDevice.setId(2L);
//            electronicDevice.setVoltage(220);
//
//            entityManager.persist(book);
//            entityManager.persist(electronicDevice);

            var q = "SELECT p FROM Product p";
            entityManager.createQuery(q, Product.class)
                            .getResultList().forEach(System.out::println);

            entityManager.getTransaction().commit();  // End of Transaction
        }
        finally {
            entityManager.close();
        }




    }
}