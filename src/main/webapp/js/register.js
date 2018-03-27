// Add our event listener 
window.onload = () => {

    /** */

    // register Event Listener
    // add a call back function to click
    document.getElementById("submit").addEventListener("click", () => {
        //check passwords are the same
        let password = document.getElementById("password").value;
        let repeatPassword = document.getElementById("repeatPassword").value;
        if(password !== repeatPassword){
            document.getElementById("registrationMessage").innerHTML = '<span class="label label-danger label-center"> Password mismatch.</span>';
            return;
        }
        //get the rest of the fields
        let firstName = document.getElementById("firstName").value;
        let lastName = document.getElementById("lastName").value;
        let username = document.getElementById("username").value;


        // AJAX Logic
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = () => {
            // If the request is DONE (4), and everything is ok 
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Getting JSON from response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                // Call registration response processing
                register(data);
            }
        };

        // Doing a HTTP to a specific endpoint
        xhr.open("POST",`register.do?firstName=${firstName}&lastName=${lastName}&username=${username}&password=${password}`);

        // Sending our request
        xhr.send();
    });
}
function disableAllComponents(){
    document.getElementById("firstName").setAttribute("disabled","disabled");
    document.getElementById("lastName").setAttribute("disabled","disabled");
    document.getElementById("username").setAttribute("disabled","disabled");
    document.getElementById("password").setAttribute("disabled","disabled");
    document.getElementById("repeatPassword").setAttribute("disabled","disabled");
}

function register(data) {
    
    // If message is a member of the JSON, something went wrong
    if(data.message==="REGISTRATION SUCCESSFUL") {
        disableAllComponents();
        document.getElementById("registrationMessage").innerHTML = '<span class="label label-success label-center"> Registration.</span>';
        setTimeout(() => {window.location.replace("login.do");},3000);
         

    }
    else {
        //SOMETHING WENT WRONG
        document.getElementById("registrationMessage").innerHTML = '<span class="label label-danger label-center"> something went wrong.</span>';
       
    }
}