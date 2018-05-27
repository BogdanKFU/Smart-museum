<#include "base.ftl">
<#macro title>Добавление пользователя</#macro>
<#macro content>
    <#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
    <#assign c=JspTaglibs["http://www.springframework.org/security/tags"]>
<div class="container-fluid">
    <div class="panel panel-default edit-profile-form">
        <div class="panel panel-heading"><p align="center">Добавить нового пользователя</p></div>
        <div class="panel panel-body">
            <@sf.form action= "/admin/users/registration" method="post" modelAttribute="u">
                <div class="row">
                    <div class="col-md-3 profile-info">
                    </div>
                    <div class="col-md-6 profile-info form-group" style="margin-top: -15px">

                        <div class="input-group input-elem">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                            <@sf.input path="login" type="text" class="form-control" placeholder="Введите логин" value="${u.login!''}"/>
                        </div>
                        <div>&nbsp;<@sf.errors path="login" cssClass="help-block-edit-form"/></div>
                        <div class="input-group input-elem">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-ok-circle"></span></span>
                            <@sf.input path="surName" type="text" class="form-control" placeholder="Введите фамилию" value="${u.surName!''}"/>
                        </div>
                        <div>&nbsp;<@sf.errors path="surName" cssClass="help-block-edit-form"/></div>
                        <div class="input-group input-elem">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-ok-circle"></span></span>
                            <@sf.input path="name" class="form-control" type="text" placeholder="Введите имя" value="${u.name!''}"/>
                        </div>
                        <div>&nbsp;<@sf.errors path="name" cssClass="help-block-edit-form"/></div>
                        <div class="input-group input-elem">
                            <span class="input-group-addon"><span class="glyphicon glyphicon-ok-circle"></span></span>
                            <@sf.input path="thirdName" class="form-control" placeholder="Введите отчество" value="${u.thirdName!''}"/>
                        </div>
                        <div>&nbsp;<@sf.errors path="thirdName" cssClass="help-block-edit-form"/></div>
                        <div class="input-group input-elem">
                            <#if positions?has_content>
                            <div class="form-group col-12">
                                <select id="boot-select" name="position">
                                    <#list positions as position>
                                        <option value="${position.id}">${position.name}</option>
                                    </#list>
                                </select>
                            </div>
                            </#if>
                        </div>
                        <div>&nbsp;<@sf.errors path="position" cssClass="help-block-edit-form"/></div>
                        <div><input class="btn btn-default" type="submit" value="Добавить"></div>
                    </div>
                </div>

            </@sf.form>
        </div>
    </div>


</div>
</#macro>

<script type="text/javascript">
    $(document).ready(function() {
        $('#boot-select').multiselect({
            buttonWidth: 300,
            dropRight: true,
            nonSelectedText: 'Выбрать',
            maxHeight: 150
        });
    });
</script>