package ir.piana.dev.core.api.repository;

import ir.piana.dev.core.api.entity.PianaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Mohamad Rahmati (rahmatii1366@gmail.com)
 * Date: 7/11/2019 5:16 PM
 **/
@NoRepositoryBean
public interface PianaEntityRepository<T extends PianaEntity, ID>
        extends PagingAndSortingRepository<T, ID>, JpaRepository<T, ID> {
}
