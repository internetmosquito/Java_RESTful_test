package user;

public class PairOfIdsToDistance{
    private String fromToIds;
    private Double distance;

    public PairOfIdsToDistance(String fromToIds, double distance) {
        this.fromToIds = fromToIds;
        this.distance = distance;
    }

    public String getFromToIds() {
        return fromToIds;
    }

    public void setFromToIds(String fromToIds) {
        this.fromToIds = fromToIds;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
