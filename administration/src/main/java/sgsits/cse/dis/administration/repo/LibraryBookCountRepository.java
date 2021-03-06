package sgsits.cse.dis.administration.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import sgsits.cse.dis.administration.model.LibraryBookCount;

@Repository
public interface LibraryBookCountRepository extends JpaRepository<LibraryBookCount, Long> {

	List<LibraryBookCount> findBySubjectCategory(String subjectCategory);

	
	@Modifying
	@Query(value = "UPDATE library_book_count SET count = count + 1 WHERE subject_category = ?1", nativeQuery = true)
	void updateCount(String subjectCategory);

}
