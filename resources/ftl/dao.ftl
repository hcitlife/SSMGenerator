package ${pkg};

import ${clazzNameWithPkg};
import java.util.List;

public interface ${clazzName}Dao {

    int insert${clazzName}(${clazzName} ${clazzName?uncap_first});

    int insert${clazzName}Selective(${clazzName} ${clazzName?uncap_first});

	int batchInsert${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List);

    int delete${clazzName}By${pk?cap_first}(${pkType} ${pk});

	int delete${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first});

	int batchDelete${clazzName}By${pk?cap_first}List(List<${pkType}> ${pk?uncap_first}List);

    int update${clazzName}(${clazzName} ${clazzName?uncap_first});

    int update${clazzName}Selective(${clazzName} ${clazzName?uncap_first});

    long selectCount();
    
    long selectCountByCondition(${clazzName} ${clazzName?uncap_first});

    ${clazzName} select${clazzName}By${pk?cap_first}(${pkType} ${pk});

    List<${clazzName}> selectAll${clazzName}();

	List<${clazzName}> select${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first});

    <#list fkItemList as item>
    //////////////////////////// 外键相关 /////////////////////////////////////
        <#if item.fk?index_of("_")!=-1>
    List<${clazzName}> select${clazzName}By${item.fk?substring(0,item.fk?last_index_of("_"))?cap_first}(${item.fkType?substring(item.fkType?last_index_of(".")+1)} ${item.fk?substring(0,item.fk?last_index_of("_"))});
        <#else>
    List<${clazzName}> select${clazzName}By${item.fk?cap_first}(${item.fkType?substring(item.fkType?last_index_of(".")+1)} ${item.fk});
        </#if>

    </#list>
}