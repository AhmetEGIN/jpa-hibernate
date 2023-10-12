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
        Map<?, ?> props = new HashMap<>();

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        // EntityManager Context'i yönetir.
        // İki yönlü olarak da yönetir. Yani hem uygulamadan veritabanına doğru hem de veri tabanından uygulamaya doğru
//        Application -> Context -> Database
//        Database -> Context -> Application

        try {
            entityManager.getTransaction().begin();

            // Veri tabanından veri çekmek için entityManger'ın find metodunu kullanabiliriz.

            // Aşağıdaki örnekte primary key' e göre Employee Class'ını getireceğiz
            Employee e1 = entityManager.find(Employee.class, 1);
//            // Bu sayede veriyi Database'den Context'e çekmiş olacağız.
//            // Eğer bu kodu çök defa çağırırsak veri tabanına sürekli query gönderilmez.
//            // Zaten Context' e kayıtlı olduğu için oradan çekilir.
//
//            e1.setName("AHMET");  // bu nesne şuanda context içerisinde bulunuyor.
//            // bu nesne üzerinde bir değişiklik yaptıktan sonra tekrardan bunu kaydetmemize gerek yok
//            // Transaction  sonlandırıldığında Context'teki nesneler veri tabanına maplenecek.
//            // Eğer transaction  sonlandırılmadan değiştirdiğimiz veriyi eski haline döndürürsek ->
//            // veri tabanına herhangi bir Query gönderilmez
//
//            System.out.println(e1);

            entityManager.remove(e1);  // Instance'ı Context'ten siler
            Employee e2 = new Employee();
            e2.setId(1);
            e2.setName("AHMET");
            e2.setAddress("Istanbul");
            entityManager.persist(e2);
            // e2 nesnesi veri tabanında bulunan veri ile aynı olduğu için query gönderilmez

            System.out.println(e1);

            /*
            * em.persist() -> Adding an entity in context
            * em.find() -> Finds by Primary Key. Get from DB and add it to the context if it doesn't already exist
            * em.remove() -> Marking entity for removal
            * em.merge() -> Merges an entity from outside the context to the context
            * em.refresh() -> Mirror the context from the database
            * em.detach() -> Taking the entity out of the context
             *
            * */



            entityManager.getTransaction().commit();  // End of Transaction
        }
        finally {
            entityManager.close();
        }


    }
}