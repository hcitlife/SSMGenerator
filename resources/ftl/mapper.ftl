<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${DaoInterfaceNameWithPkg}" >
    <resultMap id="BaseResultMap" type="${EntityNameWithPkg}" >
        <#list itemList as item>
            <#if item.pk>
        <id column="${item.fieldName}" property="${item.propertyName}" jdbcType="${item.sqlType}" />
			<#else>
        <result column="${item.fieldName}" property="${item.propertyName}" jdbcType="${item.sqlType}"  />
			</#if>
		</#list>
    </resultMap>

    <sql id="Base_Column_List" >
        <#list itemList as item>${item.fieldName}<#if item_has_next>,</#if></#list>
    </sql>

    <#list itemList as item>
		<#if item.pk>
			<#assign pkPropertyType="${item.javaType}"/>
			<#assign pkFieldName="${item.fieldName}"/>
			<#assign pkPropertyName="${item.propertyName}"/>
			<#assign pkSqlType="${item.sqlType}"/>
		</#if>
	</#list>

    <!--==========================insert===============================-->
    <insert id="insert${clazzName}" parameterType="${EntityNameWithPkg}" >
        insert into ${tableName}
        (<#list itemList as item>${item.fieldName}<#if item_has_next>,</#if></#list>)
        values
        (<#list itemList as item>${'#{'}${item.propertyName},jdbcType=${item.sqlType}}<#if item_has_next>,</#if></#list>)
    </insert>

    <insert id="insert${clazzName}Selective" parameterType="${EntityNameWithPkg}" >
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides="," >
            <#list itemList as item>
            <if test="${item.propertyName} != null" >
                ${item.fieldName},
            </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides="," >
            <#list itemList as item>
            <if test="${item.propertyName} != null" >
                ${'#{'}${item.propertyName},jdbcType=${item.sqlType}},
            </if>
            </#list>
        </trim>
    </insert>

    <insert id="batchInsert${clazzName}" parameterType="${EntityNameWithPkg}" >
        insert into ${tableName}
        (<#list itemList as item>${item.fieldName}<#if item_has_next>,</#if></#list>)
        values
        <foreach collection ="list" item="item" index= "index" separator =",">
            (<#list itemList as item>${'#{'}item.${item.propertyName},jdbcType=${item.sqlType}}<#if item_has_next>,</#if></#list>)
        </foreach >
    </insert>

    <!--==========================delete===============================-->
    <delete id="delete${clazzName}By${pkPropertyName?cap_first}" parameterType="${pkPropertyType}" >
        delete from ${tableName}
        where ${pkFieldName} = ${'#{'}${pkPropertyName},jdbcType=${pkSqlType}}
    </delete>

    <delete id="delete${clazzName}ByCondition" parameterType="${EntityNameWithPkg}" >
        delete from ${tableName}
        <where>
            <#list itemList as item>
            <if test="${item.propertyName} != null" >
                and ${item.fieldName} = ${'#{'}${item.propertyName}}
            </if>
            </#list>
        </where>
    </delete>

   <delete id="batchDelete${clazzName}By${pkPropertyName?cap_first}List" parameterType="java.util.List" >
        delete from ${tableName}
        where ${pkFieldName} in
        <foreach collection="list" index="index" item="item" open="(" separator="," close=")">
            ${'#'}{item}
        </foreach>
    </delete>

    <!--==========================update===============================-->
    <update id="update${clazzName}" parameterType="${EntityNameWithPkg}" >
        update ${tableName}
        set
        <#list itemList as item>
        ${item.fieldName} = ${'#{'}${item.propertyName},jdbcType=${item.sqlType}}<#if item_has_next>,</#if>
        </#list>
        where ${pkFieldName} = ${'#{'}${pkPropertyName},jdbcType=${pkSqlType}}
    </update>

    <update id="update${clazzName}Selective" parameterType="${EntityNameWithPkg}" >
        update ${tableName}
        <set >
            <#list itemList as item>
              <if test="${item.propertyName} != null" >
                ${item.fieldName} =  ${'#{'}${item.propertyName},jdbcType=${item.sqlType}},
              </if>
            </#list>
        </set>
        where ${pkFieldName} = ${'#{'}${pkPropertyName},jdbcType=${pkSqlType}}
    </update>

    <!--==========================select===============================-->
    <select id="selectCount" resultType="java.lang.Long">
        select count(*)
        from ${tableName}
    </select>

    <select id="selectCountByCondition" resultType="java.lang.Long" parameterType="${EntityNameWithPkg}">
        select count(*)
        from ${tableName}
        <where>
            <#list itemList as item>
            <if test="${item.propertyName} != null" >
                and ${item.fieldName} = ${'#{'}${item.propertyName}}
            </if>
            </#list>
        </where>
    </select>

    <select id="select${clazzName}By${pkPropertyName?cap_first}" resultMap="BaseResultMap" parameterType="${pkPropertyType}" >
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        where ${pkFieldName} = ${'#{'}${pkPropertyName},jdbcType=${pkSqlType}}
    </select>

    <select id="selectAll${clazzName}" resultMap="BaseResultMap">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
    </select>

    <select id="select${clazzName}ByCondition" resultMap="BaseResultMap"  parameterType="${pkPropertyType}">
        select
        <include refid="Base_Column_List" />
        from ${tableName}
        <where>
            <#list itemList as item>
            <if test="${item.propertyName} != null" >
                and ${item.fieldName} = ${'#{'}${item.propertyName}}
            </if>
            </#list>
        </where>
    </select>

   <#list fkItemList as item>
   <!--========================== 外键相关 ===============================-->
         <#if item.fk?index_of("_")!=-1>
   <select id="select${clazzName}By${item.fk?substring(0,item.fk?last_index_of("_"))?cap_first}" resultMap="BaseResultMap"  parameterType="${item.fkType}">
      select
      <include refid="Base_Column_List" />
      from ${tableName}
      where ${item.fk} = ${'#{'}${item.fk?substring(0,item.fk?last_index_of("_"))}}
   </select>
        <#else>
   <select id="select${clazzName}By${item.fk?cap_first}" resultMap="BaseResultMap"  parameterType="${item.fkType}">
      select
      <include refid="Base_Column_List" />
      from ${tableName}
      where ${item.fk} = ${'#{'}${item.fk}}
   </select>
        </#if>
   </#list>
</mapper>