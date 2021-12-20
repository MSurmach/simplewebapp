<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Employees</title>
</head>
<link href="${pageContext.request.contextPath}/styles/style.css" rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/scripts/home.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<body>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<div class="main">
    <div class="row centered">
        <span class="attractive-text">Employees Database</span>
    </div>
    <hr>
    <div class="row centered">
        <input type="button" class="button" value="Find by ID" onclick="lookAtFindByIdSection()">
        <input type="button" class="button" value="See all" onclick="lookAtAll('${context}')">
        <input type="button" class="button" value="Add new..." onclick="lookAtAddNewSection()">
    </div>
    <hr>
    <div class="row centered" id="findByIdSection" hidden>
        <span class="small-attractive-text">To find out about the employee, please type in his id: </span>
        <input type="text" class="centered" id="employee_ID">
        <input type="button" class="button" value="Find"
               onclick="findByID('${context}')">
        <div id="foundByIdEmployeeView">
        </div>
    </div>
    <div class="row centered" id="allSection" hidden>
        <div id="allEmployeesView">
        </div>
    </div>
    <div class="row centered" id="addNewSection" hidden>
        <form id="new-form">
            <label for="firstName" class="small-attractive-text">First name: </label>
            <input type="text" name="firstName">
            <br>
            <label for="lastName" class="small-attractive-text">Last name: </label>
            <input type="text" name="lastName">
            <br>
            <label for="departmentId" class="small-attractive-text">Department ID: </label>
            <input type="text" name="departmentId">
            <br>
            <label for="jobTitle" class="small-attractive-text">Job Title: </label>
            <input type="text" name="jobTitle">
            <br>
            <label for="dateOfBirth" class="small-attractive-text">Date of
                birth: </label>
            <input type="date" name="dateOfBirth">
            <br>
            <label for="gender"
                   class="small-attractive-text">Gender: </label>
            <select name="gender">
                <option value="MALE">MALE</option>
                <option value="FEMALE">FEMALE</option>
            </select>
            <br>
            <input type="button" class="button" value="Save"
                   onclick="saveNewEmployee('${context}')">
            <input type="reset" class="button"
                   value="Clear">
        </form>
    </div>
</div>
</body>
</html>