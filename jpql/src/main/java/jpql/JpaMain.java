package jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

//            TypedQuery<Member> query1 = em.createQuery("select m from Member m where m.username = :username", Member.class);
//            TypedQuery<String> query2 = em.createQuery("select m.username from Member m", String.class);
//            Query query3 = em.createQuery("SELECT m.username, m.age from Member m");
//
//            query1.setParameter("username", "member1");

            // 엔티티 프로젝션
            List<Member> result = em.createQuery("select m from Member m", Member.class)
                    .getResultList();

            result.get(0).setAge(20);

            // 엔티티 프로젝션
            em.createQuery("select m.team from Member m", Team.class).getResultList();
            em.createQuery("select t from Member m join m.team t", Team.class).getResultList();

            // 임베디드 타입 프로젝션
            em.createQuery("select o.address from Order o", Address.class).getResultList();

            // 스칼라 타입 프로젝션
            em.createQuery("select m.username, m.age from Member m").getResultList();

            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = resultList.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());

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
