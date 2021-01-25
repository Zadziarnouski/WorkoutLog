package by.zadziarnouski.workoutlog.repository;


import by.zadziarnouski.workoutlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

    @Query(value = "from User o where o.role=0")
    List<User> findAllWithTheUserRole();
}