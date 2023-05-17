package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class AbstractDao<T> {
	 // Spring boot에서는 persistence.xml 파일이 없어서 아래의 소스가 에러가 발생한다.
	  private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("JpaExample");
	  private Class<T> clazz;
	  // 람다식을 위한 interface
	  protected interface EntityManagerRunable {
	    void run(EntityManager em);
	  }
	  // 람다식을 위한 interface
	  protected interface EntityManagerCallable<V> {
	    V run(EntityManager em);
	  }
	  // 생성자를 protected로 설정
	  protected AbstractDao(Class<T> clazz) {
	    this.clazz = clazz;
	  }
	  // 클래스 타입을 취득하는 함수
	  protected final Class<T> getClazz() {
	    return clazz;
	  }
	  // 테이블에서 key의 조건으로 데이터를 취득한다.
	  public T findOne(Object id) {
	    return transaction((em) -> {
	      return em.find(clazz, id);
	    });
	  }
	  // Entity를 데이터 베이스에 Insert한다.
	  public T create(T entity) {
	    return transaction((em) -> {
	      em.persist(entity);
	      return entity;
	    });
	  }
	  // Entity를 데이터 베이스에 Update한다.
	  public T update(T entity) {
	    return transaction((em) -> {
	      // 클래스를 데이터베이스 데이터와 매핑시킨다.
	      em.detach(entity);
	      // update
	      return em.merge(entity);
	    });
	  }
	  // Entity를 데이터 베이스에 Delete한다.
	  public void delete(T entity) {
	    transaction((em) -> {
	      // 클래스를 데이터베이스와 매핑시킨다.
	      em.detach(entity);
	      // 그리고 update를 하고 삭제한다.
	      em.remove(em.merge(entity));
	    });
	  }
	  // 반환 값이 있는 트랜젝션 (일반 트랜젹션으로 데이터가 변화한다.)
	  public <V> V transaction(EntityManagerCallable<V> callable) {
	    return transaction(callable, false);
	  }
	  // 반환 값이 있는 트랜젝션 (readonly를 true 설정하면 함수를 호출하는 가운데서는 commit이 발생하지 않는다.)
	  public <V> V transaction(EntityManagerCallable<V> callable, boolean readonly) {
	    // Manager를 생성한다. EntityManagerFactory를 persistence.xml에서 취득을 못했기 때문에 EntityManager를 취득하지 못한다.
	    EntityManager em = emf.createEntityManager();
	    // transaction을 가져온다.
	    EntityTransaction transaction = em.getTransaction();
	    // 트랜젝션을 시작한다.
	    transaction.begin();
	    try {
	      // 람다식을 실행한다.
	      V ret = callable.run(em);
	      // readonly가 true면 rollback한다.
	      if (readonly) {
	        transaction.rollback();
	      } else {
	        // 트랜젝션을 데이터베이스에 입력한다.
	        transaction.commit();
	      }
	      // 결과를 리턴한다.
	      return ret;
	      // 에러가 발생할 경우
	    } catch (Throwable e) {
	      // transaction이 활성 중이라면
	      if (transaction.isActive()) {
	        // rollback
	        transaction.rollback();
	      }
	      // RuntimeException로 변환
	      throw new RuntimeException(e);
	    } finally {
	      // Manager를 닫는다.
	      em.close();
	    }
	  }
	  // 반환 값이 없는 transaction (일반 트랜젹션으로 데이터가 변화한다.)
	  public void transaction(EntityManagerRunable runnable) {
	    transaction(runnable, false);
	  }
	  // 반환 값이 없는 트랜젝션 (readonly를 true 설정하면 함수를 호출하는 가운데서는 commit이 발생하지 않는다.)
	  public void transaction(EntityManagerRunable runnable, boolean readonly) {
	    // Manager를 생성한다. EntityManagerFactory를 persistence.xml에서 취득을 못했기 때문에 EntityManager를 취득하지 못한다.
	    EntityManager em = emf.createEntityManager();
	    // transaction을 가져온다.
	    EntityTransaction transaction = em.getTransaction();
	    // 트랜젝션을 시작한다.
	    transaction.begin();
	    try {
	      // 람다식을 실행한다.
	      runnable.run(em);
	      // readonly가 true면 rollback한다.
	      if (readonly) {
	        transaction.rollback();
	      } else {
	        // 트랜젝션을 데이터베이스에 입력한다.
	        transaction.commit();
	      }
	      // 에러가 발생할 경우
	    } catch (Throwable e) {
	      // transaction이 활성 중이라면
	      if (transaction.isActive()) {
	        // rollback
	        transaction.rollback();
	      }
	      // RuntimeException로 변환
	      throw new RuntimeException(e);
	    } finally {
	      // Manager를 닫는다.
	      em.close();
	    }
	  }
}
