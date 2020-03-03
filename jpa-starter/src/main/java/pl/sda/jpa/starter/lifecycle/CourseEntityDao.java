package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.commons.Utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.stream.Collectors;

public class CourseEntityDao {
    private EntityManagerFactory entityManagerFactory;

    public CourseEntityDao(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }


    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);

    public void save(CourseEntity courseEntity) {
        return;
    }

    public void update(CourseEntity courseEntity) {

        EntityManager entityManager=null;
        try {
            /**
             * Tworzymy nową instancję EntityManager, tym samym rozpoczynamy działanie Persistence Context
             * Początek sesji logicznje
             */
            entityManager = entityManagerFactory.createEntityManager();
            /**
             * Początek transakcji nr 1 bazodanowej
             */
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            logger.info("Before: {}", courseEntity);
            logger.info("Contains: {}", entityManager.contains(courseEntity));
            /**
             * Zapisujemy nowy obiekt w Persistence Context, encja w tej chwili staje się zarządzana przez EntityManager
             * Nie zawsze oznacza to natychmiastowy zapis w bazie danych!
             */
            entityManager.persist(courseEntity);
            /**
             * Czy teraz obiekt session zarządza encją javaGda11?
             */
            logger.info("Contains: {}", entityManager.contains(courseEntity));
            logger.info("After: {}", courseEntity);

            entityManager.persist(courseEntity);
            entityManager.persist(courseEntity);

            /**
             * commitujemy transakcję nr 1, wszystkie zmiany dotąd niezapisane w bazie muszą być zapisane
             */
            transaction.commit();

            /**
             * Początek transakcji bazodanowej nr 2
             */
            entityManager.getTransaction().begin();

            /**
             * Pobieramy jedną encję po id z bazy danych
             */
            CourseEntity javaGda11FromDb = entityManager.find(CourseEntity.class, courseEntity.getId());
            /**
             * Czy encja pobrana z bazy to ta sama encja którą dodaliśmy wcześniej ?
             */
            logger.info("javaGda11 == javaGda11FromDb: {}", courseEntity == javaGda11FromDb);

            courseEntity.setName("XYZ");

            /**
             * Koniec transakcji nr 2 poprzez commit
             */
            entityManager.getTransaction().commit();

            /**
             * Początek transakcji bazodanowej nr 3
             */
            entityManager.getTransaction().begin();
            /**
             * Pobieramy wszystkie encje z bazy
             */
            List<CourseEntity> courses = entityManager.createQuery("from CourseEntity", CourseEntity.class).getResultList();
            logger.info("courses: \n{}", courses.stream()
                    .map(CourseEntity::toString)
                    .collect(Collectors.joining("\n")));

            /**
             * Czy 1 encja pobrana z bazy to ta sama encja którą dodaliśmy wcześniej ?
             */
            CourseEntity javaGda15FromDb = courses.get(1);
            logger.info("javaGda15 == javaGda15FromDb: {}", courseEntity == javaGda15FromDb);

            /**
             * Czy session nadal zarządza encją javaGda11?
             */
            logger.info("Contains: {}", entityManager.contains(courseEntity));

            /**
             * Koniec transakcji 3 poprzez commit
             */
            entityManager.getTransaction().commit();
        } finally {
            /**
             * kończymy pracę z entityManager, zamykamy go i tym samym zamykamy Persistence Context z nim związany
             */
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return;
    }

    public void delete(CourseEntity courseEntity) {
    }

    public CourseEntity findById(int id) {
        return null;
    }

    public List<CourseEntity> list() {
        return null;
    }

    public static void main(String[] args) {
        EntityManagerFactory entityManager = null;
        CourseEntity javaGda11 = new CourseEntity("JavaGda11", "Sopot", Utils.parse("2018-01-01"), Utils.parse("2018-09-01"));
        CourseEntity javaGda15 = new CourseEntity("JavaGda15", "Gdansk", Utils.parse("2018-02-10"), Utils.parse("2018-10-03"));
        CourseEntity javaGda22 = new CourseEntity("JavaGda22", "Sopot", Utils.parse("2018-02-15"), Utils.parse("2018-10-13"));

CourseEntityDao courseEntityDao=new CourseEntityDao(entityManager);
courseEntityDao.update(javaGda11);
    }
}