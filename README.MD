Welcome to the Java RESTful API test
====================================


The objetive of this test if to help us evalute your skills with:

* Problem Solving
* Web Server API Design
* Request-time data manipulation
* Testing strategies
* Deployment

**Instructions**

* Fork the repo into a private repo.
* You will need Gradle
* You can import the build.gradle file directly on your preferred IDE
* Spring Boot should be used to complete the test, although, if you feel you want to use something different, feel free
* Implement the required API endpoints
* Let us know when you have finished.

**Tasks**

The idea here is for us to see how you design a minimalistic API. This API will be
used to perform CRUD operations on a model called User.

You're free to design this model as you want, but, at a minimum should have:

* ID
* First name
* Last name
* Latitude
* Longitude

Check the UserController file, there you'll see you have to fulfill the following empty edges:

```java
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
```

This should be self-explanatory, we want you to fulfill the missing logic to generate expected
responses.

It is important that you return correct response codes.

**Notes**

* The boilerplate code assumes you will use Spring.io and Spring Boot
* You must use Docker to provide working solution, feel free to use just a Docker file or docker-compose.yml
* We will test the app using Docker only, keep this in mind
* Indicate what testing approach are you using...TDD, write code and test later, mocking, and tell us how to test your code
* You already have a test folder for this with some boilerplate code
* Keep in mind we expect some level of authentication, not everyone should be allowed to access endpoints, keep it simple
  if you want to keep authenticated user in session cache that's ok
* Using MongoDB is required to store and retrieve data.
* For the distances endpoint, feel free to use any approach, there are several ways to do this
* Add a method to delete a User too

**Questions**

* About the distances endpoint, please explain how would you scale this to hundreds and thousands of petitions per second,
considering this is a CPU intensive endpoint.
* How would you approach this if latitude and longitude of user would be changing very frequently as well?


**Deliverable**

Publish your work in a GitHub repository. Feel free to modify this readme to give any additional information a reviewer might need.
If you need more than 1 day to do this, you might be overthinking, feel free to add improvement notes in your README file, show-off there,
we prefer better quality code if it takes longer, but you must justify this.


**Improvement Notes**
* Currently the application does not have profiles (dev, test, prod) to be able to deploy/test the application accordingly.
With the profile definitions will  be good to modify port of the application to use, log level, mongo utl, etc.
* Also some load testing will be good maybe with jmeter to be able to see how much the application can scale or possible buttle necks.
* Profiling the application can be also important now that it is running.
* With the profiles in place, it would be handy to provide the specific profile as a property to the docker file and then to the spring boot application
this will allow the app to behave differently.
* Maybe some code conventions will be useful in big project to standarize things a bit, it brings benefits, obviously define pipelines to make sure code is always running, code reviews, etc.
* Also maybe to have the infrastructure in place for the docker hub to store images and be able to pull them constantly.

**Answers**
* Answering the questions, I guess we will have a phone call, mut what I did was basically split the functionality in 3 main parts.
1. Create all possible combinations of the users. This allows me to have in memory all different combinations of User -> User to I can calculate the distance
2. Iterate the previous Set of pairs to calculate the distance and store the distance result
3. Based on the distance now I can do all the simple maths for the average, min, max, etc.

This approach allows me for example to use some sort of map reduce mechanism, where I can for instance, generate an independent micro service to calculare the distance (assuming this is the most CPU intensive calculation), then I can use the results from 1 and invoke that service, since that service is independent, I can scale that up as much as I can to parelalize things and split the load among multiple machines if needed, then collect all the results and reduce that to be able to do the average, min, max, etc.


* Normally I dont focus on 100% Test coverage, not really useful to cover setters and getters for instance unless they do something specific.
I rather focus on business logic coverage as high as possible. Saying that, I am totally flexible, if the company requires 100% code coverage I would not have any issue with that.
