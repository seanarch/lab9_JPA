
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="styles.css">
        <title>User management page</title>
    </head>
    <body>
        <div class="container">
            <div class="left" id="addTable">
                <h3>Add User</h3>
                <ul>
                    <form method="post">
                    <li><input type="text" name="email" value="${user.email}" placeholder="Email"></li>
                    <li><input type="text" name="firstName" value="${user.firstName}" placeholder="First Name"></li>
                    <li><input type="text" name="lastName" value="${user.lastName}" placeholder="Last Name"></li>
                    <li><input type="password" name="password" value="${user.password}" placeholder="Password" autocomplete="on"></li>
                    <li>
                        <select name="role">
                            <option value="1"
                                <c:if test="${user.role == 1}">
                                    selected
                                </c:if>
                            >system admin</option>
                            <option value="2"
                                <c:if test="${user.role == 2}">
                                    selected
                                </c:if>
                            >regular user</option>
                            <option value="3"
                                <c:if test="${user.role == 3}">
                                    selected
                                </c:if>
                            >company admin</option>
                        </select>
                    </li>
                    <li>
                        <label>Active</label>&nbsp;<input type="checkbox" name="active"
                        <c:if test="${user.active}">
                            checked
                        </c:if>>  
                    </li>
                    <li class="center green">
                        <input type="hidden" name="action" value="add">
                        <input class="green" type="submit" value="Save" >
                    </li>
                    <c:if test="${errorMessage_EddUser}">
                        <p class="red center">&nbsp;&nbsp;Error. Please fill all fields.</p>
                    </c:if>
                    </form>
                </ul>
            </div>
            <div class="middle" id="manageTable">
                <h3>Manage Users</h3>
                <ul>
                    <li>
                        <span>Email</span>
                        <span>First Name</span>
                        <span>Last Name</span>
                        <span>Role</span>
                        <span>Active</span>
                        <span>Edit</span>
                        <span>Delete</span>
                    </li>
                
                <c:forEach var="user" items="${userList}">
                    <li>
                    <span>${user.email}</span>
                    <span>${user.firstName}</span>
                    <span>${user.lastName}</span>
                    <span>${roles.getRoleName(user.role)}</span>
                    <span>${user.active}</span>
                    <span>
                        <form method="get">
                            <input class="blue" type="submit" value="Edit">
                            <input type="hidden" name="action" value="edit">
                            <input type="hidden" name="userPrimaryKey" value="${user.email}">
                        </form>
                    </span>
                    <span>
                        <form method="post">
                            <input class="blue" type="submit" value="Delete">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="userPrimaryKey" value="${user.email}">
                        </form>
                    </span>
                </li>
                </c:forEach>
            </ul> 
              
            
            </div>
            <div class="right" id="editTable">
                <h3>Edit Users</h3>
                <ul>
                    <form method="post">
                    <li><input type="text" name="update_email" value="${selectedUser.email}" placeholder="Email" readonly></li>
                    <li><input type="text" name="update_firstName" value="${selectedUser.firstName}" placeholder="First Name"></li>
                    <li><input type="text" name="update_lastName" value="${selectedUser.lastName}" placeholder="Last Name"></li>
                    <li><input type="password" name="update_password" value="${selectedUser.password}" placeholder="Password" autocomplete="on"></li>
                    <li>
                        <select name="update_role">                    
                            <option value="1"
                                <c:if test="${selectedUser.role == 1}">
                                    selected
                                </c:if>
                            >system admin</option>
                            <option value="2"
                                <c:if test="${selectedUser.role == 2}">
                                    selected
                                </c:if>
                            >regular user</option>
                            <option value="3"
                                <c:if test="${selectedUser.role == 3}">
                                    selected
                                </c:if>
                            >company admin</option>
                        </select>
                    </li>
                    <li>
                        <label>Active</label>&nbsp;<input type="checkbox" name="update_active" value="${selectedUser.active}"
                        <c:if test="${selectedUser.active}">
                            checked
                        </c:if>>  
                    </li>
                    <li class="center green">
                        <input type="hidden" name="action" value="edit">
                        <input class="green" type="submit" value="Save">
                    </li>
                    </form>
                    <li class="grey">
                        <a href="users" class="btn">Cancel</a>
                    </li>
                    
                    <c:if test="${errorMessage_EditUser}">
                        <p class="red center">&nbsp;&nbsp;Error. Please fill all fields.</p> 
                    </c:if> 
                </ul>  
            </div>
        </div>
    </body>
</html>
