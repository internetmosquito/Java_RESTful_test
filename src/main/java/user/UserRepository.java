package user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    @Query("{ 'userId' : ?0 }")
    User findUserByUserId(int userId);

    long countByLastName(String lastName);

    @Query(value="{ 'firstName' : ?0 }", fields="{ 'firstName' : 1, 'lastName' : 1}")
    List<User> findByTheUserFirstName(String firstName);
}
