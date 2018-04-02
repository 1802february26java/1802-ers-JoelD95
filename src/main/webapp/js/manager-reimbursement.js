window.onload = () => {
    document.getElementById("checkPending").addEventListener("click", retrieveAllPending);
    document.getElementById("checkFinalized").addEventListener("click", retrieveAllFinalized);
    document.getElementById("checkAllUserPending").addEventListener("click", retrieveAllUserPending);
    document.getElementById("checkAllUserFinalized").addEventListener("click", retrieveAllUserFinalized);

}
function retrieveAllPending() {
    document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-warning label-center"> Loading...</span>';
    window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML=""},1000);
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {

        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
         
            let data = JSON.parse(xhr.responseText);
            console.log(data);
            presentPendingReimbursement(data);
        }
    };

    
    xhr.open("GET",`getAllPending.do?fetch=allPending`);

  
    xhr.send();
}
function retrieveAllFinalized() {
    document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-warning label-center"> Loading...</span>';
    window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML=""},4250);
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
       
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
         
            let data = JSON.parse(xhr.responseText);

            presentFinalizedReimbursement(data);
        }
    };


    xhr.open("GET",`getAllFinalized.do?fetch=allFinalized`);

 
    xhr.send();
}

 function checkAllUserPending() {
    //AJAX Logic
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        //If the request is DONE (4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //Getting JSON from response body
             var data = JSON.parse(xhr.responseText);
             console.log(data);
             presentMyInfo2(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("GET",`getAllUserPending.do?fetch=pending`);

    //Sending our request
    xhr.send();
}

function retrieveAllUserFinalized() {
    let userID = document.getElementById("userID").value;

    let xhr = new XMLHttpRequest();
    document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-warning label-center"> Loading...</span>';
    window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML=""},4250);
    xhr.onreadystatechange = () => {
       
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            let data = JSON.parse(xhr.responseText);

            presentFinalizedReimbursement(data);
        }
    };

    
    xhr.open("GET",`getAllUserFinalized.do?fetch=allUserFinalized&userID=${userID}`);

    
    xhr.send();
}
function retrieveAllUserPending() {
    document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-warning label-center"> Loading...</span>';
    window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML=""},1000);
    let userID = document.getElementById("userID").value;

    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {

        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

             var data = JSON.parse(xhr.responseText);

             presentPendingReimbursement(data);
        }
    };


    xhr.open("GET",`getAllUserPending.do?fetch=allUserPending&userID=${userID}`);


    xhr.send();
}

function presentPendingReimbursement(data){


    let reimbursementTable = document.getElementById("reimbursementTable");
    let reimbursementMessage = document.getElementById("reimbursementMessage");
    reimbursementTable.innerHTML="";
     if(data.message){}
     else{
        let tableHead= document.getElementById("tableHead");
        tableHead.innerHTML="";
        let head= document.createElement("th");
        let head2= document.createElement("th");
        let head3= document.createElement("th");
        let head4= document.createElement("th");
        let head6= document.createElement("th");
        let head7= document.createElement("th");
        let head8= document.createElement("th");
        let head9= document.createElement("th");
        let head10= document.createElement("th");
        let title = document.createTextNode("Username");
        let title2 = document.createTextNode("Amount");
        let title3 = document.createTextNode("Status");
        let title4 = document.createTextNode("Requested");
        let title7 = document.createTextNode("Description");
        let title8 = document.createTextNode("Approve");
        let title9 = document.createTextNode("Deny");
        let title10= document.createTextNode("Type")
        head.appendChild(title);
        head2.appendChild(title2)
        head3.appendChild(title3);
        head4.appendChild(title4);
        head7.appendChild(title7);
        head8.appendChild(title8);
        head9.appendChild(title9);
        head10.appendChild(title10);
        tableHead.appendChild(head);
        tableHead.appendChild(head2);
        tableHead.appendChild(head10);
        tableHead.appendChild(head7);
        tableHead.appendChild(head3);
        tableHead.appendChild(head4);
        tableHead.appendChild(head8);
        tableHead.appendChild(head9);
        reimbursementTable.innerHTML="";
     data.forEach( (employee) => {

        let buttonApproveNode = document.createElement("button");
        let buttonDenyNode = document.createElement("button");

        buttonApproveNode.className = "btn btn-sm btn-success";
        buttonDenyNode.className = "btn btn-sm btn-danger";

         buttonApproveNode.id="Approve";
         buttonDenyNode.id="Deny";

        let buttonApproveNodeText =document.createTextNode("Approve");
        let buttonDenyNodeText =document.createTextNode("Deny");

        buttonApproveNode.appendChild(buttonApproveNodeText);
        buttonDenyNode.appendChild(buttonDenyNodeText);
         var month =employee.requested.month;
         var day= employee.requested.dayOfMonth;
         var year=employee.requested.year;
         var hour=employee.requested.hour;
         var minute=employee.requested.minute;
         var second=employee.requested.second;
  
         var requestedDate= new Date(`${month}-${day}-${year} ${hour}:${minute}:${second}`);
         var row = reimbursementTable.insertRow(0);
         var cell1 = row.insertCell(0);
         var cell2 = row.insertCell(1);
         var cell3 = row.insertCell(2);
         var cell4 = row.insertCell(3);
         var cell5 = row.insertCell(4);
         var cell6 = row.insertCell(5);
         var cell7 = row.insertCell(6);
         var cell8 = row.insertCell(7);
         cell1.innerHTML=`${employee.requester.username}`;
         cell2.innerHTML=`$${employee.amount}`;
         cell3.innerHTML=`${employee.type.type}`;
         cell4.innerHTML=`${employee.description}`;
         cell5.innerHTML=`${employee.status.status}`;
         cell6.innerHTML=`${requestedDate}`;
         cell7.appendChild(buttonApproveNode);
         cell8.appendChild(buttonDenyNode);
         document.getElementById("Approve").addEventListener("click",finalizeApprove);
         document.getElementById("Deny").addEventListener("click",finalizeDeny);
     
         function finalizeApprove(){
            let xhr = new XMLHttpRequest();
            document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-success label-center"> Successfully Approved Reimbursement.</span>';
            window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML="";retrieveAllPending();},1000);
            xhr.onreadystatechange = () => {
                if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

                    let data = JSON.parse(xhr.responseText);
                }
            };
        

            xhr.open("POST",`getAllPending.do?rID=${employee.id}&reimbursementStatus=3&employeeID=${employee.requester.id}`);
        

            xhr.send();
     }
     function finalizeDeny(){
        let xhr = new XMLHttpRequest();
        
        xhr.onreadystatechange = () => {
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                let data = JSON.parse(xhr.responseText);

            }
        };
        document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-success label-center"> Successfully Denied Reimbursement.</span>';
        window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML="";retrieveAllPending();},1000);
        presentPendingReimbursement(data);
        xhr.open("POST",`getAllPending.do?rID=${employee.id}&reimbursementStatus=2&employeeID=${employee.requester.id}`);
        
        xhr.send();
       
 }
     });
 }
 }


 function presentFinalizedReimbursement(data){
    let reimbursementTable = document.getElementById("reimbursementTable");
    reimbursementTable.innerHTML="";
     if(data.message){}
     else{
        let tableHead= document.getElementById("tableHead");
        tableHead.innerHTML="";
        let head= document.createElement("th");
        let head2= document.createElement("th");
        let head3= document.createElement("th");
        let head4= document.createElement("th");
        let head5 = document.createElement("th");
        let head6= document.createElement("th");
        let head7= document.createElement("th");
        let head8= document.createElement("th");
        let head10= document.createElement("th");
        let title = document.createTextNode("Username");
        let title2 = document.createTextNode("Amount");
        let title3 = document.createTextNode("Status");
        let title4 = document.createTextNode("Requested");
        let title5 = document.createTextNode("Resolved");
        let title7 = document.createTextNode("Description");
        let title8 = document.createTextNode("Approver");
        let title10= document.createTextNode("Type");
        head.appendChild(title);
        head2.appendChild(title2)
        head3.appendChild(title3);
        head4.appendChild(title4);
        head7.appendChild(title7);
        head10.appendChild(title10);
        head5.appendChild(title5);
        head8.appendChild(title8)
        tableHead.appendChild(head);
        tableHead.appendChild(head2);
        tableHead.appendChild(head10);
        tableHead.appendChild(head7);
        tableHead.appendChild(head3);
        tableHead.appendChild(head4);
        tableHead.appendChild(head5);
        tableHead.appendChild(head8);
        reimbursementTable.innerHTML="";

     data.forEach( (employee) => {

        var month =employee.requested.month;
        var day= employee.requested.dayOfMonth;
        var year=employee.requested.year;
        var hour=employee.requested.hour;
        var minute=employee.requested.minute;
        var second=employee.requested.second;

        var month2 =employee.resolved.month;
        var day2= employee.resolved.dayOfMonth;
        var year2=employee.resolved.year;
        var hour2=employee.resolved.hour;
        var minute2=employee.resolved.minute;
        var second2=employee.resolved.second;
        var requestedDate= new Date(`${month}-${day}-${year} ${hour}:${minute}:${second}`);
        var resolvedDate= new Date(`${month2}-${day2}-${year2} ${hour2}:${minute2}:${second2}`);
        let myTable= document.getElementById("myTable");
        var row = reimbursementTable.insertRow(0);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        var cell6 = row.insertCell(5);
        var cell7 = row.insertCell(6);
        var cell8 = row.insertCell(7);
        cell1.innerHTML=`${employee.requester.username}`;
        cell2.innerHTML=`$${employee.amount}`;
        cell3.innerHTML=`${employee.type.type}`;
        cell4.innerHTML=`${employee.description}`;
        cell5.innerHTML=`${employee.status.status}`;
        cell6.innerHTML=`${requestedDate}`;
        cell7.innerHTML=`${resolvedDate}`;
        cell8.innerHTML=`${employee.approver.username}`;
         
     });
    }
 }
