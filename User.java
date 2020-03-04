import java.util.*;

public class User {
    int uniqueID;
    //Used in disjointSet class to keep track of parent nodes and size of individual sets
    int compId;
    ArrayList<User> friends = new ArrayList<>();
    Profile prof;

    public User(Profile p, int id){
        this.prof = p;
        this.uniqueID = id;
        this.compId = -1;
    }

    public int getUniqueID() {
        return this.uniqueID;
    }
    public ArrayList<User> getFriends() {
        return friends;
    }
    public Profile getProf() {
        return this.prof;
    }

    @Override
    public boolean equals(Object obj) {
        User castedObj = (User) obj;
        return this.getUniqueID() == castedObj.getUniqueID() && this.getProf().equals(castedObj.getProf());
    }

	@Override
	public String toString() {
		return prof.getName();
	}
    
    
}
