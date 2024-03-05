package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

public class JpaMain12 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("hello");
            em.persist(member);

            Member member2 = new Member();
            member2.setName("hello2");
            em.persist(member2);

            em.flush();
            em.clear();

//            Member refMember = em.find(Member.class, member.getId());
//            printMember(refMember);
//            printMemberAndTeam(refMember);

//            Member refMember = em.getReference(Member.class, member.getId());
//            System.out.println("refMember = " + refMember.getClass());
//            System.out.println("refMember.getId() = " + refMember.getId());
//            System.out.println("refMember.getName() = " + refMember.getName());

//            Member m1 = em.find(Member.class, member.getId());
//            System.out.println("m1 = " + m1.getClass());
//            Member m2 = em.find(Member.class, member2.getId());
//            System.out.println("m2 = " + m2.getClass());
//            System.out.println("m1 == m2 = " + (m1.getClass() == m2.getClass()));
//            System.out.println("m1 instanceof Member = " + (m1 instanceof Member));
//            System.out.println("m2 instanceof Member = " + (m2 instanceof Member));

            Member refMember = em.getReference(Member.class, member.getId());
            System.out.println("refMember = " + refMember.getClass());
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));
//            em.detach(refMember);
//            em.clear();
//            refMember.getName();
            Hibernate.initialize(refMember);
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member = " + member.getName());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getName();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}
