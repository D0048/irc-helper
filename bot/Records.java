package bot;

import java.util.HashMap;
import java.util.HashSet;

public class Records {
	public static HashSet<String> verifiedUsers = new HashSet<String>();
	public static HashSet<String> mutedUsers = new HashSet<String>();
	public static HashMap<String, HashSet<String>> records = new HashMap<String, HashSet<String>>();
}
