import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class TestSocialNetwork {
	public static void main(String[] args) throws FileNotFoundException {

		SocialNetwork sn = new SocialNetwork();
		ArrayList<String> errors = new ArrayList<String>();
		ArrayList<String> output = new ArrayList<String>();

		String testFileName = args[0];
		runTest(sn, testFileName, errors, output);
	}

	private static void runTest(SocialNetwork sn, String fileName, ArrayList<String> errors, ArrayList<String> output) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner scanner = new Scanner(file);
		int lineNumber = 0;
		while (scanner.hasNextLine()) {
			lineNumber++;
			String line = scanner.nextLine();
			if(line.startsWith("#")) {
				continue;
			}
			String[] tokens = line.split(",");
			if ("AddUser".equals(tokens[0])) {
				runAddUser(sn, line, lineNumber, tokens, errors, output);
				continue;
			}
			if ("AddFriend".equals(tokens[0])) {
				runAddFriend(sn, line, lineNumber, tokens, errors, output);
				continue;
			}
			if ("TestFriend".equals(tokens[0])) {
				runTestFriend(sn, line, lineNumber, tokens, errors, output);
				continue;
			}
			if ("TestShortestPath".equals(tokens[0])) {
				runTestShortestPath(sn, line, lineNumber, tokens, errors, output);
				continue;
			}
		}
		scanner.close();
		
		// now print the results
		if (output.size() > 0) {
			System.out.println("Results of test execution. Output:\n");
			Iterator<String> itr = output.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
		}
		// now print the errors
		if (errors.size() > 0) {
			System.out.println("\n\nResults of test execution. Errors:\n");
			Iterator<String> itr = errors.iterator();
			while(itr.hasNext()) {
				System.out.println(itr.next());
			}
		}
	}
	
	private  static void runAddUser(SocialNetwork sn, String line, Integer lineNum, 
			String[] tokens, ArrayList<String> errors, ArrayList<String> output) {
		// we expect the line to be: AddUser,<name>,<age>
		if (tokens.length != 3) {
			errors.add("Line#: " + lineNum
					+ "; Wrong number of parameters for AddUser. Expected 3 in the form of AddUser,<name>,<age>"
					+ ", but got " + tokens.length + "; Input line: " + line);
			return;
		}

		String userName = tokens[1];
		if (sn.isUser(userName)) {
			errors.add("Line#: " + lineNum + "; User already exists. Bad AddUser Call. User:" + userName
					+ ";  Input line: " + line);
			return;
		}
		Integer age = Integer.valueOf(tokens[2]);
		User user1 = sn.addUser(new Profile(userName, age));
		output.add("Line#: " + lineNum + "; User:" + user1 + " is added to the network;  Input line: " + line);
	}
	
	private  static void runAddFriend(SocialNetwork sn, String line, Integer lineNumber, 
			String[] tokens, ArrayList<String> errors, ArrayList<String> output) {
		// we expect the line to be: AddFriend,<name1>,<name2>
		if (tokens.length != 3) {
			errors.add("Line#: " + lineNumber
					+ "; Wrong number of parameters for AddFriend. Expected 3 in the form of AddFriend,<user1>,<User2>"
					+ ", but got " + tokens.length + "; Input line: " + line);
			return;
		}

		String user1 = tokens[1];
		String user2 = tokens[2];
		
		if (!sn.isUser(user1)) {
			errors.add("Line#: " + lineNumber + "; User doesn't exist. Bad AddFriend Call. User:" + user1
					+ ";  Input line: " + line);
			return;
		}
		if (!sn.isUser(user2)) {
			errors.add("Line#: " + lineNumber + "; User doesn't exist. Bad AddFriend Call. User:" + user2
					+ ";  Input line: " + line);
			return;
		}
		if (user1.equals(user2)) {
			errors.add("Line#: " + lineNumber + "; Bad AddFriend Call. Both users are same. User:" + user2
					+ ";  Input line: " + line);
			return;
		}
		sn.createFriendShip(user1, user2);
		output.add("Line#: " + lineNumber + "; Created friendship between user:" + user1 + " and user:" + user2 + " in the network;  Input line: " + line);
	}
	
	private  static void runTestFriend(SocialNetwork sn, String line, Integer lineNumber, 
			String[] tokens, ArrayList<String> errors, ArrayList<String> output) {
		// we expect the line to be: TestFriend,<name1>,<name2>
		if (tokens.length != 3) {
			errors.add("Line#: " + lineNumber
					+ "; Wrong number of parameters for TestFriend. Expected 3 in the form of TestFriend,<user1>,<User2>"
					+ ", but got " + tokens.length + "; Input line: " + line);
			return;
		}

		String user1 = tokens[1];
		String user2 = tokens[2];
		if (!sn.isUser(user1)) {
			errors.add("Line#: " + lineNumber + "; User doesn't exist. Bad TestFriend Call. User:" + user1
					+ ";  Input line: " + line);
			return;
		}
		if (!sn.isUser(user2)) {
			errors.add("Line#: " + lineNumber + "; User doesn't exist. Bad TestFriend Call. User:" + user2
					+ ";  Input line: " + line);
			return;
		}
		if(sn.shortestPath(user1, user2) == null) {
			output.add("Line#: " + lineNumber + "; User1:" + user1 + " and User2:" + user2
					+ " are NOT friends;  Input line: " + line);
		} else {
			output.add("Line#: " + lineNumber + "; User1:" + user1 + " and User2:" + user2
					+ " are friends;  Input line: " + line);
		}
	}
	
	private  static void runTestShortestPath(SocialNetwork sn, String line, Integer lineNumber, 
			String[] tokens, ArrayList<String> errors, ArrayList<String> output) {
		// we expect the line to be: TestFriend,<name1>,<name2>
		if (tokens.length != 3) {
			errors.add("Line#: " + lineNumber
					+ "; Wrong number of parameters for TestShortestPath. Expected 3 in the form of TestShortestPath,<user1>,<User2>"
					+ ", but got " + tokens.length + "; Input line: " + line);
			return;
		}

		String user1 = tokens[1];
		String user2 = tokens[2];
		if (!sn.isUser(user1)) {
			errors.add("Line#: " + lineNumber + "; User doesn't exist. Bad TestShortestPath Call. User:" + user1
					+ ";  Input line: " + line);
			return;
		}
		if (!sn.isUser(user2)) {
			errors.add("Line#: " + lineNumber + "; User doesn't exist. Bad TestShortestPath Call. User:" + user2
					+ ";  Input line: " + line);
			return;
		}
		ArrayList<User> list = sn.shortestPath(user1, user2);
		if(list == null) {
			output.add("Line#: " + lineNumber + "; User1:" + user1 + " and User2:" + user2
					+ " are NOT friends and there is NO shortest path;  Input line: " + line);
		} else {
			output.add("Line#: " + lineNumber + "; User1:" + user1 + " and User2:" + user2
					+ " are friends;  Shortest Path: " + list + "; Input line: " + line);
		}
	}
}
