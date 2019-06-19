package ${pkg};

import ${clazzNameWithPkg};
import java.util.List;
import com.github.pagehelper.PageHelper;
import ${serviceNameWithPkg};
import ${daoNameWithPkg};
import ${pageBeanWithPkg};
import ${pageParamWithPkg};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ${clazzName}ServiceImpl implements ${clazzName}Service {

    @Autowired
    private ${clazzName}Dao ${clazzName?uncap_first}Dao;

    @Override
    public int add${clazzName}(${clazzName} ${clazzName?uncap_first}){
        int res = ${clazzName?uncap_first}Dao.insert${clazzName}(${clazzName?uncap_first});
        return res;
    }

    @Override
    public int add${clazzName}Selective(${clazzName} ${clazzName?uncap_first}){
        int res = ${clazzName?uncap_first}Dao.insert${clazzName}Selective(${clazzName?uncap_first});
        return res;
    }

	@Override
    public int batchAdd${clazzName}(List<${clazzName}> ${clazzName?uncap_first}List){
		int res = ${clazzName?uncap_first}Dao.batchInsert${clazzName}(${clazzName?uncap_first}List);
		return res;
	}

    @Override
    public int delete${clazzName}By${pk?cap_first}(${pkType} ${pk}){
        int res = ${clazzName?uncap_first}Dao.delete${clazzName}By${pk?cap_first}(${pk});
        return res;
    }
    
 	@Override
	public int delete${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first}){
	 	int res = ${clazzName?uncap_first}Dao.delete${clazzName}ByCondition(${clazzName?uncap_first});
        return res;
	}
	
	@Override
	public int batchDelete${clazzName}By${pk?cap_first}List(List<${pkType}> ${pk?uncap_first}List){
		int res = ${clazzName?uncap_first}Dao.batchDelete${clazzName}By${pk?cap_first}List(${pk?uncap_first}List);
        return res;
	}
	
    @Override
    public int update${clazzName}(${clazzName} ${clazzName?uncap_first}){
        int res = ${clazzName?uncap_first}Dao.update${clazzName}(${clazzName?uncap_first});
        return res;
    }

    @Override
    public int update${clazzName}Selective(${clazzName} ${clazzName?uncap_first}){
        int res = ${clazzName?uncap_first}Dao.update${clazzName}Selective(${clazzName?uncap_first});
        return res;
    }

    @Override
    public long getCount(){
        long res = ${clazzName?uncap_first}Dao.selectCount();
        return res;
    }

    @Override
    public long getCountByCondition(${clazzName} ${clazzName?uncap_first}){
        long res = ${clazzName?uncap_first}Dao.selectCountByCondition(${clazzName?uncap_first});
        return res;
    }

    @Override
    public ${clazzName} get${clazzName}By${pk?cap_first}(${pkType} ${pk}){
        ${clazzName} res = ${clazzName?uncap_first}Dao.select${clazzName}By${pk?cap_first}(${pk});
        return res;
    }

    @Override
    public List<${clazzName}> getAll${clazzName}(){
        List<${clazzName}> res = ${clazzName?uncap_first}Dao.selectAll${clazzName}();
        return res;
    }

	@Override
	public 	List<${clazzName}> get${clazzName}ByCondition(${clazzName} ${clazzName?uncap_first}){
		List<${clazzName}> res = ${clazzName?uncap_first}Dao.select${clazzName}ByCondition(${clazzName?uncap_first});
		return res;
	}
	
    @Override
    public PageBean<${clazzName}> get${clazzName}WithPagination(PageParam pageParam){
        long count = ${clazzName?uncap_first}Dao.selectCount();
        PageBean<${clazzName}> pageBean = new PageBean<>(pageParam, (int)count);
        pageBean.setPageNum(pageParam.getPage());
        PageHelper.startPage(pageParam.getPage(),pageParam.getRows());
        List<${clazzName}> data = ${clazzName?uncap_first}Dao.selectAll${clazzName}();
        pageBean.setRecords(data);
        return pageBean;
    }

	@Override
	public PageBean<${clazzName}> get${clazzName}WithPaginationByCondition(PageParam pageParam, ${clazzName} ${clazzName?uncap_first}){
        long count = ${clazzName?uncap_first}Dao.selectCountByCondition(${clazzName?uncap_first});
        PageBean<${clazzName}> pageBean = new PageBean<>(pageParam, (int)count);
        pageBean.setPageNum(pageParam.getPage());
        PageHelper.startPage(pageParam.getPage(),pageParam.getRows());
		List<${clazzName}> data = ${clazzName?uncap_first}Dao.select${clazzName}ByCondition(${clazzName?uncap_first});
		pageBean.setRecords(data);
        return pageBean;
	}

    <#list fkItemList as item>
    //////////////////////////// 外键相关 /////////////////////////////////////
    <#if item.fk?index_of("_")!=-1>
    @Override
    public List<${clazzName}> get${clazzName}By${item.fk?substring(0,item.fk?last_index_of("_"))?cap_first}(${item.fkType?substring(item.fkType?last_index_of(".")+1)} ${item.fk?substring(0,item.fk?last_index_of("_"))}){
        List<${clazzName}> res = ${clazzName?uncap_first}Dao.select${clazzName}By${item.fk?substring(0,item.fk?last_index_of("_"))?cap_first}(${item.fk?substring(0,item.fk?last_index_of("_"))});
        return res;
    }
    <#else>
    @Override
    public List<${clazzName}> get${clazzName}By${item.fk?cap_first}(${item.fkType?substring(item.fkType?last_index_of(".")+1)} ${item.fk}){
        List<${clazzName}> res = ${clazzName?uncap_first}Dao.select${clazzName}By${item.fk?cap_first}(${item.fk});
        return res;
    }
    </#if>

    </#list>
}