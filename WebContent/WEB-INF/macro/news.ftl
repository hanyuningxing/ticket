<#-- 导入JSTL的C标签 -->
<#macro index_list_news news>
        <li>
                <span><#if news??&&news.createTime??>${news.createTime?string("yy-MM-dd")}</#if></span>
                <a <#if news??&&news.titleColor??>class="${news.titleColor.colorStyle}"</#if> href="<#if news??&&news.titleLink??>${news.titleLink}<#else>${base}/info/news!info.action?id=${news.id!}</#if>" target="_blank">
                 ${news.titleString!}
                </a>
        </li>
</#macro>

<#macro index_top_news news>
        <a <#if news??&&news.titleColor??>class="${news.titleColor.colorStyle}"</#if>  href="<#if news??&&news.titleLink??>${news.titleLink}<#else>${base}/info/news!info.action?id=${news.id!}</#if>" target="_blank">${news.titleString!}</a>
</#macro>

<#macro index_top_news_other news>
         <li>
                <a <#if news??&&news.titleColor??>class="${news.titleColor.colorStyle}"</#if> href="<#if news??&&news.titleLink??>${news.titleLink}<#else>${base}/info/news!info.action?id=${news.id!}</#if>" target="_blank">
                 ${news.titleString!}
                </a>
         </li>
</#macro>

<#macro news_list news>
       <li> <span class="listrig"><#if news??&&news.createTime??>${news.createTime?string("yy-MM-dd hh:mm")}</#if></span><a <#if news??&&news.titleColor??>class="${news.titleColor.colorStyle}"</#if> href="<#if news??&&news.titleLink??>${news.titleLink}<#else>${base}/info/news!info.action?id=${news.id!}</#if>" target="_blank">${news.titleString!}</a> </li>
</#macro>

 