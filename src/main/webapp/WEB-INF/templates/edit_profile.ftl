<#include "base.ftl">
<#macro title>Редактирование страницы</#macro>
<#macro content>
    <#assign sf=JspTaglibs["http://www.springframework.org/tags/form"]>
    <#assign c=JspTaglibs["http://www.springframework.org/security/tags"]>
    <#if error?has_content>
    <b style="color: red">Error: ${error}</b><br/>
    </#if>
    <#if u.role.name == "ADMIN">
        <#assign link = "/admin/users/${editForm.id}/edit_profile" >
    <#else>
        <#assign link = "/edit_profile">
    </#if>

    <div class="container-fluid">
        <div class="container">
            <div class="panel panel-default edit-profile-form" style="margin-bottom: 10px">
                <div class="panel panel-heading">Изменить личные данные</div>
                    <div class="panel panel-body">
                        <@sf.form action= "${link}" method="post" modelAttribute="editForm" enctype="multipart/form-data" cssStyle="margin-top: 0">
                            <div class="row">
                                <div class="col-md-6 profile-info">
                                    <#if editForm.photo?has_content>
                                        <@sf.input path="photo" type="text"  value="${editForm.photo}" hidden="true"/>
                                        <img class="user-photo" src="/image/${editForm.photo!''}" onerror="this.src='/resources/static/img/image_unavailable.jpg'">
                                    <#else>
                                        <@sf.input path="photo" type="text"  value="${editForm.photo}" hidden="true"/>
                                        <img class="user-photo" src="/resources/static/img/image_unavailable.jpg">
                                    </#if>
                                    <@sf.input path="photoFile" type="file" placeholder="Фото" cssStyle="padding-left: 25%; padding-right: 25%" />
                                    <@sf.errors path="photoFile" cssClass="help-block-edit-form"/>
                                </div>
                                <div class="col-md-6 profile-info form-group" style="margin-top: -15px">
                                    <div class="input-group input-elem">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-ok-circle"></span></span>
                                        <@sf.input path="surname" type="text" class="form-control" placeholder="Введите фамилию" value="${editForm.surname}"/>
                                    </div>
                                    <div>&nbsp;<@sf.errors path="surname" cssClass="help-block-edit-form"/></div>
                                    <div class="input-group input-elem">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-ok-circle"></span></span>
                                        <@sf.input path="name" class="form-control" type="text" placeholder="Введите имя" value="${editForm.name}"/>
                                    </div>
                                    <div>&nbsp;<@sf.errors path="name" cssClass="help-block-edit-form"/></div>
                                    <div class="input-group input-elem">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-ok-circle"></span></span>
                                        <@sf.input path="thirdName" class="form-control" placeholder="Введите отчество" value="${editForm.thirdName!''}"/>
                                    </div>
                                    <div>&nbsp;<@sf.errors path="thirdName" cssClass="help-block-edit-form"/></div>
                                    <#if u.role.name == "ADMIN">
                                        <div class="input-group input-elem" style="width: 100%">
                                                <div class="row">
                                                    <#if positions?has_content>
                                                        <div style="text-align: left" class="col-md-4 col-xs-4">Должность: </div>
                                                        <div class="col-md-8 col-xs-8">
                                                            <select class="form-control selectpicker" name="position">
                                                                <#list positions as position>
                                                                    <option value="${position.id}" <#if position.id == editForm.position>selected="selected"</#if>>${position.name}</option>
                                                                </#list>
                                                            </select>
                                                        </div>
                                                    </#if>
                                                </div>
                                                <div class="row" style="margin-top: 10px">
                                                    <#if roles?has_content>
                                                        <div class="col-md-4 col-xs-4" style="text-align: left; margin-top: 5px">Роль: </div>
                                                        <div class="col-md-8 col-xs-8">
                                                            <select class="form-control selectpicker" name="role">
                                                                <#list roles as role>
                                                                    <option value="${role.id}" <#if role.id == editForm.role>selected="selected"</#if>>${role.name}</option>
                                                                </#list>
                                                            </select>
                                                        </div>
                                                    </#if>
                                                </div>
                                        </div>
                                    </#if>
                                    <div class="input-group input-elem">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-user"></span></span>
                                            <@sf.input path="login" type="text" class="form-control" placeholder="Введите логин" value="${editForm.login}"/>
                                    </div>
                                    <div>&nbsp;<@sf.errors path="login" cssClass="help-block-edit-form"/></div>
                                    <div><input class="btn btn-default" type="submit" value="Сохранить"></div>
                                </div>
                            </div>
                        </@sf.form>
                    </div>
            </div>
            <div class="panel panel-default edit-profile-form" style="margin-top: 0">
                <div class="panel panel-heading" style="margin-bottom: 0">Изменить пароль</div>
                    <#if u.role.name == "ADMIN">
                        <#assign link2 = "/admin/users/${editForm.id}/change_password" >
                    <#else>
                        <#assign link2 = "/change_password">
                    </#if>
                    <@sf.form action= "${link2}" method="post" modelAttribute="changePasswordForm" cssStyle="margin-top: 0">
                        <div class="panel panel-body" style="margin-bottom: 0">
                            <div class="row">
                                <div class="col-md-4">
                                    <#if u.role.name != "ADMIN">
                                        <div class="input-group">
                                            <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                            <@sf.input path="currentPassword" class="form-control" type="password" placeholder="Введите текущий пароль"/></div>
                                        &nbsp;<@sf.errors path="currentPassword" cssClass="help-block-edit-form"/>
                                    </#if>
                                </div>
                                <div class="col-md-4">
                                    <div class="input-group">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                        <@sf.input path="newPassword" class="form-control" type="password" placeholder="Введите новый пароль"/></div>
                                    &nbsp;<@sf.errors path="newPassword" cssClass="help-block-edit-form"/>
                                </div>
                                <div class="col-md-4">
                                    <#if u.role.name != "ADMIN">
                                        <div class="input-group">
                                        <span class="input-group-addon"><span class="glyphicon glyphicon-lock"></span></span>
                                            <@sf.input path="newPasswordConf" class="form-control" type="password" placeholder="Введите новый пароль ещё раз"/></div>
                                        &nbsp;<@sf.errors path="newPasswordConf" cssClass="help-block-edit-form"/>
                                    </#if>
                                </div>
                            </div>
                        </div>
                        <div class="panel panel-body profile-info" style="margin-bottom: 0; padding-top: 0"><input class="btn btn-default" type="submit" value="Изменить"></div>
                    </@sf.form>
            </div>
        </div>
    </div>
</#macro>
<script>
    $(document).ready(function () {
        $("#profile").addClass('active');
    })
</script>

<script type="text/javascript">
    $(document).ready(function() {
        $('#boot-select').multiselect({
            buttonWidth: 300,
            dropRight: true,
            nonSelectedText: 'Выбрать',
            maxHeight: 150
        });
    });
    $(document).ready(function() {
        $('#boot-select2').multiselect({
            buttonWidth: 300,
            dropRight: true,
            nonSelectedText: 'Выбрать',
            maxHeight: 150
        });
    });
</script>
