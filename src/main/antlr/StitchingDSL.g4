// 语法名称
grammar StitchingDSL;
// 导入其他语法
import GraphqlSDL;

// 在运行脚本后，生成的类中自动带上这个包路径，避免了手动加入的麻烦
@header {
    package graphql.nadel.parser.antlr;
}

// 缝合定义 = 通用定义 + 服务定义
stitchingDSL:
   commonDefinition? serviceDefinition+ ;

// 通用定义 = common{ 类型系统定义 | 类型系统拓展 } # 两个都是 graphql-java里边的
commonDefinition: 'common' '{' (typeSystemDefinition|typeSystemExtension)* '}';

// 服务定义 = service ServiceName { (类型系统定义 | 类型系统拓展)* }
// todo 用不用改成 +
serviceDefinition:
   SERVICE name '{' (typeSystemDefinition|typeSystemExtension)* '}' ;

// 对象类型定义： 描述-可选； type 基本类型/自定义类型 implements 指令
objectTypeDefinition : description? TYPE name implementsInterfaces? directives? typeTransformation?  fieldsDefinition? ;

/**
* 接口类型定义：
**/
interfaceTypeDefinition : description? INTERFACE name implementsInterfaces? directives? typeTransformation? fieldsDefinition?;

/**
* union 类型
**/
unionTypeDefinition : description? UNION name directives? typeTransformation? unionMembership?;

/**
* 输入类型
**/
inputObjectTypeDefinition : description? INPUT name directives? typeTransformation? inputObjectValueDefinitions?;

/**
* 枚举类型
**/
enumTypeDefinition : description? ENUM name directives? typeTransformation? enumValueDefinitions?;

/**
* scalar
**/
scalarTypeDefinition : description? SCALAR name directives? typeTransformation?;

/**
* 字段定义
**/
fieldDefinition : description? name argumentsDefinition? ':' type directives? addFieldInfo? fieldTransformation?;

/**
* 默认批处理大小
**/
addFieldInfo: defaultBatchSize;
defaultBatchSize:'default batch size' intValue;
fieldTransformation : '=>' (fieldMappingDefinition | underlyingServiceHydration);

// 类型转换器： =>，修改名称？
typeTransformation : '=>' typeMappingDefinition;

//
// renames
//
typeMappingDefinition : 'renamed from' name;

fieldMappingDefinition : 'renamed from' name ('.'name)?;

//
// hydration
underlyingServiceHydration: 'hydrated from' serviceName '.' (syntheticField '.')? topLevelField remoteCallDefinition? objectIdentifier? batchSize?;

objectIdentifier: 'object identified by' name;

batchSize: 'batch size ' intValue;

remoteArgumentSource :  sourceObjectReference | fieldArgumentReference | contextArgumentReference;

// 远程调用定义： ( 远程参数对 )
remoteCallDefinition : '(' remoteArgumentPair+ ')' ;

remoteArgumentPair : name ':' remoteArgumentSource ;

sourceObjectReference : '$source' '.' name ('.'name)*;

fieldArgumentReference : '$argument' '.' name ;

contextArgumentReference : '$context' '.' name ;

// int 值
intValue: IntValue;

// 服务名称
serviceName: NAME;

// 顶级字段
topLevelField: NAME | SERVICE;

// 合成字段
syntheticField: NAME;


baseName: NAME | FRAGMENT | QUERY | MUTATION | SUBSCRIPTION | SCHEMA | SCALAR | TYPE | INTERFACE | IMPLEMENTS | ENUM | UNION | INPUT | EXTEND | DIRECTIVE | SERVICE;

SERVICE: 'service';
