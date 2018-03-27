window.onload = () => {
    //Get Event Listener
    document.getElementById("submit").addEventListener("click", submitUpdate);


function submitUpdate(){
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let email = document.getElementById("email").value;
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            //let data2 = JSON.parse(xhr.responseText);

            presentMyUpdatedInfo();
        }
    };

    //Doing a HTTP to a specific endpoint
    console.log("posting");
    xhr.open("POST",`update.do?firstName=${firstName}&lastName=${lastName}&email=${email}`);

    //Sending our request
    xhr.send();
}
}
function presentMyUpdatedInfo(){
  

        window.location.replace("myAccount.do");
}