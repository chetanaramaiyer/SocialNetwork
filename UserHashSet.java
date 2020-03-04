import java.util.HashSet;

/** Using a new version of UserHashSet to override HashSet's contains method*/
public class UserHashSet extends HashSet<User> {
    public boolean containsUser(User a){
        for(User i : this){
            if(i.equals(a)){
                return true;
            }
        }
        return false;
    }
}
