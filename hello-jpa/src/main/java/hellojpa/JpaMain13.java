package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain13 {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setName("member1");
            member.setTeam(team);
            em.persist(member);

            Team team2 = new Team();
            team2.setName("teamA");
            em.persist(team2);

            Member member2 = new Member();
            member2.setName("member1");
            member2.setTeam(team2);
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m = em.find(Member.class, member2.getId());
//
//            System.out.println("m = " + m.getTeam().getClass());
//
//            System.out.println("===============");
//            m.getTeam();
//            System.out.println("===============");
//            m.getTeam().getName(); // 초기화
//            System.out.println("===============");

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            System.out.println("===============");

            List<Member> members2 = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
