package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.entities.Employee;
import org.example.entities.Passport;
import org.example.entities.Person;
import org.example.entities.User;
import org.example.persistence.CustomPersistenceUnitInfo;
import org.hibernate.PersistentObjectException;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String puName = "pu-name";
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.show_sql", "true");
        props.put("hibernate.hbm2ddl.auto", "create");   // create, update, none
        // create -> veri tabanında belirtilen tablo varsa onu siler ve yeni bir tane oluşturur
        //update -> gerekli durumlarda güncelleme işlemlerini yapar

        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider()
                .createContainerEntityManagerFactory(new CustomPersistenceUnitInfo(puName), props);

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Person person = new Person();
            person.setName("Ahmet");
            Passport passport = new Passport();
            passport.setNumber("abc123");

            person.setPassport(passport);
            passport.setPerson(person);

            entityManager.persist(person);
//            entityManager.persist(passport);  -> Cascade kullanarak bu işlemi yaptırabiliriz

            // persist sırası farketmez. Persist sadece context'e ekleme yapar. Bu yüzden herhangi bir sıkıntı çıkmaz



            TypedQuery<Person> q =  entityManager.createQuery("SELECT p from Person p where p.passport.number = :number", Person.class);
            q.setParameter("number", "abc123");
            System.out.println(q.getResultList());


            User user = new User();
            user.setName("Laur");
            user.setDescription("ABC");
            entityManager.persist(user);


            entityManager.getTransaction().commit();  // End of Transaction
        }
        finally {
            entityManager.close();
        }

    }
}