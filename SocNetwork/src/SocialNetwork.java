import java.util.*;

public class SocialNetwork {
    private ArrayList<SocialNetworkMember> members = new ArrayList<>();
    private ArrayList<Message> messages = new ArrayList<>();
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public void addMember(SocialNetworkMember member) {
        members.add(member);
    }
    public void sendMessage(Message message) {
        int membersGotMessageQuantity = 0;
        for(SocialNetworkMember receiver: message.getReceiver()) {
            if (isAbleSendMessage(message.getSender(), receiver)) {
                membersGotMessageQuantity++;
                coutSentSuccessfully(message, receiver);
                messages.add(message);
            }
            else {
                coutSendingFailed(message, receiver);
            }
        }
        System.out.println(membersGotMessageQuantity + " messages was sent");
        System.out.println();
    }
    private class UnionFind {
        private int[] parents;
        private int[] size;

        public UnionFind(int n) {
            parents = new int[n];
            size = new int[n];
            for (int i = 0; i < parents.length; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        public int find(int cur) {
            int root = cur;
            while (root != parents[root]) {
                root = parents[root];
            }
            // Path Compression
            while (cur != root) {
                int preParent = parents[cur];
                parents[cur] = root;
                cur = preParent;
            }
            return root;
        }

        public void union(int node1, int node2) {
            int node1Parent = find(node1);
            int node2Parent = find(node2);

            if (node1Parent == node2Parent)
                return;

            if (size[node1Parent] > size[node2Parent]) {
                parents[node2Parent] = node1Parent;
                size[node1Parent] += size[node2Parent];
            } else {
                parents[node1Parent] = node2Parent;
                size[node2Parent] += size[node1Parent];
            }
        }
    }
    private boolean isAbleSendMessage(SocialNetworkMember sender, SocialNetworkMember receiver) {
        int membersQuantity = members.size();

        UnionFind uf = new UnionFind(membersQuantity);
        for (SocialNetworkMember member: members) {
            for (int friendId: member.getFriends()) {
                uf.union(member.getId(), friendId);
            }
        }
        return uf.find(sender.getId()) == uf.find(receiver.getId());
    }

    private void coutSentSuccessfully(Message message, SocialNetworkMember receiver) {
        System.out.println("Message was sent from " + message.getSender().getName() + " to " + receiver.getName());
        System.out.println("Message: " + message.getMessageBody());
        System.out.println("When: " + message.getSentAt());
        System.out.println();
    }
    private void coutSendingFailed(Message message, SocialNetworkMember receiver) {
        System.out.println("Message wasn't sent from " + message.getSender().getName() + " to " + receiver.getName());
        System.out.println("You can't send message to this person");
        System.out.println();
    }
}
