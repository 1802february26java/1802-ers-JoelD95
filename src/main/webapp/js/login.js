// Add our event listener 
window.onload = () => {

    /**redirect user to the right url if we comes form somewhere else */
    if(window.location.pathname !== "/ERS/login.do"){
        window.location.replace("login.do");
    }
    // Login Event Listener
    // add a call back function to click
    document.getElementById("login").addEventListener("click", () => {
        let username = document.getElementById("username").value;
        let password = document.getElementById("password").value;

        // AJAX Logic
        let xhr = new XMLHttpRequest();

        xhr.onreadystatechange = () => {
            // If the request is DONE (4), and everything is ok 
            if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                // Getting JSON from response body
                let data = JSON.parse(xhr.responseText);
                console.log(data);

                // Call login response processing
                login(data);
            }
        };

        // Doing a HTTP to s specific endpoint
        xhr.open("POST",`login.do?username=${username}&password=${password}`);

        // Sending our request
        xhr.send();
    });
}

function login(data) {
    
    // If message is a member of the JSON, it was AUTHENTICATION FAILED
    if(data.message) {
        document.getElementById("loginMessage").innerHTML = '<span class="label label-danger label-center"> Wrong credentials.</span>';
    }
    else {
        
        // Using sessionStorage of JavaScript
        sessionStorage.setItem("employeeId", data.id);
        sessionStorage.setItem("employeeUsername", data.username);
        window.location.replace("home.do");
    }
}