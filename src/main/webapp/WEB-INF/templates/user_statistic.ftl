<#include "base.ftl">
<#macro title>Статистика</#macro>
<#macro content>
<div class="row" id="schedule-content">
    <div class="col-xs-1 col-md-1">
    </div>
    <div class="col-xs-10 col-md-10">
        <div class="panel-group">

                <div class="panel panel-default">
                    <div class="panel-heading">Опции</div>
                    <div class="panel-body">
                        <form class="schedule-form" action="/user_statistic" method="get">
                            <div class="row">
                                <div class="col-md-3 col-xs-3">
                                    <input class="form-control" type="text" name="searchField" placeholder="Поиск">
                                </div>
                                <div class="col-md-3 col-xs-3">
                                    <select multiple class="form-control selectpicker" id="users" name="users" size="6">
                                        <option value="" selected disabled>Пользователь</option>
                                        <#list users as u>
                                            <option value="${u.id}">${u.login}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="col-md-3 col-xs-3">
                                    <select multiple class="form-control selectpicker" size="6" id="actions" name="actions">
                                        <option value="" selected disabled>Действие</option>
                                        <#list actions as a>
                                            <option value="${a.id}">${a.name}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="col-md-3 col-xs-3">
                                    <select multiple class="form-control selectpicker" size="6" id="entities" name="entities">
                                        <option value="" selected disabled>Объект</option>
                                        <#list entities as e>
                                            <option value="${e.id}">${e.name}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="col-md-3 col-xs-3">
                                    <button class="btn btn-default" type="submit">Применить</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>

                <div class="panel panel-default" id="active-tasks-panel">
                    <div class="panel-heading">Статистика</div>

                        <div class="panel-body">
                            <div class="row" align="center">
                                <div class="col-md-2 col-xs-2"><b>Пользователь</b></div>
                                <div class="col-md-4 col-xs-4"><b>Дата</b></div>
                                <div class="col-md-2 col-xs-2"><b>Действие</b></div>
                                <div class="col-md-2 col-xs-2"><b>Объект</b></div>
                                <div class="col-md-2 col-xs-2"><b>Идентификатор</b></div>
                            </div>
                        </div>
                        <#list user_statistic as u>
                            <div class="panel-body">
                                <div class="row" align="center">
                                    <div class="col-md-2 col-xs-2"><a href="/admin/users/${u.user.id}" >${u.user.login}</a></div>
                                    <div class="col-md-4 col-xs-4">${u.datetime}</div>
                                    <div class="col-md-2 col-xs-2">${u.actionType.name}</div>
                                    <div class="col-md-2 col-xs-2">${u.tableName.name}</div>
                                    <#if u.link?has_content && u.actionType.name != 'Удаление'>
                                        <div class="col-md-2 col-xs-2"><a href="${u.link}${u.tableid}">${u.tableid}</a></div>
                                    <#else>
                                        <div class="col-md-2 col-xs-2">${u.tableid}</div>
                                    </#if>
                                </div>
                            </div>
                        </#list>
                        <div class="text-center">
                            <ul class = "pagination justify-content-center">
                                <li class="page-item <#if page == 0>disabled</#if>">
                                    <a class="page-link" <#if page != 0>href="/user_statistic/goToAnotherPage?page=0"</#if>>
                                        Первая
                                    </a>
                                </li>
                                <li class="page-item <#if (page-1) lt 0>disabled</#if>">
                                    <a class="page-link" <#if (page-1) gte 0>href="/user_statistic/goToAnotherPage?page=${page-1}"</#if> aria-label="Предыдущая">
                                        <span aria-hidden="true">«</span>
                                        <span class="sr-only">Предыдущая</span>
                                    </a>
                                </li>

                                <#list pages as p>
                                    <li class="page-item <#if page == p>active</#if>">
                                        <a class="page-link"  href="/user_statistic/goToAnotherPage?page=${p}">${p}</a>
                                    </li>
                                </#list>
                                <li class="page-item  <#if (page+1) gt lastPage>disabled</#if>">
                                    <a class="page-link" <#if (page+1) lte lastPage>href="/user_statistic/goToAnotherPage?page=${page+1}"</#if> aria-label="Следующая">
                                        <span aria-hidden="true">»</span>
                                        <span class="sr-only">Следующая</span>
                                    </a>
                                </li>
                                <li class="page-item <#if page == lastPage>disabled</#if>">
                                    <a class="page-link" <#if page != lastPage>href="/user_statistic/goToAnotherPage?page=${lastPage}"</#if>>
                                        Последняя
                                    </a>
                                </li>
                            </ul>
                        </div>

                </div>
        </div>
    </div>
</div>
</#macro>

<script>
    $(document).ready(function () {
        $("#statistic").addClass('active');
    })
</script>