package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.Employee;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {


        String puName = "pu-name";
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "update");   // create, update, none
        // create -> veri tabanında belirtilen tablo varsa onu siler ve yeni bir tane oluşturur
        //update -> gerekli durumlarda güncelleme işlemlerini yapar

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // EntityManager Context'i yönetir.
        // İki yönlü olarak da yönetir. Yani hem uygulamadan veritabanına doğru hem de veri tabanından uygulamaya doğru
//        Application -> Context -> Database
//        Database -> Context -> Application

        try {
            entityManager.getTransaction().begin();

            Employee e2 = new Employee();
            e2.setName("AHMET");
            e2.setAddress("Istanbul");
            entityManager.persist(e2);

//            Employee e1 = entityManager.find(Employee.class, 1);
            System.out.println(e2);


//            e1.setName("Laur");

            /*
             * em.persist() -> Adding an entity in context
             * em.find() -> Finds by Primary Key. Get from DB and add it to the context if it doesn't already exist
             * em.remove() -> Marking entity for removal
             * em.merge() -> Merges an entity from outside the context to the context
             * em.refresh() -> Mirror the context from the database
             * em.detach() -> Taking the entity out of the context
             * em.getReference() -> find ile arasındaki fark  Eğer entity ile işlem yaparsak veri tabanına query gönderir.
             *
             *
             * */



            entityManager.getTransaction().commit();  // End of Transaction
        }
        finally {
            entityManager.close();
        }


    }
}