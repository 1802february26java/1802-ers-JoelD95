window.onload = () => {
    //Get Event Listener
    document.getElementById("updateInfo").addEventListener("click", updateInfo);

    //Get all customers as soon as the page loads
    getInfo();
}

function getInfo() {
    //AJAX Logic
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        //If the request is DONE (4), and everything is OK
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
            //Getting JSON from response body
            let data = JSON.parse(xhr.responseText);
            console.log(data);

            //Present the data to the user
            presentMyInfo(data);
        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("GET",`myAccount.do?fetch=LIST`);

    //Sending our request
    xhr.send();
}

function presentMyInfo(data) {
    //If message is a member of the JSON, something went wrong
    if(data.message) {
        document.getElementById("userMessage").innerHTML = '<span class="label label-danger label-center">Something went wrong.</span>';
    }
    //We present all the customers to the user
    else {
        //Get customer list node
        let userInfo = document.getElementById("userInfo");

        //Clean customer list
       userInfo.innerHTML = "";
        let employeeNodeText= document.createTextNode(`First name: ${data.firstName}, Last name: ${data.lastName}, ID: ${data.id}, Username: ${data.username}, E-mail: ${data.email}`)
       userInfo.appendChild(employeeNodeText);
    }
}
function updateInfo() {
    window.location.replace("update.do");
}
