package graphql.nadel.dsl;

import graphql.Internal;
import graphql.execution.MergedField;
import graphql.language.Node;

import java.util.Collections;
import java.util.List;

import static graphql.Assert.assertNotNull;
import static graphql.nadel.util.FpKit.map;

@Internal
public class NodeId {
    /**
     * Every AST node is given an id as additional data
     *
     * 每一个 ast节点 都给予了一个id作为额外数据。
     */
    public static final String ID = "id";

    public static String getId(Node<?> node) {
        return assertNotNull(node.getAdditionalData().get(ID), () -> String.format("expected node %s to have an id", node));
    }

    public static List<String> getIds(Node<?> node) {
        return Collections.singletonList(getId(node));
    }

    public static List<String> getIds(List<? extends Node<?>> nodes) {
        return map(nodes, NodeId::getId);
    }

    public static List<String> getIds(MergedField mergedField) {
        return map(mergedField.getFields(), NodeId::getId);
    }
}
