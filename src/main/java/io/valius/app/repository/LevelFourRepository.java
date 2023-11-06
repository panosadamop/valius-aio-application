package io.valius.app.repository;

import io.valius.app.domain.LevelFour;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelFour entity.
 */
@Repository
public interface LevelFourRepository extends JpaRepository<LevelFour, Long> {
    @Query("select levelFour from LevelFour levelFour where levelFour.user.login = ?#{principal.preferredUsername}")
    List<LevelFour> findByUserIsCurrentUser();

    default Optional<LevelFour> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LevelFour> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LevelFour> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct levelFour from LevelFour levelFour left join fetch levelFour.user",
        countQuery = "select count(distinct levelFour) from LevelFour levelFour"
    )
    Page<LevelFour> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct levelFour from LevelFour levelFour left join fetch levelFour.user")
    List<LevelFour> findAllWithToOneRelationships();

    @Query("select levelFour from LevelFour levelFour left join fetch levelFour.user where levelFour.id =:id")
    Optional<LevelFour> findOneWithToOneRelationships(@Param("id") Long id);
}
