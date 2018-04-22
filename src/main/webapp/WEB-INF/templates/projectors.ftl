<#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
<#assign c=JspTaglibs["http://www.springframework.org/security/tags"]>
<@sf.form action="/projector/create" method="post" modelAttribute="projectorForm">
    <@sf.input path="name" type="text" placeholder="Введите название"/>
    <@sf.errors path="name" cssClass="help-block"/>
<input type="submit" value="Создать">
</@sf.form>
<#if projectors?has_content>
    <#list projectors as projector>
    <a href="/projector/${projector.getId()}">${projector.getName()}</a>
    <form action="/projector/delete/${projector.getId()}" method="post">
        <input type="submit">
    </form>
    </#list>
<#else>
Нет проекторов
</#if>