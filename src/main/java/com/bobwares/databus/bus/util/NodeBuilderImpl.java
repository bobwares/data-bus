package com.bobwares.databus.bus.util;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.model.ResultsModel;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NodeBuilderImpl implements NodeBuilder {

    @Override
    public Map<String, Object> build(List<ResourceMessage> resourceMessages) {
        Map<String,Object> currentNode = new HashMap<>();
        for (ResourceMessage resourceMessage : resourceMessages) {
            String[] nodes = resourceMessage.getResourceUri().split("/");
            currentNode = buildHierarchy(currentNode, resourceMessage, nodes);
        }
        return currentNode;
    }

    @SuppressWarnings("unchecked")
	private Map<String, Object> buildHierarchy(Map<String, Object> currentNode, ResourceMessage resourceMessage, String[] nodes) {
        if (nodes.length == 1) {
            ResponseObject responseObject = resourceMessage.getResponseObject();
            ResultsModel resultsModel =  responseObject.getResultsModel();
            List<Map<String, Object>> items = resultsModel.getItems();

            String nodeName = nodes[0];
            if (resourceMessage.getResourceConfiguration().getParentNode() != null) {
                nodeName = resourceMessage.getResourceConfiguration().getParentNode();
                currentNode.put(nodeName, items);
            } else {
                for (Map<String, Object> itemMap : items) {
                	currentNode.putAll(itemMap);
                }
            }
        } else {
            Map<String, Object> newNode = (Map<String, Object>) currentNode.get(nodes[0]);

            if (newNode == null) {
                newNode = new HashMap<>();
                currentNode.put(nodes[0], newNode);
            }

            String[] newNodes = Arrays.copyOfRange(nodes,1, nodes.length);
            buildHierarchy(newNode, resourceMessage, newNodes);
        }
        return currentNode;
    }
}
