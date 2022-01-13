function lookAtFindByIdSection() {
    openSection("#findByIdSection");
}

function lookAtAddNewSection() {
    openSection("#addNewSection");
}

function lookAtAll(context) {
    openSection("#allSection");
    $.ajax({
        type: "GET",
        url: `${context}/employees`,
        success: function (employees) {
            let view = $("#allEmployeesView");
            view.html("");
            let table = createTableWithHeader();
            view.append(table);
            employees.forEach(function (employee) {
                    table.appendChild(getRowView(employee));
                }
            )
        }
    });
}

function openSection(id) {
    let sectionIDs = ["#findByIdSection", "#addNewSection", "#allSection"];
    sectionIDs.forEach(function (sectionID) {
        if (sectionID === id)
            $(sectionID).show();
        else $(sectionID).hide();
    });
}

function findByID(context) {
    let employeeID = $("#employee_ID").val();
    $.ajax({
        type: "GET",
        url: `${context}/employees/${employeeID}`,
        success: function (response) {
            let view = $('#foundByIdEmployeeView');
            $('#employee-control-buttons').show();
            let deleteButton = $('#employee-delete-button');
            deleteButton.attr('property', response.id);
            view.html("");
            let table = createTableWithHeader();
            table.appendChild(getRowView(response));
            view.append(table);
        },
        error: function (xhr) {
            if (xhr.status === 404) {
                alert("The provided ID is incorrect");
                $("#employee_ID").val("");
            }
        }
    });
}

function deleteEmployee(id, context) {
    $.ajax({
        type: "DELETE",
        url: `${context}/employees/${id}`,
        success: function () {
            $('#foundByIdEmployeeView').html('');
            $('#employee-control-buttons').hide();
            lookAtAll(context);
        }
    });
}

function saveNewEmployee(context) {
    let formData = new FormData($("#new-form")[0]);
    $.ajax({
        type: "POST",
        url: `${context}/employees`,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(Object.fromEntries(formData)),
        success: function () {
            lookAtAll(context);
        }
    });
}

function getRowView(employee) {
    let newRow = document.createElement("tr");
    let employeeMap = new Map();
    employeeMap.set(0, employee.id);
    employeeMap.set(1, employee.firstName);
    employeeMap.set(2, employee.lastName);
    employeeMap.set(3, employee.departmentId);
    employeeMap.set(4, employee.jobTitle);
    employeeMap.set(5, employee.gender);
    employeeMap.set(6, employee.dateOfBirth);

    for (let counter = 0; counter < employeeMap.size; counter++) {
        let newCell = document.createElement("td");
        newCell.innerHTML = employeeMap.get(counter);
        newRow.appendChild(newCell);
    }
    return newRow;
}


function createTableWithHeader() {
    let newTable = document.createElement("table");
    newTable.innerHTML = "<table>" +
        "<tr>" +
        "<th>ID</th>" +
        "<th>First Name</th>" +
        "<th>Last Name</th>" +
        "<th>Department ID</th>" +
        "<th>Job title</th>" +
        "<th>Gender</th>" +
        "<th>Date of birth</th>" +
        "</tr><" +
        "/table>";
    return newTable;
}
