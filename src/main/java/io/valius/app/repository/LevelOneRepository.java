package io.valius.app.repository;

import io.valius.app.domain.LevelOne;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the LevelOne entity.
 */
@Repository
public interface LevelOneRepository extends JpaRepository<LevelOne, Long> {
    @Query("select levelOne from LevelOne levelOne where levelOne.user.login = ?#{principal.preferredUsername}")
    List<LevelOne> findByUserIsCurrentUser();

    default Optional<LevelOne> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<LevelOne> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<LevelOne> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct levelOne from LevelOne levelOne left join fetch levelOne.user",
        countQuery = "select count(distinct levelOne) from LevelOne levelOne"
    )
    Page<LevelOne> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct levelOne from LevelOne levelOne left join fetch levelOne.user")
    List<LevelOne> findAllWithToOneRelationships();

    @Query("select levelOne from LevelOne levelOne left join fetch levelOne.user where levelOne.id =:id")
    Optional<LevelOne> findOneWithToOneRelationships(@Param("id") Long id);
}
