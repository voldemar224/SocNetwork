import java.util.*;

public class SocialNetwork {
    private ArrayList<SocialNetworkMember> members = new ArrayList<>();
    public void addMember(SocialNetworkMember member) {
        members.add(member);
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
    public boolean isAbleSendMessage(SocialNetworkMember sender, SocialNetworkMember receiver) {
        int membersQuantity = members.size();

        UnionFind uf = new UnionFind(membersQuantity);
        for (SocialNetworkMember member: members) {
            for (int friendId: member.getFriends()) {
                uf.union(member.getId(), friendId);
            }
        }
        return uf.find(sender.getId()) == uf.find(receiver.getId());
    }
}