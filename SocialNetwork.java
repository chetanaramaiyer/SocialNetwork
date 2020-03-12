import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class SocialNetwork {

    //represents number of users in social network, used for each user's unique ID
    private static int personIdCounter = -1;
    //Storing the mutual friendship components, u1 and u2 are connected if the two users are friends
    DisjointSet disjointSet;
    HashMap<String, User> allUsers;

    /** Creating a social Network of n people */
    public SocialNetwork(){
        disjointSet = new DisjointSet();
        allUsers = new HashMap<String, User>();
    }
    /** Adds a user to the social network*/
    public synchronized User addUser(Profile p){
        if(p == null){
            return null;
        }
        int aId = generatePersonID(); // generating unique Iq for new user
        User a = new User(p, aId);
        this.disjointSet.union(a, a); // initially, new user is in its own disjoint component
        allUsers.put(p.getName(), a);
        return a;
    }
    /** Given a user object, checks if a user is part of the social network*/
    public boolean isUser(User a){
        if(a == null){
            return false;
        }
        return allUsers.get(a.prof.getName()) != null;
    }

    /** Given a user's name, checks if a user is part of the social network
     * THIS FUNCTION IS ONLY USED IN TESTS. Otherwise, must pass in User object
     * to check if two users are friends.
     * */
    public boolean isUser(String name){
        return allUsers.get(name) != null;
    }
    
    /** Given two user's names, creates a friendship between them in the social network
     * THIS FUNCTION IS ONLY USED IN TESTS. Otherwise, must pass in User object
     * to created friendship between two users
     **/
    public synchronized void createFriendShip(String a, String b){
    	createFriendShip(allUsers.get(a), allUsers.get(b));
    }

    /** Given two user objects, creates a friendship between them in the social network*/
    public synchronized void createFriendShip(User a, User b){
        if(a == null || b == null){
            return;
        }
    	if (a.equals(b)) {
    		// same user
    		return;
    	}
        disjointSet.union(a, b);
        a.getFriends().add(b);
        b.getFriends().add(a);
    }

    /** Checks if two users are friends in the social network*/
    public boolean isFriends(User a, User b){
        if(a == null || b == null){
            return false;
        }
        if(isUser(a) && isUser(b)) {
            ArrayList<User> aFriends = a.getFriends();
            ArrayList<User> bFriends = b.getFriends();
            // if both contain each other in their friend's list
            return aFriends.contains(b) && bFriends.contains(a);
        }
        return false;
    }
    /** Given two user's names, return shortest chain between them
    * USED ONLY FOR TESTING
    */
    public ArrayList<User> shortestPath(String a, String b){
    	return shortestPath(allUsers.get(a), allUsers.get(b));
    }

    /** Given two user objects, return shortest chain between them*/
    public ArrayList<User> shortestPath(User a, User b){
        if(a == null || b == null){
            return null;
        }
        if(a.equals(b)){
            return null;
        }
        if(disjointSet.connected(a.getUniqueID(),b.getUniqueID()) == false){
            return null;
        }
        User pred [] = new User [personIdCounter + 1];
        int dist [] = new int [personIdCounter + 1];
        BFS(a, b, personIdCounter, pred, dist);

        //Form chain of users in shortest path from pred[]
        ArrayList<User> sPath = new ArrayList<>();
        User curr = b;
        sPath.add(curr);
        while(pred[curr.getUniqueID()] != null){
            sPath.add(pred[curr.getUniqueID()]);
            curr = pred[curr.getUniqueID()];
        }

        return sPath;
    }

    /** BFS that stores predecessor of each vertex in array  and its distance from source in array d */
    public void BFS(User src, User dest, int num, User pred[], int dist[])
    {   
        if (src.equals(dest))
            return;
        // queue of vertices whose friends will be traversed as per BFS Algorithm
        Queue<User> queue = new LinkedList<User>();

        //stores whether ith vertex has been visited by BFS
        HashMap<User, Boolean> visited = new HashMap<User, Boolean>();

        // initially all vertices are unvisited, no path is yet constructed, infinite dist[i] for all i
        for (int i = 0; i < num; i++) {
            dist[i] = Integer.MAX_VALUE;
            pred[i] = null;
        }

        // source is first to be visited
        // distance from source to itself should be 0
        visited.put(src,true);
        dist[src.getUniqueID()] = 0;
        queue.add(src);

        // standard BFS algorithm
        while (!queue.isEmpty()) {
            User u = queue.poll();
            ArrayList<User> friends = u.getFriends();
            for (User v : friends) {
                if (!visited.containsKey(v)) {
                    visited.put(v, true);
                    dist[v.getUniqueID()] = dist[v.getUniqueID()] + 1;
                    pred[v.getUniqueID()] = u;
                    queue.add(v);

                    // We stop BFS when we find destination user
                    if (u.equals(dest))
                        return;
                }
            }
        }
    }

    /** Given two user's names, return length of the shortest chain between them
    * USED ONLY FOR TESTING
    */
    public int shortestPathLength(String a, String b){
    	return shortestPathLength(allUsers.get(a), allUsers.get(b));
    }

    /** Given two user objects, return the length of the shortest path
    * This is different than shortestPath function because we aren't saving
    * the shortest path in memory.
    */
    public int shortestPathLength(User a, User b){
        if(a == null || b == null){
            return 0;
        }
        if(a.equals(b)){
            return 0;
        }
        if(disjointSet.connected(a.getUniqueID(),b.getUniqueID()) == false){
            return 0;
        }
        User pred [] = new User [personIdCounter + 1];
        int dist [] = new int [personIdCounter + 1];
        BFS(a, b, personIdCounter, pred, dist);

        //Form chain of users in shortest path from pred[]
        int length = 0;
        User curr = b;
        
        while(pred[curr.getUniqueID()] != null){
            length++;
            curr = pred[curr.getUniqueID()];
        }

        return length;
    }

    public static synchronized int generatePersonID()
    {
        personIdCounter++;
        return personIdCounter;
    }
}
