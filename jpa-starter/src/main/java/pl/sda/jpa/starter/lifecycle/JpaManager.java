package pl.sda.jpa.starter.lifecycle;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaManager {
    private EntityManagerFactory entityManagerFactory;

    public JpaManager() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.lifecycle");
    }


    public static void main(String[] args) {
        JpaManager jpaManager = new JpaManager();
    }
}