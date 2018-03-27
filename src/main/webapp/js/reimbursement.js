window.onload = () => {
    //Get Event Listener
    document.getElementById("checkID").addEventListener("click", checkID);
}

function checkID() {
    let reimbursementID = document.getElementById("reimbursementID").value;


    //AJAX Logic
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        //If the request is DONE (4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //Getting JSON from response body
            let data = JSON.parse(xhr.responseText);
            console.log(data);
            presentMyInfo(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("POST",`reimbursement.do?reimbursementID=${reimbursementID}`);

    //Sending our request
    xhr.send();
}

function presentMyInfo(data) {
    //If message is a member of the JSON, something went wrong
    if(data.message) {
        document.getElementById("reimbursementMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    //We present all the customers to the user
    else {
        //Get customer list node
        let reimbursementInfo = document.getElementById("reimbursementInfo");

        //Clean customer list
        reimbursementInfo.innerHTML = "";
        var date = `${data.requested.year} ${data.requested.monthValue} ${data.requested.dayOfMonth} ${data.requested.hour} ${data.requested.minute} ${data.requested.second}`;
        let newDate = new Date(Date.UTC(date[0], date[1]-1, date[2], date[3], date[4], date[5]));
        let employeeNodeText= document.createTextNode(`Request ID: ${data.id}, Requested: ${newDate}`)
        reimbursementInfo.appendChild(employeeNodeText);
    }
}
