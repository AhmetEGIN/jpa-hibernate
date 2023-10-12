package org.egin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.egin.entities.Comment;
import org.egin.entities.Post;
import org.egin.persistence.CustomPersistenceUnitInfo;
import org.hibernate.jpa.HibernatePersistenceProvider;

import java.util.HashMap;
import java.util.List;
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

            Post p = new Post();
            p.setTitle("Post-1");
            p.setContent("Post-1 Some Description");

            Comment c = new Comment();
            c.setContent("Comment - 1 ");
            Comment c2 = new Comment();
            c2.setContent("Comment - 2 ");

            p.setComments(List.of(c, c2));
            c.setPost(p);
            c2.setPost(p);

            entityManager.persist(p);
            entityManager.getTransaction().commit();
//            entityManager.persist(c);
            entityManager.getTransaction().begin();
//            entityManager.remove(p);
            entityManager.remove(p);
//            entityManager.remove(c2);

            entityManager.getTransaction().commit();  // End of Transaction
        }
        finally {
            entityManager.close();
        }


    }
}