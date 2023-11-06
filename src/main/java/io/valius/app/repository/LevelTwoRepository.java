package io.valius.app.repository;

import io.valius.app.domain.LevelTwo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelTwo entity.
 */
@Repository
public interface LevelTwoRepository extends JpaRepository<LevelTwo, Long> {
    @Query("select levelTwo from LevelTwo levelTwo where levelTwo.user.login = ?#{principal.preferredUsername}")
    List<LevelTwo> findByUserIsCurrentUser();

    default Optional<LevelTwo> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LevelTwo> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LevelTwo> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct levelTwo from LevelTwo levelTwo left join fetch levelTwo.user",
        countQuery = "select count(distinct levelTwo) from LevelTwo levelTwo"
    )
    Page<LevelTwo> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct levelTwo from LevelTwo levelTwo left join fetch levelTwo.user")
    List<LevelTwo> findAllWithToOneRelationships();

    @Query("select levelTwo from LevelTwo levelTwo left join fetch levelTwo.user where levelTwo.id =:id")
    Optional<LevelTwo> findOneWithToOneRelationships(@Param("id") Long id);
}
