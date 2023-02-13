import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Simulation {
    private final int MEMBERS_QUANTITY = 10_000;
    private final int GROUP_A_SIZE = 50, QUANTITY_FRIENDS_GROUP_A = 10;
    private final int GROUP_B_SIZE = 30, QUANTITY_FRIENDS_GROUP_B = 20;
    private final int GROUP_C_SIZE = 15, QUANTITY_FRIENDS_GROUP_C = 40;
    private final int GROUP_D_SIZE = 5, QUANTITY_FRIENDS_GROUP_D = 80;
    private SocialNetwork socNet;

    public SocialNetworkSimulationMember[] getMembers() {
        return members;
    }

    private SocialNetworkSimulationMember[] members = new SocialNetworkSimulationMember[MEMBERS_QUANTITY];
    public class SocialNetworkSimulationMember extends SocialNetworkMember {
        SimulationGroup group = SimulationGroup.FREE;
        public SimulationGroup getGroup() {
            return group;
        }

        public String print() {
            return String.valueOf(id) +  ' ' + friends + ' ' + group;
        }
    }
    private final Map<SimulationGroup, GroupCharacteristics> groupCharacteristicsMap =
            new HashMap<SimulationGroup, GroupCharacteristics>() {{
        put(SimulationGroup.A, new GroupCharacteristics(GROUP_A_SIZE, QUANTITY_FRIENDS_GROUP_A));
        put(SimulationGroup.B, new GroupCharacteristics(GROUP_B_SIZE, QUANTITY_FRIENDS_GROUP_B));
        put(SimulationGroup.C, new GroupCharacteristics(GROUP_C_SIZE, QUANTITY_FRIENDS_GROUP_C));
        put(SimulationGroup.D, new GroupCharacteristics(GROUP_D_SIZE, QUANTITY_FRIENDS_GROUP_D));
    }};

    private enum SimulationGroup {
        A, B, C, D, FREE
    }
    private class GroupCharacteristics {
        final int groupSize, quantityFriendsGroupX;
        private GroupCharacteristics(int groupSize, int quantityFriendsGroupX) {
            this.groupSize = groupSize;
            this.quantityFriendsGroupX = quantityFriendsGroupX;
        }
    }
    public void simulationProcess() {
        createUsers();
        //timeSimulation();
    }

    private void createUsers() {
        fillSocialNetwork();
        fillGroupWithFriends(SimulationGroup.A);
        fillGroupWithFriends(SimulationGroup.B);
        fillGroupWithFriends(SimulationGroup.C);
        fillGroupWithFriends(SimulationGroup.D);
    }

    private void fillSocialNetwork() {
        for(int i = 0; i<MEMBERS_QUANTITY; i++)
            members[i] = new SocialNetworkSimulationMember();
        assignGroupToMembers();
    }

    private void assignGroupToMembers() {
        AtomicInteger counter = new AtomicInteger(0);
        assignMembersToCertainGroup(counter, GROUP_A_SIZE, SimulationGroup.A);
        assignMembersToCertainGroup(counter, GROUP_B_SIZE, SimulationGroup.B);
        assignMembersToCertainGroup(counter, GROUP_C_SIZE, SimulationGroup.C);
        assignMembersToCertainGroup(counter, GROUP_D_SIZE, SimulationGroup.D);
    }

    private void assignMembersToCertainGroup(AtomicInteger counter, int GROUP_X_SIZE, SimulationGroup group) {
        for(int i=0; i<GROUP_X_SIZE; i++, counter.incrementAndGet())
            members[counter.get()].group = group;
    }

    private void fillGroupWithFriends(SimulationGroup group) {
        int indexMemberOfGroupX = 0;
        while (members[indexMemberOfGroupX].getGroup() != group)
            indexMemberOfGroupX++;

        for (int i = indexMemberOfGroupX; i < indexMemberOfGroupX+groupCharacteristicsMap.get(group).groupSize; i++) {
                findFriendsForCertainMember(members[i]);
        }
    }

    private void findFriendsForCertainMember(SocialNetworkSimulationMember groupMember) {
        for (int j = 0; j<groupCharacteristicsMap.get(groupMember.getGroup()).quantityFriendsGroupX; j++) {
            SocialNetworkSimulationMember randomMember = generateRandomMember();

            while(!isPossibleToConnect(groupMember, randomMember))
                randomMember = generateRandomMember();

            groupMember.addFriend(randomMember);
            randomMember.addFriend(groupMember);
        }
    }
    private SocialNetworkSimulationMember generateRandomMember() {
        int randomMemberId = ThreadLocalRandom.current().nextInt(0, MEMBERS_QUANTITY);
        return members[randomMemberId];
    }
    private boolean isPossibleToConnect(SocialNetworkSimulationMember groupMember, SocialNetworkSimulationMember randomMember) {
        if (randomMember.getGroup() == SimulationGroup.FREE)
            return true;

        if (randomMember.getQuantityFriends() < groupCharacteristicsMap.get(randomMember.getGroup()).quantityFriendsGroupX)
            return true;

        return false;
    }
}
