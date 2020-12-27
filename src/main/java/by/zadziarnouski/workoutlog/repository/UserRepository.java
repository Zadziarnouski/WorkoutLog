package by.zadziarnouski.workoutlog.repository;


import by.zadziarnouski.workoutlog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}