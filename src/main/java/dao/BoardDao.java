package dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Board;

public class BoardDao extends AbstractDao<Board> {
	// 생성자 재정의 protected에서 public으로 바꾸고 파라미터를 재설정한다.
	//private static BoardDao bdao = new BoardDao();
	
	public BoardDao() {
		// protected 생성자 호출
		super(Board.class);
	}

	// 모든 데이터 취득
	@SuppressWarnings("unchecked")
	public List<Board> selectAll() {
		// AbstractDao 추상 클래스의 transaction 함수를 사용한다.
		return super.transaction((em) -> {
			// 쿼리를 만든다. (실무에서는 createQuery가 아닌 createNamedQuery를 사용해서 Entity에서 쿼리를 일괄 관리한다.)
			return (List<Board>) em.createNamedQuery("Board.findAll").getResultList();
		});
	}

	// Id에 의한 데이터를 취득한다.
	public Board selectById(int bno) {
		// AbstractDao 추상 클래스의 transaction 함수를 사용한다.
		return super.transaction((em) -> {
			// 쿼리를 만든다. (실무에서는 createQuery가 아닌 createNamedQuery를 사용해서 Entity에서 쿼리를 일괄 관리한다.)
			Query query = em.createQuery("select b from Board b where b.bno = :bno");
			// 파라미터 설정
			query.setParameter("bno", bno);
			try {
				// 결과 리턴
				return (Board) query.getSingleResult();
			} catch (NoResultException e) {
				// 데이터가 없어서 에러가 발생하면 null를 리턴
				return null;
			}
		});
	}

	public Board selectBtitle(String btitle) {
		// AbstractDao 추상 클래스의 transaction 함수를 사용한다.
		return super.transaction((em) -> {
			// 쿼리를 만든다. (실무에서는 createQuery가 아닌 createNamedQuery를 사용해서 Entity에서 쿼리를 일괄 관리한다.)
			Query query = em.createQuery("select b from Boards b where b.btitle = :btitle");
			// 파라미터 설정
			query.setParameter("btitle", btitle);
			try {
				// 결과 리턴
				return (Board) query.getSingleResult();
			} catch (NoResultException e) {
				// 데이터가 없어서 에러가 발생하면 null를 리턴
				return null;
			}
		});
	}

	public void deleteAll() {
		transaction((em) -> {
			// 클래스를 데이터베이스와 매핑시킨다.
			
			List<Board> list = selectAll();
			for(Board brd : list)
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
