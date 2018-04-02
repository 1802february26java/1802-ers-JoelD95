window.onload = () => {
    //Get Event Listener
    document.getElementById("submit").addEventListener("click", submitReimbursement);

}
function submitReimbursement(){
    let reimbursementTypeID = document.getElementById("reimbursementTypeID").value;
    let reimbursementAmount = document.getElementById("reimbursementAmount").value;
    let reimbursementDescription= document.getElementById("reimbursementDescription").value;
    let xhr = new XMLHttpRequest();
        
    xhr.onreadystatechange = () => {
        if(xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {

            let data2 = JSON.parse(xhr.responseText);
            console.log(data2);
            presentRequest(data2);

        }
    };

    //Doing a HTTP to a specific endpoint
    xhr.open("POST",`submitReimbursement.do?reimbursementTypeID=${reimbursementTypeID}&reimbursementAmount=${reimbursementAmount}&reimbursementDescription=${reimbursementDescription}`);

    //Sending our request
    xhr.send();
}
//}
function presentRequest(data2){
    let submitMessage=document.getElementById("submitMessage");
    if(data2.message) {
        submitMessage.innerHTML = '<span class="label label-danger label-center"> Wrong Input.</span>';
        window.setTimeout(function(){submitMessage.innerHTML=""},3000);
    }
    else {
       document.getElementById("submitMessage").innerHTML = '<span class="label label-success label-center"> Submitted Reimbursement.</span>';
      window.setTimeout(function(){window.location.replace("home.do")},3000);

    }
   ;
}