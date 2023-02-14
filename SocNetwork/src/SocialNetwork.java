import java.util.*;

public class SocialNetwork {
    private ArrayList<SocialNetworkMember> members = new ArrayList<>();

    public void addMember(SocialNetworkMember member) {
        members.add(member);
    }

    private int getReceiversQuantityVar1(int senderId) {
        Set<Integer> receiversId = new HashSet<>();

        for (int friendId: getMemberById(senderId).getFriends()) {
            receiversId.add(friendId);
            SocialNetworkMember friend = getMemberById(friendId);
            for (int friendOfFriend : friend.getFriends())
                receiversId.add(friendOfFriend);
        }
        return receiversId.size() - 1;
    }


    private SocialNetworkMember getMemberById(int friendsOfFriend) {
        for (SocialNetworkMember member: members) {
            if (member.getId() == friendsOfFriend)
                return member;
        }
        return null;
    }

    public int getReceiversQuantityVar2(int senderId) {
        UnionFind uf = unionMembers();

        return uf.findComponentSize(senderId) - 1;
    }

    private UnionFind unionMembers() {
        int membersQuantity = members.size();

        UnionFind uf = new UnionFind(membersQuantity);
        for (SocialNetworkMember member: members) {
            for (int friendId: member.getFriends()) {
                uf.union(member.getId(), friendId);
            }
        }
        return uf;
    }
    protected boolean isAbleSendMessageVar2(SocialNetworkMember sender, SocialNetworkMember receiver) {
        UnionFind uf = unionMembers();

        return uf.find(sender.getId()) == uf.find(receiver.getId());
    }
}



