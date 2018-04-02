window.onload = () => {
    document.getElementById("submit").addEventListener("click", submitUpdate);


function submitUpdate(){
    let firstName = document.getElementById("firstName").value;
    let lastName = document.getElementById("lastName").value;
    let email = document.getElementById("email").value;
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            let data2 = JSON.parse(xhr.responseText);

            presentMyUpdatedInfo(data2);
        }
    };
    xhr.open("POST",`update.do?firstName=${firstName}&lastName=${lastName}&email=${email}`);

    xhr.send();
}
}
function presentMyUpdatedInfo(data2){
    if(data2.message){
        document.getElementById("updateMessage").innerHTML = '<span class="label label-danger label-center"> Please input all Fields.</span>';
        }
        else
        window.location.replace("myAccount.do");
        window.setTimeout(function(){window.location.replace("myAccount.do")},3000);
}