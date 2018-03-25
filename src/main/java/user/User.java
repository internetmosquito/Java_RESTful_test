package user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
public class User {

    @Id
    private int userId;
    private String firstName;
    private String lastName;
    private Double latitude;
    private Double longitude;

    public User(){
    }

    public User(String firstName, String lastName, Double latitude, Double longitude) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public User(int userId, String firstName, String lastName, Double latitude, Double longitude) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFirstName() { return this.firstName; }
    public String getLastName() { return this.lastName; }
    public Double getLatitude() { return this.latitude; }
    public Double getLongitude() { return this.longitude; }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return this.firstName;
    }
}
