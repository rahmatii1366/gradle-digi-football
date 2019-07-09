package ir.piana.dev.user.business.data.repo;

import ir.piana.dev.user.business.data.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 6/23/2019 2:52 PM
 **/
@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>,
        JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
}
