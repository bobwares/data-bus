package com.bobwares.databus.bus.service.endpoint;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.bus.model.ResponseMessage;
import com.bobwares.databus.bus.util.NodeBuilder;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

@Service
public class ResultsTransformer {

    NodeBuilder nodeBuilder;

    @Inject
    public void setNodeBuilder(NodeBuilder nodeBuilder) {
        this.nodeBuilder = nodeBuilder;
    }

    @Transformer(inputChannel="transformerChannel")
    public ResponseMessage transform(List<ResourceMessage> resourceMessages) {
        Map<String, Object> nodes = nodeBuilder.build(resourceMessages);
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setNodes(nodes);
        return responseMessage;
    }
}
