window.onload = () => {
    retrieveAllEmployees();

}
function retrieveAllEmployees() {
    //AJAX Logic
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        //If the request is DONE (4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //Getting JSON from response body
            let data = JSON.parse(xhr.responseText);
            console.log(data);
            presentEmployees(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("GET",`getAllEmployees.do?fetch=employees`);

    //Sending our request
    xhr.send();
}
function presentEmployees(data){
     if(data.message){}
     else{
    let employeeTable = document.getElementById("employeeTable");
    employeeTable.innerHTML="";
     let tableHead= document.getElementById("tableHead");
     tableHead.innerHTML="";
     let head= document.createElement("th");
     let head2= document.createElement("th");
     let head3= document.createElement("th");
     let head4= document.createElement("th");
     let head5= document.createElement("th");
     let head6= document.createElement("th");
     let head7= document.createElement("th");

     let title = document.createTextNode("Employee ID");
     let title2 = document.createTextNode("Username");
     let title3 = document.createTextNode("First Name");
     let title4 = document.createTextNode("Last Name");
     let title5 = document.createTextNode("Role");
     let title6 = document.createTextNode("Email");
     head.appendChild(title);
     head2.appendChild(title2)
     head3.appendChild(title3);
     head4.appendChild(title4);
     head5.appendChild(title5);
     head6.appendChild(title6);
     tableHead.appendChild(head);
     tableHead.appendChild(head2);
     tableHead.appendChild(head3);
     tableHead.appendChild(head4);
     tableHead.appendChild(head5);
     tableHead.appendChild(head6);
     employeeTable.innerHTML="";

     data.forEach( (employee) => {

        let myTable= document.getElementById("myTable");
        var row = employeeTable.insertRow(0);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        var cell6 = row.insertCell(5);
        cell1.innerHTML=`${employee.id}`;
        cell2.innerHTML=`${employee.username}`;
        cell3.innerHTML=`${employee.firstName}`;
        cell4.innerHTML=`${employee.lastName}`;
        cell5.innerHTML=`${employee.employeeRole.type}`;
        cell6.innerHTML=`${employee.email}`;
     });
 }
 }