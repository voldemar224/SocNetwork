public class FriendsList {

    private int[] friends;
    private int numberOfFriends;
    private int sizeOfArray;

    //creating a constructor of the class that initializes the values
    public FriendsList() {
        friends = new int[1];
        numberOfFriends = 0;
        sizeOfArray = 1;
    }
    public FriendsList(int[] existingFriends) {
        if (existingFriends.length == 0) new FriendsList();

        for (int i = 0; i<existingFriends.length; i++) {
            for (int j = i + 1; j < existingFriends.length; j++) {
                if (existingFriends[i] == existingFriends[j]) throw new IllegalArgumentException("You can't add the same friend twice");
            }
        }

        friends = existingFriends;
        numberOfFriends = existingFriends.length;
        sizeOfArray = existingFriends.length;
    }

    public void addFriend(int friendId) {
        if (numberOfFriends == sizeOfArray) {
            growSize();
        }
        friends[numberOfFriends] = friendId;
        numberOfFriends++;
    }

    //function that creates an array of double size
    public void growSize() {
        int[] temp = new int[sizeOfArray * 2];
        {
            if (sizeOfArray >= 0) System.arraycopy(friends, 0, temp, 0, sizeOfArray);
        }

        friends = temp;
        sizeOfArray = sizeOfArray * 2;
    }
    public void shrinkSize() {
        int[] temp = null;
        if (numberOfFriends > 0) {
            temp = new int[numberOfFriends];
            System.arraycopy(friends, 0, temp, 0, numberOfFriends);
            sizeOfArray = numberOfFriends;
            friends = temp;
        }
    }

    public void removeElement() {
        if (numberOfFriends > 0) {
            friends[numberOfFriends - 1] = 0;
            numberOfFriends--;
        }
    }

    public void removeElementAt(int index) {
        if (numberOfFriends > 0) {
            for (int i = index; i < numberOfFriends - 1; i++) {
                friends[i] = friends[i + 1];
            }
            friends[numberOfFriends - 1] = 0;
            numberOfFriends--;
        }
    }
}
