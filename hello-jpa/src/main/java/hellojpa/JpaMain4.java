package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaMain4 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member1 = new Member();
            member1.setId(1L);
            member1.setName("A");
            member1.setRoleType(RoleType.USER);

            Member member2 = new Member();
            member2.setId(2L);
            member2.setName("B");
            member2.setRoleType(RoleType.ADMIN);

            Member member3 = new Member();
            member3.setId(3L);
            member3.setName("C");
            member3.setRoleType(RoleType.GUEST);

            em.persist(member1);
            em.persist(member2);
            em.persist(member3);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
