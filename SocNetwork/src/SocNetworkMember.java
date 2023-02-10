public class SocNetworkMember {
    static int newMemberId = 0;
    private int id;
    private FriendsList friends;
    private String name;

    public SocNetworkMember(int[] existingFriends, String newName){
        id = newMemberId;
        newMemberId++;

        friends = new FriendsList(existingFriends);
        name = newName;
    }
    public SocNetworkMember() {
        this(null, "user_" + newMemberId);
    }
    public SocNetworkMember(int[] existingFriends) {
        this(existingFriends, "user_" + newMemberId);
    }

    public SocNetworkMember(String newName) {
        this(null, newName);
    }

}
