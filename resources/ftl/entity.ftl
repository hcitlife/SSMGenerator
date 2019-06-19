package ${pkg};

<#list importList as importItem>
import ${importItem};
</#list>

public class ${clazzName} {
    <#list itemList as property>
    <#if "${property.fieldComment}"?default("")?trim?length gt 0>
    /**
    * ${property.fieldComment}
    */
    </#if>
    private ${property.javaType?substring(property.javaType?last_index_of(".")+1)} ${property.propertyName};
    </#list>

    public ${clazzName}(){
    }

    public ${clazzName}(<#list itemList as prop>${prop.javaType} ${prop.propertyName}<#if prop_has_next>, </#if></#list>){
        <#list itemList as prop>
         this.${prop.propertyName}=${prop.propertyName};
        </#list>
    }

    <#list itemList as prop>
    public ${prop.javaType} get${prop.propertyName?cap_first}(){
        return this.${prop.propertyName};
    }
    public void set${prop.propertyName?cap_first}(${prop.javaType} ${prop.propertyName}){
        this.${prop.propertyName} = ${prop.propertyName};
    }

    </#list>

    @Override
    public String toString() {
        return "{"+
                <#list itemList as prop>
                "<#if prop_index !=0>, </#if>\"${prop.propertyName}\":" +"\""+${prop.propertyName}+"\""+
                </#list>
                "}";
    }

    <#list fkItemList as item>
    //////////////////////////// 当前表的外键所对应表的实体类 /////////////////////////////////////
    private ${item.clazzName} ${item.clazzName?uncap_first};

    public ${item.clazzName} get${item.clazzName}(){
        return this.${item.clazzName?uncap_first};
    }

    public void set${item.clazzName}(${item.clazzName} ${item.clazzName?uncap_first}){
        this.${item.clazzName?uncap_first} = ${item.clazzName?uncap_first};
    }

    </#list>
}
