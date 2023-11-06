package io.valius.app.repository;

import io.valius.app.domain.LevelThree;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelThree entity.
 */
@Repository
public interface LevelThreeRepository extends JpaRepository<LevelThree, Long> {
    @Query("select levelThree from LevelThree levelThree where levelThree.user.login = ?#{principal.preferredUsername}")
    List<LevelThree> findByUserIsCurrentUser();

    default Optional<LevelThree> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LevelThree> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LevelThree> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct levelThree from LevelThree levelThree left join fetch levelThree.user",
        countQuery = "select count(distinct levelThree) from LevelThree levelThree"
    )
    Page<LevelThree> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct levelThree from LevelThree levelThree left join fetch levelThree.user")
    List<LevelThree> findAllWithToOneRelationships();

    @Query("select levelThree from LevelThree levelThree left join fetch levelThree.user where levelThree.id =:id")
    Optional<LevelThree> findOneWithToOneRelationships(@Param("id") Long id);
}
