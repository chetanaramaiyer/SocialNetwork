import java.util.*;
/**
 *   DisjointSet of User objects backed by ArrayList<User>. Represents the
 *   friend and mutual friend connections between users.
 *
 *   1. Index of each User's object is equal to that User's uniqueId.
 *   2. Each User has a compId: index of that user's parent node.
 *   3. The root node's compId: negative size of its disjoint component.
 *   We use path compression when connecting two User's to reduce
 *   the size of each disjoint component.
 *
 * */
public class DisjointSet {

    public ArrayList<User> arr;

    /* Creates a UnionFind data structure. Initially, all vertices are in disjoint sets. */
    public DisjointSet() {
        arr = new ArrayList<>();
    }

    /* Throws an exception if personIdVertex is not a valid index. */
    private void validate(int personIdVertex) {
        if (personIdVertex < 0)
            throw new IllegalArgumentException("Not a valid index");
    }

    /* Returns the size of the set personIdVertex belongs to. */
    public int sizeOf(int personIdVertex) {
        validate(personIdVertex);
        User root = find(personIdVertex);
        int rootIndex = root.getUniqueID();

        //Multiplying by -1 because root's compId is neg size of its disjointSet
        return -1 * arr.get(rootIndex).compId;
    }

    /* Returns the parent of personIdVertex. If personIdVertex is the root of a tree, returns the
       negative size of the tree for which personIdVertex is the root. */
    public int parent(int personIdVertex) {
        validate(personIdVertex);
        return arr.get(personIdVertex).compId;
    }

    /* Returns true if nodes personIdVertexOne and personIdVertexTwo are connected. */
    public boolean connected(int personIdVertexOne, int personIdVertexTwo) {
        validate(personIdVertexOne);
        validate(personIdVertexTwo);

        User rootOne = find(personIdVertexOne);
        User rootTwo = find(personIdVertexTwo);

        //When they are in separate disjoint sets
        if(!rootOne.equals(rootTwo) && rootOne.compId < 0 && rootTwo.compId < 0){
            return false;
        }
        return rootOne.compId == rootTwo.compId;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a
       vertex with itself or vertices that are already connected should not
       change the sets but may alter the internal structure of the data. */
    public void union(User v1, User v2) {
        validate(v1.getUniqueID());
        validate(v2.getUniqueID());

        if(v1.equals(v2)){
            arr.add(v1.getUniqueID(), v1);
            return;
        }
        if (connected(v1.getUniqueID(), v2.getUniqueID())) {
            return;
        }
        //get size of v1's disjoint set
        int sizeOne = sizeOf(v1.getUniqueID());
        //get size of v2's disjoint set
        int sizeTwo = sizeOf(v2.getUniqueID());

        User v1Root = find(v1.getUniqueID());
        User v2Root = find(v2.getUniqueID());

        if (sizeTwo >= sizeOne) {
            //Setting v1 root's compId to be the index of v2's root
            arr.get(v1Root.getUniqueID()).compId = v2Root.getUniqueID();
            //Increasing v2 root's compId to include the size of v1
            //Subtracting because holds the negative size
            arr.get(v2Root.getUniqueID()).compId -= sizeOne;

            return;
        }
        //Setting v2 root's compId to be the index of v1's root
        arr.get(v2Root.getUniqueID()).compId = v1Root.getUniqueID();
        //Increasing v1 root's compId to include the size of v2
        arr.get(v1Root.getUniqueID()).compId -= sizeTwo;
    }

    /* Returns the root of the set that user with uniqueId belongs to. Path-compression is employed
       allowing for fast search-time. */
    public User find(int uniqueId) {
        validate(uniqueId);
        int parentCompId = parent(uniqueId);
        if (parentCompId > 0) {
            arr.get(uniqueId).compId = find(parentCompId).getUniqueID();
        } else if (arr.get(uniqueId).compId < 0) {
            return arr.get(uniqueId);
        }
        return arr.get(arr.get(uniqueId).compId);
    }
}
