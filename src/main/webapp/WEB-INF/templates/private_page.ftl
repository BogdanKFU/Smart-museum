<#include "base.ftl">
<#macro title>Личная страница</#macro>
<#macro content>
<div class="page-header col-md-12 col-xs-12">
    <div class="col-md-10 col-xs-10">
        <h2><b>Личная страница</b></h2>
    </div>
</div>
<div class="col-md-12 col-xs-12">
    <div class="row">

        <div class="col-xs-12 col-sm-4 text">
<#if u.photo?has_content>
    <div><img class="user-photo" src="/image/${u.photo!}" onerror="this.src='/resources/static/img/image_unavailable.jpg'"></div>
    <#else>
            <div><img src="/resources/static/img/image_unavailable.jpg" class="img-responsive img-circle center-block"></div>
    </#if>
        </div>
        <div class="col-xs-12 col-sm-offset-1  col-sm-6 text">
            <div class="col-sm-offset-6 button">
                <a type="button" class="btn btn-info btn-lg" href="<#if authorities?seq_contains("ROLE_ADMIN")>/admin/users/${u.id}/edit<#else>/profile/edit</#if>"><span class="glyphicon glyphicon-pencil"></span>
                    Редактировать профиль
                </a>
            </div>
            <table class="table user-data ">
                <tbody>
                <tr>
                    <td class="active"><b>Фамилия</b></td>
                    <td>${u.surname}</td>
                </tr>
                <tr>
                    <td class="active"><b>Имя</b></td>
                    <td>${u.name}</td>
                </tr>
                <tr>
                    <td class="active"><b>Отчество</b></td>
                    <td>${u.thirdName}</td>
                </tr>
                <tr>
                    <td class="active"><b>Роль</b></td>
                    <td>${u.role.name}</td>
                </tr>
                <tr>
                    <td class="active"><b>Должность</b></td>
                    <td>${u.position.name}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>
</div>
    <script>
        $(document).ready(function () {
            $("li.active").removeClass('active');
            var role = document.getElementById("role").value;
            if (role != 'ADMIN'){
                $("#profile").addClass('active');
            }

        })
    </script>
</#macro>