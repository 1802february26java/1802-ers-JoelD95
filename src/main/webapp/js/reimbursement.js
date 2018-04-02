window.onload = () => {
    document.getElementById("checkID").addEventListener("click", checkID);
    document.getElementById("checkAllUserPending").addEventListener("click",checkAllUserPending);
    document.getElementById("checkAllUserFinalized").addEventListener("click", checkAllUserFinalized);

}

function checkID() {
    let reimbursementID = document.getElementById("reimbursementID").value;
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
     
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
           
            console.log(xhr.responseText);
            let data = JSON.parse(xhr.responseText);
            console.log(data);
            presentMyInfo(data);
        }
    };


    xhr.open("POST",`reimbursement.do?reimbursementID=${reimbursementID}`);


    xhr.send();
}

function presentMyInfo(data) {
    if(data.message) {
        document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    else if (data.resolved===null){
        reimbursementTable.innerHTML="";
        let tableHead= document.getElementById("tableHead");
        tableHead.innerHTML="";
        let head= document.createElement("th");
        let head2= document.createElement("th");
        let head3= document.createElement("th");
        let head4= document.createElement("th");
        let head6= document.createElement("th");
        let head7= document.createElement("th");
        let title = document.createTextNode("Username");
        let title2 = document.createTextNode("Amount");
        let title3 = document.createTextNode("Status");
        let title4 = document.createTextNode("Requested");
        let title7 = document.createTextNode("Description");
        head.appendChild(title);
        head2.appendChild(title2)
        head3.appendChild(title3);
        head4.appendChild(title4);
        head7.appendChild(title7);
        tableHead.appendChild(head);
        tableHead.appendChild(head2);
        tableHead.appendChild(head7);
        tableHead.appendChild(head3);
        tableHead.appendChild(head4);

        var month =data.requested.month;
        var day= data.requested.dayOfMonth;
        var year=data.requested.year;
        var hour=data.requested.hour;
        var minute=data.requested.minute;
        var second=data.requested.second;
 
        var requestedDate= new Date(`${month}-${day}-${year} ${hour}:${minute}:${second}`);
        var row = reimbursementTable.insertRow(0);
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);
        var cell5 = row.insertCell(4);
        cell1.innerHTML=`${data.requester.username}`;
        cell2.innerHTML=`$${data.amount}`;
        cell3.innerHTML=`${data.description}`;
        cell4.innerHTML=`${data.status.status}`;
        cell5.innerHTML=`${requestedDate}`;
    }
    else{
        let reimbursementMessage = document.getElementById("reimbursementMessage");
        let tableHead= document.getElementById("tableHead");
        tableHead.innerHTML="";
        let head= document.createElement("th");
        let head2= document.createElement("th");
        let head3= document.createElement("th");
        let head4= document.createElement("th");
        let head5= document.createElement("th");
        let head6= document.createElement("th");
        let head7= document.createElement("th");
        let title = document.createTextNode("Username");
        let title2 = document.createTextNode("Amount");
        let title3 = document.createTextNode("Status");
        let title4 = document.createTextNode("Requested");
        let title5 = document.createTextNode("Finalized");
        let title6 = document.createTextNode("Approver");
        let title7 = document.createTextNode("Description");

        head.appendChild(title);
        head2.appendChild(title2)
        head3.appendChild(title3);
        head4.appendChild(title4);
        head5.appendChild(title5);
        head6.appendChild(title6);
        head7.appendChild(title7);
        tableHead.appendChild(head);
        tableHead.appendChild(head2);
        tableHead.appendChild(head7);
        tableHead.appendChild(head3);
        tableHead.appendChild(head4);
        tableHead.appendChild(head5);
        tableHead.appendChild(head6);
        
        reimbursementTable.innerHTML="";
        var month =data.requested.month;
        var day= data.requested.dayOfMonth;
        var year=data.requested.year;
        var hour=data.requested.hour;
        var minute=data.requested.minute;
        var second=data.requested.second;

        var month2 =data.resolved.month;
        var day2= data.resolved.dayOfMonth;
        var year2=data.resolved.year;
        var hour2=data.resolved.hour;
        var minute2=data.resolved.minute;
        var second2=data.resolved.second;
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
        cell1.innerHTML=`${data.requester.username}`;
        cell2.innerHTML=`$${data.amount}`;
        cell3.innerHTML=`${data.description}`;
        cell4.innerHTML=`${data.status.status}`;
        cell5.innerHTML=`${requestedDate}`;
        cell6.innerHTML=`${resolvedDate}`;
        cell7.innerHTML=`${data.approver.username}`;
    }
}

function checkAllUserPending() {
    document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-warning label-center"> Loading...</span>';
    window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML=""},1000);
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
 
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
    
             var data = JSON.parse(xhr.responseText);
             presentMyPendingInfo(data);
        }
    };

 
    xhr.open("GET",`getAllUserPending.do?fetch=pending`);

   
    xhr.send();
}

function checkAllUserFinalized() {
    document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-warning label-center"> Loading...</span>';
    window.setTimeout(function(){document.getElementById("reimbursementMessage").innerHTML=""},4250);
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {

        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            let data = JSON.parse(xhr.responseText);
            presentMyInfo2(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("GET",`getAllUserFinalized.do?fetch=finalized`);

    //Sending our request
    xhr.send();
}
function presentMyPendingInfo(data){
    if(data.message){}
    else{
       let reimbursementMessage = document.getElementById("reimbursementMessage");
       let tableHead= document.getElementById("tableHead");
       tableHead.innerHTML="";
       let head= document.createElement("th");
       let head2= document.createElement("th");
       let head3= document.createElement("th");
       let head4= document.createElement("th");
       let head6= document.createElement("th");
       let head7= document.createElement("th");
       let head8= document.createElement("th");
       let title = document.createTextNode("Username");
       let title2 = document.createTextNode("Amount");
       let title3 = document.createTextNode("Status");
       let title4 = document.createTextNode("Requested");
       let title7 = document.createTextNode("Description");
       let title8 = document.createTextNode("Request ID");
       head.appendChild(title);
       head2.appendChild(title2)
       head3.appendChild(title3);
       head4.appendChild(title4);
       head7.appendChild(title7);
       head8.appendChild(title8);
       tableHead.appendChild(head);
       tableHead.appendChild(head2);
       tableHead.appendChild(head7);
       tableHead.appendChild(head3);
       tableHead.appendChild(head4);
       tableHead.appendChild(head8);
       reimbursementTable.innerHTML="";
    data.forEach( (employee) => {
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
       cell1.innerHTML=`${employee.requester.username}`;
       cell2.innerHTML=`$${employee.amount}`;
       cell3.innerHTML=`${employee.description}`;
       cell4.innerHTML=`${employee.status.status}`;
       cell5.innerHTML=`${requestedDate}`;
       cell6.innerHTML=`${employee.id}`;
    });
}

}
function presentMyInfo2(data){
     if(data.message){
        document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-danger label-center"> Please try again later.</span>';

     }
     else{
        let reimbursementMessage = document.getElementById("reimbursementMessage");
        let tableHead= document.getElementById("tableHead");
        tableHead.innerHTML="";
        let head= document.createElement("th");
        let head2= document.createElement("th");
        let head3= document.createElement("th");
        let head4= document.createElement("th");
        let head5= document.createElement("th");
        let head6= document.createElement("th");
        let head7= document.createElement("th");
        let title = document.createTextNode("Username");
        let title2 = document.createTextNode("Amount");
        let title3 = document.createTextNode("Status");
        let title4 = document.createTextNode("Requested");
        let title5 = document.createTextNode("Finalized");
        let title6 = document.createTextNode("Approver");
        let title7 = document.createTextNode("Description");
        head.appendChild(title);
        head2.appendChild(title2)
        head3.appendChild(title3);
        head4.appendChild(title4);
        head5.appendChild(title5);
        head6.appendChild(title6);
        head7.appendChild(title7);
        tableHead.appendChild(head);
        tableHead.appendChild(head2);
        tableHead.appendChild(head7);
        tableHead.appendChild(head3);
        tableHead.appendChild(head4);
        tableHead.appendChild(head5);
        tableHead.appendChild(head6);
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
        cell1.innerHTML=`${employee.requester.username}`;
        cell2.innerHTML=`$${employee.amount}`;
        cell3.innerHTML=`${employee.description}`;
        cell4.innerHTML=`${employee.status.status}`;
        cell5.innerHTML=`${requestedDate}`;
        cell6.innerHTML=`${resolvedDate}`;
        cell7.innerHTML=`${employee.approver.username}`;
     });
 }
 }
