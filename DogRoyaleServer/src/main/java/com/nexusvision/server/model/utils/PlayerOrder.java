package com.nexusvision.server.model.utils;

import com.nexusvision.server.model.enums.OrderType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author felixwr
 */
@Data
public class PlayerOrder {
    private OrderType type;
    private List<PlayerElement> order;

    /**
     * The index of <code>clientId</code> in the <code>order</code> list, correlating to the order position
     *
     * @param clientId The clientId to search for
     * @return The index in the <code>order</code> list
     */
    public int find(Integer clientId) {
        for (int i = 0; i < order.size(); i++) {
            if (Objects.equals(clientId, order.get(i).getClientId())) return i;
        }
        return -1;
    }

    public void removeClientId(Integer clientId) {
        order.removeIf(orderElement -> Objects.equals(clientId, orderElement.getClientId()));
    }

    public List<Integer> getClientIdList() {
        List<Integer> clientIdList = new ArrayList<>();
        for (PlayerElement orderElement : order) {
            int clientId = orderElement.getClientId();
            if (clientId != -1) clientIdList.add(clientId);
        }
        return clientIdList;
    }
}
