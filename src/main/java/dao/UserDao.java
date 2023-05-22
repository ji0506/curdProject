package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.User;

public class UserDao extends AbstractDao<User> {
	// 생성자 재정의 protected에서 public으로 바꾸고 파라미터를 재설정한다.
	//private static BoardDao bdao = new BoardDao();
	
	public UserDao() {
		// protected 생성자 호출
		super(User.class);
	}

	// 모든 데이터 취득
	@SuppressWarnings("unchecked")
	public List<User> selectAll() {
		// AbstractDao 추상 클래스의 transaction 함수를 사용한다.
		return super.transaction((em) -> {
			// 쿼리를 만든다. (실무에서는 createQuery가 아닌 createNamedQuery를 사용해서 Entity에서 쿼리를 일괄 관리한다.)
			return (List<User>) em.createNamedQuery("User.findAll").getResultList();
		});
	}

	// Id에 의한 데이터를 취득한다.
	public User selectById(String userId) {
		// AbstractDao 추상 클래스의 transaction 함수를 사용한다.
		return super.transaction((em) -> {
			// 쿼리를 만든다. (실무에서는 createQuery가 아닌 createNamedQuery를 사용해서 Entity에서 쿼리를 일괄 관리한다.)
			Query query = em.createQuery("select u from User u where u.userid = :userId");
			// 파라미터 설정
			query.setParameter("userId", userId);
			try {
				// 결과 리턴
				return (User) query.getSingleResult();
			} catch (NoResultException e) {
				// 데이터가 없어서 에러가 발생하면 null를 리턴
				return null;
			}
		});
	}

	public void deleteAll() {
		transaction((em) -> {
			// 클래스를 데이터베이스와 매핑시킨다.
			
			List<User> list = selectAll();
			for(User brd : list)
			{
				em.detach(brd);
				// 그리고 update를 하고 삭제한다.
				em.remove(em.merge(brd));
			}
		
		});
	}

	
	/*
	public static BoardDao getInstance(){
		return bdao;
	}
	*/
}
