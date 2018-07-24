<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
Add new user
<b>${message}</b>
<@l.login "/registartion" />
</@c.page>