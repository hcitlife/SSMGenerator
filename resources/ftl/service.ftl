package ${pkg};

import ${clazzNameWithPkg};
import java.util.List;
import ${pageBeanWithPkg};
import ${pageParamWithPkg};

public interface ${clazzName}Service {
    int add${clazzName}(${clazzName} ${clazzName?uncap_first});

    int add${clazzName}Selective(${clazzName} ${clazzName?uncap_first});

    int batchAdd${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List);

    int delete${clazzName}By${pk?cap_first}(${pkType} ${pk});

	int delete${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first});

    int batchDelete${clazzName}By${pk?cap_first}List(List<${pkType}> ${pk?uncap_first}List);
	
    int update${clazzName}(${clazzName} ${clazzName?uncap_first});

    int update${clazzName}Selective(${clazzName} ${clazzName?uncap_first});

    long getCount();

    long getCountByCondition(${clazzName} ${clazzName?uncap_first});

    ${clazzName} get${clazzName}By${pk?cap_first}(${pkType} ${pk});

    List<${clazzName}> getAll${clazzName}();

	List<${clazzName}> get${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first});
	
    PageBean<${clazzName}> get${clazzName}WithPagination(PageParam pageParam);

	PageBean<${clazzName}> get${clazzName}WithPaginationByCondition(PageParam pageParam, ${clazzName} ${clazzName?uncap_first});

    <#list fkItemList as item>
    //////////////////////////// 外键相关 /////////////////////////////////////
    <#if item.fk?index_of("_")!=-1>
    List<${clazzName}> get${clazzName}By${item.fk?substring(0,item.fk?last_index_of("_"))?cap_first}(${item.fkType?substring(item.fkType?last_index_of(".")+1)} ${item.fk?substring(0,item.fk?last_index_of("_"))});
    <#else>
    List<${clazzName}> get${clazzName}By${item.fk?cap_first}(${item.fkType?substring(item.fkType?last_index_of(".")+1)} ${item.fk});
    </#if>

    </#list>
}