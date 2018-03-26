package user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import javax.validation.constraints.Size;

@Document(collection = "users")
public class User {

    private static final int MAX_LENGTH_NAME = 30;
    private static final int MIN_LENGTH_NAME = 2;
    private static final int INTEGERS_IN_GEOPOSITION = 5;
    private static final int DECIMALS_IN_GEOPOSITION = 20;


    @Id
    private int userId;

    @NotNull
    @NotBlank
    @Size(min=MIN_LENGTH_NAME, max=MAX_LENGTH_NAME)
    private String firstName;

    @NotNull
    @NotBlank
    @Size(min=MIN_LENGTH_NAME, max=MAX_LENGTH_NAME)
    private String lastName;

    @Digits(integer=INTEGERS_IN_GEOPOSITION,fraction=DECIMALS_IN_GEOPOSITION)
    private Double latitude;

    @Digits(integer=INTEGERS_IN_GEOPOSITION,fraction=DECIMALS_IN_GEOPOSITION)
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
