package app.adapter.out.order.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderEntityRepository extends CrudRepository<OrderEntity, Long> {

    @Override
    List<OrderEntity> findAll();
}
