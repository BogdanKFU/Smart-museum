<#include "base.ftl">
<#macro title>
Список экспозиций
</#macro>
<#macro content>
 <div class="page-header col-md-12 col-xs-12">
     <div class="col-md-11 col-xs-11">
         <h2><b>Экспозиции</b></h2>
     </div>
    <@security.authorize access="hasRole('ADMIN')">
     <div class="col-md-1 col-xs-1">
         <div class="btn-group">
             <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                 <i class="glyphicon glyphicon-cog"></i>
             </button>
             <ul class="dropdown-menu" role="menu">
                 <li><a href="/expositions/add">Создать экспозицию</a></li>
                 <#--<li><a href="#">Включить все</a></li>-->
                 <#--<li><a href="#">Выключить все</a></li>-->
             </ul>
         </div>
     </div>
    </@security.authorize>
 </div>
<div class="col-md-12 col-xs-12">
    <form role="search" action="/expositions/search" method="get">
        <div class="input-group">
            <input name="searchField" type="text" class="form-control" placeholder="Найти экспозицию">
            <span class="input-group-btn">
    	                <button class="btn btn-default" type="submit">
	      	                <i class="glyphicon glyphicon-search"></i>
                        </button>
	                </span>
        </div>
    </form>
</div>
    <#if expositions?has_content>
<div class="col-md-12 table">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Название</th>
                    <#--<th>Состояние</th>-->

                    <th>Редактирование</th>
                    <@security.authorize access="hasRole('ADMIN')">
                    <th>Удаление</th>
                    </@security.authorize>
                </tr>
                </thead>
                <tbody>
<#list expositions as exposition>

<tr>
    <td><a href="/expositions/${exposition.id}">${exposition.name}</a></td>
    <#--<td>-->
        <#--<div class="btn-group" data-toggle="buttons">-->
            <#--<label class="btn  btn-default active">-->
                <#--<input type="radio" name="options" autocomplete="off" checked> Вкл-->
            <#--</label>-->
            <#--<label class="btn  btn-default">-->
                <#--<input type="radio" name="options" autocomplete="off"> Выкл-->
            <#--</label>-->
        <#--</div>-->
    <#--</td>-->

    <td>
        <a href="/expositions/${exposition.id}/edit" type="button" class="btn btn-info"><span
                class="glyphicon glyphicon-pencil"></span>
            Редактировать
        </a>
    </td>
    <@security.authorize access="hasRole('ADMIN')">
    <td>
        <a href="/expositions/${exposition.id}/delete" class="btn btn-danger" data-toggle="modal"><span
                class="glyphicon glyphicon-remove"></span>
            Удалить
        </a>
    </td>
    </@security.authorize>
</tr>
</#list>
                </tbody>
            </table>
    <#else>
    <h4>Экспозиций не найдено</h4>
</#if>
</#macro>

<script>
    $(document).ready(function () {
        $("li.active").removeClass('active');
        $("#expositions").addClass('active');
    })
</script>

