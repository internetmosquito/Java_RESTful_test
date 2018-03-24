package user;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @RequestMapping(method = RequestMethod.GET, value="/jrt/api/v1.0/users")
    public Collection<User> users() {
        /**
         * Update this to return a json stream defining a listing of the users
         * Note: Always return the appropriate response for the action requested.
         *
         */
        //TODO: Implement this
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value="/jrt/api/v1.0/user")
    public ResponseEntity<?> get_user(@PathVariable String userId) {
        //TODO: Implement this
        return null;
    }

    @RequestMapping(method = RequestMethod.POST, value="/jrt/api/v1.0/user")
    public ResponseEntity<?> add_user(@RequestBody User input) {
        /**
         * Should add a new user to the users collection, with validation
         * note: Always return the appropriate response for the action requested.
         */
        //TODO: Implement this
        return null;
    }

    @RequestMapping(method = RequestMethod.PUT, value="/jrt/api/v1.0/user")
    public ResponseEntity<?> update_user(@PathVariable String userId, @RequestBody User input) {
        /**
         * Update user specified with user ID and return updated user contents
         * Note: Always return the appropriate response for the action requested.
         */
        //TODO: Implement this
        return null;
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/jrt/api/v1.0/user")
    public ResponseEntity<?> delete_user(@PathVariable String userId) {
        /**
         * Delete user specified with user ID and return updated user contents
         * Note: Always return the appropriate response for the action requested.
         */
        //TODO: Implement this
        return null;
    }


    @RequestMapping(method = RequestMethod.GET, value="/jrt/api/v1.0/distances")
    public String distances() {
        /**
         * Each user has a lat/lon associated with them.  Determine the distance
         * between each user pair, and provide the min/max/average/std as a json response.
         * This should be GET only.
         *
         */
        //TODO: Implement this
        return null;
    }
}
