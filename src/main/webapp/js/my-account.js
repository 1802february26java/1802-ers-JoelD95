window.onload = () => {
    document.getElementById("updateInfo").addEventListener("click", updateInfo);

    getInfo();
}

function getInfo() {
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            presentMyInfo(data);
        }
    };

    xhr.open("GET",`myAccount.do?fetch=LIST`);

    xhr.send();
}

function presentMyInfo(data) {
    if(data.message) {
        document.getElementById("userMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    else {
        let userInfo = document.getElementById("userInfo");
        let infoNode = document.createElement("li");
        let infoNode2 = document.createElement("li");
        let infoNode3 = document.createElement("li");
        let infoNode4 = document.createElement("li");
        let infoNode5 = document.createElement("li");
        let breakNode = document.createElement("br");
        infoNode.className = "list-group-item";
        infoNode2.className = "list-group-item";
        infoNode3.className = "list-group-item";
        infoNode4.className = "list-group-item";
        infoNode5.className = "list-group-item";
       userInfo.innerHTML = "";
        let employeeNodeText= document.createTextNode(`First name: ${data.firstName}`)
        let employeeNodeText2= document.createTextNode(`Last name: ${data.lastName}`)
        let employeeNodeText3= document.createTextNode(`ID: ${data.id}`)
        let employeeNodeText4= document.createTextNode(`Username: ${data.username}`)
        let employeeNodeText5= document.createTextNode(`E-mail: ${data.email}`)
        infoNode.appendChild(employeeNodeText);
        infoNode2.appendChild(employeeNodeText2);
        infoNode3.appendChild(employeeNodeText3);
        infoNode4.appendChild(employeeNodeText4);
        infoNode5.appendChild(employeeNodeText5);
       userInfo.appendChild(infoNode);
       userInfo.appendChild(infoNode2);
       userInfo.appendChild(infoNode3);
       userInfo.appendChild(infoNode4);
       userInfo.appendChild(infoNode5);
    }
}
function updateInfo() {
    window.location.replace("update.do");
}
