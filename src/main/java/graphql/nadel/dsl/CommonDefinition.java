package graphql.nadel.dsl;

import graphql.Assert;
import graphql.Internal;
import graphql.language.AbstractNode;
import graphql.language.Comment;
import graphql.language.IgnoredChars;
import graphql.language.Node;
import graphql.language.NodeBuilder;
import graphql.language.NodeChildrenContainer;
import graphql.language.NodeVisitor;
import graphql.language.SDLDefinition;
import graphql.language.SourceLocation;
import graphql.util.TraversalControl;
import graphql.util.TraverserContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * todo 想知道干啥用的，看 1. 咋构建的；2. 咋使用的；
 * // 通用定义 = common{ 类型系统定义 | 类型系统拓展 } # 两个都是 graphql-java里边的
 * commonDefinition: 'common' '{' (typeSystemDefinition|typeSystemExtension)* '}';
 */
@Internal
public class CommonDefinition extends AbstractNode<CommonDefinition> {

    // sdl定义列表
    private List<SDLDefinition> typeDefinitions;

    private CommonDefinition(List<SDLDefinition> typeDefinitions,
                             SourceLocation sourceLocation,
                             List<Comment> comments,
                             IgnoredChars ignoredChars,
                             Map<String, String> additionalData) {
        super(sourceLocation, comments, ignoredChars, additionalData);
        this.typeDefinitions = typeDefinitions;
    }

    @Override
    public List<Node> getChildren() {
        return null;
    }

    @Override
    public NodeChildrenContainer getNamedChildren() {
        return null;
    }

    @Override
    public CommonDefinition withNewChildren(NodeChildrenContainer newChildren) {
        return null;
    }

    @Override
    public boolean isEqualTo(Node node) {
        return false;
    }

    @Override
    public CommonDefinition deepCopy() {
        return null;
    }

    @Override
    public TraversalControl accept(TraverserContext<Node> context, NodeVisitor visitor) {
        return null;
    }

    public List<SDLDefinition> getTypeDefinitions() {
        return new ArrayList<>(typeDefinitions);
    }

    public static Builder newCommonDefinition() {
        return new Builder();
    }

    public static class Builder implements NodeBuilder {

        // 注释
        private List<Comment> comments = new ArrayList<>();
        // 资源位置
        private SourceLocation sourceLocation;
        // 类型定义列表
        private List<SDLDefinition> typeDefinitions = new ArrayList<>();
        // 忽略的字符
        private IgnoredChars ignoredChars = IgnoredChars.EMPTY;
        // 额外的数据
        private Map<String, String> additionalData = new LinkedHashMap<>();

        private Builder() { }

        public Builder comments(List<Comment> comments) {
            this.comments = comments;
            return this;
        }

        @Override
        public NodeBuilder ignoredChars(IgnoredChars ignoredChars) {
            this.ignoredChars = ignoredChars;
            return this;
        }

        public Builder sourceLocation(SourceLocation sourceLocation) {
            this.sourceLocation = sourceLocation;
            return this;
        }

        public Builder typeDefinitions(List<SDLDefinition> typeDefinitions) {
            this.typeDefinitions.clear();
            this.typeDefinitions.addAll(typeDefinitions);
            return this;
        }

        public Builder addTypeDefinitions(List<SDLDefinition> typeDefinitions) {
            this.typeDefinitions.addAll(typeDefinitions);
            return this;
        }

        public Builder additionalData(Map<String, String> additionalData) {
            this.additionalData = Assert.assertNotNull(additionalData);
            return this;
        }

        public Builder additionalData(String key, String value) {
            this.additionalData.put(key, value);
            return this;
        }

        public CommonDefinition build() {
            return new CommonDefinition(typeDefinitions, sourceLocation, comments, ignoredChars, additionalData);
        }
    }
}
